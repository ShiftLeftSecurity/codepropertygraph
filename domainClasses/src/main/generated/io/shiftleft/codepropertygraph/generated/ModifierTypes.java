package io.shiftleft.codepropertygraph.generated;

import overflowdb.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ModifierTypes {

/** The static modifier */
public static final String STATIC = "STATIC";

/** The public modifier */
public static final String PUBLIC = "PUBLIC";

/** The protected modifier */
public static final String PROTECTED = "PROTECTED";

/** The private modifier */
public static final String PRIVATE = "PRIVATE";

/** The abstract modifier */
public static final String ABSTRACT = "ABSTRACT";

/** The native modifier */
public static final String NATIVE = "NATIVE";

/** The constructor modifier */
public static final String CONSTRUCTOR = "CONSTRUCTOR";

/** The virtual modifier */
public static final String VIRTUAL = "VIRTUAL";

/** The internal modifier */
public static final String INTERNAL = "INTERNAL";

/** The final modifier */
public static final String FINAL = "FINAL";

/** The readonly modifier */
public static final String READONLY = "READONLY";

/** Indicate that a method defines a module in the sense e.g. a python module does with the creation of a module object */
public static final String MODULE = "MODULE";

/** Indicate that a method is an anonymous function, lambda, or closure */
public static final String LAMBDA = "LAMBDA";


public static Set<String> ALL = new HashSet<String>() {{
add(STATIC);
add(PUBLIC);
add(PROTECTED);
add(PRIVATE);
add(ABSTRACT);
add(NATIVE);
add(CONSTRUCTOR);
add(VIRTUAL);
add(INTERNAL);
add(FINAL);
add(READONLY);
add(MODULE);
add(LAMBDA);
}};

}