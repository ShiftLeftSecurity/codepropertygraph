name := "codepropertygraph"

dependsOn(Projects.protoBindings, Projects.domainClasses)

libraryDependencies ++= Seq(
  "com.github.pathikrit"     %% "better-files"         % "3.9.1",
  "org.scala-lang.modules"   %% "scala-java8-compat"   % "0.9.0",
  "org.slf4j"                %  "slf4j-api"            % "1.7.30",
  "org.scalatest"            %% "scalatest"            % Versions.scalatest % Test
)

// TODO: do this again?
// note: this is only invoked on `package`, `publish` etc. since it's not needed for `compile`
// Compile / resourceGenerators += generateProtobuf.taskValue.map(Seq(_))

// import Path._
// mappings in (Compile, packageSrc) ++= { // publish generated sources
//   val srcs = (managedSources in Compile).value
//   val sdirs = (managedSourceDirectories in Compile).value
//   val base = baseDirectory.value
//   (srcs --- sdirs --- base) pair (relativeTo(sdirs) | relativeTo(base) | flat)
// }
