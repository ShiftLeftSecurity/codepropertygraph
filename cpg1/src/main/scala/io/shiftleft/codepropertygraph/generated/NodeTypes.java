package io.shiftleft.codepropertygraph.generated;

import gremlin.scala.Key;

public class NodeTypes {

/** A method annotation */
public static final String ANNOTATION = "ANNOTATION";

/** A literal value assigned to an ANNOTATION_PARAMETER */
public static final String ANNOTATION_LITERAL = "ANNOTATION_LITERAL";

/** Formal annotation parameter */
public static final String ANNOTATION_PARAMETER = "ANNOTATION_PARAMETER";

/** Assignment of annotation argument to annotation parameter */
public static final String ANNOTATION_PARAMETER_ASSIGN = "ANNOTATION_PARAMETER_ASSIGN";

/** Initialization construct for arrays */
public static final String ARRAY_INITIALIZER = "ARRAY_INITIALIZER";

/** A structuring block in the AST */
public static final String BLOCK = "BLOCK";

/** A (method)-call */
public static final String CALL = "CALL";

/**  */
public static final String CALL_CHAIN = "CALL_CHAIN";

/**  */
public static final String CALL_SITE = "CALL_SITE";

/** Represents the binding of a LOCAL or METHOD_PARAMETER_IN into the closure of a method */
public static final String CLOSURE_BINDING = "CLOSURE_BINDING";

/** A comment */
public static final String COMMENT = "COMMENT";

/** Configuration file contents. Might be in use by a framework */
public static final String CONFIG_FILE = "CONFIG_FILE";

/** This node represents a dependency */
public static final String DEPENDENCY = "DEPENDENCY";

/**  */
public static final String DETACHED_TRACKING_POINT = "DETACHED_TRACKING_POINT";

/** Node representing a source file. Often also the AST root */
public static final String FILE = "FILE";

/**  */
public static final String FINDING = "FINDING";

/**  */
public static final String FLOW = "FLOW";

/** Indicates the usage of a framework. E.g. java spring. The name key is one of the values from frameworks list */
public static final String FRAMEWORK = "FRAMEWORK";

/** Data used by a framework */
public static final String FRAMEWORK_DATA = "FRAMEWORK_DATA";

/** An arbitrary identifier/reference */
public static final String IDENTIFIER = "IDENTIFIER";

/**  */
public static final String IOFLOW = "IOFLOW";

/** Literal/Constant */
public static final String LITERAL = "LITERAL";

/** A local variable */
public static final String LOCAL = "LOCAL";

/**  */
public static final String LOCATION = "LOCATION";

/**  */
public static final String MATCH_INFO = "MATCH_INFO";

/** Member of a class struct or union */
public static final String MEMBER = "MEMBER";

/** Node to save meta data about the graph on its properties. Exactly one node of this type per graph */
public static final String META_DATA = "META_DATA";

/** A method/function/procedure */
public static final String METHOD = "METHOD";

/** A method instance which always has to reference a method and may have type argument children if the referred to method is a template */
public static final String METHOD_INST = "METHOD_INST";

/** This node represents a formal parameter going towards the callee side */
public static final String METHOD_PARAMETER_IN = "METHOD_PARAMETER_IN";

/** This node represents a formal parameter going towards the caller side */
public static final String METHOD_PARAMETER_OUT = "METHOD_PARAMETER_OUT";

/** Reference to a method instance */
public static final String METHOD_REF = "METHOD_REF";

/** A formal method return */
public static final String METHOD_RETURN = "METHOD_RETURN";

/**  */
public static final String METHOD_SUMMARY = "METHOD_SUMMARY";

/** The static-modifier */
public static final String MODIFIER = "MODIFIER";

/** This node represents a namespace as a whole whereas the NAMESPACE_BLOCK is used for each grouping occurrence of a namespace in code. Single representing NAMESPACE node is required for easier navigation in the query language */
public static final String NAMESPACE = "NAMESPACE";

/** A reference to a namespace */
public static final String NAMESPACE_BLOCK = "NAMESPACE_BLOCK";

/**  */
public static final String PROGRAM_POINT = "PROGRAM_POINT";

/**  */
public static final String READ = "READ";

/** A return instruction */
public static final String RETURN = "RETURN";

/**  */
public static final String ROUTE = "ROUTE";

/**  */
public static final String SENSITIVE_DATA_TYPE = "SENSITIVE_DATA_TYPE";

/**  */
public static final String SENSITIVE_MEMBER = "SENSITIVE_MEMBER";

/**  */
public static final String SENSITIVE_REFERENCE = "SENSITIVE_REFERENCE";

/**  */
public static final String SENSITIVE_VARIABLE = "SENSITIVE_VARIABLE";

/**  */
public static final String SINK = "SINK";

/**  */
public static final String SOURCE = "SOURCE";

/**  */
public static final String SP_ANNOTATION_PARAMETER = "SP_ANNOTATION_PARAMETER";

/**  */
public static final String SP_BLACKLIST = "SP_BLACKLIST";

/** A string tag */
public static final String TAG = "TAG";

/** Multiple tags */
public static final String TAGS = "TAGS";

/**  */
public static final String TAG_NODE_PAIR = "TAG_NODE_PAIR";

/**  */
public static final String TRANSFORM = "TRANSFORM";

/**  */
public static final String TRANSFORMATION = "TRANSFORMATION";

/** A type which always has to reference a type declaration and may have type argument children if the referred to type declaration is a template */
public static final String TYPE = "TYPE";

/** Argument for a TYPE_PARAMETER that belongs to a TYPE or METHOD_INST. It binds another TYPE to a TYPE_PARAMETER */
public static final String TYPE_ARGUMENT = "TYPE_ARGUMENT";

/** A type declaration */
public static final String TYPE_DECL = "TYPE_DECL";

/** Type parameter of TYPE_DECL or METHOD */
public static final String TYPE_PARAMETER = "TYPE_PARAMETER";

/** A language-specific node */
public static final String UNKNOWN = "UNKNOWN";

/**  */
public static final String VARIABLE_INFO = "VARIABLE_INFO";

/**  */
public static final String WRITE = "WRITE";

/** This node records what package prefix is most common to all analysed classes in the CPG */
public static final String PACKAGE_PREFIX = "PACKAGE_PREFIX";


}
