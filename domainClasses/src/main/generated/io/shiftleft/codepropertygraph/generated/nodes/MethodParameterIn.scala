package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}
import scala.collection.mutable

/** Node base type for compiletime-only checks to improve type safety. EMT stands for: "erased marker trait", i.e. it is
  * erased at runtime
  */
trait MethodParameterInEMT
    extends AnyRef
    with CfgNodeEMT
    with DeclarationEMT
    with HasClosureBindingIdEMT
    with HasDynamicTypeHintFullNameEMT
    with HasEvaluationStrategyEMT
    with HasIndexEMT
    with HasIsVariadicEMT
    with HasPossibleTypesEMT
    with HasTypeFullNameEMT

trait MethodParameterInBase
    extends AbstractNode
    with CfgNodeBase
    with DeclarationBase
    with StaticType[MethodParameterInEMT] {

  override def propertiesMap: java.util.Map[String, Any] = {
    import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*
    val res = new java.util.HashMap[String, Any]()
    this.closureBindingId.foreach { p => res.put("CLOSURE_BINDING_ID", p) }
    if (("<empty>": String) != this.code) res.put("CODE", this.code)
    this.columnNumber.foreach { p => res.put("COLUMN_NUMBER", p) }
    val tmpDynamicTypeHintFullName = this.dynamicTypeHintFullName;
    if (tmpDynamicTypeHintFullName.nonEmpty) res.put("DYNAMIC_TYPE_HINT_FULL_NAME", tmpDynamicTypeHintFullName)
    if (("<empty>": String) != this.evaluationStrategy) res.put("EVALUATION_STRATEGY", this.evaluationStrategy)
    if ((-1: Int) != this.index) res.put("INDEX", this.index)
    if ((false: Boolean) != this.isVariadic) res.put("IS_VARIADIC", this.isVariadic)
    this.lineNumber.foreach { p => res.put("LINE_NUMBER", p) }
    if (("<empty>": String) != this.name) res.put("NAME", this.name)
    this.offset.foreach { p => res.put("OFFSET", p) }
    this.offsetEnd.foreach { p => res.put("OFFSET_END", p) }
    if ((-1: Int) != this.order) res.put("ORDER", this.order)
    val tmpPossibleTypes = this.possibleTypes;
    if (tmpPossibleTypes.nonEmpty) res.put("POSSIBLE_TYPES", tmpPossibleTypes)
    if (("<empty>": String) != this.typeFullName) res.put("TYPE_FULL_NAME", this.typeFullName)
    res
  }
}

object MethodParameterIn {
  val Label = "METHOD_PARAMETER_IN"
}

/** * NODE PROPERTIES:
  *
  * ▸ ClosureBindingId (String); Cardinality `ZeroOrOne` (optional); Identifier which uniquely describes a
  * CLOSURE_BINDING. This property is used to match captured LOCAL nodes with the corresponding CLOSURE_BINDING nodes
  *
  * ▸ Code (String); Cardinality `one` (mandatory with default value `<empty>`); This field holds the code snippet that
  * the node represents.
  *
  * ▸ ColumnNumber (Int); Cardinality `ZeroOrOne` (optional); This optional fields provides the column number of the
  * program construct represented by the node.
  *
  * ▸ DynamicTypeHintFullName (String); Cardinality `List` (many); Type hint for the dynamic type. These are observed to
  * be verifiable at runtime.
  *
  * ▸ EvaluationStrategy (String); Cardinality `one` (mandatory with default value `<empty>`); For formal method input
  * parameters, output parameters, and return parameters, this field holds the evaluation strategy, which is one of the
  * following: 1) `BY_REFERENCE` indicates that the parameter is passed by reference, 2) `BY_VALUE` indicates that it is
  * passed by value, that is, a copy is made, 3) `BY_SHARING` the parameter is a pointer/reference and it is shared with
  * the caller/callee. While a copy of the pointer is made, a copy of the object that it points to is not made.
  *
  * ▸ Index (Int); Cardinality `one` (mandatory with default value `-1`); Specifies an index, e.g., for a parameter or
  * argument. Explicit parameters are numbered from 1 to N, while index 0 is reserved for implicit self / this
  * parameter.
  *
  * ▸ IsVariadic (Boolean); Cardinality `one` (mandatory with default value `false`); Specifies whether a parameter is
  * the variadic argument handling parameter of a variadic method. Only one parameter of a method is allowed to have
  * this property set to true.
  *
  * ▸ LineNumber (Int); Cardinality `ZeroOrOne` (optional); This optional field provides the line number of the program
  * construct represented by the node.
  *
  * ▸ Name (String); Cardinality `one` (mandatory with default value `<empty>`); Name of represented object, e.g.,
  * method name (e.g. "run")
  *
  * ▸ Offset (Int); Cardinality `ZeroOrOne` (optional); Start offset into the CONTENT property of the corresponding FILE
  * node. The offset is such that parts of the content can easily be accessed via
  * `content.substring(offset, offsetEnd)`. This means that the offset must be measured in utf16 encoding (i.e. neither
  * in characters/codeunits nor in byte-offsets into a utf8 encoding). E.g. for METHOD nodes this start offset points to
  * the start of the methods source code in the string holding the source code of the entire file.
  *
  * ▸ OffsetEnd (Int); Cardinality `ZeroOrOne` (optional); End offset (exclusive) into the CONTENT property of the
  * corresponding FILE node. See OFFSET documentation for finer details. E.g. for METHOD nodes this end offset points to
  * the first code position which is not part of the method.
  *
  * ▸ Order (Int); Cardinality `one` (mandatory with default value `-1`); This integer indicates the position of the
  * node among its siblings in the AST. The left-most child has an order of 0.
  *
  * ▸ PossibleTypes (String); Cardinality `List` (many); Similar to `DYNAMIC_TYPE_HINT_FULL_NAME`, but that this makes
  * no guarantee that types within this property are correct. This property is used to capture observations between node
  * interactions during a 'may-analysis'.
  *
  * ▸ TypeFullName (String); Cardinality `one` (mandatory with default value `<empty>`); This field contains the
  * fully-qualified static type name of the program construct represented by a node. It is the name of an instantiated
  * type, e.g., `java.util.List<Integer>`, rather than `java.util.List[T]`. If the type cannot be determined, this field
  * should be set to the empty string.
  */
class MethodParameterIn(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 26, seq_4762)
    with MethodParameterInBase
    with CfgNode
    with Declaration
    with StaticType[MethodParameterInEMT] {

  override def productElementName(n: Int): String =
    n match {
      case 0  => "closureBindingId"
      case 1  => "code"
      case 2  => "columnNumber"
      case 3  => "dynamicTypeHintFullName"
      case 4  => "evaluationStrategy"
      case 5  => "index"
      case 6  => "isVariadic"
      case 7  => "lineNumber"
      case 8  => "name"
      case 9  => "offset"
      case 10 => "offsetEnd"
      case 11 => "order"
      case 12 => "possibleTypes"
      case 13 => "typeFullName"
      case _  => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0  => this.closureBindingId
      case 1  => this.code
      case 2  => this.columnNumber
      case 3  => this.dynamicTypeHintFullName
      case 4  => this.evaluationStrategy
      case 5  => this.index
      case 6  => this.isVariadic
      case 7  => this.lineNumber
      case 8  => this.name
      case 9  => this.offset
      case 10 => this.offsetEnd
      case 11 => this.order
      case 12 => this.possibleTypes
      case 13 => this.typeFullName
      case _  => null
    }

  override def productPrefix = "MethodParameterIn"
  override def productArity  = 14

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[MethodParameterIn]
}
