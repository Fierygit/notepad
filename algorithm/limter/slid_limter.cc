#include "../util.h"

class RateLimter {
 public:
  // 注意不要创建临时对象
  RateLimter() : RateLimter::RateLimter(1) {}

  RateLimter(int frequency, int interval = 1)
      : frequency(frequency), interval(interval) {}

  bool getToken(int tokenNum = 1) {
    if (queue.size() < frequency) {
      queue.push_back(currentTime());
      return true;
    } else {
      while (!queue.empty()) {
        auto intervalTmp = currentTime() - queue.front();
        Milliseconds lastTimeInterval =
            std::chrono::duration_cast<Milliseconds>(intervalTmp);
        if (lastTimeInterval.count() < interval * 1000) break;
        queue.pop_front();
      }
      if (queue.size() < frequency) {
        queue.push_back(currentTime());
        return true;
      } else
        return false;
    }
  }

 private:
  int frequency;  // 执行次数
  int interval;   // seconds
  std::deque<Time> queue;
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