package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}
import scala.collection.mutable

/** Node base type for compiletime-only checks to improve type safety. EMT stands for: "erased marker trait", i.e. it is
  * erased at runtime
  */
trait CallEMT
    extends AnyRef
    with CallReprEMT
    with ExpressionEMT
    with HasDispatchTypeEMT
    with HasDynamicTypeHintFullNameEMT
    with HasMethodFullNameEMT
    with HasPossibleTypesEMT
    with HasStaticReceiverEMT
    with HasTypeFullNameEMT

trait CallBase extends AbstractNode with CallReprBase with ExpressionBase with StaticType[CallEMT] {

  override def propertiesMap: java.util.Map[String, Any] = {
    import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*
    val res = new java.util.HashMap[String, Any]()
    if ((-1: Int) != this.argumentIndex) res.put("ARGUMENT_INDEX", this.argumentIndex)
    this.argumentLabel.foreach { p => res.put("ARGUMENT_LABEL", p) }
    this.argumentName.foreach { p => res.put("ARGUMENT_NAME", p) }
    if (("<empty>": String) != this.code) res.put("CODE", this.code)
    this.columnNumber.foreach { p => res.put("COLUMN_NUMBER", p) }
    if (("<empty>": String) != this.dispatchType) res.put("DISPATCH_TYPE", this.dispatchType)
    val tmpDynamicTypeHintFullName = this.dynamicTypeHintFullName;
    if (tmpDynamicTypeHintFullName.nonEmpty) res.put("DYNAMIC_TYPE_HINT_FULL_NAME", tmpDynamicTypeHintFullName)
    this.lineNumber.foreach { p => res.put("LINE_NUMBER", p) }
    if (("<empty>": String) != this.methodFullName) res.put("METHOD_FULL_NAME", this.methodFullName)
    if (("<empty>": String) != this.name) res.put("NAME", this.name)
    this.offset.foreach { p => res.put("OFFSET", p) }
    this.offsetEnd.foreach { p => res.put("OFFSET_END", p) }
    if ((-1: Int) != this.order) res.put("ORDER", this.order)
    val tmpPossibleTypes = this.possibleTypes;
    if (tmpPossibleTypes.nonEmpty) res.put("POSSIBLE_TYPES", tmpPossibleTypes)
    if (("": String) != this.signature) res.put("SIGNATURE", this.signature)
    this.staticReceiver.foreach { p => res.put("STATIC_RECEIVER", p) }
    if (("<empty>": String) != this.typeFullName) res.put("TYPE_FULL_NAME", this.typeFullName)
    res
  }
}

object Call {
  val Label = "CALL"
}

