package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object Location {
  def apply(graph: Graph, id: Long) = new Location(graph, id)

  val Label = "LOCATION"

  object PropertyNames {
    val ClassName = "CLASS_NAME" 
    val ClassShortName = "CLASS_SHORT_NAME" 
    val Filename = "FILENAME" 
    val LineNumber = "LINE_NUMBER" 
    val MethodFullName = "METHOD_FULL_NAME" 
    val MethodShortName = "METHOD_SHORT_NAME" 
    val NodeLabel = "NODE_LABEL" 
    val PackageName = "PACKAGE_NAME" 
    val Symbol = "SYMBOL" 
    val Node = "node" 
    val all: Set[String] = Set(ClassName, ClassShortName, Filename, LineNumber, MethodFullName, MethodShortName, NodeLabel, PackageName, Symbol, Node)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    val ClassName = new PropertyKey[String]("CLASS_NAME") 
    val ClassShortName = new PropertyKey[String]("CLASS_SHORT_NAME") 
    val Filename = new PropertyKey[String]("FILENAME") 
    val LineNumber = new PropertyKey[java.lang.Integer]("LINE_NUMBER") 
    val MethodFullName = new PropertyKey[String]("METHOD_FULL_NAME") 
    val MethodShortName = new PropertyKey[String]("METHOD_SHORT_NAME") 
    val NodeLabel = new PropertyKey[String]("NODE_LABEL") 
    val PackageName = new PropertyKey[String]("PACKAGE_NAME") 
    val Symbol = new PropertyKey[String]("SYMBOL") 
    val Node = new PropertyKey[CpgNode]("node") 
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List().asJava,
    List().asJava)


  object Edges {
    val Out: Array[String] = Array()
    val In: Array[String] = Array()
  }

  val factory = new NodeFactory[LocationDb] {
    override val forLabel = Location.Label

    override def createNode(ref: NodeRef[LocationDb]) =
      new LocationDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = Location(graph, id)
  }
}

trait LocationBase extends CpgNode  with HasClassName with HasClassShortName with HasFilename with HasLineNumber with HasMethodFullName with HasMethodShortName with HasNodeLabel with HasPackageName with HasSymbol {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  def node: Option[CpgNode]
}

class Location(graph: Graph, id: Long) extends NodeRef[LocationDb](graph, id)
  with LocationBase
  with StoredNode
   {
    override def className: String = get().className
  override def classShortName: String = get().classShortName
  override def filename: String = get().filename
  override def lineNumber: Option[java.lang.Integer] = get().lineNumber
  override def methodFullName: String = get().methodFullName
  override def methodShortName: String = get().methodShortName
  override def nodeLabel: String = get().nodeLabel
  override def packageName: String = get().packageName
  override def symbol: String = get().symbol
    def node: Option[CpgNode] = get().node
  
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    Location.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "className" 
case 2 => "classShortName" 
case 3 => "filename" 
case 4 => "lineNumber" 
case 5 => "methodFullName" 
case 6 => "methodShortName" 
case 7 => "nodeLabel" 
case 8 => "packageName" 
case 9 => "symbol" 
case 10 => "node" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => className
case 2 => classShortName
case 3 => filename
case 4 => lineNumber
case 5 => methodFullName
case 6 => methodShortName
case 7 => nodeLabel
case 8 => packageName
case 9 => symbol
case 10 => node
    }

  override def productPrefix = "Location"
  override def productArity = 11
}

class LocationDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
   with LocationBase {

  override def layoutInformation: NodeLayoutInformation = Location.layoutInformation

private var _className: String = null
def className: String = _className

private var _classShortName: String = null
def classShortName: String = _classShortName

private var _filename: String = null
def filename: String = _filename

private var _lineNumber: Option[java.lang.Integer] = None
def lineNumber: Option[java.lang.Integer] = _lineNumber

private var _methodFullName: String = null
def methodFullName: String = _methodFullName

private var _methodShortName: String = null
def methodShortName: String = _methodShortName

private var _nodeLabel: String = null
def nodeLabel: String = _nodeLabel

private var _packageName: String = null
def packageName: String = _packageName

private var _symbol: String = null
def symbol: String = _symbol

private var _node: Option[CpgNode] = None
def node: Option[CpgNode] = this._node


  /* all properties */
  override def valueMap: JMap[String, AnyRef] =  {
  val properties = new JHashMap[String, AnyRef]
if (className != null) { properties.put("CLASS_NAME", className) }
if (classShortName != null) { properties.put("CLASS_SHORT_NAME", classShortName) }
if (filename != null) { properties.put("FILENAME", filename) }
lineNumber.map { value => properties.put("LINE_NUMBER", value) }
if (methodFullName != null) { properties.put("METHOD_FULL_NAME", methodFullName) }
if (methodShortName != null) { properties.put("METHOD_SHORT_NAME", methodShortName) }
if (nodeLabel != null) { properties.put("NODE_LABEL", nodeLabel) }
if (packageName != null) { properties.put("PACKAGE_NAME", packageName) }
if (symbol != null) { properties.put("SYMBOL", symbol) }
   if (this._node != null && this._node.nonEmpty) { properties.put("node", this._node.get) }
  properties
}

  

  override def label: String = {
    Location.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "className" 
case 2 => "classShortName" 
case 3 => "filename" 
case 4 => "lineNumber" 
case 5 => "methodFullName" 
case 6 => "methodShortName" 
case 7 => "nodeLabel" 
case 8 => "packageName" 
case 9 => "symbol" 
case 10 => "node" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => className
case 2 => classShortName
case 3 => filename
case 4 => lineNumber
case 5 => methodFullName
case 6 => methodShortName
case 7 => nodeLabel
case 8 => packageName
case 9 => symbol
case 10 => node
    }

  override def productPrefix = "Location"
  override def productArity = 11

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[LocationDb]

  override def property(key:String): AnyRef = {
    key match {
      case "CLASS_NAME" => this._className
      case "CLASS_SHORT_NAME" => this._classShortName
      case "FILENAME" => this._filename
      case "LINE_NUMBER" => this._lineNumber.orNull
      case "METHOD_FULL_NAME" => this._methodFullName
      case "METHOD_SHORT_NAME" => this._methodShortName
      case "NODE_LABEL" => this._nodeLabel
      case "PACKAGE_NAME" => this._packageName
      case "SYMBOL" => this._symbol
      case "node" => this._node.orNull
      case _ => null
    }
  }

  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
    key match {
      case "CLASS_NAME" => this._className = value.asInstanceOf[String]
      case "CLASS_SHORT_NAME" => this._classShortName = value.asInstanceOf[String]
      case "FILENAME" => this._filename = value.asInstanceOf[String]
      case "LINE_NUMBER" => this._lineNumber = value match {
        case null | None => None
        case someVal: java.lang.Integer => Some(someVal)
      }
      case "METHOD_FULL_NAME" => this._methodFullName = value.asInstanceOf[String]
      case "METHOD_SHORT_NAME" => this._methodShortName = value.asInstanceOf[String]
      case "NODE_LABEL" => this._nodeLabel = value.asInstanceOf[String]
      case "PACKAGE_NAME" => this._packageName = value.asInstanceOf[String]
      case "SYMBOL" => this._symbol = value.asInstanceOf[String]
      case "node" => this._node = value match {
        case null | None => None
        case someVal: CpgNode => Some(someVal)
      }
      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

override def removeSpecificProperty(key: String): Unit =
  this.updateSpecificProperty(key, null)

override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
  //this will throw for bad types -- no need to check by hand, we don't have a better error message
  val other = someNewNode.asInstanceOf[NewLocation]
   this._className = other.className
   this._classShortName = other.classShortName
   this._filename = other.filename
   this._lineNumber = if(other.lineNumber != null) other.lineNumber else None
   this._methodFullName = other.methodFullName
   this._methodShortName = other.methodShortName
   this._nodeLabel = other.nodeLabel
   this._packageName = other.packageName
   this._symbol = other.symbol
  this._node = other.node match {
    case null | None => None
    case Some(newNode:NewNode) => Some(mapping(newNode).asInstanceOf[CpgNode])
    case Some(oldNode:StoredNode) => Some(oldNode.asInstanceOf[CpgNode])
    case _ => throw new MatchError("unreachable")
  }

}

}

/** Traversal steps for Location */
class LocationTraversal[NodeType <: Location](val traversal: Traversal[NodeType]) extends AnyVal {

  /** Traverse to className property */
  def className: Traversal[String] =
    traversal.map(_.className)

