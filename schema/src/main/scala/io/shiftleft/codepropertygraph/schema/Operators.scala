package io.shiftleft.codepropertygraph.schema

import overflowdb.schema._
import overflowdb.schema.Property.ValueType

object Operators extends SchemaBase {

  override def index: Int = 17

  override def description: String =
    """
      |
      |""".stripMargin

  def apply(builder: SchemaBuilder) = new Schema(builder)

  class Schema(builder: SchemaBuilder) {
    implicit private val schemaInfo = SchemaInfo.forClass(getClass)

// constants
    val operators = builder.addConstants(
      category = "Operators",
      Constant(name = "addition", value = "<operator>.addition", valueType = ValueType.String, comment = ""),
      Constant(name = "subtraction", value = "<operator>.subtraction", valueType = ValueType.String, comment = ""),
      Constant(name = "multiplication",
               value = "<operator>.multiplication",
               valueType = ValueType.String,
               comment = ""),
      Constant(name = "division", value = "<operator>.division", valueType = ValueType.String, comment = ""),
      Constant(name = "exponentiation",
               value = "<operator>.exponentiation",
               valueType = ValueType.String,
               comment = ""),
      Constant(name = "modulo", value = "<operator>.modulo", valueType = ValueType.String, comment = ""),
      Constant(name = "shiftLeft", value = "<operator>.shiftLeft", valueType = ValueType.String, comment = ""),
      Constant(name = "logicalShiftRight",
               value = "<operator>.logicalShiftRight",
               valueType = ValueType.String,
               comment = "Shift right padding with zeros"),
      Constant(name = "arithmeticShiftRight",
               value = "<operator>.arithmeticShiftRight",
               valueType = ValueType.String,
               comment = "Shift right preserving the sign"),
      Constant(name = "not", value = "<operator>.not", valueType = ValueType.String, comment = ""),
      Constant(name = "and", value = "<operator>.and", valueType = ValueType.String, comment = ""),
      Constant(name = "or", value = "<operator>.or", valueType = ValueType.String, comment = ""),
      Constant(name = "xor", value = "<operator>.xor", valueType = ValueType.String, comment = ""),
      Constant(name = "assignmentPlus",
               value = "<operator>.assignmentPlus",
               valueType = ValueType.String,
               comment = ""),
      Constant(name = "assignmentMinus",
               value = "<operator>.assignmentMinus",
               valueType = ValueType.String,
               comment = ""),
      Constant(name = "assignmentMultiplication",
               value = "<operator>.assignmentMultiplication",
               valueType = ValueType.String,
               comment = ""),
      Constant(name = "assignmentDivision",
               value = "<operator>.assignmentDivision",
               valueType = ValueType.String,
               comment = ""),
      Constant(name = "assignmentExponentiation",
               value = "<operators>.assignmentExponentiation",
               valueType = ValueType.String,
               comment = ""),
      Constant(name = "assignmentModulo",
               value = "<operators>.assignmentModulo",
               valueType = ValueType.String,
               comment = ""),
      Constant(name = "assignmentShiftLeft",
               value = "<operators>.assignmentShiftLeft",
               valueType = ValueType.String,
               comment = ""),
      Constant(name = "assignmentLogicalShiftRight",
               value = "<operators>.assignmentLogicalShiftRight",
               valueType = ValueType.String,
               comment = ""),
      Constant(name = "assignmentArithmeticShiftRight",
               value = "<operators>.assignmentArithmeticShiftRight",
               valueType = ValueType.String,
               comment = ""),
      Constant(name = "assignmentAnd",
               value = "<operators>.assignmentAnd",
               valueType = ValueType.String,
               comment = ""),
      Constant(name = "assignmentOr", value = "<operators>.assignmentOr", valueType = ValueType.String, comment = ""),
      Constant(name = "assignmentXor",
               value = "<operators>.assignmentXor",
               valueType = ValueType.String,
               comment = ""),
      Constant(name = "assignment", value = "<operator>.assignment", valueType = ValueType.String, comment = ""),
      Constant(name = "minus", value = "<operator>.minus", valueType = ValueType.String, comment = "E.g. `a = -b`"),
      Constant(name = "plus", value = "<operator>.plus", valueType = ValueType.String, comment = "E.g. `a = +b`"),
      Constant(name = "preIncrement", value = "<operator>.preIncrement", valueType = ValueType.String, comment = ""),
      Constant(name = "preDecrement", value = "<operator>.preDecrement", valueType = ValueType.String, comment = ""),
      Constant(name = "postIncrement", value = "<operator>.postIncrement", valueType = ValueType.String, comment = ""),
      Constant(name = "postDecrement", value = "<operator>.postDecrement", valueType = ValueType.String, comment = ""),
      Constant(name = "logicalNot", value = "<operator>.logicalNot", valueType = ValueType.String, comment = ""),
      Constant(name = "logicalOr", value = "<operator>.logicalOr", valueType = ValueType.String, comment = ""),
      Constant(name = "logicalAnd", value = "<operator>.logicalAnd", valueType = ValueType.String, comment = ""),
      Constant(name = "equals", value = "<operator>.equals", valueType = ValueType.String, comment = ""),
      Constant(name = "notEquals", value = "<operator>.notEquals", valueType = ValueType.String, comment = ""),
      Constant(name = "greaterThan", value = "<operator>.greaterThan", valueType = ValueType.String, comment = ""),
      Constant(name = "lessThan", value = "<operator>.lessThan", valueType = ValueType.String, comment = ""),
      Constant(name = "greaterEqualsThan",
               value = "<operator>.greaterEqualsThan",
               valueType = ValueType.String,
               comment = ""),
      Constant(name = "lessEqualsThan",
               value = "<operator>.lessEqualsThan",
               valueType = ValueType.String,
               comment = ""),
      Constant(name = "instanceOf", value = "<operator>.instanceOf", valueType = ValueType.String, comment = ""),
      Constant(name = "memberAccess",
               value = "<operator>.memberAccess",
               valueType = ValueType.String,
               comment = "Deprecated. Formerly in C: `a.b` but not! in Java"),
      Constant(
        name = "indirectMemberAccess",
        value = "<operator>.indirectMemberAccess",
        valueType = ValueType.String,
        comment = "Deprecated. Formerly in C: `a->b` and `a.b` in Java"
      ),
      Constant(
        name = "computedMemberAccess",
        value = "<operator>.computedMemberAccess",
        valueType = ValueType.String,
        comment = "Deprecated. Formerly in C: `a[b]` but not! in Java"
      ),
      Constant(
        name = "indirectComputedMemberAccess",
        value = "<operator>.indirectComputedMemberAccess",
        valueType = ValueType.String,
        comment = "Deprecated. Formerly in C++: `a->*b` and a[b] in Java"
      ),
      Constant(
        name = "indirection",
        value = "<operator>.indirection",
        valueType = ValueType.String,
        comment =
          "Accesses through a pointer (load/store), i.e. dereferences it. Typically the star-operator in C/C++. Pairs of addressOf and indirection operators cancel each other. Handling of this operator is special-cased in the back-end"
      ),
      Constant(
        name = "delete",
        value = "<operator>.delete",
        valueType = ValueType.String,
        comment = "Deletes a property from a namespace. E.g. `a=3; delete a; a == undefined;"
      ),
      Constant(
        name = "conditional",
        value = "<operator>.conditional",
        valueType = ValueType.String,
        comment = "E.g. `a ? consequent : alternate`. In future probably also used for if statements"
      ),
      Constant(name = "cast",
               value = "<operator>.cast",
               valueType = ValueType.String,
               comment = "Type casts of any sort"),
      Constant(
        name = "compare",
        value = "<operator>.compare",
        valueType = ValueType.String,
        comment =
          "Comparison between two arguments with the results: 0 == equal, negative == left < right, positive == left > right"
      ),
      Constant(
        name = "addressOf",
        value = "<operator>.addressOf",
        valueType = ValueType.String,
        comment =
          "Returns the address of a given object. Pairs of addressOf and indirection operators cancel each other. Handling of this operator is special-cased in the back-end"
      ),
      Constant(name = "sizeOf",
               value = "<operator>.sizeOf",
               valueType = ValueType.String,
               comment = "Returns the size of a given object"),
      Constant(
        name = "fieldAccess",
        value = "<operator>.fieldAccess",
        valueType = ValueType.String,
        comment =
          "Returns or sets a field/member of a struct/class. Typically, the dot-operator. In terms of CPG, the first argument is the struct/class and the second argument is either a FIELD_IDENTIFIER node, a LITERAL node or an arbitrary expression. If it is a FIELD_IDENTIFIER, then the string contained in the CANONICAL_NAME field of this FIELD_IDENTIFIER node selects which field/member is accessed. If it is a LITERAL node, then its CODE selects which field/member is selected. In any other case the access is considered unpredictable by the backend, and we overtaint. indexAccess and fieldAccess live in the same space, such that e.g. obj.field and obj[\"field\"] refer to the same target (as is correct in e.g. javascript). Handling of this operator is special-cased in the back-end"
      ),
      Constant(
        name = "indirectFieldAccess",
        value = "<operator>.indirectFieldAccess",
        valueType = ValueType.String,
        comment =
          "Accesses (loads/stores) to a field/member through a pointer to a struct/class. Typically, C/C++ arrow-operator. The field selection works the same way as for fieldAccess. For the sake of data-flow tracking, this is the same as first dereferencing the pointer and then accessing the field. Handling of this operator is special-cased in the back-end"
      ),
      Constant(
        name = "indexAccess",
        value = "<operator>.indexAccess",
        valueType = ValueType.String,
        comment =
          "Accesses a container (e.g. array or associative array / dict / map) at an index. The index selection works the same way as for fieldAccess. Handling of this operator is special-cased in the back-end"
      ),
      Constant(
        name = "indirectIndexAccess",
        value = "<operator>.indirectIndexAccess",
        valueType = ValueType.String,
        comment =
          "Accesses an element of an Array through a pointer. The index selection works similar as for fieldAccess: However, the index must be an integer. If the second argument is a FIELD_ACCESS resp. LITERAL then its CANONICAL_NAME resp. CODE field is parsed as an integer; on parsing failure, the access is considered unpredictable and we overtaint. This is equivalent to a pointerShift followed by an indirection. This operator is currently only used by C-style languages. This behavior allows the backend to infer that ptr[0] and *ptr refer to the same object. Handling of this operator is special-cased in the back-end"
      ),
      Constant(
        name = "pointerShift",
        value = "<operator>.pointerShift",
        valueType = ValueType.String,
        comment =
          "Shifts a pointer. In terms of CPG, the first argument is the pointer and the second argument is the index. The index selection works the same way as for indirectIndexAccess. This operator is currently only used directly by the LLVM language, but it is also used internally for C. For example, pointerShift(ptr, 7) is equivalent to &(ptr[7]). Handling of this operator is special-cased in the back-end"
      ),
      Constant(
        name = "getElementPtr",
        value = "<operator>.getElementPtr",
        valueType = ValueType.String,
        comment =
          "Derives a pointer to a field of a struct from a pointer to the entire struct. This corresponds to the C idiom &(ptr->field), which does not access memory. The field selection works the same way as for fieldAccess. This operator is currently only used directly by the LLVM language. Handling of this operator is special-cased in the back-end"
      ),
    )

  }

}
