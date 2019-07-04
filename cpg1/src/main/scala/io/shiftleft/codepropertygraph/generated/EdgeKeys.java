package io.shiftleft.codepropertygraph.generated;

import gremlin.scala.Key;

public class EdgeKeys {

/** Defines whether a PROPAGATE edge creates an alias */
public static final Key<Boolean> ALIAS = new Key<>("ALIAS");

/** The condition result under which a CFG edge is followed */
public static final Key<Boolean> CONDITION = new Key<>("CONDITION");

/** Index of referenced CONTAINED node (0 based) - used together with cardinality=list */
public static final Key<Integer> INDEX = new Key<>("INDEX");

/** Local name of referenced CONTAINED node */
public static final Key<String> LOCAL_NAME = new Key<>("LOCAL_NAME");


}
