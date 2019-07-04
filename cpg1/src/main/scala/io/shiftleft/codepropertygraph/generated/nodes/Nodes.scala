      package io.shiftleft.codepropertygraph.generated.nodes

      import gremlin.scala._
      import io.shiftleft.codepropertygraph.generated.EdgeKeys
      import java.lang.{Boolean => JBoolean, Long => JLong}
      import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet, TreeMap}
      import org.apache.tinkerpop.gremlin.structure.{Vertex, VertexProperty}
      import org.apache.tinkerpop.gremlin.tinkergraph.structure.{SpecializedElementFactory, SpecializedTinkerVertex, TinkerGraph, TinkerVertexProperty, VertexRef}
      import org.apache.tinkerpop.gremlin.util.iterator.IteratorUtils
      import scala.collection.JavaConverters._
        


      object Block {
        implicit val marshaller: Marshallable[BlockDb] = new Marshallable[BlockDb] {
          override def fromCC(cc: BlockDb): FromCC = ???
          override def toCC(element: Element): BlockDb = element.asInstanceOf[BlockDb]
        }
        val Label = "BLOCK"
        object Keys {
          val Code = "CODE" 
val LineNumber = "LINE_NUMBER" 
        /* XXX0 new part start */
          object Indices {
            val Code = 0
            val LineNumber = 1
          }
        /* XXX0 new part end */
          val All: JSet[String] = Set(Code, LineNumber).asJava
          val KeyToValue: Map[String, BlockDb => Any] = Map(
             "CODE" -> { instance: BlockDb => instance.code},
 "LINE_NUMBER" -> { instance: BlockDb => instance.lineNumber.orNull},
          )
        }
        object Edges {
          val In: Set[String] = Set("CALL_ARG_OUT","RECEIVER","CDG","REACHING_DEF","CONTAINS_NODE","CONTAINS","CFG","AST","DOMINATE","POST_DOMINATE")
          val Out: Set[String] = Set("AST","CFG","CALL_ARG","EVAL_TYPE","REACHING_DEF","CONTAINS_NODE")
        }

        val Factory = new SpecializedElementFactory.ForVertex[BlockDb] {
          override val forLabel = Block.Label

          override def createVertex(id: JLong, graph: TinkerGraph) = new BlockDb(id, graph)
          override def createVertexRef(vertex: BlockDb) = Block(vertex)
          override def createVertexRef(id: JLong, graph: TinkerGraph) = Block(id, graph)

          /* XXX0 new part start*/
          override def propertyNamesByIndex() = {
            val ret = new JHashMap[Integer, String]
            ret.put(Block.Keys.Indices.Code, Block.Keys.Code)
            ret.put(Block.Keys.Indices.LineNumber, Block.Keys.LineNumber)
            ret
          }
          override def propertyTypeByIndex() = {
            val ret = new JHashMap[Integer, Class[_]]
            ret.put(Block.Keys.Indices.Code, classOf[String])
            ret.put(Block.Keys.Indices.LineNumber, classOf[Integer])
            ret
          }
          /* XXX0 new part end*/
        }

        def apply(wrapped: BlockDb) = new VertexRef(wrapped) with Block
        def apply(id: Long, graph: TinkerGraph) = 
          new VertexRef[BlockDb](id, Block.Label, graph) with Block
      }
      
trait Block extends VertexRef[BlockDb] with BlockBase with StoredNode with Expression {
override def code = get().code
override def lineNumber = get().lineNumber


  // don't worry about
  def columnNumber: Option[Integer] = ???
   def columnNumberEnd: Option[Integer] = ???
   def lineNumberEnd: Option[Integer] = ??? 
   def order: Integer = ??? 

