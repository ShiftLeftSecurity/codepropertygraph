name := "cpgloader-tinkergraph-shiftleft"

dependsOn(Projects.enhancements)

libraryDependencies ++= Seq(
  "io.shiftleft" % "tinkergraph-gremlin" % "3.3.3.0",
  "org.scalatest" %% "scalatest" % "3.0.3" % Test
)
