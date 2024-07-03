package io.shiftleft.codepropertygraph.generated
import flatgraph.{DiffGraphApplier, DiffGraphBuilder}
import flatgraph.help.DocSearchPackages
import flatgraph.help.Table.AvailableWidthProvider
import io.shiftleft.codepropertygraph.generated.language.*

object Cpg {
  val defaultDocSearchPackage = DocSearchPackages.default.withAdditionalPackage(getClass.getPackage.getName)

  @scala.annotation.implicitNotFound("""If you're using flatgraph purely without a schema and associated generated domain classes, you can
    |start with `given DocSearchPackages = DocSearchPackages.default`.
    |If you have generated domain classes, use `given DocSearchPackages = MyDomain.defaultDocSearchPackage`.
    |If you have additional custom extension steps that specify help texts via @Doc annotations, use `given DocSearchPackages = MyDomain.defaultDocSearchPackage.withAdditionalPackage("my.custom.package)"`
    |""".stripMargin)
  def help(implicit searchPackageNames: DocSearchPackages, availableWidthProvider: AvailableWidthProvider) =
    flatgraph.help.TraversalHelp(searchPackageNames).forTraversalSources(verbose = false)

  @scala.annotation.implicitNotFound("""If you're using flatgraph purely without a schema and associated generated domain classes, you can
    |start with `given DocSearchPackages = DocSearchPackages.default`.
    |If you have generated domain classes, use `given DocSearchPackages = MyDomain.defaultDocSearchPackage`.
    |If you have additional custom extension steps that specify help texts via @Doc annotations, use `given DocSearchPackages = MyDomain.defaultDocSearchPackage.withAdditionalPackage("my.custom.package)"`
    |""".stripMargin)
  def helpVerbose(implicit searchPackageNames: DocSearchPackages, availableWidthProvider: AvailableWidthProvider) =
    flatgraph.help.TraversalHelp(searchPackageNames).forTraversalSources(verbose = true)

  def empty: Cpg = new Cpg(new flatgraph.Graph(GraphSchema))

  def from(initialElements: DiffGraphBuilder => DiffGraphBuilder): Cpg = {
    val graph = new flatgraph.Graph(GraphSchema)
    DiffGraphApplier.applyDiff(graph, initialElements(new DiffGraphBuilder(GraphSchema)))
    new Cpg(graph)
  }

  /** Instantiate a new graph with storage. If the file already exists, this will deserialize the given file into
    * memory. `Graph.close` will serialise graph to that given file (and override whatever was there before), unless you
    * specify `persistOnClose = false`.
    */
  def withStorage(storagePath: java.nio.file.Path, persistOnClose: Boolean = true): Cpg = {
    val graph = flatgraph.Graph.withStorage(GraphSchema, storagePath, persistOnClose)
    new Cpg(graph)
  }

  def newDiffGraphBuilder: DiffGraphBuilder = new DiffGraphBuilder(GraphSchema)
}

class Cpg(private val _graph: flatgraph.Graph = new flatgraph.Graph(GraphSchema)) extends AutoCloseable {
  def graph: flatgraph.Graph = _graph

  def help(implicit searchPackageNames: DocSearchPackages, availableWidthProvider: AvailableWidthProvider) =
    Cpg.help
  def helpVerbose(implicit searchPackageNames: DocSearchPackages, availableWidthProvider: AvailableWidthProvider) =
    Cpg.helpVerbose

  override def close(): Unit =
    _graph.close()

  override def toString(): String =
    String.format("Cpg[%s]", graph)
}

@flatgraph.help.TraversalSource
class CpgNodeStarters(val wrappedCpg: Cpg) {

  @flatgraph.help.Doc(info = "all nodes")
  def all: Iterator[nodes.StoredNode] = wrappedCpg.graph.allNodes.asInstanceOf[Iterator[nodes.StoredNode]]

  def id(nodeId: Long): Iterator[nodes.StoredNode] =
    Option(wrappedCpg.graph.node(nodeId)).iterator.asInstanceOf[Iterator[nodes.StoredNode]]

  def ids(nodeIds: Long*): Iterator[nodes.StoredNode] = nodeIds.iterator.flatMap(id)

  /** A method annotation. The semantics of the FULL_NAME property on this node differ from the usual FULL_NAME
    * semantics in the sense that FULL_NAME describes the represented annotation class/interface itself and not the
    * ANNOTATION node.
    */
  @flatgraph.help.Doc(info = """A method annotation.
The semantics of the FULL_NAME property on this node differ from the usual FULL_NAME
semantics in the sense that FULL_NAME describes the represented annotation class/interface
itself and not the ANNOTATION node.""")
  def annotation: Iterator[nodes.Annotation] = wrappedCpg.graph._nodes(0).asInstanceOf[Iterator[nodes.Annotation]]

  /** A literal value assigned to an ANNOTATION_PARAMETER */
  @flatgraph.help.Doc(info = """A literal value assigned to an ANNOTATION_PARAMETER""")
  def annotationLiteral: Iterator[nodes.AnnotationLiteral] =
    wrappedCpg.graph._nodes(1).asInstanceOf[Iterator[nodes.AnnotationLiteral]]

  /** Formal annotation parameter */
  @flatgraph.help.Doc(info = """Formal annotation parameter""")
  def annotationParameter: Iterator[nodes.AnnotationParameter] =
    wrappedCpg.graph._nodes(2).asInstanceOf[Iterator[nodes.AnnotationParameter]]

  /** Assignment of annotation argument to annotation parameter */
  @flatgraph.help.Doc(info = """Assignment of annotation argument to annotation parameter""")
  def annotationParameterAssign: Iterator[nodes.AnnotationParameterAssign] =
    wrappedCpg.graph._nodes(3).asInstanceOf[Iterator[nodes.AnnotationParameterAssign]]

  /** Initialization construct for arrays */
  @flatgraph.help.Doc(info = """Initialization construct for arrays""")
  def arrayInitializer: Iterator[nodes.ArrayInitializer] =
    wrappedCpg.graph._nodes(4).asInstanceOf[Iterator[nodes.ArrayInitializer]]