    /**
    * Traverse to nodes where the className matches the regular expression `value`
    * */
  def className(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.className == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.className); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the className matches at least one of the regular expressions in `values`
    * */
  def className(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.className); matcher.matches()}}
   }

  /**
    * Traverse to nodes where className matches `value` exactly.
    * */
  def classNameExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.className == value}

  /**
    * Traverse to nodes where className matches one of the elements in `values` exactly.
    * */
  def classNameExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.className)}
  }

  /**
    * Traverse to nodes where className does not match the regular expression `value`.
    * */
  def classNameNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.className != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.className); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where className does not match any of the regular expressions in `values`.
    * */
  def classNameNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.className); matcher.matches()}}
   }



  /** Traverse to classShortName property */
  def classShortName: Traversal[String] =
    traversal.map(_.classShortName)

    /**
    * Traverse to nodes where the classShortName matches the regular expression `value`
    * */
  def classShortName(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.classShortName == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.classShortName); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the classShortName matches at least one of the regular expressions in `values`
    * */
  def classShortName(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.classShortName); matcher.matches()}}
   }

  /**
    * Traverse to nodes where classShortName matches `value` exactly.
    * */
  def classShortNameExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.classShortName == value}

  /**
    * Traverse to nodes where classShortName matches one of the elements in `values` exactly.
    * */
  def classShortNameExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.classShortName)}
  }

  /**
    * Traverse to nodes where classShortName does not match the regular expression `value`.
    * */
  def classShortNameNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.classShortName != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.classShortName); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where classShortName does not match any of the regular expressions in `values`.
    * */
  def classShortNameNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.classShortName); matcher.matches()}}
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


  /** Traverse to methodFullName property */
  def methodFullName: Traversal[String] =
    traversal.map(_.methodFullName)

    /**
    * Traverse to nodes where the methodFullName matches the regular expression `value`
    * */
  def methodFullName(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.methodFullName == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.methodFullName); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the methodFullName matches at least one of the regular expressions in `values`
    * */
  def methodFullName(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.methodFullName); matcher.matches()}}
   }

  /**
    * Traverse to nodes where methodFullName matches `value` exactly.
    * */
  def methodFullNameExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.methodFullName == value}

  /**
    * Traverse to nodes where methodFullName matches one of the elements in `values` exactly.
    * */
  def methodFullNameExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.methodFullName)}
  }

  /**
    * Traverse to nodes where methodFullName does not match the regular expression `value`.
    * */
  def methodFullNameNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.methodFullName != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.methodFullName); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where methodFullName does not match any of the regular expressions in `values`.
    * */
  def methodFullNameNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.methodFullName); matcher.matches()}}
   }



  /** Traverse to methodShortName property */
  def methodShortName: Traversal[String] =
    traversal.map(_.methodShortName)

    /**
    * Traverse to nodes where the methodShortName matches the regular expression `value`
    * */
  def methodShortName(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.methodShortName == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.methodShortName); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the methodShortName matches at least one of the regular expressions in `values`
    * */
  def methodShortName(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.methodShortName); matcher.matches()}}
   }

  /**
    * Traverse to nodes where methodShortName matches `value` exactly.
    * */
  def methodShortNameExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.methodShortName == value}

  /**
    * Traverse to nodes where methodShortName matches one of the elements in `values` exactly.
    * */
  def methodShortNameExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.methodShortName)}
  }

  /**
    * Traverse to nodes where methodShortName does not match the regular expression `value`.
    * */
  def methodShortNameNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.methodShortName != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.methodShortName); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where methodShortName does not match any of the regular expressions in `values`.
    * */
  def methodShortNameNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.methodShortName); matcher.matches()}}
   }



  /** Traverse to nodeLabel property */
  def nodeLabel: Traversal[String] =
    traversal.map(_.nodeLabel)

    /**
    * Traverse to nodes where the nodeLabel matches the regular expression `value`
    * */
  def nodeLabel(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.nodeLabel == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.nodeLabel); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the nodeLabel matches at least one of the regular expressions in `values`
    * */
  def nodeLabel(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.nodeLabel); matcher.matches()}}
   }

  /**
    * Traverse to nodes where nodeLabel matches `value` exactly.
    * */
  def nodeLabelExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.nodeLabel == value}

  /**
    * Traverse to nodes where nodeLabel matches one of the elements in `values` exactly.
    * */
  def nodeLabelExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.nodeLabel)}
  }

  /**
    * Traverse to nodes where nodeLabel does not match the regular expression `value`.
    * */
  def nodeLabelNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.nodeLabel != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.nodeLabel); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where nodeLabel does not match any of the regular expressions in `values`.
    * */
  def nodeLabelNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.nodeLabel); matcher.matches()}}
   }



  /** Traverse to packageName property */
  def packageName: Traversal[String] =
    traversal.map(_.packageName)

    /**
    * Traverse to nodes where the packageName matches the regular expression `value`
    * */
  def packageName(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.packageName == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.packageName); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the packageName matches at least one of the regular expressions in `values`
    * */
  def packageName(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.packageName); matcher.matches()}}
   }

  /**
    * Traverse to nodes where packageName matches `value` exactly.
    * */
  def packageNameExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.packageName == value}

  /**
    * Traverse to nodes where packageName matches one of the elements in `values` exactly.
    * */
  def packageNameExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.packageName)}
  }

  /**
    * Traverse to nodes where packageName does not match the regular expression `value`.
    * */
  def packageNameNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.packageName != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.packageName); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where packageName does not match any of the regular expressions in `values`.
    * */
  def packageNameNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.packageName); matcher.matches()}}
   }



  /** Traverse to symbol property */
  def symbol: Traversal[String] =
    traversal.map(_.symbol)

    /**
    * Traverse to nodes where the symbol matches the regular expression `value`
    * */
  def symbol(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.symbol == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.symbol); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the symbol matches at least one of the regular expressions in `values`
    * */
  def symbol(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.symbol); matcher.matches()}}
   }

  /**
    * Traverse to nodes where symbol matches `value` exactly.
    * */
  def symbolExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.symbol == value}

  /**
    * Traverse to nodes where symbol matches one of the elements in `values` exactly.
    * */
  def symbolExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.symbol)}
  }

  /**
    * Traverse to nodes where symbol does not match the regular expression `value`.
    * */
  def symbolNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.symbol != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.symbol); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where symbol does not match any of the regular expressions in `values`.
    * */
  def symbolNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.symbol); matcher.matches()}}
   }




}
