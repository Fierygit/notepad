

#include <iostream>

int(add)(int x, int y) { return x + y; }  // add 方法可以加一个括号

int main() {
  // Create a function pointer and make it point to the add function
  int (&pFcn)(int, int) = add;
  int (*pF)(int, int) = pFcn;            // 注意和方法的区别
  std::cout << pFcn(5, 3) << std::endl;  // add 5 + 3

  return 0;
}


