#include "test_utils.hpp"

#include <fstream>
#include <sstream>

namespace fuzzypp::tests {
    const std::filesystem::path
    create_temp_file(const std::string& file_name, const std::string& content) {
        const std::filesystem::path file_path { file_name };
        const auto full_path = (std::filesystem::temp_directory_path() /= file_path).make_preferred();

        if (full_path.has_parent_path()) std::filesystem::create_directories(full_path.parent_path());

        std::ofstream output { full_path, std::ofstream::trunc };
        output << content;

        return full_path;
    }

    const std::string
    read_file_content(const std::filesystem::path& file_path) {
        std::ifstream input { file_path };
        std::stringstream ss;
        ss << input.rdbuf();
        return ss.str();
    }

    const std::string
    to_native_path(const std::string& path) {
        return std::filesystem::path { path }.make_preferred().string();
    }
}
