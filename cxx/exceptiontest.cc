/*
 * @Author: Firefly
 * @Date: 2021-01-26 11:50:10
 * @Descripttion:
 * @LastEditTime: 2021-01-26 11:50:34
 */
/*
 * @Author: Firefly
 * @Date: 2021-01-26 10:17:04
 * @Descripttion:
 * @LastEditTime: 2021-01-26 11:47:04
 */
#include <iostream>
using namespace std;
#include "exception.h"

int main() {
  try {
    throw MyException(MyException1);
  } catch (Exception e) {
    print_exception(e);
  }
  try {
    throw MyException(MyException2, "hello wolrd");
  } catch (Exception e) {
    print_exception(e);
  }
  try {
    throw MyException(MyException2, 1, "this is error code to std strerror ");
  } catch (Exception e) {
    print_exception(e);
  }
}
