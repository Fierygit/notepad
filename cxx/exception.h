/*
 * @Author: Firefly
 * @Date: 2021-01-26 11:48:02
 * @Descripttion: 
 * @LastEditTime: 2021-01-26 11:49:46
 */
/*
 * @Author: Firefly
 * @Date: 2021-01-26 10:53:59
 * @Descripttion:
 * @LastEditTime: 2021-01-26 11:45:03
 */
#pragma once
#include <iostream>
using namespace std;

enum ExceptionType {
  MyException1 = 1,
  MyException2 = 2,
  MyException3 = 3,

};

struct Exception {
  // Which exception
  int num;
  const char *name;
  // Additional information
  std::string msg;
  int errno_val;
  // Where it was thrown
  const char *file;
  int line;
  Exception(int exc, const char *exc_name, const char *f, int l)
      : num(exc), name(exc_name), msg(), errno_val(0), file(f), line(l) {
    cout << "1int exc, const char *exc_name, const char *f, int l" << endl;
  }

  Exception(int exc, const char *exc_name, const string &m, const char *f,
            int l)
      : num(exc), name(exc_name), msg(m), errno_val(0), file(f), line(l) {
    cout << "2int exc, const char *exc_name, const string &m,const char *f, "
            "int l"
         << endl;
  }

  Exception(int exc, const char *exc_name, int err, const string &m,
            const char *f, int l)
      : num(exc), name(exc_name), msg(m), errno_val(err), file(f), line(l) {
    cout << "3int exc, const char *exc_name, int err, const string &m, "
            "  const char *f, int l"
         << endl;
  }

#define MyException(name, ...) \
  Exception(name, #name, ##__VA_ARGS__, __FILE__, __LINE__)
};

#include <string.h>
void print_exception(const Exception &e, FILE *f = stdout) {
  fprintf(f, "[Exception] %s at %s:%d\n", e.name, e.file, e.line);
  if (e.errno_val != 0)
    fprintf(f, "%s: %s\n", e.msg.c_str(), strerror(e.errno_val));
  else if (!e.msg.empty())
    fprintf(f, "%s\n", e.msg.c_str());
}