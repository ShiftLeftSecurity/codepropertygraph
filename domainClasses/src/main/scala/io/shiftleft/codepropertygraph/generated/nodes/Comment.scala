package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object Comment {
  def apply(graph: Graph, id: Long) = new Comment(graph, id)

  val Label = "COMMENT"

  object PropertyNames {
    val Code = "CODE" 
    val Filename = "FILENAME" 
    val LineNumber = "LINE_NUMBER" 
    val all: Set[String] = Set(Code, Filename, LineNumber)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    val Code = new PropertyKey[String]("CODE") 
    val Filename = new PropertyKey[String]("FILENAME") 
    val LineNumber = new PropertyKey[java.lang.Integer]("LINE_NUMBER") 
    
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(edges.SourceFile.layoutInformation).asJava,
    List(edges.Ast.layoutInformation, edges.SourceFile.layoutInformation).asJava)


  object Edges {
    val Out: Array[String] = Array("SOURCE_FILE")
    val In: Array[String] = Array("AST","SOURCE_FILE")
  }

  val factory = new NodeFactory[CommentDb] {
    override val forLabel = Comment.Label

    override def createNode(ref: NodeRef[CommentDb]) =
      new CommentDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = Comment(graph, id)
  }
}

trait CommentBase extends CpgNode  with HasCode with HasFilename with HasLineNumber {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  
}

class Comment(graph: Graph, id: Long) extends NodeRef[CommentDb](graph, id)
  with CommentBase
  with StoredNode
   {
    override def code: String = get().code
  override def filename: String = get().filename
  override def lineNumber: Option[java.lang.Integer] = get().lineNumber
  
  def _commentViaSourceFileOut: Iterator[Comment] = get()._commentViaSourceFileOut
override def _sourceFileOut: JIterator[StoredNode] = get()._sourceFileOut
def _fileViaAstIn: Iterator[File] = get()._fileViaAstIn
override def _astIn: JIterator[StoredNode] = get()._astIn
def _commentViaSourceFileIn: Iterator[Comment] = get()._commentViaSourceFileIn
override def _sourceFileIn: JIterator[StoredNode] = get()._sourceFileIn
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    Comment.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "code" 
case 2 => "filename" 
case 3 => "lineNumber" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => code
case 2 => filename
case 3 => lineNumber
    }

  override def productPrefix = "Comment"
  override def productArity = 4
}

class CommentDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
   with CommentBase {

  override def layoutInformation: NodeLayoutInformation = Comment.layoutInformation

private var _code: String = null
def code: String = _code

private var _filename: String = null
def filename: String = _filename

private var _lineNumber: Option[java.lang.Integer] = None
def lineNumber: Option[java.lang.Integer] = _lineNumber


  /* all properties */
  override def valueMap: JMap[String, AnyRef] =  {
  val properties = new JHashMap[String, AnyRef]
if (code != null) { properties.put("CODE", code) }
if (filename != null) { properties.put("FILENAME", filename) }
lineNumber.map { value => properties.put("LINE_NUMBER", value) }

  properties
}

  def _commentViaSourceFileOut: Iterator[Comment] = _sourceFileOut.asScala.collect { case node: Comment => node }
override def _sourceFileOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(0).asInstanceOf[JIterator[StoredNode]]
def _fileViaAstIn: Iterator[File] = _astIn.asScala.collect { case node: File => node }
override def _astIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(1).asInstanceOf[JIterator[StoredNode]]
def _commentViaSourceFileIn: Iterator[Comment] = _sourceFileIn.asScala.collect { case node: Comment => node }
override def _sourceFileIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(2).asInstanceOf[JIterator[StoredNode]]

  override def label: String = {
    Comment.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "code" 
case 2 => "filename" 
case 3 => "lineNumber" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => code
case 2 => filename
case 3 => lineNumber
    }

  override def productPrefix = "Comment"
  override def productArity = 4

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[CommentDb]

  override def property(key:String): AnyRef = {
    key match {
      case "CODE" => this._code
      case "FILENAME" => this._filename
      case "LINE_NUMBER" => this._lineNumber.orNull
      
      case _ => null
    }
  }

  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
    key match {
      case "CODE" => this._code = value.asInstanceOf[String]
      case "FILENAME" => this._filename = value.asInstanceOf[String]
      case "LINE_NUMBER" => this._lineNumber = value match {
        case null | None => None
        case someVal: java.lang.Integer => Some(someVal)
      }
    
      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

override def removeSpecificProperty(key: String): Unit =
  this.updateSpecificProperty(key, null)

override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
  //this will throw for bad types -- no need to check by hand, we don't have a better error message
  val other = someNewNode.asInstanceOf[NewComment]
   this._code = other.code
   this._filename = other.filename
   this._lineNumber = if(other.lineNumber != null) other.lineNumber else None


}

}

