      package io.shiftleft.codepropertygraph.generated.edges

      import java.lang.{Boolean => JBoolean, Long => JLong}
      import java.util.{HashMap => JHashMap, Set => JSet, TreeMap}
      import org.apache.tinkerpop.gremlin.structure.Property
      import org.apache.tinkerpop.gremlin.structure.{Vertex, VertexProperty}
      import org.apache.tinkerpop.gremlin.tinkergraph.structure.{EdgeRef, SpecializedElementFactory, SpecializedTinkerEdge, TinkerGraph, TinkerProperty, TinkerVertex, VertexRef}
      import scala.collection.JavaConverters._
      
        object Factories {
//           lazy val All: List[SpecializedElementFactory.ForEdge[_]] = List(AliasOf.Factory, Ast.Factory, AttachedData.Factory, BindsTo.Factory, Call.Factory, CallArg.Factory, CallArgOut.Factory, CallRet.Factory, Capture.Factory, CapturedBy.Factory, Cdg.Factory, Cfg.Factory, Contains.Factory, ContainsNode.Factory, Dominate.Factory, EvalType.Factory, InheritsFrom.Factory, IsSensitiveDataDescrOf.Factory, IsSensitiveDataDescrOfRef.Factory, IsSensitiveDataOfType.Factory, ParameterLink.Factory, PostDominate.Factory, Propagate.Factory, ReachingDef.Factory, Receiver.Factory, Ref.Factory, ResolvedTo.Factory, TaggedBy.Factory, TaintRemove.Factory, Vtable.Factory)
//           lazy val AllAsJava: java.util.List[SpecializedElementFactory.ForEdge[_]] = All.asJava
        }
      

object ContainsNode {
  val Label = "CONTAINS_NODE"
  object Keys {
    val LocalName = "LOCAL_NAME"
    object Indices {
      val LocalName = 0
    }

    val All: JSet[String] = Set(LocalName).asJava
    val KeyToValue: Map[String, ContainsNodeDb => Any] = Map(
       LocalName -> { instance: ContainsNodeDb => instance.localName()},
    )
  }

  val Factory = new SpecializedElementFactory.ForEdge[ContainsNodeDb] {
    override val forLabel = ContainsNode.Label

    override def createEdge(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) =
      new ContainsNodeDb(graph, id, outVertex, inVertex)

    override def createEdgeRef(edge: ContainsNodeDb) = ContainsNode(edge)

    override def createEdgeRef(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) = 
      ContainsNode(id, graph)

    /* XXX0 new part start*/
    override def propertyNamesByIndex() = {
      val ret = new JHashMap[Integer, String]
      ret.put(ContainsNode.Keys.Indices.LocalName, ContainsNode.Keys.LocalName)
      ret
    }
    override def propertyTypeByIndex() = {
      val ret = new JHashMap[Integer, Class[_]]
      ret.put(ContainsNode.Keys.Indices.LocalName, classOf[String])
      ret
    }
    /* XXX0 new part end*/
  }

  def apply(wrapped: ContainsNodeDb) = new EdgeRef(wrapped) with ContainsNode
  def apply(id: Long, graph: TinkerGraph) = 
    new EdgeRef[ContainsNodeDb](id, ContainsNode.Label, graph) with ContainsNode
}
      trait ContainsNode extends EdgeRef[ContainsNodeDb]
      class ContainsNodeDb(private val _graph: TinkerGraph, private val _id: Long, private val _outVertex: Vertex, _inVertex: Vertex)
          extends SpecializedTinkerEdge(_graph, _id, _outVertex, ContainsNode.Label, _inVertex, ContainsNode.Keys.All) {

        /* XXX0 new part start */
        private var _localName: Option[String] = None
def localName(): Option[String] = _localName
        /* XXX0 new part end */

        /* XXX0 new part start */
        override def propertiesByStorageIdx = {
          val properties = new TreeMap[Integer, Object]
          // TODO optimise: load all properties in one go rather than lazily
          localName.map { value => properties.put(ContainsNode.Keys.Indices.LocalName, value) }
          properties
        }
        /* XXX0 new part end */

        override protected def specificProperty[A](key: String): Property[A] =
          ContainsNode.Keys.KeyToValue.get(key) match {
            case None => Property.empty[A]
            case Some(fieldAccess) => 
              fieldAccess(this) match {
                case null | None => Property.empty[A]
                case Some(value) => new TinkerProperty(this, key, value.asInstanceOf[A])
                case value => new TinkerProperty(this, key, value.asInstanceOf[A])
              }
          }

        override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
           if (key == "LOCAL_NAME") this._localName = Option(value).asInstanceOf[Option[String]] 
 else throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
          property(key)
        }

        override protected def removeSpecificProperty(key: String): Unit =
           if (key == "LOCAL_NAME") this._localName = null 
 else throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
      }
        

// object AliasOf {
//   val Label = "ALIAS_OF"
//   object Keys {
//     val All: JSet[String] = Set().asJava
//     val KeyToValue: Map[String, AliasOfDb => Any] = Map(
      
//     )
//   }

//   val Factory = new SpecializedElementFactory.ForEdge[AliasOfDb] {
//     override val forLabel = AliasOf.Label

//     override def createEdge(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) =
//       new AliasOfDb(graph, id, outVertex, inVertex)

//     override def createEdgeRef(edge: AliasOfDb) = AliasOf(edge)

//     override def createEdgeRef(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) = 
//       AliasOf(id, graph)
//   }

//   def apply(wrapped: AliasOfDb) = new EdgeRef(wrapped) with AliasOf
//   def apply(id: Long, graph: TinkerGraph) = 
//     new EdgeRef[AliasOfDb](id, AliasOf.Label, graph) with AliasOf
// }
//       trait AliasOf extends EdgeRef[AliasOfDb]
//       class AliasOfDb(private val _graph: TinkerGraph, private val _id: Long, private val _outVertex: Vertex, _inVertex: Vertex)
//           extends SpecializedTinkerEdge(_graph, _id, _outVertex, AliasOf.Label, _inVertex, AliasOf.Keys.All) {

        
//         override protected def specificProperty[A](key: String): Property[A] =
//           AliasOf.Keys.KeyToValue.get(key) match {
//             case None => Property.empty[A]
//             case Some(fieldAccess) => 
//               fieldAccess(this) match {
//                 case null | None => Property.empty[A]
//                 case Some(value) => new TinkerProperty(this, key, value.asInstanceOf[A])
//                 case value => new TinkerProperty(this, key, value.asInstanceOf[A])
//               }
//           }

//         override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//           property(key)
//         }

//         override protected def removeSpecificProperty(key: String): Unit =
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//       }
      

// object Ast {
//   val Label = "AST"
//   object Keys {
//     val All: JSet[String] = Set().asJava
//     val KeyToValue: Map[String, AstDb => Any] = Map(
      
//     )
//   }

//   val Factory = new SpecializedElementFactory.ForEdge[AstDb] {
//     override val forLabel = Ast.Label

//     override def createEdge(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) =
//       new AstDb(graph, id, outVertex, inVertex)

//     override def createEdgeRef(edge: AstDb) = Ast(edge)

//     override def createEdgeRef(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) = 
//       Ast(id, graph)
//   }

