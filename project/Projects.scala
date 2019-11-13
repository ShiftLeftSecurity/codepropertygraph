import sbt._

object Projects {
  lazy val codepropertygraph = project.in(file("codepropertygraph"))
  lazy val protoBindings = project.in(file("proto-bindings"))
  lazy val semanticcpg = project.in(file("semanticcpg"))
  lazy val dataflowengine = project.in(file("dataflowengine"))
  lazy val cpgserver = project.in(file("cpgserver"))
  lazy val cpgvalidator = project.in(file("cpgvalidator"))
  lazy val cpg2overflowdb = project.in(file("cpg2overflowdb"))
  lazy val console = project.in(file("console"))
  lazy val queries = project.in(file("queries"))
}
