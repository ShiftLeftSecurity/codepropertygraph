name := "codepropertygraph"

dependsOn(Projects.protoBindings, Projects.domainClasses)

libraryDependencies ++= Seq(
  "io.joern"              %% "flatgraph-formats"     % Versions.flatgraph,
  "io.joern"              %% "flatgraph-help"        % Versions.flatgraph,
  "io.joern"              %% "flatgraph-odb-convert" % Versions.flatgraph,
  "com.github.scopt"      %% "scopt"                % "4.0.1",
  "com.github.pathikrit"  %% "better-files"         % "3.9.2",
  "org.slf4j"              % "slf4j-api"            % "2.0.6",
  "org.scalatest"         %% "scalatest"            % Versions.scalatest % Test
)