/** Traversal steps for Comment */
class CommentTraversal[NodeType <: Comment](val traversal: Traversal[NodeType]) extends AnyVal {

  /** Traverse to code property */
  def code: Traversal[String] =
    traversal.map(_.code)

    /**
    * Traverse to nodes where the code matches the regular expression `value`
    * */
  def code(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.code == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.code); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the code matches at least one of the regular expressions in `values`
    * */
  def code(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.code); matcher.matches()}}
   }

  /**
    * Traverse to nodes where code matches `value` exactly.
    * */
  def codeExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.code == value}

  /**
    * Traverse to nodes where code matches one of the elements in `values` exactly.
    * */
  def codeExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.code)}
  }

  /**
    * Traverse to nodes where code does not match the regular expression `value`.
    * */
  def codeNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.code != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.code); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where code does not match any of the regular expressions in `values`.
    * */
  def codeNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.code); matcher.matches()}}
   }



  /** Traverse to filename property */
  def filename: Traversal[String] =
    traversal.map(_.filename)

    /**
    * Traverse to nodes where the filename matches the regular expression `value`
    * */
  def filename(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.filename == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.filename); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the filename matches at least one of the regular expressions in `values`
    * */
  def filename(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.filename); matcher.matches()}}
   }

  /**
    * Traverse to nodes where filename matches `value` exactly.
    * */
  def filenameExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.filename == value}

  /**
    * Traverse to nodes where filename matches one of the elements in `values` exactly.
    * */
  def filenameExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.filename)}
  }

  /**
    * Traverse to nodes where filename does not match the regular expression `value`.
    * */
  def filenameNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.filename != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.filename); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where filename does not match any of the regular expressions in `values`.
    * */
  def filenameNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.filename); matcher.matches()}}
   }



  /** Traverse to lineNumber property */
  def lineNumber: Traversal[java.lang.Integer] =
    traversal.flatMap(_.lineNumber)

    /**
    * Traverse to nodes where the lineNumber equals the given `value`
    * */
  def lineNumber(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.lineNumber.isDefined && node.lineNumber.get == value}

  /**
    * Traverse to nodes where the lineNumber equals at least one of the given `values`
    * */
  def lineNumber(values: java.lang.Integer*): Traversal[NodeType] = {
    val vset = values.toSet
    traversal.filter{node => node.lineNumber.isDefined && vset.contains(node.lineNumber.get)}
  }

  /**
    * Traverse to nodes where the lineNumber is greater than the given `value`
    * */
  def lineNumberGt(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.lineNumber.isDefined && node.lineNumber.get > value}

  /**
    * Traverse to nodes where the lineNumber is greater than or equal the given `value`
    * */
  def lineNumberGte(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.lineNumber.isDefined && node.lineNumber.get >= value}

  /**
    * Traverse to nodes where the lineNumber is less than the given `value`
    * */
  def lineNumberLt(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.lineNumber.isDefined && node.lineNumber.get < value}

  /**
    * Traverse to nodes where the lineNumber is less than or equal the given `value`
    * */
  def lineNumberLte(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.lineNumber.isDefined && node.lineNumber.get <= value}

  /**
    * Traverse to nodes where lineNumber is not equal to the given `value`.
    * */
  def lineNumberNot(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => !node.lineNumber.isDefined || node.lineNumber.get != value}

  /**
    * Traverse to nodes where lineNumber is not equal to any of the given `values`.
    * */
  def lineNumberNot(values: java.lang.Integer*): Traversal[NodeType] = {
    val vset = values.toSet
    traversal.filter{node => !node.lineNumber.isDefined || !vset.contains(node.lineNumber.get)}
  }



}