//   def apply(wrapped: AstDb) = new EdgeRef(wrapped) with Ast
//   def apply(id: Long, graph: TinkerGraph) = 
//     new EdgeRef[AstDb](id, Ast.Label, graph) with Ast
// }
//       trait Ast extends EdgeRef[AstDb]
//       class AstDb(private val _graph: TinkerGraph, private val _id: Long, private val _outVertex: Vertex, _inVertex: Vertex)
//           extends SpecializedTinkerEdge(_graph, _id, _outVertex, Ast.Label, _inVertex, Ast.Keys.All) {

        
//         override protected def specificProperty[A](key: String): Property[A] =
//           Ast.Keys.KeyToValue.get(key) match {
//             case None => Property.empty[A]
//             case Some(fieldAccess) => 
//               fieldAccess(this) match {
//                 case null | None => Property.empty[A]
//                 case Some(value) => new TinkerProperty(this, key, value.asInstanceOf[A])
//                 case value => new TinkerProperty(this, key, value.asInstanceOf[A])
//               }
//           }

//         override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//           property(key)
//         }

//         override protected def removeSpecificProperty(key: String): Unit =
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//       }
      

// object AttachedData {
//   val Label = "ATTACHED_DATA"
//   object Keys {
//     val All: JSet[String] = Set().asJava
//     val KeyToValue: Map[String, AttachedDataDb => Any] = Map(
      
//     )
//   }

//   val Factory = new SpecializedElementFactory.ForEdge[AttachedDataDb] {
//     override val forLabel = AttachedData.Label

//     override def createEdge(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) =
//       new AttachedDataDb(graph, id, outVertex, inVertex)

//     override def createEdgeRef(edge: AttachedDataDb) = AttachedData(edge)

//     override def createEdgeRef(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) = 
//       AttachedData(id, graph)
//   }

//   def apply(wrapped: AttachedDataDb) = new EdgeRef(wrapped) with AttachedData
//   def apply(id: Long, graph: TinkerGraph) = 
//     new EdgeRef[AttachedDataDb](id, AttachedData.Label, graph) with AttachedData
// }
//       trait AttachedData extends EdgeRef[AttachedDataDb]
//       class AttachedDataDb(private val _graph: TinkerGraph, private val _id: Long, private val _outVertex: Vertex, _inVertex: Vertex)
//           extends SpecializedTinkerEdge(_graph, _id, _outVertex, AttachedData.Label, _inVertex, AttachedData.Keys.All) {

        
//         override protected def specificProperty[A](key: String): Property[A] =
//           AttachedData.Keys.KeyToValue.get(key) match {
//             case None => Property.empty[A]
//             case Some(fieldAccess) => 
//               fieldAccess(this) match {
//                 case null | None => Property.empty[A]
//                 case Some(value) => new TinkerProperty(this, key, value.asInstanceOf[A])
//                 case value => new TinkerProperty(this, key, value.asInstanceOf[A])
//               }
//           }

//         override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//           property(key)
//         }

//         override protected def removeSpecificProperty(key: String): Unit =
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//       }
      

// object BindsTo {
//   val Label = "BINDS_TO"
//   object Keys {
//     val All: JSet[String] = Set().asJava
//     val KeyToValue: Map[String, BindsToDb => Any] = Map(
      
//     )
//   }

//   val Factory = new SpecializedElementFactory.ForEdge[BindsToDb] {
//     override val forLabel = BindsTo.Label

//     override def createEdge(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) =
//       new BindsToDb(graph, id, outVertex, inVertex)

//     override def createEdgeRef(edge: BindsToDb) = BindsTo(edge)

//     override def createEdgeRef(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) = 
//       BindsTo(id, graph)
//   }

//   def apply(wrapped: BindsToDb) = new EdgeRef(wrapped) with BindsTo
//   def apply(id: Long, graph: TinkerGraph) = 
//     new EdgeRef[BindsToDb](id, BindsTo.Label, graph) with BindsTo
// }
//       trait BindsTo extends EdgeRef[BindsToDb]
//       class BindsToDb(private val _graph: TinkerGraph, private val _id: Long, private val _outVertex: Vertex, _inVertex: Vertex)
//           extends SpecializedTinkerEdge(_graph, _id, _outVertex, BindsTo.Label, _inVertex, BindsTo.Keys.All) {

        
//         override protected def specificProperty[A](key: String): Property[A] =
//           BindsTo.Keys.KeyToValue.get(key) match {
//             case None => Property.empty[A]
//             case Some(fieldAccess) => 
//               fieldAccess(this) match {
//                 case null | None => Property.empty[A]
//                 case Some(value) => new TinkerProperty(this, key, value.asInstanceOf[A])
//                 case value => new TinkerProperty(this, key, value.asInstanceOf[A])
//               }
//           }

//         override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//           property(key)
//         }

//         override protected def removeSpecificProperty(key: String): Unit =
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//       }
      

// object Call {
//   val Label = "CALL"
//   object Keys {
//     val All: JSet[String] = Set().asJava
//     val KeyToValue: Map[String, CallDb => Any] = Map(
      
//     )
//   }

//   val Factory = new SpecializedElementFactory.ForEdge[CallDb] {
//     override val forLabel = Call.Label

//     override def createEdge(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) =
//       new CallDb(graph, id, outVertex, inVertex)

//     override def createEdgeRef(edge: CallDb) = Call(edge)

//     override def createEdgeRef(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) = 
//       Call(id, graph)
//   }

//   def apply(wrapped: CallDb) = new EdgeRef(wrapped) with Call
//   def apply(id: Long, graph: TinkerGraph) = 
//     new EdgeRef[CallDb](id, Call.Label, graph) with Call
// }
//       trait Call extends EdgeRef[CallDb]
//       class CallDb(private val _graph: TinkerGraph, private val _id: Long, private val _outVertex: Vertex, _inVertex: Vertex)
//           extends SpecializedTinkerEdge(_graph, _id, _outVertex, Call.Label, _inVertex, Call.Keys.All) {

        
//         override protected def specificProperty[A](key: String): Property[A] =
//           Call.Keys.KeyToValue.get(key) match {
//             case None => Property.empty[A]
//             case Some(fieldAccess) => 
//               fieldAccess(this) match {
//                 case null | None => Property.empty[A]
//                 case Some(value) => new TinkerProperty(this, key, value.asInstanceOf[A])
//                 case value => new TinkerProperty(this, key, value.asInstanceOf[A])
//               }
//           }

//         override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//           property(key)
//         }

//         override protected def removeSpecificProperty(key: String): Unit =
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//       }
      

// object CallArg {
//   val Label = "CALL_ARG"
//   object Keys {
//     val All: JSet[String] = Set().asJava
//     val KeyToValue: Map[String, CallArgDb => Any] = Map(
      
//     )
//   }

//   val Factory = new SpecializedElementFactory.ForEdge[CallArgDb] {
//     override val forLabel = CallArg.Label

//     override def createEdge(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) =
//       new CallArgDb(graph, id, outVertex, inVertex)

//     override def createEdgeRef(edge: CallArgDb) = CallArg(edge)

//     override def createEdgeRef(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) = 
//       CallArg(id, graph)
//   }

//   def apply(wrapped: CallArgDb) = new EdgeRef(wrapped) with CallArg
//   def apply(id: Long, graph: TinkerGraph) = 
//     new EdgeRef[CallArgDb](id, CallArg.Label, graph) with CallArg
// }
//       trait CallArg extends EdgeRef[CallArgDb]
//       class CallArgDb(private val _graph: TinkerGraph, private val _id: Long, private val _outVertex: Vertex, _inVertex: Vertex)
//           extends SpecializedTinkerEdge(_graph, _id, _outVertex, CallArg.Label, _inVertex, CallArg.Keys.All) {

        
//         override protected def specificProperty[A](key: String): Property[A] =
//           CallArg.Keys.KeyToValue.get(key) match {
//             case None => Property.empty[A]
//             case Some(fieldAccess) => 
//               fieldAccess(this) match {
//                 case null | None => Property.empty[A]
//                 case Some(value) => new TinkerProperty(this, key, value.asInstanceOf[A])
//                 case value => new TinkerProperty(this, key, value.asInstanceOf[A])
//               }
//           }

