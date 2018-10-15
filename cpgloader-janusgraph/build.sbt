name := "cpgloader-janusgraph"

dependsOn(Projects.enhancements)

libraryDependencies ++= Seq(
  "org.janusgraph" % "janusgraph-berkeleyje" % "0.3.1"
    exclude("org.apache.tinkerpop", "tinkergraph-gremlin"), // really? janusgraph depends on tinkergraph? lol
  "org.scalatest" %% "scalatest" % "3.0.3" % Test
)

// for com.sleepycat:je:jar
resolvers += "Oracle Releases" at "http://download.oracle.com/maven"
