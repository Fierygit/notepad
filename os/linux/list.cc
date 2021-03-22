/*
 * @Author: Firefly
 * @Date: 2021-03-22 10:36:05
 * @Descripttion:
 * @LastEditTime: 2021-03-22 12:56:24
 */
#include <stdio.h>

#include <cstdlib>
#define offsetof(TYPE, MEMBER) ((size_t) & ((TYPE *)0)->MEMBER)

#define container_of(ptr, type, member)                \
  ({                                                   \
    const typeof(((type *)0)->member) *__mptr =        \
        (const typeof(((type *)0)->member) *)(ptr);    \
    (type *)((char *)__mptr - offsetof(type, member)); \
  })
//  ({})    表达式返回最后一个表达式的值。
//  typeof  获取变量的类型 int a = 6;  typeof(a) b = 9;
//   (struct st*)0 的作用    把结构体放到 0 地址开始的地方，

struct list_head {
  list_head *next;
  list_head *prev;
};
void list_add(list_head *add, list_head *head) {
  if (head == add) {
    head = add;
    head->prev = head， head->next = head;
  } else {
    head->prev->next = add;
    add->prev = head->prev;
    head->prev = add;
    add->next = head;
  }
}
#define list_for_each(pos, head) \
  for (pos = (head)->next; pos != (head); pos = pos->next)

void test_struct0() {
  struct test {
    int a;
    short b;
  } t;
  printf("%p\n", &((struct test *)100)->b);  // 104   0x68
  printf("%p\n", &((struct test *)0)->b);    // 4     0x4
}
#include <list>
#include <vector>
void test_list() {
  struct my_list {
    int a;
    short b;
    double c;
    list_head list;
  };
  list_head *head = nullptr;
  auto add = [&](int a, short b, double c) {
    my_list *l = (my_list *)malloc(sizeof(my_list));
    l->a = a, l->b = b, l->c = c;
    if (!head) head = &l->list;
    list_add(&l->list, head);  // head = &l->list
  };
  add(1, 2, 3);
  add(4, 5, 6);
  list_head *pos;
  printf("%p\n", head);
  list_for_each(pos, head) {
    auto one = container_of(pos, my_list, list);
    printf("%d\t%d\t%d\n", one->a, one->b,
           one->c);  // 头节点也存数据了。。没访问到。。。。
  }
  // c++ 泛型
  std::vector<my_list> vv;
  std::list<my_list> ll;
}

int main(void) {
  test_struct0();
  test_list();
}