//         override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//           property(key)
//         }

//         override protected def removeSpecificProperty(key: String): Unit =
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//       }
      

// object CallArgOut {
//   val Label = "CALL_ARG_OUT"
//   object Keys {
//     val All: JSet[String] = Set().asJava
//     val KeyToValue: Map[String, CallArgOutDb => Any] = Map(
      
//     )
//   }

//   val Factory = new SpecializedElementFactory.ForEdge[CallArgOutDb] {
//     override val forLabel = CallArgOut.Label

//     override def createEdge(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) =
//       new CallArgOutDb(graph, id, outVertex, inVertex)

//     override def createEdgeRef(edge: CallArgOutDb) = CallArgOut(edge)

//     override def createEdgeRef(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) = 
//       CallArgOut(id, graph)
//   }

//   def apply(wrapped: CallArgOutDb) = new EdgeRef(wrapped) with CallArgOut
//   def apply(id: Long, graph: TinkerGraph) = 
//     new EdgeRef[CallArgOutDb](id, CallArgOut.Label, graph) with CallArgOut
// }
//       trait CallArgOut extends EdgeRef[CallArgOutDb]
//       class CallArgOutDb(private val _graph: TinkerGraph, private val _id: Long, private val _outVertex: Vertex, _inVertex: Vertex)
//           extends SpecializedTinkerEdge(_graph, _id, _outVertex, CallArgOut.Label, _inVertex, CallArgOut.Keys.All) {

        
//         override protected def specificProperty[A](key: String): Property[A] =
//           CallArgOut.Keys.KeyToValue.get(key) match {
//             case None => Property.empty[A]
//             case Some(fieldAccess) => 
//               fieldAccess(this) match {
//                 case null | None => Property.empty[A]
//                 case Some(value) => new TinkerProperty(this, key, value.asInstanceOf[A])
//                 case value => new TinkerProperty(this, key, value.asInstanceOf[A])
//               }
//           }

//         override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//           property(key)
//         }

//         override protected def removeSpecificProperty(key: String): Unit =
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//       }
      

// object CallRet {
//   val Label = "CALL_RET"
//   object Keys {
//     val All: JSet[String] = Set().asJava
//     val KeyToValue: Map[String, CallRetDb => Any] = Map(
      
//     )
//   }

//   val Factory = new SpecializedElementFactory.ForEdge[CallRetDb] {
//     override val forLabel = CallRet.Label

//     override def createEdge(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) =
//       new CallRetDb(graph, id, outVertex, inVertex)

//     override def createEdgeRef(edge: CallRetDb) = CallRet(edge)

//     override def createEdgeRef(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) = 
//       CallRet(id, graph)
//   }

//   def apply(wrapped: CallRetDb) = new EdgeRef(wrapped) with CallRet
//   def apply(id: Long, graph: TinkerGraph) = 
//     new EdgeRef[CallRetDb](id, CallRet.Label, graph) with CallRet
// }
//       trait CallRet extends EdgeRef[CallRetDb]
//       class CallRetDb(private val _graph: TinkerGraph, private val _id: Long, private val _outVertex: Vertex, _inVertex: Vertex)
//           extends SpecializedTinkerEdge(_graph, _id, _outVertex, CallRet.Label, _inVertex, CallRet.Keys.All) {

        
//         override protected def specificProperty[A](key: String): Property[A] =
//           CallRet.Keys.KeyToValue.get(key) match {
//             case None => Property.empty[A]
//             case Some(fieldAccess) => 
//               fieldAccess(this) match {
//                 case null | None => Property.empty[A]
//                 case Some(value) => new TinkerProperty(this, key, value.asInstanceOf[A])
//                 case value => new TinkerProperty(this, key, value.asInstanceOf[A])
//               }
//           }

//         override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//           property(key)
//         }

//         override protected def removeSpecificProperty(key: String): Unit =
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//       }
      

// object Capture {
//   val Label = "CAPTURE"
//   object Keys {
//     val All: JSet[String] = Set().asJava
//     val KeyToValue: Map[String, CaptureDb => Any] = Map(
      
//     )
//   }

//   val Factory = new SpecializedElementFactory.ForEdge[CaptureDb] {
//     override val forLabel = Capture.Label

//     override def createEdge(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) =
//       new CaptureDb(graph, id, outVertex, inVertex)

//     override def createEdgeRef(edge: CaptureDb) = Capture(edge)

//     override def createEdgeRef(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) = 
//       Capture(id, graph)
//   }

//   def apply(wrapped: CaptureDb) = new EdgeRef(wrapped) with Capture
//   def apply(id: Long, graph: TinkerGraph) = 
//     new EdgeRef[CaptureDb](id, Capture.Label, graph) with Capture
// }
//       trait Capture extends EdgeRef[CaptureDb]
//       class CaptureDb(private val _graph: TinkerGraph, private val _id: Long, private val _outVertex: Vertex, _inVertex: Vertex)
//           extends SpecializedTinkerEdge(_graph, _id, _outVertex, Capture.Label, _inVertex, Capture.Keys.All) {

        
//         override protected def specificProperty[A](key: String): Property[A] =
//           Capture.Keys.KeyToValue.get(key) match {
//             case None => Property.empty[A]
//             case Some(fieldAccess) => 
//               fieldAccess(this) match {
//                 case null | None => Property.empty[A]
//                 case Some(value) => new TinkerProperty(this, key, value.asInstanceOf[A])
//                 case value => new TinkerProperty(this, key, value.asInstanceOf[A])
//               }
//           }

//         override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//           property(key)
//         }

//         override protected def removeSpecificProperty(key: String): Unit =
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//       }
      

// object CapturedBy {
//   val Label = "CAPTURED_BY"
//   object Keys {
//     val All: JSet[String] = Set().asJava
//     val KeyToValue: Map[String, CapturedByDb => Any] = Map(
      
//     )
//   }

//   val Factory = new SpecializedElementFactory.ForEdge[CapturedByDb] {
//     override val forLabel = CapturedBy.Label

//     override def createEdge(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) =
//       new CapturedByDb(graph, id, outVertex, inVertex)

//     override def createEdgeRef(edge: CapturedByDb) = CapturedBy(edge)

//     override def createEdgeRef(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) = 
//       CapturedBy(id, graph)
//   }

//   def apply(wrapped: CapturedByDb) = new EdgeRef(wrapped) with CapturedBy
//   def apply(id: Long, graph: TinkerGraph) = 
//     new EdgeRef[CapturedByDb](id, CapturedBy.Label, graph) with CapturedBy
// }
//       trait CapturedBy extends EdgeRef[CapturedByDb]
//       class CapturedByDb(private val _graph: TinkerGraph, private val _id: Long, private val _outVertex: Vertex, _inVertex: Vertex)
//           extends SpecializedTinkerEdge(_graph, _id, _outVertex, CapturedBy.Label, _inVertex, CapturedBy.Keys.All) {

        
//         override protected def specificProperty[A](key: String): Property[A] =
//           CapturedBy.Keys.KeyToValue.get(key) match {
//             case None => Property.empty[A]
//             case Some(fieldAccess) => 
//               fieldAccess(this) match {
//                 case null | None => Property.empty[A]
//                 case Some(value) => new TinkerProperty(this, key, value.asInstanceOf[A])
//                 case value => new TinkerProperty(this, key, value.asInstanceOf[A])
//               }
//           }

