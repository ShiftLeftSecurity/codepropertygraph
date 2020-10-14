# Build tests.
add_executable(${FUZZYPP_TEST_NAME}
               main.cpp
               cliopts_test.cpp
               preprocessor_test.cpp
               test_utils.cpp)

add_dependencies(${FUZZYPP_TEST_NAME} ${FUZZYPP_LIB_NAME})

target_include_directories(${FUZZYPP_TEST_NAME} PRIVATE
                           "${CMAKE_SOURCE_DIR}/external/catch2/single_include/catch2"
                           "${CMAKE_SOURCE_DIR}/src")

set_target_properties(${FUZZYPP_TEST_NAME} PROPERTIES
                      CXX_STANDARD 17
                      CXX_STANDARD_REQUIRED ON
                      CXX_EXTENSIONS OFF
                      RUNTIME_OUTPUT_DIRECTORY "${CMAKE_SOURCE_DIR}/bin")

if (CMAKE_CXX_COMPILER_ID MATCHES "MSVC")
    target_link_libraries(${FUZZYPP_TEST_NAME} ${FUZZYPP_LIB_NAME})
else()
    target_link_libraries(${FUZZYPP_TEST_NAME} ${FUZZYPP_LIB_NAME} stdc++fs -static)
endif()

add_test(NAME fuzzyppcli_tests COMMAND ${FUZZYPP_TEST_NAME})
