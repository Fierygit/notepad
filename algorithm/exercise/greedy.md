---
title: 贪心算法
date: 2019-12-28
categories: 
- 算法
tag: 贪心
---
# <center>贪心算法</center>



贪心方法的一般策略

   问题的一般特征：问题的解是由n个输入的、满足某些事先给定的条件的子集组成。

​    1）一般方法

​    根据题意，选取一种度量标准。然后按照这种度量标准对n个输入排序，并按序一次输入一个量。

​    如果这个输入和当前已构成在这种量度意义下的部分最优解加在一起不能产生一个可行解，则不把此输入加到这部分解中。否则，将当前输入合并到部分解中从而得到包含当前输入的新的部分解。

   2）贪心方法

   这种能够得到某种量度意义下的最优解的分级处理方法称为贪心方法



## 经典例题

### Bridge

> N个人在晚上想通过桥。每次最多过去2个人，需要有手电筒。现在只有1个手电筒。每个人过桥的速度都不一样，过桥的时间以慢的为准。
> 你的工作是找到最少的过桥的方法； 
>
> 输入：
> 首先是n,然后n 行是过桥的时间。
> 人数不超过1000，而且没有人过桥的时间超过100秒；
> 输出：
> 首先输出总共所需最少时间；
> 下面表示过桥的过程（见样例），如果有多种过桥方案，输出其中一种。

```
每次过桥2个人，希望一起过去的人时间相差最小，所以采用贪心技术。
而且返回的时间要少，所以用时间最少的人返回；

对于样例 1	2	5	10

每一组时间相差最小的数有两种选择：
1、可以选择一个最小的1， 每次送每一个人过去再回来。
	1 	10		1和10过去
	1			1 回来
	1 	5			1和5过去
	1			1回来
一共10 + 1 + 5 + 1 = 17
2、也可以选两个最小的数来送手电筒
	1	2		1和2过去
	1			1回来
	5	10		5和10 过去
	2			回来
一共2 + 1 + 10 + 2 = 15

那么怎么选用送回手电筒的策略呢？

结合下面代码看！
```

```c
#include <algorithm>
#include <iostream>
using namespace std;
int main() {
  int i, j, n;
  cin >> n;
  int s[n];
  for (i = 0; i < n; i++) cin >> s[i];
  sort(s, s + n);// 先排序
  // 选择第一种情况：
  //  s1 = s[i] + s[0] + s[i-1] + s[0]; // 相当于 上面的 10 + 1 + 5 + 1
  // 选择第二种情况
  //  s2 = s[1] + s[0] + s[i] + s[1];	//相当于上面的 2 + 1 + 10 + 2；
  int t = 2 * s[1] - s[0];
   // 先考虑第一种情况  即: s1 > s2, 化简为   s[i-1] > t; 
  for (i = n - 1; (i > 2) && (s[i - 1] > t); i -= 2)
    printf("%d %d\n%d\n%d %d\n%d\n", s[0], s[1], s[0], s[i - 1], s[i], s[1]);
   // 在考虑第二种情况
  for (j = i; j > 1; --j) printf("%d %d\n%d\n", s[0], s[j], s[0]);
  return 0;
}
```



### Radar Installation

> 在一个海边建一些雷达，雷达覆盖范围为d为半径的一个圆，所有海岛都需要覆盖在雷达范围内。问题是最少需要建多少雷达站，使得所有的海岛都被覆盖。
>
> **Input**
>
> The input consists of several test cases. The first line of each case contains two integers n (1<=n<=1000) and d, where n is the number of islands in the sea and d is the distance of coverage of the radar installation. This is followed by n lines each containing two integers representing the coordinate of the position of each island. Then a blank line follows to separate the cases. 
>
>  The input is terminated by a line containing pair of zeros 
>
> **Output**
>
> For each test case output one line consisting of the test case number followed by the minimal number of radar installations needed. "-1" installation means no solution for that case.

