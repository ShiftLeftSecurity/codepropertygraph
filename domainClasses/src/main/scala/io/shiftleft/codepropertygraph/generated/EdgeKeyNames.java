package io.shiftleft.codepropertygraph.generated;

import overflowdb.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class EdgeKeyNames {

 /** Local name of referenced CONTAINED node. This key is deprecated. */
 public static final String LOCAL_NAME = "LOCAL_NAME";

 /** Index of referenced CONTAINED node (0 based) - used together with cardinality=list. This key is deprecated. */
 public static final String INDEX = "INDEX";

 /** Defines whether a PROPAGATE edge creates an alias */
 public static final String ALIAS = "ALIAS";

 /** A variable propagated by a reaching-def edge */
 public static final String VARIABLE = "VARIABLE";


 public static Set<String> ALL = new HashSet<String>() {{
     add(LOCAL_NAME);
     add(INDEX);
     add(ALIAS);
     add(VARIABLE);
 }};

}