package io.shiftleft.codepropertygraph.generated;

import gremlin.scala.Key;

public class EdgeKeys {

/** The condition result under which a CFG edge is followed. */
public static final Key<Boolean> CONDITION = new Key<>("CONDITION");

/** index of referenced CONTAINED node (0 based) - used together with cardinality=list */
public static final Key<Integer> INDEX = new Key<>("INDEX");

/** localName of referenced CONTAINED node */
public static final Key<String> LOCAL_NAME = new Key<>("LOCAL_NAME");


}
