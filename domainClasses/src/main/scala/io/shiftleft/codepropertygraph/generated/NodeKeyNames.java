package io.shiftleft.codepropertygraph.generated;

import overflowdb.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class NodeKeyNames {

 /** The programming language this graph originates from */
 public static final String LANGUAGE = "LANGUAGE";

 /** A version, given as a string */
 public static final String VERSION = "VERSION";

 /** Names of overlays applied to this graph, in order of application */
 public static final String OVERLAYS = "OVERLAYS";

 /** Hash value of the artifact that this CPG is built from. */
 public static final String HASH = "HASH";

 /** Line where the code starts */
 public static final String LINE_NUMBER = "LINE_NUMBER";

 /** Column where the code starts */
 public static final String COLUMN_NUMBER = "COLUMN_NUMBER";

 /** Line where the code ends */
 public static final String LINE_NUMBER_END = "LINE_NUMBER_END";

 /** Column where the code ends */
 public static final String COLUMN_NUMBER_END = "COLUMN_NUMBER_END";

 /** Type name emitted by parser, only present for logical type UNKNOWN */
 public static final String PARSER_TYPE_NAME = "PARSER_TYPE_NAME";

 /** General ordering property, such that the children of each AST-node are typically numbered from 1, ..., N (this is not enforced). The ordering has no technical meaning, but is used for pretty printing and OUGHT TO reflect order in the source code */
 public static final String ORDER = "ORDER";

 /** AST-children of CALL nodes have an argument index, that is used to match call-site arguments with callee parameters. Explicit parameters are numbered from 1 to N, while index 0 is reserved for implicit self / this parameter. CALLs without implicit parameter therefore have arguments starting with index 1. AST-children of BLOCK nodes may have an argument index as well; in this case, the last argument index determines the return-value of a BLOCK expression */
 public static final String ARGUMENT_INDEX = "ARGUMENT_INDEX";

 /** Indicates that the construct (METHOD or TYPE_DECL) is external, that is, it is referenced but not defined in the code (applies both to insular parsing and to library functions where we have header files only) */
 public static final String IS_EXTERNAL = "IS_EXTERNAL";

 /** Name of represented object, e.g., method name (e.g. "run") */
 public static final String NAME = "NAME";

 /** Full name of an element, e.g., the class name along, including its package (e.g. "io.shiftleft.dataflowenging.layers.dataflows.DataFlowRunner.run"). In theory, the FULL_NAME just needs to be unique and is used for linking references, so a consecutive integer would be valid. In practice, this should be human readable */
 public static final String FULL_NAME = "FULL_NAME";

 /** Canonical token of a FIELD_IDENTIFIER. Typically identical to the CODE field, but canonicalized according to source language semantics. Human readable names are preferable. FIELD_IDENTIFIERs must share identical CANONICAL_NAME if and only if they alias, e.g. in C-style unions (if the aliasing relationship is unknown or there are partial overlaps, then one must make a reasonable guess, and trade off between false negatives and false positives) */
 public static final String CANONICAL_NAME = "CANONICAL_NAME";

 /** The code snippet the node represents */
 public static final String CODE = "CODE";

 /** Method signature. The format is defined by the language front-end, and the backend simply compares strings to resolve function overloading, i.e. match call-sites to METHODs. In theory, consecutive integers would be valid, but in practice this should be human readable */
 public static final String SIGNATURE = "SIGNATURE";

 /** Indicates the modifier which is represented by a MODIFIER node. See modifierTypes */
 public static final String MODIFIER_TYPE = "MODIFIER_TYPE";

 /** Indicates the control structure type. See controlStructureTypes */
 public static final String CONTROL_STRUCTURE_TYPE = "CONTROL_STRUCTURE_TYPE";

 /** The static type of an entity. E.g. expressions, local, parameters etc. This property is matched against the FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node for each TYPE_FULL_NAME */
 public static final String TYPE_FULL_NAME = "TYPE_FULL_NAME";

 /** The static type decl of a TYPE. This property is matched against the FULL_NAME of TYPE_DECL nodes. It is required to have exactly one TYPE_DECL for each different TYPE_DECL_FULL_NAME */
 public static final String TYPE_DECL_FULL_NAME = "TYPE_DECL_FULL_NAME";

 /** The static types a TYPE_DECL inherits from. This property is matched against the FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node for each TYPE_FULL_NAME */
 public static final String INHERITS_FROM_TYPE_FULL_NAME = "INHERITS_FROM_TYPE_FULL_NAME";

 /** The FULL_NAME of a method. Used to link CALL and METHOD nodes. It is required to have exactly one METHOD node for each METHOD_FULL_NAME */
 public static final String METHOD_FULL_NAME = "METHOD_FULL_NAME";

 /** Deprecated */
 public static final String METHOD_INST_FULL_NAME = "METHOD_INST_FULL_NAME";

 /** Type full name of which a TYPE_DECL is an alias of */
 public static final String ALIAS_TYPE_FULL_NAME = "ALIAS_TYPE_FULL_NAME";

 /** Full path of canonical file that contained this node; will be linked into corresponding FILE nodes. Possible for METHOD, TYPE_DECL and NAMESPACE_BLOCK */
 public static final String FILENAME = "FILENAME";

 /** References to other nodes. This is not a real property; it exists here for the sake of proto serialization only. valueType and cardinality are meaningless. */
 public static final String CONTAINED_REF = "CONTAINED_REF";

 /** Tag value */
 public static final String VALUE = "VALUE";

 /** True if the referenced method is never overridden by the subclasses and false otherwise */
 public static final String IS_METHOD_NEVER_OVERRIDDEN = "IS_METHOD_NEVER_OVERRIDDEN";

 /** Sub directories of the policy directory that should be loaded when processing the CPG */
 public static final String POLICY_DIRECTORIES = "POLICY_DIRECTORIES";

 /** Evaluation strategy for function parameters and return values. One of the values in "evaluationStrategies" */
 public static final String EVALUATION_STRATEGY = "EVALUATION_STRATEGY";

 /** The dispatch type of a call, which is either static or dynamic. See dispatchTypes */
 public static final String DISPATCH_TYPE = "DISPATCH_TYPE";

 /** Type hint for the dynamic type */
 public static final String DYNAMIC_TYPE_HINT_FULL_NAME = "DYNAMIC_TYPE_HINT_FULL_NAME";

 /** The type of the AST parent. Since this is only used in some parts of the graph the list does not include all possible parents by intention. Possible parents: METHOD, TYPE_DECL, NAMESPACE_BLOCK */
 public static final String AST_PARENT_TYPE = "AST_PARENT_TYPE";

 /** The FULL_NAME of a the AST parent of an entity */
 public static final String AST_PARENT_FULL_NAME = "AST_PARENT_FULL_NAME";

 /** Binary type signature */
 public static final String BINARY_SIGNATURE = "BINARY_SIGNATURE";

 /** Content of CONFIG_FILE node */
 public static final String CONTENT = "CONTENT";

 /** Identifier which uniquely describes a CLOSURE_BINDING. This property is used to match captured LOCAL nodes with the corresponding CLOSURE_BINDING nodes */
 public static final String CLOSURE_BINDING_ID = "CLOSURE_BINDING_ID";

 /** The original name of the (potentially mangled) captured variable */
 public static final String CLOSURE_ORIGINAL_NAME = "CLOSURE_ORIGINAL_NAME";

 /** The group ID for a dependency */
 public static final String DEPENDENCY_GROUP_ID = "DEPENDENCY_GROUP_ID";

 /** The depth first ordering number used to detect back edges in reducible CFGs */
 public static final String DEPTH_FIRST_ORDER = "DEPTH_FIRST_ORDER";

 /** Marks that a method has at least one mapping defined from the policies */
 public static final String HAS_MAPPING = "HAS_MAPPING";

 /** Internal flags */
 public static final String INTERNAL_FLAGS = "INTERNAL_FLAGS";

 
 public static final String KEY = "KEY";

 
 public static final String SYMBOL = "SYMBOL";

 
 public static final String METHOD_SHORT_NAME = "METHOD_SHORT_NAME";

 
 public static final String PACKAGE_NAME = "PACKAGE_NAME";

 
 public static final String CLASS_NAME = "CLASS_NAME";

 
 public static final String CLASS_SHORT_NAME = "CLASS_SHORT_NAME";

 
 public static final String NODE_LABEL = "NODE_LABEL";

 
 public static final String SOURCE_TYPE = "SOURCE_TYPE";

 
 public static final String SINK_TYPE = "SINK_TYPE";


 public static Set<String> ALL = new HashSet<String>() {{
     add(LANGUAGE);
     add(VERSION);
     add(OVERLAYS);
     add(HASH);
     add(LINE_NUMBER);
     add(COLUMN_NUMBER);
     add(LINE_NUMBER_END);
     add(COLUMN_NUMBER_END);
     add(PARSER_TYPE_NAME);
     add(ORDER);
     add(ARGUMENT_INDEX);
     add(IS_EXTERNAL);
     add(NAME);
     add(FULL_NAME);
     add(CANONICAL_NAME);
     add(CODE);
     add(SIGNATURE);
     add(MODIFIER_TYPE);
     add(CONTROL_STRUCTURE_TYPE);
     add(TYPE_FULL_NAME);
     add(TYPE_DECL_FULL_NAME);
     add(INHERITS_FROM_TYPE_FULL_NAME);
     add(METHOD_FULL_NAME);
     add(METHOD_INST_FULL_NAME);
     add(ALIAS_TYPE_FULL_NAME);
     add(FILENAME);
     add(CONTAINED_REF);
     add(VALUE);
     add(IS_METHOD_NEVER_OVERRIDDEN);
     add(POLICY_DIRECTORIES);
     add(EVALUATION_STRATEGY);
     add(DISPATCH_TYPE);
     add(DYNAMIC_TYPE_HINT_FULL_NAME);
     add(AST_PARENT_TYPE);
     add(AST_PARENT_FULL_NAME);
     add(BINARY_SIGNATURE);
     add(CONTENT);
     add(CLOSURE_BINDING_ID);
     add(CLOSURE_ORIGINAL_NAME);
     add(DEPENDENCY_GROUP_ID);
     add(DEPTH_FIRST_ORDER);
     add(HAS_MAPPING);
     add(INTERNAL_FLAGS);
     add(KEY);
     add(SYMBOL);
     add(METHOD_SHORT_NAME);
     add(PACKAGE_NAME);
     add(CLASS_NAME);
     add(CLASS_SHORT_NAME);
     add(NODE_LABEL);
     add(SOURCE_TYPE);
     add(SINK_TYPE);
 }};

}