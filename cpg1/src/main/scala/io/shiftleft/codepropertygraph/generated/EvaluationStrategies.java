package io.shiftleft.codepropertygraph.generated;

import gremlin.scala.Key;

public class EvaluationStrategies {

/** A parameter or return of a function is passed by reference which means an address is used behind the scenes */
public static final String BY_REFERENCE = "BY_REFERENCE";

/** Only applicable to object parameter or return values. The pointer to the object is passed by value but the object itself is not copied and changes to it are thus propagated out of the method context */
public static final String BY_SHARING = "BY_SHARING";

/** A parameter or return of a function passed by value which means a flat copy is used */
public static final String BY_VALUE = "BY_VALUE";


}
