name := "cpg2overflowdb"

dependsOn(Projects.codepropertygraph)

enablePlugins(JavaAppPackaging)

libraryDependencies ++= Seq(
  "org.apache.logging.log4j" % "log4j-slf4j-impl" % "2.11.2" % Runtime, //redirect tinkerpop's slf4j logging to log4j
  "org.scalatest" %% "scalatest" % "3.0.8" % Test,
  "org.scala-lang.modules" %% "scala-parallel-collections" % "0.2.0"
)

ThisBuild/mainClass in Compile := Some("io.shiftleft.codepropertygraph.cpgloading.ProtoToOverflowDb")

skip in publish := true
