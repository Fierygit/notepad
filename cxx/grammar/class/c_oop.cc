#include <stdio.h>
#include <stdlib.h>

// 1、 封装： 使用 struct， 如果要用私有变量， 可以使用一个内部结构体再次封装， 外部就不能直接访问到了

// 2、 继承： 使用组合的方式， 将 base 类放在第一个变量， 后面多态有转换的作用

// 3、 多态： 使用操作内存的方式, 将子类强行转换为基类， 注意 parent 一定要写在子类的第一个变量， 
// 强转的时候，会截取子类的一部分内存，此时子类第一个就是父类， 就可以直接强转。




typedef struct _Parent {
  int a;
  int b;
  void (*print)(struct _Parent *This);
} Parent;

typedef struct _Child {
  Parent parent;
  int c;
} Child;

void print_parent(Parent *This) {
  printf("a = %d. b = %d.\n", This->a, This->b);
}

void print_child(Parent *This) {
  Child *p = (Child *)This;
  printf("a = %d. b = %d. c = %d.\n", p->parent.a, p->parent.b, p->c);
}

Parent *create_parent(int a, int b) {
  Parent *This;

  This = NULL;
  This = (Parent *)malloc(sizeof(Parent));
  if (This != NULL) {
    This->a = a;
    This->b = b;
    This->print = print_parent;
    printf("Create parent successfully!\n");
  }

  return This;
}

void destroy_parent(Parent **p) {
  if (*p != NULL) {
    free(*p);
    *p = NULL;
    printf("Delete parent successfully!\n");
  }
}

Child *create_child(int a, int b, int c) {
  Child *This;

  This = NULL;
  This = (Child *)malloc(sizeof(Child));
  if (This != NULL) {
    This->parent.a = a;
    This->parent.b = b;
    This->c = c;
    This->parent.print = print_child;
    printf("Create child successfully!\n");
  }

  return This;
}

void destroy_child(Child **p) {
  if (*p != NULL) {
    free(*p);
    *p = NULL;
    printf("Delete child successfully!\n");
  }
}

int main() {
  Child *p = create_child(1, 2, 3);
  Parent *q;

  /*Use parent pointer to point to child*/
  q = (Parent *)p;
  /*Be attention!*/
  /*Actually the child's print function is called!*/
  q->print(q);

  destroy_child(&p);
  return 0;
}