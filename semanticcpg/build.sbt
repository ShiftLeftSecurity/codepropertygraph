name := "semanticcpg"

dependsOn(Projects.queryPrimitives)

libraryDependencies ++= Seq(
  "org.apache.commons"  % "commons-lang3"    % "3.8",
  "org.scalatest"       %% "scalatest"       % "3.0.3" % Test,
)

compile / javacOptions ++= Seq("-g") //debug symbols

// execute tests in root project so that they work in sbt *and* intellij
Test / baseDirectory := (ThisBuild / Test / run / baseDirectory).value
