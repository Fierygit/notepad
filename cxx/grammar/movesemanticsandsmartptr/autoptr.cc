#include <iostream>

// autoptr 会引发多次删除的问题

// Same as above
template <class T>
class Auto_ptr1 {
  T* m_ptr;

 public:
  Auto_ptr1(T* ptr = nullptr) : m_ptr(ptr) {}

  ~Auto_ptr1() { delete m_ptr; }

  T& operator*() const { return *m_ptr; }
  T* operator->() const { return m_ptr; }
  Auto_ptr1& operator=(Auto_ptr1& a)  // note: not const
  {
    if (&a == this) return *this;

    delete m_ptr;     // make sure we deallocate any pointer the destination is
                      // already holding first
    m_ptr = a.m_ptr;  // then transfer our dumb pointer from the source to the
                      // local object
    a.m_ptr = nullptr;  // make sure the source no longer owns the pointer
    return *this;
  }
};

class Resource {
 public:
  Resource() { std::cout << "Resource acquired\n"; }
  ~Resource() { std::cout << "Resource destroyed\n"; }
};

// auto 不能记录引用次数
int main() {
  {
    Auto_ptr1<Resource> res1(new Resource);
    // Auto_ptr1<Resource> res2(res1);  
    // Alternatively, don't initialize res2
    // and
    // then assign res2 = res1;
  }
  std::cout << " test over !!!" << std::endl;

  return 0;
}
//如果我们构造一个对象或进行参数为左值的赋值，那么我们唯一可以合理做的就是复制左值。
// 我们不能假设更改l值是安全的，因为它可能在程序的后面再次使用。
// 如果我们有一个表达式“ a = b”，我们就不会合理地期望b会以任何方式改变。

// 但是，如果我们构造一个对象或做一个参数为r值的赋值，那么我们知道r值只是某种临时对象。
// 除了复制它（可能很昂贵）之外，我们还可以简单地将其资源（便宜）转移到我们正在构造或分配的对象上。
// 之所以这样做是安全的，因为无论如何该临时变量都将在表达式的末尾销毁，因此我们知道它将永远不会被再次使用！