#include <iostream>
class Base {
 protected:
  int m_value{};

 public:
  Base(int value) : m_value{value} {}
  virtual const char* getName() const { return "Base"; }
  int getValue() const { return m_value; }
};

class Derived : public Base {
 public:
  Derived(int value) : Base{value} {}
  virtual const char* getName() const { return "Derived"; }
};

void printName(const Base base)  // note: base passed by value, not reference
{
  std::cout << "I am a " << base.getName() << '\n';
}

int main() {

  Derived derived{5};
  Base base{derived};  // what happens here?
  std::cout << "base is a " << base.getName() << " and has value "
            << base.getValue() << '\n';



  return 0;
}