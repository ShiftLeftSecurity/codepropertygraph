name := "macros"

enablePlugins(JavaAppPackaging)

scalacOptions ++= Seq( "-Yrangepos" )

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
)

publishArtifact in (Test, packageBin) := true

// execute tests in root project so that they work in sbt *and* intellij
Test / baseDirectory := (ThisBuild / Test / run / baseDirectory).value
