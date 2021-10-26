package io.shiftleft.semanticcpg.passes.linking.calllinker

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{DispatchTypes, EdgeTypes}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.mutable

class DynamicCallLinker(cpg: Cpg) extends CpgPass(cpg) {

  import DynamicCallLinker._

  // Used to track potential method candidates for a given method fullname. Since our method
  // full names contain the type decl we don't need to specify an addition map to wrap this in
  private val validM = mutable.Map.empty[String, Set[String]]
  // Used for dynamic programming as subtree's don't need to be recalculated later
  private val subclassCache = mutable.Map.empty[String, Set[String]]

  /** Main method of enhancement - to be implemented by child class
   */
  override def run(): Iterator[DiffGraph] = {
    val dstGraph = DiffGraph.newBuilder

    // ValidM maps class C and method name N to the set of
    // func ptrs implementing N for C and its subclasses
    cpg.typeDecl
      .flatMap { c =>
        c._methodViaAstOut.map { n =>
          (
            n.fullName,
            allSubclasses(c.fullName).flatMap(sc => staticLookup(sc, n))
          )
        }.toMap
      }
      .foreach { case (methodName, candidates) => validM.put(methodName, candidates) }

    subclassCache.clear()

    cpg.call.filter(_.dispatchType == DispatchTypes.DYNAMIC_DISPATCH).foreach { call =>
      try {
        linkDynamicCall(call, dstGraph)
      } catch {
        case exception: Exception =>
          throw new RuntimeException(exception)
      }
    }

    Iterator(dstGraph.build())
  }

  /** Recursively returns all the sub-types of the given type declaration
   */
  def allSubclasses(typDeclFullName: String): Set[String] = {
    subclassCache.get(typDeclFullName) match {
      case Some(value) => value
      case None =>
        val directSubclasses =
          cpg.typ
            .nameExact(typDeclFullName)
            .flatMap(_.in(EdgeTypes.INHERITS_FROM))
            .collect { case x: TypeDecl =>
              x.fullName
            }
            .toSet
            .toSet
        // The second check makes sure that set is changing which wouldn't be the case in circular hierarchies
        val totalSubclasses: Set[String] = if (directSubclasses.isEmpty) {
          directSubclasses ++ Set(typDeclFullName)
        } else {
          directSubclasses.flatMap(t => allSubclasses(t)) ++ Set(typDeclFullName)
        }
        subclassCache.put(typDeclFullName, totalSubclasses)
        totalSubclasses
    }
  }

  /** Returns the method from a sub-class implementing a method for the given subclass.
   */
  private def staticLookup(subclass: String, method: Method): Option[String] = {
    cpg.typeDecl.fullNameExact(subclass).headOption match {
      case Some(sc) =>
        sc._methodViaAstOut
          .nameExact(method.name)
          .and(_.signatureExact(method.signature))
          .map(_.fullName)
          .headOption
      case None => None
    }
  }

  private def linkDynamicCall(call: Call, dstGraph: DiffGraph.Builder): Unit = {
    validM.get(call.methodFullName) match {
      case Some(value) =>
        value.foreach { destMethod =>
          dstGraph.addEdgeInOriginal(
            call,
            cpg.method.fullNameExact(destMethod).head,
            EdgeTypes.CALL
          )
        }
      case None =>
        logger.info(
          s"Unable to link dynamic CALL with METHOD_FULL_NAME ${call.methodFullName}, NAME ${call.name}, " +
            s"SIGNATURE ${call.signature}, CODE ${call.code}"
        )
    }
  }
}

object DynamicCallLinker {
  private val logger: Logger = LoggerFactory.getLogger(classOf[StaticCallLinker])
}

