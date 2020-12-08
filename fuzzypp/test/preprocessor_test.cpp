#include <algorithm>

#include <catch.hpp>

#include <cliopts.hpp>
#include <preprocessor.hpp>

#include "test_utils.hpp"

TEST_CASE("1: Preprocess an input file, preserving comments", "preprocess") {
    const auto source_content = "int main ( ) { } // A sight to behold!";
    const auto source_file = fuzzypp::tests::create_temp_file("preprocess/1/source.cpp", source_content);
    const auto output_dir = std::filesystem::temp_directory_path() /= std::filesystem::path { "preprocess/1/output" }.make_preferred();

    fuzzypp::cliopts::CliOptions opts {
        std::vector<std::filesystem::path> { source_file },
        std::vector<std::filesystem::path> {},
        std::vector<std::filesystem::path> {},
        std::vector<std::string> {},
        std::vector<std::string> {},
        output_dir,
        false
    };

    fuzzypp::preprocessor::Preprocessor::preprocess(opts);

    auto result_path = std::filesystem::path { output_dir } /= source_file;
    auto result = fuzzypp::tests::read_file_content(result_path);
    
    REQUIRE(result == source_content);
}

TEST_CASE("2: Preprocess an input file with an included file", "preprocess") {
// TODO: remove the early return after the CI temp file path issue has been resolved
#ifdef _WIN32
  return;
#endif

    const auto header_content = "int x = 5;";
    const auto source_content = "#include \"source.hpp\"\n"
                                "int main ( ) { return x; } // A sight to behold!";

    auto header_file = fuzzypp::tests::create_temp_file("preprocess/2/source.hpp", header_content);
    auto source_file = fuzzypp::tests::create_temp_file("preprocess/2/source.cpp", source_content);
    const auto output_dir = std::filesystem::temp_directory_path() /= std::filesystem::path { "preprocess/2/output" }.make_preferred();

    fuzzypp::cliopts::CliOptions opts {
        std::vector<std::filesystem::path> { source_file },
        std::vector<std::filesystem::path> { header_file },
        std::vector<std::filesystem::path> {},
        std::vector<std::string> {},
        std::vector<std::string> {},
        output_dir,
        false
    };

    fuzzypp::preprocessor::Preprocessor::preprocess(opts);

    auto header_file_str = header_file.string();
// SimpleCPP for whatever reason converts header paths into Unix form when printing #line directives...
#ifdef _WIN32
    std::replace(header_file_str.begin(), header_file_str.end(), '\\', '/');   
#endif

    const auto expected = "\n"
                          "#line 1 \"" + header_file_str + "\"\n"
                          "int x = 5 ;\n"
                          "#line 2 \"" + source_file.string() + "\"\n"
                          "int main ( ) { return x ; } // A sight to behold!";
    auto result_path = std::filesystem::path { output_dir } /= source_file.relative_path();
    auto result = fuzzypp::tests::read_file_content(result_path);

    REQUIRE(result == expected);
}

TEST_CASE("3: Preprocess an input file with an included path", "preprocess") {
// TODO: remove the early return after the CI temp file path issue has been resolved
#ifdef _WIN32
  return;
#endif

    const auto header_content = "int x = 5;";
    const auto source_content = "#include <header.hpp>\n"
                                "int main ( ) { return x; } // A sight to behold!";

    auto header_file = fuzzypp::tests::create_temp_file("preprocess/3/other/header.hpp", header_content);
    auto source_file = fuzzypp::tests::create_temp_file("preprocess/3/source.cpp", source_content);
    const auto output_dir = std::filesystem::temp_directory_path() /= std::filesystem::path { "preprocess/3/output" }.make_preferred();

    fuzzypp::cliopts::CliOptions opts {
        std::vector<std::filesystem::path> { source_file },
        std::vector<std::filesystem::path> {},
        std::vector<std::filesystem::path> { header_file.parent_path() },
        std::vector<std::string> {},
        std::vector<std::string> {},
        output_dir,
        false
    };

    fuzzypp::preprocessor::Preprocessor::preprocess(opts);

    auto header_file_str = header_file.string();
// SimpleCPP for whatever reason converts header paths into Unix form when printing #line directives...
#ifdef _WIN32
    std::replace(header_file_str.begin(), header_file_str.end(), '\\', '/');   
#endif

    const auto expected = "\n"
                          "#line 1 \"" + header_file_str + "\"\n"
                          "int x = 5 ;\n"
                          "#line 2 \"" + source_file.string() + "\"\n"
                          "int main ( ) { return x ; } // A sight to behold!";
    auto result_path = std::filesystem::path { output_dir } /= source_file.relative_path();
    auto result = fuzzypp::tests::read_file_content(result_path);

    REQUIRE(result == expected);
}