//         override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//           property(key)
//         }

//         override protected def removeSpecificProperty(key: String): Unit =
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//       }
      

// object Cdg {
//   val Label = "CDG"
//   object Keys {
//     val All: JSet[String] = Set().asJava
//     val KeyToValue: Map[String, CdgDb => Any] = Map(
      
//     )
//   }

//   val Factory = new SpecializedElementFactory.ForEdge[CdgDb] {
//     override val forLabel = Cdg.Label

//     override def createEdge(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) =
//       new CdgDb(graph, id, outVertex, inVertex)

//     override def createEdgeRef(edge: CdgDb) = Cdg(edge)

//     override def createEdgeRef(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) = 
//       Cdg(id, graph)
//   }

//   def apply(wrapped: CdgDb) = new EdgeRef(wrapped) with Cdg
//   def apply(id: Long, graph: TinkerGraph) = 
//     new EdgeRef[CdgDb](id, Cdg.Label, graph) with Cdg
// }
//       trait Cdg extends EdgeRef[CdgDb]
//       class CdgDb(private val _graph: TinkerGraph, private val _id: Long, private val _outVertex: Vertex, _inVertex: Vertex)
//           extends SpecializedTinkerEdge(_graph, _id, _outVertex, Cdg.Label, _inVertex, Cdg.Keys.All) {

        
//         override protected def specificProperty[A](key: String): Property[A] =
//           Cdg.Keys.KeyToValue.get(key) match {
//             case None => Property.empty[A]
//             case Some(fieldAccess) => 
//               fieldAccess(this) match {
//                 case null | None => Property.empty[A]
//                 case Some(value) => new TinkerProperty(this, key, value.asInstanceOf[A])
//                 case value => new TinkerProperty(this, key, value.asInstanceOf[A])
//               }
//           }

//         override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//           property(key)
//         }

//         override protected def removeSpecificProperty(key: String): Unit =
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//       }
      

// object Cfg {
//   val Label = "CFG"
//   object Keys {
//     val All: JSet[String] = Set().asJava
//     val KeyToValue: Map[String, CfgDb => Any] = Map(
      
//     )
//   }

//   val Factory = new SpecializedElementFactory.ForEdge[CfgDb] {
//     override val forLabel = Cfg.Label

//     override def createEdge(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) =
//       new CfgDb(graph, id, outVertex, inVertex)

//     override def createEdgeRef(edge: CfgDb) = Cfg(edge)

//     override def createEdgeRef(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) = 
//       Cfg(id, graph)
//   }

//   def apply(wrapped: CfgDb) = new EdgeRef(wrapped) with Cfg
//   def apply(id: Long, graph: TinkerGraph) = 
//     new EdgeRef[CfgDb](id, Cfg.Label, graph) with Cfg
// }
//       trait Cfg extends EdgeRef[CfgDb]
//       class CfgDb(private val _graph: TinkerGraph, private val _id: Long, private val _outVertex: Vertex, _inVertex: Vertex)
//           extends SpecializedTinkerEdge(_graph, _id, _outVertex, Cfg.Label, _inVertex, Cfg.Keys.All) {

        
//         override protected def specificProperty[A](key: String): Property[A] =
//           Cfg.Keys.KeyToValue.get(key) match {
//             case None => Property.empty[A]
//             case Some(fieldAccess) => 
//               fieldAccess(this) match {
//                 case null | None => Property.empty[A]
//                 case Some(value) => new TinkerProperty(this, key, value.asInstanceOf[A])
//                 case value => new TinkerProperty(this, key, value.asInstanceOf[A])
//               }
//           }

//         override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//           property(key)
//         }

//         override protected def removeSpecificProperty(key: String): Unit =
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//       }
      

// object Contains {
//   val Label = "CONTAINS"
//   object Keys {
//     val All: JSet[String] = Set().asJava
//     val KeyToValue: Map[String, ContainsDb => Any] = Map(
      
//     )
//   }

//   val Factory = new SpecializedElementFactory.ForEdge[ContainsDb] {
//     override val forLabel = Contains.Label

//     override def createEdge(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) =
//       new ContainsDb(graph, id, outVertex, inVertex)

//     override def createEdgeRef(edge: ContainsDb) = Contains(edge)

//     override def createEdgeRef(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) = 
//       Contains(id, graph)
//   }

//   def apply(wrapped: ContainsDb) = new EdgeRef(wrapped) with Contains
//   def apply(id: Long, graph: TinkerGraph) = 
//     new EdgeRef[ContainsDb](id, Contains.Label, graph) with Contains
// }
//       trait Contains extends EdgeRef[ContainsDb]
//       class ContainsDb(private val _graph: TinkerGraph, private val _id: Long, private val _outVertex: Vertex, _inVertex: Vertex)
//           extends SpecializedTinkerEdge(_graph, _id, _outVertex, Contains.Label, _inVertex, Contains.Keys.All) {

        
//         override protected def specificProperty[A](key: String): Property[A] =
//           Contains.Keys.KeyToValue.get(key) match {
//             case None => Property.empty[A]
//             case Some(fieldAccess) => 
//               fieldAccess(this) match {
//                 case null | None => Property.empty[A]
//                 case Some(value) => new TinkerProperty(this, key, value.asInstanceOf[A])
//                 case value => new TinkerProperty(this, key, value.asInstanceOf[A])
//               }
//           }

//         override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//           property(key)
//         }

//         override protected def removeSpecificProperty(key: String): Unit =
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//       }
      

// object Dominate {
//   val Label = "DOMINATE"
//   object Keys {
//     val All: JSet[String] = Set().asJava
//     val KeyToValue: Map[String, DominateDb => Any] = Map(
      
//     )
//   }

//   val Factory = new SpecializedElementFactory.ForEdge[DominateDb] {
//     override val forLabel = Dominate.Label

//     override def createEdge(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) =
//       new DominateDb(graph, id, outVertex, inVertex)

//     override def createEdgeRef(edge: DominateDb) = Dominate(edge)

//     override def createEdgeRef(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) = 
//       Dominate(id, graph)
//   }

//   def apply(wrapped: DominateDb) = new EdgeRef(wrapped) with Dominate
//   def apply(id: Long, graph: TinkerGraph) = 
//     new EdgeRef[DominateDb](id, Dominate.Label, graph) with Dominate
// }
//       trait Dominate extends EdgeRef[DominateDb]
//       class DominateDb(private val _graph: TinkerGraph, private val _id: Long, private val _outVertex: Vertex, _inVertex: Vertex)
//           extends SpecializedTinkerEdge(_graph, _id, _outVertex, Dominate.Label, _inVertex, Dominate.Keys.All) {

        
//         override protected def specificProperty[A](key: String): Property[A] =
//           Dominate.Keys.KeyToValue.get(key) match {
//             case None => Property.empty[A]
//             case Some(fieldAccess) => 
//               fieldAccess(this) match {
//                 case null | None => Property.empty[A]
//                 case Some(value) => new TinkerProperty(this, key, value.asInstanceOf[A])
//                 case value => new TinkerProperty(this, key, value.asInstanceOf[A])
//               }
//           }

