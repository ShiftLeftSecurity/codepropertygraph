name := "console"

enablePlugins(JavaAppPackaging)

dependsOn(Projects.codepropertygraph,
          Projects.semanticcpg % "test -> test")

scalacOptions ++= Seq(
  "-deprecation",                      // Emit warning and location for usages of deprecated APIs.
  "-encoding", "utf-8",                // Specify character encoding used by source files.
  "-explaintypes",                     // Explain type errors in more detail.
  "-feature",                          // Emit warning and location for usages of features that should be imported explicitly.
  "-language:existentials",            // Existential types (besides wildcard types) can be written and inferred
  "-language:experimental.macros",     // Allow macro definition (besides implementation and application)
  "-language:higherKinds",             // Allow higher-kinded types
  "-language:implicitConversions",     // Allow definition of implicit functions called views
  "-unchecked",                        // Enable additional warnings where generated code depends on assumptions.
  "-Xcheckinit",                       // Wrap field accessors to throw an exception on uninitialized access.
  "-Xfatal-warnings",                  // Fail the compilation if there are any warnings.
  "-Xlint:adapted-args",               // Warn if an argument list is modified to match the receiver.
  "-Xlint:constant",                   // Evaluation of a constant arithmetic expression results in an error.
  "-Xlint:delayedinit-select",         // Selecting member of DelayedInit.
  "-Xlint:doc-detached",               // A Scaladoc comment appears to be detached from its element.
  "-Xlint:inaccessible",               // Warn about inaccessible types in method signatures.
  "-Xlint:infer-any",                  // Warn when a type argument is inferred to be `Any`.
  "-Xlint:missing-interpolator",       // A string literal appears to be missing an interpolator id.
  "-Xlint:nullary-unit",               // Warn when nullary methods return Unit.
  "-Xlint:option-implicit",            // Option.apply used implicit view.
  "-Xlint:package-object-classes",     // Class or object defined in package object.
  "-Xlint:poly-implicit-overload",     // Parameterized overloaded implicit methods are not visible as view bounds.
  "-Xlint:private-shadow",             // A private field (or class parameter) shadows a superclass field.
  "-Xlint:stars-align",                // Pattern sequence wildcard must align with sequence component.
  "-Xlint:type-parameter-shadow",      // A local type parameter shadows a type already in scope.
  "-Ywarn-dead-code",                  // Warn when dead code is identified.
  "-Ywarn-extra-implicit",             // Warn when more than one implicit parameter section is defined.
  "-Ywarn-numeric-widen",              // Warn when numerics are widened.
  "-Ywarn-unused:implicits",           // Warn if an implicit parameter is unused.
  "-Ywarn-unused:imports",             // Warn if an import selector is not referenced.
  "-Ywarn-unused:locals",              // Warn if a local definition is unused.
  "-Ywarn-unused:params",              // Warn if a value parameter is unused.
  "-Ywarn-unused:patvars",             // Warn if a variable bound in a pattern is unused.
  "-Ywarn-unused:privates",            // Warn if a private member is unused.
  // "-Ywarn-value-discard"               // Warn when non-Unit expression results are unused.
)

val ScoptVersion = "3.7.1"
val BetterFilesVersion = "3.8.0"
val CatsVersion = "2.0.0"
val CirceVersion = "0.12.2"
val AmmoniteVersion = "1.8.1"
val ScalatestVersion = "3.0.8"
val ZeroturnaroundVersion = "1.13"

libraryDependencies ++= Seq(
  "com.github.scopt"     %% "scopt"         % ScoptVersion,
  "com.github.pathikrit" %% "better-files"  % BetterFilesVersion,
  "org.typelevel"        %% "cats-core"     % CatsVersion,
  "org.typelevel"        %% "cats-effect"   % CatsVersion,
  "io.circe"             %% "circe-generic" % CirceVersion,
  "io.circe"             %% "circe-parser"  % CirceVersion,
  "org.zeroturnaround"   %  "zt-zip"        % ZeroturnaroundVersion,
  "com.lihaoyi"          %% "ammonite"      % AmmoniteVersion cross CrossVersion.full,

  "org.scalatest"        %% "scalatest"     % ScalatestVersion % Test,
)