  /** `BINDING` nodes represent name-signature pairs that can be resolved at a type declaration (`TYPE_DECL`). They are
    * connected to `TYPE_DECL` nodes via incoming `BINDS` edges. The bound method is either associated with an outgoing
    * `REF` edge to a `METHOD` or with the `METHOD_FULL_NAME` property. The `REF` edge if present has priority.
    */
  @flatgraph.help.Doc(info = """`BINDING` nodes represent name-signature pairs that can be resolved at a
type declaration (`TYPE_DECL`). They are connected to `TYPE_DECL` nodes via
incoming `BINDS` edges. The bound method is either associated with an outgoing
`REF` edge to a `METHOD` or with the `METHOD_FULL_NAME` property. The `REF` edge
if present has priority.""")
  def binding: Iterator[nodes.Binding] = wrappedCpg.graph._nodes(5).asInstanceOf[Iterator[nodes.Binding]]

  /** This node represents a compound statement. Compound statements are used in many languages to allow grouping a
    * sequence of statements. For example, in C and Java, compound statements are statements enclosed by curly braces.
    * Function/Method bodies are compound statements. We do not use the term "compound statement" because "statement"
    * would imply that the block does not yield a value upon evaluation, that is, that it is not an expression. This is
    * true in languages such as C and Java, but not for languages such as Scala where the value of the block is given by
    * that of the last expression it contains. In fact, the Scala grammar uses the term "BlockExpr" (short for "block
    * expression") to describe what in the CPG we call "Block".
    */
  @flatgraph.help.Doc(info =
    """This node represents a compound statement. Compound statements are used in many languages to allow
grouping a sequence of statements. For example, in C and Java, compound statements
are statements enclosed by curly braces. Function/Method bodies are compound
statements. We do not use the term "compound statement" because "statement" would
imply that the block does not yield a value upon evaluation, that is, that it is
not an expression. This is true in languages such as C and Java, but not for languages
such as Scala where the value of the block is given by that of the last expression it
contains. In fact, the Scala grammar uses the term "BlockExpr" (short for
"block expression") to describe what in the CPG we call "Block"."""
  )
  def block: Iterator[nodes.Block] = wrappedCpg.graph._nodes(6).asInstanceOf[Iterator[nodes.Block]]

  /** A (function/method/procedure) call. The `METHOD_FULL_NAME` property is the name of the invoked method (the callee)
    * while the `TYPE_FULL_NAME` is its return type, and therefore, the return type of the call when viewing it as an
    * expression. For languages like Javascript, it is common that we may know the (short-) name of the invoked method,
    * but we do not know at compile time which method will actually be invoked, e.g., because it depends on a dynamic
    * import. In this case, we leave `METHOD_FULL_NAME` blank but at least fill out `NAME`, which contains the method's
    * (short-) name and `SIGNATURE`, which contains any information we may have about the types of arguments and return
    * value.
    */
  @flatgraph.help.Doc(info = """A (function/method/procedure) call. The `METHOD_FULL_NAME` property is the name of the
invoked method (the callee) while the `TYPE_FULL_NAME` is its return type, and
therefore, the return type of the call when viewing it as an expression. For
languages like Javascript, it is common that we may know the (short-) name
of the invoked method, but we do not know at compile time which method
will actually be invoked, e.g., because it depends on a dynamic import.
In this case, we leave `METHOD_FULL_NAME` blank but at least fill out `NAME`,
which contains the method's (short-) name and `SIGNATURE`, which contains
any information we may have about the types of arguments and return value.""")
  def call: Iterator[nodes.Call] = wrappedCpg.graph._nodes(7).asInstanceOf[Iterator[nodes.Call]]

  /** Shorthand for call.name */
  def call(name: String): Iterator[nodes.Call] = call.name(name)

  /** Represents the binding of a LOCAL or METHOD_PARAMETER_IN into the closure of a method */
  @flatgraph.help.Doc(info =
    """Represents the binding of a LOCAL or METHOD_PARAMETER_IN into the closure of a method"""
  )
  def closureBinding: Iterator[nodes.ClosureBinding] =
    wrappedCpg.graph._nodes(8).asInstanceOf[Iterator[nodes.ClosureBinding]]

  /** A source code comment */
  @flatgraph.help.Doc(info = """A source code comment""")
  def comment: Iterator[nodes.Comment] = wrappedCpg.graph._nodes(9).asInstanceOf[Iterator[nodes.Comment]]

  /** Shorthand for comment.code */
  def comment(code: String): Iterator[nodes.Comment] = comment.code(code)

  /** This node type represent a configuration file, where `NAME` is the name of the file and `content` is its content.
    * The exact representation of the name is left undefined and can be chosen as required by consumers of the
    * corresponding configuration files.
    */
  @flatgraph.help.Doc(info = """This node type represent a configuration file, where `NAME` is the name
of the file and `content` is its content. The exact representation of the
name is left undefined and can be chosen as required by consumers of
the corresponding configuration files.""")
  def configFile: Iterator[nodes.ConfigFile] = wrappedCpg.graph._nodes(10).asInstanceOf[Iterator[nodes.ConfigFile]]

  /** This node represents a control structure as introduced by control structure statements as well as conditional and
    * unconditional jumps. Its type is stored in the `CONTROL_STRUCTURE_TYPE` field to be one of several pre-defined
    * types. These types are used in the construction of the control flow layer, making it possible to generate the
    * control flow layer from the abstract syntax tree layer automatically.
    *
    * In addition to the `CONTROL_STRUCTURE_TYPE` field, the `PARSER_TYPE_NAME` field MAY be used by frontends to store
    * the name of the control structure as emitted by the parser or disassembler, however, the value of this field is
    * not relevant for construction of the control flow layer.
    */
  @flatgraph.help.Doc(info = """This node represents a control structure as introduced by control structure
statements as well as conditional and unconditional jumps. Its type is stored in the
`CONTROL_STRUCTURE_TYPE` field to be one of several pre-defined types. These types
 are used in the construction of the control flow layer, making it possible to
 generate the control flow layer from the abstract syntax tree layer automatically.

In addition to the `CONTROL_STRUCTURE_TYPE` field, the `PARSER_TYPE_NAME` field
MAY be used by frontends to store the name of the control structure as emitted by
the parser or disassembler, however, the value of this field is not relevant
for construction of the control flow layer.""")
  def controlStructure: Iterator[nodes.ControlStructure] =
    wrappedCpg.graph._nodes(11).asInstanceOf[Iterator[nodes.ControlStructure]]

