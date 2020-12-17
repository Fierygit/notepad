---
title: ACM_exercise
date: 2019-12-28
categories: ["算法"]
tags: ["算法"]
---
# HNU ACM





## dp

### 1. Longest Ordered Subsequence

| **Problem description**                                      |
| ------------------------------------------------------------ |
| A numeric sequence of *ai* is ordered if *a1* < *a2* < ... < *aN*. Let the subsequence of the given numeric sequence (*a1*, *a2*, ..., *aN*) be any sequence (*ai1*, *ai2*, ..., *aiK*), where 1 <= *i1* < *i2* < ... < *iK* <= *N*. For example, sequence (1, 7, 3, 5, 9, 4, 8) has ordered subsequences, e. g., (1, 7), (3, 4, 8) and many others. All longest ordered subsequences are of length 4, e. g., (1, 3, 5, 8).  Your program, when given the numeric sequence, must find the length of its longest ordered subsequence. |
| **Input**                                                    |
| The first line of input contains the length of sequence N. The second line contains the elements of sequence - N integers in the range from 0 to 10000 each, separated by spaces. 1 <= N <= 1000 |
| **Output**                                                   |
| Output must contain a single integer - the length of the longest ordered subsequence of the given sequence. |
| **Sample Input**                                             |
| 7 |
| 1 7 3 5 9 4 8                                             |
| **Sample Output**                                            |
| `4 `                                                         |
| **Problem Source**                                           |
| HNU Contest                                                  |

思路：

dp[i] : 表示以第 i 个字符结尾的最长子上升子序列！

```c++
#include <iostream>
#include <vector>
using namespace std;
int len;
const int maxn = 1000 + 1;
int a[maxn];
int dp[maxn];
int ans = 1; 
int main(){
    cin >> len;
    int tmp;
    for(int i = 0; i < len; i++)   cin >> a[i];
    for(int i = 0 ; i < len; i++){  // 对于第 i 个 zifu
        tmp  = 1;
        for(int j = 0; j < i; j ++){
            if(a[i] >= a[j]) tmp = max(dp[j] + 1, tmp );
        }
        dp[i] = tmp;
        ans = max(tmp,ans);       
    }
    cout << ans <<endl;
    return 0;
}
```







### 2.**Dissatisfying Lift**

| **Problem description**                                      |
| ------------------------------------------------------------ |
| There's a building with M floors. The amounts of tenants of every floor are K1, K2, K3, ..., Km. One day all the tenants went home together and they took the same lift (suppose the lift was large enough). Because of some reason the lift could only stop on one floor and the tenants must go upstairs or downstairs to their houses. Every tenant went up N floors would make the dissatisfied degree rise N * a + 0.5 * N * (N - 1) degrees, and every tenant went down N floors would make the dissatisfied degree rise N * b + 0.5 * N * (N - 1) degrees. Your task is to tell which floor the lift should stop, in order to make the dissatisfied degree as low as possible. |
| **Input**                                                    |
| The first line of the input contains a single integer T (1 <= T <= 20), the number of test cases. Then T cases follow. The first line of each test contains M (1 <= M <= 10000), a and b (0 <= a, b <= 100). The second line contains K1, K2, K3, ..., Km(0 <= Ki <= 20, i = 1..M). |
| **Output**                                                   |
| For each test case, print a line containing a single integer, indicating which floor the lift should stop. |
| **Sample Input**                                             |
| 1 |
|5 3 2|
|1 1 1 1 1                                            |
| **Sample Output**                                            |
| `3`                                                          |
| **Judge Tips**                                               |
| Dynamic Programming                                          |
| **Problem Source**                                           |
| POJ Monthly                                                  |









### 3.





## 贪心

























