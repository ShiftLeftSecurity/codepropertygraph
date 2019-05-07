name := "cpgqueryingtests"

dependsOn(
  Projects.semanticcpg,
  Projects.dataflowengine
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.3" % Test,
  "io.shiftleft" %% "fuzzyc2cpg" % Versions.fuzzyc2cpg
)

scalacOptions ++= Seq("-deprecation", "-feature")
