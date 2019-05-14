package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated
import java.lang.{Boolean => JBoolean, Long => JLong}
import java.util.{ArrayList => JArrayList, Collections => JCollections, Iterator => JIterator, HashMap => JHashMap, Map => JMap, Set => JSet}
import gremlin.scala._
import gnu.trove.iterator.TLongIterator
import org.apache.tinkerpop.gremlin.tinkergraph.storage.iterator.TLongMultiIterator
import gnu.trove.set.TLongSet
import gnu.trove.set.hash.TLongHashSet
import org.apache.tinkerpop.gremlin.structure.{Direction, Edge, Vertex, VertexProperty}
import org.apache.tinkerpop.gremlin.tinkergraph.structure.{SpecializedElementFactory, SpecializedTinkerVertex, TinkerGraph, TinkerVertexProperty}
import org.apache.tinkerpop.gremlin.util.iterator.{IteratorUtils, MultiIterator}
import scala.collection.JavaConverters._
import shapeless.HNil

trait Node extends gremlin.scala.dsl.DomainRoot {
  def accept[T](visitor: NodeVisitor[T]): T
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

        /* returns Map of all properties plus label and id */
        def toMap: Map[String, Any]
      }
      trait NodeVisitor[T] {
//           def visit(node: ArrayInitializer): T = ???
// def visit(node: Block): T = ???
// def visit(node: Call): T = ???
// def visit(node: ClosureBinding): T = ???
// def visit(node: File): T = ???
// def visit(node: Identifier): T = ???
// def visit(node: Literal): T = ???
// def visit(node: Local): T = ???
// def visit(node: Member): T = ???
// def visit(node: MetaData): T = ???
def visit(node: Method): T = ???
// def visit(node: MethodInst): T = ???
// def visit(node: MethodParameterIn): T = ???
// def visit(node: MethodParameterOut): T = ???
// def visit(node: MethodRef): T = ???
// def visit(node: MethodReturn): T = ???
// def visit(node: Modifier): T = ???
// def visit(node: Namespace): T = ???
// def visit(node: NamespaceBlock): T = ???
// def visit(node: Return): T = ???
// def visit(node: Tag): T = ???
// def visit(node: Type): T = ???
// def visit(node: TypeArgument): T = ???
// def visit(node: TypeDecl): T = ???
// def visit(node: TypeParameter): T = ???
// def visit(node: Unknown): T = ???
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
trait CfgNodeBase extends Node with HasLineNumber with HasLineNumberEnd with HasColumnNumber with HasColumnNumberEnd with WithinMethodBase
trait WithinMethodBase extends Node  
              
trait HasAstParentFullName { def astParentFullName: String }
trait HasAstParentType { def astParentType: String }
trait HasClosureBindingId { def closureBindingId: Option[String] }
trait HasCode { def code: String }
trait HasColumnNumber { def columnNumber: Option[Integer] }
trait HasColumnNumberEnd { def columnNumberEnd: Option[Integer] }
trait HasDispatchType { def dispatchType: String }
trait HasEvaluationStrategy { def evaluationStrategy: String }
trait HasFullName { def fullName: String }
trait HasInheritsFromTypeFullName { def inheritsFromTypeFullName: List[String] }
trait HasIsExternal { def isExternal: JBoolean }
trait HasLanguage { def language: String }
trait HasLineNumber { def lineNumber: Option[Integer] }
trait HasLineNumberEnd { def lineNumberEnd: Option[Integer] }
trait HasMethodFullName { def methodFullName: String }
trait HasMethodInstFullName { def methodInstFullName: String }
trait HasModifierType { def modifierType: String }
trait HasName { def name: String }
trait HasOrder { def order: Integer }
trait HasParserTypeName { def parserTypeName: String }
trait HasSignature { def signature: String }
trait HasTypeDeclFullName { def typeDeclFullName: String }
trait HasTypeFullName { def typeFullName: String }
trait HasValue { def value: String }
trait HasVersion { def version: String }


trait MethodBase extends Node with DeclarationBase with CfgNodeBase {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]
  def name: String
  def fullName: String
  def signature: String
  def astParentType: String
  def astParentFullName: String
  def lineNumber: Option[Integer]
  def lineNumberEnd: Option[Integer]
  def columnNumber: Option[Integer]
  def columnNumberEnd: Option[Integer]
}

