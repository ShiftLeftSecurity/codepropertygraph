name := "cpgvalidator"

dependsOn(Projects.codepropertygraph)

enablePlugins(JavaAppPackaging)

libraryDependencies ++= Seq(
)

scalacOptions in (Compile, doc) ++= Seq(
  version.value
)
