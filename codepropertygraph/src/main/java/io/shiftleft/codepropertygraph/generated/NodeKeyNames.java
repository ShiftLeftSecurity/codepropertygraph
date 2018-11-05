package io.shiftleft.codepropertygraph.generated;

import gremlin.scala.Key;

public class NodeKeyNames {

/** The index of a call argument. This is used for the association between arguments and parameters. This property is different from ORDER for named arguments because of the required reordering to align arguments with parameters. */
public static final String ARGUMENT_INDEX = "ARGUMENT_INDEX";

/** The FULL_NAME of a the AST parent of an entity. */
public static final String AST_PARENT_FULL_NAME = "AST_PARENT_FULL_NAME";

/** The type of the AST parent. Since this is only used in some parts of the graph the list does not include all possible parents by intention. Possible parents: METHOD, TYPE_DECL, NAMESPACE_BLOCK. */
public static final String AST_PARENT_TYPE = "AST_PARENT_TYPE";

/** Identifier which uniquely describes a CLOSURE_BINDING. This property is used to match captured LOCAL nodes with the corresponding CLOSURE_BINDING nodes. */
public static final String CLOSURE_BINDING_ID = "CLOSURE_BINDING_ID";

/** The code snippet the node represents */
public static final String CODE = "CODE";

/** Column where the code starts */
public static final String COLUMN_NUMBER = "COLUMN_NUMBER";

/** Column where the code ends */
public static final String COLUMN_NUMBER_END = "COLUMN_NUMBER_END";

/** The dispatch type of a call, which is either static or dynamic. See dispatchTypes. */
public static final String DISPATCH_TYPE = "DISPATCH_TYPE";

/** Evaluation strategy for function parameters and return values. One of the values in "evaluationStrategies". */
public static final String EVALUATION_STRATEGY = "EVALUATION_STRATEGY";

/** Full name of an element, e.g., the class name along, including its package. */
public static final String FULL_NAME = "FULL_NAME";

/** The static types a TYPE_DECL inherits from. This property is matched against the FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node for each TYPE_FULL_NAME. */
public static final String INHERITS_FROM_TYPE_FULL_NAME = "INHERITS_FROM_TYPE_FULL_NAME";

/** Indicates that the construct is external, that is, it is referenced but not defined in the code */
public static final String IS_EXTERNAL = "IS_EXTERNAL";

/** The programming language this graph originates from */
public static final String LANGUAGE = "LANGUAGE";

/** Line where the code starts */
public static final String LINE_NUMBER = "LINE_NUMBER";

/** Line where the code ends */
public static final String LINE_NUMBER_END = "LINE_NUMBER_END";

/** The FULL_NAME of a method. Used to link METHOD_INST and METHOD nodes. It is required to have exactly one METHOD node for each METHOD_FULL_NAME. */
public static final String METHOD_FULL_NAME = "METHOD_FULL_NAME";

/** The FULL_NAME of a method instance. Used to link CALL and METHOD_REF nodes to METHOD_INST nodes. There needs to be at least one METHOD_INST node for each METHOD_INST_FULL_NAME. */
public static final String METHOD_INST_FULL_NAME = "METHOD_INST_FULL_NAME";

/** Indicates the which modifier is represented by a MODIFIER node. See modifierTypes. */
public static final String MODIFIER_TYPE = "MODIFIER_TYPE";

/** Name of represented object, e.g., method name */
public static final String NAME = "NAME";

/** General ordering property. E.g. used to express the ordering of children in the AST. */
public static final String ORDER = "ORDER";

/** Type name emitted by parser, only present for logical type  UNKNOWN */
public static final String PARSER_TYPE_NAME = "PARSER_TYPE_NAME";

/** Method signature */
public static final String SIGNATURE = "SIGNATURE";

/** The static type decl of a TYPE. This property is matched against the FULL_NAME of TYPE_DECL nodes. It is required to have exactly one TYPE_DECL for each different TYPE_DECL_FULL_NAME. */
public static final String TYPE_DECL_FULL_NAME = "TYPE_DECL_FULL_NAME";

/** The static type of an entity. E.g. expressions, local, parameters etc. This property is matched against the FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node for each TYPE_FULL_NAME. */
public static final String TYPE_FULL_NAME = "TYPE_FULL_NAME";

/** Tag value */
public static final String VALUE = "VALUE";

/** A version, given as a string */
public static final String VERSION = "VERSION";


}
