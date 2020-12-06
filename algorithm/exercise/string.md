---
title: 字符串
date: 2019-12-28
categories: 
- 算法
tag: 算法
---
# <center> string处理 </center>

[toc]

### 字符串hash

字符串可以看做是一个n进制的表示方式： 

``` c#
ABC =   A * 26^2 + B * 26^1 + C * 26^0; 
```

这就简单地转成 10 进制了， 然后在 取余， 让它在整数范围内就ok， 但取余后就会有冲突！

把进制 换为一个 10^7 级别的质数， 和mod 设为 10^9 的一个质素冲突就很小！

首先进制转为很大了， 足以让每一种字符都有一份， mod 也足够大防止取余后的冲突！

```c
H[i]  = (H[i-1] * p + index(str[i]))% mod
```

假设 p = 10000019；   mod =1000000007

 ```c
#include <iostream>
using namespace std;
const int P = 10000019;
const int MOD = 1000000007;
int hashStr(string& a){
    long long sum = 1;
    for(int i = 0 ; i < a.length(); i++){
        sum  = (sum * P + a[i] - 'a') % MOD;
    }
    return sum;
}
int main(){
    string a;
    while(cin >> a){
        cout << a << " hash num is : " << hashStr(a) << endl;
    }
}
 ```











### kmp算法（字符串匹配）

这个算法最大的难点在于next数组的求解， 求解出了next数组，剩下就容易了！

关于next的介绍，看了好久才懂， 看懂了，算法又实现半天也没有实现出来！

https://www.cnblogs.com/zhangtianq/p/5839909.html （这是介绍最完整的一篇，花了几个小时）



代码的逻辑真的是太绕了， 有点难理解！ 理解了next的原理，再写代码，也有点想象不来。

```c
/*
 * @Author: Firefly
 * @Date: 2020-03-15 15:20:41
 * @Descripttion:
 * @LastEditTime: 2020-03-15 16:08:23
 */
#include <iostream>
using namespace std;
#define MAXN 1000
int nextState[MAXN];
void getNext(string& str) {
  int start = -1;     // 第二行的pattern 从 -1 开始
  nextState[0] = -1;  // 第一个字母肯定没有相同的序列
  for (int i = 1; i < str.length(); i++) {  // 对于后面的每一个字母
    //跳转到开始的索引， 到前面的索引
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
```















