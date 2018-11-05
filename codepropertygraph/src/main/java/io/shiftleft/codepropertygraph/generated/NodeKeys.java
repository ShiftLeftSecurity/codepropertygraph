package io.shiftleft.codepropertygraph.generated;

import gremlin.scala.Key;

public class NodeKeys {

/** A version, given as a string */
public static final Key<String> VERSION = new Key<>("VERSION");

/** Column where the code ends */
public static final Key<Integer> COLUMN_NUMBER_END = new Key<>("COLUMN_NUMBER_END");

/** Column where the code starts */
public static final Key<Integer> COLUMN_NUMBER = new Key<>("COLUMN_NUMBER");

/** Evaluation strategy for function parameters and return values. One of the values in "evaluationStrategies". */
public static final Key<String> EVALUATION_STRATEGY = new Key<>("EVALUATION_STRATEGY");

/** Full name of an element, e.g., the class name along, including its package. */
public static final Key<String> FULL_NAME = new Key<>("FULL_NAME");

/** General ordering property. E.g. used to express the ordering of children in the AST. */
public static final Key<Integer> ORDER = new Key<>("ORDER");

/** Identifier which uniquely describes a CLOSURE_BINDING. This property is used to match captured LOCAL nodes with the corresponding CLOSURE_BINDING nodes. */
public static final Key<String> CLOSURE_BINDING_ID = new Key<>("CLOSURE_BINDING_ID");

/** Indicates that the construct is external, that is, it is referenced but not defined in the code */
public static final Key<Boolean> IS_EXTERNAL = new Key<>("IS_EXTERNAL");

/** Indicates the which modifier is represented by a MODIFIER node. See modifierTypes. */
public static final Key<String> MODIFIER_TYPE = new Key<>("MODIFIER_TYPE");

/** Line where the code ends */
public static final Key<Integer> LINE_NUMBER_END = new Key<>("LINE_NUMBER_END");

/** Line where the code starts */
public static final Key<Integer> LINE_NUMBER = new Key<>("LINE_NUMBER");

/** Method signature */
public static final Key<String> SIGNATURE = new Key<>("SIGNATURE");

/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** Tag value */
public static final Key<String> VALUE = new Key<>("VALUE");

/** The FULL_NAME of a method instance. Used to link CALL and METHOD_REF nodes to METHOD_INST nodes. There needs to be at least one METHOD_INST node for each METHOD_INST_FULL_NAME. */
public static final Key<String> METHOD_INST_FULL_NAME = new Key<>("METHOD_INST_FULL_NAME");

/** The FULL_NAME of a method. Used to link METHOD_INST and METHOD nodes. It is required to have exactly one METHOD node for each METHOD_FULL_NAME. */
public static final Key<String> METHOD_FULL_NAME = new Key<>("METHOD_FULL_NAME");

/** The FULL_NAME of a the AST parent of an entity. */
public static final Key<String> AST_PARENT_FULL_NAME = new Key<>("AST_PARENT_FULL_NAME");

/** The code snippet the node represents */
public static final Key<String> CODE = new Key<>("CODE");

/** The dispatch type of a call, which is either static or dynamic. See dispatchTypes. */
public static final Key<String> DISPATCH_TYPE = new Key<>("DISPATCH_TYPE");

/** The index of a call argument. This is used for the association between arguments and parameters. This property is different from ORDER for named arguments because of the required reordering to align arguments with parameters. */
public static final Key<Integer> ARGUMENT_INDEX = new Key<>("ARGUMENT_INDEX");

/** The programming language this graph originates from */
public static final Key<String> LANGUAGE = new Key<>("LANGUAGE");

/** The static type decl of a TYPE. This property is matched against the FULL_NAME of TYPE_DECL nodes. It is required to have exactly one TYPE_DECL for each different TYPE_DECL_FULL_NAME. */
public static final Key<String> TYPE_DECL_FULL_NAME = new Key<>("TYPE_DECL_FULL_NAME");

/** The static type of an entity. E.g. expressions, local, parameters etc. This property is matched against the FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node for each TYPE_FULL_NAME. */
public static final Key<String> TYPE_FULL_NAME = new Key<>("TYPE_FULL_NAME");

/** The static types a TYPE_DECL inherits from. This property is matched against the FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node for each TYPE_FULL_NAME. */
public static final Key<String> INHERITS_FROM_TYPE_FULL_NAME = new Key<>("INHERITS_FROM_TYPE_FULL_NAME");

/** The type of the AST parent. Since this is only used in some parts of the graph the list does not include all possible parents by intention. Possible parents: METHOD, TYPE_DECL, NAMESPACE_BLOCK. */
public static final Key<String> AST_PARENT_TYPE = new Key<>("AST_PARENT_TYPE");

/** Type name emitted by parser, only present for logical type  UNKNOWN */
public static final Key<String> PARSER_TYPE_NAME = new Key<>("PARSER_TYPE_NAME");

/** Initialization contruct for arrays. */
public static class ARRAY_INITIALIZER { 
}

/** A structuring block in the AST. */
public static class BLOCK { 
/** Line where the code starts */
public static final Key<Integer> LINE_NUMBER = new Key<>("LINE_NUMBER");

/** Line where the code ends */
public static final Key<Integer> LINE_NUMBER_END = new Key<>("LINE_NUMBER_END");

/** Column where the code starts */
public static final Key<Integer> COLUMN_NUMBER = new Key<>("COLUMN_NUMBER");

/** Column where the code ends */
public static final Key<Integer> COLUMN_NUMBER_END = new Key<>("COLUMN_NUMBER_END");

}

/** A (method)-call */
public static class CALL { 
/** The code snippet the node represents */
public static final Key<String> CODE = new Key<>("CODE");

/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** General ordering property. E.g. used to express the ordering of children in the AST. */
public static final Key<Integer> ORDER = new Key<>("ORDER");

/** The index of a call argument. This is used for the association between arguments and parameters. This property is different from ORDER for named arguments because of the required reordering to align arguments with parameters. */
public static final Key<Integer> ARGUMENT_INDEX = new Key<>("ARGUMENT_INDEX");

/** The dispatch type of a call, which is either static or dynamic. See dispatchTypes. */
public static final Key<String> DISPATCH_TYPE = new Key<>("DISPATCH_TYPE");

/** Method signature */
public static final Key<String> SIGNATURE = new Key<>("SIGNATURE");

/** The static type of an entity. E.g. expressions, local, parameters etc. This property is matched against the FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node for each TYPE_FULL_NAME. */
public static final Key<String> TYPE_FULL_NAME = new Key<>("TYPE_FULL_NAME");

/** The FULL_NAME of a method instance. Used to link CALL and METHOD_REF nodes to METHOD_INST nodes. There needs to be at least one METHOD_INST node for each METHOD_INST_FULL_NAME. */
public static final Key<String> METHOD_INST_FULL_NAME = new Key<>("METHOD_INST_FULL_NAME");

/** Line where the code starts */
public static final Key<Integer> LINE_NUMBER = new Key<>("LINE_NUMBER");

/** Line where the code ends */
public static final Key<Integer> LINE_NUMBER_END = new Key<>("LINE_NUMBER_END");

/** Column where the code starts */
public static final Key<Integer> COLUMN_NUMBER = new Key<>("COLUMN_NUMBER");

/** Column where the code ends */
public static final Key<Integer> COLUMN_NUMBER_END = new Key<>("COLUMN_NUMBER_END");

}

/** Represents the binding of a LOCAL or METHOD_PARAMETER_IN into the closure of a method. */
public static class CLOSURE_BINDING { 
/** Identifier which uniquely describes a CLOSURE_BINDING. This property is used to match captured LOCAL nodes with the corresponding CLOSURE_BINDING nodes. */
public static final Key<String> CLOSURE_BINDING_ID = new Key<>("CLOSURE_BINDING_ID");

/** Evaluation strategy for function parameters and return values. One of the values in "evaluationStrategies". */
public static final Key<String> EVALUATION_STRATEGY = new Key<>("EVALUATION_STRATEGY");

}

/** Node presenting a source file. Often also the AST root. */
public static class FILE { 
/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

}

/** An arbitrary identifier/reference. */
public static class IDENTIFIER { 
/** The code snippet the node represents */
public static final Key<String> CODE = new Key<>("CODE");

/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** General ordering property. E.g. used to express the ordering of children in the AST. */
public static final Key<Integer> ORDER = new Key<>("ORDER");

/** The index of a call argument. This is used for the association between arguments and parameters. This property is different from ORDER for named arguments because of the required reordering to align arguments with parameters. */
public static final Key<Integer> ARGUMENT_INDEX = new Key<>("ARGUMENT_INDEX");

/** The static type of an entity. E.g. expressions, local, parameters etc. This property is matched against the FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node for each TYPE_FULL_NAME. */
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

/** Literal/Constant */
public static class LITERAL { 
/** The code snippet the node represents */
public static final Key<String> CODE = new Key<>("CODE");

/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** General ordering property. E.g. used to express the ordering of children in the AST. */
public static final Key<Integer> ORDER = new Key<>("ORDER");

/** The index of a call argument. This is used for the association between arguments and parameters. This property is different from ORDER for named arguments because of the required reordering to align arguments with parameters. */
public static final Key<Integer> ARGUMENT_INDEX = new Key<>("ARGUMENT_INDEX");

/** The static type of an entity. E.g. expressions, local, parameters etc. This property is matched against the FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node for each TYPE_FULL_NAME. */
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

/** A local variable */
public static class LOCAL { 
/** The code snippet the node represents */
public static final Key<String> CODE = new Key<>("CODE");

/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** Identifier which uniquely describes a CLOSURE_BINDING. This property is used to match captured LOCAL nodes with the corresponding CLOSURE_BINDING nodes. */
public static final Key<String> CLOSURE_BINDING_ID = new Key<>("CLOSURE_BINDING_ID");

/** The static type of an entity. E.g. expressions, local, parameters etc. This property is matched against the FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node for each TYPE_FULL_NAME. */
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

/** Member of a class struct or union */
public static class MEMBER { 
/** The code snippet the node represents */
public static final Key<String> CODE = new Key<>("CODE");

/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** The static type of an entity. E.g. expressions, local, parameters etc. This property is matched against the FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node for each TYPE_FULL_NAME. */
public static final Key<String> TYPE_FULL_NAME = new Key<>("TYPE_FULL_NAME");

}

/** Node to save meta data about the graph on its properties. Exactly one node of this type per graph. */
public static class META_DATA { 
/** The programming language this graph originates from */
public static final Key<String> LANGUAGE = new Key<>("LANGUAGE");

/** A version, given as a string */
public static final Key<String> VERSION = new Key<>("VERSION");

}

/** A method/function/procedure */
public static class METHOD { 
/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** Full name of an element, e.g., the class name along, including its package. */
public static final Key<String> FULL_NAME = new Key<>("FULL_NAME");

/** Method signature */
public static final Key<String> SIGNATURE = new Key<>("SIGNATURE");

/** The type of the AST parent. Since this is only used in some parts of the graph the list does not include all possible parents by intention. Possible parents: METHOD, TYPE_DECL, NAMESPACE_BLOCK. */
public static final Key<String> AST_PARENT_TYPE = new Key<>("AST_PARENT_TYPE");

/** The FULL_NAME of a the AST parent of an entity. */
public static final Key<String> AST_PARENT_FULL_NAME = new Key<>("AST_PARENT_FULL_NAME");

/** Line where the code starts */
public static final Key<Integer> LINE_NUMBER = new Key<>("LINE_NUMBER");

/** Line where the code ends */
public static final Key<Integer> LINE_NUMBER_END = new Key<>("LINE_NUMBER_END");

/** Column where the code starts */
public static final Key<Integer> COLUMN_NUMBER = new Key<>("COLUMN_NUMBER");

/** Column where the code ends */
public static final Key<Integer> COLUMN_NUMBER_END = new Key<>("COLUMN_NUMBER_END");

}

/** A method instance which always has to reference a method and may have type argument children if the refered method is a template. */
public static class METHOD_INST { 
/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** Full name of an element, e.g., the class name along, including its package. */
public static final Key<String> FULL_NAME = new Key<>("FULL_NAME");

/** Method signature */
public static final Key<String> SIGNATURE = new Key<>("SIGNATURE");

/** The FULL_NAME of a method. Used to link METHOD_INST and METHOD nodes. It is required to have exactly one METHOD node for each METHOD_FULL_NAME. */
public static final Key<String> METHOD_FULL_NAME = new Key<>("METHOD_FULL_NAME");

}

/** This node represents a formal parameter going towards the callee side. */
public static class METHOD_PARAMETER_IN { 
/** The code snippet the node represents */
public static final Key<String> CODE = new Key<>("CODE");

/** General ordering property. E.g. used to express the ordering of children in the AST. */
public static final Key<Integer> ORDER = new Key<>("ORDER");

/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** Evaluation strategy for function parameters and return values. One of the values in "evaluationStrategies". */
public static final Key<String> EVALUATION_STRATEGY = new Key<>("EVALUATION_STRATEGY");

/** The static type of an entity. E.g. expressions, local, parameters etc. This property is matched against the FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node for each TYPE_FULL_NAME. */
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

/** This node represents a formal parameter going towards the caller side. */
public static class METHOD_PARAMETER_OUT { 
/** The code snippet the node represents */
public static final Key<String> CODE = new Key<>("CODE");

/** General ordering property. E.g. used to express the ordering of children in the AST. */
public static final Key<Integer> ORDER = new Key<>("ORDER");

/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** Evaluation strategy for function parameters and return values. One of the values in "evaluationStrategies". */
public static final Key<String> EVALUATION_STRATEGY = new Key<>("EVALUATION_STRATEGY");

/** The static type of an entity. E.g. expressions, local, parameters etc. This property is matched against the FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node for each TYPE_FULL_NAME. */
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

/** Reference to a method instance. */
public static class METHOD_REF { 
/** The code snippet the node represents */
public static final Key<String> CODE = new Key<>("CODE");

/** General ordering property. E.g. used to express the ordering of children in the AST. */
public static final Key<Integer> ORDER = new Key<>("ORDER");

/** The index of a call argument. This is used for the association between arguments and parameters. This property is different from ORDER for named arguments because of the required reordering to align arguments with parameters. */
public static final Key<Integer> ARGUMENT_INDEX = new Key<>("ARGUMENT_INDEX");

/** The FULL_NAME of a method instance. Used to link CALL and METHOD_REF nodes to METHOD_INST nodes. There needs to be at least one METHOD_INST node for each METHOD_INST_FULL_NAME. */
public static final Key<String> METHOD_INST_FULL_NAME = new Key<>("METHOD_INST_FULL_NAME");

/** Line where the code starts */
public static final Key<Integer> LINE_NUMBER = new Key<>("LINE_NUMBER");

/** Line where the code ends */
public static final Key<Integer> LINE_NUMBER_END = new Key<>("LINE_NUMBER_END");

/** Column where the code starts */
public static final Key<Integer> COLUMN_NUMBER = new Key<>("COLUMN_NUMBER");

/** Column where the code ends */
public static final Key<Integer> COLUMN_NUMBER_END = new Key<>("COLUMN_NUMBER_END");

}

/** A formal method return */
public static class METHOD_RETURN { 
/** The code snippet the node represents */
public static final Key<String> CODE = new Key<>("CODE");

/** Evaluation strategy for function parameters and return values. One of the values in "evaluationStrategies". */
public static final Key<String> EVALUATION_STRATEGY = new Key<>("EVALUATION_STRATEGY");

/** The static type of an entity. E.g. expressions, local, parameters etc. This property is matched against the FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node for each TYPE_FULL_NAME. */
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

/** The static-modifier */
public static class MODIFIER { 
/** Indicates the which modifier is represented by a MODIFIER node. See modifierTypes. */
public static final Key<String> MODIFIER_TYPE = new Key<>("MODIFIER_TYPE");

}

/** This node represents a namespace as a hole whereas the NAMESPACE_BLOCK is used for each grouping occurrence of a namespace in code. Single representing NAMESPACE node is required for easier navigation in the query language. */
public static class NAMESPACE { 
/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

}

/** A reference to a namespace. */
public static class NAMESPACE_BLOCK { 
/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** Full name of an element, e.g., the class name along, including its package. */
public static final Key<String> FULL_NAME = new Key<>("FULL_NAME");

}

/** A return instruction. */
public static class RETURN { 
/** Line where the code starts */
public static final Key<Integer> LINE_NUMBER = new Key<>("LINE_NUMBER");

/** Line where the code ends */
public static final Key<Integer> LINE_NUMBER_END = new Key<>("LINE_NUMBER_END");

/** Column where the code starts */
public static final Key<Integer> COLUMN_NUMBER = new Key<>("COLUMN_NUMBER");

/** Column where the code ends */
public static final Key<Integer> COLUMN_NUMBER_END = new Key<>("COLUMN_NUMBER_END");

/** General ordering property. E.g. used to express the ordering of children in the AST. */
public static final Key<Integer> ORDER = new Key<>("ORDER");

/** The index of a call argument. This is used for the association between arguments and parameters. This property is different from ORDER for named arguments because of the required reordering to align arguments with parameters. */
public static final Key<Integer> ARGUMENT_INDEX = new Key<>("ARGUMENT_INDEX");

/** The code snippet the node represents */
public static final Key<String> CODE = new Key<>("CODE");

}

/** A string tag. */
public static class TAG { 
/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** Tag value */
public static final Key<String> VALUE = new Key<>("VALUE");

}

/** A type which always has to reference a type declaration and may have type argument children if the refered type declaration is a template. */
public static class TYPE { 
/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** Full name of an element, e.g., the class name along, including its package. */
public static final Key<String> FULL_NAME = new Key<>("FULL_NAME");

/** The static type decl of a TYPE. This property is matched against the FULL_NAME of TYPE_DECL nodes. It is required to have exactly one TYPE_DECL for each different TYPE_DECL_FULL_NAME. */
public static final Key<String> TYPE_DECL_FULL_NAME = new Key<>("TYPE_DECL_FULL_NAME");

}

/** Argument for a TYPE_PARAMETER and belonging to a TYPE or METHOD_INST. It binds another TYPE to a TYPE_PARAMETER. */
public static class TYPE_ARGUMENT { 
}

/** A type declaration. */
public static class TYPE_DECL { 
/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** Full name of an element, e.g., the class name along, including its package. */
public static final Key<String> FULL_NAME = new Key<>("FULL_NAME");

/** Indicates that the construct is external, that is, it is referenced but not defined in the code */
public static final Key<Boolean> IS_EXTERNAL = new Key<>("IS_EXTERNAL");

/** The static types a TYPE_DECL inherits from. This property is matched against the FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node for each TYPE_FULL_NAME. */
public static final Key<String> INHERITS_FROM_TYPE_FULL_NAME = new Key<>("INHERITS_FROM_TYPE_FULL_NAME");

/** The type of the AST parent. Since this is only used in some parts of the graph the list does not include all possible parents by intention. Possible parents: METHOD, TYPE_DECL, NAMESPACE_BLOCK. */
public static final Key<String> AST_PARENT_TYPE = new Key<>("AST_PARENT_TYPE");

/** The FULL_NAME of a the AST parent of an entity. */
public static final Key<String> AST_PARENT_FULL_NAME = new Key<>("AST_PARENT_FULL_NAME");

}

/** Type parameter of TYPE_DECL or METHOD. */
public static class TYPE_PARAMETER { 
/** Name of represented object, e.g., method name */
public static final Key<String> NAME = new Key<>("NAME");

/** General ordering property. E.g. used to express the ordering of children in the AST. */
public static final Key<Integer> ORDER = new Key<>("ORDER");

}

/** A language-specific node */
public static class UNKNOWN { 
/** The code snippet the node represents */
public static final Key<String> CODE = new Key<>("CODE");

/** Type name emitted by parser, only present for logical type  UNKNOWN */
public static final Key<String> PARSER_TYPE_NAME = new Key<>("PARSER_TYPE_NAME");

/** General ordering property. E.g. used to express the ordering of children in the AST. */
public static final Key<Integer> ORDER = new Key<>("ORDER");

/** The index of a call argument. This is used for the association between arguments and parameters. This property is different from ORDER for named arguments because of the required reordering to align arguments with parameters. */
public static final Key<Integer> ARGUMENT_INDEX = new Key<>("ARGUMENT_INDEX");

/** The static type of an entity. E.g. expressions, local, parameters etc. This property is matched against the FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node for each TYPE_FULL_NAME. */
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



}