  /** This node represents a dependency */
  @flatgraph.help.Doc(info = """This node represents a dependency""")
  def dependency: Iterator[nodes.Dependency] = wrappedCpg.graph._nodes(12).asInstanceOf[Iterator[nodes.Dependency]]

  /** Shorthand for dependency.name */
  def dependency(name: String): Iterator[nodes.Dependency] = dependency.name(name)

  /** This node represents the field accessed in a field access, e.g., in `a.b`, it represents `b`. The field name as it
    * occurs in the code is stored in the `CODE` field. This may mean that the `CODE` field holds an expression. The
    * `CANONICAL_NAME` field MAY contain the same value is the `CODE` field but SHOULD contain the normalized name that
    * results from evaluating `CODE` as an expression if such an evaluation is possible for the language frontend. The
    * objective is to store an identifier in `CANONICAL_NAME` that is the same for two nodes iff they refer to the same
    * field, regardless of whether they use the same expression to reference it.
    */
  @flatgraph.help.Doc(info = """This node represents the field accessed in a field access, e.g., in
`a.b`, it represents `b`. The field name as it occurs in the code is
stored in the `CODE` field. This may mean that the `CODE` field holds
an expression. The `CANONICAL_NAME` field MAY contain the same value is
the `CODE` field but SHOULD contain the normalized name that results
from evaluating `CODE` as an expression if such an evaluation is
possible for the language frontend. The objective is to store an identifier
in `CANONICAL_NAME` that is the same for two nodes iff they refer to the
same field, regardless of whether they use the same expression to reference
it.""")
  def fieldIdentifier: Iterator[nodes.FieldIdentifier] =
    wrappedCpg.graph._nodes(13).asInstanceOf[Iterator[nodes.FieldIdentifier]]

  /** File nodes represent source files or a shared objects from which the CPG was generated. File nodes serve as
    * indices, that is, they allow looking up all elements of the code by file.
    *
    * For each file, the graph CAN contain exactly one File node, if not File nodes are created as indicated by
    * `FILENAME` property of other nodes. As file nodes are root nodes of abstract syntax tress, they are AstNodes and
    * their order field is set to 0. This is because they have no sibling nodes, not because they are the first node of
    * the AST.
    */
  @flatgraph.help.Doc(info = """File nodes represent source files or a shared objects from which the CPG
was generated. File nodes serve as indices, that is, they allow looking up all
elements of the code by file.

For each file, the graph CAN contain exactly one File node, if not File nodes
are created as indicated by `FILENAME` property of other nodes.
As file nodes are root nodes of abstract syntax tress, they are AstNodes and
their order field is set to 0. This is because they have no sibling nodes,
not because they are the first node of the AST.""")
  def file: Iterator[nodes.File] = wrappedCpg.graph._nodes(14).asInstanceOf[Iterator[nodes.File]]

  /** Shorthand for file.name */
  def file(name: String): Iterator[nodes.File] = file.name(name)

  /** Finding nodes may be used to store analysis results in the graph that are to be exposed to an end-user, e.g.,
    * information about potential vulnerabilities or dangerous programming practices. A Finding node may contain an
    * abitrary list of key value pairs that characterize the finding, as well as a list of nodes that serve as evidence
    * for the finding.
    */
  @flatgraph.help.Doc(info = """Finding nodes may be used to store analysis results in the graph
that are to be exposed to an end-user, e.g., information about
potential vulnerabilities or dangerous programming practices.
A Finding node may contain an abitrary list of key value pairs
that characterize the finding, as well as a list of nodes that
serve as evidence for the finding.""")
  def finding: Iterator[nodes.Finding] = wrappedCpg.graph._nodes(15).asInstanceOf[Iterator[nodes.Finding]]

  /** This node represents an identifier as used when referring to a variable by name. It holds the identifier's name in
    * the `NAME` field and its fully-qualified type name in `TYPE_FULL_NAME`.
    */
  @flatgraph.help.Doc(info = """This node represents an identifier as used when referring to a variable by name.
It holds the identifier's name in the `NAME` field and its fully-qualified type
name in `TYPE_FULL_NAME`.""")
  def identifier: Iterator[nodes.Identifier] = wrappedCpg.graph._nodes(16).asInstanceOf[Iterator[nodes.Identifier]]

  /** Shorthand for identifier.name */
  def identifier(name: String): Iterator[nodes.Identifier] = identifier.name(name)

  /** Declarative import as it is found in statically typed languages like Java. This kind of node is not supposed to be
    * used for imports in dynamically typed languages like Javascript.
    */
  @flatgraph.help.Doc(info = """Declarative import as it is found in statically typed languages like Java.
This kind of node is not supposed to be used for imports in dynamically typed
languages like Javascript.""")
  def imports: Iterator[nodes.Import] = wrappedCpg.graph._nodes(17).asInstanceOf[Iterator[nodes.Import]]

  /** A jump label specifies the label and thus the JUMP_TARGET of control structures BREAK and CONTINUE. The `NAME`
    * field holds the name of the label while the `PARSER_TYPE_NAME` field holds the name of language construct that
    * this jump label is created from, e.g., "Label".
    */
  @flatgraph.help.Doc(info = """A jump label specifies the label and thus the JUMP_TARGET of control structures
BREAK and CONTINUE. The `NAME` field holds the name of the label while the
`PARSER_TYPE_NAME` field holds the name of language construct that this jump
label is created from, e.g., "Label".""")
  def jumpLabel: Iterator[nodes.JumpLabel] = wrappedCpg.graph._nodes(18).asInstanceOf[Iterator[nodes.JumpLabel]]

