load("//bazel:scala.bzl", "scala_binary")

scala_binary(
    name = "schema2json",
    srcs = glob([
        "src/main/scala/**/*.scala",
    ]),
    main_class = "Schema2Json",
    runtime_deps = [
        "//schema:domainClassesLinked",
    ],
    deps = [
        "//schema",
        "@maven//:com_lihaoyi_os_lib_3",
        "@maven//:com_lihaoyi_ujson_3",
    ],
)
