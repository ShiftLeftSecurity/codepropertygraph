import sbt._

object Projects {
  lazy val codepropertygraph = project.in(file("codepropertygraph"))
  lazy val protoBindings = project.in(file("proto-bindings"))
  lazy val queryPrimitives = project.in(file("query-primitives"))
  lazy val enhancements = project.in(file("enhancements"))
  lazy val cpgqueryingtests = project.in(file("cpgqueryingtests"))
  lazy val semanticcpg = project.in(file("semanticcpg"))
  lazy val dataflowengine = project.in(file("dataflowengine"))
  lazy val cpgserver = project.in(file("cpgserver"))
  lazy val cpgvalidator = project.in(file("cpgvalidator"))
  lazy val cpgsplitter = project.in(file("cpgsplitter"))
}