  /** A jump target is any location in the code that has been specifically marked as the target of a jump, e.g., via a
    * label. The `NAME` field holds the name of the label while the `PARSER_TYPE_NAME` field holds the name of language
    * construct that this jump target is created from, e.g., "Label".
    */
  @flatgraph.help.Doc(info = """A jump target is any location in the code that has been specifically marked
as the target of a jump, e.g., via a label. The `NAME` field holds the name of
the label while the `PARSER_TYPE_NAME` field holds the name of language construct
that this jump target is created from, e.g., "Label".""")
  def jumpTarget: Iterator[nodes.JumpTarget] = wrappedCpg.graph._nodes(19).asInstanceOf[Iterator[nodes.JumpTarget]]

  /** This node represents a key value pair, where both the key and the value are strings. */
  @flatgraph.help.Doc(info = """This node represents a key value pair, where both the key and the value are strings.""")
  def keyValuePair: Iterator[nodes.KeyValuePair] =
    wrappedCpg.graph._nodes(20).asInstanceOf[Iterator[nodes.KeyValuePair]]

  /** This node represents a literal such as an integer or string constant. Literals are symbols included in the code in
    * verbatim form and which are immutable. The `TYPE_FULL_NAME` field stores the literal's fully-qualified type name,
    * e.g., `java.lang.Integer`.
    */
  @flatgraph.help.Doc(info = """This node represents a literal such as an integer or string constant. Literals
are symbols included in the code in verbatim form and which are immutable.
The `TYPE_FULL_NAME` field stores the literal's fully-qualified type name,
e.g., `java.lang.Integer`.""")
  def literal: Iterator[nodes.Literal] = wrappedCpg.graph._nodes(21).asInstanceOf[Iterator[nodes.Literal]]

  /** Shorthand for literal.code */
  def literal(code: String): Iterator[nodes.Literal] = literal.code(code)

  /** This node represents a local variable. Its fully qualified type name is stored in the `TYPE_FULL_NAME` field and
    * its name in the `NAME` field. The `CODE` field contains the entire local variable declaration without
    * initialization, e.g., for `int x = 10;`, it contains `int x`.
    */
  @flatgraph.help.Doc(info = """This node represents a local variable. Its fully qualified type name is stored
in the `TYPE_FULL_NAME` field and its name in the `NAME` field. The `CODE` field
contains the entire local variable declaration without initialization, e.g., for
`int x = 10;`, it contains `int x`.""")
  def local: Iterator[nodes.Local] = wrappedCpg.graph._nodes(22).asInstanceOf[Iterator[nodes.Local]]

  /** Shorthand for local.name */
  def local(name: String): Iterator[nodes.Local] = local.name(name)

  /** A location node summarizes a source code location. */
  @flatgraph.help.Doc(info = """A location node summarizes a source code location.""")
  def location: Iterator[nodes.Location] = wrappedCpg.graph._nodes(23).asInstanceOf[Iterator[nodes.Location]]

  /** This node represents a type member of a class, struct or union, e.g., for the type declaration `class Foo{ int i ;
    * }`, it represents the declaration of the variable `i`.
    */
  @flatgraph.help.Doc(info = """This node represents a type member of a class, struct or union, e.g., for the
 type declaration `class Foo{ int i ; }`, it represents the declaration of the
 variable `i`.""")
  def member: Iterator[nodes.Member] = wrappedCpg.graph._nodes(24).asInstanceOf[Iterator[nodes.Member]]

  /** Shorthand for member.name */
  def member(name: String): Iterator[nodes.Member] = member.name(name)

  /** This node contains the CPG meta data. Exactly one node of this type MUST exist per CPG. The `HASH` property MAY
    * contain a hash value calculated over the source files this CPG was generated from. The `VERSION` MUST be set to
    * the version of the specification ("1.1"). The language field indicates which language frontend was used to
    * generate the CPG and the list property `OVERLAYS` specifies which overlays have been applied to the CPG.
    */
  @flatgraph.help.Doc(info = """This node contains the CPG meta data. Exactly one node of this type
MUST exist per CPG. The `HASH` property MAY contain a hash value calculated
over the source files this CPG was generated from. The `VERSION` MUST be
set to the version of the specification ("1.1"). The language field indicates
which language frontend was used to generate the CPG and the list property
`OVERLAYS` specifies which overlays have been applied to the CPG.""")
  def metaData: Iterator[nodes.MetaData] = wrappedCpg.graph._nodes(25).asInstanceOf[Iterator[nodes.MetaData]]

  /** Programming languages offer many closely-related concepts for describing blocks of code that can be executed with
    * input parameters and return output parameters, possibly causing side effects. In the CPG specification, we refer
    * to all of these concepts (procedures, functions, methods, etc.) as methods. A single METHOD node must exist for
    * each method found in the source program.
    *
    * The `FULL_NAME` field specifies the method's fully-qualified name, including information about the namespace it is
    * contained in if applicable, the name field is the function's short name. The field `IS_EXTERNAL` indicates whether
    * it was possible to identify a method body for the method. This is true for methods that are defined in the source
    * program, and false for methods that are dynamically linked to the program, that is, methods that exist in an
    * external dependency.
    *
    * Line and column number information is specified in the optional fields `LINE_NUMBER`, `COLUMN_NUMBER`,
    * `LINE_NUMBER_END`, and `COLUMN_NUMBER_END` and the name of the source file is specified in `FILENAME`. An optional
    * hash value MAY be calculated over the function contents and included in the `HASH` field.
    *
    * The optional `OFFSET` and `OFFSET_END` specify the start and exclusive end position of the code belonging to a
    * method within the corresponding `FILE` nodes `CONTENT` property.
    *
    * Finally, the fully qualified name of the program constructs that the method is immediately contained in is stored
    * in the `AST_PARENT_FULL_NAME` field and its type is indicated in the `AST_PARENT_TYPE` field to be one of
    * `METHOD`, `TYPE_DECL` or `NAMESPACE_BLOCK`.
    */
  @flatgraph.help.Doc(info = """Programming languages offer many closely-related concepts for describing blocks
of code that can be executed with input parameters and return output parameters,
possibly causing side effects. In the CPG specification, we refer to all of these
concepts (procedures, functions, methods, etc.) as methods. A single METHOD node
must exist for each method found in the source program.

The `FULL_NAME` field specifies the method's fully-qualified name, including
information about the namespace it is contained in if applicable, the name field
is the function's short name. The field `IS_EXTERNAL` indicates whether it was
possible to identify a method body for the method. This is true for methods that
are defined in the source program, and false for methods that are dynamically
linked to the program, that is, methods that exist in an external dependency.

Line and column number information is specified in the optional fields
`LINE_NUMBER`, `COLUMN_NUMBER`, `LINE_NUMBER_END`, and `COLUMN_NUMBER_END` and
the name of the source file is specified in `FILENAME`. An optional hash value
MAY be calculated over the function contents and included in the `HASH` field.

The optional `OFFSET` and `OFFSET_END` specify the start
and exclusive end position of the code belonging to a method within the corresponding
`FILE` nodes `CONTENT` property.

Finally, the fully qualified name of the program constructs that the method
is immediately contained in is stored in the `AST_PARENT_FULL_NAME` field
and its type is indicated in the `AST_PARENT_TYPE` field to be one of
`METHOD`, `TYPE_DECL` or `NAMESPACE_BLOCK`.""")
  def method: Iterator[nodes.Method] = wrappedCpg.graph._nodes(26).asInstanceOf[Iterator[nodes.Method]]

