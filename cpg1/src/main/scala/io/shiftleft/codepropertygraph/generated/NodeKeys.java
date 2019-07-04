package io.shiftleft.codepropertygraph.generated;

import gremlin.scala.Key;

public class NodeKeys {

/**  */
public static final Key<Boolean> IS_STATIC = new Key<>("IS_STATIC");

/**  */
public static final Key<Integer> PARAMETER_INDEX = new Key<>("PARAMETER_INDEX");

/**  */
public static final Key<Integer> SCORE = new Key<>("SCORE");

/**  */
public static final Key<String> ANNOTATION_FULL_NAME = new Key<>("ANNOTATION_FULL_NAME");

/**  */
public static final Key<String> ANNOTATION_NAME = new Key<>("ANNOTATION_NAME");

/**  */
public static final Key<String> CATEGORY = new Key<>("CATEGORY");

/**  */
public static final Key<String> CLASS_NAME = new Key<>("CLASS_NAME");

/**  */
public static final Key<String> DESCRIPTION = new Key<>("DESCRIPTION");

/**  */
public static final Key<String> EVALUATION_TYPE = new Key<>("EVALUATION_TYPE");

/**  */
public static final Key<String> FILENAME = new Key<>("FILENAME");

/**  */
public static final Key<String> FINGERPRINT = new Key<>("FINGERPRINT");

/**  */
public static final Key<String> LINE_NO = new Key<>("LINE_NO");

/**  */
public static final Key<String> LINK = new Key<>("LINK");

/**  */
public static final Key<String> LITERALS_TO_SINK = new Key<>("LITERALS_TO_SINK");

/**  */
public static final Key<String> METHOD_NAME = new Key<>("METHOD_NAME");

/**  */
public static final Key<String> METHOD_SHORT_NAME = new Key<>("METHOD_SHORT_NAME");

/**  */
public static final Key<String> NODE_ID = new Key<>("NODE_ID");

/**  */
public static final Key<String> NODE_LABEL = new Key<>("NODE_LABEL");

/**  */
public static final Key<String> PACKAGE_NAME = new Key<>("PACKAGE_NAME");

/**  */
public static final Key<String> PARAMETER = new Key<>("PARAMETER");

/**  */
public static final Key<String> PATH = new Key<>("PATH");

/**  */
public static final Key<String> PATTERN = new Key<>("PATTERN");

/**  */
public static final Key<String> SINK_TYPE = new Key<>("SINK_TYPE");

/**  */
public static final Key<String> SOURCE_TYPE = new Key<>("SOURCE_TYPE");

/**  */
public static final Key<String> SPID = new Key<>("SPID");

/**  */
public static final Key<String> SYMBOL = new Key<>("SYMBOL");

/**  */
public static final Key<String> TITLE = new Key<>("TITLE");

/**  */
public static final Key<String> VAR_TYPE = new Key<>("VAR_TYPE");

/**  */
public static final Key<String> VULN_DESCR = new Key<>("VULN_DESCR");

/** A version, given as a string */
public static final Key<String> VERSION = new Key<>("VERSION");

/** Binary type signature */
public static final Key<String> BINARY_SIGNATURE = new Key<>("BINARY_SIGNATURE");

/** Column where the code ends */
public static final Key<Integer> COLUMN_NUMBER_END = new Key<>("COLUMN_NUMBER_END");

/** Column where the code starts */
public static final Key<Integer> COLUMN_NUMBER = new Key<>("COLUMN_NUMBER");

/** Content of CONFIG_FILE node */
public static final Key<String> CONTENT = new Key<>("CONTENT");

/** Evaluation strategy for function parameters and return values. One of the values in "evaluationStrategies" */
public static final Key<String> EVALUATION_STRATEGY = new Key<>("EVALUATION_STRATEGY");

/** Full name of an element, e.g., the class name along, including its package */
public static final Key<String> FULL_NAME = new Key<>("FULL_NAME");

/** General ordering property. E.g. used to express the ordering of children in the AST */
public static final Key<Integer> ORDER = new Key<>("ORDER");

/** Identifier which uniquely describes a CLOSURE_BINDING. This property is used to match captured LOCAL nodes with the corresponding CLOSURE_BINDING nodes */
public static final Key<String> CLOSURE_BINDING_ID = new Key<>("CLOSURE_BINDING_ID");

/** Indicates that the construct is external, that is, it is referenced but not defined in the code */
public static final Key<Boolean> IS_EXTERNAL = new Key<>("IS_EXTERNAL");

/** Indicates the modifier which is represented by a MODIFIER node. See modifierTypes */
public static final Key<String> MODIFIER_TYPE = new Key<>("MODIFIER_TYPE");

/** Indicates whether a call was already resolved. If not set this means not yet resolved */
public static final Key<Boolean> RESOLVED = new Key<>("RESOLVED");

/** Line where the code ends */
public static final Key<Integer> LINE_NUMBER_END = new Key<>("LINE_NUMBER_END");

/** Line where the code starts */
public static final Key<Integer> LINE_NUMBER = new Key<>("LINE_NUMBER");

/** Marks that a method has at least one mapping defined from the policies */
public static final Key<Boolean> HAS_MAPPING = new Key<>("HAS_MAPPING");

/** Method signature */
public static final Key<String> SIGNATURE = new Key<>("SIGNATURE");

/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** Strongly connected component(SCC) ID which is equal for all expressions in the same component. The SCC IDs start from 0 for each method */
public static final Key<Integer> SCC_ID = new Key<>("SCC_ID");

/** Tag value */
public static final Key<String> VALUE = new Key<>("VALUE");

/** The FULL_NAME of a method instance. Used to link CALL and METHOD_REF nodes to METHOD_INST nodes. There needs to be at least one METHOD_INST node for each METHOD_INST_FULL_NAME */
public static final Key<String> METHOD_INST_FULL_NAME = new Key<>("METHOD_INST_FULL_NAME");

/** The FULL_NAME of a method. Used to link METHOD_INST and METHOD nodes. It is required to have exactly one METHOD node for each METHOD_FULL_NAME */
public static final Key<String> METHOD_FULL_NAME = new Key<>("METHOD_FULL_NAME");

/** The FULL_NAME of a the AST parent of an entity */
public static final Key<String> AST_PARENT_FULL_NAME = new Key<>("AST_PARENT_FULL_NAME");

/** The code snippet the node represents */
public static final Key<String> CODE = new Key<>("CODE");

/** The depth first ordering number used to detect back edges in reducible CFGs */
public static final Key<Integer> DEPTH_FIRST_ORDER = new Key<>("DEPTH_FIRST_ORDER");

/** The dispatch type of a call, which is either static or dynamic. See dispatchTypes */
public static final Key<String> DISPATCH_TYPE = new Key<>("DISPATCH_TYPE");

/** The group ID for a dependency */
public static final Key<String> DEPENDENCY_GROUP_ID = new Key<>("DEPENDENCY_GROUP_ID");

/** The index of a call argument. This is used for the association between arguments and parameters. This property is different from ORDER for named arguments because of the required reordering to align arguments with parameters */
public static final Key<Integer> ARGUMENT_INDEX = new Key<>("ARGUMENT_INDEX");

/** The original name of the (potentially mangled) captured variable */
public static final Key<String> CLOSURE_ORIGINAL_NAME = new Key<>("CLOSURE_ORIGINAL_NAME");

/** The programming language this graph originates from */
public static final Key<String> LANGUAGE = new Key<>("LANGUAGE");

/** The static type decl of a TYPE. This property is matched against the FULL_NAME of TYPE_DECL nodes. It is required to have exactly one TYPE_DECL for each different TYPE_DECL_FULL_NAME */
public static final Key<String> TYPE_DECL_FULL_NAME = new Key<>("TYPE_DECL_FULL_NAME");

/** The static type of an entity. E.g. expressions, local, parameters etc. This property is matched against the FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node for each TYPE_FULL_NAME */
public static final Key<String> TYPE_FULL_NAME = new Key<>("TYPE_FULL_NAME");

/** The static types a TYPE_DECL inherits from. This property is matched against the FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node for each TYPE_FULL_NAME */
public static final Key<String> INHERITS_FROM_TYPE_FULL_NAME = new Key<>("INHERITS_FROM_TYPE_FULL_NAME");

/** The type of the AST parent. Since this is only used in some parts of the graph the list does not include all possible parents by intention. Possible parents: METHOD, TYPE_DECL, NAMESPACE_BLOCK */
public static final Key<String> AST_PARENT_TYPE = new Key<>("AST_PARENT_TYPE");

/** Type full name of which a TYPE_DECL is an alias of */
public static final Key<String> ALIAS_TYPE_FULL_NAME = new Key<>("ALIAS_TYPE_FULL_NAME");

/** Type name emitted by parser, only present for logical type UNKNOWN */
public static final Key<String> PARSER_TYPE_NAME = new Key<>("PARSER_TYPE_NAME");

/** A method annotation */
public static class ANNOTATION { 
/** The code snippet the node represents */
public static final Key<String> CODE = new Key<>("CODE");

/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** Full name of an element, e.g., the class name along, including its package */
public static final Key<String> FULL_NAME = new Key<>("FULL_NAME");

}

/** A literal value assigned to an ANNOTATION_PARAMETER */
public static class ANNOTATION_LITERAL { 
/** The code snippet the node represents */
public static final Key<String> CODE = new Key<>("CODE");

/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** General ordering property. E.g. used to express the ordering of children in the AST */
public static final Key<Integer> ORDER = new Key<>("ORDER");

}

/** Formal annotation parameter */
public static class ANNOTATION_PARAMETER { 
/** The code snippet the node represents */
public static final Key<String> CODE = new Key<>("CODE");

}

/** Assignment of annotation argument to annotation parameter */
public static class ANNOTATION_PARAMETER_ASSIGN { 
/** The code snippet the node represents */
public static final Key<String> CODE = new Key<>("CODE");

}

/** Initialization construct for arrays */
public static class ARRAY_INITIALIZER { 
/** The code snippet the node represents */
public static final Key<String> CODE = new Key<>("CODE");

}

/** A structuring block in the AST */
public static class BLOCK { 
/** The code snippet the node represents */
public static final Key<String> CODE = new Key<>("CODE");

/** General ordering property. E.g. used to express the ordering of children in the AST */
public static final Key<Integer> ORDER = new Key<>("ORDER");

/** The index of a call argument. This is used for the association between arguments and parameters. This property is different from ORDER for named arguments because of the required reordering to align arguments with parameters */
public static final Key<Integer> ARGUMENT_INDEX = new Key<>("ARGUMENT_INDEX");

/** The static type of an entity. E.g. expressions, local, parameters etc. This property is matched against the FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node for each TYPE_FULL_NAME */
public static final Key<String> TYPE_FULL_NAME = new Key<>("TYPE_FULL_NAME");

/** Line where the code starts */
public static final Key<Integer> LINE_NUMBER = new Key<>("LINE_NUMBER");

/** Line where the code ends */
public static final Key<Integer> LINE_NUMBER_END = new Key<>("LINE_NUMBER_END");

/** Column where the code starts */
public static final Key<Integer> COLUMN_NUMBER = new Key<>("COLUMN_NUMBER");

/** Column where the code ends */
public static final Key<Integer> COLUMN_NUMBER_END = new Key<>("COLUMN_NUMBER_END");

/** Strongly connected component(SCC) ID which is equal for all expressions in the same component. The SCC IDs start from 0 for each method */
public static final Key<Integer> SCC_ID = new Key<>("SCC_ID");

/** The depth first ordering number used to detect back edges in reducible CFGs */
public static final Key<Integer> DEPTH_FIRST_ORDER = new Key<>("DEPTH_FIRST_ORDER");

}

/** A (method)-call */
public static class CALL { 
/** The code snippet the node represents */
public static final Key<String> CODE = new Key<>("CODE");

/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** General ordering property. E.g. used to express the ordering of children in the AST */
public static final Key<Integer> ORDER = new Key<>("ORDER");

/** The index of a call argument. This is used for the association between arguments and parameters. This property is different from ORDER for named arguments because of the required reordering to align arguments with parameters */
public static final Key<Integer> ARGUMENT_INDEX = new Key<>("ARGUMENT_INDEX");

/** The dispatch type of a call, which is either static or dynamic. See dispatchTypes */
public static final Key<String> DISPATCH_TYPE = new Key<>("DISPATCH_TYPE");

/** Method signature */
public static final Key<String> SIGNATURE = new Key<>("SIGNATURE");

/** The static type of an entity. E.g. expressions, local, parameters etc. This property is matched against the FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node for each TYPE_FULL_NAME */
public static final Key<String> TYPE_FULL_NAME = new Key<>("TYPE_FULL_NAME");

/** The FULL_NAME of a method instance. Used to link CALL and METHOD_REF nodes to METHOD_INST nodes. There needs to be at least one METHOD_INST node for each METHOD_INST_FULL_NAME */
public static final Key<String> METHOD_INST_FULL_NAME = new Key<>("METHOD_INST_FULL_NAME");

/** Line where the code starts */
public static final Key<Integer> LINE_NUMBER = new Key<>("LINE_NUMBER");

/** Line where the code ends */
public static final Key<Integer> LINE_NUMBER_END = new Key<>("LINE_NUMBER_END");

/** Column where the code starts */
public static final Key<Integer> COLUMN_NUMBER = new Key<>("COLUMN_NUMBER");

/** Column where the code ends */
public static final Key<Integer> COLUMN_NUMBER_END = new Key<>("COLUMN_NUMBER_END");

/** Indicates whether a call was already resolved. If not set this means not yet resolved */
public static final Key<Boolean> RESOLVED = new Key<>("RESOLVED");

/** Strongly connected component(SCC) ID which is equal for all expressions in the same component. The SCC IDs start from 0 for each method */
public static final Key<Integer> SCC_ID = new Key<>("SCC_ID");

/** The depth first ordering number used to detect back edges in reducible CFGs */
public static final Key<Integer> DEPTH_FIRST_ORDER = new Key<>("DEPTH_FIRST_ORDER");

}

/**  */
public static class CALL_CHAIN { 
}

/**  */
public static class CALL_SITE { 
}

/** Represents the binding of a LOCAL or METHOD_PARAMETER_IN into the closure of a method */
public static class CLOSURE_BINDING { 
/** Identifier which uniquely describes a CLOSURE_BINDING. This property is used to match captured LOCAL nodes with the corresponding CLOSURE_BINDING nodes */
public static final Key<String> CLOSURE_BINDING_ID = new Key<>("CLOSURE_BINDING_ID");

/** Evaluation strategy for function parameters and return values. One of the values in "evaluationStrategies" */
public static final Key<String> EVALUATION_STRATEGY = new Key<>("EVALUATION_STRATEGY");

/** The original name of the (potentially mangled) captured variable */
public static final Key<String> CLOSURE_ORIGINAL_NAME = new Key<>("CLOSURE_ORIGINAL_NAME");

}

/** A comment */
public static class COMMENT { 
/** Line where the code starts */
public static final Key<Integer> LINE_NUMBER = new Key<>("LINE_NUMBER");

/** The code snippet the node represents */
public static final Key<String> CODE = new Key<>("CODE");

}

/** Configuration file contents. Might be in use by a framework */
public static class CONFIG_FILE { 
/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** Content of CONFIG_FILE node */
public static final Key<String> CONTENT = new Key<>("CONTENT");

}

/** This node represents a dependency */
public static class DEPENDENCY { 
/** A version, given as a string */
public static final Key<String> VERSION = new Key<>("VERSION");

/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** The group ID for a dependency */
public static final Key<String> DEPENDENCY_GROUP_ID = new Key<>("DEPENDENCY_GROUP_ID");

}

/**  */
public static class DETACHED_TRACKING_POINT { 
}

/** Node representing a source file. Often also the AST root */
public static class FILE { 
/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

}

/**  */
public static class FINDING { 
/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/**  */
public static final Key<String> CATEGORY = new Key<>("CATEGORY");

/**  */
public static final Key<String> TITLE = new Key<>("TITLE");

/**  */
public static final Key<String> DESCRIPTION = new Key<>("DESCRIPTION");

/**  */
public static final Key<Integer> SCORE = new Key<>("SCORE");

/**  */
public static final Key<String> LINK = new Key<>("LINK");

/**  */
public static final Key<String> VULN_DESCR = new Key<>("VULN_DESCR");

/**  */
public static final Key<String> PARAMETER = new Key<>("PARAMETER");

/**  */
public static final Key<String> METHOD_NAME = new Key<>("METHOD_NAME");

/**  */
public static final Key<String> LINE_NO = new Key<>("LINE_NO");

/**  */
public static final Key<String> FILENAME = new Key<>("FILENAME");

}

/**  */
public static class FLOW { 
}

/** Indicates the usage of a framework. E.g. java spring. The name key is one of the values from frameworks list */
public static class FRAMEWORK { 
/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

}

/** Data used by a framework */
public static class FRAMEWORK_DATA { 
/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** Content of CONFIG_FILE node */
public static final Key<String> CONTENT = new Key<>("CONTENT");

}

/** An arbitrary identifier/reference */
public static class IDENTIFIER { 
/** The code snippet the node represents */
public static final Key<String> CODE = new Key<>("CODE");

/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** General ordering property. E.g. used to express the ordering of children in the AST */
public static final Key<Integer> ORDER = new Key<>("ORDER");

/** The index of a call argument. This is used for the association between arguments and parameters. This property is different from ORDER for named arguments because of the required reordering to align arguments with parameters */
public static final Key<Integer> ARGUMENT_INDEX = new Key<>("ARGUMENT_INDEX");

/** The static type of an entity. E.g. expressions, local, parameters etc. This property is matched against the FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node for each TYPE_FULL_NAME */
public static final Key<String> TYPE_FULL_NAME = new Key<>("TYPE_FULL_NAME");

/** Line where the code starts */
public static final Key<Integer> LINE_NUMBER = new Key<>("LINE_NUMBER");

/** Line where the code ends */
public static final Key<Integer> LINE_NUMBER_END = new Key<>("LINE_NUMBER_END");

/** Column where the code starts */
public static final Key<Integer> COLUMN_NUMBER = new Key<>("COLUMN_NUMBER");

/** Column where the code ends */
public static final Key<Integer> COLUMN_NUMBER_END = new Key<>("COLUMN_NUMBER_END");

/** Strongly connected component(SCC) ID which is equal for all expressions in the same component. The SCC IDs start from 0 for each method */
public static final Key<Integer> SCC_ID = new Key<>("SCC_ID");

/** The depth first ordering number used to detect back edges in reducible CFGs */
public static final Key<Integer> DEPTH_FIRST_ORDER = new Key<>("DEPTH_FIRST_ORDER");

}

/**  */
public static class IOFLOW { 
/**  */
public static final Key<String> FINGERPRINT = new Key<>("FINGERPRINT");

/**  */
public static final Key<String> LITERALS_TO_SINK = new Key<>("LITERALS_TO_SINK");

}

/** Literal/Constant */
public static class LITERAL { 
/** The code snippet the node represents */
public static final Key<String> CODE = new Key<>("CODE");

/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** General ordering property. E.g. used to express the ordering of children in the AST */
public static final Key<Integer> ORDER = new Key<>("ORDER");

/** The index of a call argument. This is used for the association between arguments and parameters. This property is different from ORDER for named arguments because of the required reordering to align arguments with parameters */
public static final Key<Integer> ARGUMENT_INDEX = new Key<>("ARGUMENT_INDEX");

/** The static type of an entity. E.g. expressions, local, parameters etc. This property is matched against the FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node for each TYPE_FULL_NAME */
public static final Key<String> TYPE_FULL_NAME = new Key<>("TYPE_FULL_NAME");

/** Line where the code starts */
public static final Key<Integer> LINE_NUMBER = new Key<>("LINE_NUMBER");

/** Line where the code ends */
public static final Key<Integer> LINE_NUMBER_END = new Key<>("LINE_NUMBER_END");

/** Column where the code starts */
public static final Key<Integer> COLUMN_NUMBER = new Key<>("COLUMN_NUMBER");

/** Column where the code ends */
public static final Key<Integer> COLUMN_NUMBER_END = new Key<>("COLUMN_NUMBER_END");

/** Strongly connected component(SCC) ID which is equal for all expressions in the same component. The SCC IDs start from 0 for each method */
public static final Key<Integer> SCC_ID = new Key<>("SCC_ID");

/** The depth first ordering number used to detect back edges in reducible CFGs */
public static final Key<Integer> DEPTH_FIRST_ORDER = new Key<>("DEPTH_FIRST_ORDER");

}

/** A local variable */
public static class LOCAL { 
/** The code snippet the node represents */
public static final Key<String> CODE = new Key<>("CODE");

/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** Identifier which uniquely describes a CLOSURE_BINDING. This property is used to match captured LOCAL nodes with the corresponding CLOSURE_BINDING nodes */
public static final Key<String> CLOSURE_BINDING_ID = new Key<>("CLOSURE_BINDING_ID");

/** The static type of an entity. E.g. expressions, local, parameters etc. This property is matched against the FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node for each TYPE_FULL_NAME */
public static final Key<String> TYPE_FULL_NAME = new Key<>("TYPE_FULL_NAME");

/** Line where the code starts */
public static final Key<Integer> LINE_NUMBER = new Key<>("LINE_NUMBER");

/** Line where the code ends */
public static final Key<Integer> LINE_NUMBER_END = new Key<>("LINE_NUMBER_END");

/** Column where the code starts */
public static final Key<Integer> COLUMN_NUMBER = new Key<>("COLUMN_NUMBER");

/** Column where the code ends */
public static final Key<Integer> COLUMN_NUMBER_END = new Key<>("COLUMN_NUMBER_END");

}

/**  */
public static class LOCATION { 
/**  */
public static final Key<String> SYMBOL = new Key<>("SYMBOL");

/** The FULL_NAME of a method. Used to link METHOD_INST and METHOD nodes. It is required to have exactly one METHOD node for each METHOD_FULL_NAME */
public static final Key<String> METHOD_FULL_NAME = new Key<>("METHOD_FULL_NAME");

/**  */
public static final Key<String> METHOD_SHORT_NAME = new Key<>("METHOD_SHORT_NAME");

/**  */
public static final Key<String> PACKAGE_NAME = new Key<>("PACKAGE_NAME");

/** Line where the code starts */
public static final Key<Integer> LINE_NUMBER = new Key<>("LINE_NUMBER");

/**  */
public static final Key<String> CLASS_NAME = new Key<>("CLASS_NAME");

/**  */
public static final Key<String> NODE_LABEL = new Key<>("NODE_LABEL");

/**  */
public static final Key<String> FILENAME = new Key<>("FILENAME");

}

/**  */
public static class MATCH_INFO { 
/**  */
public static final Key<String> PATTERN = new Key<>("PATTERN");

/**  */
public static final Key<String> CATEGORY = new Key<>("CATEGORY");

}

/** Member of a class struct or union */
public static class MEMBER { 
/** The code snippet the node represents */
public static final Key<String> CODE = new Key<>("CODE");

/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** The static type of an entity. E.g. expressions, local, parameters etc. This property is matched against the FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node for each TYPE_FULL_NAME */
public static final Key<String> TYPE_FULL_NAME = new Key<>("TYPE_FULL_NAME");

}

/** Node to save meta data about the graph on its properties. Exactly one node of this type per graph */
public static class META_DATA { 
/** The programming language this graph originates from */
public static final Key<String> LANGUAGE = new Key<>("LANGUAGE");

/** A version, given as a string */
public static final Key<String> VERSION = new Key<>("VERSION");

/**  */
public static final Key<String> SPID = new Key<>("SPID");

}

/** A method/function/procedure */
public static class METHOD { 
/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** Full name of an element, e.g., the class name along, including its package */
public static final Key<String> FULL_NAME = new Key<>("FULL_NAME");

/** Method signature */
public static final Key<String> SIGNATURE = new Key<>("SIGNATURE");

/** The type of the AST parent. Since this is only used in some parts of the graph the list does not include all possible parents by intention. Possible parents: METHOD, TYPE_DECL, NAMESPACE_BLOCK */
public static final Key<String> AST_PARENT_TYPE = new Key<>("AST_PARENT_TYPE");

/** The FULL_NAME of a the AST parent of an entity */
public static final Key<String> AST_PARENT_FULL_NAME = new Key<>("AST_PARENT_FULL_NAME");

/** Line where the code starts */
public static final Key<Integer> LINE_NUMBER = new Key<>("LINE_NUMBER");

/** Line where the code ends */
public static final Key<Integer> LINE_NUMBER_END = new Key<>("LINE_NUMBER_END");

/** Column where the code starts */
public static final Key<Integer> COLUMN_NUMBER = new Key<>("COLUMN_NUMBER");

/** Column where the code ends */
public static final Key<Integer> COLUMN_NUMBER_END = new Key<>("COLUMN_NUMBER_END");

/** Marks that a method has at least one mapping defined from the policies */
public static final Key<Boolean> HAS_MAPPING = new Key<>("HAS_MAPPING");

/** The depth first ordering number used to detect back edges in reducible CFGs */
public static final Key<Integer> DEPTH_FIRST_ORDER = new Key<>("DEPTH_FIRST_ORDER");

/** Binary type signature */
public static final Key<String> BINARY_SIGNATURE = new Key<>("BINARY_SIGNATURE");

}

/** A method instance which always has to reference a method and may have type argument children if the referred to method is a template */
public static class METHOD_INST { 
/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** Full name of an element, e.g., the class name along, including its package */
public static final Key<String> FULL_NAME = new Key<>("FULL_NAME");

/** Method signature */
public static final Key<String> SIGNATURE = new Key<>("SIGNATURE");

/** The FULL_NAME of a method. Used to link METHOD_INST and METHOD nodes. It is required to have exactly one METHOD node for each METHOD_FULL_NAME */
public static final Key<String> METHOD_FULL_NAME = new Key<>("METHOD_FULL_NAME");

}

/** This node represents a formal parameter going towards the callee side */
public static class METHOD_PARAMETER_IN { 
/** The code snippet the node represents */
public static final Key<String> CODE = new Key<>("CODE");

/** General ordering property. E.g. used to express the ordering of children in the AST */
public static final Key<Integer> ORDER = new Key<>("ORDER");

/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** Evaluation strategy for function parameters and return values. One of the values in "evaluationStrategies" */
public static final Key<String> EVALUATION_STRATEGY = new Key<>("EVALUATION_STRATEGY");

/** The static type of an entity. E.g. expressions, local, parameters etc. This property is matched against the FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node for each TYPE_FULL_NAME */
public static final Key<String> TYPE_FULL_NAME = new Key<>("TYPE_FULL_NAME");

/** Line where the code starts */
public static final Key<Integer> LINE_NUMBER = new Key<>("LINE_NUMBER");

/** Line where the code ends */
public static final Key<Integer> LINE_NUMBER_END = new Key<>("LINE_NUMBER_END");

/** Column where the code starts */
public static final Key<Integer> COLUMN_NUMBER = new Key<>("COLUMN_NUMBER");

/** Column where the code ends */
public static final Key<Integer> COLUMN_NUMBER_END = new Key<>("COLUMN_NUMBER_END");

}

/** This node represents a formal parameter going towards the caller side */
public static class METHOD_PARAMETER_OUT { 
/** The code snippet the node represents */
public static final Key<String> CODE = new Key<>("CODE");

/** General ordering property. E.g. used to express the ordering of children in the AST */
public static final Key<Integer> ORDER = new Key<>("ORDER");

/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** Evaluation strategy for function parameters and return values. One of the values in "evaluationStrategies" */
public static final Key<String> EVALUATION_STRATEGY = new Key<>("EVALUATION_STRATEGY");

/** The static type of an entity. E.g. expressions, local, parameters etc. This property is matched against the FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node for each TYPE_FULL_NAME */
public static final Key<String> TYPE_FULL_NAME = new Key<>("TYPE_FULL_NAME");

/** Line where the code starts */
public static final Key<Integer> LINE_NUMBER = new Key<>("LINE_NUMBER");

/** Line where the code ends */
public static final Key<Integer> LINE_NUMBER_END = new Key<>("LINE_NUMBER_END");

/** Column where the code starts */
public static final Key<Integer> COLUMN_NUMBER = new Key<>("COLUMN_NUMBER");

/** Column where the code ends */
public static final Key<Integer> COLUMN_NUMBER_END = new Key<>("COLUMN_NUMBER_END");

}

/** Reference to a method instance */
public static class METHOD_REF { 
/** The code snippet the node represents */
public static final Key<String> CODE = new Key<>("CODE");

/** General ordering property. E.g. used to express the ordering of children in the AST */
public static final Key<Integer> ORDER = new Key<>("ORDER");

/** The index of a call argument. This is used for the association between arguments and parameters. This property is different from ORDER for named arguments because of the required reordering to align arguments with parameters */
public static final Key<Integer> ARGUMENT_INDEX = new Key<>("ARGUMENT_INDEX");

/** The FULL_NAME of a method instance. Used to link CALL and METHOD_REF nodes to METHOD_INST nodes. There needs to be at least one METHOD_INST node for each METHOD_INST_FULL_NAME */
public static final Key<String> METHOD_INST_FULL_NAME = new Key<>("METHOD_INST_FULL_NAME");

/** Line where the code starts */
public static final Key<Integer> LINE_NUMBER = new Key<>("LINE_NUMBER");

/** Line where the code ends */
public static final Key<Integer> LINE_NUMBER_END = new Key<>("LINE_NUMBER_END");

/** Column where the code starts */
public static final Key<Integer> COLUMN_NUMBER = new Key<>("COLUMN_NUMBER");

/** Column where the code ends */
public static final Key<Integer> COLUMN_NUMBER_END = new Key<>("COLUMN_NUMBER_END");

/** Strongly connected component(SCC) ID which is equal for all expressions in the same component. The SCC IDs start from 0 for each method */
public static final Key<Integer> SCC_ID = new Key<>("SCC_ID");

/** The depth first ordering number used to detect back edges in reducible CFGs */
public static final Key<Integer> DEPTH_FIRST_ORDER = new Key<>("DEPTH_FIRST_ORDER");

}

/** A formal method return */
public static class METHOD_RETURN { 
/** The code snippet the node represents */
public static final Key<String> CODE = new Key<>("CODE");

/** Evaluation strategy for function parameters and return values. One of the values in "evaluationStrategies" */
public static final Key<String> EVALUATION_STRATEGY = new Key<>("EVALUATION_STRATEGY");

/** The static type of an entity. E.g. expressions, local, parameters etc. This property is matched against the FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node for each TYPE_FULL_NAME */
public static final Key<String> TYPE_FULL_NAME = new Key<>("TYPE_FULL_NAME");

/** Line where the code starts */
public static final Key<Integer> LINE_NUMBER = new Key<>("LINE_NUMBER");

/** Line where the code ends */
public static final Key<Integer> LINE_NUMBER_END = new Key<>("LINE_NUMBER_END");

/** Column where the code starts */
public static final Key<Integer> COLUMN_NUMBER = new Key<>("COLUMN_NUMBER");

/** Column where the code ends */
public static final Key<Integer> COLUMN_NUMBER_END = new Key<>("COLUMN_NUMBER_END");

/** The depth first ordering number used to detect back edges in reducible CFGs */
public static final Key<Integer> DEPTH_FIRST_ORDER = new Key<>("DEPTH_FIRST_ORDER");

}

/**  */
public static class METHOD_SUMMARY { 
/**  */
public static final Key<Boolean> IS_STATIC = new Key<>("IS_STATIC");

/** Indicates that the construct is external, that is, it is referenced but not defined in the code */
public static final Key<Boolean> IS_EXTERNAL = new Key<>("IS_EXTERNAL");

/** Binary type signature */
public static final Key<String> BINARY_SIGNATURE = new Key<>("BINARY_SIGNATURE");

}

/** The static-modifier */
public static class MODIFIER { 
/** Indicates the modifier which is represented by a MODIFIER node. See modifierTypes */
public static final Key<String> MODIFIER_TYPE = new Key<>("MODIFIER_TYPE");

}

/** This node represents a namespace as a whole whereas the NAMESPACE_BLOCK is used for each grouping occurrence of a namespace in code. Single representing NAMESPACE node is required for easier navigation in the query language */
public static class NAMESPACE { 
/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

}

/** A reference to a namespace */
public static class NAMESPACE_BLOCK { 
/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** Full name of an element, e.g., the class name along, including its package */
public static final Key<String> FULL_NAME = new Key<>("FULL_NAME");

}

/**  */
public static class PROGRAM_POINT { 
}

/**  */
public static class READ { 
}

/** A return instruction */
public static class RETURN { 
/** Line where the code starts */
public static final Key<Integer> LINE_NUMBER = new Key<>("LINE_NUMBER");

/** Line where the code ends */
public static final Key<Integer> LINE_NUMBER_END = new Key<>("LINE_NUMBER_END");

/** Column where the code starts */
public static final Key<Integer> COLUMN_NUMBER = new Key<>("COLUMN_NUMBER");

/** Column where the code ends */
public static final Key<Integer> COLUMN_NUMBER_END = new Key<>("COLUMN_NUMBER_END");

/** General ordering property. E.g. used to express the ordering of children in the AST */
public static final Key<Integer> ORDER = new Key<>("ORDER");

/** The index of a call argument. This is used for the association between arguments and parameters. This property is different from ORDER for named arguments because of the required reordering to align arguments with parameters */
public static final Key<Integer> ARGUMENT_INDEX = new Key<>("ARGUMENT_INDEX");

/** The code snippet the node represents */
public static final Key<String> CODE = new Key<>("CODE");

/** Strongly connected component(SCC) ID which is equal for all expressions in the same component. The SCC IDs start from 0 for each method */
public static final Key<Integer> SCC_ID = new Key<>("SCC_ID");

/** The depth first ordering number used to detect back edges in reducible CFGs */
public static final Key<Integer> DEPTH_FIRST_ORDER = new Key<>("DEPTH_FIRST_ORDER");

}

/**  */
public static class ROUTE { 
/**  */
public static final Key<String> PATH = new Key<>("PATH");

}

/**  */
public static class SENSITIVE_DATA_TYPE { 
/** Full name of an element, e.g., the class name along, including its package */
public static final Key<String> FULL_NAME = new Key<>("FULL_NAME");

}

/**  */
public static class SENSITIVE_MEMBER { 
/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

}

/**  */
public static class SENSITIVE_REFERENCE { 
}

/**  */
public static class SENSITIVE_VARIABLE { 
/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

}

/**  */
public static class SINK { 
/**  */
public static final Key<String> SINK_TYPE = new Key<>("SINK_TYPE");

}

/**  */
public static class SOURCE { 
/**  */
public static final Key<String> SOURCE_TYPE = new Key<>("SOURCE_TYPE");

}

/**  */
public static class SP_ANNOTATION_PARAMETER { 
/**  */
public static final Key<String> ANNOTATION_NAME = new Key<>("ANNOTATION_NAME");

/**  */
public static final Key<String> ANNOTATION_FULL_NAME = new Key<>("ANNOTATION_FULL_NAME");

/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** Tag value */
public static final Key<String> VALUE = new Key<>("VALUE");

}

/**  */
public static class SP_BLACKLIST { 
}

/** A string tag */
public static class TAG { 
/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** Tag value */
public static final Key<String> VALUE = new Key<>("VALUE");

}

/** Multiple tags */
public static class TAGS { 
}

/**  */
public static class TAG_NODE_PAIR { 
}

/**  */
public static class TRANSFORM { 
}

/**  */
public static class TRANSFORMATION { 
}

/** A type which always has to reference a type declaration and may have type argument children if the referred to type declaration is a template */
public static class TYPE { 
/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** Full name of an element, e.g., the class name along, including its package */
public static final Key<String> FULL_NAME = new Key<>("FULL_NAME");

/** The static type decl of a TYPE. This property is matched against the FULL_NAME of TYPE_DECL nodes. It is required to have exactly one TYPE_DECL for each different TYPE_DECL_FULL_NAME */
public static final Key<String> TYPE_DECL_FULL_NAME = new Key<>("TYPE_DECL_FULL_NAME");

}

/** Argument for a TYPE_PARAMETER that belongs to a TYPE or METHOD_INST. It binds another TYPE to a TYPE_PARAMETER */
public static class TYPE_ARGUMENT { 
}

/** A type declaration */
public static class TYPE_DECL { 
/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** Full name of an element, e.g., the class name along, including its package */
public static final Key<String> FULL_NAME = new Key<>("FULL_NAME");

/** Indicates that the construct is external, that is, it is referenced but not defined in the code */
public static final Key<Boolean> IS_EXTERNAL = new Key<>("IS_EXTERNAL");

/** The static types a TYPE_DECL inherits from. This property is matched against the FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node for each TYPE_FULL_NAME */
public static final Key<String> INHERITS_FROM_TYPE_FULL_NAME = new Key<>("INHERITS_FROM_TYPE_FULL_NAME");

/** The type of the AST parent. Since this is only used in some parts of the graph the list does not include all possible parents by intention. Possible parents: METHOD, TYPE_DECL, NAMESPACE_BLOCK */
public static final Key<String> AST_PARENT_TYPE = new Key<>("AST_PARENT_TYPE");

/** The FULL_NAME of a the AST parent of an entity */
public static final Key<String> AST_PARENT_FULL_NAME = new Key<>("AST_PARENT_FULL_NAME");

/** Type full name of which a TYPE_DECL is an alias of */
public static final Key<String> ALIAS_TYPE_FULL_NAME = new Key<>("ALIAS_TYPE_FULL_NAME");

}

/** Type parameter of TYPE_DECL or METHOD */
public static class TYPE_PARAMETER { 
/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** General ordering property. E.g. used to express the ordering of children in the AST */
public static final Key<Integer> ORDER = new Key<>("ORDER");

}

/** A language-specific node */
public static class UNKNOWN { 
/** The code snippet the node represents */
public static final Key<String> CODE = new Key<>("CODE");

/** Type name emitted by parser, only present for logical type UNKNOWN */
public static final Key<String> PARSER_TYPE_NAME = new Key<>("PARSER_TYPE_NAME");

/** General ordering property. E.g. used to express the ordering of children in the AST */
public static final Key<Integer> ORDER = new Key<>("ORDER");

/** The index of a call argument. This is used for the association between arguments and parameters. This property is different from ORDER for named arguments because of the required reordering to align arguments with parameters */
public static final Key<Integer> ARGUMENT_INDEX = new Key<>("ARGUMENT_INDEX");

/** The static type of an entity. E.g. expressions, local, parameters etc. This property is matched against the FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node for each TYPE_FULL_NAME */
public static final Key<String> TYPE_FULL_NAME = new Key<>("TYPE_FULL_NAME");

/** Line where the code starts */
public static final Key<Integer> LINE_NUMBER = new Key<>("LINE_NUMBER");

/** Line where the code ends */
public static final Key<Integer> LINE_NUMBER_END = new Key<>("LINE_NUMBER_END");

/** Column where the code starts */
public static final Key<Integer> COLUMN_NUMBER = new Key<>("COLUMN_NUMBER");

/** Column where the code ends */
public static final Key<Integer> COLUMN_NUMBER_END = new Key<>("COLUMN_NUMBER_END");

/** Strongly connected component(SCC) ID which is equal for all expressions in the same component. The SCC IDs start from 0 for each method */
public static final Key<Integer> SCC_ID = new Key<>("SCC_ID");

/** The depth first ordering number used to detect back edges in reducible CFGs */
public static final Key<Integer> DEPTH_FIRST_ORDER = new Key<>("DEPTH_FIRST_ORDER");

}

/**  */
public static class VARIABLE_INFO { 
/**  */
public static final Key<String> VAR_TYPE = new Key<>("VAR_TYPE");

/**  */
public static final Key<String> EVALUATION_TYPE = new Key<>("EVALUATION_TYPE");

/**  */
public static final Key<Integer> PARAMETER_INDEX = new Key<>("PARAMETER_INDEX");

}

/**  */
public static class WRITE { 
}

/** This node records what package prefix is most common to all analysed classes in the CPG */
public static class PACKAGE_PREFIX { 
/** Tag value */
public static final Key<String> VALUE = new Key<>("VALUE");

}



}
