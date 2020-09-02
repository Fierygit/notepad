#include <iostream>
using namespace std;

struct list {
 public:
  list() {
    header = new Node();
    header->next = header;
  }
  list(int val) : list() { insert(val); }

  struct Node {
    int val;
    Node* next;
    Node() {}
    Node(int val) : val(val) {}
  } * header;

  struct ite {
    Node* cur;
    ite(Node* header) { cur = header; }
    void operator++(int) { cur = cur->next; }
    int operator*() { return cur->val; }
    bool operator==(ite a) { return a.cur = cur; }
  };
  friend bool operator!=(ite a, ite b) { return a.cur != b.cur; }

  void insert(int val) {
    if (header->next == header) {
      header->next = new Node(val);
      header->next->next = header;
    } else {
      header->val = val;
      Node* tmp = header->next;  // 头节点
      header->next = new Node();
      header = header->next;
      header->next = tmp;
    }
  }

  ite begin() { return ite(header->next); }
  ite end() { return ite(header); }
};

int main() {
  list l;

  l.insert(10);
  l.insert(20);
  l.insert(30);
  auto i = l.begin();
  while (i != l.end()) {
    cout << *i << endl;
    i++;
  }
}