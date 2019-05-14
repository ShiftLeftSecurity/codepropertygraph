      package io.shiftleft.codepropertygraph.generated.edges

      import java.lang.{Boolean => JBoolean, Long => JLong}
      import java.util.{Map => JMap, Set => JSet}

      import org.apache.tinkerpop.gremlin.structure.Property
      import org.apache.tinkerpop.gremlin.structure.Vertex
      import org.apache.tinkerpop.gremlin.tinkergraph.structure.{EdgeRef, SpecializedElementFactory, SpecializedTinkerEdge, TinkerGraph, TinkerProperty, VertexRef}

      import scala.collection.JavaConverters._

      object Ast {
        val Label = "AST"
        object Keys {
          val All: JSet[String] = Set("TEST").asJava
          val KeyToValue: Map[String, Ast => Any] = Map(
            "TEST" -> { instance: Ast => instance.test()}
          )
        }

        val Factory = new SpecializedElementFactory.ForEdge[Ast] {
          override val forLabel = Ast.Label

          override def createEdge(id: JLong, graph: TinkerGraph, outVertex: Vertex, inVertex: Vertex) =
            new Ast(graph, id, outVertex, inVertex)

          override def createEdgeRef(id: JLong, graph: TinkerGraph, outVertex: Vertex, inVertex: Vertex): EdgeRef[Ast] = new AstRef(createEdge(id, graph, outVertex, inVertex))
        }
      }

/** important: do not used `wrapped` internally in this class, only pass it to VertexRef constructor
  * this is to ensure it can be managed by the ReferenceManager */
class AstRef(wrapped: Ast) extends EdgeRef[Ast](wrapped) {
}
      
      class Ast(private val _graph: TinkerGraph, private val _id: Long, private val _outVertex: Vertex, _inVertex: Vertex)
          extends SpecializedTinkerEdge(_graph, _id, _outVertex, Ast.Label, _inVertex, Ast.Keys.All) {

        override protected def specificProperty[A](key: String): Property[A] =
          Ast.Keys.KeyToValue.get(key) match {
            case None => Property.empty[A]
            case Some(fieldAccess) =>
              fieldAccess(this) match {
                case null | None => Property.empty[A]
                case Some(value) => new TinkerProperty(this, key, value.asInstanceOf[A])
                case value => new TinkerProperty(this, key, value.asInstanceOf[A])
              }
          }

        private var _test: String = null
        def test(): String = _test

        override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
          if (key == "TEST") this._test = value.asInstanceOf[String]
          else throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
          property(key)
        }

        override protected def removeSpecificProperty(key: String): Unit =
          if (key == "TEST") this._test = null
          else throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
      }

       object Propagate {
         val Label = "PROPAGATE"
         object Keys {
           val All: JSet[String] = Set("ALIAS", "TEST").asJava
           val KeyToValue: Map[String, Propagate => Any] = Map(
              "ALIAS" -> { instance: Propagate => instance.alias()},
              "TEST" -> { instance: Propagate => instance.test()}
           )
         }

         val Factory = new SpecializedElementFactory.ForEdge[Propagate] {
           override val forLabel = Propagate.Label

           override def createEdge(id: JLong, graph: TinkerGraph, outVertex: Vertex, inVertex: Vertex) =
             new Propagate(graph, id, outVertex, inVertex)

           override def createEdgeRef(id: JLong, graph: TinkerGraph, outVertex: Vertex, inVertex: Vertex): EdgeRef[Propagate] = new PropagateRef(createEdge(id, graph, outVertex, inVertex))
         }
       }

      /** important: do not used `wrapped` internally in this class, only pass it to VertexRef constructor
        * this is to ensure it can be managed by the ReferenceManager */
      class PropagateRef(wrapped: Propagate) extends EdgeRef[Propagate](wrapped) {
      }
      
       class Propagate(private val _graph: TinkerGraph, private val _id: Long, private val _outVertex: Vertex, _inVertex: Vertex)
           extends SpecializedTinkerEdge(_graph, _id, _outVertex, Propagate.Label, _inVertex, Propagate.Keys.All) {

         private var _alias: JBoolean = null
 def alias(): JBoolean = _alias

         private var _test: String = null
         def test(): String = _test

         override protected def specificProperty[A](key: String): Property[A] =
           Propagate.Keys.KeyToValue.get(key) match {
             case None => Property.empty[A]
             case Some(fieldAccess) =>
               fieldAccess(this) match {
                 case null | None => Property.empty[A]
                 case Some(value) => new TinkerProperty(this, key, value.asInstanceOf[A])
                 case value => new TinkerProperty(this, key, value.asInstanceOf[A])
               }
           }

         override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
            if (key == "ALIAS") this._alias = value.asInstanceOf[JBoolean]
  else throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
           property(key)
         }

         override protected def removeSpecificProperty(key: String): Unit =
            if (key == "ALIAS") this._alias = null
  else throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
       }
