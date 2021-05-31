package overflowdb.schema

import overflowdb.codegen.DefaultNodeTypes
import overflowdb.codegen.Helpers._
import overflowdb.storage.ValueTypes

import scala.collection.mutable

class SchemaBuilder(basePackage: String) {
  val properties = mutable.ListBuffer.empty[Property]
  val nodeBaseTypes = mutable.ListBuffer.empty[NodeBaseType]
  val nodeTypes = mutable.ListBuffer.empty[NodeType]
  val edgeTypes = mutable.ListBuffer.empty[EdgeType]
  val constantsByCategory = mutable.Map.empty[String, Seq[Constant]]
  var protoOptions: Option[ProtoOptions] = None

  /** root node trait for all nodes - use if you want to be explicitly unspecific
   * TODO handle differently - it's a cpg-specific special type at the moment, which isn't nice.
   * it's not even part of the regular base types, but instead defined in the RootTypes.scala
   * */
  lazy val anyNode: NodeBaseType =
    new NodeBaseType(
      DefaultNodeTypes.AbstractNodeName,
      Some("generic node base trait - use if you want to be explicitly unspecific"),
      SchemaInfo.forClass(getClass)
    )

  def addProperty(name: String, valueType: ValueTypes, cardinality: Cardinality, comment: String = "")(
    implicit schemaInfo: SchemaInfo = SchemaInfo.Unknown): Property =
    addAndReturn(properties, new Property(name, stringToOption(comment), valueType, cardinality, schemaInfo))

  def addNodeBaseType(name: String, comment: String = "")(
    implicit schemaInfo: SchemaInfo = SchemaInfo.Unknown): NodeBaseType =
    addAndReturn(nodeBaseTypes, new NodeBaseType(name, stringToOption(comment), schemaInfo))

  def addEdgeType(name: String, comment: String = "")(
    implicit schemaInfo: SchemaInfo = SchemaInfo.Unknown): EdgeType =
    addAndReturn(edgeTypes, new EdgeType(name, stringToOption(comment), schemaInfo))

  def addNodeType(name: String, comment: String = "")(
    implicit schemaInfo: SchemaInfo = SchemaInfo.Unknown): NodeType =
    addAndReturn(nodeTypes, new NodeType(name, stringToOption(comment), schemaInfo))

  def addConstants(category: String, constants: Constant*): Seq[Constant] = {
    val previousEntries = constantsByCategory.getOrElse(category, Seq.empty)
    constantsByCategory.put(category, previousEntries ++ constants)
    constants
  }

  def protoOptions(value: ProtoOptions): SchemaBuilder = {
    this.protoOptions = Option(value)
    this
  }

  def build: Schema = {
    val schema = new Schema(
      basePackage,
      properties.sortBy(_.name.toLowerCase).toSeq,
      nodeBaseTypes.sortBy(_.name.toLowerCase).toSeq,
      nodeTypes.sortBy(_.name.toLowerCase).toSeq,
      edgeTypes.sortBy(_.name.toLowerCase).toSeq,
      constantsByCategory.toMap,
      protoOptions,
    )
    verifyProtoIdsUnique(schema)
    schema
  }

  /** proto ids must be unique (if used) */
  def verifyProtoIdsUnique(schema: Schema): Unit = {
    def ensureNoDuplicateProtoIds(categoryName: String, elements: Iterable[HasOptionalProtoId]): Unit = {
      val elementsWithProtoId = elements.filter(_.protoId.isDefined)
      val duplicates = elementsWithProtoId.groupBy(_.protoId.get).filter(_._2.size > 1)
      if (duplicates.nonEmpty) {
        throw new AssertionError(
          s"proto ids must be unique across all schema elements, however we found the following " +
            s"duplicates in $categoryName:\n" +
            duplicates.map { case (protoId , elements) => s"$protoId -> ${elements.mkString(",")}"}.mkString
        )
      }
    }

    ensureNoDuplicateProtoIds("node properties", schema.nodeProperties)
    ensureNoDuplicateProtoIds("edge properties", schema.edgeProperties)
    ensureNoDuplicateProtoIds("node types", schema.nodeTypes)
    ensureNoDuplicateProtoIds("edge types", schema.edgeTypes)
    schema.constantsByCategory.foreach { case (categoryName, constants) =>
      ensureNoDuplicateProtoIds(s"constants $categoryName", constants)
    }
  }

  private def addAndReturn[A](buffer: mutable.Buffer[A], a: A): A = {
    buffer.append(a)
    a
  }
}
