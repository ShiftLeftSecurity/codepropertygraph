name := "cpgvalidator"

dependsOn(Projects.codepropertygraph, Projects.schema)

enablePlugins(JavaAppPackaging)

libraryDependencies ++= Seq(
  "org.apache.logging.log4j" %  "log4j-slf4j-impl" % "2.11.2" % Runtime, //redirect tinkerpop's slf4j logging to log4j
  "com.github.scopt"         %% "scopt"           % "3.7.1",
  "org.scalatest"            %% "scalatest"       % Versions.scalatest % Test
)
