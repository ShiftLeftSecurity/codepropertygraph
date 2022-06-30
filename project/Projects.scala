import sbt._

object Projects {
  lazy val codepropertygraph = project.in(file("codepropertygraph"))
  lazy val schema            = project.in(file("schema"))
  lazy val domainClasses     = project.in(file("domainClasses"))
  lazy val protoBindings     = project.in(file("proto-bindings"))
}
