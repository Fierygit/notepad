#include <iostream>
template <class T>

// 没有左值， 右值得得定义， 下面代码会多一次，拷贝， 可以运行查看


class Auto_ptr3 {
  T* m_ptr;

 public:
  Auto_ptr3(T* ptr = nullptr) : m_ptr(ptr) {}

  ~Auto_ptr3() { delete m_ptr; }

  // Copy constructor
  // Do deep copy of a.m_ptr to m_ptr
  Auto_ptr3(const Auto_ptr3& a) {
    m_ptr = new T;
    *m_ptr = *a.m_ptr;
  }

  // Copy assignment
  // Do deep copy of a.m_ptr to m_ptr
  Auto_ptr3& operator=(const Auto_ptr3& a) {
    // Self-assignment detection
    if (&a == this) return *this;

    // Release any resource we're holding
    delete m_ptr;
    std::cout << "here is copy" << std::endl;
    // Copy the resource
    m_ptr = new T;
    *m_ptr = *a.m_ptr;

    return *this;
  }

  T& operator*() const { return *m_ptr; }
  T* operator->() const { return m_ptr; }
  bool isNull() const { return m_ptr == nullptr; }
};

class Resource {
 public:
  Resource() { std::cout << "Resource acquired\n"; }
  ~Resource() { std::cout << "Resource destroyed\n"; }
};

Auto_ptr3<Resource> generateResource() {
  Auto_ptr3<Resource> res(new Resource);
  return res;  // this return value will invoke the copy constructor
}

int main() {
  Auto_ptr3<Resource> mainres;
  mainres =
      generateResource();  //note this assignment will invoke the copy assignment

  return 0;
}