load("@rules_java//java:java_library.bzl", "java_library")
load("//bazel:scala.bzl", "scala_library", "scala_test")

scala_library(
    name = "codepropertygraph",
    srcs = glob([
        "src/main/scala/**/*.java",
        "src/main/scala/**/*.scala",
    ]),
    visibility = ["//visibility:public"],
    deps = [
        "//schema:domainClassesNeverLinked",
        "//schema:java_proto",
        "@maven//:com_google_protobuf_protobuf_java",
        "@maven//:io_joern_flatgraph_core_3",
        "@maven//:io_joern_flatgraph_help_3",
        "@maven//:io_joern_flatgraph_odb_convert_3",
        "@maven//:net_sf_trove4j_core",
        "@maven//:org_slf4j_slf4j_api",
    ],
)

# We need to use the java_library rule because the dependency
# @rules_java//java/runfiles brings in an annotation processor and
# those only get picked up and executed by java_library and not
# scala_library.
java_library(
    name = "tests_java",
    testonly = True,
    srcs = glob(["src/test/javaBazel/**/*.java"]),
    deps = [
        "@rules_java//java/runfiles",
    ],
)

scala_test(
    name = "tests",
    srcs = glob(["src/test/scala/**/*.scala"]),
    data = glob(["src/test/resources/**"]),
    # The compile time domain classes are neverlink, so the tests need the linked variant at runtime.
    runtime_deps = ["//schema:domainClassesLinked"],
    deps = [
        ":codepropertygraph",
        ":tests_java",
        "//schema:domainClassesNeverLinked",
        "//schema:java_proto",
        "@maven//:com_github_pathikrit_better_files_3",
        "@maven//:io_joern_flatgraph_core_3",
        "@maven//:io_joern_flatgraph_help_3",
    ],
)