  /** Shorthand for method.name */
  def method(name: String): Iterator[nodes.Method] = method.name(name)

  /** This node represents a formal input parameter. The field `NAME` contains its name, while the field
    * `TYPE_FULL_NAME` contains the fully qualified type name.
    */
  @flatgraph.help.Doc(info = """This node represents a formal input parameter. The field `NAME` contains its
name, while the field `TYPE_FULL_NAME` contains the fully qualified type name.""")
  def methodParameterIn: Iterator[nodes.MethodParameterIn] =
    wrappedCpg.graph._nodes(27).asInstanceOf[Iterator[nodes.MethodParameterIn]]

  /** Shorthand for methodParameterIn.name */
  def methodParameterIn(name: String): Iterator[nodes.MethodParameterIn] = methodParameterIn.name(name)

  /** This node represents a formal output parameter. Corresponding output parameters for input parameters MUST NOT be
    * created by the frontend as they are automatically created upon first loading the CPG.
    */
  @flatgraph.help.Doc(info = """This node represents a formal output parameter. Corresponding output parameters
for input parameters MUST NOT be created by the frontend as they are automatically
created upon first loading the CPG.""")
  def methodParameterOut: Iterator[nodes.MethodParameterOut] =
    wrappedCpg.graph._nodes(28).asInstanceOf[Iterator[nodes.MethodParameterOut]]

  /** This node represents a reference to a method/function/procedure as it appears when a method is passed as an
    * argument in a call. The `METHOD_FULL_NAME` field holds the fully-qualified name of the referenced method and the
    * `TYPE_FULL_NAME` holds its fully-qualified type name.
    */
  @flatgraph.help.Doc(info = """This node represents a reference to a method/function/procedure as it
appears when a method is passed as an argument in a call. The `METHOD_FULL_NAME`
field holds the fully-qualified name of the referenced method and the
`TYPE_FULL_NAME` holds its fully-qualified type name.""")
  def methodRef: Iterator[nodes.MethodRef] = wrappedCpg.graph._nodes(29).asInstanceOf[Iterator[nodes.MethodRef]]

  /** This node represents an (unnamed) formal method return parameter. It carries its fully qualified type name in
    * `TYPE_FULL_NAME`. The `CODE` field MAY be set freely, e.g., to the constant `RET`, however, subsequent layer
    * creators MUST NOT depend on this value.
    */
  @flatgraph.help.Doc(info = """This node represents an (unnamed) formal method return parameter. It carries its
fully qualified type name in `TYPE_FULL_NAME`. The `CODE` field MAY be set freely,
e.g., to the constant `RET`, however, subsequent layer creators MUST NOT depend
on this value.""")
  def methodReturn: Iterator[nodes.MethodReturn] =
    wrappedCpg.graph._nodes(30).asInstanceOf[Iterator[nodes.MethodReturn]]

  /** This field represents a (language-dependent) modifier such as `static`, `private` or `public`. Unlike most other
    * AST nodes, it is NOT an expression, that is, it cannot be evaluated and cannot be passed as an argument in
    * function calls.
    */
  @flatgraph.help.Doc(info = """This field represents a (language-dependent) modifier such as `static`, `private`
or `public`. Unlike most other AST nodes, it is NOT an expression, that is, it
cannot be evaluated and cannot be passed as an argument in function calls.""")
  def modifier: Iterator[nodes.Modifier] = wrappedCpg.graph._nodes(31).asInstanceOf[Iterator[nodes.Modifier]]

  /** This node represents a namespace. Similar to FILE nodes, NAMESPACE nodes serve as indices that allow all
    * definitions inside a namespace to be obtained by following outgoing edges from a NAMESPACE node.
    *
    * NAMESPACE nodes MUST NOT be created by language frontends. Instead, they are generated from NAMESPACE_BLOCK nodes
    * automatically upon first loading of the CPG.
    */
  @flatgraph.help.Doc(info = """This node represents a namespace. Similar to FILE nodes, NAMESPACE nodes
serve as indices that allow all definitions inside a namespace to be
obtained by following outgoing edges from a NAMESPACE node.

NAMESPACE nodes MUST NOT be created by language frontends. Instead,
they are generated from NAMESPACE_BLOCK nodes automatically upon
first loading of the CPG.""")
  def namespace: Iterator[nodes.Namespace] = wrappedCpg.graph._nodes(32).asInstanceOf[Iterator[nodes.Namespace]]

  /** Shorthand for namespace.name */
  def namespace(name: String): Iterator[nodes.Namespace] = namespace.name(name)

