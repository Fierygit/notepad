#include <iostream>
// note
//  作用等同于 unique_ptr， 一个 智能指针只管理一个 指针， 注意两个 delete 函数

// 使用的时候只能 move 左值


template <class T>
class Auto_ptr5 {
  T* m_ptr;

 public:
  Auto_ptr5(T* ptr = nullptr) : m_ptr(ptr) {}

  ~Auto_ptr5() { delete m_ptr; }

  // Copy constructor -- no copying allowed!
  Auto_ptr5(const Auto_ptr5& a) = delete;

  // Move constructor
  // Transfer ownership of a.m_ptr to m_ptr
  Auto_ptr5(Auto_ptr5&& a) : m_ptr(a.m_ptr) { a.m_ptr = nullptr; }

  // Copy assignment -- no copying allowed!
  Auto_ptr5& operator=(const Auto_ptr5& a) = delete;

  // Move assignment
  // Transfer ownership of a.m_ptr to m_ptr
  Auto_ptr5& operator=(Auto_ptr5&& a) {
    // Self-assignment detection
    if (&a == this) return *this;

    // Release any resource we're holding
    delete m_ptr;

    // Transfer ownership of a.m_ptr to m_ptr
    m_ptr = a.m_ptr;
    a.m_ptr = nullptr;

    return *this;
  }

  T& operator*() const { return *m_ptr; }
  T* operator->() const { return m_ptr; }
  bool isNull() const { return m_ptr == nullptr; }
};