  override def accept[T](visitor: NodeVisitor[T]): T = {
    visitor.visit(this)
  }
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def productElement(n: Int): Any = get.productElement(n)
  override def productPrefix = "Block"
  override def productArity = 2 + 1 // add one for id, leaving out `_graph`
  override def canEqual(that: Any): Boolean = get.canEqual(that)
}
      trait BlockBase extends Node with ExpressionBase with HasCode with HasLineNumber {
        def asStored : StoredNode = this.asInstanceOf[StoredNode]
      }

      class BlockDb(private val _id: JLong, private val _graph: TinkerGraph)
          extends SpecializedTinkerVertex(_id, Block.Label, _graph) with StoredNode with Expression with BlockBase {

        override def allowedInEdgeLabels() = Block.Edges.In.asJava
        override def allowedOutEdgeLabels() = Block.Edges.Out.asJava
        override def specificKeys() = Block.Keys.All

        /* all properties */
        override def valueMap: JMap[String, AnyRef] =  {
        /* XXX0 new part start */
          // TODO optimise: load all properties in one go
  val properties = new JHashMap[String, AnyRef]
  properties.put("CODE", code)
lineNumber.map { value => properties.put("LINE_NUMBER", value) }
        /* XXX0 new part end */
  properties
}

        private var _code: String = null
        /* XXX0 new part start */
        def code(): String = {
          if (_code == null)
            _code = graph.readProperty(this, Block.Keys.Indices.Code, classOf[String]).asInstanceOf[String]
          validateMandatoryProperty(Block.Keys.Code, _code)
          _code
        }
        /* XXX0 new part end */

        /* XXX0 new part start */
private var _lineNumber: Option[Integer] = None
        def lineNumber(): Option[Integer] = {
          if (_lineNumber == null)
            _lineNumber = Option(graph.readProperty(this, Block.Keys.Indices.LineNumber, classOf[Integer]).asInstanceOf[Integer])
          _lineNumber
        }
        /* XXX0 new part end */

        /* XXX0 new part start */
        override def propertiesByStorageIdx = {
          val properties = new TreeMap[Integer, Object]
          // TODO optimise: load all properties in one go
          properties.put(Block.Keys.Indices.Code, code);
          lineNumber.map { value => properties.put(Block.Keys.Indices.LineNumber, value) }
          properties
        }
        /* XXX0 new part end */


        override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[BlockDb]
        override def productElement(n: Int): Any =
            n match {
              case 0 => _id
              case 1 => code()
case 2 => lineNumber()
            }

        override def productPrefix = "Block"
        override def productArity = 2 + 1 // add one for id, leaving out `_graph`
        
        /* performance optimisation to save instantiating an iterator for each property lookup */
        override protected def specificProperty[A](key: String): VertexProperty[A] = {
          Block.Keys.KeyToValue.get(key) match {
            case None => VertexProperty.empty[A]
            case Some(fieldAccess) => 
              fieldAccess(this) match {
                case null | None => VertexProperty.empty[A]
                case values: List[_] => throw Vertex.Exceptions.multiplePropertiesExistForProvidedKey(key)
                case Some(value) => new TinkerVertexProperty(-1, this, key, value.asInstanceOf[A])
                case value => new TinkerVertexProperty(-1, this, key, value.asInstanceOf[A])
              }
          }
        }

        override protected def specificProperties[A](key: String): JIterator[VertexProperty[A]] = {
          Block.Keys.KeyToValue.get(key) match {
            case None => JCollections.emptyIterator[VertexProperty[A]]
            case Some(fieldAccess) => 
              fieldAccess(this) match {
                case null => JCollections.emptyIterator[VertexProperty[A]]
                case values: List[_] => 
                  values.map { value => 
                    new TinkerVertexProperty(-1, this, key, value).asInstanceOf[VertexProperty[A]]
                  }.toIterator.asJava
                case value => IteratorUtils.of(new TinkerVertexProperty(-1, this, key, value.asInstanceOf[A]))
              }
          }
        }

        override protected def updateSpecificProperty[A](cardinality: VertexProperty.Cardinality, key: String, value: A): VertexProperty[A] = {
           if (key == "CODE") this._code = value.asInstanceOf[String] 
 else  if (key == "LINE_NUMBER") this._lineNumber = Option(value).asInstanceOf[Option[Integer]] 
 else throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
          new TinkerVertexProperty(-1, this, key, value)
        }

        override protected def removeSpecificProperty(key: String): Unit =
           if (key == "CODE") this._code = null 
 else  if (key == "LINE_NUMBER") this._lineNumber = null 
 else throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")

        // don't worry about
       def columnNumber: Option[Integer] = ???
   def columnNumberEnd: Option[Integer] = ???
   def lineNumberEnd: Option[Integer] = ??? 
   def order: Integer = ??? 
      }

      trait Node extends Product {
        def accept[T](visitor: NodeVisitor[T]): T = ???
      }

      /* making use of the fact that SpecializedVertex is also our domain node */
      trait StoredNode extends Vertex with Node {
        /* underlying vertex in the graph database. 
         * since this is a StoredNode, this is always set */
        def underlying: Vertex = this

        // This is required for accessing the id from java code which only has a reference to StoredNode at hand.
        // Java does not seem to be capable of calling methods from java classes if a scala trait is in the inheritance
        // chain.
        def getId: JLong = underlying.id.asInstanceOf[JLong]

        /* all properties plus label and id */
        def toMap: Map[String, Any] = {
          val map = valueMap
          map.put("_label", label)
          map.put("_id", getId)
          map.asScala.toMap
        }

        /* all properties */
        def valueMap: JMap[String, AnyRef]
      }

      trait NodeVisitor[T] {
//           def visit(node: Annotation): T = ???
// def visit(node: AnnotationLiteral): T = ???
// def visit(node: AnnotationParameter): T = ???
// def visit(node: AnnotationParameterAssign): T = ???
// def visit(node: ArrayInitializer): T = ???
def visit(node: Block): T = ???
// def visit(node: Call): T = ???
// def visit(node: CallChain): T = ???
// def visit(node: CallSite): T = ???
// def visit(node: ClosureBinding): T = ???
// def visit(node: Comment): T = ???
// def visit(node: ConfigFile): T = ???
// def visit(node: Dependency): T = ???
// def visit(node: DetachedTrackingPoint): T = ???
// def visit(node: File): T = ???
// def visit(node: Finding): T = ???
// def visit(node: Flow): T = ???
// def visit(node: Framework): T = ???
// def visit(node: FrameworkData): T = ???
// def visit(node: Identifier): T = ???
// def visit(node: Ioflow): T = ???
// def visit(node: Literal): T = ???
// def visit(node: Local): T = ???
// def visit(node: Location): T = ???
// def visit(node: MatchInfo): T = ???
// def visit(node: Member): T = ???
// def visit(node: MetaData): T = ???
// def visit(node: Method): T = ???
// def visit(node: MethodInst): T = ???
// def visit(node: MethodParameterIn): T = ???
// def visit(node: MethodParameterOut): T = ???
// def visit(node: MethodRef): T = ???
// def visit(node: MethodReturn): T = ???
// def visit(node: MethodSummary): T = ???
// def visit(node: Modifier): T = ???
// def visit(node: Namespace): T = ???
// def visit(node: NamespaceBlock): T = ???
// def visit(node: ProgramPoint): T = ???
// def visit(node: Read): T = ???
// def visit(node: Return): T = ???
// def visit(node: Route): T = ???
// def visit(node: SensitiveDataType): T = ???
// def visit(node: SensitiveMember): T = ???
// def visit(node: SensitiveReference): T = ???
// def visit(node: SensitiveVariable): T = ???
// def visit(node: Sink): T = ???
// def visit(node: Source): T = ???
// def visit(node: SpAnnotationParameter): T = ???
// def visit(node: SpBlacklist): T = ???
// def visit(node: Tag): T = ???
// def visit(node: Tags): T = ???
// def visit(node: TagNodePair): T = ???
// def visit(node: Transform): T = ???
// def visit(node: Transformation): T = ???
// def visit(node: Type): T = ???
// def visit(node: TypeArgument): T = ???
// def visit(node: TypeDecl): T = ???
// def visit(node: TypeParameter): T = ???
// def visit(node: Unknown): T = ???
// def visit(node: VariableInfo): T = ???
// def visit(node: Write): T = ???
// def visit(node: PackagePrefix): T = ???
//           def visit(node: Declaration): T = ???
// def visit(node: Expression): T = ???
// def visit(node: LocalLike): T = ???
// def visit(node: CfgNode): T = ???
// def visit(node: TrackingPoint): T = ???
// def visit(node: WithinMethod): T = ???
        }