//         override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//           property(key)
//         }

//         override protected def removeSpecificProperty(key: String): Unit =
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//       }
      

// object EvalType {
//   val Label = "EVAL_TYPE"
//   object Keys {
//     val All: JSet[String] = Set().asJava
//     val KeyToValue: Map[String, EvalTypeDb => Any] = Map(
      
//     )
//   }

//   val Factory = new SpecializedElementFactory.ForEdge[EvalTypeDb] {
//     override val forLabel = EvalType.Label

//     override def createEdge(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) =
//       new EvalTypeDb(graph, id, outVertex, inVertex)

//     override def createEdgeRef(edge: EvalTypeDb) = EvalType(edge)

//     override def createEdgeRef(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) = 
//       EvalType(id, graph)
//   }

//   def apply(wrapped: EvalTypeDb) = new EdgeRef(wrapped) with EvalType
//   def apply(id: Long, graph: TinkerGraph) = 
//     new EdgeRef[EvalTypeDb](id, EvalType.Label, graph) with EvalType
// }
//       trait EvalType extends EdgeRef[EvalTypeDb]
//       class EvalTypeDb(private val _graph: TinkerGraph, private val _id: Long, private val _outVertex: Vertex, _inVertex: Vertex)
//           extends SpecializedTinkerEdge(_graph, _id, _outVertex, EvalType.Label, _inVertex, EvalType.Keys.All) {

        
//         override protected def specificProperty[A](key: String): Property[A] =
//           EvalType.Keys.KeyToValue.get(key) match {
//             case None => Property.empty[A]
//             case Some(fieldAccess) => 
//               fieldAccess(this) match {
//                 case null | None => Property.empty[A]
//                 case Some(value) => new TinkerProperty(this, key, value.asInstanceOf[A])
//                 case value => new TinkerProperty(this, key, value.asInstanceOf[A])
//               }
//           }

//         override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//           property(key)
//         }

//         override protected def removeSpecificProperty(key: String): Unit =
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//       }
      

// object InheritsFrom {
//   val Label = "INHERITS_FROM"
//   object Keys {
//     val All: JSet[String] = Set().asJava
//     val KeyToValue: Map[String, InheritsFromDb => Any] = Map(
      
//     )
//   }

//   val Factory = new SpecializedElementFactory.ForEdge[InheritsFromDb] {
//     override val forLabel = InheritsFrom.Label

//     override def createEdge(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) =
//       new InheritsFromDb(graph, id, outVertex, inVertex)

//     override def createEdgeRef(edge: InheritsFromDb) = InheritsFrom(edge)

//     override def createEdgeRef(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) = 
//       InheritsFrom(id, graph)
//   }

//   def apply(wrapped: InheritsFromDb) = new EdgeRef(wrapped) with InheritsFrom
//   def apply(id: Long, graph: TinkerGraph) = 
//     new EdgeRef[InheritsFromDb](id, InheritsFrom.Label, graph) with InheritsFrom
// }
//       trait InheritsFrom extends EdgeRef[InheritsFromDb]
//       class InheritsFromDb(private val _graph: TinkerGraph, private val _id: Long, private val _outVertex: Vertex, _inVertex: Vertex)
//           extends SpecializedTinkerEdge(_graph, _id, _outVertex, InheritsFrom.Label, _inVertex, InheritsFrom.Keys.All) {

        
//         override protected def specificProperty[A](key: String): Property[A] =
//           InheritsFrom.Keys.KeyToValue.get(key) match {
//             case None => Property.empty[A]
//             case Some(fieldAccess) => 
//               fieldAccess(this) match {
//                 case null | None => Property.empty[A]
//                 case Some(value) => new TinkerProperty(this, key, value.asInstanceOf[A])
//                 case value => new TinkerProperty(this, key, value.asInstanceOf[A])
//               }
//           }

//         override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//           property(key)
//         }

//         override protected def removeSpecificProperty(key: String): Unit =
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//       }
      

// object IsSensitiveDataDescrOf {
//   val Label = "IS_SENSITIVE_DATA_DESCR_OF"
//   object Keys {
//     val All: JSet[String] = Set().asJava
//     val KeyToValue: Map[String, IsSensitiveDataDescrOfDb => Any] = Map(
      
//     )
//   }

//   val Factory = new SpecializedElementFactory.ForEdge[IsSensitiveDataDescrOfDb] {
//     override val forLabel = IsSensitiveDataDescrOf.Label

//     override def createEdge(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) =
//       new IsSensitiveDataDescrOfDb(graph, id, outVertex, inVertex)

//     override def createEdgeRef(edge: IsSensitiveDataDescrOfDb) = IsSensitiveDataDescrOf(edge)

//     override def createEdgeRef(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) = 
//       IsSensitiveDataDescrOf(id, graph)
//   }

//   def apply(wrapped: IsSensitiveDataDescrOfDb) = new EdgeRef(wrapped) with IsSensitiveDataDescrOf
//   def apply(id: Long, graph: TinkerGraph) = 
//     new EdgeRef[IsSensitiveDataDescrOfDb](id, IsSensitiveDataDescrOf.Label, graph) with IsSensitiveDataDescrOf
// }
//       trait IsSensitiveDataDescrOf extends EdgeRef[IsSensitiveDataDescrOfDb]
//       class IsSensitiveDataDescrOfDb(private val _graph: TinkerGraph, private val _id: Long, private val _outVertex: Vertex, _inVertex: Vertex)
//           extends SpecializedTinkerEdge(_graph, _id, _outVertex, IsSensitiveDataDescrOf.Label, _inVertex, IsSensitiveDataDescrOf.Keys.All) {

        
//         override protected def specificProperty[A](key: String): Property[A] =
//           IsSensitiveDataDescrOf.Keys.KeyToValue.get(key) match {
//             case None => Property.empty[A]
//             case Some(fieldAccess) => 
//               fieldAccess(this) match {
//                 case null | None => Property.empty[A]
//                 case Some(value) => new TinkerProperty(this, key, value.asInstanceOf[A])
//                 case value => new TinkerProperty(this, key, value.asInstanceOf[A])
//               }
//           }

//         override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//           property(key)
//         }

//         override protected def removeSpecificProperty(key: String): Unit =
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//       }
      

// object IsSensitiveDataDescrOfRef {
//   val Label = "IS_SENSITIVE_DATA_DESCR_OF_REF"
//   object Keys {
//     val All: JSet[String] = Set().asJava
//     val KeyToValue: Map[String, IsSensitiveDataDescrOfRefDb => Any] = Map(
      
//     )
//   }

//   val Factory = new SpecializedElementFactory.ForEdge[IsSensitiveDataDescrOfRefDb] {
//     override val forLabel = IsSensitiveDataDescrOfRef.Label

//     override def createEdge(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) =
//       new IsSensitiveDataDescrOfRefDb(graph, id, outVertex, inVertex)

//     override def createEdgeRef(edge: IsSensitiveDataDescrOfRefDb) = IsSensitiveDataDescrOfRef(edge)

//     override def createEdgeRef(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) = 
//       IsSensitiveDataDescrOfRef(id, graph)
//   }

