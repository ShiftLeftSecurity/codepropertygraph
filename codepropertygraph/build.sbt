name := "codepropertygraph"

dependsOn(Projects.protoBindings, Projects.domainClasses)

libraryDependencies ++= Seq(
  "com.google.protobuf"    % "protobuf-java"        % "3.18.0", // TODO drop after protoBindings are back
  "net.sf.trove4j"         % "core"                 % "3.1.0",
  "com.github.scopt"      %% "scopt"                % "4.0.1",
  "com.github.pathikrit"  %% "better-files"         % "3.9.2",
  "org.slf4j"              % "slf4j-api"            % "2.0.6",
  "io.joern.flatgraph"    %% "odb-convert"          % Versions.flatgraph,
  "org.scalatest"         %% "scalatest"            % Versions.scalatest % Test
)