/** * NODE PROPERTIES:
  *
  * ▸ ArgumentIndex (Int); Cardinality `one` (mandatory with default value `-1`); AST-children of CALL nodes have an
  * argument index, that is used to match call-site arguments with callee parameters. Explicit parameters are numbered
  * from 1 to N, while index 0 is reserved for implicit self / this parameter. CALLs without implicit parameter
  * therefore have arguments starting with index 1. AST-children of BLOCK nodes may have an argument index as well; in
  * this case, the last argument index determines the return expression of a BLOCK expression. If the `PARAMETER_NAME`
  * field is set, then the `ARGUMENT_INDEX` field is ignored. It is suggested to set it to -1.
  *
  * ▸ ArgumentLabel (String); Cardinality `ZeroOrOne` (optional); This field is used to keep track of the argument label
  * for languages that support them, such as Swift. It is used in addition to `ARGUMENT_INDEX` and can be used to
  * reconstruct the original call syntax more faithfully. For example, in Swift, a method call may look like
  * `foo(arg1: 42, arg2: "hello")` where `arg1` and `arg2` are argument labels. In this case, the `ARGUMENT_LABEL` field
  * for the first argument would be set to `arg1` and for the second argument it would be set to `arg2`. Contrary to the
  * `ARGUMENT_NAME` the label should not be expected to match the name of any parameter, and is not needed for dataflow
  * purposes at all.
  *
  * ▸ ArgumentName (String); Cardinality `ZeroOrOne` (optional); For calls involving named parameters, the
  * `ARGUMENT_NAME` field holds the name of the parameter initialized by the expression. For all other calls, this field
  * is unset. Note that the `ARGUMENT_NAME` should be an exact match of the NAME of a METHOD_PARAMETER_{IN,OUT}. It
  * overrides ARGUMENT_INDEX for dataflow purposes.
  *
  * ▸ Code (String); Cardinality `one` (mandatory with default value `<empty>`); This field holds the code snippet that
  * the node represents.
  *
  * ▸ ColumnNumber (Int); Cardinality `ZeroOrOne` (optional); This optional fields provides the column number of the
  * program construct represented by the node.
  *
  * ▸ DispatchType (String); Cardinality `one` (mandatory with default value `<empty>`); This field holds the dispatch
  * type of a call, which is either `STATIC_DISPATCH` or `DYNAMIC_DISPATCH`. For statically dispatched method calls, the
  * call target is known at compile time while for dynamically dispatched calls, it can only be determined at runtime as
  * it may depend on the type of an object (as is the case for virtual method calls) or calculation of an offset.
  *
  * ▸ DynamicTypeHintFullName (String); Cardinality `List` (many); Type hint for the dynamic type. These are observed to
  * be verifiable at runtime.
  *
  * ▸ LineNumber (Int); Cardinality `ZeroOrOne` (optional); This optional field provides the line number of the program
  * construct represented by the node.
  *
  * ▸ MethodFullName (String); Cardinality `one` (mandatory with default value `<empty>`); The FULL_NAME of a method.
  * Used to link CALL and METHOD nodes. It is required to have exactly one METHOD node for each METHOD_FULL_NAME
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
  * ▸ Signature (String); Cardinality `one` (mandatory with default value ``); The method signature encodes the types of
  * parameters in a string. The string SHOULD be human readable and suitable for differentiating methods with different
  * parameter types sufficiently to allow for resolving of function overloading. The present specification does not
  * enforce a strict format for the signature, that is, it can be chosen by the frontend implementor to fit the source
  * language.
  *
  * ▸ StaticReceiver (String); Cardinality `ZeroOrOne` (optional); The `STATIC_RECEIVER` field is used to keep track of
  * the type on which a static method is called for static methods which may be inherited. This information can then be
  * used to find the true `METHOD_FULL_NAME` of the method being called during call linking. For example, if a class
  * `Foo` defines a static method `foo` and a class `Bar extends Foo`, then the `STATIC_RECEIVER` of a`Bar.foo()` call
  * is `Bar` and the `METHOD_FULL_NAME` of the `foo` call is rewritten to `Foo.foo:<signature>`.
  *
  * ▸ TypeFullName (String); Cardinality `one` (mandatory with default value `<empty>`); This field contains the
  * fully-qualified static type name of the program construct represented by a node. It is the name of an instantiated
  * type, e.g., `java.util.List<Integer>`, rather than `java.util.List[T]`. If the type cannot be determined, this field
  * should be set to the empty string.
  */
class Call(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 7, seq_4762)
    with CallBase
    with CallRepr
    with Expression
    with StaticType[CallEMT] {

  override def productElementName(n: Int): String =
    n match {
      case 0  => "argumentIndex"
      case 1  => "argumentLabel"
      case 2  => "argumentName"
      case 3  => "code"
      case 4  => "columnNumber"
      case 5  => "dispatchType"
      case 6  => "dynamicTypeHintFullName"
      case 7  => "lineNumber"
      case 8  => "methodFullName"
      case 9  => "name"
      case 10 => "offset"
      case 11 => "offsetEnd"
      case 12 => "order"
      case 13 => "possibleTypes"
      case 14 => "signature"
      case 15 => "staticReceiver"
      case 16 => "typeFullName"
      case _  => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0  => this.argumentIndex
      case 1  => this.argumentLabel
      case 2  => this.argumentName
      case 3  => this.code
      case 4  => this.columnNumber
      case 5  => this.dispatchType
      case 6  => this.dynamicTypeHintFullName
      case 7  => this.lineNumber
      case 8  => this.methodFullName
      case 9  => this.name
      case 10 => this.offset
      case 11 => this.offsetEnd
      case 12 => this.order
      case 13 => this.possibleTypes
      case 14 => this.signature
      case 15 => this.staticReceiver
      case 16 => this.typeFullName
      case _  => null
    }

  override def productPrefix = "Call"
  override def productArity  = 17

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[Call]
}
