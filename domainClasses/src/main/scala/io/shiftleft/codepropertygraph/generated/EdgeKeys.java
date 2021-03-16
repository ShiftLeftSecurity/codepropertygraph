package io.shiftleft.codepropertygraph.generated;

import overflowdb.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class EdgeKeys {

 /** Local name of referenced CONTAINED node. This key is deprecated. */
 public static final overflowdb.PropertyKey<String> LOCAL_NAME = new overflowdb.PropertyKey<>("LOCAL_NAME");

 /** Index of referenced CONTAINED node (0 based) - used together with cardinality=list. This key is deprecated. */
 public static final overflowdb.PropertyKey<java.lang.Integer> INDEX = new overflowdb.PropertyKey<>("INDEX");

 /** Defines whether a PROPAGATE edge creates an alias */
 public static final overflowdb.PropertyKey<java.lang.Boolean> ALIAS = new overflowdb.PropertyKey<>("ALIAS");

 /** A variable propagated by a reaching-def edge */
 public static final overflowdb.PropertyKey<String> VARIABLE = new overflowdb.PropertyKey<>("VARIABLE");


 public static Set<PropertyKey> ALL = new HashSet<PropertyKey>() {{
     add(LOCAL_NAME);
     add(INDEX);
     add(ALIAS);
     add(VARIABLE);
 }};

}