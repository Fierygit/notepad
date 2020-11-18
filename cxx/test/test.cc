/*
 * @Author: Firefly
 * @Date: 2020-11-07 10:49:54
 * @Descripttion:
 * @LastEditTime: 2020-11-07 12:13:58
 */
#define NDEBUG
#include <cassert>
#include <iostream>
using namespace std;

int x[10] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

int main() {
  int* ptr = new int[3];
  *ptr = 10;
  cout << ptr[0] << endl;
  ptr[0] = 10;
  cout << ptr[0] << endl;
}