  /** A reference to a namespace. We borrow the concept of a "namespace block" from C++, that is, a namespace block is a
    * block of code that has been placed in the same namespace by a programmer. This block may be introduced via a
    * `package` statement in Java or a `namespace{ }` statement in C++.
    *
    * The `FULL_NAME` field contains a unique identifier to represent the namespace block itself not just the namespace
    * it references. So in addition to the namespace name it can be useful to use the containing file name to derive a
    * unique identifier.
    *
    * The `NAME` field contains the namespace name in a human-readable format. The name should be given in dot-separated
    * form where a dot indicates that the right hand side is a sub namespace of the left hand side, e.g., `foo.bar`
    * denotes the namespace `bar` contained in the namespace `foo`.
    */
  @flatgraph.help.Doc(info = """A reference to a namespace.
We borrow the concept of a "namespace block" from C++, that is, a namespace block
is a block of code that has been placed in the same namespace by a programmer.
This block may be introduced via a `package` statement in Java or
a `namespace{ }` statement in C++.

The `FULL_NAME` field contains a unique identifier to represent the namespace block
itself not just the namespace it references. So in addition to the namespace name
it can be useful to use the containing file name to derive a unique identifier.

The `NAME` field contains the namespace name in a human-readable format.
The name should be given in dot-separated form where a dot indicates
that the right hand side is a sub namespace of the left hand side, e.g.,
`foo.bar` denotes the namespace `bar` contained in the namespace `foo`.""")
  def namespaceBlock: Iterator[nodes.NamespaceBlock] =
    wrappedCpg.graph._nodes(33).asInstanceOf[Iterator[nodes.NamespaceBlock]]

  /** Shorthand for namespaceBlock.name */
  def namespaceBlock(name: String): Iterator[nodes.NamespaceBlock] = namespaceBlock.name(name)

  /** This node represents a return instruction, e.g., `return x`. Note that it does NOT represent a formal return
    * parameter as formal return parameters are represented via `METHOD_RETURN` nodes.
    */
  @flatgraph.help.Doc(info = """This node represents a return instruction, e.g., `return x`. Note that it does
NOT represent a formal return parameter as formal return parameters are
represented via `METHOD_RETURN` nodes.""")
  def ret: Iterator[nodes.Return] = wrappedCpg.graph._nodes(34).asInstanceOf[Iterator[nodes.Return]]

  /** Shorthand for ret.code */
  def ret(code: String): Iterator[nodes.Return] = ret.code(code)

  /** This node represents a tag. */
  @flatgraph.help.Doc(info = """This node represents a tag.""")
  def tag: Iterator[nodes.Tag] = wrappedCpg.graph._nodes(35).asInstanceOf[Iterator[nodes.Tag]]

  /** Shorthand for tag.name */
  def tag(name: String): Iterator[nodes.Tag] = tag.name(name)

  /** This node contains an arbitrary node and an associated tag node. */
  @flatgraph.help.Doc(info = """This node contains an arbitrary node and an associated tag node.""")
  def tagNodePair: Iterator[nodes.TagNodePair] = wrappedCpg.graph._nodes(36).asInstanceOf[Iterator[nodes.TagNodePair]]

  /** This node represents a DOM node used in template languages, e.g., JSX/TSX */
  @flatgraph.help.Doc(info = """This node represents a DOM node used in template languages, e.g., JSX/TSX""")
  def templateDom: Iterator[nodes.TemplateDom] = wrappedCpg.graph._nodes(37).asInstanceOf[Iterator[nodes.TemplateDom]]

  /** This node represents a type instance, that is, a concrete instantiation of a type declaration.
    */
  @flatgraph.help.Doc(info = """This node represents a type instance, that is, a concrete instantiation
of a type declaration.""")
  def typ: Iterator[nodes.Type] = wrappedCpg.graph._nodes(38).asInstanceOf[Iterator[nodes.Type]]

  /** Shorthand for typ.name */
  def typ(name: String): Iterator[nodes.Type] = typ.name(name)

  /** An (actual) type argument as used to instantiate a parametrized type, in the same way an (actual) arguments
    * provides concrete values for a parameter at method call sites. As it true for arguments, the method is not
    * expected to interpret the type argument. It MUST however store its code in the `CODE` field.
    */
  @flatgraph.help.Doc(info = """An (actual) type argument as used to instantiate a parametrized type, in the
same way an (actual) arguments provides concrete values for a parameter
at method call sites. As it true for arguments, the method is not expected
to  interpret the type argument. It MUST however store its code in the
`CODE` field.""")
  def typeArgument: Iterator[nodes.TypeArgument] =
    wrappedCpg.graph._nodes(39).asInstanceOf[Iterator[nodes.TypeArgument]]

  /** This node represents a type declaration as for example given by a class-, struct-, or union declaration. In
    * contrast to a `TYPE` node, this node does not represent a concrete instantiation of a type, e.g., for the
    * parametrized type `List[T]`, it represents `List[T]`, but not `List[Integer]` where `Integer` is a concrete type.
    *
    * The language frontend MUST create type declarations for all types declared in the source program and MAY provide
    * type declarations for types that are not declared but referenced by the source program. If a declaration is
    * present in the source program, the field `IS_EXTERNAL` is set to `false`. Otherwise, it is set to `true`.
    *
    * The `FULL_NAME` field specifies the type's fully-qualified name, including information about the namespace it is
    * contained in if applicable, the name field is the type's short name. Line and column number information is
    * specified in the optional fields `LINE_NUMBER`, `COLUMN_NUMBER`, `LINE_NUMBER_END`, and `COLUMN_NUMBER_END` and
    * the name of the source file is specified in `FILENAME`.
    *
    * Base types can be specified via the `INHERITS_FROM_TYPE_FULL_NAME` list, where each entry contains the
    * fully-qualified name of a base type. If the type is known to be an alias of another type (as for example
    * introduced via the C `typedef` statement), the name of the alias is stored in `ALIAS_TYPE_FULL_NAME`.
    *
    * The optional `OFFSET` and `OFFSET_END` specify the start and exclusive end position of the code belonging to a
    * `TYPE_DECL` within the corresponding `FILE` nodes `CONTENT` property.
    *
    * Finally, the fully qualified name of the program constructs that the type declaration is immediately contained in
    * is stored in the `AST_PARENT_FULL_NAME` field and its type is indicated in the `AST_PARENT_TYPE` field to be one
    * of `METHOD`, `TYPE_DECL` or `NAMESPACE_BLOCK`.
    */
  @flatgraph.help.Doc(info = """This node represents a type declaration as for example given by a class-, struct-,
or union declaration. In contrast to a `TYPE` node, this node does not represent a
concrete instantiation of a type, e.g., for the parametrized type `List[T]`, it represents
`List[T]`, but not `List[Integer]` where `Integer` is a concrete type.

The language frontend MUST create type declarations for all types declared in the
source program and MAY provide type declarations for types that are not declared
but referenced by the source program. If a declaration is present in the source
program, the field `IS_EXTERNAL` is set to `false`. Otherwise, it is set to `true`.

The `FULL_NAME` field specifies the type's fully-qualified name, including
information about the namespace it is contained in if applicable, the name field
is the type's short name. Line and column number information is specified in the
optional fields `LINE_NUMBER`, `COLUMN_NUMBER`, `LINE_NUMBER_END`, and
`COLUMN_NUMBER_END` and the name of the source file is specified in `FILENAME`.

Base types can be specified via the `INHERITS_FROM_TYPE_FULL_NAME` list, where
each entry contains the fully-qualified name of a base type. If the type is
known to be an alias of another type (as for example introduced via the C
`typedef` statement), the name of the alias is stored in `ALIAS_TYPE_FULL_NAME`.

The optional `OFFSET` and `OFFSET_END` specify the start
and exclusive end position of the code belonging to a `TYPE_DECL` within the corresponding
`FILE` nodes `CONTENT` property.

Finally, the fully qualified name of the program constructs that the type declaration
is immediately contained in is stored in the `AST_PARENT_FULL_NAME` field
and its type is indicated in the `AST_PARENT_TYPE` field to be one of
`METHOD`, `TYPE_DECL` or `NAMESPACE_BLOCK`.""")
  def typeDecl: Iterator[nodes.TypeDecl] = wrappedCpg.graph._nodes(40).asInstanceOf[Iterator[nodes.TypeDecl]]

