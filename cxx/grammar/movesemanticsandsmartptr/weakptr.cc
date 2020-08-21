#include <iostream>
#include <memory>  // for std::shared_ptr and std::weak_ptr
#include <string>

// 我们不必担心std :: shared_ptr变量“ partner”的循环依赖，
//因为它只是函数内部的局部变量。它最终将在函数末尾超出范围，并且引用计数将减少1。


//std :: weak_ptr的缺点是std :: weak_ptr不能直接使用（它们没有operator->）。
// 要使用std :: weak_ptr，必须首先将其转换为std :: shared_ptr。
// 然后，您可以使用std :: shared_ptr。要将std :: weak_ptr转换为std :: shared_ptr，
// 可以使用lock（）成员函数

//weak_ptr是一个弱引用，只引用，不计数。
// 如果一块内存被shared_ptr和weak_ptr同时引用，当所有shared_ptr析构了之后，
// 不管还有没有weak_ptr引用该内存，内存也会被释放。所以weak_ptr不保证它指向的内存一定是有效的，
// 在使用之前需要检查weak_ptr是否为空指针。

class Person {
  std::string m_name;
  std::weak_ptr<Person> m_partner;  // note: This is now a std::weak_ptr

 public:
  Person(const std::string &name) : m_name(name) {
    std::cout << m_name << " created\n";
  }
  ~Person() { std::cout << m_name << " destroyed\n"; }

  friend bool partnerUp(std::shared_ptr<Person> &p1,
                        std::shared_ptr<Person> &p2) {
    if (!p1 || !p2) return false;

    p1->m_partner = p2;
    p2->m_partner = p1;

    std::cout << p1->m_name << " is now partnered with " << p2->m_name << "\n";

    return true;
  }// 离开时释放的时 share的一次引用计数， 仔细体会。。。。

  const std::shared_ptr<Person> getPartner() const {
    return m_partner.lock();
  }  // use lock() to convert weak_ptr to shared_ptr
  const std::string &getName() const { return m_name; }
};

int main() {
  auto lucy = std::make_shared<Person>("Lucy");
  auto ricky = std::make_shared<Person>("Ricky");

  partnerUp(lucy, ricky);

  auto partner = ricky->getPartner();  // get shared_ptr to Ricky's partner
  std::cout << ricky->getName() << "'s partner is: " << partner->getName()
            << '\n';

  return 0;
}