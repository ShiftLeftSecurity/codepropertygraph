name := "cpgvalidator"

dependsOn(Projects.codepropertygraph)

libraryDependencies ++= Seq(
)

scalacOptions in (Compile, doc) ++= Seq(
  version.value
)