  /** Shorthand for typeDecl.name */
  def typeDecl(name: String): Iterator[nodes.TypeDecl] = typeDecl.name(name)

  /** This node represents a formal type parameter, that is, the type parameter as given in a type-parametrized method
    * or type declaration. Examples for languages that support type parameters are Java (via Generics) and C++ (via
    * templates). Apart from the standard fields of AST nodes, the type parameter carries only a `NAME` field that holds
    * the parameters name.
    */
  @flatgraph.help.Doc(info = """This node represents a formal type parameter, that is, the type parameter
as given in a type-parametrized method or type declaration. Examples for
languages that support type parameters are Java (via Generics) and C++
(via templates). Apart from the standard fields of AST nodes, the type
parameter carries only a `NAME` field that holds the parameters name.""")
  def typeParameter: Iterator[nodes.TypeParameter] =
    wrappedCpg.graph._nodes(41).asInstanceOf[Iterator[nodes.TypeParameter]]

  /** Reference to a type/class */
  @flatgraph.help.Doc(info = """Reference to a type/class""")
  def typeRef: Iterator[nodes.TypeRef] = wrappedCpg.graph._nodes(42).asInstanceOf[Iterator[nodes.TypeRef]]

  /** Any AST node that the frontend would like to include in the AST but for which no suitable AST node is specified in
    * the CPG specification may be included using a node of type `UNKNOWN`.
    */
  @flatgraph.help.Doc(info = """Any AST node that the frontend would like to include in the AST but for
which no suitable AST node is specified in the CPG specification may be
included using a node of type `UNKNOWN`.""")
  def unknown: Iterator[nodes.Unknown] = wrappedCpg.graph._nodes(43).asInstanceOf[Iterator[nodes.Unknown]]

  /** This is the base type for all nodes of the abstract syntax tree (AST). An AST node has a `CODE` and an `ORDER`
    * field. The `CODE` field contains the code (verbatim) represented by the AST node. The `ORDER` field contains the
    * nodes position among its siblings, encoded as an integer where the left most sibling has the position `0`.
    *
    * AST nodes contain optional `LINE_NUMBER` and `COLUMN_NUMBER` fields. For source-based frontends, these fields
    * contain the start line number and start column number of the code represented by the node. For machine-code-based
    * and bytecode-based frontends, `LINE_NUMBER` contains the address at which the code starts while `COLUMN_NUMBER` is
    * undefined. subtypes: ANNOTATION, ANNOTATION_LITERAL, ANNOTATION_PARAMETER, ANNOTATION_PARAMETER_ASSIGN,
    * ARRAY_INITIALIZER, BLOCK, CALL, COMMENT, CONTROL_STRUCTURE, FIELD_IDENTIFIER, FILE, IDENTIFIER, IMPORT,
    * JUMP_LABEL, JUMP_TARGET, LITERAL, LOCAL, MEMBER, METHOD, METHOD_PARAMETER_IN, METHOD_PARAMETER_OUT, METHOD_REF,
    * METHOD_RETURN, MODIFIER, NAMESPACE, NAMESPACE_BLOCK, RETURN, TEMPLATE_DOM, TYPE_ARGUMENT, TYPE_DECL,
    * TYPE_PARAMETER, TYPE_REF, UNKNOWN
    */
  @flatgraph.help.Doc(
    info = """This is the base type for all nodes of the abstract syntax tree (AST). An AST
node has a `CODE` and an `ORDER` field. The `CODE` field contains the
code (verbatim) represented by the AST node. The `ORDER` field contains the
nodes position among its siblings, encoded as an integer where the left most
sibling has the position `0`.

AST nodes contain optional `LINE_NUMBER` and `COLUMN_NUMBER` fields. For
source-based frontends, these fields contain the start line number and
start column number of the code represented by the node.
For machine-code-based and bytecode-based frontends, `LINE_NUMBER` contains
the address at which the code starts while `COLUMN_NUMBER` is undefined.""",
    longInfo =
      """subtypes: ANNOTATION, ANNOTATION_LITERAL, ANNOTATION_PARAMETER, ANNOTATION_PARAMETER_ASSIGN, ARRAY_INITIALIZER, BLOCK, CALL, COMMENT, CONTROL_STRUCTURE, FIELD_IDENTIFIER, FILE, IDENTIFIER, IMPORT, JUMP_LABEL, JUMP_TARGET, LITERAL, LOCAL, MEMBER, METHOD, METHOD_PARAMETER_IN, METHOD_PARAMETER_OUT, METHOD_REF, METHOD_RETURN, MODIFIER, NAMESPACE, NAMESPACE_BLOCK, RETURN, TEMPLATE_DOM, TYPE_ARGUMENT, TYPE_DECL, TYPE_PARAMETER, TYPE_REF, UNKNOWN"""
  )
  def astNode: Iterator[nodes.AstNode] = Iterator(
    this.annotation,
    this.annotationLiteral,
    this.annotationParameter,
    this.annotationParameterAssign,
    this.arrayInitializer,
    this.block,
    this.call,
    this.comment,
    this.controlStructure,
    this.fieldIdentifier,
    this.file,
    this.identifier,
    this.imports,
    this.jumpLabel,
    this.jumpTarget,
    this.literal,
    this.local,
    this.member,
    this.method,
    this.methodParameterIn,
    this.methodParameterOut,
    this.methodRef,
    this.methodReturn,
    this.modifier,
    this.namespace,
    this.namespaceBlock,
    this.ret,
    this.templateDom,
    this.typeArgument,
    this.typeDecl,
    this.typeParameter,
    this.typeRef,
    this.unknown
  ).flatten

