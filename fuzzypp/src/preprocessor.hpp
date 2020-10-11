#ifndef FUZZYPP_PREPROCESSOR_HPP
#define FUZZYPP_PREPROCESSOR_HPP

#include <algorithm>
#include <filesystem>
#include <list>
#include <string>

#include <simplecpp.h>

#include "cliopts.hpp"

namespace fuzzypp::preprocessor {
    class Preprocessor {
        public:
            static void preprocess(const fuzzypp::cliopts::CliOptions& options);

        private:
            static const simplecpp::DUI
            generate_simplecpp_opts(const fuzzypp::cliopts::CliOptions& options);

            static const std::string 
            stringify(const std::filesystem::path& file_path, const fuzzypp::cliopts::CliOptions& options);

            static void
            print_preprocessor_errors(const simplecpp::OutputList& output_list);

            inline static std::list<std::string>
            to_string_list(const std::vector<std::filesystem::path>& path_vector) {
                std::list<std::string> string_list;
                
                std::transform(path_vector.cbegin(), path_vector.cend(), std::back_inserter(string_list),
                               [](const auto& path) { return path.string(); });
                
                return string_list;
            }
    };
}

#endif
