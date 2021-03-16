package io.shiftleft.codepropertygraph.generated;

import overflowdb.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ControlStructureTypes {

 /** Represents a break statement */
 public static final String BREAK = "BREAK";

 /** Represents a continue statement */
 public static final String CONTINUE = "CONTINUE";

 /** Represents a while statement */
 public static final String WHILE = "WHILE";

 /** Represents a do statement */
 public static final String DO = "DO";

 /** Represents a for statement */
 public static final String FOR = "FOR";

 /** Represents a goto statement */
 public static final String GOTO = "GOTO";

 /** Represents an if statement */
 public static final String IF = "IF";

 /** Represents an else statement */
 public static final String ELSE = "ELSE";

 /** Represents a switch statement */
 public static final String SWITCH = "SWITCH";

 /** Represents a try statement */
 public static final String TRY = "TRY";


 public static Set<String> ALL = new HashSet<String>() {{
     add(BREAK);
     add(CONTINUE);
     add(WHILE);
     add(DO);
     add(FOR);
     add(GOTO);
     add(IF);
     add(ELSE);
     add(SWITCH);
     add(TRY);
 }};

}