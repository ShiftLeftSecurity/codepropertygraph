package io.shiftleft.codepropertygraph.generated;

import overflowdb.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DispatchTypes {

 /** For statically dispatched calls the call target is known before program execution */
 public static final String STATIC_DISPATCH = "STATIC_DISPATCH";

 /** For dynamically dispatched calls the target is determined during runtime */
 public static final String DYNAMIC_DISPATCH = "DYNAMIC_DISPATCH";


 public static Set<String> ALL = new HashSet<String>() {{
     add(STATIC_DISPATCH);
     add(DYNAMIC_DISPATCH);
 }};

}