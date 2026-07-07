load("@protobuf//bazel:java_proto_library.bzl", "java_proto_library")
load("@protobuf//bazel:proto_library.bzl", "proto_library")
load("//bazel:java.bzl", "java_import")
load("//bazel:scala.bzl", "scala_binary", "scala_library")

scala_library(
    name = "schema",
    srcs = glob(["src/main/scala/**/*.scala"]),
    visibility = ["//visibility:public"],
    deps = [
        "@maven//:io_joern_flatgraph_domain_classes_generator_3",
    ],
)

scala_binary(
    name = "codegen",
    main_class = "flatgraph.codegen.Main",
    runtime_deps = [
        ":schema",
    ],
    deps = [
        "@maven//:io_joern_flatgraph_domain_classes_generator_3",
    ],
)

genrule(
    name = "run_codegen",
    outs = ["generated_classes.srcjar"],
    cmd = """
  $(location :codegen) -c 'io.shiftleft.codepropertygraph.schema.CpgSchema$$' -f instance -o $(RULEDIR)/generated_classes
  $(JAVABASE)/bin/jar --create --file $@ -C $(RULEDIR)/generated_classes .
  """,
    toolchains = ["@bazel_tools//tools/jdk:current_java_runtime"],
    tools = [":codegen"],
)

# TODO remove workaround once bug is fixed
# We would want to use this library target directly to include the generated
# files. While this works fine for the build itself, we currently have a bug
# in the Intellij Bazel plugin which prevents it from reliable picking up
# the generated class symbols for symbol resolution resulting in a bunch of
# red unresolved symbols. We cannot accept this for the generated domain
# classes as those are the type system anchors for all our DSL language steps
# which means we would have no completion for them in the IDE.
# To resolve this we package the resulting jars from this target into a single
# jar in 'domainClasses_jar' and include the resulting jar via a java_import
# rule.
# Once this is not needed anymore we can just rename this target to 'domainClasses'
# and delete 'domainClasses_jar' and the java_import rule.
scala_library(
    name = "domainClassesDontUseDirectly",
    srcs = [":run_codegen"],
    javacopts = [
        "-XepDisableAllChecks",
    ],
    deps = [
        "@maven//:io_joern_flatgraph_core_3",
        "@maven//:io_joern_flatgraph_help_3",
    ],
)

genrule(
    name = "domainClasses_jar",
    srcs = [":domainClassesDontUseDirectly"],
    outs = ["domainClasses.jar"],
    cmd = """
  START_PWD=$$(pwd)
  TMP=$$(mktemp -d)
  for jar in $(locations :domainClassesDontUseDirectly); do
    (cd $$TMP && $$START_PWD/$(JAVABASE)/bin/jar -xf $$START_PWD/$$jar)
  done
  $(JAVABASE)/bin/jar cf $@ -C $$TMP .
  rm -rf $$TMP
  """,
    toolchains = ["@bazel_tools//tools/jdk:current_java_runtime"],
)

genrule(
    name = "domainClasses_src_jar",
    srcs = [":run_codegen"],
    outs = ["domainClasses-sources.jar"],
    cmd = "cp $< $@",
)

java_import(
    name = "domainClassesNeverLinked",
    jars = [":domainClasses_jar"],
    srcjar = ":domainClasses_src_jar",
    visibility = ["//visibility:public"],
    exports = [
        "@maven//:io_joern_flatgraph_core_3",
        "@maven//:io_joern_flatgraph_help_3",
    ],
    neverlink = True,
)

java_import(
    name = "domainClassesLinked",
    jars = [":domainClasses_jar"],
    srcjar = ":domainClasses_src_jar",
    visibility = ["//visibility:public"],
    exports = [
        "@maven//:io_joern_flatgraph_core_3",
        "@maven//:io_joern_flatgraph_help_3",
    ],
)

scala_binary(
    name = "protogen-bin",
    main_class = "io.shiftleft.codepropertygraph.schema.Protogen",
    runtime_deps = [
        ":schema",
    ],
    deps = [
    ],
)

genrule(
    name = "run_protogen",
    outs = ["cpg.proto"],
    cmd = """
  $(location :protogen-bin) $(RULEDIR)/
  """,
    tools = [":protogen-bin"],
)

proto_library(
    name = "proto",
    srcs = [
        ":run_protogen",
    ],
)

java_proto_library(
    name = "java_proto",
    visibility = ["//visibility:public"],
    deps = [
        ":proto",
    ],
)
