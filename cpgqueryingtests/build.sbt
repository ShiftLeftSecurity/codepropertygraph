name := "cpgqueryingtests"
publish / skip := true

dependsOn(Projects.cpgloaderTinkergraph % Test)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.3" % Test
)

scalacOptions ++= Seq("-deprecation", "-feature")
