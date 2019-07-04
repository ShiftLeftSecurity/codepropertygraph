package io.shiftleft.codepropertygraph.generated;

import gremlin.scala.Key;

public class Operators {

/**  */
public static final String addition = "<operator>.addition";

/**  */
public static final String subtraction = "<operator>.subtraction";

/**  */
public static final String multiplication = "<operator>.multiplication";

/**  */
public static final String division = "<operator>.division";

/**  */
public static final String exponentiation = "<operator>.exponentiation";

/**  */
public static final String modulo = "<operator>.modulo";

/**  */
public static final String shiftLeft = "<operator>.shiftLeft";

/** Shift right padding with zeros */
public static final String logicalShiftRight = "<operator>.logicalShiftRight";

/** Shift right preserving the sign */
public static final String arithmeticShiftRight = "<operator>.arithmeticShiftRight";

/**  */
public static final String not = "<operator>.not";

/**  */
public static final String and = "<operator>.and";

/**  */
public static final String or = "<operator>.or";

/**  */
public static final String xor = "<operator>.xor";

/**  */
public static final String assignmentPlus = "<operator>.assignmentPlus";

/**  */
public static final String assignmentMinus = "<operator>.assignmentMinus";

/**  */
public static final String assignmentMultiplication = "<operator>.assignmentMultiplication";

/**  */
public static final String assignmentDivision = "<operator>.assignmentDivision";

/**  */
public static final String assignmentExponentiation = "<operators>.assignmentExponentiation";

/**  */
public static final String assignmentModulo = "<operators>.assignmentModulo";

/**  */
public static final String assignmentShiftLeft = "<operators>.assignmentShiftLeft";

/**  */
public static final String assignmentLogicalShifRight = "<operators>.assignmentLogicalShifRight";

/**  */
public static final String assignmentArithmeticShiftRight = "<operators>.assignmentArithmeticShiftRight";

/**  */
public static final String assignmentAnd = "<operators>.assignmentAnd";

/**  */
public static final String assignmentOr = "<operators>.assignmentOr";

/**  */
public static final String assignmentXor = "<operators>.assignmentXor";

/**  */
public static final String assignment = "<operator>.assignment";

/** E.g. `a = -b` */
public static final String minus = "<operator>.minus";

/** E.g. `a = +b` */
public static final String plus = "<operator>.plus";

/**  */
public static final String preIncrement = "<operator>.preIncrement";

/**  */
public static final String preDecrement = "<operator>.preDecrement";

/**  */
public static final String postIncrement = "<operator>.postIncrement";

/**  */
public static final String postDecrement = "<operator>.postDecrement";

/**  */
public static final String logicalNot = "<operator>.logicalNot";

/**  */
public static final String logicalOr = "<operator>.logicalOr";

/**  */
public static final String logicalAnd = "<operator>.logicalAnd";

/**  */
public static final String equals = "<operator>.equals";

/**  */
public static final String notEquals = "<operator>.notEquals";

/**  */
public static final String greaterThan = "<operator>.greaterThan";

/**  */
public static final String lessThan = "<operator>.lessThan";

/**  */
public static final String greaterEqualsThan = "<operator>.greaterEqualsThan";

/**  */
public static final String lessEqualsThan = "<operator>.lessEqualsThan";

/**  */
public static final String instanceOf = "<operator>.instanceOf";

/** E.g. in C: `a.b` but not! in Java */
public static final String memberAccess = "<operator>.memberAccess";

/** E.g. in C: `a->b` and `a.b` in Java */
public static final String indirectMemberAccess = "<operator>.indirectMemberAccess";

/** E.g. in C: `a[b]` but not! in Java */
public static final String computedMemberAccess = "<operator>.computedMemberAccess";

/** E.g. in C++: `a->*b` and a[b] in Java */
public static final String indirectComputedMemberAccess = "<operator>.indirectComputedMemberAccess";

/** E.g. in C: `*a` */
public static final String indirection = "<operator>.indirection";

/** Deletes a property from a namespace. E.g. `a=3; delete a; a == undefined; */
public static final String delete = "<operator>.delete";

/** E.g. `a ? consequent : alternate`. In future probably also used for if statements */
public static final String conditional = "<operator>.conditional";

/** Type casts of any sort */
public static final String cast = "<operator>.cast";

/** Comparison between two arguments with the results: 0 == equal, negative == left < right, positive == left > right */
public static final String compare = "<operator>.compare";

/** Returns the address of a given object */
public static final String addressOf = "<operator>.addressOf";

/** Returns the size of a given object */
public static final String sizeOf = "<operator>.sizeOf";


}