//   def apply(wrapped: IsSensitiveDataDescrOfRefDb) = new EdgeRef(wrapped) with IsSensitiveDataDescrOfRef
//   def apply(id: Long, graph: TinkerGraph) = 
//     new EdgeRef[IsSensitiveDataDescrOfRefDb](id, IsSensitiveDataDescrOfRef.Label, graph) with IsSensitiveDataDescrOfRef
// }
//       trait IsSensitiveDataDescrOfRef extends EdgeRef[IsSensitiveDataDescrOfRefDb]
//       class IsSensitiveDataDescrOfRefDb(private val _graph: TinkerGraph, private val _id: Long, private val _outVertex: Vertex, _inVertex: Vertex)
//           extends SpecializedTinkerEdge(_graph, _id, _outVertex, IsSensitiveDataDescrOfRef.Label, _inVertex, IsSensitiveDataDescrOfRef.Keys.All) {

        
//         override protected def specificProperty[A](key: String): Property[A] =
//           IsSensitiveDataDescrOfRef.Keys.KeyToValue.get(key) match {
//             case None => Property.empty[A]
//             case Some(fieldAccess) => 
//               fieldAccess(this) match {
//                 case null | None => Property.empty[A]
//                 case Some(value) => new TinkerProperty(this, key, value.asInstanceOf[A])
//                 case value => new TinkerProperty(this, key, value.asInstanceOf[A])
//               }
//           }

//         override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//           property(key)
//         }

//         override protected def removeSpecificProperty(key: String): Unit =
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//       }
      

// object IsSensitiveDataOfType {
//   val Label = "IS_SENSITIVE_DATA_OF_TYPE"
//   object Keys {
//     val All: JSet[String] = Set().asJava
//     val KeyToValue: Map[String, IsSensitiveDataOfTypeDb => Any] = Map(
      
//     )
//   }

//   val Factory = new SpecializedElementFactory.ForEdge[IsSensitiveDataOfTypeDb] {
//     override val forLabel = IsSensitiveDataOfType.Label

//     override def createEdge(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) =
//       new IsSensitiveDataOfTypeDb(graph, id, outVertex, inVertex)

//     override def createEdgeRef(edge: IsSensitiveDataOfTypeDb) = IsSensitiveDataOfType(edge)

//     override def createEdgeRef(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) = 
//       IsSensitiveDataOfType(id, graph)
//   }

//   def apply(wrapped: IsSensitiveDataOfTypeDb) = new EdgeRef(wrapped) with IsSensitiveDataOfType
//   def apply(id: Long, graph: TinkerGraph) = 
//     new EdgeRef[IsSensitiveDataOfTypeDb](id, IsSensitiveDataOfType.Label, graph) with IsSensitiveDataOfType
// }
//       trait IsSensitiveDataOfType extends EdgeRef[IsSensitiveDataOfTypeDb]
//       class IsSensitiveDataOfTypeDb(private val _graph: TinkerGraph, private val _id: Long, private val _outVertex: Vertex, _inVertex: Vertex)
//           extends SpecializedTinkerEdge(_graph, _id, _outVertex, IsSensitiveDataOfType.Label, _inVertex, IsSensitiveDataOfType.Keys.All) {

        
//         override protected def specificProperty[A](key: String): Property[A] =
//           IsSensitiveDataOfType.Keys.KeyToValue.get(key) match {
//             case None => Property.empty[A]
//             case Some(fieldAccess) => 
//               fieldAccess(this) match {
//                 case null | None => Property.empty[A]
//                 case Some(value) => new TinkerProperty(this, key, value.asInstanceOf[A])
//                 case value => new TinkerProperty(this, key, value.asInstanceOf[A])
//               }
//           }

//         override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//           property(key)
//         }

//         override protected def removeSpecificProperty(key: String): Unit =
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//       }
      

// object ParameterLink {
//   val Label = "PARAMETER_LINK"
//   object Keys {
//     val All: JSet[String] = Set().asJava
//     val KeyToValue: Map[String, ParameterLinkDb => Any] = Map(
      
//     )
//   }

//   val Factory = new SpecializedElementFactory.ForEdge[ParameterLinkDb] {
//     override val forLabel = ParameterLink.Label

//     override def createEdge(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) =
//       new ParameterLinkDb(graph, id, outVertex, inVertex)

//     override def createEdgeRef(edge: ParameterLinkDb) = ParameterLink(edge)

//     override def createEdgeRef(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) = 
//       ParameterLink(id, graph)
//   }

//   def apply(wrapped: ParameterLinkDb) = new EdgeRef(wrapped) with ParameterLink
//   def apply(id: Long, graph: TinkerGraph) = 
//     new EdgeRef[ParameterLinkDb](id, ParameterLink.Label, graph) with ParameterLink
// }
//       trait ParameterLink extends EdgeRef[ParameterLinkDb]
//       class ParameterLinkDb(private val _graph: TinkerGraph, private val _id: Long, private val _outVertex: Vertex, _inVertex: Vertex)
//           extends SpecializedTinkerEdge(_graph, _id, _outVertex, ParameterLink.Label, _inVertex, ParameterLink.Keys.All) {

        
//         override protected def specificProperty[A](key: String): Property[A] =
//           ParameterLink.Keys.KeyToValue.get(key) match {
//             case None => Property.empty[A]
//             case Some(fieldAccess) => 
//               fieldAccess(this) match {
//                 case null | None => Property.empty[A]
//                 case Some(value) => new TinkerProperty(this, key, value.asInstanceOf[A])
//                 case value => new TinkerProperty(this, key, value.asInstanceOf[A])
//               }
//           }

//         override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//           property(key)
//         }

//         override protected def removeSpecificProperty(key: String): Unit =
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//       }
      

// object PostDominate {
//   val Label = "POST_DOMINATE"
//   object Keys {
//     val All: JSet[String] = Set().asJava
//     val KeyToValue: Map[String, PostDominateDb => Any] = Map(
      
//     )
//   }

//   val Factory = new SpecializedElementFactory.ForEdge[PostDominateDb] {
//     override val forLabel = PostDominate.Label

//     override def createEdge(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) =
//       new PostDominateDb(graph, id, outVertex, inVertex)

//     override def createEdgeRef(edge: PostDominateDb) = PostDominate(edge)

//     override def createEdgeRef(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) = 
//       PostDominate(id, graph)
//   }

//   def apply(wrapped: PostDominateDb) = new EdgeRef(wrapped) with PostDominate
//   def apply(id: Long, graph: TinkerGraph) = 
//     new EdgeRef[PostDominateDb](id, PostDominate.Label, graph) with PostDominate
// }
//       trait PostDominate extends EdgeRef[PostDominateDb]
//       class PostDominateDb(private val _graph: TinkerGraph, private val _id: Long, private val _outVertex: Vertex, _inVertex: Vertex)
//           extends SpecializedTinkerEdge(_graph, _id, _outVertex, PostDominate.Label, _inVertex, PostDominate.Keys.All) {

        
//         override protected def specificProperty[A](key: String): Property[A] =
//           PostDominate.Keys.KeyToValue.get(key) match {
//             case None => Property.empty[A]
//             case Some(fieldAccess) => 
//               fieldAccess(this) match {
//                 case null | None => Property.empty[A]
//                 case Some(value) => new TinkerProperty(this, key, value.asInstanceOf[A])
//                 case value => new TinkerProperty(this, key, value.asInstanceOf[A])
//               }
//           }

//         override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//           property(key)
//         }

//         override protected def removeSpecificProperty(key: String): Unit =
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//       }
      

// object Propagate {
//   val Label = "PROPAGATE"
//   object Keys {
//     val All: JSet[String] = Set("ALIAS").asJava
//     val KeyToValue: Map[String, PropagateDb => Any] = Map(
//        "ALIAS" -> { instance: PropagateDb => instance.alias()}
//     )
//   }

