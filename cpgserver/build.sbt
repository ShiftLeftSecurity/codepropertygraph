val ScalatraVersion = "2.7.0-RC1"
organization := "io.shiftleft"
name := "CPG server"

resolvers += Classpaths.typesafeReleases

dependsOn(Projects.codepropertygraph)
dependsOn(Projects.semanticcpg)

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % ScalatraVersion,
  "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test",
  "org.scalatra" %% "scalatra-json" % ScalatraVersion,
  "org.scalatra" %% "scalatra-swagger"  % ScalatraVersion,

  "com.github.pathikrit" %% "better-files"  % "3.8.0",
  "com.typesafe.akka" %% "akka-actor" % "2.5.25",
  // "net.databinder.dispatch" %% "dispatch-core" % "0.13.1",

  "org.json4s"   %% "json4s-native" % "3.6.7",
  "ch.qos.logback" % "logback-classic" % "1.2.3" % "runtime",
  "org.eclipse.jetty" % "jetty-webapp" % "9.4.7.v20170914" % "container;compile",
  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided"
)

enablePlugins(JavaAppPackaging)
enablePlugins(SbtTwirl)
enablePlugins(ScalatraPlugin)