  /** This is the base class of `CALL` that language implementers may safely ignore. subtypes: CALL
    */
  @flatgraph.help.Doc(
    info = """This is the base class of `CALL` that language implementers may safely ignore.""",
    longInfo = """subtypes: CALL"""
  )
  def callRepr: Iterator[nodes.CallRepr] = Iterator(this.call).flatten

  /** This is the base class for all control flow nodes. It is itself a child class of `AST_NODE`, that is, all control
    * flow graph nodes are also syntax tree nodes in the CPG specification. subtypes: ANNOTATION, ANNOTATION_LITERAL,
    * ARRAY_INITIALIZER, BLOCK, CALL, CONTROL_STRUCTURE, FIELD_IDENTIFIER, IDENTIFIER, JUMP_TARGET, LITERAL, METHOD,
    * METHOD_PARAMETER_IN, METHOD_PARAMETER_OUT, METHOD_REF, METHOD_RETURN, RETURN, TEMPLATE_DOM, TYPE_REF, UNKNOWN
    */
  @flatgraph.help.Doc(
    info = """This is the base class for all control flow nodes. It is itself
a child class of `AST_NODE`, that is, all control flow graph nodes
are also syntax tree nodes in the CPG specification.""",
    longInfo =
      """subtypes: ANNOTATION, ANNOTATION_LITERAL, ARRAY_INITIALIZER, BLOCK, CALL, CONTROL_STRUCTURE, FIELD_IDENTIFIER, IDENTIFIER, JUMP_TARGET, LITERAL, METHOD, METHOD_PARAMETER_IN, METHOD_PARAMETER_OUT, METHOD_REF, METHOD_RETURN, RETURN, TEMPLATE_DOM, TYPE_REF, UNKNOWN"""
  )
  def cfgNode: Iterator[nodes.CfgNode] = Iterator(
    this.annotation,
    this.annotationLiteral,
    this.arrayInitializer,
    this.block,
    this.call,
    this.controlStructure,
    this.fieldIdentifier,
    this.identifier,
    this.jumpTarget,
    this.literal,
    this.method,
    this.methodParameterIn,
    this.methodParameterOut,
    this.methodRef,
    this.methodReturn,
    this.ret,
    this.templateDom,
    this.typeRef,
    this.unknown
  ).flatten

  /** This is the base node class for all declarations. subtypes: LOCAL, MEMBER, METHOD, METHOD_PARAMETER_IN,
    * METHOD_PARAMETER_OUT
    */
  @flatgraph.help.Doc(
    info = """This is the base node class for all declarations.""",
    longInfo = """subtypes: LOCAL, MEMBER, METHOD, METHOD_PARAMETER_IN, METHOD_PARAMETER_OUT"""
  )
  def declaration: Iterator[nodes.Declaration] =
    Iterator(this.local, this.member, this.method, this.methodParameterIn, this.methodParameterOut).flatten

  /** `EXPRESSION` is the base class for all nodes that represent code pieces that can be evaluated.
    *
    * Expression may be arguments in method calls. For method calls that do not involved named parameters, the
    * `ARGUMENT_INDEX` field indicates at which position in the argument list the expression occurs, e.g., an
    * `ARGUMENT_INDEX` of 1 indicates that the expression is the first argument in a method call. For calls that employ
    * named parameters, `ARGUMENT_INDEX` is set to -1 and the `ARGUMENT_NAME` fields holds the name of the parameter.
    * subtypes: ANNOTATION, ANNOTATION_LITERAL, ARRAY_INITIALIZER, BLOCK, CALL, CONTROL_STRUCTURE, FIELD_IDENTIFIER,
    * IDENTIFIER, LITERAL, METHOD_REF, RETURN, TEMPLATE_DOM, TYPE_REF, UNKNOWN
    */
  @flatgraph.help.Doc(
    info = """`EXPRESSION` is the base class for all nodes that represent code pieces
that can be evaluated.

 Expression may be arguments in method calls. For method calls that do
 not involved named parameters, the `ARGUMENT_INDEX` field indicates at
 which position in the argument list the expression occurs, e.g., an
 `ARGUMENT_INDEX` of 1 indicates that the expression is the first argument
 in a method call. For calls that employ named parameters, `ARGUMENT_INDEX`
 is set to -1 and the `ARGUMENT_NAME` fields holds the name of the parameter.""",
    longInfo =
      """subtypes: ANNOTATION, ANNOTATION_LITERAL, ARRAY_INITIALIZER, BLOCK, CALL, CONTROL_STRUCTURE, FIELD_IDENTIFIER, IDENTIFIER, LITERAL, METHOD_REF, RETURN, TEMPLATE_DOM, TYPE_REF, UNKNOWN"""
  )
  def expression: Iterator[nodes.Expression] = Iterator(
    this.annotation,
    this.annotationLiteral,
    this.arrayInitializer,
    this.block,
    this.call,
    this.controlStructure,
    this.fieldIdentifier,
    this.identifier,
    this.literal,
    this.methodRef,
    this.ret,
    this.templateDom,
    this.typeRef,
    this.unknown
  ).flatten

}
