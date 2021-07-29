name := "codepropertygraph"

dependsOn(Projects.protoBindings, Projects.domainClasses)

libraryDependencies ++= Seq(
  "io.shiftleft" %% "overflowdb-traversal" % Versions.overflowdb,
  "com.github.scopt"     %% "scopt"                    % "4.0.1",
  "com.github.pathikrit"     %% "better-files"         % "3.9.1",
  "org.slf4j"                %  "slf4j-api"            % "1.7.30",
  "org.scalatest"            %% "scalatest"            % Versions.scalatest % Test,
  "org.scalameta"            %% "scalameta"            % "4.4.23"
)
