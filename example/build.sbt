name := "example"
organization := "org.example"
scalaVersion := "2.12.7"

val cpgVersion = "0.9.85"

libraryDependencies ++= Seq(
  "io.shiftleft" % "codepropertygraph" % cpgVersion,
  "io.shiftleft" % "cpgloader-tinkergraph" % cpgVersion,
  "io.shiftleft" % "query-primitives" % cpgVersion,
  "io.shiftleft" % "enhancements" % cpgVersion,
)

enablePlugins(JavaAppPackaging)
