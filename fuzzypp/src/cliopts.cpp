#include <iostream>

#include "cliopts.hpp"

namespace fuzzypp::cliopts {
    const std::optional<CliOptions> 
    CliOptions::parse_command_line(int argc, char* argv[]) {
        try {
            cxxopts::Options options { argv[0], " - options" };
            options
                .positional_help("[optional args]")
                .show_positional_help();
            
            options
                .allow_unrecognised_options()
                .add_options()
                ("f,file", "A set of input source files (.c/.cpp).", cxxopts::value<std::vector<std::string>>(), "FILE")
                ("o,output", "A directory the output should be written to", cxxopts::value<std::string>(), "FILE")
                ("I", "A set of paths to include on the header search path", cxxopts::value<std::vector<std::string>>(), "DIR")
                ("include", "A set of files to include on the header search path", cxxopts::value<std::vector<std::string>>(), "FILE")
                ("D,define", "A set of defined values", cxxopts::value<std::vector<std::string>>(), "NAME[=VALUE]")
                ("U,undefine", "A set of undefined values", cxxopts::value<std::vector<std::string>>(), "NAME")
                ("verbose", "Print additional diagnostic information")
                ("help", "Print help.");

            auto result = options.parse(argc, argv);

            if (result.count("help")) {
                std::cout << options.help() << std::endl;
            }

            auto files = extract_paths(result, "file");
            auto i_paths = extract_paths(result, "I");
            auto i_files = extract_paths(result, "include");
            auto defs = extract_elements(result, "define");
            auto undefs = extract_elements(result, "undefine");
            auto output_directory = result.count("output") ?
                result["output"].as<std::string>() : "";
            auto verbose = result.count("verbose") > 0;

            auto normalised_output_directory = 
                std::filesystem::path { output_directory }.make_preferred();

            return std::optional<CliOptions> {
                CliOptions {
                    files,
                    i_files,
                    i_paths,
                    defs,
                    undefs,
                    normalised_output_directory,
                    verbose
                }
            };
        } catch (const cxxopts::OptionException& e) {
            std::cerr << "Error occured whilst parsing command line arguments: " << e.what() << std::endl;
            return std::optional<CliOptions> {};
        }
    }

    const std::optional<std::string>
    CliOptions::validate_options() const {
        if (files.empty()) {
            return std::optional<std::string> { "At least one input file must be specified." };
        }

        if (output_directory.empty()) {
            return std::optional<std::string> { "The output directory must be specified." };
        }

        if (std::filesystem::exists(output_directory) && !std::filesystem::is_directory(output_directory)) {
            return std::optional<std::string> { "The specified output directory must be a directory." };
        }

        for (const auto& file : files) {
            if (!is_path_valid(file)) {
                return std::optional<std::string> { "File paths must not contain '..'." };
            }
            if (!std::filesystem::is_regular_file(file)) {
                return std::optional<std::string> { "File [" + file.string() + "] does not exist." };
            }
        }

        for (const auto& i_file : include_files) {
            if (!is_path_valid(i_file)) {
                return std::optional<std::string> { "Include file paths must not contain '..'." };
            }
            if (!std::filesystem::is_regular_file(i_file)) {
                return std::optional<std::string> { "Include file [" + i_file.string() + "] does not exist." };
            }
        }

        for (const auto& i_path : include_paths) {
            if (!is_path_valid(i_path)) {
                return std::optional<std::string> { "Include paths must not contain '..'." };
            }
            if (!std::filesystem::is_directory(i_path)) {
                return std::optional<std::string> { "Include path [" + i_path.string() + "] does not exist." };
            }
        }

        return std::optional<std::string> {};
    }

    std::ostream& 
    operator<<(std::ostream& out, const CliOptions& opts) {
        out << "Command line arguments:" << std::endl;
        out << "=======================" << std::endl;

        out << "Output directory: " << opts.output_directory << std::endl;
        
        out << "Source files:" << std::endl;
        for (const auto& src_file : opts.files) {
            out << "=> " << src_file << std::endl;
        }
        out << std::endl;

        out << "Include files:" << std::endl;
        for (const auto& incl_file : opts.include_files) {
            out << "=> " << incl_file << std::endl;
        }
        out << std::endl;

        out << "Include paths:" << std::endl;
        for (const auto& incl_path : opts.include_paths) {
            out << "=> " << incl_path << std::endl;
        }
        out << std::endl;

        out << "Defines:" << std::endl;
        for (const auto& define : opts.defines) {
            out << "=> " << define << std::endl;
        }
        out << std::endl;

        out << "Undefines:" << std::endl;
        for (const auto& undefine : opts.undefines) {
            out << "=> " << undefine << std::endl;
        }
        out << std::endl;

        return out;
    }
}
