cmake_minimum_required(VERSION 3.20)
project(parser)

set(CMAKE_CXX_STANDARD 23)


add_executable(main main.cpp)
#add_executable(bba bba.cpp)
#add_executable(cse cse.cpp)
#add_executable(ece ece.cpp)

# sudo apt install sqlite
# sudo apt-get install libsqlite3-dev
target_link_libraries(main sqlite3)