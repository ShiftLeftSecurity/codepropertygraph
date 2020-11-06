#include <catch.hpp>

#include <algorithm>
#include <vector>

#include <cliopts.hpp>

#include "test_utils.hpp"

TEST_CASE("1: Parse command line arguments", "cli-parse") { 
    const int argc = 20;
    const char* argv[] = {
        "prog-name",
        "-f", "/path/to/a/file",
        "--file", "/path/to/another/file",
        "-o", "/path/to/output",
        "-I", "/path/to/include",
        "--include", "/file/to/include",
        "-D", "SHORT_DEF",
        "--define", "LONG_DEF",
        "-U", "SHORT_UDEF",
        "--undefine", "LONG_UDEF",
        "--verbose"
    };

    auto result = fuzzypp::cliopts::CliOptions::parse_command_line(argc, const_cast<char**>(argv));

    REQUIRE(result.has_value());

    std::vector<std::string> expected_files { 
        fuzzypp::tests::to_native_path("/path/to/a/file"), 
        fuzzypp::tests::to_native_path("/path/to/another/file") 
    };
    REQUIRE(std::equal(result->files.cbegin(), result->files.cend(), expected_files.cbegin()));

    REQUIRE(result->output_directory == fuzzypp::tests::to_native_path("/path/to/output"));

    std::vector<std::string> expected_incl_path { fuzzypp::tests::to_native_path("/path/to/include") };
    REQUIRE(std::equal(result->include_paths.cbegin(), result->include_paths.cend(), expected_incl_path.cbegin()));

    std::vector<std::string> expected_incl_file { fuzzypp::tests::to_native_path("/file/to/include") };
    REQUIRE(std::equal(result->include_files.cbegin(), result->include_files.cend(), expected_incl_file.cbegin()));

    std::vector<std::string> expected_defs { "SHORT_DEF", "LONG_DEF" };
    REQUIRE(std::equal(result->defines.cbegin(), result->defines.cend(), expected_defs.cbegin()));

    std::vector<std::string> expected_udefs { "SHORT_UDEF", "LONG_UDEF" };
    REQUIRE(std::equal(result->undefines.cbegin(), result->undefines.cend(), expected_udefs.cbegin()));

    REQUIRE(result->verbose);
}

TEST_CASE("2: Fail to parse invalid arguments", "cli-parse") {
    const int argc = 2;
    const char* argv[] = {
        "prog-name",
        "-f"
    };

    auto result = fuzzypp::cliopts::CliOptions::parse_command_line(argc, const_cast<char**>(argv));

    REQUIRE_FALSE(result.has_value());
 }

TEST_CASE("3: Ignore unknown command line arguments",  "cli-parse") { 
    const int argc = 5;
    const char* argv[] = {
        "prog-name",
        "-f", "/path/to/a/file",
        "--unknown",
        "-z"
    };

    auto result = fuzzypp::cliopts::CliOptions::parse_command_line(argc, const_cast<char**>(argv));

    REQUIRE(result.has_value());

    std::vector<std::string> expected_files { fuzzypp::tests::to_native_path("/path/to/a/file") };
    REQUIRE(std::equal(result->files.cbegin(), result->files.cend(), expected_files.cbegin()));
}

TEST_CASE("1: Pass validation", "cli-validate") {
    auto source_file = fuzzypp::tests::create_temp_file("cli-validate/1/source.cpp");
    auto header_file = fuzzypp::tests::create_temp_file("cli-validate/1/source.hpp");
    auto other_header = fuzzypp::tests::create_temp_file("cli-validate/1/other/header.hpp");
    auto output_path = std::filesystem::temp_directory_path() /= std::filesystem::path { "cli-validate/1/output" };

    auto opts = fuzzypp::cliopts::CliOptions {
        std::vector<std::filesystem::path> { source_file },
        std::vector<std::filesystem::path> { header_file },
        std::vector<std::filesystem::path> { other_header.parent_path() },
        std::vector<std::string> { "DEF" },
        std::vector<std::string> { "UNDEF" },
        output_path,
        false
    };

    auto result = opts.validate_options();
    REQUIRE_FALSE(result.has_value());
}

