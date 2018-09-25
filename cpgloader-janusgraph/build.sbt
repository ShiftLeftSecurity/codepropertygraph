name := "cpgloader-janusgraph"

dependsOn(Projects.enhancements)

libraryDependencies ++= Seq(
  "org.janusgraph" % "janusgraph-berkeleyje" % "0.3.0"
    exclude("org.apache.tinkerpop", "tinkergraph-gremlin"), // really? janusgraph depends on tinkergraph? lol
  "org.scalatest" %% "scalatest" % "3.0.3" % Test
)
