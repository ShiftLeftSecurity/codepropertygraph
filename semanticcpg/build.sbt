name := "semanticcpg"

dependsOn(Projects.queryPrimitives, Projects.enhancements)

libraryDependencies ++= Seq(
  "com.massisframework"             % "j-text-utils"                      % "0.3.4",
)

compile / javacOptions ++= Seq("-g") //debug symbols

// execute tests in root project so that they work in sbt *and* intellij
Test / baseDirectory := (ThisBuild / Test / run / baseDirectory).value
