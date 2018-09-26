name := "cpgloader-tinkergraph"

dependsOn(Projects.enhancements)

libraryDependencies ++= Seq(
  "org.apache.tinkerpop" % "tinkergraph-gremlin" % "3.3.3",
  "org.scalatest" %% "scalatest" % "3.0.3" % Test
)
