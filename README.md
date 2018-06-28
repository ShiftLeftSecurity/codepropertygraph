[![Build Status](https://buildstatus.dev.sltr.io/buildStatus/icon?job=CI-MASTER-CodePropertyGraph)](https://ci.shiftleftsecurity.com/job/CI-MASTER-CodePropertyGraph/)

# Code Property Graph - Specification and Tooling

Code property graphs are an extensible representation of program
code designed for language agnostic, incremental, and distributed code
analysis. This repository hosts its base specification, along with a
build process that generates corresponding data structure definitions
for different programming languages.

# Building the code

The build process has been tested on Linux, but it should be possible
to build the code on OS X and BSD variants as well. The following
components need to be installed.

* Python3
* Java runtime 8
* Scala build tool (Sbt)

All remaining dependencies are automatically fetched by the build
process. To build and install into your local maven cache, issue the
command `sbt publishM2`. This will install the following artifacts:
* `codepropertygraph-VERSION.jar`: Java and Scala classes to be used
  in combination with the ShiftLeft Tinkergraph [3].

* `codepropertygraph-protos-VERSION.jar`: Java bindings for google
  protocol buffer definitions

# Creating protocol buffer bindings for different languages

`codepropertygraph-VERSION.jar` contains a file `cpg.proto` that you
can use to generate your own language-specific bindings. E.g., to
create C++ and Python bindings:
```
sbt package
cd codepropertygraph/target
unzip codepropertygraph-*.jar cpg.proto
mkdir cpp python
protoc --cpp_out=cpp --python_out=python cpg.proto
```

# Base schema of the Code Property Graph

The original paper on code property graphs [2] is primarily
concerned with exploring the capabilities of code property graphs, but
does not provide a strict schema for these graphs. This specification
provides the missing schema, thereby creating a concrete exchange
format.

The code property graph is a directed, edge-labeled, attributed
multigraph, or property graph for short (see [1]). A property graph
is the generic data structure underlying many contemporary graph
databases (e.g., Tinkergraph, Neo4J, Janusgraph). As a result, data
representations based on property graphs are immediately processible
with graph database technology.

Property graphs alone are comparable in generality to hash tables and
linked lists. To tailor them towards storing, transmitting, and
analyzing code, the main challenge is to specify a suitable graph
schema. In particular, a schema describes the valid node and edge
types, as well as node and edge keys, along with their domains.
Finally, it provides constraints on the types of edges that may
connect nodes, depending on their type.

In the following, we describe the base schema of the code property
graph. The base schema provides the minimum contraints all valid code
property graphs must conform to. The base specification is concerned
with three aspects of the program, namely, *program structure*, *type
declarations*, and *method declarations*. In the following sections,
we first give a brief overview of the graph's node and edge types, and
proceed to describe each of the three aspects in greater detail.

> ***Note about property graphs:*** We deviate from the original
definition by (a) referring to edge labels as *edge types*, and (b)
requiring each node to have a mandatory node type. The node type can
be represented by a node attribute. Compatibility with the property
graph model as used by graph databases is therefore not sacrificed.

> ***Note about terminology:*** the term *declaration* is employed by us
as in the specification of the Java language, where a declaration
comprises a formal signature, along with defining content such as a
method body for methods or a literal value in the definition of a
variable. This stands in contrast to the use of the term *declaration*
in C/C++, where a declaration is only a formal signature.

## Overview

The base schema of the code property graph is specified in the JSON
file
[base.json](codepropertygraph/src/main/resources/schemas/base.json).
The file contains a JSON object with the following members.

* `nodeKeys/edgeKeys` is a list of all valid node/edge attributes.
  Each list element is an object specifying the attribute's id, name,
  its type, and a comment.

* `nodeTypes/edgeTypes` is a list of all node/edge types, where each
  node/edge type is given by an object that specifies an id, name,
  keys, comment, and, for node types, valid outgoing edge types.

There is a total of 20 node types in 5 categories:

Category          | Names
------------------| -------------------------------------------------------------------------------------------------
Program structure | FILE, NAMESPACE_BLOCK
Type declarations | TYPE_DECL, TYPE_PARAMETER, MEMBER, TYPE, TYPE_ARGUMENT
Method header     | METHOD, METHOD_PARAMETER_IN, METHOD_PARAMETER_OUT, METHOD_RETURN, LOCAL, BLOCK, MODIFIER
Method body       | LITERAL, IDENTIFIER, CALL, RETURN, METHOD_INST
Meta data         | META_DATA


There is a total of 8 edge types:

Name | Usage
-----|----------------------------------------------------------------
AST  | Syntax tree edge - used to encode structure
CFG  | Control flow edge - execution order and conditions
REF  | Reference edge - references to type/method/identifier declarations
EVAL_TYPE | Type edge - used to attach known types to expressions
CALL | Method invocation edge - indicates caller/callee relationship
VTABLE | Virtual method table edge - employed to represent vtables
INHERITS_FROM | Type inheritance edge - used to model OOP inheritance
BINDS_TO | Binding edge - used to provide type parameters

There is a total of 16 node keys in 3 categories:

Category       | Names
---------------| ---------------------------------------------------------------------------------------------------------------------------------
Declarations   | NAME, FULL_NAME, IS_EXTERNAL
Method header  | SIGNATURE, MODIFIER_TYPE |
Method body    | PARSER_TYPE_NAME, ORDER, CODE, DISPATCH_TYPE, EVALUATION_STRATEGY,LINE_NUMBER, LINE_NUMBER_END, COLUMN_NUMBER,COLUMN_NUMBER_END
Meta data      | LANGUAGE, VERSION

There are zero edge keys in the base specification.

> We deviate from the JSON standard by allowing inline comments. Any
line for which the first two non-whitespace characters are equal to
`/`, are treated as comments and need to be stripped prior to passing
the definitions to standard JSON parsers.

## Program structure

Node types: FILE, NAMESPACE_BLOCK

Program structure is concerned with the organization of programs into
files and namespaces/packages. A program is composed of zero or more
files (type FILE), each of which contains one or more namespace blocks
(type NAMESPACE_BLOCK). Namespace blocks contain type and method
declarations (type TYPE_DECL and METHOD). AST edges must exist
from files to namespace blocks and from namespace blocks to the type
and method declarations they contain. Figure XXX shows how a Java
class definition is translated into a corresponding code property
graph.

***TODO Figure***

The concept of namespace blocks correspond to the corresponding
concepts in the C++ programming language, where namespace blocks are
used to place declarations into a namespace. Other languages, e.g.,
Java or Python, do not provide namespace blocks, however, they allow
package declarations at the start of source files that serve the
purpose of placing all remaining declarations of the source file into
a namespace. We translate package declarations into corresponding
namespace blocks for these languages.

## Type declarations

Node types: TYPE_DECL, TYPE_PARAMETER, MEMBER, TYPE, TYPE_ARGUMENT

We express language constructs that declare types via *type
declarations*. Examples are classes, interfaces, structures, and
enumerations. A type declaration consists of a name, optional lists
for type parameters, member variables, and methods. Finally,
inheritance relations with other types can be encoded.

In the code property graph, each type declaration is represented by a
designated type-declaration node (type TYPE_DECL), with at least a
full-name attribute. Member variables (type MEMBER), method declarations
(type METHOD), and type parameters (type TYPE_PARAMETER) are
connected to the type declaration via AST edges, originating at the
type declaration. Inheritance relations are expressed via
`INHERITS_FROM` edges to zero or more other type declarations (type
TYPE_DECL), which indicate that the source type declaration inherits
from the destination declaration.

Usage of a type, e.g., in the declaration of a variable, is indicated
by a type node (type TYPE). The type node is connected to the
corresponding type declaration via a reference edge (type REF), and to
type arguments via AST edges (type AST). Finally, type-argument nodes
are connected to type parameters via binding edges (type BINDS_TO).

## Method declarations

A method declaration consists of a method header and a method body,
where the declaration is a graph representation of the method's input
and output parameters, and the method body contains the
instructions/statements of the method.

### Method header

Node types: METHOD, METHOD_PARAMETER_IN, METHOD_PARAMETER_OUT,
METHOD_RETURN, LOCAL, BLOCK, MODIFIER

The term *method* is used in object-oriented programming languages to
refer to a procedure that is associated with a class. We use the term
in a broader sense to refer to any named block of code. This code may
or may not be defined to be associated with a type. The method
consists of a method header and a method body. The method header is
given by a name, a formal return parameter, and finally, a list of
formal input parameters and corresponding output parameters. The
method body is simply a block of statements.

***TODO: Figure XXX - illustration of method header ***

In the code property graph, we represent each method by a designated
method node (type METHOD) that contains the method name in
particular. Methods are connected to their method input parameters
(type METHOD_PARAMETER_IN), output parameters (type:
METHOD_PARAMETER_OUT), return parameter (type METHOD_RETURN),
modifiers (type MODIFIER), and locals (type LOCAL) via AST edges.
Finally, the method node is connected to a block node (type BLOCK),
which represents the method body. Figure XXX shows a sample method along
with a representation of its method header in the code property graph.

<!-- Input parameters can be connected to output parameters or the return -->
<!-- parameter via DFG edges to indicate data flow. These edges serve to -->
<!-- (a) encode data flow relations that cannot be derived from the code -->
<!-- automatically due to calls into external libraries, and (b) as summary -->
<!-- edges that store results of data flow analysis for subsequent -->
<!-- queries. -->

### Method body

Node types: LITERAL, IDENTIFIER, CALL, RETURN, METHOD_INST

Method bodies contain the method implementation, given by the
operations the method carries out. We represent method bodies as
control flow graphs over method invocations, a representation we
arrive at to provide a common ground for the instruction, statement,
and expression concepts used across machine-level and high-level
programming languages. The core elements of the method body
representation are thus (a) method invocations ("calls"), and
control-flow edges.

In the code property graph, we represent a method invocation via a
designated call node (type CALL). Arguments are either identifiers
(type IDENTIFIER), literals (type LITERAL), or other calls (type
CALL). Calls are connected to their arguments via outgoing AST edges,
and to method instance nodes (type METHOD_INST) via call edges (type
CALL). Method-instance nodes represent concrete instantiations of
method declarations, that is, method declarations along with type
parameters. Method nodes are connected to type arguments (type
TYPE_ARGUMENT) via AST edges, and to their corresponding method
declaration via references edges (type: REF). Figure XXX shows a
sample call, along with its corresponding representation in the code
property graph.

***TODO - Figure showing call site***

We proceed to create return nodes (type RETURN) for each location in
the method body where control is returned to the caller. Unconditional
control flow edges are created from preceding calls to return nodes.
We proceed to connect all remaining nodes via control flow edges (type
CFG) according to execution order and constraints. The method node is
treated as the entry node of the control flow graph. Finally, we
create a designated block node (type BLOCK) for the method body, and
create outgoing AST edges to all expressions that correspond to
statements.

### Background on the method body representation

In machine-level languages, procedure boddies are given by
instructions, connected by control flow edges to form a control flow
graph. Each instruction represents an operation carried out by the
machine, which can modify the program state. In contrast, higher-level
languages (C and above) typically drop the instruction concept in
favor of statements. As for instructions, statements can modify the
program state. They differ from instructions in that they can consist
of multiple expressions. Expressions are anonymous blocks of code that
receive input and produce an output value upon evaluation. Inputs to
an expression can be literals and identifiers, but they may also be
other expressions. In fact, the semantics of statements can be fully
encoded via expression trees, and control flow edges attached to
the roots of these trees, to represent the statement's control flow
semantics. 

The ability of a statement to enclose several expressions allows for
concise program formulation, however, it introduces a challenge for
program analysis: while it is possible to create a control flow graph
by introducing control flow edges between statements, this graph does
not encode the intra-statement control flow. Fortunately, the tree,
along with disambiguation rules of the programming language encode the
evaluation order of expressions within a statement, allowing us to
create explicitly represent their evaluation order via control flow
edges.

Expressions consist of method evaluations and applications of
operators built into the language. By expressing operators as methods,
and allowing methods to receive the return values of other methods as
input, all expressions can be represented as method invocations. We
thus arrive at a program representation for the method body, which
consists of method invocations connected via control flow edges.

## Meta data block

We include a meta-data block (type META_DATA) in code property graphs
with two fields: a language field (key LANGUAGE) to indicate the
programming language the graph was generated from, and a version field
(key VERSION) holding the specification version. Both fields are
free-text strings.


<!-- # Extending the specification -->

<!-- The build process creates the final graph schema by merging all JSON -->
<!-- files in the directory `src/main/resources/schemas/`. This makes it -->
<!-- possible for developers of program analysis tools to extend the graph -->
<!-- definition by providing additional JSON files. On the graph side, it -->
<!-- also provides the basis for augmenting the graph with additional -->
<!-- layers later in the processing pipeline for code analysis systems. -->



# References

[1]  Rodriguez and Neubauer - The Graph Traversal Pattern:
   https://pdfs.semanticscholar.org/ae6d/dcba8c848dd0a30a30c5a895cbb491c9e445.pdf

[2] Yamaguchi et al. - Modeling and Discovering Vulnerabilities with Code Property Graphs
    https://www.sec.cs.tu-bs.de/pubs/2014-ieeesp.pdf

[3] The ShiftLeft Tinkergraph
    https://github.com/ShiftLeftSecurity/tinkergraph-gremlin
