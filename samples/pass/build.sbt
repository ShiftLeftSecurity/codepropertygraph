name := "samplepass"
organization := "io.shiftleft"

val cpgVersion = "0.11.1"

libraryDependencies ++= Seq(
  "io.shiftleft"  %% "codepropertygraph" % cpgVersion,
  "io.shiftleft"  %% "enhancements"      % cpgVersion,
  "org.scalatest" %% "scalatest"         % Versions.scalatest % Test
)

ThisBuild / resolvers ++= Seq(
  Resolver.mavenLocal,
  "Sonatype OSS" at "https://oss.sonatype.org/content/repositories/public",
)

enablePlugins(JavaAppPackaging)

githubOwner      := "Privado-Inc"
githubRepository := "codepropertygraph"
credentials +=
  Credentials(
    "GitHub Package Registry",
    "maven.pkg.github.com",
    "Privado-Inc",
    sys.env.getOrElse("GITHUB_TOKEN", "N/A")
  )