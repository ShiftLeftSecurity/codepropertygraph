[![Build Status](https://secure.travis-ci.org/ShiftLeftSecurity/codepropertygraph.png?branch=master)](http://travis-ci.org/ShiftLeftSecurity/codepropertygraph)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.shiftleft/codepropertygraph/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.shiftleft/codepropertygraph)

# Code Property Graph - Specification and Tooling

**Note: for first-time users, we recommend building "joern" at https://github.com/ShiftLeftSecurity/joern/ instead. It combines this repo with a C/C++ language frontend to construct a complete code analysis platform.**

A Code Property Graph (CPG) is an extensible and language-agnostic
representation of program code designed for incremental and
distributed code analysis. This repository hosts the base
specification together with a build process that generates data
structure definitions for accessing the graph with different
programming languages.


We are publishing the Code Property Graph specification as a
suggestion for an open standard for the exchange of code in
intermediate representations along with analysis results. With this
goal in mind, the specification consists of a minimal base schema that
can be augmented via extension schemas to enable storage of
application-specific data.

# Building the code

*Note*: for first-time users, we recommend building "joern" at https://github.com/ShiftLeftSecurity/joern/ instead. It contains a code property graph generator for C/C++, a component for querying the code property graph, as well as a few helpful examples to get started.

The build process has been verified on Linux and it should be possible
to build on OS X and BSD systems as well. The build process requires
the following prerequisites:

* Python3
  - Link: https://www.python.org/downloads/
* Python2 and python2-requests
* Java runtime 8
  - Link: http://openjdk.java.net/install/
* Scala build tool (sbt)
  - Link: https://www.scala-sbt.org/
* Git-lfs
  - Link: https://git-lfs.github.com/
* Protoc
  - Link: https://github.com/protocolbuffers/protobuf/releases

Some binary files required for testing are managed through `git-lfs`. If you haven't cloned this repository yet, simply run `git lfs install`.
If you have cloned it already, additionally run `git lfs pull` (from within the repository).

Additional build-time dependencies are automatically downloaded as
part of the build process. To build and install into your local Maven
cache, issue the command `sbt publishM2`.

This command will install the following artifacts:

* _codepropertygraph-VERSION.jar_: Java and Scala classes to be used in combination with the ShiftLeft Tinkergraph [3].

* _codepropertygraph-protos-VERSION.jar_: Java bindings for Google's Protocol Buffer definitions

# Creating Protocol Buffer bindings for different languages

The _codepropertygraph-VERSION.jar_ artifact contains a Protocol Buffer definition file _cpg.proto_ that you
can use to generate your own language-specific bindings. For instance, to create C++ and Python bindings, issue the following series of commands:

```
sbt package
cd codepropertygraph/target
unzip codepropertygraph-*.jar cpg.proto
mkdir cpp python
protoc --cpp_out=cpp --python_out=python cpg.proto
```

# Base schema for the Code Property Graph

You can find the code property graph specification at:

https://docs.shiftleft.io/shiftleft/using-shiftleft-ocular/getting-started/cpg-deep-dive


<!-- # Extending the specification -->

<!-- The build process creates the final graph schema by merging all JSON -->
<!-- files in the directory `src/main/resources/schemas/`. This makes it -->
<!-- possible for developers of program analysis tools to extend the graph -->
<!-- definition by providing additional JSON files. On the graph side, it -->
<!-- also provides the basis for augmenting the graph with additional -->
<!-- layers later in the processing pipeline for code analysis systems. -->


# Loading a codepropertygraph

Here's how you can load a cpg into ShiftLeft Tinkergraph [3] in the sbt console - the next section will list some queries you can interactively run from there.

There are some sample cpgs in this repository in the `resources/cpgs` directory.
You can run `./regenerate-test-cpgs.sh` to update them, but this requires the proprietary java2cpg installed locally.

### [Tinkergraph (in memory reference db)](http://tinkerpop.apache.org/docs/current/reference/#tinkergraph-gremlin)
```
sbt semanticcpg/console
```
```scala
val cpg = io.shiftleft.cpgloading.CpgLoader.load("cpg.bin.zip")
```

# Querying the cpg

Once you've loaded a cpg you can run queries, which are provided by the `query-primitives` subproject. Note that if you're in the sbt shell you can play with it interactively: `TAB` completion is your friend. Otherwise your IDE will assist.

Here are some simple traversals to get all the base nodes. Running all of these without errors is a good test to ensure that your cpg is valid:

```scala
cpg.literal.toList
cpg.file.toList
cpg.namespace.toList
cpg.types.toList
cpg.methodReturn.toList
cpg.parameter.toList
cpg.member.toList
cpg.call.toList
cpg.local.toList
cpg.identifier.toList
cpg.argument.toList
cpg.typeDecl.toList
cpg.method.toList
cpg.methodInstance.toList
```

From here you can traverse through the cpg. The query-primitives DSL ensures that only valid steps are available - anything else will result in a compile error:

```scala
cpg.method.name("getAccountList").parameter.toList
/* List(
 *   MethodParameterIn(Some(v[7054781587948444580]),this,0,this,BY_SHARING,io.shiftleft.controller.AccountController,Some(28),None,None,None),
 *   MethodParameterIn(Some(v[7054781587948444584]),request,2,request,BY_SHARING,javax.servlet.http.HttpServletRequest,Some(28),None,None,None),
 *   MethodParameterIn(Some(v[7054781587948444582]),response,1,response,BY_SHARING,javax.servlet.http.HttpServletResponse,Some(28),None,None,None)
 *   )
 **/

cpg.method.name("getAccountList").definingTypeDecl.toList.head
// TypeDecl(Some(v[464]),AccountController,io.shiftleft.controller.AccountController,false,List(java.lang.Object))
```

# References

[1]  Rodriguez and Neubauer - The Graph Traversal Pattern:
   https://pdfs.semanticscholar.org/ae6d/dcba8c848dd0a30a30c5a895cbb491c9e445.pdf

[2] Yamaguchi et al. - Modeling and Discovering Vulnerabilities with Code Property Graphs
    https://www.sec.cs.tu-bs.de/pubs/2014-ieeesp.pdf

[3] The ShiftLeft Tinkergraph
    https://github.com/ShiftLeftSecurity/tinkergraph-gremlin