trait DeclarationBase extends Node with HasName 
                  trait Declaration extends StoredNode with DeclarationBase 
              
trait ExpressionBase extends Node with HasCode with HasOrder with TrackingPointBase with CfgNodeBase
                  trait Expression extends StoredNode with ExpressionBase with TrackingPoint with CfgNode
              
trait LocalLikeBase extends Node with HasName 
                  trait LocalLike extends StoredNode with LocalLikeBase 
              
trait CfgNodeBase extends Node with HasLineNumber with HasLineNumberEnd with HasColumnNumber with HasColumnNumberEnd with WithinMethodBase
                  trait CfgNode extends StoredNode with CfgNodeBase with WithinMethod
              
trait TrackingPointBase extends Node  with WithinMethodBase
                  trait TrackingPoint extends StoredNode with TrackingPointBase with WithinMethod
              
trait WithinMethodBase extends Node  
                  trait WithinMethod extends StoredNode with WithinMethodBase 
              trait HasAliasTypeFullName { def aliasTypeFullName: Option[String] }
trait HasAnnotationFullName { def annotationFullName: String }
trait HasAnnotationName { def annotationName: String }
trait HasArgumentIndex { def argumentIndex: Integer }
trait HasAstParentFullName { def astParentFullName: String }
trait HasAstParentType { def astParentType: String }
trait HasBinarySignature { def binarySignature: Option[String] }
trait HasCategory { def category: String }
trait HasClassName { def className: String }
trait HasClosureBindingId { def closureBindingId: Option[String] }
trait HasClosureOriginalName { def closureOriginalName: Option[String] }
trait HasCode { def code: String }
trait HasColumnNumber { def columnNumber: Option[Integer] }
trait HasColumnNumberEnd { def columnNumberEnd: Option[Integer] }
trait HasContent { def content: String }
trait HasDependencyGroupId { def dependencyGroupId: Option[String] }
trait HasDepthFirstOrder { def depthFirstOrder: Option[Integer] }
trait HasDescription { def description: String }
trait HasDispatchType { def dispatchType: String }
trait HasEvaluationStrategy { def evaluationStrategy: String }
trait HasEvaluationType { def evaluationType: String }
trait HasFilename { def filename: String }
trait HasFingerprint { def fingerprint: String }
trait HasFullName { def fullName: String }
trait HasHasMapping { def hasMapping: Option[JBoolean] }
trait HasInheritsFromTypeFullName { def inheritsFromTypeFullName: List[String] }
trait HasIsExternal { def isExternal: JBoolean }
trait HasIsStatic { def isStatic: JBoolean }
trait HasLanguage { def language: String }
trait HasLineNo { def lineNo: String }
trait HasLineNumber { def lineNumber: Option[Integer] }
trait HasLineNumberEnd { def lineNumberEnd: Option[Integer] }
trait HasLink { def link: String }
trait HasLiteralsToSink { def literalsToSink: List[String] }
trait HasMethodFullName { def methodFullName: String }
trait HasMethodInstFullName { def methodInstFullName: String }
trait HasMethodName { def methodName: String }
trait HasMethodShortName { def methodShortName: String }
trait HasModifierType { def modifierType: String }
trait HasName { def name: String }
trait HasNodeId { def nodeId: String }
trait HasNodeLabel { def nodeLabel: String }
trait HasOrder { def order: Integer }
trait HasPackageName { def packageName: String }
trait HasParameter { def parameter: String }
trait HasParameterIndex { def parameterIndex: Option[Integer] }
trait HasParserTypeName { def parserTypeName: String }
trait HasPath { def path: String }
trait HasPattern { def pattern: String }
trait HasResolved { def resolved: Option[JBoolean] }
trait HasSccId { def sccId: Integer }
trait HasScore { def score: Integer }
trait HasSignature { def signature: String }
trait HasSinkType { def sinkType: String }
trait HasSourceType { def sourceType: String }
trait HasSpid { def spid: Option[String] }
trait HasSymbol { def symbol: String }
trait HasTitle { def title: String }
trait HasTypeDeclFullName { def typeDeclFullName: String }
trait HasTypeFullName { def typeFullName: String }
trait HasValue { def value: String }
trait HasVarType { def varType: String }
trait HasVersion { def version: String }
trait HasVulnDescr { def vulnDescr: String }

