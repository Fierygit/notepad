#include <iostream>
#include <typeinfo>

typedef void (*test)(int);

void t(int a) { std::cout << a << std::endl; }

int main() {
  test();

  std::cout << "hello world " << std::endl;

  test tt;
  tt = t;
  tt(3);

  if (1 <= 2 && true || 3 <= 4 | 5 >= 6 & (7 != 8))
    std::cout << "firefly love coding..." << std::endl;

  return 0;
}