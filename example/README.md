# Create and query a Code Property Graph

In order to query a Code Property Graph (CPG) you need to generate it
first in a separate step. Since the CPG is an open and language agnostic
[format](https://github.com/ShiftLeftSecurity/codepropertygraph#base-schema-for-the-code-property-graph)
, you can either provide your own CPG or use our
open source C/C++ frontend to create a CPG for any C/C++ program.

For detail on how to build and run the C/C++ please click
[here](https://github.com/ShiftLeftSecurity/fuzzyc2cpg).


Once a CPG is generated checkout this
[example](https://github.com/ShiftLeftSecurity/codepropertygraph/tree/master/example)
project which shows how to query a CPG.
It consists of a
[build.sbt](https://github.com/ShiftLeftSecurity/codepropertygraph/blob/master/example/build.sbt)
file where you control the dependencies
used in the example project. For now it consists of the minimum required
to run CPG queries. It also contains a
[Main.scala](https://github.com/ShiftLeftSecurity/codepropertygraph/blob/master/example/src/main/scala/org/example/Main.scala)
loads the CPG and
queries it. In order to build the example program issue the command
`sbt stage` in the example directory. After the program is build you
can run it with the command `target/universal/stage/bin/example`.