//   val Factory = new SpecializedElementFactory.ForEdge[PropagateDb] {
//     override val forLabel = Propagate.Label

//     override def createEdge(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) =
//       new PropagateDb(graph, id, outVertex, inVertex)

//     override def createEdgeRef(edge: PropagateDb) = Propagate(edge)

//     override def createEdgeRef(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) = 
//       Propagate(id, graph)
//   }

//   def apply(wrapped: PropagateDb) = new EdgeRef(wrapped) with Propagate
//   def apply(id: Long, graph: TinkerGraph) = 
//     new EdgeRef[PropagateDb](id, Propagate.Label, graph) with Propagate
// }
//       trait Propagate extends EdgeRef[PropagateDb]
//       class PropagateDb(private val _graph: TinkerGraph, private val _id: Long, private val _outVertex: Vertex, _inVertex: Vertex)
//           extends SpecializedTinkerEdge(_graph, _id, _outVertex, Propagate.Label, _inVertex, Propagate.Keys.All) {

//         private var _alias: JBoolean = null
// def alias(): JBoolean = _alias
//         override protected def specificProperty[A](key: String): Property[A] =
//           Propagate.Keys.KeyToValue.get(key) match {
//             case None => Property.empty[A]
//             case Some(fieldAccess) => 
//               fieldAccess(this) match {
//                 case null | None => Property.empty[A]
//                 case Some(value) => new TinkerProperty(this, key, value.asInstanceOf[A])
//                 case value => new TinkerProperty(this, key, value.asInstanceOf[A])
//               }
//           }

//         override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
//            if (key == "ALIAS") this._alias = value.asInstanceOf[JBoolean] 
//  else throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//           property(key)
//         }

//         override protected def removeSpecificProperty(key: String): Unit =
//            if (key == "ALIAS") this._alias = null 
//  else throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//       }
      

// object ReachingDef {
//   val Label = "REACHING_DEF"
//   object Keys {
//     val All: JSet[String] = Set().asJava
//     val KeyToValue: Map[String, ReachingDefDb => Any] = Map(
      
//     )
//   }

//   val Factory = new SpecializedElementFactory.ForEdge[ReachingDefDb] {
//     override val forLabel = ReachingDef.Label

//     override def createEdge(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) =
//       new ReachingDefDb(graph, id, outVertex, inVertex)

//     override def createEdgeRef(edge: ReachingDefDb) = ReachingDef(edge)

//     override def createEdgeRef(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) = 
//       ReachingDef(id, graph)
//   }

//   def apply(wrapped: ReachingDefDb) = new EdgeRef(wrapped) with ReachingDef
//   def apply(id: Long, graph: TinkerGraph) = 
//     new EdgeRef[ReachingDefDb](id, ReachingDef.Label, graph) with ReachingDef
// }
//       trait ReachingDef extends EdgeRef[ReachingDefDb]
//       class ReachingDefDb(private val _graph: TinkerGraph, private val _id: Long, private val _outVertex: Vertex, _inVertex: Vertex)
//           extends SpecializedTinkerEdge(_graph, _id, _outVertex, ReachingDef.Label, _inVertex, ReachingDef.Keys.All) {

        
//         override protected def specificProperty[A](key: String): Property[A] =
//           ReachingDef.Keys.KeyToValue.get(key) match {
//             case None => Property.empty[A]
//             case Some(fieldAccess) => 
//               fieldAccess(this) match {
//                 case null | None => Property.empty[A]
//                 case Some(value) => new TinkerProperty(this, key, value.asInstanceOf[A])
//                 case value => new TinkerProperty(this, key, value.asInstanceOf[A])
//               }
//           }

//         override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//           property(key)
//         }

//         override protected def removeSpecificProperty(key: String): Unit =
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//       }
      

// object Receiver {
//   val Label = "RECEIVER"
//   object Keys {
//     val All: JSet[String] = Set().asJava
//     val KeyToValue: Map[String, ReceiverDb => Any] = Map(
      
//     )
//   }

//   val Factory = new SpecializedElementFactory.ForEdge[ReceiverDb] {
//     override val forLabel = Receiver.Label

//     override def createEdge(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) =
//       new ReceiverDb(graph, id, outVertex, inVertex)

//     override def createEdgeRef(edge: ReceiverDb) = Receiver(edge)

//     override def createEdgeRef(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) = 
//       Receiver(id, graph)
//   }

//   def apply(wrapped: ReceiverDb) = new EdgeRef(wrapped) with Receiver
//   def apply(id: Long, graph: TinkerGraph) = 
//     new EdgeRef[ReceiverDb](id, Receiver.Label, graph) with Receiver
// }
//       trait Receiver extends EdgeRef[ReceiverDb]
//       class ReceiverDb(private val _graph: TinkerGraph, private val _id: Long, private val _outVertex: Vertex, _inVertex: Vertex)
//           extends SpecializedTinkerEdge(_graph, _id, _outVertex, Receiver.Label, _inVertex, Receiver.Keys.All) {

        
//         override protected def specificProperty[A](key: String): Property[A] =
//           Receiver.Keys.KeyToValue.get(key) match {
//             case None => Property.empty[A]
//             case Some(fieldAccess) => 
//               fieldAccess(this) match {
//                 case null | None => Property.empty[A]
//                 case Some(value) => new TinkerProperty(this, key, value.asInstanceOf[A])
//                 case value => new TinkerProperty(this, key, value.asInstanceOf[A])
//               }
//           }

//         override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//           property(key)
//         }

//         override protected def removeSpecificProperty(key: String): Unit =
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//       }
      

// object Ref {
//   val Label = "REF"
//   object Keys {
//     val All: JSet[String] = Set().asJava
//     val KeyToValue: Map[String, RefDb => Any] = Map(
      
//     )
//   }

//   val Factory = new SpecializedElementFactory.ForEdge[RefDb] {
//     override val forLabel = Ref.Label

//     override def createEdge(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) =
//       new RefDb(graph, id, outVertex, inVertex)

//     override def createEdgeRef(edge: RefDb) = Ref(edge)

//     override def createEdgeRef(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) = 
//       Ref(id, graph)
//   }

//   def apply(wrapped: RefDb) = new EdgeRef(wrapped) with Ref
//   def apply(id: Long, graph: TinkerGraph) = 
//     new EdgeRef[RefDb](id, Ref.Label, graph) with Ref
// }
//       trait Ref extends EdgeRef[RefDb]
//       class RefDb(private val _graph: TinkerGraph, private val _id: Long, private val _outVertex: Vertex, _inVertex: Vertex)
//           extends SpecializedTinkerEdge(_graph, _id, _outVertex, Ref.Label, _inVertex, Ref.Keys.All) {

        
//         override protected def specificProperty[A](key: String): Property[A] =
//           Ref.Keys.KeyToValue.get(key) match {
//             case None => Property.empty[A]
//             case Some(fieldAccess) => 
//               fieldAccess(this) match {
//                 case null | None => Property.empty[A]
//                 case Some(value) => new TinkerProperty(this, key, value.asInstanceOf[A])
//                 case value => new TinkerProperty(this, key, value.asInstanceOf[A])
//               }
//           }

//         override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//           property(key)
//         }

//         override protected def removeSpecificProperty(key: String): Unit =
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//       }
      

// object ResolvedTo {
//   val Label = "RESOLVED_TO"
//   object Keys {
//     val All: JSet[String] = Set().asJava
//     val KeyToValue: Map[String, ResolvedToDb => Any] = Map(
      
