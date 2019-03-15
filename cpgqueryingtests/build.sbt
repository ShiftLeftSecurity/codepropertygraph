name := "cpgqueryingtests"
publish / skip := true

dependsOn(Projects.cpgloaderTinkergraph % Test, Projects.semanticcpg % Test)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.3" % Test,
  "io.shiftleft" %% "fuzzyc2cpg" % Versions.fuzzyc2cpg % Test
)

scalacOptions ++= Seq("-deprecation", "-feature")