// new code for DomainCodeGenerator
import org.apache.tinkerpop.gremlin.tinkergraph.structure.VertexRef
/** important: do not used `wrapped` internally in this class, only pass it to VertexRef constructor
  * this is to ensure it can be managed by the ReferenceManager */
class MethodRef(wrapped: Method) extends VertexRef[Method](wrapped) with MethodBase {
  override def astParentFullName: String = get.astParentFullName
  override def astParentType: String = get.astParentType
  override def columnNumber: Option[Integer] = get.columnNumber
  override def columnNumberEnd: Option[Integer] = get.columnNumberEnd
  override def fullName: String = get.fullName
  override def lineNumber: Option[Integer] = get.lineNumber
  override def lineNumberEnd: Option[Integer] = get.lineNumberEnd
  override def name: String = get.name
  override def signature: String = get.signature

  override def accept[T](visitor: NodeVisitor[T]): T = get.accept(visitor)
  override val productArity = get.productArity
  override def productElement(n: Int): Any = wrapped.productElement(n)
  override def canEqual(that: Any): Boolean = get.canEqual(that)
}


      object Method {
        implicit val marshaller: Marshallable[Method] = new Marshallable[Method] {
          override def fromCC(cc: Method): FromCC = ???
          override def toCC(element: Element): Method = element.asInstanceOf[Method]
        }
        val Label = "METHOD"
        object Keys {
          val Name = "NAME" 
val FullName = "FULL_NAME" 
val Signature = "SIGNATURE" 
val AstParentType = "AST_PARENT_TYPE" 
val AstParentFullName = "AST_PARENT_FULL_NAME" 
val LineNumber = "LINE_NUMBER" 
val LineNumberEnd = "LINE_NUMBER_END" 
val ColumnNumber = "COLUMN_NUMBER" 
val ColumnNumberEnd = "COLUMN_NUMBER_END" 
          val All: JSet[String] = Set(Name, FullName, Signature, AstParentType, AstParentFullName, LineNumber, LineNumberEnd, ColumnNumber, ColumnNumberEnd).asJava
          val KeyToValue: Map[String, Method => Any] = Map(
             "NAME" -> { instance: Method => instance.name},
 "FULL_NAME" -> { instance: Method => instance.fullName},
 "SIGNATURE" -> { instance: Method => instance.signature},
 "AST_PARENT_TYPE" -> { instance: Method => instance.astParentType},
 "AST_PARENT_FULL_NAME" -> { instance: Method => instance.astParentFullName},
 "LINE_NUMBER" -> { instance: Method => instance.lineNumber.orNull},
 "LINE_NUMBER_END" -> { instance: Method => instance.lineNumberEnd.orNull},
 "COLUMN_NUMBER" -> { instance: Method => instance.columnNumber.orNull},
 "COLUMN_NUMBER_END" -> { instance: Method => instance.columnNumberEnd.orNull}
          )
        }
        object Edges {
          val In: Set[String] = Set("REF","CONTAINS_NODE","CONTAINS","VTABLE","AST")
          val Out: Set[String] = Set("AST","CFG","REACHING_DEF","CONTAINS","CONTAINS_NODE")
        }

        val Factory = new SpecializedElementFactory.ForVertex[Method] {
          override val forLabel = Method.Label

          override def createVertex(id: JLong, graph: TinkerGraph) = new Method(id, graph)
          override def createVertexRef(id: JLong, graph: TinkerGraph) = new MethodRef(createVertex(id, graph))
        }
      }

      class Method(private val _id: JLong, private val _graph: TinkerGraph)
          extends SpecializedTinkerVertex(_id, Method.Label, _graph) with StoredNode with Declaration /*with CfgNode*/ with HasName with HasFullName with HasSignature with HasAstParentType with HasAstParentFullName with HasLineNumber with HasLineNumberEnd with HasColumnNumber with HasColumnNumberEnd with Product with MethodBase {

// new code for DomainCodeGenerator
        override def allowedInEdgeLabels() = Method.Edges.In.asJava
        override def allowedOutEdgeLabels() = Method.Edges.Out.asJava
        override def specificKeys() = Method.Keys.All

        override def toMap: Map[String, Any] = 
        Map("_label" -> "METHOD",
          "_id" -> (_id: Long),
          ("NAME" -> _name ),
("FULL_NAME" -> _fullName ),
("SIGNATURE" -> _signature ),
("AST_PARENT_TYPE" -> _astParentType ),
("AST_PARENT_FULL_NAME" -> _astParentFullName ),
("LINE_NUMBER" -> lineNumber ),
("LINE_NUMBER_END" -> lineNumberEnd ),
("COLUMN_NUMBER" -> columnNumber ),
("COLUMN_NUMBER_END" -> columnNumberEnd )
        ).filterNot { case (k,v) =>
            v == null || v == None
          }
         .map {
            case (k, Some(v)) => (k,v)
            case other => other
          }

        override def accept[T](visitor: NodeVisitor[T]): T = {
          visitor.visit(this)
        }

        private var _name: String = null
def name(): String = _name

private var _fullName: String = null
def fullName(): String = _fullName

private var _signature: String = null
def signature(): String = _signature

private var _astParentType: String = null
def astParentType(): String = _astParentType

private var _astParentFullName: String = null
def astParentFullName(): String = _astParentFullName

private var _lineNumber: Option[Integer] = None
def lineNumber(): Option[Integer] = _lineNumber

private var _lineNumberEnd: Option[Integer] = None
def lineNumberEnd(): Option[Integer] = _lineNumberEnd

private var _columnNumber: Option[Integer] = None
def columnNumber(): Option[Integer] = _columnNumber

private var _columnNumberEnd: Option[Integer] = None
def columnNumberEnd(): Option[Integer] = _columnNumberEnd

        override val productPrefix = "Method"
        override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[Method]
        override val productArity = 9 + 1 // add one for id, leaving out `_graph`
        override def productElement(n: Int): Any =
            n match {
              case 0 => _id
              case 1 => name()
case 2 => fullName()
case 3 => signature()
case 4 => astParentType()
case 5 => astParentFullName()
case 6 => lineNumber()
case 7 => lineNumberEnd()
case 8 => columnNumber()
case 9 => columnNumberEnd()
            }
        
// new code for DomainCodeGenerator: used to be LongSets
        // TODO use edgeRef once we have it for AST
//           private var _astOut: TLongSet = null
//           def astOut: TLongSet = {
//             if (_astOut == null) _astOut = new TLongHashSet(10)
//             _astOut
//           }

        /* performance optimisation to save instantiating an iterator for each property lookup */
        override protected def specificProperty[A](key: String): VertexProperty[A] = {
          Method.Keys.KeyToValue.get(key) match {
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
          Method.Keys.KeyToValue.get(key) match {
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
           if (key == "NAME") this._name = value.asInstanceOf[String] 
 else  if (key == "FULL_NAME") this._fullName = value.asInstanceOf[String] 
 else  if (key == "SIGNATURE") this._signature = value.asInstanceOf[String] 
 else  if (key == "AST_PARENT_TYPE") this._astParentType = value.asInstanceOf[String] 
 else  if (key == "AST_PARENT_FULL_NAME") this._astParentFullName = value.asInstanceOf[String] 
 else  if (key == "LINE_NUMBER") this._lineNumber = Option(value).asInstanceOf[Option[Integer]] 
 else  if (key == "LINE_NUMBER_END") this._lineNumberEnd = Option(value).asInstanceOf[Option[Integer]] 
 else  if (key == "COLUMN_NUMBER") this._columnNumber = Option(value).asInstanceOf[Option[Integer]] 
 else  if (key == "COLUMN_NUMBER_END") this._columnNumberEnd = Option(value).asInstanceOf[Option[Integer]] 
 else throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
          new TinkerVertexProperty(-1, this, key, value)
        }

        override protected def removeSpecificProperty(key: String): Unit =
           if (key == "NAME") this._name = null 
 else  if (key == "FULL_NAME") this._fullName = null 
 else  if (key == "SIGNATURE") this._signature = null 
 else  if (key == "AST_PARENT_TYPE") this._astParentType = null 
 else  if (key == "AST_PARENT_FULL_NAME") this._astParentFullName = null 
 else  if (key == "LINE_NUMBER") this._lineNumber = null 
 else  if (key == "LINE_NUMBER_END") this._lineNumberEnd = null 
 else  if (key == "COLUMN_NUMBER") this._columnNumber = null 
 else  if (key == "COLUMN_NUMBER_END") this._columnNumberEnd = null 
 else throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")

//         override protected def addSpecializedInEdge(edgeLabel: String, edgeId: Long): Unit =
//           edgeLabel match {
//             case io.shiftleft.codepropertygraph.generated.edges.Ref.Label => refIn.add(edgeId)
// case io.shiftleft.codepropertygraph.generated.edges.ContainsNode.Label => containsNodeIn.add(edgeId)
// case io.shiftleft.codepropertygraph.generated.edges.Contains.Label => containsIn.add(edgeId)
// case io.shiftleft.codepropertygraph.generated.edges.Vtable.Label => vtableIn.add(edgeId)
// case io.shiftleft.codepropertygraph.generated.edges.Ast.Label => astIn.add(edgeId)
//             case otherwise => throw new IllegalArgumentException("incoming edge of type " + edgeLabel + " not (yet) supported by Method. You may want to add it to cpg.json")
//           }

//         override protected def addSpecializedOutEdge(edgeLabel: String, edgeId: Long): Unit =
//           edgeLabel match {
//             case io.shiftleft.codepropertygraph.generated.edges.Ast.Label => astOut.add(edgeId)
// case io.shiftleft.codepropertygraph.generated.edges.Cfg.Label => cfgOut.add(edgeId)
// case io.shiftleft.codepropertygraph.generated.edges.ReachingDef.Label => reachingDefOut.add(edgeId)
// case io.shiftleft.codepropertygraph.generated.edges.Contains.Label => containsOut.add(edgeId)
// case io.shiftleft.codepropertygraph.generated.edges.ContainsNode.Label => containsNodeOut.add(edgeId)
//             case otherwise => throw new IllegalArgumentException("outgoing edge of type " + edgeLabel + " not supported by Method. You may want to add it to cpg.json")
//           }

//         override protected def specificEdges(direction: Direction, labels: String*): TLongIterator = {
//           val walkLabels = 
//             if (labels.length > 0) labels
//             else {
//               // if no labels are specified, walk all
//               direction match {
//                 case Direction.IN => Method.Edges.In
//                 case Direction.OUT => Method.Edges.Out
//                 case Direction.BOTH => Method.Edges.In ++ Method.Edges.Out
//               }
//             }

//           val walkIterators = new JArrayList[TLongIterator](20)
//           if (direction == Direction.IN || direction == Direction.BOTH) {
//             walkLabels.collect {
//               case io.shiftleft.codepropertygraph.generated.edges.Ref.Label => walkIterators.add(refIn.iterator)
// case io.shiftleft.codepropertygraph.generated.edges.ContainsNode.Label => walkIterators.add(containsNodeIn.iterator)
// case io.shiftleft.codepropertygraph.generated.edges.Contains.Label => walkIterators.add(containsIn.iterator)
// case io.shiftleft.codepropertygraph.generated.edges.Vtable.Label => walkIterators.add(vtableIn.iterator)
// case io.shiftleft.codepropertygraph.generated.edges.Ast.Label => walkIterators.add(astIn.iterator)
//               case _ => // ignore other labels
//             }
//           }
//           if (direction == Direction.OUT || direction == Direction.BOTH) {
//             walkLabels.collect {
//               case io.shiftleft.codepropertygraph.generated.edges.Ast.Label => walkIterators.add(astOut.iterator)
// case io.shiftleft.codepropertygraph.generated.edges.Cfg.Label => walkIterators.add(cfgOut.iterator)
// case io.shiftleft.codepropertygraph.generated.edges.ReachingDef.Label => walkIterators.add(reachingDefOut.iterator)
// case io.shiftleft.codepropertygraph.generated.edges.Contains.Label => walkIterators.add(containsOut.iterator)
// case io.shiftleft.codepropertygraph.generated.edges.ContainsNode.Label => walkIterators.add(containsNodeOut.iterator)
//               case _ => // ignore other labels
//             }
//           }
//           new TLongMultiIterator(walkIterators)
//         }

//         override protected def removeSpecificInEdge(edgeId: JLong): Unit = {
//           refIn.remove(edgeId)
// containsNodeIn.remove(edgeId)
// containsIn.remove(edgeId)
// vtableIn.remove(edgeId)
// astIn.remove(edgeId)
//         }

//         override protected def removeSpecificOutEdge(edgeId: JLong): Unit = {
//           astOut.remove(edgeId)
// cfgOut.remove(edgeId)
// reachingDefOut.remove(edgeId)
// containsOut.remove(edgeId)
// containsNodeOut.remove(edgeId)
//         }

        
      }
