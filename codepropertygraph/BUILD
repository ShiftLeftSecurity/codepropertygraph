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

scala_test(
    name = "tests",
    srcs = glob(["src/main/scala/**/*.scala"]),
    deps = [
        ":codepropertygraph",
    ],
)
