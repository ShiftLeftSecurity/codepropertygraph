package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object ClosureBinding {
  def apply(graph: Graph, id: Long) = new ClosureBinding(graph, id)

  val Label = "CLOSURE_BINDING"

  object PropertyNames {
    val ClosureBindingId = "CLOSURE_BINDING_ID" 
    val ClosureOriginalName = "CLOSURE_ORIGINAL_NAME" 
    val EvaluationStrategy = "EVALUATION_STRATEGY" 
    val all: Set[String] = Set(ClosureBindingId, ClosureOriginalName, EvaluationStrategy)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    val ClosureBindingId = new PropertyKey[String]("CLOSURE_BINDING_ID") 
    val ClosureOriginalName = new PropertyKey[String]("CLOSURE_ORIGINAL_NAME") 
    val EvaluationStrategy = new PropertyKey[String]("EVALUATION_STRATEGY") 
    
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(edges.Ref.layoutInformation).asJava,
    List(edges.CapturedBy.layoutInformation, edges.Capture.layoutInformation).asJava)


  object Edges {
    val Out: Array[String] = Array("REF")
    val In: Array[String] = Array("CAPTURE","CAPTURED_BY")
  }

  val factory = new NodeFactory[ClosureBindingDb] {
    override val forLabel = ClosureBinding.Label

    override def createNode(ref: NodeRef[ClosureBindingDb]) =
      new ClosureBindingDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = ClosureBinding(graph, id)
  }
}

trait ClosureBindingBase extends CpgNode  with HasClosureBindingId with HasClosureOriginalName with HasEvaluationStrategy {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  
}

class ClosureBinding(graph: Graph, id: Long) extends NodeRef[ClosureBindingDb](graph, id)
  with ClosureBindingBase
  with StoredNode
   {
    override def closureBindingId: Option[String] = get().closureBindingId
  override def closureOriginalName: Option[String] = get().closureOriginalName
  override def evaluationStrategy: String = get().evaluationStrategy
  
  def _methodParameterInViaRefOut: Iterator[MethodParameterIn] = get()._methodParameterInViaRefOut
def _localViaRefOut: Local = get()._localViaRefOut
override def _refOut: JIterator[StoredNode] = get()._refOut
def _localViaCapturedByIn: Iterator[Local] = get()._localViaCapturedByIn
override def _capturedByIn: JIterator[StoredNode] = get()._capturedByIn
def _methodRefViaCaptureIn: Iterator[MethodRef] = get()._methodRefViaCaptureIn
def _typeRefViaCaptureIn: Iterator[TypeRef] = get()._typeRefViaCaptureIn
override def _captureIn: JIterator[StoredNode] = get()._captureIn
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    ClosureBinding.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "closureBindingId" 
case 2 => "closureOriginalName" 
case 3 => "evaluationStrategy" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => closureBindingId
case 2 => closureOriginalName
case 3 => evaluationStrategy
    }

  override def productPrefix = "ClosureBinding"
  override def productArity = 4
}

class ClosureBindingDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
   with ClosureBindingBase {

  override def layoutInformation: NodeLayoutInformation = ClosureBinding.layoutInformation

private var _closureBindingId: Option[String] = None
def closureBindingId: Option[String] = _closureBindingId

private var _closureOriginalName: Option[String] = None
def closureOriginalName: Option[String] = _closureOriginalName

private var _evaluationStrategy: String = null
def evaluationStrategy: String = _evaluationStrategy


  /* all properties */
  override def valueMap: JMap[String, AnyRef] =  {
  val properties = new JHashMap[String, AnyRef]
closureBindingId.map { value => properties.put("CLOSURE_BINDING_ID", value) }
closureOriginalName.map { value => properties.put("CLOSURE_ORIGINAL_NAME", value) }
if (evaluationStrategy != null) { properties.put("EVALUATION_STRATEGY", evaluationStrategy) }

  properties
}

  def _methodParameterInViaRefOut: Iterator[MethodParameterIn] = _refOut.asScala.collect { case node: MethodParameterIn => node }
def _localViaRefOut: Local = _refOut.asScala.collect { case node: Local => node }.next()
override def _refOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(0).asInstanceOf[JIterator[StoredNode]]
def _localViaCapturedByIn: Iterator[Local] = _capturedByIn.asScala.collect { case node: Local => node }
override def _capturedByIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(1).asInstanceOf[JIterator[StoredNode]]
def _methodRefViaCaptureIn: Iterator[MethodRef] = _captureIn.asScala.collect { case node: MethodRef => node }
def _typeRefViaCaptureIn: Iterator[TypeRef] = _captureIn.asScala.collect { case node: TypeRef => node }
override def _captureIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(2).asInstanceOf[JIterator[StoredNode]]

  override def label: String = {
    ClosureBinding.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "closureBindingId" 
case 2 => "closureOriginalName" 
case 3 => "evaluationStrategy" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => closureBindingId
case 2 => closureOriginalName
case 3 => evaluationStrategy
    }

  override def productPrefix = "ClosureBinding"
  override def productArity = 4

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[ClosureBindingDb]

  override def property(key:String): AnyRef = {
    key match {
      case "CLOSURE_BINDING_ID" => this._closureBindingId.orNull
      case "CLOSURE_ORIGINAL_NAME" => this._closureOriginalName.orNull
      case "EVALUATION_STRATEGY" => this._evaluationStrategy
      
      case _ => null
    }
  }

  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
    key match {
      case "CLOSURE_BINDING_ID" => this._closureBindingId = value match {
        case null | None => None
        case someVal: String => Some(someVal)
      }
      case "CLOSURE_ORIGINAL_NAME" => this._closureOriginalName = value match {
        case null | None => None
        case someVal: String => Some(someVal)
      }
      case "EVALUATION_STRATEGY" => this._evaluationStrategy = value.asInstanceOf[String]
    
      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

override def removeSpecificProperty(key: String): Unit =
  this.updateSpecificProperty(key, null)

override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
  //this will throw for bad types -- no need to check by hand, we don't have a better error message
  val other = someNewNode.asInstanceOf[NewClosureBinding]
   this._closureBindingId = if(other.closureBindingId != null) other.closureBindingId else None
   this._closureOriginalName = if(other.closureOriginalName != null) other.closureOriginalName else None
   this._evaluationStrategy = other.evaluationStrategy


}

}

