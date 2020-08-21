#include <iostream>
#include <string>
#include <utility>  // for std::move
#include <vector>

// 使用多次 move 可以加快效率

template <class T>
void myswap(T& a, T& b) {
  T tmp{std::move(a)};  // invokes move constructor
  a = std::move(b);     // invokes move assignment
  b = std::move(tmp);   // invokes move assignment
}
int main() {
  std::vector<std::string> v;
  std::string str = "Knock";

  std::cout << "Copying str-----------------\n";
  v.push_back(str);  // calls l-value version of push_back, which copies str
                     // into the array element

  std::cout << "str: " << str << '\n';
  std::cout << "vector: " << v[0] << '\n';

  std::cout << "\nMoving str-----------------\n";

  v.push_back(std::move(str));  // calls r-value version of push_back, which
                                // moves str into the array element

  std::cout << "str: " << str << '\n';
  std::cout << "vector:" << v[0] << ' ' << v[1] << '\n';

  return 0;
}