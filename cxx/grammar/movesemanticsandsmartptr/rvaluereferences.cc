#include <iostream>


// 理解什么是右值


void fun(const int &lref)  // l-value arguments will select this function
{
  std::cout << "l-value reference to const\n";
}

void fun(int &&rref)  // r-value arguments will select this function
{
  std::cout << "r-value reference\n";
}

int main() {
  int x{5};
  fun(x);  // l-value argument calls l-value version of function
  fun(5);  // r-value argument calls r-value version of function

  int &&ref{5};
  fun(ref);  // l-value argument calls l-value version of function

  // l-value references
  int &ref1{x};  // A
                 // int &ref2{5};  // B

  const int &ref3{x};  // C
  const int &ref4{5};  // D

  // r-value references
  // int &&ref5{x};  // E
  int &&ref6{5};  // F

  // const int &&ref7{x};  // G
  const int &&ref8{5};  // H

  return 0;
}