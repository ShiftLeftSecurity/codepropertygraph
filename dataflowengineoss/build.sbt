name := "dataflowengineoss"

dependsOn(Projects.semanticcpg % "compile -> compile; test -> test")

val antlrVersion = "4.7.2"

libraryDependencies ++= Seq(
  "org.antlr"     %  "antlr4-runtime" % antlrVersion,
  "org.scala-lang.modules" %% "scala-parallel-collections" % "0.2.0",
  "org.scalatest" %% "scalatest"      % Versions.scalatest % Test,
  "io.shiftleft"  %% "fuzzyc2cpg"     % Versions.fuzzyc2cpg % Test
)

enablePlugins(Antlr4Plugin)

Antlr4 / antlr4PackageName := Some("io.shiftleft.dataflowengineoss")
Antlr4 / antlr4Version := antlrVersion
Antlr4 / javaSource := (sourceManaged in Compile).value
sources in (Compile, doc) ~= (_ filter (_ => false))