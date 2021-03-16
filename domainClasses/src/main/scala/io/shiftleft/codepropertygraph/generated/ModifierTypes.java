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


 public static Set<String> ALL = new HashSet<String>() {{
     add(STATIC);
     add(PUBLIC);
     add(PROTECTED);
     add(PRIVATE);
     add(ABSTRACT);
     add(NATIVE);
     add(CONSTRUCTOR);
     add(VIRTUAL);
 }};

}