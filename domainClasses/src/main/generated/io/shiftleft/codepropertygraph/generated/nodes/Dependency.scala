package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}
import scala.collection.mutable

/** Node base type for compiletime-only checks to improve type safety. EMT stands for: "erased marker trait", i.e. it is
  * erased at runtime
  */
trait DependencyEMT extends AnyRef with HasDependencyGroupIdEMT with HasNameEMT with HasVersionEMT

trait DependencyBase extends AbstractNode with StaticType[DependencyEMT] {

  override def propertiesMap: java.util.Map[String, Any] = {
    import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*
    val res = new java.util.HashMap[String, Any]()
    this.dependencyGroupId.foreach { p => res.put("DEPENDENCY_GROUP_ID", p) }
    if (("<empty>": String) != this.name) res.put("NAME", this.name)
    if (("<empty>": String) != this.version) res.put("VERSION", this.version)
    res
  }
}

object Dependency {
  val Label = "DEPENDENCY"
}

/** * NODE PROPERTIES:
  *
  * ▸ DependencyGroupId (String); Cardinality `ZeroOrOne` (optional); The group ID for a dependency
  *
  * ▸ Name (String); Cardinality `one` (mandatory with default value `<empty>`); Name of represented object, e.g.,
  * method name (e.g. "run")
  *
  * ▸ Version (String); Cardinality `one` (mandatory with default value `<empty>`); A version, given as a string. Used,
  * for example, in the META_DATA node to indicate which version of the CPG spec this CPG conforms to
  */
class Dependency(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 12, seq_4762)
    with DependencyBase
    with StaticType[DependencyEMT] {

  override def productElementName(n: Int): String =
    n match {
      case 0 => "dependencyGroupId"
      case 1 => "name"
      case 2 => "version"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.dependencyGroupId
      case 1 => this.name
      case 2 => this.version
      case _ => null
    }

  override def productPrefix = "Dependency"
  override def productArity  = 3

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[Dependency]
}
