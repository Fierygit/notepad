#include <iostream>
#include <string>

class A {
 public:
  virtual std::string getName() const { return "A"; }
};

class B : public A {
 public:
  virtual std::string getName() const { return "B"; }
};

class C : public B {
 public:
  // Note: no getName() function here
};

class D : public C {
 public:
  virtual std::string getName() const { return "D"; }
};

class T {
 public:
  static int a;
};
int T::a = 10;
class TT : T {
 public:
  void test() { std::cout << T::a << std::endl; }
};

int main() {
  C c;
  A &rBase{c};
  std::cout << rBase.getName() << '\n';

  TT tt;
  tt.test();
  T::a;

  return 0;
}
