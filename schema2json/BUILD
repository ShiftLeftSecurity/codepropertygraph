load("//bazel:scala.bzl", "scala_binary")

scala_binary(
    name = "schema2json",
    srcs = glob([
        "src/main/scala/**/*.scala",
    ]),
    main_class = "Schema2Json",
    deps = [
        "//schema",
        "@maven//:com_lihaoyi_os_lib_3",
        "@maven//:org_json4s_json4s_ast_3",
        "@maven//:org_json4s_json4s_native_3",
    ],
)
