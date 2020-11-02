import sbt._

object Projects {
  lazy val codepropertygraph = project.in(file("codepropertygraph"))
  lazy val schema = project.in(file("schema"))
  lazy val domainClasses = project.in(file("domainClasses"))
  lazy val protoBindings = project.in(file("proto-bindings"))
  lazy val semanticcpg = project.in(file("semanticcpg"))
  lazy val semanticcpgtests = project.in(file("semanticcpg-tests"))
  lazy val dataflowengineoss = project.in(file("dataflowengineoss"))
  lazy val cpgvalidator = project.in(file("cpgvalidator"))
  lazy val console = project.in(file("console"))
  lazy val queries = project.in(file("queries"))
  lazy val fuzzyc2cpg = project.in(file("fuzzyc2cpg"))
}
