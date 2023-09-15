name := "codepropertygraph"

// dependsOn(Projects.protoBindings, Projects.domainClasses)
dependsOn(Projects.domainClasses)

libraryDependencies ++= Seq(
  // "io.shiftleft"          %% "overflowdb-traversal" % Versions.overflowdb,
  // "io.shiftleft"          %% "overflowdb-formats"   % Versions.overflowdb,
  "net.sf.trove4j"         % "core"                 % "3.1.0",
  "com.github.scopt"      %% "scopt"                % "4.0.1",
  "com.github.pathikrit"  %% "better-files"         % "3.9.2",
  "org.slf4j"              % "slf4j-api"            % "2.0.6",
  "org.scalatest"         %% "scalatest"            % Versions.scalatest % Test
)
