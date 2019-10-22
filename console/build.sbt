name := "console"

enablePlugins(JavaAppPackaging)

libraryDependencies ++= Seq(
  "com.massisframework"  %  "j-text-utils"  % "0.3.4",
  "com.github.scopt"     %% "scopt"         % "3.7.1",
  "com.github.pathikrit" %% "better-files"  % "3.8.0",
  "org.apache.commons"   % "commons-lang3"  % "3.8",
  "io.circe"             %% "circe-generic" % "0.12.1",
  "io.circe"             %% "circe-parser"  % "0.12.1",
  "com.lihaoyi"          %% "ammonite"      % "1.7.1" cross CrossVersion.full,
  "org.scalatest"        %% "scalatest"     % "3.0.8",
  "org.scalatest"        %% "scalatest"     % "3.0.8" % Test,
)
