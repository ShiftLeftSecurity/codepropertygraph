package io.shiftleft.codepropertygraph.generated;

import java.util.HashSet;
import java.util.Set;

public class PropertyNames {

/** This property holds the fully qualified name of the type that the node is
a type alias of. */
public static final String ALIAS_TYPE_FULL_NAME = "ALIAS_TYPE_FULL_NAME";

/** AST-children of CALL nodes have an argument index, that is used to match
call-site arguments with callee parameters. Explicit parameters are numbered
from 1 to N, while index 0 is reserved for implicit self / this parameter.
CALLs without implicit parameter therefore have arguments starting with index 1.
AST-children of BLOCK nodes may have an argument index as well; in this case,
the last argument index determines the return expression of a BLOCK expression.
If the `PARAMETER_NAME` field is set, then the `ARGUMENT_INDEX` field is
ignored. It is suggested to set it to -1. */
public static final String ARGUMENT_INDEX = "ARGUMENT_INDEX";

/** For calls involving named parameters, the `ARGUMENT_NAME` field holds the
name of the parameter initialized by the expression. For all other calls,
this field is unset. */
public static final String ARGUMENT_NAME = "ARGUMENT_NAME";

/** This field holds the FULL_NAME of the AST parent of an entity. */
public static final String AST_PARENT_FULL_NAME = "AST_PARENT_FULL_NAME";

/** The type of the AST parent. Since this is only used in some parts of the graph,
the list does not include all possible parents by intention.
Possible parents: METHOD, TYPE_DECL, NAMESPACE_BLOCK. */
public static final String AST_PARENT_TYPE = "AST_PARENT_TYPE";

/** This field holds the canonical name of a `FIELD_IDENTIFIER`. It is typically
identical to the CODE field, but canonicalized according to source language
semantics. Human readable names are preferable. `FIELD_IDENTIFIER` nodes must
share identical `CANONICAL_NAME` if and
only if they alias, e.g., in C-style unions (if the aliasing relationship is
unknown or there are partial overlaps, then one must make a reasonable guess,
and trade off between false negatives and false positives). */
public static final String CANONICAL_NAME = "CANONICAL_NAME";


public static final String CLASS_NAME = "CLASS_NAME";


public static final String CLASS_SHORT_NAME = "CLASS_SHORT_NAME";

/** Identifier which uniquely describes a CLOSURE_BINDING. This property is used to match captured LOCAL nodes with the corresponding CLOSURE_BINDING nodes */
public static final String CLOSURE_BINDING_ID = "CLOSURE_BINDING_ID";

/** The original name of the (potentially mangled) captured variable */
public static final String CLOSURE_ORIGINAL_NAME = "CLOSURE_ORIGINAL_NAME";

/** This field holds the code snippet that the node represents. */
public static final String CODE = "CODE";

/** This optional fields provides the column number of the program construct
represented by the node. */
public static final String COLUMN_NUMBER = "COLUMN_NUMBER";

/** This optional fields provides the column number at which the program construct
represented by the node ends. */
public static final String COLUMN_NUMBER_END = "COLUMN_NUMBER_END";

/** References to other nodes. This is not a real property; it exists here for the sake of proto serialization only. valueType and cardinality are meaningless. */
public static final String CONTAINED_REF = "CONTAINED_REF";

/** Certain files, e.g., configuration files, may be included in the CPG as-is.
For such files, the `CONTENT` field contains the files content. */
public static final String CONTENT = "CONTENT";

/** The `CONTROL_STRUCTURE_TYPE` field indicates which kind of control structure
a `CONTROL_STRUCTURE` node represents. The available types are the following:
 BREAK, CONTINUE, DO, WHILE, FOR, GOTO, IF, ELSE, TRY, THROW and SWITCH. */
public static final String CONTROL_STRUCTURE_TYPE = "CONTROL_STRUCTURE_TYPE";

/** The group ID for a dependency */
public static final String DEPENDENCY_GROUP_ID = "DEPENDENCY_GROUP_ID";

/** This field holds the dispatch type of a call, which is either `STATIC_DISPATCH` or
`DYNAMIC_DISPATCH`. For statically dispatched method calls, the call target is known
at compile time while for dynamically dispatched calls, it can only be determined at
runtime as it may depend on the type of an object (as is the case for virtual method
calls) or calculation of an offset. */
public static final String DISPATCH_TYPE = "DISPATCH_TYPE";

/** Type hint for the dynamic type. These are observed to be verifiable at runtime. */
public static final String DYNAMIC_TYPE_HINT_FULL_NAME = "DYNAMIC_TYPE_HINT_FULL_NAME";

/** For formal method input parameters, output parameters, and return parameters,
this field holds the evaluation strategy, which is one of the following:
1) `BY_REFERENCE` indicates that the parameter is passed by reference, 2)
`BY_VALUE` indicates that it is passed by value, that is, a copy is made,
3) `BY_SHARING` the parameter is a pointer/reference and it is shared with
the caller/callee. While a copy of the pointer is made, a copy of the object
that it points to is not made. */
public static final String EVALUATION_STRATEGY = "EVALUATION_STRATEGY";

/** Specifies whether the IMPORTED_AS property was explicitly present in the code.
For languages like Java which do not allow a renaming during import this is
always false. For e.g. Kotlin it depends on the existence of the "as" keyword. */
public static final String EXPLICIT_AS = "EXPLICIT_AS";

/** The path of the source file this node was generated from, relative to the root
path in the meta data node. This field must be set but may be set to the value `<unknown>` to
indicate that no source file can be associated with the node, e.g., because the node represents
an entity known to exist because it is referenced, but for which the file that is is declared in
is unknown. */
public static final String FILENAME = "FILENAME";

/** This is the fully-qualified name of an entity, e.g., the fully-qualified
name of a method or type. The details of what constitutes a fully-qualified
name are language specific. This field SHOULD be human readable. */
public static final String FULL_NAME = "FULL_NAME";

/** This property contains a hash value in the form of a string.
Hashes can be used to summarize data, e.g., to summarize the
contents of source files or sub graphs. Such summaries are useful
to determine whether code has already been analyzed in incremental
analysis pipelines. This property is optional to allow its calculation
to be deferred or skipped if the hash is not needed. */
public static final String HASH = "HASH";

/** The identifier under which the import can be accessed in the importing context.
For a Java import this is always identical to the class name. But e.g. for a
Kotlin import like "import java.nio.ByteBuffer as BBuffer" this would be "BBuffer".
This property is ignored if IS_WILDCARD is true. */
public static final String IMPORTED_AS = "IMPORTED_AS";

/** The identifying string of the imported entity.
For a Java import like "import java.nio.ByteBuffer;" this would be "java.nio.ByteBuffer". */
public static final String IMPORTED_ENTITY = "IMPORTED_ENTITY";

/** Specifies an index, e.g., for a parameter or argument.
 Explicit parameters are numbered from 1 to N, while index 0 is reserved for implicit
 self / this parameter. */
public static final String INDEX = "INDEX";

/** The static types a TYPE_DECL inherits from. This property is matched against the
FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node
for each TYPE_FULL_NAME */
public static final String INHERITS_FROM_TYPE_FULL_NAME = "INHERITS_FROM_TYPE_FULL_NAME";

/** Specifies whether this is an explicit import.
Most languages have implicit default imports of some standard library elements
and this flag is used to distinguish those from explicit imports found in the
code base. */
public static final String IS_EXPLICIT = "IS_EXPLICIT";

/** Indicates that the construct (METHOD or TYPE_DECL) is external, that is,
it is referenced but not defined in the code (applies both to insular
parsing and to library functions where we have header files only) */
public static final String IS_EXTERNAL = "IS_EXTERNAL";

/** Specifies whether a parameter is the variadic argument handling parameter of
a variadic method. Only one parameter of a method is allowed to have this
property set to true. */
public static final String IS_VARIADIC = "IS_VARIADIC";

/** Specifies whether this is a wildcard import.
For a Java import like "import java.nio.*;" IS_WILDCARD would be "true" and
IMPORTED_ENTITY would be "java.nio".
For wildcard imports the IMPORTED_AS property is ignored. */
public static final String IS_WILDCARD = "IS_WILDCARD";

/** This property denotes a key of a key-value pair. */
public static final String KEY = "KEY";

/** This field indicates which CPG language frontend generated the CPG.
Frontend developers may freely choose a value that describes their frontend
so long as it is not used by an existing frontend. Reserved values are to date:
C, LLVM, GHIDRA, PHP. */
public static final String LANGUAGE = "LANGUAGE";

/** This optional field provides the line number of the program construct
represented by the node. */
public static final String LINE_NUMBER = "LINE_NUMBER";

/** This optional fields provides the line number at which the program construct
represented by the node ends. */
public static final String LINE_NUMBER_END = "LINE_NUMBER_END";

/** The FULL_NAME of a method. Used to link CALL and METHOD nodes. It is required
to have exactly one METHOD node for each METHOD_FULL_NAME */
public static final String METHOD_FULL_NAME = "METHOD_FULL_NAME";


public static final String METHOD_SHORT_NAME = "METHOD_SHORT_NAME";

/** The modifier type is a free-form string. The following are known modifier types:
`STATIC`, `PUBLIC`, `PROTECTED`, `PRIVATE`, `ABSTRACT`, `NATIVE`, `CONSTRUCTOR`, `VIRTUAL`. */
public static final String MODIFIER_TYPE = "MODIFIER_TYPE";

/** Name of represented object, e.g., method name (e.g. "run") */
public static final String NAME = "NAME";


public static final String NODE_LABEL = "NODE_LABEL";

/** Start offset into the CONTENT property of the corresponding FILE node.
The offset is such that parts of the content can easily
be accessed via `content.substring(offset, offsetEnd)`.
This means that the offset must be measured in utf16 encoding (i.e. neither in
characters/codeunits nor in byte-offsets into a utf8 encoding).
E.g. for METHOD nodes this start offset points to the start of the methods
source code in the string holding the source code of the entire file. */
public static final String OFFSET = "OFFSET";

/** End offset (exclusive) into the CONTENT property of the corresponding FILE node.
See OFFSET documentation for finer details.
E.g. for METHOD nodes this end offset points to the first code position which is
not part of the method. */
public static final String OFFSET_END = "OFFSET_END";

/** This integer indicates the position of the node among
its siblings in the AST. The left-most child has an
order of 0. */
public static final String ORDER = "ORDER";

/** The field contains the names of the overlays applied to this CPG, in order of their
application. Names are free-form strings, that is, this specification does not
dictate them but rather requires tool producers and consumers to communicate them
between each other. */
public static final String OVERLAYS = "OVERLAYS";


public static final String PACKAGE_NAME = "PACKAGE_NAME";

/** AST node type name emitted by parser. */
public static final String PARSER_TYPE_NAME = "PARSER_TYPE_NAME";

/** Similar to `DYNAMIC_TYPE_HINT_FULL_NAME`, but that this makes no guarantee that types within this property are correct. This property is used to capture observations between node interactions during a 'may-analysis'. */
public static final String POSSIBLE_TYPES = "POSSIBLE_TYPES";

/** The path to the root directory of the source/binary this CPG is generated from. */
public static final String ROOT = "ROOT";

/** ID from a different context, e.g. if the graph was imported from a different format,
we can use this to preserve the link to the original */
public static final String SECONDARY_ID = "SECONDARY_ID";

/** The method signature encodes the types of parameters in a string.
The string SHOULD be human readable and suitable for differentiating methods
with different parameter types sufficiently to allow for resolving of
function overloading. The present specification does not enforce a strict
format for the signature, that is, it can be chosen by the frontend
implementor to fit the source language. */
public static final String SIGNATURE = "SIGNATURE";


public static final String SYMBOL = "SYMBOL";

/** The static type decl of a TYPE. This property is matched against the FULL_NAME
of TYPE_DECL nodes. It is required to have exactly one TYPE_DECL for each
different TYPE_DECL_FULL_NAME */
public static final String TYPE_DECL_FULL_NAME = "TYPE_DECL_FULL_NAME";

/** This field contains the fully-qualified static type name of the program
construct represented by a node. It is the name of an instantiated type, e.g.,
`java.util.List<Integer>`, rather than `java.util.List[T]`. If the type
cannot be determined, this field should be set to the empty string. */
public static final String TYPE_FULL_NAME = "TYPE_FULL_NAME";

/** This property denotes a string value as used in a key-value pair. */
public static final String VALUE = "VALUE";

/** This edge property represents the variable propagated by a reaching definition edge. */
public static final String VARIABLE = "VARIABLE";

/** A version, given as a string. Used, for example, in the META_DATA node to
indicate which version of the CPG spec this CPG conforms to */
public static final String VERSION = "VERSION";

public static Set<String> ALL = new HashSet<String>() {{
add(ALIAS_TYPE_FULL_NAME);
add(ARGUMENT_INDEX);
add(ARGUMENT_NAME);
add(AST_PARENT_FULL_NAME);
add(AST_PARENT_TYPE);
add(CANONICAL_NAME);
add(CLASS_NAME);
add(CLASS_SHORT_NAME);
add(CLOSURE_BINDING_ID);
add(CLOSURE_ORIGINAL_NAME);
add(CODE);
add(COLUMN_NUMBER);
add(COLUMN_NUMBER_END);
add(CONTAINED_REF);
add(CONTENT);
add(CONTROL_STRUCTURE_TYPE);
add(DEPENDENCY_GROUP_ID);
add(DISPATCH_TYPE);
add(DYNAMIC_TYPE_HINT_FULL_NAME);
add(EVALUATION_STRATEGY);
add(EXPLICIT_AS);
add(FILENAME);
add(FULL_NAME);
add(HASH);
add(IMPORTED_AS);
add(IMPORTED_ENTITY);
add(INDEX);
add(INHERITS_FROM_TYPE_FULL_NAME);
add(IS_EXPLICIT);
add(IS_EXTERNAL);
add(IS_VARIADIC);
add(IS_WILDCARD);
add(KEY);
add(LANGUAGE);
add(LINE_NUMBER);
add(LINE_NUMBER_END);
add(METHOD_FULL_NAME);
add(METHOD_SHORT_NAME);
add(MODIFIER_TYPE);
add(NAME);
add(NODE_LABEL);
add(OFFSET);
add(OFFSET_END);
add(ORDER);
add(OVERLAYS);
add(PACKAGE_NAME);
add(PARSER_TYPE_NAME);
add(POSSIBLE_TYPES);
add(ROOT);
add(SECONDARY_ID);
add(SIGNATURE);
add(SYMBOL);
add(TYPE_DECL_FULL_NAME);
add(TYPE_FULL_NAME);
add(VALUE);
add(VARIABLE);
add(VERSION);
}};

}