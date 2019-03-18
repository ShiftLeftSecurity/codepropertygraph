import sbt._

object Projects {
  lazy val codepropertygraph = project.in(file("codepropertygraph"))
  lazy val protoBindings = project.in(file("proto-bindings"))
  lazy val queryPrimitives = project.in(file("query-primitives"))
  lazy val enhancements = project.in(file("enhancements"))
  lazy val cpgloaderTinkergraph = project.in(file("cpgloader-tinkergraph"))
  lazy val cpgloaderTinkergraphShiftleft = project.in(file("cpgloader-tinkergraph-shiftleft"))
  lazy val cpgloaderJanusgraph = project.in(file("cpgloader-janusgraph"))
  lazy val cpgloaderNeo4j = project.in(file("cpgloader-neo4j"))
  lazy val cpgqueryingtests = project.in(file("cpgqueryingtests"))
  lazy val semanticcpg = project.in(file("semanticcpg"))
}
