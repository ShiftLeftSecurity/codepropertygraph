name := "macrosSm"

// dependsOn(Projects.codepropertygraph)
// scalacOptions ++= Seq( "-Yrangepos" )
scalacOptions += "-Ymacro-annotations"

libraryDependencies ++= Seq(
  "org.scalameta" %% "scalameta" % "4.4.7"
)

// execute tests in root project so that they work in sbt *and* intellij
Test / baseDirectory := (ThisBuild / Test / run / baseDirectory).value