TEST_CASE("2: Fail if an input file contains '..'", "cli-validate") {
    auto header_file = fuzzypp::tests::create_temp_file("cli-validate/2/source.hpp");
    auto other_header = fuzzypp::tests::create_temp_file("cli-validate/2/other/header.hpp");
    auto output_path = std::filesystem::temp_directory_path() /= std::filesystem::path { "cli-validate/2/output" };

    auto bad_input_file = std::filesystem::temp_directory_path() /= "cli-validate/2/..";

    auto opts = fuzzypp::cliopts::CliOptions {
        std::vector<std::filesystem::path> { bad_input_file },
        std::vector<std::filesystem::path> { header_file },
        std::vector<std::filesystem::path> { other_header.parent_path() },
        std::vector<std::string> { "DEF" },
        std::vector<std::string> { "UNDEF" },
        output_path,
        false
    };

    auto result = opts.validate_options();
    REQUIRE(result.has_value());
    REQUIRE(*result == "File paths must not contain '..'.");
}

TEST_CASE("3: Fail if no input files are specified", "cli-validate") { 
    auto opts = fuzzypp::cliopts::CliOptions {
        std::vector<std::filesystem::path> { },
        std::vector<std::filesystem::path> { },
        std::vector<std::filesystem::path> { },
        std::vector<std::string> { },
        std::vector<std::string> { },
        std::filesystem::path {},
        false
    };

    auto result = opts.validate_options();
    REQUIRE(result.has_value());
    REQUIRE(*result == "At least one input file must be specified.");
}

TEST_CASE("4: Fail if no output files are specified", "cli-validate") { 
    auto source_file = fuzzypp::tests::create_temp_file("cli-validate/4/source.cpp");
    
    auto opts = fuzzypp::cliopts::CliOptions {
        std::vector<std::filesystem::path> { source_file },
        std::vector<std::filesystem::path> { },
        std::vector<std::filesystem::path> { },
        std::vector<std::string> { },
        std::vector<std::string> { },
        std::filesystem::path {},
        false
    };

    auto result = opts.validate_options();
    REQUIRE(result.has_value());
    REQUIRE(*result == "The output directory must be specified.");
}

TEST_CASE("5: Fail if no output files are specified", "cli-validate") { 
    auto source_file = fuzzypp::tests::create_temp_file("cli-validate/5/source.cpp");
    auto lying_output_directory = fuzzypp::tests::create_temp_file("cli-validate/5/outdir");

    auto opts = fuzzypp::cliopts::CliOptions {
        std::vector<std::filesystem::path> { source_file },
        std::vector<std::filesystem::path> { },
        std::vector<std::filesystem::path> { },
        std::vector<std::string> { },
        std::vector<std::string> { },
        lying_output_directory,
        false
    };

    auto result = opts.validate_options();
    REQUIRE(result.has_value());
    REQUIRE(*result == "The specified output directory must be a directory.");
}

TEST_CASE("6: Fail if an input file does not exist", "cli-validate") {
    auto header_file = fuzzypp::tests::create_temp_file("cli-validate/6/source.hpp");
    auto other_header = fuzzypp::tests::create_temp_file("cli-validate/6/other/header.hpp");
    auto output_path = std::filesystem::temp_directory_path() /= std::filesystem::path { "cli-validate/6/output" };

    auto fake_input_file = std::filesystem::temp_directory_path() /= "cli-validate/6/source.cpp";

    auto opts = fuzzypp::cliopts::CliOptions {
        std::vector<std::filesystem::path> { fake_input_file },
        std::vector<std::filesystem::path> { header_file },
        std::vector<std::filesystem::path> { other_header.parent_path() },
        std::vector<std::string> { "DEF" },
        std::vector<std::string> { "UNDEF" },
        output_path,
        false
    };

    auto result = opts.validate_options();
    REQUIRE(result.has_value());
    REQUIRE(*result == "File [" + fake_input_file.string() + "] does not exist.");
}

