[![CI pipeline](https://github.com/ShiftLeftSecurity/codepropertygraph/actions/workflows/push.yml/badge.svg)](https://github.com/ShiftLeftSecurity/codepropertygraph/actions/workflows/push.yml)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.shiftleft/codepropertygraph_2.13/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.shiftleft/codepropertygraph_2.13)

# Code Property Graph - Specification and Tooling

You can find a clickable specification at:

https://cpg.joern.io

**Note: for first-time users, we recommend building "joern" at https://github.com/joernio/joern/ instead. It combines this repo with a C/C++ language frontend to construct a complete code analysis platform.**

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

# Usage as a dependency
`build.sbt`:
```
libraryDependencies += "io.shiftleft" %% "codepropertygraph" % "x.y.z"
```

# Building the code

The build process has been verified on Linux and it should be possible
to build on OS X and BSD systems as well. The build process requires
the following prerequisites:

* [Java runtime 8/11](http://openjdk.java.net/install/)
* [Scala build tool (SBT)](https://www.scala-sbt.org/)
* [Git-lfs](https://git-lfs.github.com/)
* [Protocol Buffer](https://github.com/protocolbuffers/protobuf/releases)

Some binary files required for testing are managed through `git-lfs`. If you haven't cloned this repository yet, simply run `git lfs install`.
If you have cloned it already, additionally run `git lfs pull` (from within the repository).

Additional build-time dependencies are automatically downloaded as
part of the build process. To build and install into your local Maven
cache, issue the command `sbt clean test publishM2`.

# Code style

Code style is automatically verified by external tools:

* [scalafmt](https://github.com/scalameta/scalafmt)
* [scalafix](https://github.com/scalacenter/scalafix)

If your PR build fails code formatting check, simply run `sbt format` and submit the change along with the rest of the code. The commands runs necessary formatting in the right order.

# Creating Protocol Buffer bindings for different languages

The _codepropertygraph-VERSION.jar_ artifact contains a Protocol Buffer definition file _cpg.proto_ that you
can use to generate your own language-specific bindings. For instance, to create C++ and Python bindings, issue the following series of commands:

```
sbt package
cd codepropertygraph/target
mkdir cpp python
protoc --cpp_out=cpp --python_out=python cpg.proto
```

# Loading a codepropertygraph

Here's how you can load a cpg into ShiftLeft Tinkergraph [3] in the sbt console - the next section will list some queries you can interactively run from there.

There are some sample cpgs in this repository in the `resources/testcode/cpgs` directory.
You can run `./regenerate-test-cpgs.sh` to update them, but this requires the proprietary java2cpg installed locally.

### [Tinkergraph (in memory reference db)](http://tinkerpop.apache.org/docs/current/reference/#tinkergraph-gremlin)
```
sbt semanticcpg/console
```
```scala
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.semanticcpg.language._
val cpg = io.shiftleft.codepropertygraph.cpgloading.CpgLoader.load("./resources/testcode/cpgs/hello-shiftleft-0.0.5/cpg.bin.zip")
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

