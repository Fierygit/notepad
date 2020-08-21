
#include "util.h"



Time currentTime() { return std::chrono::system_clock::now(); }

std::time_t getTimeStamp() {
  auto now = currentTime().time_since_epoch();
  return std::chrono::duration_cast<Milliseconds>(now).count();
}

std::tm* gettm(uint64_t timestamp) {
  //转换时区，北京时间+8小时
  uint64_t milli = timestamp + (uint64_t)8 * 60 * 60 * 1000;
  auto mTime = Milliseconds(milli);
  auto tp =
      std::chrono::time_point<std::chrono::system_clock, Milliseconds>(mTime);
  auto tt = std::chrono::system_clock::to_time_t(tp);
  std::tm* now = std::gmtime(&tt);
  return now;
}

std::string getTimeStr() {
  time_t timep = getTimeStamp();
  struct tm* info;
  info = gettm(timep);
  char tmp[27] = {0};
  sprintf(tmp, "[%04d-%02d-%02d %02d:%02d:%02d %03ld]", info->tm_year + 1900,
          info->tm_mon + 1, info->tm_mday, info->tm_hour, info->tm_min,
          info->tm_sec, (timep % 10000) / 10);
  return tmp;
}