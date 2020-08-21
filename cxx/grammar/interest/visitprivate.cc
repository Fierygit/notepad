#include <cassert>
#include <iostream>


class A {
 public:
  int X() { return x_; }

 private:
  int x_;
};

int A::*FiledPtr(); // 函数指针

template <int A::*M>  // 定义一个结构体， 无状态
struct Rob {          // 内部类可以访问外部类
  friend int A::*FiledPtr() { return M; }
};

template struct Rob<&A::x_>;  // 生命一个结构体

// Rob<&A::x_> r;
// TODO fuck
int main() {
  A o;
  o.*FiledPtr() = 10;
  assert(o.X() == 10);
}