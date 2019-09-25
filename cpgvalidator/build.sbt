name := "cpgvalidator"

dependsOn(Projects.codepropertygraph)

enablePlugins(JavaAppPackaging)

libraryDependencies ++= Seq(
  "org.apache.logging.log4j" % "log4j-slf4j-impl" % "2.11.2" % Runtime, //redirect tinkerpop's slf4j logging to log4j
  "com.typesafe.play" %% "play-json" % "2.7.4",
  "org.scalatest"       %% "scalatest"       % "3.0.8" % "test",
)

// execute tests in root project so that they work in sbt *and* intellij
Test / baseDirectory := (ThisBuild / Test / run / baseDirectory).value
