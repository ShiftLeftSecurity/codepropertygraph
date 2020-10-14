cmake_minimum_required(VERSION 3.10)

project(fuzzyppcli VERSION 0.0.1 LANGUAGES CXX)

# Set compile options. 
if (CMAKE_CXX_COMPILER_ID MATCHES "MSVC")
    add_compile_options(/O2
                        /utf-8
                        /MT)
    add_link_options(/INCREMENTAL:NO
                     /NODEFAULTLIB:MSVCRT)
endif()

if (CMAKE_CXX_COMPILER_ID MATCHES "GNU")
    add_compile_options(-O3
                        -Wall
                        -Wextra
                        -Werror
                        -Wwrite-strings
                        -Winit-self
                        -Wcast-align
                        -Wcast-qual
                        -Wpointer-arith
                        -Wstrict-aliasing
                        -Wshadow
                        -Wformat=2
                        -Wmissing-declarations
                        -Wmissing-include-dirs
                        -Wno-unused-parameter
                        -Wuninitialized
                        -ansi
                        -pedantic)
endif()

if (CMAKE_CXX_COMPILER_ID MATCHES "Clang")
    add_compile_options(-O3
                        -Wall
                        -Werror)
endif()

# Initialise submodules if necessary.
find_package(Git QUIET)
if(GIT_FOUND AND EXISTS "${PROJECT_SOURCE_DIR}/.git")
    option(GIT_SUBMODULE "Check submodules during build" ON)
    if(GIT_SUBMODULE)
        message(STATUS "Submodule update")
        execute_process(COMMAND ${GIT_EXECUTABLE} submodule update --init --recursive
                        WORKING_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}
                        RESULT_VARIABLE GIT_SUBMOD_RESULT)
        if(NOT GIT_SUBMOD_RESULT EQUAL "0")
            message(FATAL_ERROR "git submodule update --init failed with ${GIT_SUBMOD_RESULT}, please checkout submodules")
        endif()
    endif()
endif()

# Set fuzzypp library name for use in CLI & tests.
set(FUZZYPP_LIB_NAME "${CMAKE_PROJECT_NAME}-dev")
set(FUZZYPP_CLI_NAME ${CMAKE_PROJECT_NAME})
set(FUZZYPP_TEST_NAME "${CMAKE_PROJECT_NAME}-tests")

add_subdirectory(src)
enable_testing()
add_subdirectory(test)
