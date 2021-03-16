package io.shiftleft.codepropertygraph.generated;

import overflowdb.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Operators {

 
 public static final String addition = "<operator>.addition";

 
 public static final String subtraction = "<operator>.subtraction";

 
 public static final String multiplication = "<operator>.multiplication";

 
 public static final String division = "<operator>.division";

 
 public static final String exponentiation = "<operator>.exponentiation";

 
 public static final String modulo = "<operator>.modulo";

 
 public static final String shiftLeft = "<operator>.shiftLeft";

 /** Shift right padding with zeros */
 public static final String logicalShiftRight = "<operator>.logicalShiftRight";

 /** Shift right preserving the sign */
 public static final String arithmeticShiftRight = "<operator>.arithmeticShiftRight";

 
 public static final String not = "<operator>.not";

 
 public static final String and = "<operator>.and";

 
 public static final String or = "<operator>.or";

 
 public static final String xor = "<operator>.xor";

 
 public static final String assignmentPlus = "<operator>.assignmentPlus";

 
 public static final String assignmentMinus = "<operator>.assignmentMinus";

 
 public static final String assignmentMultiplication = "<operator>.assignmentMultiplication";

 
 public static final String assignmentDivision = "<operator>.assignmentDivision";

 
 public static final String assignmentExponentiation = "<operators>.assignmentExponentiation";

 
 public static final String assignmentModulo = "<operators>.assignmentModulo";

 
 public static final String assignmentShiftLeft = "<operators>.assignmentShiftLeft";

 
 public static final String assignmentLogicalShiftRight = "<operators>.assignmentLogicalShiftRight";

 
 public static final String assignmentArithmeticShiftRight = "<operators>.assignmentArithmeticShiftRight";

 
 public static final String assignmentAnd = "<operators>.assignmentAnd";

 
 public static final String assignmentOr = "<operators>.assignmentOr";

 
 public static final String assignmentXor = "<operators>.assignmentXor";

 
 public static final String assignment = "<operator>.assignment";

 /** E.g. `a = -b` */
 public static final String minus = "<operator>.minus";

 /** E.g. `a = +b` */
 public static final String plus = "<operator>.plus";

 
 public static final String preIncrement = "<operator>.preIncrement";

 
 public static final String preDecrement = "<operator>.preDecrement";

 
 public static final String postIncrement = "<operator>.postIncrement";

 
 public static final String postDecrement = "<operator>.postDecrement";

 
 public static final String logicalNot = "<operator>.logicalNot";

 
 public static final String logicalOr = "<operator>.logicalOr";

 
 public static final String logicalAnd = "<operator>.logicalAnd";

 
 public static final String equals = "<operator>.equals";

 
 public static final String notEquals = "<operator>.notEquals";

 
 public static final String greaterThan = "<operator>.greaterThan";

 
 public static final String lessThan = "<operator>.lessThan";

 
 public static final String greaterEqualsThan = "<operator>.greaterEqualsThan";

 
 public static final String lessEqualsThan = "<operator>.lessEqualsThan";

 
 public static final String instanceOf = "<operator>.instanceOf";

 /** Deprecated. Formerly in C: `a.b` but not! in Java */
 public static final String memberAccess = "<operator>.memberAccess";

 /** Deprecated. Formerly in C: `a->b` and `a.b` in Java */
 public static final String indirectMemberAccess = "<operator>.indirectMemberAccess";

 /** Deprecated. Formerly in C: `a[b]` but not! in Java */
 public static final String computedMemberAccess = "<operator>.computedMemberAccess";

 /** Deprecated. Formerly in C++: `a->*b` and a[b] in Java */
 public static final String indirectComputedMemberAccess = "<operator>.indirectComputedMemberAccess";

 /** Accesses through a pointer (load/store), i.e. dereferences it. Typically the star-operator in C/C++. Pairs of addressOf and indirection operators cancel each other. Handling of this operator is special-cased in the back-end */
 public static final String indirection = "<operator>.indirection";

 /** Deletes a property from a namespace. E.g. `a=3; delete a; a == undefined; */
 public static final String delete = "<operator>.delete";

 /** E.g. `a ? consequent : alternate`. In future probably also used for if statements */
 public static final String conditional = "<operator>.conditional";

 /** Type casts of any sort */
 public static final String cast = "<operator>.cast";

 /** Comparison between two arguments with the results: 0 == equal, negative == left < right, positive == left > right */
 public static final String compare = "<operator>.compare";

 /** Returns the address of a given object. Pairs of addressOf and indirection operators cancel each other. Handling of this operator is special-cased in the back-end */
 public static final String addressOf = "<operator>.addressOf";

 /** Returns the size of a given object */
 public static final String sizeOf = "<operator>.sizeOf";

 /** Returns or sets a field/member of a struct/class. Typically, the dot-operator. In terms of CPG, the first argument is the struct/class and the second argument is either a FIELD_IDENTIFIER node, a LITERAL node or an arbitrary expression. If it is a FIELD_IDENTIFIER, then the string contained in the CANONICAL_NAME field of this FIELD_IDENTIFIER node selects which field/member is accessed. If it is a LITERAL node, then its CODE selects which field/member is selected. In any other case the access is considered unpredictable by the backend, and we overtaint. indexAccess and fieldAccess live in the same space, such that e.g. obj.field and obj["field"] refer to the same target (as is correct in e.g. javascript). Handling of this operator is special-cased in the back-end */
 public static final String fieldAccess = "<operator>.fieldAccess";

 /** Accesses (loads/stores) to a field/member through a pointer to a struct/class. Typically, C/C++ arrow-operator. The field selection works the same way as for fieldAccess. For the sake of data-flow tracking, this is the same as first dereferencing the pointer and then accessing the field. Handling of this operator is special-cased in the back-end */
 public static final String indirectFieldAccess = "<operator>.indirectFieldAccess";

 /** Accesses a container (e.g. array or associative array / dict / map) at an index. The index selection works the same way as for fieldAccess. Handling of this operator is special-cased in the back-end */
 public static final String indexAccess = "<operator>.indexAccess";

 /** Accesses an element of an Array through a pointer. The index selection works similar as for fieldAccess: However, the index must be an integer. If the second argument is a FIELD_ACCESS resp. LITERAL then its CANONICAL_NAME resp. CODE field is parsed as an integer; on parsing failure, the access is considered unpredictable and we overtaint. This is equivalent to a pointerShift followed by an indirection. This operator is currently only used by C-style languages. This behavior allows the backend to infer that ptr[0] and *ptr refer to the same object. Handling of this operator is special-cased in the back-end */
 public static final String indirectIndexAccess = "<operator>.indirectIndexAccess";

 /** Shifts a pointer. In terms of CPG, the first argument is the pointer and the second argument is the index. The index selection works the same way as for indirectIndexAccess. This operator is currently only used directly by the LLVM language, but it is also used internally for C. For example, pointerShift(ptr, 7) is equivalent to &(ptr[7]). Handling of this operator is special-cased in the back-end */
 public static final String pointerShift = "<operator>.pointerShift";

 /** Derives a pointer to a field of a struct from a pointer to the entire struct. This corresponds to the C idiom &(ptr->field), which does not access memory. The field selection works the same way as for fieldAccess. This operator is currently only used directly by the LLVM language. Handling of this operator is special-cased in the back-end */
 public static final String getElementPtr = "<operator>.getElementPtr";


 public static Set<String> ALL = new HashSet<String>() {{
     add(addition);
     add(subtraction);
     add(multiplication);
     add(division);
     add(exponentiation);
     add(modulo);
     add(shiftLeft);
     add(logicalShiftRight);
     add(arithmeticShiftRight);
     add(not);
     add(and);
     add(or);
     add(xor);
     add(assignmentPlus);
     add(assignmentMinus);
     add(assignmentMultiplication);
     add(assignmentDivision);
     add(assignmentExponentiation);
     add(assignmentModulo);
     add(assignmentShiftLeft);
     add(assignmentLogicalShiftRight);
     add(assignmentArithmeticShiftRight);
     add(assignmentAnd);
     add(assignmentOr);
     add(assignmentXor);
     add(assignment);
     add(minus);
     add(plus);
     add(preIncrement);
     add(preDecrement);
     add(postIncrement);
     add(postDecrement);
     add(logicalNot);
     add(logicalOr);
     add(logicalAnd);
     add(equals);
     add(notEquals);
     add(greaterThan);
     add(lessThan);
     add(greaterEqualsThan);
     add(lessEqualsThan);
     add(instanceOf);
     add(memberAccess);
     add(indirectMemberAccess);
     add(computedMemberAccess);
     add(indirectComputedMemberAccess);
     add(indirection);
     add(delete);
     add(conditional);
     add(cast);
     add(compare);
     add(addressOf);
     add(sizeOf);
     add(fieldAccess);
     add(indirectFieldAccess);
     add(indexAccess);
     add(indirectIndexAccess);
     add(pointerShift);
     add(getElementPtr);
 }};

}