/** Traversal steps for ClosureBinding */
class ClosureBindingTraversal[NodeType <: ClosureBinding](val traversal: Traversal[NodeType]) extends AnyVal {

  /** Traverse to closureBindingId property */
  def closureBindingId: Traversal[String] =
    traversal.flatMap(_.closureBindingId)

    /**
    * Traverse to nodes where the closureBindingId matches the regular expression `value`
    * */
  def closureBindingId(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.closureBindingId.isDefined && node.closureBindingId.get == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node => node.closureBindingId.isDefined && {matcher.reset(node.closureBindingId.get); matcher.matches()}}
    }
  }

  /**
    * Traverse to nodes where the closureBindingId matches at least one of the regular expressions in `values`
    * */
  def closureBindingId(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => node.closureBindingId.isDefined && matchers.exists{ matcher => matcher.reset(node.closureBindingId.get); matcher.matches()}}
   }

  /**
    * Traverse to nodes where closureBindingId matches `value` exactly.
    * */
  def closureBindingIdExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.closureBindingId.isDefined && node.closureBindingId.get == value}

  /**
    * Traverse to nodes where closureBindingId matches one of the elements in `values` exactly.
    * */
  def closureBindingIdExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => node.closureBindingId.isDefined && vset.contains(node.closureBindingId.get)}
  }

  /**
    * Traverse to nodes where closureBindingId does not match the regular expression `value`.
    * */
  def closureBindingIdNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.closureBindingId.isEmpty || node.closureBindingId.get != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node => node.closureBindingId.isEmpty || {matcher.reset(node.closureBindingId.get); !matcher.matches()}}
    }
  }

  /**
    * Traverse to nodes where closureBindingId does not match any of the regular expressions in `values`.
    * */
  def closureBindingIdNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => node.closureBindingId.isEmpty || !matchers.exists{ matcher => matcher.reset(node.closureBindingId.get); matcher.matches()}}
   }



  /** Traverse to closureOriginalName property */
  def closureOriginalName: Traversal[String] =
    traversal.flatMap(_.closureOriginalName)

    /**
    * Traverse to nodes where the closureOriginalName matches the regular expression `value`
    * */
  def closureOriginalName(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.closureOriginalName.isDefined && node.closureOriginalName.get == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node => node.closureOriginalName.isDefined && {matcher.reset(node.closureOriginalName.get); matcher.matches()}}
    }
  }

  /**
    * Traverse to nodes where the closureOriginalName matches at least one of the regular expressions in `values`
    * */
  def closureOriginalName(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => node.closureOriginalName.isDefined && matchers.exists{ matcher => matcher.reset(node.closureOriginalName.get); matcher.matches()}}
   }

  /**
    * Traverse to nodes where closureOriginalName matches `value` exactly.
    * */
  def closureOriginalNameExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.closureOriginalName.isDefined && node.closureOriginalName.get == value}

  /**
    * Traverse to nodes where closureOriginalName matches one of the elements in `values` exactly.
    * */
  def closureOriginalNameExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => node.closureOriginalName.isDefined && vset.contains(node.closureOriginalName.get)}
  }

  /**
    * Traverse to nodes where closureOriginalName does not match the regular expression `value`.
    * */
  def closureOriginalNameNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.closureOriginalName.isEmpty || node.closureOriginalName.get != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node => node.closureOriginalName.isEmpty || {matcher.reset(node.closureOriginalName.get); !matcher.matches()}}
    }
  }

  /**
    * Traverse to nodes where closureOriginalName does not match any of the regular expressions in `values`.
    * */
  def closureOriginalNameNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => node.closureOriginalName.isEmpty || !matchers.exists{ matcher => matcher.reset(node.closureOriginalName.get); matcher.matches()}}
   }



  /** Traverse to evaluationStrategy property */
  def evaluationStrategy: Traversal[String] =
    traversal.map(_.evaluationStrategy)

    /**
    * Traverse to nodes where the evaluationStrategy matches the regular expression `value`
    * */
  def evaluationStrategy(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.evaluationStrategy == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.evaluationStrategy); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the evaluationStrategy matches at least one of the regular expressions in `values`
    * */
  def evaluationStrategy(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.evaluationStrategy); matcher.matches()}}
   }

  /**
    * Traverse to nodes where evaluationStrategy matches `value` exactly.
    * */
  def evaluationStrategyExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.evaluationStrategy == value}

  /**
    * Traverse to nodes where evaluationStrategy matches one of the elements in `values` exactly.
    * */
  def evaluationStrategyExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.evaluationStrategy)}
  }

  /**
    * Traverse to nodes where evaluationStrategy does not match the regular expression `value`.
    * */
  def evaluationStrategyNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.evaluationStrategy != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.evaluationStrategy); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where evaluationStrategy does not match any of the regular expressions in `values`.
    * */
  def evaluationStrategyNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.evaluationStrategy); matcher.matches()}}
   }




}
