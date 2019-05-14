name := "codepropertygraph1"

dependsOn(Projects.protoBindings)

libraryDependencies ++= Seq(
  "io.shiftleft" % "tinkergraph-gremlin" % "3.3.4.7-SNAPSHOT",
  "com.michaelpollmeier" %% "gremlin-scala" % "3.3.4.13",
  "com.google.guava" % "guava" % "21.0",
  "org.apache.commons" % "commons-lang3" % "3.5",
  "commons-io" % "commons-io" % "2.5",
  "org.scala-lang.modules" %% "scala-java8-compat" % "0.8.0",
  "org.apache.logging.log4j" % "log4j-api" % "2.11.0",
  "org.apache.logging.log4j" % "log4j-core" % "2.11.0",
  "org.scalatest" %% "scalatest" % "3.0.3" % Test
)