//     )
//   }

//   val Factory = new SpecializedElementFactory.ForEdge[ResolvedToDb] {
//     override val forLabel = ResolvedTo.Label

//     override def createEdge(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) =
//       new ResolvedToDb(graph, id, outVertex, inVertex)

//     override def createEdgeRef(edge: ResolvedToDb) = ResolvedTo(edge)

//     override def createEdgeRef(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) = 
//       ResolvedTo(id, graph)
//   }

//   def apply(wrapped: ResolvedToDb) = new EdgeRef(wrapped) with ResolvedTo
//   def apply(id: Long, graph: TinkerGraph) = 
//     new EdgeRef[ResolvedToDb](id, ResolvedTo.Label, graph) with ResolvedTo
// }
//       trait ResolvedTo extends EdgeRef[ResolvedToDb]
//       class ResolvedToDb(private val _graph: TinkerGraph, private val _id: Long, private val _outVertex: Vertex, _inVertex: Vertex)
//           extends SpecializedTinkerEdge(_graph, _id, _outVertex, ResolvedTo.Label, _inVertex, ResolvedTo.Keys.All) {

        
//         override protected def specificProperty[A](key: String): Property[A] =
//           ResolvedTo.Keys.KeyToValue.get(key) match {
//             case None => Property.empty[A]
//             case Some(fieldAccess) => 
//               fieldAccess(this) match {
//                 case null | None => Property.empty[A]
//                 case Some(value) => new TinkerProperty(this, key, value.asInstanceOf[A])
//                 case value => new TinkerProperty(this, key, value.asInstanceOf[A])
//               }
//           }

//         override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//           property(key)
//         }

//         override protected def removeSpecificProperty(key: String): Unit =
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//       }
      

// object TaggedBy {
//   val Label = "TAGGED_BY"
//   object Keys {
//     val All: JSet[String] = Set().asJava
//     val KeyToValue: Map[String, TaggedByDb => Any] = Map(
      
//     )
//   }

//   val Factory = new SpecializedElementFactory.ForEdge[TaggedByDb] {
//     override val forLabel = TaggedBy.Label

//     override def createEdge(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) =
//       new TaggedByDb(graph, id, outVertex, inVertex)

//     override def createEdgeRef(edge: TaggedByDb) = TaggedBy(edge)

//     override def createEdgeRef(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) = 
//       TaggedBy(id, graph)
//   }

//   def apply(wrapped: TaggedByDb) = new EdgeRef(wrapped) with TaggedBy
//   def apply(id: Long, graph: TinkerGraph) = 
//     new EdgeRef[TaggedByDb](id, TaggedBy.Label, graph) with TaggedBy
// }
//       trait TaggedBy extends EdgeRef[TaggedByDb]
//       class TaggedByDb(private val _graph: TinkerGraph, private val _id: Long, private val _outVertex: Vertex, _inVertex: Vertex)
//           extends SpecializedTinkerEdge(_graph, _id, _outVertex, TaggedBy.Label, _inVertex, TaggedBy.Keys.All) {

        
//         override protected def specificProperty[A](key: String): Property[A] =
//           TaggedBy.Keys.KeyToValue.get(key) match {
//             case None => Property.empty[A]
//             case Some(fieldAccess) => 
//               fieldAccess(this) match {
//                 case null | None => Property.empty[A]
//                 case Some(value) => new TinkerProperty(this, key, value.asInstanceOf[A])
//                 case value => new TinkerProperty(this, key, value.asInstanceOf[A])
//               }
//           }

//         override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//           property(key)
//         }

//         override protected def removeSpecificProperty(key: String): Unit =
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//       }
      

// object TaintRemove {
//   val Label = "TAINT_REMOVE"
//   object Keys {
//     val All: JSet[String] = Set().asJava
//     val KeyToValue: Map[String, TaintRemoveDb => Any] = Map(
      
//     )
//   }

//   val Factory = new SpecializedElementFactory.ForEdge[TaintRemoveDb] {
//     override val forLabel = TaintRemove.Label

//     override def createEdge(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) =
//       new TaintRemoveDb(graph, id, outVertex, inVertex)

//     override def createEdgeRef(edge: TaintRemoveDb) = TaintRemove(edge)

//     override def createEdgeRef(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) = 
//       TaintRemove(id, graph)
//   }

//   def apply(wrapped: TaintRemoveDb) = new EdgeRef(wrapped) with TaintRemove
//   def apply(id: Long, graph: TinkerGraph) = 
//     new EdgeRef[TaintRemoveDb](id, TaintRemove.Label, graph) with TaintRemove
// }
//       trait TaintRemove extends EdgeRef[TaintRemoveDb]
//       class TaintRemoveDb(private val _graph: TinkerGraph, private val _id: Long, private val _outVertex: Vertex, _inVertex: Vertex)
//           extends SpecializedTinkerEdge(_graph, _id, _outVertex, TaintRemove.Label, _inVertex, TaintRemove.Keys.All) {

        
//         override protected def specificProperty[A](key: String): Property[A] =
//           TaintRemove.Keys.KeyToValue.get(key) match {
//             case None => Property.empty[A]
//             case Some(fieldAccess) => 
//               fieldAccess(this) match {
//                 case null | None => Property.empty[A]
//                 case Some(value) => new TinkerProperty(this, key, value.asInstanceOf[A])
//                 case value => new TinkerProperty(this, key, value.asInstanceOf[A])
//               }
//           }

//         override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//           property(key)
//         }

//         override protected def removeSpecificProperty(key: String): Unit =
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//       }
      

// object Vtable {
//   val Label = "VTABLE"
//   object Keys {
//     val All: JSet[String] = Set().asJava
//     val KeyToValue: Map[String, VtableDb => Any] = Map(
      
//     )
//   }

//   val Factory = new SpecializedElementFactory.ForEdge[VtableDb] {
//     override val forLabel = Vtable.Label

//     override def createEdge(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) =
//       new VtableDb(graph, id, outVertex, inVertex)

//     override def createEdgeRef(edge: VtableDb) = Vtable(edge)

//     override def createEdgeRef(id: JLong, graph: TinkerGraph, outVertex: VertexRef[_ <: TinkerVertex], inVertex: VertexRef[_ <: TinkerVertex]) = 
//       Vtable(id, graph)
//   }

//   def apply(wrapped: VtableDb) = new EdgeRef(wrapped) with Vtable
//   def apply(id: Long, graph: TinkerGraph) = 
//     new EdgeRef[VtableDb](id, Vtable.Label, graph) with Vtable
// }
//       trait Vtable extends EdgeRef[VtableDb]
//       class VtableDb(private val _graph: TinkerGraph, private val _id: Long, private val _outVertex: Vertex, _inVertex: Vertex)
//           extends SpecializedTinkerEdge(_graph, _id, _outVertex, Vtable.Label, _inVertex, Vtable.Keys.All) {

        
//         override protected def specificProperty[A](key: String): Property[A] =
//           Vtable.Keys.KeyToValue.get(key) match {
//             case None => Property.empty[A]
//             case Some(fieldAccess) => 
//               fieldAccess(this) match {
//                 case null | None => Property.empty[A]
//                 case Some(value) => new TinkerProperty(this, key, value.asInstanceOf[A])
//                 case value => new TinkerProperty(this, key, value.asInstanceOf[A])
//               }
//           }

//         override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//           property(key)
//         }

//         override protected def removeSpecificProperty(key: String): Unit =
//           throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
//       }
      
