
      package io.shiftleft.codepropertygraph.generated.edges

      import java.lang.{Boolean => JBoolean, Long => JLong}
      import java.util.{Map => JMap, Set => JSet}
      import org.apache.tinkerpop.gremlin.structure.Property
      import org.apache.tinkerpop.gremlin.structure.Vertex
      import org.apache.tinkerpop.gremlin.structure.VertexProperty
      import org.apache.tinkerpop.gremlin.tinkergraph.structure.SpecializedElementFactory
      import org.apache.tinkerpop.gremlin.tinkergraph.structure.SpecializedTinkerEdge
      import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph
      import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerProperty
      import scala.collection.JavaConverters._
      
        object Factories {
          lazy val All: List[SpecializedElementFactory.ForEdge[_, _]] = List(Ast.Factory, BindsTo.Factory, Call.Factory, CallArg.Factory, CallArgOut.Factory, CallRet.Factory, Capture.Factory, CapturedBy.Factory, Cfg.Factory, Contains.Factory, ContainsNode.Factory, EvalType.Factory, InheritsFrom.Factory, ParameterLink.Factory, Ref.Factory, TaggedBy.Factory, Vtable.Factory)
          lazy val AllAsJava: java.util.List[SpecializedElementFactory.ForEdge[_, _]] = All.asJava
        }
        

      object Ast {
        val Label = "AST"
        object Keys {
          val All: JSet[String] = Set().asJava
          val KeyToValue: Map[String, Ast => Any] = Map(
            
          )
        }

        val Factory = new SpecializedElementFactory.ForEdge[Ast, JLong] {
          override val forLabel = Ast.Label

          override def createEdge(id: JLong, outVertex: Vertex, inVertex: Vertex) = 
            new Ast(id, outVertex, inVertex)
        }
      }
      
      class Ast(private val _id: JLong, private val _outVertex: Vertex, _inVertex: Vertex)
          extends SpecializedTinkerEdge[JLong](_id, _outVertex, Ast.Label, _inVertex, Ast.Keys.All) {

        
        override protected def specificProperty[A](key: String): Property[A] =
          Ast.Keys.KeyToValue.get(key) match {
            case None => Property.empty[A]
            case Some(fieldAccess) => 
              val value = fieldAccess(this)
              if (value == null) Property.empty[A]
              else new TinkerProperty(this, key, value.asInstanceOf[A])
          }

        override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
          throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
          property(key)
        }
      }
      

      object BindsTo {
        val Label = "BINDS_TO"
        object Keys {
          val All: JSet[String] = Set().asJava
          val KeyToValue: Map[String, BindsTo => Any] = Map(
            
          )
        }

        val Factory = new SpecializedElementFactory.ForEdge[BindsTo, JLong] {
          override val forLabel = BindsTo.Label

          override def createEdge(id: JLong, outVertex: Vertex, inVertex: Vertex) = 
            new BindsTo(id, outVertex, inVertex)
        }
      }
      
      class BindsTo(private val _id: JLong, private val _outVertex: Vertex, _inVertex: Vertex)
          extends SpecializedTinkerEdge[JLong](_id, _outVertex, BindsTo.Label, _inVertex, BindsTo.Keys.All) {

        
        override protected def specificProperty[A](key: String): Property[A] =
          BindsTo.Keys.KeyToValue.get(key) match {
            case None => Property.empty[A]
            case Some(fieldAccess) => 
              val value = fieldAccess(this)
              if (value == null) Property.empty[A]
              else new TinkerProperty(this, key, value.asInstanceOf[A])
          }

        override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
          throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
          property(key)
        }
      }
      

      object Call {
        val Label = "CALL"
        object Keys {
          val All: JSet[String] = Set().asJava
          val KeyToValue: Map[String, Call => Any] = Map(
            
          )
        }

        val Factory = new SpecializedElementFactory.ForEdge[Call, JLong] {
          override val forLabel = Call.Label

          override def createEdge(id: JLong, outVertex: Vertex, inVertex: Vertex) = 
            new Call(id, outVertex, inVertex)
        }
      }
      
      class Call(private val _id: JLong, private val _outVertex: Vertex, _inVertex: Vertex)
          extends SpecializedTinkerEdge[JLong](_id, _outVertex, Call.Label, _inVertex, Call.Keys.All) {

        
        override protected def specificProperty[A](key: String): Property[A] =
          Call.Keys.KeyToValue.get(key) match {
            case None => Property.empty[A]
            case Some(fieldAccess) => 
              val value = fieldAccess(this)
              if (value == null) Property.empty[A]
              else new TinkerProperty(this, key, value.asInstanceOf[A])
          }

        override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
          throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
          property(key)
        }
      }
      

      object CallArg {
        val Label = "CALL_ARG"
        object Keys {
          val All: JSet[String] = Set().asJava
          val KeyToValue: Map[String, CallArg => Any] = Map(
            
          )
        }

        val Factory = new SpecializedElementFactory.ForEdge[CallArg, JLong] {
          override val forLabel = CallArg.Label

          override def createEdge(id: JLong, outVertex: Vertex, inVertex: Vertex) = 
            new CallArg(id, outVertex, inVertex)
        }
      }
      
      class CallArg(private val _id: JLong, private val _outVertex: Vertex, _inVertex: Vertex)
          extends SpecializedTinkerEdge[JLong](_id, _outVertex, CallArg.Label, _inVertex, CallArg.Keys.All) {

        
        override protected def specificProperty[A](key: String): Property[A] =
          CallArg.Keys.KeyToValue.get(key) match {
            case None => Property.empty[A]
            case Some(fieldAccess) => 
              val value = fieldAccess(this)
              if (value == null) Property.empty[A]
              else new TinkerProperty(this, key, value.asInstanceOf[A])
          }

        override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
          throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
          property(key)
        }
      }
      

      object CallArgOut {
        val Label = "CALL_ARG_OUT"
        object Keys {
          val All: JSet[String] = Set().asJava
          val KeyToValue: Map[String, CallArgOut => Any] = Map(
            
          )
        }

        val Factory = new SpecializedElementFactory.ForEdge[CallArgOut, JLong] {
          override val forLabel = CallArgOut.Label

          override def createEdge(id: JLong, outVertex: Vertex, inVertex: Vertex) = 
            new CallArgOut(id, outVertex, inVertex)
        }
      }
      
      class CallArgOut(private val _id: JLong, private val _outVertex: Vertex, _inVertex: Vertex)
          extends SpecializedTinkerEdge[JLong](_id, _outVertex, CallArgOut.Label, _inVertex, CallArgOut.Keys.All) {

        
        override protected def specificProperty[A](key: String): Property[A] =
          CallArgOut.Keys.KeyToValue.get(key) match {
            case None => Property.empty[A]
            case Some(fieldAccess) => 
              val value = fieldAccess(this)
              if (value == null) Property.empty[A]
              else new TinkerProperty(this, key, value.asInstanceOf[A])
          }

        override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
          throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
          property(key)
        }
      }
      

      object CallRet {
        val Label = "CALL_RET"
        object Keys {
          val All: JSet[String] = Set().asJava
          val KeyToValue: Map[String, CallRet => Any] = Map(
            
          )
        }

        val Factory = new SpecializedElementFactory.ForEdge[CallRet, JLong] {
          override val forLabel = CallRet.Label

          override def createEdge(id: JLong, outVertex: Vertex, inVertex: Vertex) = 
            new CallRet(id, outVertex, inVertex)
        }
      }
      
      class CallRet(private val _id: JLong, private val _outVertex: Vertex, _inVertex: Vertex)
          extends SpecializedTinkerEdge[JLong](_id, _outVertex, CallRet.Label, _inVertex, CallRet.Keys.All) {

        
        override protected def specificProperty[A](key: String): Property[A] =
          CallRet.Keys.KeyToValue.get(key) match {
            case None => Property.empty[A]
            case Some(fieldAccess) => 
              val value = fieldAccess(this)
              if (value == null) Property.empty[A]
              else new TinkerProperty(this, key, value.asInstanceOf[A])
          }

        override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
          throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
          property(key)
        }
      }
      

      object Capture {
        val Label = "CAPTURE"
        object Keys {
          val All: JSet[String] = Set().asJava
          val KeyToValue: Map[String, Capture => Any] = Map(
            
          )
        }

        val Factory = new SpecializedElementFactory.ForEdge[Capture, JLong] {
          override val forLabel = Capture.Label

          override def createEdge(id: JLong, outVertex: Vertex, inVertex: Vertex) = 
            new Capture(id, outVertex, inVertex)
        }
      }
      
      class Capture(private val _id: JLong, private val _outVertex: Vertex, _inVertex: Vertex)
          extends SpecializedTinkerEdge[JLong](_id, _outVertex, Capture.Label, _inVertex, Capture.Keys.All) {

        
        override protected def specificProperty[A](key: String): Property[A] =
          Capture.Keys.KeyToValue.get(key) match {
            case None => Property.empty[A]
            case Some(fieldAccess) => 
              val value = fieldAccess(this)
              if (value == null) Property.empty[A]
              else new TinkerProperty(this, key, value.asInstanceOf[A])
          }

        override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
          throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
          property(key)
        }
      }
      

      object CapturedBy {
        val Label = "CAPTURED_BY"
        object Keys {
          val All: JSet[String] = Set().asJava
          val KeyToValue: Map[String, CapturedBy => Any] = Map(
            
          )
        }

        val Factory = new SpecializedElementFactory.ForEdge[CapturedBy, JLong] {
          override val forLabel = CapturedBy.Label

          override def createEdge(id: JLong, outVertex: Vertex, inVertex: Vertex) = 
            new CapturedBy(id, outVertex, inVertex)
        }
      }
      
      class CapturedBy(private val _id: JLong, private val _outVertex: Vertex, _inVertex: Vertex)
          extends SpecializedTinkerEdge[JLong](_id, _outVertex, CapturedBy.Label, _inVertex, CapturedBy.Keys.All) {

        
        override protected def specificProperty[A](key: String): Property[A] =
          CapturedBy.Keys.KeyToValue.get(key) match {
            case None => Property.empty[A]
            case Some(fieldAccess) => 
              val value = fieldAccess(this)
              if (value == null) Property.empty[A]
              else new TinkerProperty(this, key, value.asInstanceOf[A])
          }

        override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
          throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
          property(key)
        }
      }
      

      object Cfg {
        val Label = "CFG"
        object Keys {
          val All: JSet[String] = Set().asJava
          val KeyToValue: Map[String, Cfg => Any] = Map(
            
          )
        }

        val Factory = new SpecializedElementFactory.ForEdge[Cfg, JLong] {
          override val forLabel = Cfg.Label

          override def createEdge(id: JLong, outVertex: Vertex, inVertex: Vertex) = 
            new Cfg(id, outVertex, inVertex)
        }
      }
      
      class Cfg(private val _id: JLong, private val _outVertex: Vertex, _inVertex: Vertex)
          extends SpecializedTinkerEdge[JLong](_id, _outVertex, Cfg.Label, _inVertex, Cfg.Keys.All) {

        
        override protected def specificProperty[A](key: String): Property[A] =
          Cfg.Keys.KeyToValue.get(key) match {
            case None => Property.empty[A]
            case Some(fieldAccess) => 
              val value = fieldAccess(this)
              if (value == null) Property.empty[A]
              else new TinkerProperty(this, key, value.asInstanceOf[A])
          }

        override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
          throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
          property(key)
        }
      }
      

      object Contains {
        val Label = "CONTAINS"
        object Keys {
          val All: JSet[String] = Set("LOCAL_NAME", "INDEX").asJava
          val KeyToValue: Map[String, Contains => Any] = Map(
             "LOCAL_NAME" -> { instance: Contains => instance.localName},
 "INDEX" -> { instance: Contains => instance.index}
          )
        }

        val Factory = new SpecializedElementFactory.ForEdge[Contains, JLong] {
          override val forLabel = Contains.Label

          override def createEdge(id: JLong, outVertex: Vertex, inVertex: Vertex) = 
            new Contains(id, outVertex, inVertex)
        }
      }
      
      class Contains(private val _id: JLong, private val _outVertex: Vertex, _inVertex: Vertex)
          extends SpecializedTinkerEdge[JLong](_id, _outVertex, Contains.Label, _inVertex, Contains.Keys.All) {

        var localName: Option[String] = None

var index: Option[Integer] = None
        override protected def specificProperty[A](key: String): Property[A] =
          Contains.Keys.KeyToValue.get(key) match {
            case None => Property.empty[A]
            case Some(fieldAccess) => 
              val value = fieldAccess(this)
              if (value == null) Property.empty[A]
              else new TinkerProperty(this, key, value.asInstanceOf[A])
          }

        override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
           if (key == "LOCAL_NAME") this.localName = Option(value).asInstanceOf[Option[String]] 
 else  if (key == "INDEX") this.index = Option(value).asInstanceOf[Option[Integer]] 
 else throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
          property(key)
        }
      }
      

      object ContainsNode {
        val Label = "CONTAINS_NODE"
        object Keys {
          val All: JSet[String] = Set("LOCAL_NAME", "INDEX").asJava
          val KeyToValue: Map[String, ContainsNode => Any] = Map(
             "LOCAL_NAME" -> { instance: ContainsNode => instance.localName},
 "INDEX" -> { instance: ContainsNode => instance.index}
          )
        }

        val Factory = new SpecializedElementFactory.ForEdge[ContainsNode, JLong] {
          override val forLabel = ContainsNode.Label

          override def createEdge(id: JLong, outVertex: Vertex, inVertex: Vertex) = 
            new ContainsNode(id, outVertex, inVertex)
        }
      }
      
      class ContainsNode(private val _id: JLong, private val _outVertex: Vertex, _inVertex: Vertex)
          extends SpecializedTinkerEdge[JLong](_id, _outVertex, ContainsNode.Label, _inVertex, ContainsNode.Keys.All) {

        var localName: Option[String] = None

var index: Option[Integer] = None
        override protected def specificProperty[A](key: String): Property[A] =
          ContainsNode.Keys.KeyToValue.get(key) match {
            case None => Property.empty[A]
            case Some(fieldAccess) => 
              val value = fieldAccess(this)
              if (value == null) Property.empty[A]
              else new TinkerProperty(this, key, value.asInstanceOf[A])
          }

        override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
           if (key == "LOCAL_NAME") this.localName = Option(value).asInstanceOf[Option[String]] 
 else  if (key == "INDEX") this.index = Option(value).asInstanceOf[Option[Integer]] 
 else throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
          property(key)
        }
      }
      

      object EvalType {
        val Label = "EVAL_TYPE"
        object Keys {
          val All: JSet[String] = Set().asJava
          val KeyToValue: Map[String, EvalType => Any] = Map(
            
          )
        }

        val Factory = new SpecializedElementFactory.ForEdge[EvalType, JLong] {
          override val forLabel = EvalType.Label

          override def createEdge(id: JLong, outVertex: Vertex, inVertex: Vertex) = 
            new EvalType(id, outVertex, inVertex)
        }
      }
      
      class EvalType(private val _id: JLong, private val _outVertex: Vertex, _inVertex: Vertex)
          extends SpecializedTinkerEdge[JLong](_id, _outVertex, EvalType.Label, _inVertex, EvalType.Keys.All) {

        
        override protected def specificProperty[A](key: String): Property[A] =
          EvalType.Keys.KeyToValue.get(key) match {
            case None => Property.empty[A]
            case Some(fieldAccess) => 
              val value = fieldAccess(this)
              if (value == null) Property.empty[A]
              else new TinkerProperty(this, key, value.asInstanceOf[A])
          }

        override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
          throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
          property(key)
        }
      }
      

      object InheritsFrom {
        val Label = "INHERITS_FROM"
        object Keys {
          val All: JSet[String] = Set().asJava
          val KeyToValue: Map[String, InheritsFrom => Any] = Map(
            
          )
        }

        val Factory = new SpecializedElementFactory.ForEdge[InheritsFrom, JLong] {
          override val forLabel = InheritsFrom.Label

          override def createEdge(id: JLong, outVertex: Vertex, inVertex: Vertex) = 
            new InheritsFrom(id, outVertex, inVertex)
        }
      }
      
      class InheritsFrom(private val _id: JLong, private val _outVertex: Vertex, _inVertex: Vertex)
          extends SpecializedTinkerEdge[JLong](_id, _outVertex, InheritsFrom.Label, _inVertex, InheritsFrom.Keys.All) {

        
        override protected def specificProperty[A](key: String): Property[A] =
          InheritsFrom.Keys.KeyToValue.get(key) match {
            case None => Property.empty[A]
            case Some(fieldAccess) => 
              val value = fieldAccess(this)
              if (value == null) Property.empty[A]
              else new TinkerProperty(this, key, value.asInstanceOf[A])
          }

        override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
          throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
          property(key)
        }
      }
      

      object ParameterLink {
        val Label = "PARAMETER_LINK"
        object Keys {
          val All: JSet[String] = Set().asJava
          val KeyToValue: Map[String, ParameterLink => Any] = Map(
            
          )
        }

        val Factory = new SpecializedElementFactory.ForEdge[ParameterLink, JLong] {
          override val forLabel = ParameterLink.Label

          override def createEdge(id: JLong, outVertex: Vertex, inVertex: Vertex) = 
            new ParameterLink(id, outVertex, inVertex)
        }
      }
      
      class ParameterLink(private val _id: JLong, private val _outVertex: Vertex, _inVertex: Vertex)
          extends SpecializedTinkerEdge[JLong](_id, _outVertex, ParameterLink.Label, _inVertex, ParameterLink.Keys.All) {

        
        override protected def specificProperty[A](key: String): Property[A] =
          ParameterLink.Keys.KeyToValue.get(key) match {
            case None => Property.empty[A]
            case Some(fieldAccess) => 
              val value = fieldAccess(this)
              if (value == null) Property.empty[A]
              else new TinkerProperty(this, key, value.asInstanceOf[A])
          }

        override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
          throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
          property(key)
        }
      }
      

      object Ref {
        val Label = "REF"
        object Keys {
          val All: JSet[String] = Set().asJava
          val KeyToValue: Map[String, Ref => Any] = Map(
            
          )
        }

        val Factory = new SpecializedElementFactory.ForEdge[Ref, JLong] {
          override val forLabel = Ref.Label

          override def createEdge(id: JLong, outVertex: Vertex, inVertex: Vertex) = 
            new Ref(id, outVertex, inVertex)
        }
      }
      
      class Ref(private val _id: JLong, private val _outVertex: Vertex, _inVertex: Vertex)
          extends SpecializedTinkerEdge[JLong](_id, _outVertex, Ref.Label, _inVertex, Ref.Keys.All) {

        
        override protected def specificProperty[A](key: String): Property[A] =
          Ref.Keys.KeyToValue.get(key) match {
            case None => Property.empty[A]
            case Some(fieldAccess) => 
              val value = fieldAccess(this)
              if (value == null) Property.empty[A]
              else new TinkerProperty(this, key, value.asInstanceOf[A])
          }

        override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
          throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
          property(key)
        }
      }
      

      object TaggedBy {
        val Label = "TAGGED_BY"
        object Keys {
          val All: JSet[String] = Set().asJava
          val KeyToValue: Map[String, TaggedBy => Any] = Map(
            
          )
        }

        val Factory = new SpecializedElementFactory.ForEdge[TaggedBy, JLong] {
          override val forLabel = TaggedBy.Label

          override def createEdge(id: JLong, outVertex: Vertex, inVertex: Vertex) = 
            new TaggedBy(id, outVertex, inVertex)
        }
      }
      
      class TaggedBy(private val _id: JLong, private val _outVertex: Vertex, _inVertex: Vertex)
          extends SpecializedTinkerEdge[JLong](_id, _outVertex, TaggedBy.Label, _inVertex, TaggedBy.Keys.All) {

        
        override protected def specificProperty[A](key: String): Property[A] =
          TaggedBy.Keys.KeyToValue.get(key) match {
            case None => Property.empty[A]
            case Some(fieldAccess) => 
              val value = fieldAccess(this)
              if (value == null) Property.empty[A]
              else new TinkerProperty(this, key, value.asInstanceOf[A])
          }

        override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
          throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
          property(key)
        }
      }
      

      object Vtable {
        val Label = "VTABLE"
        object Keys {
          val All: JSet[String] = Set().asJava
          val KeyToValue: Map[String, Vtable => Any] = Map(
            
          )
        }

        val Factory = new SpecializedElementFactory.ForEdge[Vtable, JLong] {
          override val forLabel = Vtable.Label

          override def createEdge(id: JLong, outVertex: Vertex, inVertex: Vertex) = 
            new Vtable(id, outVertex, inVertex)
        }
      }
      
      class Vtable(private val _id: JLong, private val _outVertex: Vertex, _inVertex: Vertex)
          extends SpecializedTinkerEdge[JLong](_id, _outVertex, Vtable.Label, _inVertex, Vtable.Keys.All) {

        
        override protected def specificProperty[A](key: String): Property[A] =
          Vtable.Keys.KeyToValue.get(key) match {
            case None => Property.empty[A]
            case Some(fieldAccess) => 
              val value = fieldAccess(this)
              if (value == null) Property.empty[A]
              else new TinkerProperty(this, key, value.asInstanceOf[A])
          }

        override protected def updateSpecificProperty[A](key: String, value: A): Property[A] = {
          throw new RuntimeException("property with key=" + key + " not (yet) supported by " + this.getClass.getName + ". You may want to add it to cpg.json")
          property(key)
        }
      }
      
