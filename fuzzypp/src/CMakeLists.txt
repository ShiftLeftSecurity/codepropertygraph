# Build static library for use by CLI & tests.
add_library(${FUZZYPP_LIB_NAME} STATIC
            cliopts.cpp
            preprocessor.cpp
            "${CMAKE_SOURCE_DIR}/external/simplecpp/simplecpp.cpp")

target_include_directories(${FUZZYPP_LIB_NAME} PUBLIC
                           "${CMAKE_SOURCE_DIR}/external/cxxopts/include"
                           "${CMAKE_SOURCE_DIR}/external/simplecpp")

set_target_properties(${FUZZYPP_LIB_NAME} PROPERTIES
                      CXX_STANDARD 17
                      CXX_STANDARD_REQUIRED ON
                      CXX_EXTENSIONS OFF
                      ARCHIVE_OUTPUT_DIRECTORY "${CMAKE_SOURCE_DIR}/lib")

if (NOT CMAKE_CXX_COMPILER_ID MATCHES "MSVC")
    target_link_libraries(${FUZZYPP_LIB_NAME} stdc++fs -static)
endif()


# Build CLI.
add_executable(${FUZZYPP_CLI_NAME}
               main.cpp)

set_target_properties(${FUZZYPP_CLI_NAME} PROPERTIES
                      CXX_STANDARD 17
                      CXX_STANDARD_REQUIRED ON
                      CXX_EXTENSIONS OFF
                      RUNTIME_OUTPUT_DIRECTORY "${CMAKE_SOURCE_DIR}/bin")

if (CMAKE_CXX_COMPILER_ID MATCHES "MSVC")
    target_link_libraries(${FUZZYPP_CLI_NAME} ${FUZZYPP_LIB_NAME})
else()
    target_link_libraries(${FUZZYPP_CLI_NAME} ${FUZZYPP_LIB_NAME} stdc++fs -static)
endif()

