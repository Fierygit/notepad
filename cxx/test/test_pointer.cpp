/*
 * @Author: Firefly
 * @Date: 2020-10-15 16:21:10
 * @Descripttion:
 * @LastEditTime: 2020-10-15 17:25:16
 */
#include <iostream>
using namespace std;
struct Model {
  char a;
  int b;
};
int main() {
  void* p = malloc(8);
  Model* test = reinterpret_cast<Model*>(p);
  test->a = 'a';
  test->b = 2;
  cout << test->a << " " << test->b << endl;
  test++;
  test->a = 'b';
  test->b = 3;
  cout << test->a << " " << test->b << endl;
  typedef char byte;
  byte* i = reinterpret_cast<byte*>(p);
//   i += sizeof(Model);
  for (int j = 0; j < 10; j++, i++) cout << (int)(*i) << " ";
  return 0;
}