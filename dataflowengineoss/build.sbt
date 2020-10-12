name := "dataflowengineoss"

dependsOn(Projects.semanticcpg % "compile -> compile; test -> test",
          Projects.fuzzyc2cpg % Test)

libraryDependencies ++= Seq(
  "org.antlr"     %  "antlr4-runtime" % Versions.antlr,
  "org.scalatest" %% "scalatest"      % Versions.scalatest % Test,
)

enablePlugins(Antlr4Plugin)

Antlr4 / antlr4PackageName := Some("io.shiftleft.dataflowengineoss")
Antlr4 / antlr4Version := Versions.antlr
Antlr4 / javaSource := (sourceManaged in Compile).value
sources in (Compile, doc) ~= (_ filter (_ => false))

// execute tests in root project so that they work in sbt *and* intellij
Test / baseDirectory := (ThisBuild / Test / run / baseDirectory).value
