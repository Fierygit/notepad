/*
 * @Author: Firefly
 * @Date: 2020-03-15 15:20:41
 * @Descripttion:
 * @LastEditTime: 2020-06-27 11:15:09
 */

#include <iostream>
using namespace std;

#define MAXN 1000

int nextState[MAXN];

void get(string& str) {
  int j = -1;
  nextState[0] = -1;
  for (int i = 0; i < str.length(); i++) {
    while (j != -1 && nextState[i] != str[j + 1]) j = nextState[j];
    if (str[i] == str[j + 1]) {
      j++;
    }
    nextState[i] = j;
  }
}

void getNext(string& str) {
  int start = -1;     // 第二行的pattern 从 -1 开始
  nextState[0] = -1;  // 第一个字母肯定没有相同的序列
  for (int i = 1; i < str.length(); i++) {  // 对于后面的每一个字母
    //跳转 开始的索引， 到前面的索引
    while (start != -1 && str[i] != str[start + 1]) start = nextState[start];
    // 写代码时，想像第一个字母的情况，
    //如果第二个（i） 等于 第一个（start +1）  第一二个相同，
    if (str[i] == str[start + 1]) start++;
    nextState[i] = start;  //设置第二行 start 的长度
  }
}

int main() {
  string text, pattern;
  cin >> text >> pattern;
  getNext(pattern);
  for (int i = 0; i < pattern.length(); i++) cout << nextState[i] << " ";
  cout << endl;

  int j = -1;  // j 表示pattern 的索引
  for (int i = 0; i < text.length(); i++) {
    while (j != -1 && text[i] != pattern[j + 1]) {
      j = nextState[j];
    }
    if (text[i] == pattern[j + 1]) {
      j++;
    }
    if (j == pattern.length() - 1) {
      cout << "find at " << i - j << endl;
    }
  }

  return 0;
}

void get1(string& str) {
  int nextStat[str.size()];
  nextStat[0] = -1;
  int j = -1;
  for (int i = 0; i < str.size(); i++) {
    
    while (j != -1 && str[i] != str[j + 1]) j = nextStat[j];

    if (str[i] == str[j + 1]) j++;
    nextStat[i] = j;
  }
}