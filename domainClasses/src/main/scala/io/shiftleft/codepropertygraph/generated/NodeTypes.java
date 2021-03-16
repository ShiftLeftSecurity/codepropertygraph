package io.shiftleft.codepropertygraph.generated;

import overflowdb.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class NodeTypes {

 /** Node to save meta data about the graph on its properties. Exactly one node of this type per graph */
 public static final String META_DATA = "META_DATA";

 /** Node representing a source file - the root of the AST */
 public static final String FILE = "FILE";

 /** A method/function/procedure */
 public static final String METHOD = "METHOD";

 /** This node represents a formal parameter going towards the callee side */
 public static final String METHOD_PARAMETER_IN = "METHOD_PARAMETER_IN";

 /** A formal method return */
 public static final String METHOD_RETURN = "METHOD_RETURN";

 /** A modifier, e.g., static, public, private */
 public static final String MODIFIER = "MODIFIER";

 /** A type which always has to reference a type declaration and may have type argument children if the referred to type declaration is a template */
 public static final String TYPE = "TYPE";

 /** A type declaration */
 public static final String TYPE_DECL = "TYPE_DECL";

 /** Type parameter of TYPE_DECL or METHOD */
 public static final String TYPE_PARAMETER = "TYPE_PARAMETER";

 /** Argument for a TYPE_PARAMETER that belongs to a TYPE. It binds another TYPE to a TYPE_PARAMETER */
 public static final String TYPE_ARGUMENT = "TYPE_ARGUMENT";

 /** Member of a class struct or union */
 public static final String MEMBER = "MEMBER";

 /** A reference to a namespace */
 public static final String NAMESPACE_BLOCK = "NAMESPACE_BLOCK";

 /** Literal/Constant */
 public static final String LITERAL = "LITERAL";

 /** A (method)-call */
 public static final String CALL = "CALL";

 /** A local variable */
 public static final String LOCAL = "LOCAL";

 /** An arbitrary identifier/reference */
 public static final String IDENTIFIER = "IDENTIFIER";

 /** A node that represents which field is accessed in a <operator>.fieldAccess, in e.g. obj.field. The CODE part is used for human display and matching to MEMBER nodes. The CANONICAL_NAME is used for dataflow tracking; typically both coincide. However, suppose that two fields foo and bar are a C-style union; then CODE refers to whatever the programmer wrote (obj.foo or obj.bar), but both share the same CANONICAL_NAME (e.g. GENERATED_foo_bar) */
 public static final String FIELD_IDENTIFIER = "FIELD_IDENTIFIER";

 /** A return instruction */
 public static final String RETURN = "RETURN";

 /** A structuring block in the AST */
 public static final String BLOCK = "BLOCK";

 /** A method instance which always has to reference a method and may have type argument children if the referred to method is a template */
 public static final String METHOD_INST = "METHOD_INST";

 /** Initialization construct for arrays */
 public static final String ARRAY_INITIALIZER = "ARRAY_INITIALIZER";

 /** Reference to a method instance */
 public static final String METHOD_REF = "METHOD_REF";

 /** Reference to a type/class */
 public static final String TYPE_REF = "TYPE_REF";

 /** A control structure such as if, while, or for */
 public static final String CONTROL_STRUCTURE = "CONTROL_STRUCTURE";

 /** A jump target made explicit in the code using a label */
 public static final String JUMP_TARGET = "JUMP_TARGET";

 /** A language-specific node */
 public static final String UNKNOWN = "UNKNOWN";

 /** A binding of a METHOD into a TYPE_DECL */
 public static final String BINDING = "BINDING";

 /** An implicit call site hidden in a method indicated by METHOD_MAP policy entries */
 public static final String IMPLICIT_CALL = "IMPLICIT_CALL";

 /** Indicates the existence of a call executed on a return value or out parameter of a method after this method has been executed. This is used to model framework code calling functors returned from user code. The outgoing REF edge indicates on which returned entitity the call will happen. */
 public static final String POST_EXECUTION_CALL = "POST_EXECUTION_CALL";

 /** A string tag */
 public static final String TAG = "TAG";

 /** This node represents a namespace as a whole whereas the NAMESPACE_BLOCK is used for each grouping occurrence of a namespace in code. Single representing NAMESPACE node is required for easier navigation in the query language */
 public static final String NAMESPACE = "NAMESPACE";

 /** This node represents a formal parameter going towards the caller side */
 public static final String METHOD_PARAMETER_OUT = "METHOD_PARAMETER_OUT";

 /** A method annotation */
 public static final String ANNOTATION = "ANNOTATION";

 /** Assignment of annotation argument to annotation parameter */
 public static final String ANNOTATION_PARAMETER_ASSIGN = "ANNOTATION_PARAMETER_ASSIGN";

 /** Formal annotation parameter */
 public static final String ANNOTATION_PARAMETER = "ANNOTATION_PARAMETER";

 /** A literal value assigned to an ANNOTATION_PARAMETER */
 public static final String ANNOTATION_LITERAL = "ANNOTATION_LITERAL";

 /** Configuration file contents. Might be in use by a framework */
 public static final String CONFIG_FILE = "CONFIG_FILE";

 /** Represents the binding of a LOCAL or METHOD_PARAMETER_IN into the closure of a method */
 public static final String CLOSURE_BINDING = "CLOSURE_BINDING";

 /** This node represents a dependency */
 public static final String DEPENDENCY = "DEPENDENCY";

 /** A node in a Document Object Model (Tree) as obtained from, e.g., an HTML parser */
 public static final String DOM_NODE = "DOM_NODE";

 /** Attribute of a DOM node */
 public static final String DOM_ATTRIBUTE = "DOM_ATTRIBUTE";

 /** Multiple tags */
 public static final String TAGS = "TAGS";

 /** Indicates the usage of a framework. E.g. java spring. The name key is one of the values from frameworks list */
 public static final String FRAMEWORK = "FRAMEWORK";

 /** Data used by a framework */
 public static final String FRAMEWORK_DATA = "FRAMEWORK_DATA";

 
 public static final String DETACHED_TRACKING_POINT = "DETACHED_TRACKING_POINT";

 
 public static final String FINDING = "FINDING";

 
 public static final String KEY_VALUE_PAIR = "KEY_VALUE_PAIR";

 /** A comment */
 public static final String COMMENT = "COMMENT";

 /** This node records what package prefix is most common to all analysed classes in the CPG */
 public static final String PACKAGE_PREFIX = "PACKAGE_PREFIX";

 
 public static final String LOCATION = "LOCATION";

 
 public static final String TAG_NODE_PAIR = "TAG_NODE_PAIR";

 
 public static final String SOURCE = "SOURCE";

 
 public static final String SINK = "SINK";


 public static Set<String> ALL = new HashSet<String>() {{
     add(META_DATA);
     add(FILE);
     add(METHOD);
     add(METHOD_PARAMETER_IN);
     add(METHOD_RETURN);
     add(MODIFIER);
     add(TYPE);
     add(TYPE_DECL);
     add(TYPE_PARAMETER);
     add(TYPE_ARGUMENT);
     add(MEMBER);
     add(NAMESPACE_BLOCK);
     add(LITERAL);
     add(CALL);
     add(LOCAL);
     add(IDENTIFIER);
     add(FIELD_IDENTIFIER);
     add(RETURN);
     add(BLOCK);
     add(METHOD_INST);
     add(ARRAY_INITIALIZER);
     add(METHOD_REF);
     add(TYPE_REF);
     add(CONTROL_STRUCTURE);
     add(JUMP_TARGET);
     add(UNKNOWN);
     add(BINDING);
     add(IMPLICIT_CALL);
     add(POST_EXECUTION_CALL);
     add(TAG);
     add(NAMESPACE);
     add(METHOD_PARAMETER_OUT);
     add(ANNOTATION);
     add(ANNOTATION_PARAMETER_ASSIGN);
     add(ANNOTATION_PARAMETER);
     add(ANNOTATION_LITERAL);
     add(CONFIG_FILE);
     add(CLOSURE_BINDING);
     add(DEPENDENCY);
     add(DOM_NODE);
     add(DOM_ATTRIBUTE);
     add(TAGS);
     add(FRAMEWORK);
     add(FRAMEWORK_DATA);
     add(DETACHED_TRACKING_POINT);
     add(FINDING);
     add(KEY_VALUE_PAIR);
     add(COMMENT);
     add(PACKAGE_PREFIX);
     add(LOCATION);
     add(TAG_NODE_PAIR);
     add(SOURCE);
     add(SINK);
 }};

}