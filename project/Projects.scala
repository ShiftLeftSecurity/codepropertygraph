import sbt._

object Projects {
  lazy val codepropertygraph = project.in(file("codepropertygraph"))
  lazy val protoBindings = project.in(file("proto-bindings"))
  lazy val queryPrimitives = project.in(file("query-primitives"))
  lazy val enhancements = project.in(file("enhancements"))
}
