#include <iostream>

template <typename IterT>
struct my_iterator_traits {
  typedef typename IterT::value_type value_type; // 萃取类的内部信息 
};
// 偏特化
template <typename IterT>
struct my_iterator_traits<IterT*> {
  typedef IterT value_type;
};

template <typename T>
class vector {
 public:
  class iterator {
   public:
    typedef T value_type;
  };
  typedef T value_type;
};

void fun(int a) { std::cout << "fun(int) is called" << std::endl; }
void fun(double a) { std::cout << "fun(double) is called" << std::endl; }
void fun(char a) { std::cout << "fun(char) is called" << std::endl; }

int main() {
  my_iterator_traits<vector<int>>::value_type a; // 萃取出int 类型
  fun(a);  // 输出 fun(int) is called
  my_iterator_traits<vector<double>::iterator>::value_type b;
  fun(b);  // 输出 fun(double) is called
  my_iterator_traits<char*>::value_type c;
  fun(c);  // 输出 fun(char) is called
}