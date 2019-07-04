name := "cpg1"

dependsOn(Projects.protoBindings)

libraryDependencies ++= Seq(
  "io.shiftleft" % "tinkergraph-gremlin" % "3.3.4.17-MP-SNAPSHOT",
  "com.michaelpollmeier" %% "gremlin-scala" % "3.3.4.13",
  "com.google.guava" % "guava" % "21.0",
  "org.apache.commons" % "commons-lang3" % "3.5",
  "commons-io" % "commons-io" % "2.5",
  "org.scala-lang.modules" %% "scala-java8-compat" % "0.8.0",
  "com.jsuereth" %% "scala-arm" % "2.0",
  "com.github.scopt" %% "scopt" % "3.7.0",
  "org.apache.logging.log4j" % "log4j-api" % "2.11.0",
  "org.apache.logging.log4j" % "log4j-core" % "2.11.0",
  "org.apache.logging.log4j" % "log4j-slf4j-impl" % "2.11.2" % Runtime, //redirect tinkerpop's slf4j logging to log4j
  "org.scalatest" %% "scalatest" % "3.0.3" % Test
)
