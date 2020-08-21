
#ifndef MYHEADER_H
#define MYHEADER_H

#include <unistd.h>

#include <chrono>
#include <deque>
#include <iostream>
#include <list>
#include <string>
#include <thread>

namespace firefly {  // 防止与math 冲突

#define min(a, b) (a) < (b) ? (a) : (b)

}  // namespace firefly

using Time = decltype(std::chrono::system_clock::now());
using Milliseconds = std::chrono::milliseconds;
using Seconds = std::chrono::seconds;
using int64_t = long int;

#endif