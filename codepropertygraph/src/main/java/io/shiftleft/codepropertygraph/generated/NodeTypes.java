package io.shiftleft.codepropertygraph.generated;

import gremlin.scala.Key;

public class NodeTypes {

/** Initialization contruct for arrays. */
public static final String ARRAY_INITIALIZER = "ARRAY_INITIALIZER";

/** A structuring block in the AST. */
public static final String BLOCK = "BLOCK";

/** A (method)-call */
public static final String CALL = "CALL";

/** Represents the binding of a LOCAL or METHOD_PARAMETER_IN into the closure of a method. */
public static final String CLOSURE_BINDING = "CLOSURE_BINDING";

/** Node presenting a source file. Often also the AST root. */
public static final String FILE = "FILE";

/** An arbitrary identifier/reference. */
public static final String IDENTIFIER = "IDENTIFIER";

/** Literal/Constant */
public static final String LITERAL = "LITERAL";

/** A local variable */
public static final String LOCAL = "LOCAL";

/** Member of a class struct or union */
public static final String MEMBER = "MEMBER";

/** Node to save meta data about the graph on its properties. Exactly one node of this type per graph. */
public static final String META_DATA = "META_DATA";

/** A method/function/procedure */
public static final String METHOD = "METHOD";

/** A method instance which always has to reference a method and may have type argument children if the refered method is a template. */
public static final String METHOD_INST = "METHOD_INST";

/** This node represents a formal parameter going towards the callee side. */
public static final String METHOD_PARAMETER_IN = "METHOD_PARAMETER_IN";

/** This node represents a formal parameter going towards the caller side. */
public static final String METHOD_PARAMETER_OUT = "METHOD_PARAMETER_OUT";

/** Reference to a method instance. */
public static final String METHOD_REF = "METHOD_REF";

/** A formal method return */
public static final String METHOD_RETURN = "METHOD_RETURN";

/** The static-modifier */
public static final String MODIFIER = "MODIFIER";

/** This node represents a namespace as a hole whereas the NAMESPACE_BLOCK is used for each grouping occurrence of a namespace in code. Single representing NAMESPACE node is required for easier navigation in the query language. */
public static final String NAMESPACE = "NAMESPACE";

/** A reference to a namespace. */
public static final String NAMESPACE_BLOCK = "NAMESPACE_BLOCK";

/** A return instruction. */
public static final String RETURN = "RETURN";

/** A string tag. */
public static final String TAG = "TAG";

/** A type which always has to reference a type declaration and may have type argument children if the refered type declaration is a template. */
public static final String TYPE = "TYPE";

/** Argument for a TYPE_PARAMETER and belonging to a TYPE or METHOD_INST. It binds another TYPE to a TYPE_PARAMETER. */
public static final String TYPE_ARGUMENT = "TYPE_ARGUMENT";

/** A type declaration. */
public static final String TYPE_DECL = "TYPE_DECL";

/** Type parameter of TYPE_DECL or METHOD. */
public static final String TYPE_PARAMETER = "TYPE_PARAMETER";

/** A language-specific node */
public static final String UNKNOWN = "UNKNOWN";


}