TEST_CASE("4: Preprocess an input file with a define", "preprocess") {
    const auto source_content = "int main() {\n" 
                                "#ifdef CAKE\n"
                                "  return 0;\n"
                                "#else\n"
                                "  return 1;\n"
                                "#endif\n"
                                "} // A sight to behold!";
    auto source_file = fuzzypp::tests::create_temp_file("preprocess/4/source.cpp", source_content);
    const auto output_dir = std::filesystem::temp_directory_path() /= std::filesystem::path { "preprocess/4/output" }.make_preferred();

    fuzzypp::cliopts::CliOptions opts {
        std::vector<std::filesystem::path> { source_file },
        std::vector<std::filesystem::path> {},
        std::vector<std::filesystem::path> {},
        std::vector<std::string> { "CAKE" },
        std::vector<std::string> {},
        output_dir,
        false
    };

    fuzzypp::preprocessor::Preprocessor::preprocess(opts);

    const auto expected = "int main ( ) {\n"
                          "\n"
                          "return 0 ;\n"
                          "\n"
                          "\n"
                          "\n"
                          "} // A sight to behold!";
    auto result_path = std::filesystem::path { output_dir } /= source_file.relative_path();
    auto result = fuzzypp::tests::read_file_content(result_path);

    REQUIRE(result == expected);
}

TEST_CASE("5: Preprocess an input file with a define value", "preprocess") {
    const auto source_content = "int main() { return CAKE; } // A sight to behold!";
    auto source_file = fuzzypp::tests::create_temp_file("preprocess/5/source.cpp", source_content);
    const auto output_dir = std::filesystem::temp_directory_path() /= std::filesystem::path { "preprocess/5/output" }.make_preferred();

    fuzzypp::cliopts::CliOptions opts {
        std::vector<std::filesystem::path> { source_file },
        std::vector<std::filesystem::path> {},
        std::vector<std::filesystem::path> {},
        std::vector<std::string> { "CAKE=0" },
        std::vector<std::string> {},
        output_dir,
        false
    };

    fuzzypp::preprocessor::Preprocessor::preprocess(opts);

    const auto expected = "int main ( ) { return 0 ; } // A sight to behold!";
    auto result_path = std::filesystem::path { output_dir } /= source_file.relative_path();
    auto result = fuzzypp::tests::read_file_content(result_path);

    REQUIRE(result == expected);
}

TEST_CASE("6: Preprocess an input file with an undefine", "preprocess") {
    const auto source_content = "#define CAKE\n"
                                "int main() {\n" 
                                "#ifdef CAKE\n"
                                "  return 0;\n"
                                "#else\n"
                                "  return 1;\n"
                                "#endif\n"
                                "} // A sight to behold!";
    auto source_file = fuzzypp::tests::create_temp_file("preprocess/6/source.cpp", source_content);
    const auto output_dir = std::filesystem::temp_directory_path() /= std::filesystem::path { "preprocess/6/output" }.make_preferred();

    fuzzypp::cliopts::CliOptions opts {
        std::vector<std::filesystem::path> { source_file },
        std::vector<std::filesystem::path> {},
        std::vector<std::filesystem::path> {},
        std::vector<std::string> {},
        std::vector<std::string> { "CAKE" },
        output_dir,
        false
    };

    fuzzypp::preprocessor::Preprocessor::preprocess(opts);

    const auto expected = "\n"
                          "int main ( ) {\n"
                          "\n"
                          "\n"
                          "\n"
                          "return 1 ;\n"
                          "\n"
                          "} // A sight to behold!";
    auto result_path = std::filesystem::path { output_dir } /= source_file.relative_path();
    auto result = fuzzypp::tests::read_file_content(result_path);

    REQUIRE(result == expected);
}

TEST_CASE("7: Elide any include directives for which an explict header file can not be found", "preprocess") { 
    const auto source_content = "#include <andioop>\n"
                                "int main ( ) { } // A sight to behold!";
    const auto source_file = fuzzypp::tests::create_temp_file("preprocess/7/source.cpp", source_content);
    const auto output_dir = std::filesystem::temp_directory_path() /= std::filesystem::path { "preprocess/7/output" }.make_preferred();

    fuzzypp::cliopts::CliOptions opts {
        std::vector<std::filesystem::path> { source_file },
        std::vector<std::filesystem::path> {},
        std::vector<std::filesystem::path> {},
        std::vector<std::string> {},
        std::vector<std::string> {},
        output_dir,
        false
    };

    fuzzypp::preprocessor::Preprocessor::preprocess(opts);

    const auto expected = "\n"
                          "int main ( ) { } // A sight to behold!";
    auto result_path = std::filesystem::path { output_dir } /= source_file.relative_path();
    auto result = fuzzypp::tests::read_file_content(result_path);
    
    REQUIRE(result == expected);
}

