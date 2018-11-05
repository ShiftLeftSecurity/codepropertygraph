      package io.shiftleft.codepropertygraph.generated.nodes

      import io.shiftleft.codepropertygraph.generated
      import java.lang.{Boolean => JBoolean, Long => JLong}
      import java.util.{Collections => JCollections, Iterator => JIterator, LinkedList => JLinkedList, List => JList, Map => JMap, Set => JSet}
      import gremlin.scala._
      import org.apache.tinkerpop.gremlin.structure.{Direction, Edge, Vertex, VertexProperty}
      import org.apache.tinkerpop.gremlin.tinkergraph.structure.{SpecializedElementFactory, SpecializedTinkerVertex, TinkerGraph, TinkerVertexProperty}
      import org.apache.tinkerpop.gremlin.util.iterator.{IteratorUtils, MultiIterator}
      import scala.collection.JavaConverters._
      import shapeless.HNil

        object Factories {
          lazy val All: List[SpecializedElementFactory.ForVertex[_, _]] =
            List(Type2.Factory)
            // List(ArrayInitializer.Factory, Block.Factory, Call.Factory, ClosureBinding.Factory, File.Factory, Identifier.Factory, Literal.Factory, Local.Factory, Member.Factory, MetaData.Factory, Method.Factory, MethodInst.Factory, MethodParameterIn.Factory, MethodParameterOut.Factory, MethodRef.Factory, MethodReturn.Factory, Modifier.Factory, Namespace.Factory, NamespaceBlock.Factory, Return.Factory, Tag.Factory, Type.Factory, TypeArgument.Factory, TypeDecl.Factory, TypeParameter.Factory, Unknown.Factory)
          lazy val AllAsJava: java.util.List[SpecializedElementFactory.ForVertex[_, _]] = All.asJava
        }

      object Type2 {
        implicit val marshaller: Marshallable[Type2] = new Marshallable[Type2] {
          override def fromCC(cc: Type2): FromCC = ???
          override def toCC(element: Element): Type2 = element.asInstanceOf[Type2]
        }

        val Label = "TYPE"
        object Keys {
          val Name = "NAME" 
val FullName = "FULL_NAME" 
val Type2DeclFullName = "TYPE_DECL_FULL_NAME" 
          val All: JSet[String] = Set(Name, FullName, Type2DeclFullName).asJava
          val KeyToValue: Map[String, Type2 => Any] = Map(
             "NAME" -> { instance: Type2 => instance.name},
 "FULL_NAME" -> { instance: Type2 => instance.fullName},
 "TYPE_DECL_FULL_NAME" -> { instance: Type2 => instance.typeDeclFullName}
          )
        }
        object Edges {
          val In: Set[String] = Set("REF","EVAL_TYPE","CONTAINS_NODE","INHERITS_FROM")
          val Out: Set[String] = Set("AST","REF","CONTAINS_NODE")
        }

        val Factory = new SpecializedElementFactory.ForVertex[Type2, JLong] {
          override val forLabel = Type2.Label

          override def createVertex(id: JLong, graph: TinkerGraph) =
            new Type2(id, graph)
        }
      }
      
      trait Type2Base extends Node {
        def asStored : StoredNode = this.asInstanceOf[StoredNode]

        
 def name: String
 def fullName: String
 def typeDeclFullName: String
        
      }

      class Type2(private val _id: JLong, private val _graph: TinkerGraph)
          extends SpecializedTinkerVertex[JLong](_id, Type2.Label, _graph, Type2.Keys.All) with StoredNode  with HasName with HasFullName with HasTypeDeclFullName with Product with Type2Base {

        override val underlying = this

        var _name: String = null
def name: String = _name

var _fullName: String = null
def fullName: String = _fullName

var _typeDeclFullName: String = null
def typeDeclFullName: String = _typeDeclFullName

        override val productPrefix = "Type2"
        override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[Type2]
        override val productArity = 3 + 1 // add one for id, leaving out `_graph`
        override def productElement(n: Int): Any =
            n match {
              case 0 => _id
              case 1 => _name
case 2 => _fullName
case 3 => _typeDeclFullName
            }
        
//         override def toMap: Map[String, Any] = 
//         Map("_label" -> "TYPE",
//           "_id" -> (_id: Long),
//           ("NAME" -> _name ),
// ("FULL_NAME" -> _fullName ),
// ("TYPE_DECL_FULL_NAME" -> _typeDeclFullName )
//         ).filterNot { case (k,v) =>
//             v == null || v == None
//           }
//          .map {
//             case (k, Some(v)) => (k,v)
//             case other => other
//           }
        

        
          private var _refIn: JList[Edge] = null
          private def refIn: JList[Edge] = {
            if (_refIn == null) _refIn = new JLinkedList
            _refIn
          }

          private var _evalType2In: JList[Edge] = null
          private def evalType2In: JList[Edge] = {
            if (_evalType2In == null) _evalType2In = new JLinkedList
            _evalType2In
          }

          private var _containsNodeIn: JList[Edge] = null
          private def containsNodeIn: JList[Edge] = {
            if (_containsNodeIn == null) _containsNodeIn = new JLinkedList
            _containsNodeIn
          }

          private var _inheritsFromIn: JList[Edge] = null
          private def inheritsFromIn: JList[Edge] = {
            if (_inheritsFromIn == null) _inheritsFromIn = new JLinkedList
            _inheritsFromIn
          }

          private var _astOut: JList[Edge] = null
          private def astOut: JList[Edge] = {
            if (_astOut == null) _astOut = new JLinkedList
            _astOut
          }

          private var _refOut: JList[Edge] = null
          private def refOut: JList[Edge] = {
            if (_refOut == null) _refOut = new JLinkedList
            _refOut
          }

          private var _containsNodeOut: JList[Edge] = null
          private def containsNodeOut: JList[Edge] = {
            if (_containsNodeOut == null) _containsNodeOut = new JLinkedList
            _containsNodeOut
          }

        def getId: JLong = {
          _id
        }

        /* performance optimisation to save instantiating an iterator for each property lookup */
        override protected def specificProperty[A](key: String): VertexProperty[A] = {
          Type2.Keys.KeyToValue.get(key) match {
            case None => VertexProperty.empty[A]
            case Some(fieldAccess) => 
              fieldAccess(this) match {
                case null => VertexProperty.empty[A]
                case values: List[_] => throw Vertex.Exceptions.multiplePropertiesExistForProvidedKey(key)
                case value => new TinkerVertexProperty(-1, this, key, value.asInstanceOf[A])
              }
          }
        }

        override protected def specificProperties[A](key: String): JIterator[VertexProperty[A]] = {
          Type2.Keys.KeyToValue.get(key) match {
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
 else  if (key == "TYPE_DECL_FULL_NAME") this._typeDeclFullName = value.asInstanceOf[String] 
 else throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
          new TinkerVertexProperty(-1, this, key, value)
        }

        override protected def addSpecializedInEdge(edge: Edge): Unit =
          edge match {
            case edge: io.shiftleft.codepropertygraph.generated.edges.Ref => refIn.add(edge)
case edge: io.shiftleft.codepropertygraph.generated.edges.EvalType => evalType2In.add(edge)
case edge: io.shiftleft.codepropertygraph.generated.edges.ContainsNode => containsNodeIn.add(edge)
case edge: io.shiftleft.codepropertygraph.generated.edges.InheritsFrom => inheritsFromIn.add(edge)
            case otherwise => throw new IllegalArgumentException("incoming edge of type " + edge.getClass + " not (yet) supported by Type2. You may want to add it to cpg.json")
          }

        override protected def addSpecializedOutEdge(edge: Edge): Unit =
          edge match {
            case edge: io.shiftleft.codepropertygraph.generated.edges.Ast => astOut.add(edge)
case edge: io.shiftleft.codepropertygraph.generated.edges.Ref => refOut.add(edge)
case edge: io.shiftleft.codepropertygraph.generated.edges.ContainsNode => containsNodeOut.add(edge)
            case otherwise => throw new IllegalArgumentException("outgoing edge of type " + edge.getClass + " not supported by Type2. You may want to add it to cpg.json")
          }

        override protected def specificEdges(direction: Direction, labels: String*): JIterator[Edge] = {
          val walkLabels: Set[String] = 
            if (labels.length > 0) labels.toSet
            else {
              // if no labels are specified, walk all
              direction match {
                case Direction.IN => Type2.Edges.In
                case Direction.OUT => Type2.Edges.Out
                case Direction.BOTH => Type2.Edges.In ++ Type2.Edges.Out
              }
            }

          val iterators = new MultiIterator[Edge]
          if (direction == Direction.IN || direction == Direction.BOTH) {
            walkLabels.collect {
              case io.shiftleft.codepropertygraph.generated.edges.Ref.Label => iterators.addIterator(refIn.iterator)
case io.shiftleft.codepropertygraph.generated.edges.EvalType.Label => iterators.addIterator(evalType2In.iterator)
case io.shiftleft.codepropertygraph.generated.edges.ContainsNode.Label => iterators.addIterator(containsNodeIn.iterator)
case io.shiftleft.codepropertygraph.generated.edges.InheritsFrom.Label => iterators.addIterator(inheritsFromIn.iterator)
              case _ => // ignore other labels
            }
          }
          if (direction == Direction.OUT || direction == Direction.BOTH) {
            walkLabels.collect {
              case io.shiftleft.codepropertygraph.generated.edges.Ast.Label => iterators.addIterator(astOut.iterator)
case io.shiftleft.codepropertygraph.generated.edges.Ref.Label => iterators.addIterator(refOut.iterator)
case io.shiftleft.codepropertygraph.generated.edges.ContainsNode.Label => iterators.addIterator(containsNodeOut.iterator)
              case _ => // ignore other labels
            }
          }
          iterators
        }

        override protected def removeSpecificInEdge(edge: Edge): Unit = {
          edge match {
            case edge: io.shiftleft.codepropertygraph.generated.edges.Ref => refIn.remove(edge)
case edge: io.shiftleft.codepropertygraph.generated.edges.EvalType => evalType2In.remove(edge)
case edge: io.shiftleft.codepropertygraph.generated.edges.ContainsNode => containsNodeIn.remove(edge)
case edge: io.shiftleft.codepropertygraph.generated.edges.InheritsFrom => inheritsFromIn.remove(edge)
            case otherwise => throw new IllegalArgumentException("incoming edge of type " + edge.getClass + " not supported by Type2. You may want to add it to cpg.json")
          }
        }

        override protected def removeSpecificOutEdge(edge: Edge): Unit = {
          edge match {
            case edge: io.shiftleft.codepropertygraph.generated.edges.Ast => astOut.remove(edge)
case edge: io.shiftleft.codepropertygraph.generated.edges.Ref => refOut.remove(edge)
case edge: io.shiftleft.codepropertygraph.generated.edges.ContainsNode => containsNodeOut.remove(edge)
            case otherwise => throw new IllegalArgumentException("outgoing edge of type " + edge.getClass + " not supported by Type2. You may want to add it to cpg.json")
          }
        }

        
      }
