#include <sstream>

#include "../../util.h"


class RateLimter {
 public:
  // 注意不要创建临时对象
  RateLimter() : RateLimter::RateLimter(1) {}

  RateLimter(int frequency, int interval = 1)
      : frequency(frequency),
        interval(interval),
        msPer_(interval * 1000 / frequency),  // 先乘以 1000
        last(currentTime() - Seconds(interval)) {}

  bool getToken(int tokenNum = 1) {
    Time now = currentTime();
    Milliseconds left = std::chrono::duration_cast<Milliseconds>(now - last);
    //  剩下的秒数除以执行一次需要的时间
    int leftTokenNum = left.count() / msPer_;
    // 取小的， 不能大于桶的大小
    leftTokenNum = min(frequency, leftTokenNum);
    // 如果可以拿到 token
    if (leftTokenNum >= tokenNum) {
      leftTokenNum -= tokenNum;
      // 这里的 now 要不要重新获取
      last = now - Milliseconds(leftTokenNum * msPer_);
      return true;
    } else {
      return false;
    }
  }

 private:
  Time last;
  int frequency;  // 执行次数
  int interval;   // seconds
  int msPer_;     // 每一次的间隔
};

int main() {
  RateLimter limter(5);

  bool stop{false};
  std::thread([&stop]() {
    std::this_thread::sleep_for(Seconds(10));
    stop = true;
  }).detach();

  int cnt{0};
  while (!stop) {
    while (!limter.getToken()) std::this_thread::sleep_for(Milliseconds(20));
    std::cout << getTimeStr() << " get token!\n";
    if (cnt++ == 10) {
      std::cout << getTimeStr() << " sleep for 2 seconds\n";
      std::this_thread::sleep_for(Seconds(2));
    }
  }
}