TEST_CASE("7: Fail if an included file does not exist", "cli-validate") {
    auto source_file = fuzzypp::tests::create_temp_file("cli-validate/7/source.cpp");
    auto other_header = fuzzypp::tests::create_temp_file("cli-validate/7/other/header.hpp");
    auto output_path = std::filesystem::temp_directory_path() /= std::filesystem::path { "cli-validate/7/output" };

    auto fake_include_file = std::filesystem::temp_directory_path() /= "cli-validate/7/source.hpp";

    auto opts = fuzzypp::cliopts::CliOptions {
        std::vector<std::filesystem::path> { source_file },
        std::vector<std::filesystem::path> { fake_include_file },
        std::vector<std::filesystem::path> { other_header.parent_path() },
        std::vector<std::string> { "DEF" },
        std::vector<std::string> { "UNDEF" },
        output_path,
        false
    };

    auto result = opts.validate_options();
    REQUIRE(result.has_value());
    REQUIRE(*result == "Include file [" + fake_include_file.string() + "] does not exist.");
}

TEST_CASE("8: Fail if an included file contains '..'", "cli-validate") {
    auto source_file = fuzzypp::tests::create_temp_file("cli-validate/8/source.cpp");
    auto other_header = fuzzypp::tests::create_temp_file("cli-validate/8/other/header.hpp");
    auto output_path = std::filesystem::temp_directory_path() /= std::filesystem::path { "cli-validate/8/output" };

    auto bad_include_file = std::filesystem::temp_directory_path() /= "cli-validate/8/..";

    auto opts = fuzzypp::cliopts::CliOptions {
        std::vector<std::filesystem::path> { source_file },
        std::vector<std::filesystem::path> { bad_include_file },
        std::vector<std::filesystem::path> { other_header.parent_path() },
        std::vector<std::string> { "DEF" },
        std::vector<std::string> { "UNDEF" },
        output_path,
        false
    };

    auto result = opts.validate_options();
    REQUIRE(result.has_value());
    REQUIRE(*result == "Include file paths must not contain '..'.");
}

TEST_CASE("9: Fail if an included path does not exist", "cli-validate") { 
    auto source_file = fuzzypp::tests::create_temp_file("cli-validate/9/source.cpp");
    auto header_file = fuzzypp::tests::create_temp_file("cli-validate/9/source.hpp");
    auto output_path = std::filesystem::temp_directory_path() /= std::filesystem::path { "cli-validate/9/output" };

    auto fake_include_path = std::filesystem::temp_directory_path() /= "cli-validate/9/other";

    auto opts = fuzzypp::cliopts::CliOptions {
        std::vector<std::filesystem::path> { source_file },
        std::vector<std::filesystem::path> { header_file },
        std::vector<std::filesystem::path> { fake_include_path },
        std::vector<std::string> { "DEF" },
        std::vector<std::string> { "UNDEF" },
        output_path,
        false
    };

    auto result = opts.validate_options();
    REQUIRE(result.has_value());
    REQUIRE(*result == "Include path [" + fake_include_path.string() + "] does not exist.");
}

TEST_CASE("10: Fail if an included path contains '..'", "cli-validate") {
    auto source_file = fuzzypp::tests::create_temp_file("cli-validate/10/source.cpp");
    auto header_file = fuzzypp::tests::create_temp_file("cli-validate/10/source.hpp");
    auto other_header = fuzzypp::tests::create_temp_file("cli-validate/10/other/../header.hpp");
    auto output_path = std::filesystem::temp_directory_path() /= std::filesystem::path { "cli-validate/10/output" };

    auto opts = fuzzypp::cliopts::CliOptions {
        std::vector<std::filesystem::path> { source_file },
        std::vector<std::filesystem::path> { header_file },
        std::vector<std::filesystem::path> { other_header.parent_path() },
        std::vector<std::string> { "DEF" },
        std::vector<std::string> { "UNDEF" },
        output_path,
        false
    };

    auto result = opts.validate_options();
    REQUIRE(result.has_value());
    REQUIRE(*result == "Include paths must not contain '..'.");
}
