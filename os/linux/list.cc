/*
 * @Author: Firefly
 * @Date: 2021-03-22 10:36:05
 * @Descripttion:
 * @LastEditTime: 2021-03-22 11:31:22
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

void test_struct0() {
  struct test {
    int a;
    short b;
  } t;
  printf("%p\n", &((struct test *)100)->b);  // 104   0x68
  printf("%p\n", &((struct test *)0)->b);    // 4     0x4
}

void test_list() {
  struct my_list {
    int a;
    short b;
    double c;
    list_head list;
  };

  list_head *head;

  auto add = [&](int a, short, double c) {
    my_list *l = (my_list *)malloc(sizeof(my_list));
    l->a = 1, l->b = 2;
    l->c = 3.0;
    *l->list.next = l->list;  // l->list.next = &(l->list);
    if (!head)
      *head = l->list;
    else {
      *head->prev->next = l->list;
      l->list.prev = head->prev;
      *head->prev = l->list;
      l->list.next = head;
    }
  };
  add(1, 2, 3);
}

int main(void) { test_struct0(); }