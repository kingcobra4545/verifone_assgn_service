cmake_minimum_required(VERSION 3.10.2)

project("analyticsservice")

MESSAGE ("Source path: ${PROJECT_SOURCE_DIR}")
MESSAGE ("Binary path: ${PROJECT_BINARY_DIR}")

add_library(
        signature-verifier
        SHARED
        ${PROJECT_SOURCE_DIR}/src/main/cpp/Sign_Utils.cpp
)

find_library(
        log-lib
        log
)

target_link_libraries(
        signature-verifier
        ${log-lib}
)