//         object Factories {
//           lazy val All: List[SpecializedElementFactory.ForVertex[_]] = List(Annotation.Factory, AnnotationLiteral.Factory, AnnotationParameter.Factory, AnnotationParameterAssign.Factory, ArrayInitializer.Factory, Block.Factory, Call.Factory, CallChain.Factory, CallSite.Factory, ClosureBinding.Factory, Comment.Factory, ConfigFile.Factory, Dependency.Factory, DetachedTrackingPoint.Factory, File.Factory, Finding.Factory, Flow.Factory, Framework.Factory, FrameworkData.Factory, Identifier.Factory, Ioflow.Factory, Literal.Factory, Local.Factory, Location.Factory, MatchInfo.Factory, Member.Factory, MetaData.Factory, Method.Factory, MethodInst.Factory, MethodParameterIn.Factory, MethodParameterOut.Factory, MethodRef.Factory, MethodReturn.Factory, MethodSummary.Factory, Modifier.Factory, Namespace.Factory, NamespaceBlock.Factory, ProgramPoint.Factory, Read.Factory, Return.Factory, Route.Factory, SensitiveDataType.Factory, SensitiveMember.Factory, SensitiveReference.Factory, SensitiveVariable.Factory, Sink.Factory, Source.Factory, SpAnnotationParameter.Factory, SpBlacklist.Factory, Tag.Factory, Tags.Factory, TagNodePair.Factory, Transform.Factory, Transformation.Factory, Type.Factory, TypeArgument.Factory, TypeDecl.Factory, TypeParameter.Factory, Unknown.Factory, VariableInfo.Factory, Write.Factory, PackagePrefix.Factory)
//           lazy val AllAsJava: java.util.List[SpecializedElementFactory.ForVertex[_]] = All.asJava
//         }
