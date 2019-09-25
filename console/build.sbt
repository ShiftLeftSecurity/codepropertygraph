name := "console"

enablePlugins(JavaAppPackaging)

libraryDependencies ++= Seq(
  "com.massisframework"  %  "j-text-utils"  % "0.3.4",
  "com.github.scopt"     %% "scopt"         % "3.7.1",
  "com.github.pathikrit" %% "better-files"  % "3.8.0",
  "org.apache.commons"   % "commons-lang3"  % "3.8",
  "com.lihaoyi" %% "ammonite" % "1.7.1" cross CrossVersion.full,
  "org.typelevel" %% "cats-kernel" % "1.4.0",
  "org.scalatest" %% "scalatest" % "3.0.8",  
  "org.scalatest"        %% "scalatest"     % "3.0.8" % Test,
  "com.pauldijou" %% "jwt-json4s-native" % "3.0.1",
)
