package overflowdb.codegen

import java.io.File
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import ujson._

object SchemaMerger {
  object FieldNames {
    /* we're merging elements based on the name field */
    val Name = "name"

    /* ids must not have duplicates within a given collection */
    val Id = "id"
  }

  def mergeCollections(inputFiles: Seq[File]): File = {
    import better.files.FileExtensions

    val inputJsons = inputFiles.map { file =>
      val jsonString = file.toScala.lines.filterNot(isComment).mkString("\n")
      Obj(ujson.read(jsonString).obj)
    }
    val mergedJson = mergeCollections(inputJsons)
    better.files.File.newTemporaryFile(getClass.getSimpleName).write(
      write(mergedJson)
    ).toJava
  }

  /* merge the first level elements, assuming they are all named lists */
  def mergeCollections(inputJsons: Seq[Obj]): Obj = {
    val result = mutable.LinkedHashMap.empty[String, Value]
    inputJsons.foreach { json =>
      json.value.foreach { case (name, value) =>
        result.get(name) match {
          case None =>
            // first time we encounter this element, simply add it to the result
            result.put(name, value)
          case Some(oldValue) =>
            // we've seen this element before, merge the old and new ones
            result.put(name, mergeLists(oldValue.arr, value.arr))
        }
      }
    }

    verifyNoDuplicateIds(result.iterator)
    Obj.from(result)
  }

  /* for any node that has `containedNode` entries, automatically add the corresponding `outEdges`
  * n.b. not strictly a `merge` feature, but closely related - in mergeSchemas.py before... */
  def addMissingContainsEdges(json: Obj): Obj = {
    val result = ujson.copy(json).obj
    result("nodeTypes").arr.map(_.obj).foreach { nodeType =>
      val outEdges = nodeType("outEdges").arr
      val outEdgeNames = outEdges.map(_.obj("edgeName").str).toSet

      if (!outEdgeNames.contains("CONTAINS_NODE")) {
        val containsNodeEntry = read(""" { "edgeName": "CONTAINS_NODE", "inNodes": ["NODE"] }""")
        outEdges.append(containsNodeEntry)
      }

      val requiredInNodesForContains = nodeType.get("containedNodes").map(_.arr).getOrElse(Nil).map { containedNode =>
        containedNode.obj("nodeType").str
      }

      /* replace entry with `edge["edgeName"] == "CONTAINS_NODE"` if it exists, or add one if it doesn't.
       * to do that, convert outEdges to Map<EdgeName, OutEdge> and back at the end */
      val inNodesByOutEdgeName = outEdges.map { edge =>
        edge.obj("edgeName").str -> edge.obj("inNodes").arr.map(_.str)
      }.toMap
      val containsInNodesBefore = inNodesByOutEdgeName.getOrElse("CONTAINS_NODE", Seq.empty)
      val containsInNodes = (containsInNodesBefore ++ requiredInNodesForContains).distinct

      outEdges.clear
      inNodesByOutEdgeName.+("CONTAINS_NODE" -> containsInNodes).foreach { case (edgeName, inNodes) =>
        outEdges.append(Obj(
          "edgeName" -> edgeName,
          "inNodes" -> inNodes
        ))
      }

    }
    result
  }


  private def mergeLists(oldValues: ArrayBuffer[Value], newValues: ArrayBuffer[Value]): Arr = {
    val combined = (oldValues ++ newValues).map(_.obj)
    val byName = combined.groupBy(_(FieldNames.Name))
    val combinedElements = byName.map { case (elementName, keyValues) =>
      val combinedElement = mutable.LinkedHashMap.empty[String, Value]
      keyValues.foreach(_.foreach { case (name, value) =>
        combinedElement.put(name, value) match {
          case Some(oldValue) if name != FieldNames.Name =>
            throw new AssertionError(s"$elementName cannot be merged, because it defines the property " +
              s"$name multiple times with different values: $oldValue, $value")
          case _ => // new entry, i.e. no duplicate
        }
      })
      combinedElement
    }
    Arr.from(combinedElements.map(Obj.from))
  }

  private def verifyNoDuplicateIds(collections: Iterator[(String, Value)]) =
    collections.foreach { case (collectionName, Arr(entries)) =>
      val idValues = entries.map(_.obj.get(FieldNames.Id)).flatten
      if (idValues.size != idValues.distinct.size) {
        // there are duplicate ids!
        val duplicateIds = idValues.groupBy(id => id).collect {
          case (id, ids) if ids.size > 1 => id
        }
        throw new AssertionError(s"duplicate ids for collection `$collectionName`: ${duplicateIds.mkString(",")}")
      }
    }

  private def isComment(line: String): Boolean =
    line.trim.startsWith("//")
}

