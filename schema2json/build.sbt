name := "schema2json"

dependsOn(Projects.schema)

libraryDependencies ++= Seq(
  "com.lihaoyi"           %% "ujson"         % Versions.ujson,
  "org.scalatest"         %% "scalatest"     % Versions.scalatest % Test
)

scalacOptions -= "-Xfatal-warnings" // some antl-generated sources prompt compiler warnings :(

compile / javacOptions ++= Seq("-Xlint:all", "-Xlint:-cast", "-g")
Test / fork := true
testOptions += Tests.Argument(TestFrameworks.JUnit, "-a", "-v")

enablePlugins(JavaAppPackaging)

stage := stage.dependsOn(Projects.schema / Compile / generateDomainClasses).value