![image-20200502185150700](https://raw.githubusercontent.com/Fierygit/picbed/master/20200502185142.png)





解题思路：

- 一、从左往右考虑建雷达站，首先第一个雷达尽量往右建（贪心）以期能覆盖尽可能多的海岛。

- 二、重复一，一直到所有的海岛都被覆盖到。

- 三、怎么确定圆心位置。对于每个海岛来说，以海岛为中心，以d为半径画圆，在海岸线上有2个交点。那么第一个右交点就是第一个雷达站位置。

- 四、解题步骤：首先对于每个海岛，求出它的左右交点，按照右交点、左交点的顺序排序。依次找到最小的右交点，只要左交点比该右交点小，就被该圆覆盖。找到下一个不能被覆盖的左交点。

```c
#include <math.h>
#include <stdio.h>
#include <stdlib.h>

typedef struct {
  double left, right;
} Interval;

int cmp(const void *c, const void *d) {  // 万能指针， c语言的
  Interval *a, *b;
  a = (Interval *)c;
  b = (Interval *)d;
  if ((*a).right < (*b).right) return -1;
  if ((*a).right > (*b).right) return 1;
  if ((*a).left < (*b).left) return -1;
  if ((*a).left > (*b).left) return 1;
  return 0;
}
Interval island[1000];
int main() {
  int i, j, x, y, dd, m = 0, n, d;
  double t;
  while (scanf("%d%d", &n, &d), n) {
    dd = d * d;
    for (i = j = 0; i < n; ++i) {
      scanf("%d%d", &x, &y);
      if (y > d) j = -1;  // 输入点的y 大于雷达的半径一定无解， 雷达覆盖不到
      if (j == -1) continue;
      t = sqrt(dd - y * y);
      island[i].left = x - t;  //左右交点， 这一步转换是核心， 大多数这一步想不到
      island[i].right = x + t;  // 转换成一个合适的数据结构来计算
    }
    if (j == 0) {
      /* Sort NMEMB elements of BASE, of SIZE bytes each,
     using COMPAR to perform the comparisons.  */
      qsort(island, n, sizeof(island[0]), cmp);
      for (i = j = 1, t = island[0].right; i < n; ++i)// 贪心， 核心代码
        if (island[i].left > t) { // 尽可能往右边建， j 表示个数
          t = island[i].right;      // 不能建了， 只能新开一个
          ++j;
        }
    }
    printf("Case %d: %d\n", ++m, j);
  }
  return 0;
}
```



### 合并石头

> 有n堆石头， 只能两两合并， 求合成一堆的最小花费。
>
> input
>
> 第一行输入 n， 接下来n 行输入每堆石头的重量

解题思路：

每次选择，最小的两堆合并！ haffman 算法。

极致效率：

```c
#include <algorithm>
#include <iostream>
using namespace std;
// int cmp(const void* a, const void* b) { return (int)(a) - (int)(b); }
int main() {
  /**
   * 为了不要每次选择 最小的两堆 合并后再次排序， 可以选择两个数组
   * a 存储输入的数据，
   * b  存储合并后的数据
   * 分别用一个标记记录边界
   * haffman 算法的最优求解方法！！！
   * */
  int n, a[10003], b[10000];
  sort(a, a + n);
  // qsort(a, n, sizeof(a[0]), cmp);
  a[n] = a[n + 1] = 1073741800;   // 为最后的判断设置的一个最大的数
  int i, j, k, r, m, c, t;
  for (i = 0; i < n;) b[i++] = 20001;
  n--;
  for (i = j = k = r = 0; i < n; ++i) {
    m = a[j] + a[j + 1]; // 选取原来数组的两个值相加
    c = 0;
    if (m > (t = a[j] + b[k])) {   // 原来数组和合并之后的各选一个
      m = t;
      c = 1;
    }
    if (m > (t = b[k] + b[k + 1])) {// 都选合并之后的石头堆， k 越大b[k] 一定是递增的
      m = t;
      c = 2;
    }
    r += b[i] = m;      // 合并后的记录在 b， 总的花费加上1
    if (c == 0)         // 移动标记 j -> a,    k -> b;
      j += 2;
    else if (c == 1)
      j++, k++;
    else
      k += 2;
  }
  printf("%d\n", r);
  return 0;
}
```



### **Sum of Factorials**

> 一个数是否可以表示为阶乘之和
>
> input
>
> 9
>
> output
>
> yes
>
> example
>
> 9=1!+2!+3!



因为是超递增序列，所以可以贪心。

> 超递增序列： 第n个数 大于 前n - 1个数之和
>
> n! , n^2, 都是超递增序列



```c
#include <iostream>
using namespace std;
int fact[11];
int jud(int n) {
  if (n >= fact[10]) return 0;
  for (int i = 9; i >= 0; --i)
    if (n > fact[i])  // 9的阶乘已经很大了， 从大到小， 只要可以放就放进去
      n -= fact[i];
    else if (n == fact[i])
      return 1;
  return 0;
}
int main() {
  fact[0] = 1;
  int n;
  for (n = 1; n < 11; ++n)
    fact[n] = n * fact[n - 1];  // 提前计算好每一个数的阶乘
  while (scanf("%d", &n), n >= 0) printf(jud(n) ? "YES\n" : "NO\n");
  return 0;
}
```