TEST_CASE("8: Elide any include directives for which a header can not be found in the included paths", "preprocess") {
    const auto header_content = "int x = 5;";
    const auto source_content = "#include <header.hpp>\n"
                                "int main ( ) { return x; } // A sight to behold!";

    auto header_file = fuzzypp::tests::create_temp_file("preprocess/8/other/header.hpp", header_content);
    auto source_file = fuzzypp::tests::create_temp_file("preprocess/8/source.cpp", source_content);
    const auto output_dir = std::filesystem::temp_directory_path() /= std::filesystem::path { "preprocess/8/output" }.make_preferred();

    fuzzypp::cliopts::CliOptions opts {
        std::vector<std::filesystem::path> { source_file },
        std::vector<std::filesystem::path> {},
        std::vector<std::filesystem::path> {},
        std::vector<std::string> {},
        std::vector<std::string> {},
        output_dir,
        false
    };

    fuzzypp::preprocessor::Preprocessor::preprocess(opts);

    const auto expected = "\n"
                          "int main ( ) { return x ; } // A sight to behold!";
    auto result_path = std::filesystem::path { output_dir } /= source_file.relative_path();
    auto result = fuzzypp::tests::read_file_content(result_path);

    REQUIRE(result == expected);
}

TEST_CASE("9: Elide any defines which are also specified in the list of undefines", "preprocess") { 
    const auto source_content = "int main() {\n" 
                                "#ifdef CAKE\n"
                                "  return 0;\n"
                                "#else\n"
                                "  return 1;\n"
                                "#endif\n"
                                "} // A sight to behold!";
    auto source_file = fuzzypp::tests::create_temp_file("preprocess/9/source.cpp", source_content);
    const auto output_dir = std::filesystem::temp_directory_path() /= std::filesystem::path { "preprocess/9/output" }.make_preferred();

    fuzzypp::cliopts::CliOptions opts {
        std::vector<std::filesystem::path> { source_file },
        std::vector<std::filesystem::path> {},
        std::vector<std::filesystem::path> {},
        std::vector<std::string> { "CAKE" },
        std::vector<std::string> { "CAKE" },
        output_dir,
        false
    };

    fuzzypp::preprocessor::Preprocessor::preprocess(opts);

    const auto expected = "int main ( ) {\n"
                          "\n"
                          "\n"
                          "\n"
                          "return 1 ;\n"
                          "\n"
                          "} // A sight to behold!";
    auto result_path = std::filesystem::path { output_dir } /= source_file.relative_path();
    auto result = fuzzypp::tests::read_file_content(result_path);

    REQUIRE(result == expected);
}

TEST_CASE("10: Correctly handle paths containing .", "preprocess") {
// TODO: remove the early return after the CI temp file path issue has been resolved
#ifdef _WIN32
  return;
#endif

    const auto header_content = "int x = 5;";
    const auto source_content = "#include <header.hpp>\n"
                                "int main ( ) { return x; } // A sight to behold!";

    auto header_file = fuzzypp::tests::create_temp_file("preprocess/10/./other/header.hpp", header_content);
    auto source_file = fuzzypp::tests::create_temp_file("preprocess/10/source.cpp", source_content);
    const auto output_dir = std::filesystem::temp_directory_path() /= std::filesystem::path { "preprocess/10/output" }.make_preferred();

    fuzzypp::cliopts::CliOptions opts {
        std::vector<std::filesystem::path> { source_file },
        std::vector<std::filesystem::path> {},
        std::vector<std::filesystem::path> { header_file.parent_path() },
        std::vector<std::string> {},
        std::vector<std::string> {},
        output_dir,
        false
    };

    fuzzypp::preprocessor::Preprocessor::preprocess(opts);

    auto header_file_str = header_file.lexically_normal().string();
// SimpleCPP for whatever reason converts header paths into Unix form when printing #line directives...
#ifdef _WIN32
    std::replace(header_file_str.begin(), header_file_str.end(), '\\', '/');   
#endif

    const auto expected = "\n"
                          "#line 1 \"" + header_file_str + "\"\n" //
                          "int x = 5 ;\n"
                          "#line 2 \"" + source_file.string() + "\"\n"
                          "int main ( ) { return x ; } // A sight to behold!";
    auto result_path = std::filesystem::path { output_dir } /= source_file.relative_path();
    auto result = fuzzypp::tests::read_file_content(result_path);

    REQUIRE(result == expected);
}

TEST_CASE("11: Defines from headers are correctly imported", "preprocess") {
    const auto header_content = "#define CAKE 5";
    const auto source_content = "#include \"source.hpp\"\n"
                                "int main ( ) { return CAKE; } // A sight to behold!";

    auto header_file = fuzzypp::tests::create_temp_file("preprocess/11/source.hpp", header_content);
    auto source_file = fuzzypp::tests::create_temp_file("preprocess/11/source.cpp", source_content);
    const auto output_dir = std::filesystem::temp_directory_path() /= std::filesystem::path { "preprocess/11/output" }.make_preferred();

    fuzzypp::cliopts::CliOptions opts {
        std::vector<std::filesystem::path> { source_file },
        std::vector<std::filesystem::path> { header_file },
        std::vector<std::filesystem::path> {},
        std::vector<std::string> {},
        std::vector<std::string> {},
        output_dir,
        false
    };

    fuzzypp::preprocessor::Preprocessor::preprocess(opts);

    const auto expected = "\n"
                          "int main ( ) { return 5 ; } // A sight to behold!";
    auto result_path = std::filesystem::path { output_dir } /= source_file.relative_path();
    auto result = fuzzypp::tests::read_file_content(result_path);

    REQUIRE(result == expected);
}
