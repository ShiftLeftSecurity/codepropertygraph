name := "cpgloader-janusgraph"

dependsOn(Projects.enhancements)

libraryDependencies ++= Seq(
  "org.apache.tinkerpop" % "neo4j-gremlin" % "3.3.3",
  "org.neo4j" % "neo4j-tinkerpop-api-impl" % "0.7-3.2.3",
  "org.scalatest" %% "scalatest" % "3.0.3" % Test
)
