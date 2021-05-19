name := "codepropertygraph"

dependsOn(Projects.protoBindings, Projects.domainClasses)

libraryDependencies ++= Seq(
  "io.shiftleft" %% "overflowdb-traversal" % Versions.overflowdb,
  "com.github.pathikrit"     %% "better-files"         % "3.9.1",
  "org.scala-lang.modules"   %% "scala-java8-compat"   % "0.9.0",
  "org.slf4j"                %  "slf4j-api"            % "1.7.30",
  "org.scalatest"            %% "scalatest"            % Versions.scalatest % Test
)
