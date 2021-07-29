import sbt._

object Projects {
  lazy val codepropertygraph = project.in(file("codepropertygraph"))
  lazy val schema = project.in(file("schema"))
  lazy val domainClasses = project.in(file("domainClasses"))
  lazy val protoBindings = project.in(file("proto-bindings"))
  lazy val semanticcpg = project.in(file("semanticcpg"))
  lazy val semanticcpgtests = project.in(file("semanticcpg-tests"))
  lazy val dataflowengineoss = project.in(file("dataflowengineoss"))
  lazy val dataflowengineosstests = project.in(file("dataflowengineoss-tests"))
  lazy val console = project.in(file("console"))
  lazy val fuzzyc2cpg = project.in(file("fuzzyc2cpg"))
  lazy val fuzzyc2cpgtests = project.in(file("fuzzyc2cpg-tests"))
  lazy val c2cpg = project.in(file("c2cpg"))
  lazy val c2cpgtests = project.in(file("c2cpg-tests"))
  lazy val macros = project.in(file("macros"))
  lazy val schema2json = project.in(file("schema2json"))
}
