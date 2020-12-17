---
title: Leetcode100
date: 2020-01-08
categories: ["算法"]
tag: ["算法"]
---
## <center>leetcode100 练习</center>

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/add-two-numbers
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。





#### 1.两数之和

> 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。

你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。

```c
给定 nums = [2, 7, 11, 15], target = 9
因为 nums[0] + nums[1] = 2 + 7 = 9
所以返回 [0, 1]
来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/two-sum
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
```

思路： 一开始看到这道题就是暴力，暴力不知道时间和空间够不够， 因此不写了，想了一会儿， 可以哈希一下，map[num[i]] = i,  用一个map来存储数据的值 和 下标， 这样遍历一遍就可以知道， 有没有 target - num[i]  对应的值， 如果有， 可以直接map到；

```c++
class Solution {
public:
    vector<int> twoSum(vector<int>& nums, int target) {
        // 数字  map 下标
        vector<int> ans;
        map<int, int> a;
        for(int i = 0; i < nums.size(); i++){
            a[nums[i]] = i;
        }
        for(int i = 0 ; i < nums.size(); i++){
            int temp = target - nums[i];
            if( a[temp] != 0){
                if(a[temp] != i){
                    ans.push_back(i);
                    ans.push_back(a[temp]);
                    break;
                }
            }
        }
        return ans;
    }
};
```



#### 2.两数相加



>  给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。

如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。

您可以假设除了数字 0 之外，这两个数都不会以 0 开头。

```
输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
输出：7 -> 0 -> 8
原因：342 + 465 = 807
```

解题思路：

刚拿到这道题的时候，想都没想，直接就遍历两条链表， 求出值， 然后再加起来， 结果是，给出了一个大于9位的数值，直接就超出了int的范围，看来我还是太年轻了，幼稚！！

然后看了下正确的题解， 遍历链表， 一位一位求， 像人工算超长的算法一样！

```c
/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode(int x) : val(x), next(NULL) {}
 * };
 */
class Solution {
public:
    ListNode* addTwoNumbers(ListNode* l1, ListNode* l2) {      
        ListNode* ans = new ListNode(0);
        ListNode* index = ans;
        //l l1 中的值； r l2 中的值；  a 进位
        int l, r, a;
        l = r = a = 0;
        //只要有一个不是空的就计算
        while(l1 != NULL || l2 != NULL){
            
            l = l1 == NULL ? 0 : l1->val;
            r = l2 == NULL ? 0 : l2->val;
            //计算每一位的和， 一开始a为0，没有进位
            int sum = l + r + a;    
            sum -= sum>= 10 ? 10 : 0; //大于10的话减去10 进位
            ListNode* temp = new ListNode(sum);
            a = l + r + a>= 10 ? 1 : 0;
            //指向答案下一个节点
            index->next = temp;
            index = temp;
            //响应移动
            if(l1 != NULL)   l1 = l1->next;
            if(l2 != NULL)   l2 = l2->next;
        }
        if(a == 1){
             ListNode* temp = new ListNode(1);
             index->next = temp;
        }
        return ans->next;
    }
};
```



#### 3.无重复字符的最长子串

>  给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。

```c
输入: "abcabcbb"
输出: 3 
解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
```

```c
输入: "bbbbb"
输出: 1
解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
```

```c
输入: "pwwkew"
输出: 3
解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
```


​     请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。

思路：

一开始没考虑全题目的意思！！！

```c
class Solution {
public:
    int lengthOfLongestSubstring(string s) {
        set<char>  c;
        int ans = 0;
        for(int i = 0; i < s.length(); i++){
            if(c.find(s[i]) != c.end()){
                ans = c.size() > ans? c.size() : ans;
                c.clear();               
            }
             c.insert(s[i]);    
        }
        ans = c.size() > ans? c.size() : ans; 
        return ans;
    }
};
```

想都不用想肯定错了！

这道题的正确思路是滑动窗口的解题思想， 确保右边的“窗口”一定是没有重复的字母， 每次增加一个字符， 更新窗口的值，在这个过程中记录出现的最大值即为解。

```c
class Solution {
public:
    int lengthOfLongestSubstring(string s) {
        
        // 滑动窗口 start 窗口的开始
        int start = 0, max = 0, j;
        for(int i = 0 ; i < s.length(); i ++){// 每当加入一个字符
            for(j = start; j < i; j ++){  // 遍历新的窗口
                if(s[i] == s[j]){
                     start = j + 1;			//找到第一个相等的字符
                     break;
                }
            }
            if(i - start + 1 > max)
                max = i - start  + 1;
        }
        return max;

    }
};
```



#### 4.寻找两个有序数组的中位数todo

> 给定两个大小为 m 和 n 的有序数组 nums1 和 nums2。请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。

你可以假设 nums1 和 nums2 不会同时为空。

```c
nums1 = [1, 3]
nums2 = [2]
则中位数是 2.0
```


```c
nums1 = [1, 2]
nums2 = [3, 4]
则中位数是 (2 + 3)/2 = 2.5
```



//todo 没弄懂

#### 5.最长回文子串

给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。

```c
输入: "babad"
输出: "bab"
注意: "aba" 也是一个有效答案。
```

```c
输入: "cbbd"
输出: "bb"
```



解题思路：

题解一： 中心查找

遍历一遍字符串， 如果当前的字符不对称就开始检查，现在是否为最大的回文串, 是就更新！

```c
class Solution {
public:
    string longestPalindrome(string s) {
        int len = s.length();
        int size = len * 2 - 1;//一共有 2*n - 1 个重点
        int max = 0;
        int maxi = 0;
        for(int i = 0; i < size; i++){
            if(i % 2 == 0){//字符本身为中点
                int mid = i / 2;
                int j = 0;
                while(mid - j >= 0 && mid + j < len
                && s[mid-j] == s[mid+j]) j++;
                maxi = 2 * j - 1 > max ? i : maxi;
                max = 2 * j - 1 > max ? 2 * j - 1 : max;
             cout << max << " " << maxi << " " << j << " " << maxi/ 2 <<  " *0*" << i << endl;  
            }
            else if(i % 2 == 1){//不是以字符本身为中点
                int left = i / 2;
                int right = left + 1;
                int j = 0;
                while(left - j >= 0 && right + j < len && 
                s[left - j] == s[right + j]) j++;
                maxi = 2 * j > max ? i : maxi;
                max = 2 * j > max ? 2 * j : max;   
               cout << max << " " << maxi << " " << j << " " << maxi/ 2 <<  " *1*" << i << endl;         
            }
        }
        cout << max << " " << maxi <<endl;
        if(maxi % 2 == 0){
            return s.substr(maxi / 2 - (max/2), max);
        } else return s.substr(maxi / 2 - max/2 +1, max);
    }
};
```

题解二： 动态规划

p[ i ] [ j ] 表示 i 到 j 为回文串！

递推式：

p[i+1] [j+1] =  p[ i ] [ j ] + s[i+1] [j+1];

p[i] [j] = (i == j - 1 ) && s[i] [j - 1];



```c
class Solution {
public:
    string longestPalindrome(string s) {
        if(s == "") return "";
        int len = s.length();
        bool flag[len][len] = {0};
        int max, l;
        l = max = 0;     
        for(int i = 0; i < len; i++)
            for(int j = 0; j < len; j++)
                flag[i][j] = 0;
        for(int i = 0 ; i < len; i ++)  flag[i][i] = 1;
        for(int i = 0 ; i < len; i++){      
            for(int j = 0; j < i; j++){
                 if(j == i - 1 && s[j] == s[i]){
                    flag[j][i] = 1;
                    if(  max < i - j ) max = i - j , l = j;
                 } 
                 if(j - 1 >= 0 && flag[j][i-1] && s[j-1] == s[i]){
                    flag[j-1][i] = 1;
                    if(  max < i - (j - 1) ) max = i - j + 1, l = j-1;
                 }
            }
        }
        return s.substr(l,max+1);
    }
};
```



#### 6. Z字型变换

> 将一个给定字符串根据给定的行数，以从上往下、从左到右进行 Z 字形排列。

比如输入字符串为 "LEETCODEISHIRING" 行数为 3 时，排列如下：

L    C     I        R
E T O E S  I	 I  G
E    D    H       N
之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："LCIRETOESIIGEDHN"。

请你实现这个将字符串进行指定行数变换的函数：

string convert(string s, int numRows);

```c
输入: s = "LEETCODEISHIRING", numRows = 3
输出: "LCIRETOESIIGEDHN"
```

```c
输入: s = "LEETCODEISHIRING", numRows = 4
输出: "LDREOEIIECIHNTSG"
```

解题思路：

这道题首先想到的是使用找规律， 规律其实很好找！

看到题解中有一个很不错的思路，是把这个的过程直接转换成代码的形式， 好多的题都是这样的！

之前不知道这种做法， 真的是孤陋寡闻！！！

```c
class Solution {
public:
	string convert(string s, int numRows) {

		if (numRows == 1) return s;
		// 这个表示每一行的字符
		vector<string> rows(min(numRows, int(s.size()))); // 防止s的长度小于行数
		int curRow = 0;
		bool goingDown = false;

		for (char c : s) {
			rows[curRow] += c;
			if (curRow == 0 || curRow == numRows - 1) {// 当前行curRow为0或numRows -1时，箭头发生反向转
				goingDown = !goingDown;
			}
			curRow += goingDown ? 1 : -1;
		}
		string ret;
		for (string row : rows) {// 从上到下遍历行
			ret += row;
		}
		return ret;
	}
};
```



#### 7.整数反转

>  给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。

```c
输入: 123
输出: 321
```

```c
输入: -123
输出: -321
```

==注意:==

假设我们的环境只能存储得下 32 位的有符号整数，则其数值范围为 [−231,  231 − 1]。请根据这个假设，如果反转后整数溢出那么就返回 0。

思路：

这道题最重要的是判断是否溢出， 用一个更大的来存， 或者直接判断（笨方法）。

请你记住下面这两个的求法！！！

MAX_INT = (unsigned)(-1)>>1;
MIN_INT = ~MAX_INT;

或者

MIN_INT = 1 << 31;

MAX_INT = ~MAX_INT;

```c
class Solution {
public:
    int reverse(int x) {
        //首次找到最大的int值
        int MAX_INT, MIN_INT;
        MAX_INT = (unsigned)(-1)>>1;
        MIN_INT = ~MAX_INT;
        long ans = 0;;
        while(x){
            ans = ans * 10 + x % 10;
            x /= 10;
        }
        if(ans > MAX_INT ||  ans < MIN_INT) return 0;
        else return (int)ans;
    }
};
```



#### 8.字符串转换整数 (atoi)

> 请你来实现一个 atoi 函数，使其能将字符串转换成整数。

- 首先，该函数会根据需要丢弃无用的开头空格字符，直到寻找到第一个非空格的字符为止。

- 当我们寻找到的第一个非空字符为正或者负号时，则将该符号与之后面尽可能多的连续数字组合起来，作为该整数的正负号；假如第一个非空字符是数字，则直接将其与之后连续的数字字符组合起来，形成整数。

- 该字符串除了有效的整数部分之后也可能会存在多余的字符，这些字符可以被忽略，它们对于函数不应该造成影响。

- 注意：假如该字符串中的第一个非空格字符不是一个有效整数字符、字符串为空或字符串仅包含空白字符时，则你的函数不需要进行转换。

- 在任何情况下，若函数不能进行有效的转换时，请返回 0。

说明：

假设我们的环境只能存储 32 位大小的有符号整数，那么其数值范围为 [−2^31,  2^31 −  1]。如果数值超过这个范围，请返回  INT_MAX (2^31 −  1) 或 INT_MIN (−2^31) 。

```c
输入: "42"
输出: 42
```

````c
输入: "   -42"
输出: -42
````

```c
输入: "4193 with words"
输出: 4193
```

```c
输入: "words and 987"
输出: 0
```

```c
输入: "-91283472332"
输出: -2147483648
```

解题思路： 直接暴力， 暴力求解！！！

```c
class Solution {

#define INT_MIN (1<<31)
#define INT_MAX ~INT_MIN

public:
    int myAtoi(string str) {
        bool firstnum = 0;
        bool f = 0;
        int  len = str.length();
        long ans = 0;
        for(int i = 0; i < len; i++){       
             if(!firstnum){
                 if(str[i] == ' ') continue;
                 else if(!isdigit(str[i]) && !(str[i] == '-' || str[i] == '+'))
                     return 0;               
                 else if(str[i] == '-' || str[i] == '+'){                  
                    f = str[i] == '-'?1:0;
                 }else ans = ans * 10 + (str[i] - '0');             
                firstnum = 1;           
              }else if(isdigit(str[i])){
                ans = ans * 10 + (str[i] - '0');
                if(f && (ans > INT_MAX )) return INT_MIN;
                else if(ans > INT_MAX) return INT_MAX;
              }else break;
       }
       return f ? ~(int)ans + 1:(int)ans;
    }
};
```



#### 9.回文数

判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。

```c
输入: 121
输出: true
```

```c
输入: -121
输出: false
```

```c
输入: 10
输出: false
```

你能不将整数转为字符串来解决这个问题吗？

我就直接用字符串处理解决这道题了，还可以用数字反转处理！！

```c
class Solution {
public:
    bool isPalindrome(int x) {
        stringstream ss;
        ss << x;
        string temp;
        ss >> temp;
        for(int i = 0 ; i < temp.length() / 2; i++)
            if(temp[i] != temp[temp.length() - i - 1]) return false;
        return true;
    }
};
```



#### 10.正则表达式匹配

> 给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。

> '.' 匹配任意单个字符
> '*' 匹配零个或多个前面的那一个元素
> 所谓匹配，是要涵盖 整个 字符串 s的，而不是部分字符串。

说明:

s 可能为空，且只包含从 a-z 的小写字母。
p 可能为空，且只包含从 a-z 的小写字母，以及字符 . 和 *。

```c
s = "aa"
p = "a"
输出: false
解释: "a" 无法匹配 "aa" 整个字符串。
```

```c
s = "aa"
p = "a*"
输出: true
解释: 因为 '*' 代表可以匹配零个或多个前面的那一个元素, 在这里前面的元素就是 'a'。
     因此，字符串 "aa" 可被视为 'a' 重复了一次。
```

```c
s = "ab"
p = ".*"
输出: true
解释: ".*" 表示可匹配零个或多个（'*'）任意字符（'.'）。
```

```c
s = "aab"
p = "c*a*b"
输出: true
解释: 因为 '*' 表示零个或多个，这里 'c' 为 0 个, 'a' 被重复一次。因此可以匹配字符串 "aab"。
```

```c
s = "mississippi"
p = "mis*is*p*."
输出: false
```



这道题利用的是递归的思想， 我是一步一步增加考虑的情况，

我下面的代码其实可以合并， 合并后可以更加简洁，跟题解是一样的，但我认为初次做这道题， 一下子合并代码的话不容易理解！！

具体的思路都在注释里面！！

```c
class Solution {
public:
    bool isMatch(string s, string p) {
        //这是消除相同的闭包， 不然会超时， 比如a*a*a*a*a*其实一个a*和很多个是没有区别的！！
       // 但是处理的情况却多了很多， 比如第一个a* 会考虑 直接匹配a 也可以不匹配a让后面的匹配，
        // 每一个a* 都是这样考虑这就是一个指数函数了！！！
        if(p.length() > 4){
            for(int i = 0; i < p.length(); i++){
                if(p[i+1] == '*' && i + 3 <  p.length() 
                && p[i+3] =='*' && p[i] == p[i+2]){
                    p = p.substr(0, i) + p.substr(i + 2, p.length() - i - 2);
                }
            }
        }
        cout << "s :  " << s << "   " ;
        cout << " P : " << p <<endl <<endl;
        
        // 如果pattern 空了， 但是s还没有空的话肯定错了
        if(p == "")   return s == "" ? true : false;  
        // 如果字符空了， 但是p还没空， 那就删除一个闭包，因为闭包可以为空，
        // 比如 s == “” p == “a*” 这个是可以匹配的！
        if(s == "") return p.length() >= 2 && p[1] == '*' ?  
            isMatch(s,p.substr(2,p.length() - 2)) : false;
		
        //第一种情况， 一个 . 的情况
        if(p[0] == '.' && (p.length() >=2 && p[1] != '*' || p.length() == 1)){
            return isMatch(s.substr(1,s.length() - 1),p.substr(1,p.length() - 1));
        }
        //第二种  .* 的情况， 返回有两种情况 1、闭包为空， 2、闭包不为空
        else if(p[0] == '.' && p.length() >= 2 && p[1] == '*'){
            return  isMatch(s,p.substr(2,p.length() - 2)) || isMatch(s.substr(1,s.length() - 1),p) ;
        }
        // 第三种情况 字符相同但不是闭包的情况
        else if(p[0] == s[0] && (p.length() >=2 && p[1] != '*' || p.length() == 1) ){
            return isMatch(s.substr(1,s.length() - 1), p.substr(1,p.length() - 1));
        }
        //第四种情况 字符相同且是闭包的情况
        // 返回也有两种： 1、 闭包为空 2、 闭包吃掉一个字符
        else if(p[0] == s[0] && (p.length() >=2 && p[1] == '*')){
            return isMatch(s,p.substr(2,p.length() - 2)) ||isMatch(s.substr(1,s.length() - 1),p);
        }
        // 最后一个情况， 字符不相同， 但是p有闭包， 闭包可以为空
        else if(p[0] != s[0] && p.length() >=2 && p[1] == '*'){
            return isMatch(s,p.substr(2,p.length() - 2));
        }
        // 其它情况的话就是匹配不了的了，返回false
        return false;
    }
};
```

#### 11.盛最多水的容器

给定 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0)。找出其中的两条线，使得它们与 x hyj轴共同构成的容器可以容纳最多的水。

说明：你不能倾斜容器，且 n 的值至少为 2。

<img src="https://aliyun-lc-upload.oss-cn-hangzhou.aliyuncs.com/aliyun-lc-upload/uploads/2018/07/25/question_11.jpg" alt="img" style="zoom:80%;" />

图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。

```c
输入: [1,8,6,2,5,4,8,3,7]
输出: 49
```

首先思路简单：直接暴力可以通过！

```c
class Solution {
public:
    int min(int a, int b){
        return a < b? a : b;
    }

    int maxArea(vector<int>& height) {
        int len = height.size();
        int ans = 0;
        for(int i  = 0 ; i < len; i++ ){
            for(int j = 0; j < i; j ++ ){
                int temp = (i - j) * min(height[i],height[j]);
                ans = temp > ans ? temp : ans;
            }
        }
        return ans;
    }
};
```

当这道题肯定没有这么简单就过了， 暴力应该不是出题人的本意！

leetco的题解上抄的！ Terry su

简单反证法证明：通过双指针方法，两个指针一定会同时经过最大面积对应的指针位置。

假设mn是最大的面积

```txt
                  |                     
           |      |                     
           |      |                     
     ......|......|......               
——————————————————————————————————————— 
           m      n
```

假设有条边p更高在外面，

```txt
        |                                
        |          |                     
        |   |      |                     
        |   |      |                     
     ...|...|......|......               
——————————————————————————————————————— 
        p   m      n
```

```lisp
  AreaMN = ( n - m ) * min( arr[ m ], arr[ n ] )
  AreaPN = ( n - p ) * min( arr[ p ], arr[ n ] )
```

```lisp
 (  n - m ) <= ( n - p )
  min( arr[ m ], arr[ n ] ) <= min( arr[ p ], arr[ n ] )
```

所以： `AreaMN < AreaPN`, 与m和n构成最大面积相矛盾，所以假设不成立，即m左侧的点都不高于n，即等于或矮于n。同理可证，n右侧指针等于或矮于m。所以通过双指针方法，两个指针一定会同时经过最大面积对应的指针位置。

原理明白了， 代码自然简单！

```c
class Solution {
public:
    int min(int a, int b){
        return a > b? b : a;
    }
    int maxArea(vector<int>& height) {
        int l = 0, r = height.size() - 1;
        int max = 0;

        while(l < r){
            int temp = (r - l) * min(height[l],height[r]);
            max = max < temp? temp : max;
            if(height[l] < height[r]) l++;
            else r--;
        }
        return max;
    }
};
```



#### 12.整数转罗马数字

>  罗马数字包含以下七种字符： I， V， X， L，C，D 和 M。

```c
字符          数值
I             1
V             5
X             10
L             50
C             100
D             500
M             1000
```


例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。

通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：

```
I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。 
C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。给定一个整数，将其转为罗马数字。输入确保在 1 到 3999 的范围内。
```

```c
输入: 3
输出: "III"
```

这道是我做过简答的， 相当于找钱，每次找最大面额的钱！

我应该是做过类似的题， 不然我不会一看到代码就直接会思路了， 这应该就是刷题的好处了！！！

```c
class Solution {
public:
    string intToRoman(int num) {
        map<int, string> m;
        m[1000] = "M";
        m[900] = "CM";
        m[500] = "D";
        m[400] = "CD";
        m[100] = "C";
        m[90] = "XC";
        m[50] = "L";
        m[40] = "XL";
        m[10] = "X";
        m[9] = "IX";
        m[5] = "V";
        m[4] = "IV";
        m[1] =  "I";
        int range[] = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
        string ans = "";
        for(int i = 0; i < 13 ; i++)
            while(num >= range[i]) ans += m[range[i]], num-=range[i];
        return ans;
    }
};
```



#### 13. 罗马数字转整数

罗马数字包含以下七种字符: I， V， X， L，C，D 和 M。

字符          数值
I             1
V             5
X             10
L             50
C             100
D             500
M             1000
例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。

通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：

I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。 
C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
给定一个罗马数字，将其转换成整数。输入确保在 1 到 3999 的范围内。

```c
输入: "III"
输出: 3
```

解题思路： 12 题反过来， 顺序遍历字符串， 全部加起来， 如果前面的比后面的小要双倍减去，一看代码就明白了！！

```c
class Solution {
public:
    int romanToInt(string s) {
        map<char, int> m;
        m['I'] = 1;
        m['V'] = 5;
        m['X'] = 10;
        m['L'] = 50;
        m['C'] = 100;
        m['D'] = 500;
        m['M'] = 1000;
        int ans = 0;
        for(int i = 0; i < s.length(); i++){
            if(!i){
                ans += m[s[i]];continue;
            } 
            else if(m[s[i]] > m[s[i - 1]]){
                ans -= 2 * m[s[i - 1]]; // 加倍奉还
            }
            ans+= m[s[i]];
        }
        return ans;
    }
};
```

#### 14.最长公共子串

> 编写一个函数来查找字符串数组中的最长公共前缀。

如果不存在公共前缀，返回空字符串 ""。

```c
输入: ["flower","flow","flight"]
输出: "fl"
```

```c
输入: ["dog","racecar","car"]
输出: ""
```

暴力，^_~

```c
class Solution {
public:
    string longestCommonPrefix(vector<string>& strs) {
        if(strs.size() == 0)  return "";
        string ans;
        char com;
        ans = "";
        int index = 0, flag = 0;
        while(true){
            for(int i = 0 ; i < strs.size(); i++){
                string temp = strs[i];
                if(temp.length() == 0) return "";
                if(!i) com = temp[index];
                else if(com  !=  temp[index])
                    return ans;               
                if(index == temp.length() - 1) flag = 1;
            }
            cout << ans <<endl;     
            ans += com;     
            if(flag) return ans;
            index++;
        }              
        return ans;
    }
};
```



#### 15. 三数之和

> 给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组。

注意：答案中不可以包含重复的三元组。

 解题思路：

首先这题可以变成一个两数之和题目， 然后一个for循环找nums 里的一个数等于这个两数之和；

所以这道题首先要会两数之和的做法：

> 两数之和等于0(特例=0)

先将数组排序， 然后利用双指针一个在左边， 一个在右边， 如果指针对应的两个数的和大于0， 则右边指针减1，小于则左边加， 直到相遇， 如果相遇了还没有找到答案就没有解， 这个算法容易，但是怎么证明？

想了很久很久。。。。。。

证明：

首先这个算法有三种情况

- 和等于0

   随机一个指针往里面移动，比如（-2  -1 1 2）  现在有两个答案， 找到等于0的 -2 2 后， 假设2左移到1，之后 -2 + 1 < 0, 因此左边 右移， -1 + 1 这个答案也找到了！！

- 和小于0， 左指针右移

- 和大于0， 右指针左移

现在考虑后面这两种情况， 只有两种选择， 因此这两个指针一定会移到答案（如果有）的其中一个因子上！

上面这句话其实好理解， 因为如果有答案， 算法的停止条件是left  < right, 已经遍历了所有的项了！

再举个例子：  两数之和等于 16

```
-8 -6 5 7 11 17 22（答案可以看出 5  11）
```

现在再来考虑两种情况：

1、假设 右指针 **先** 移到  11（刚好这道题是），

那么此时无论左边指针移到哪里， 反正就是小于5， 那么条件为真的只有左指针右移，直到找到答案！

2、 假设 左指针 **先** 移到  5

那么此时无论右指针在哪里， 反正就是大于11，条件为真的只有右指针左移，直至找到答案或相遇！

3、一次只移动左指针或右指针， 不会同时移到答案的两个， 也就是 5  和 11！！

综上上面四点（包括一定会一个先移到答案）， 可以证明， 如果有答案一定可以找到这个答案！！

三数之和就好解了！！！看注释就会了！！！

```c
给定数组 nums = [-1, 0, 1, 2, -1, -4]，
满足要求的三元组集合为：
[
  [-1, 0, 1],
  [-1, -1, 2]
]
```



```c
class Solution {
public:
    vector<vector<int>> threeSum(vector<int>& nums) {
        int len = nums.size();
        vector<vector<int> > ans;
        sort(nums.begin(),nums.end());
        int le, ri,sum;
		
        for(int i = 0; i < len; i++){
            le = i + 1;//不重头开始是为了 去重1
            ri = len - 1;      
            if(nums[i] > 0) break;  //这个也是 去重1， 具体自己想
            
            // 如果 第三项 作为 两数之和的相反数 相同， 去重2
            if(i > 0 && nums[i] == nums[i-1]) continue;
            while(le < ri){
                sum = nums[i] + nums[le] + nums[ri];
                if(sum == 0){
                    vector<int> a = {nums[le],nums[i],nums[ri]};
                    ans.push_back(a);   
                    //下面两个 去重2！！！
                    while(le < ri  && nums[le] == nums[le + 1]) le++;
                    while(le < ri  && nums[ri] == nums[ri - 1]) ri--;     
                }                       
                if(sum > 0) ri--;
                else le++; // 这个包含相同时的移动
            }
        }
        return ans;
    }
};

//去重1    a + b + c = 0  如果不去重， a作为 i 得到一个答案， b作为i得到一个答案, c作为i得到一个答案
//去重2    数组中含有相同的数子     a1 + b + c = 0    a2 + b + c = 0
```

![image-20200114220202937](images/image-20200114220202937.png)





#### 16.最接近的三数之和

> 给定一个包括 n 个整数的数组 nums 和 一个目标值 target。找出 nums 中的三个整数，使得它们的和与 target 最接近。返回这三个数的和。假定每组输入只存在唯一答案。

```c
给定数组 nums = [-1，2，1，-4], 和 target = 1.

与 target 最接近的三个数的和为 2. (-1 + 2 + 1 = 2).
```
思路就是和三数之和一样不同的是多一个判断！！
```c
class Solution {
public:
    int threeSumClosest(vector<int>& nums, int target) {
        int ans = 0;
        int num = ~(1 << 31);
        int len = nums.size();
        sort(nums.begin(), nums.end());
        int le, ri;
        for(int i = 0; i < len; i++){
            if(i > 0 && nums[i] == nums[i-1]) continue;
            le = i + 1;
            ri = len - 1;    
            while(le < ri){
                int sum = nums[i] + nums[le] + nums[ri] ;   
                //while(le < ri && nums[le] == nums[le+1]) le++;
                //while(le < ri && nums[ri] == nums[ri-1]) ri--;       
                // cout << "num: " << num << " sum: " << sum << " " << i << endl;    
                if(sum ==  target) return target; //已经最进了
                else if(sum < target){
                    le++;
                    if(num >=  target - sum){
                        num = target -sum;
                        ans = sum;
                    }
                }else {
                    ri--;
                    if(num >= sum - target){
                        num = sum - target;
                        ans = sum;
                    }                 
                }                 
            }
             //cout << "num: " << num << " i: " << i << " *" << endl;
        }      
        return ans;
    }
};
```



#### 17. 电话号码的字母组合

> 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。

给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。

![](https://raw.githubusercontent.com/Fierygit/picbed/master/20200115170558.png)

```c
输入："23"
输出：["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
说明:
尽管上面的答案是按字典序排列的，但是你可以任意选择答案输出的顺序。
```

我用的是广搜！这道搞人的地方是 :

```c
string str = "cao";

string str2 = str[0] + ""; //不行
string str3 = 'a' + 'b'; //不行
```

```c
class Solution {
    map<char, string> m;  

    
public:
    Solution(){
        m['2'] = "abc";
        m['3'] = "def";
        m['4'] = "ghi";
        m['5'] = "jkl";
        m['6'] = "mno";
        m['7'] = "pqrs";
        m['8'] = "tuv";
        m['9'] = "wxyz";
    }
        
    vector<string> letterCombinations(string digits) {     
        
        vector<string> ans, next;
        if(digits == "") return ans;
        string str = m[digits[0]];
        if(digits.length() > 1)
            next = letterCombinations(digits.substr(1, digits.length() - 1));
        for(int i = 0; i < str.length(); i ++){
            if(digits.length() > 1)
                for(int j = 0; j < next.size(); j ++)
                    ans.push_back(str[i] + next[j]);     
            else    ans.push_back(str.substr(i,1));
        }
        return ans;
    }
};
```







#### 18. 做不出



#### 19. 删除链表的倒数第N个节点

> 给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。

```c
给定一个链表: 1->2->3->4->5, 和 n = 2.
当删除了倒数第二个节点后，链表变为 1->2->3->5.
```


说明：

给定的 n 保证是有效的。

进阶：

你能尝试使用一趟扫描实现吗？使用hashmap

```c
/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode(int x) : val(x), next(NULL) {}
 * };
 */
class Solution {
public:
    ListNode* removeNthFromEnd(ListNode* head, int n) {
        ListNode* first= new ListNode(0);
        ListNode* second = new ListNode(0);
        if(head->next == NULL) return NULL;
        first = second = head;
        while( first->next != NULL &&  n-- ) first = first->next;
        if(n == 1) return head->next;
        while(first->next != NULL)
            first = first->next, second = second->next;
        second->next = second->next->next;
        return head;
    }
};
```





#### 20.有效的括号

> 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。

有效字符串需满足：

左括号必须用相同类型的右括号闭合。
左括号必须以正确的顺序闭合。
注意空字符串可被认为是有效字符串。

```c
输入: "()"
输出: true
```

```c
输入: "()[]{}"
输出: true
```

思路：
使用一个栈来存就行了！

```c
class Solution {
public:
    bool isValid(string s) {

        stack<char> st;
        
        for(int i = 0; i < s.length(); i++){
                if(s[i] == ')' ) {
                    if(st.size() == 0 ||st.top() != '(')  return false;
                    else st.pop();
                }else if( s[i] == ']' ) {
                    if(st.size() == 0 || st.top() != '[')  return false;
                    else st.pop();
                }else if( s[i] == '}' ) {
                    if( st.size() == 0 || st.top() != '{')  return false;
                    else st.pop();
                }else st.push(s[i]);
        }
        if(st.size() == 0) return true;
        return false;

    }
};
```



#### 21.合并两个有序链表

将两个有序链表合并为一个新的有序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。 

```c
输入：1->2->4, 1->3->4
输出：1->1->2->3->4->4
```

思路没什么思路， 就是归并排序的最后一步！！！ 相当于又写了一遍归并排序！

```c
/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode(int x) : val(x), next(NULL) {}
 * };
 */
class Solution {
public:
    ListNode* mergeTwoLists(ListNode* l1, ListNode* l2) {
        
        ListNode* ans = new ListNode(0);
        ListNode* index = new ListNode(0);
        index = ans;
		//一步一步想！！！！
        while(l1 != NULL || l2 != NULL){
            if(l2 == NULL ) index->next = l1, l1 = l1->next;
            else if(l1 == NULL ) index->next = l2, l2 = l2->next;
            else if(l1->val < l2->val) index->next = l1, l1 = l1->next;
            else index->next = l2, l2 = l2->next;
            index = index->next;
        }
        return ans->next;
    }
};
```

简化后的代码：

```c
/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode(int x) : val(x), next(NULL) {}
 * };
 */
class Solution {
public:
    ListNode* mergeTwoLists(ListNode* l1, ListNode* l2) {
     
        ListNode* ans = new ListNode(0);
        ListNode* index = new ListNode(0);
        index = ans;
        while(l1 != NULL || l2 != NULL){
            if(l2 == NULL ) index->next = l1, l1 = l1->next;
            //下面代码 l2 肯定不为空了， l1为空了 就直接跳过后面的了， 所以可以直接合并
            else if(l1 == NULL || l1->val >= l2->val ) index->next = l2, l2 = l2->next;
            else index->next = l1, l1 = l1->next;
            index = index->next;
        }
        return ans->next;
    }
};
```

#### 22.有效括号

> 给出 n 代表生成括号的对数，请你写出一个函数，使其能够生成所有可能的并且有效的括号组合。

```c
例如，给出 n = 3，生成结果为：

[
  "((()))",
  "(()())",
  "(())()",
  "()(())",
  "()()()"
]
```

思路： 一开始我想到的是广搜， 广搜虽好，但是会超内存， 因为要一个栈来存储答案， 于时就改成了深搜，深搜一定要剪枝！



广搜如果保留太多的信息会让内存超出限制

```c
class Solution {
public:
    vector<string> generateParenthesis(int n) {
        vector<string> ans = {"("};
        return  bfs(ans,1,0,n);
    }
    vector<string>  bfs(vector<string> m, int left, int right, int len){    
        vector<string> ans; 
        if(left < right) return ans;
        if(left >= len && right == len) return ans;
        if(left >= right){
            vector<string> temp = m;
            if(left > right){          
                for(int j = 0; j < temp.size(); j++) temp[j] += ")";
                temp = bfs(temp,left, right+1,len);
            }       
            for(int i = 0; i < m.size(); i++) m[i] += "(";
            ans =  bfs(m,left +1 , right,len);
            if(temp.size() != 0) 
                for(int i = 0; i < temp.size(); i++)
                    ans.push_back(temp[i]);
        }
        return m;
    }
};
```

改一下不用临时变量， 还他么超了?

```c
class Solution {
public:
    vector<string> generateParenthesis(int n) {
        vector<string> ans;
        bfs(ans,1,0,n,"(");
        return  ans;
    }
    void  bfs(vector<string>& ans, int left, int right, int len, string cur){    
        if(left < right) return;
        if(left >= len && right == len){
            ans.push_back(cur);
            return ;
        } 
        if(left >= right){
            if(left != right)  
                bfs(ans,left, right+1, len,cur + ")");
            bfs(ans,left+1,right, len, cur + "(");
        }
        return;
    }
};
```

再改！！

只有改dfs了, dfs的剪枝很重要

```c
class Solution {
public:
    vector<string> generateParenthesis(int n) {
        vector<string> ans;
        dfs(ans,n,"(");
        return  ans;
    }
    void  dfs(vector<string>& ans, int len, string cur){    
        if(!valid(len, cur)) return;
        if(valid(len, cur) == 1)
             ans.push_back(cur); 
        dfs(ans, len, cur+ "(");
        dfs(ans, len, cur+ ")");
    }
    int valid(int len, string cur){
        int left, right;
        right = left = 0;
        for(int i = 0; i < cur.length(); i++){
            if(cur[i] == '(') left++;
            else right++;
            //剪枝
            if(left > len  || left < right) return 0;
        }
        if(left == len && right == len) return 1;
        return 2;
    }

};
```



#### 23.合并K个排序链表

>  合并 k 个排序链表，返回合并后的排序链表。请分析和描述算法的复杂度。

```c
输入:
[
  1->4->5,
  1->3->4,
  2->6
]
输出: 1->1->2->3->4->4->5->6
```

解题思路， 每次将链表合并到一个总的链表上

时间复杂度分析：

前两个合并， 一共有n个节点，评分 n/k 个节点每条，

前两条合并为 n*2 / k ， 之后每次合并都要加上之前的

x + x + 2x + x + 3x  + x + 4x + x + ........(k-1)x + x = kx + k^2 / 2 x = k^2 * x = k ^2 * (n*2/k) = kx

```c
/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode(int x) : val(x), next(NULL) {}
 * };
 */
class Solution {
public:
    ListNode* mergeKLists(vector<ListNode*>& lists) {
        if(lists.size() == 0) return NULL;
        ListNode* ans = new ListNode(0); 
        for(int i = 0; i < lists.size(); i++){
            if(!i) ans = lists[i];
            else {
                ListNode* head = new ListNode(0);
                ListNode* index = new ListNode(0);
                head = index;
                while(lists[i] != NULL || ans!= NULL){
                        if(lists[i] == NULL){
                            index->next = ans;
                            break;
                        }else if(ans == NULL){
                            index->next = lists[i];
                            break;
                        }else if(ans->val >= lists[i]->val){
                            index->next = lists[i];
                            lists[i] = lists[i]->next;
                        }else{
                            index->next = ans;
                            ans = ans->next;
                        }
                        index = index->next;
                }
                ans = head->next;
            }      
        }
        return ans;
    }
};
```





####  24.两两交换链表 todo

给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。

你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。

 

示例:

给定 1->2->3->4, 你应该返回 2->1->4->3.



```c
/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode(int x) : val(x), next(NULL) {}
 * };
 */
class Solution {
public:
     ListNode* swapPairs(ListNode* head) {
        ListNode *res = new ListNode(0),*tmp = res;
        res->next = head;
        while(tmp->next != NULL && tmp->next->next != NULL) {
            ListNode *start = tmp->next,*end = tmp->next->next;
            tmp->next = end;
            start->next = end->next;
            end->next = start;
            tmp = start;
        }
        return res->next;
    }

};
```





#### 25.k个一组翻转链表

>  给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。

k 是一个正整数，它的值小于或等于链表的长度。

如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。

```c
给定这个链表：1->2->3->4->5

当 k = 2 时，应当返回: 2->1->4->3->5

当 k = 3 时，应当返回: 3->2->1->4->5
```

说明 :

你的算法只能使用常数的额外空间。
你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。

思路： 其实就是暴力

```c
/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode(int x) : val(x), next(NULL) {}
 * };
 */
class Solution {
public:
    ListNode* reverseKGroup(ListNode* head, int k) {   
        ListNode * index = head;   
        bool flag = 0;
        for(int i = 0; i < k; i ++ ){
            if(index == NULL) break;
            else index = index->next;
            if(i == k -1 )  flag = 1;  
        }
        ListNode* pre = NULL;
        ListNode* next = NULL;
        ListNode* temp = head;
        if(flag){       
            while(head != index){
                next = head->next;
                head->next = pre;
                pre = head;
                head = next;
            }
            temp->next = reverseKGroup(index,k);
        }else {
            pre = head;
        }    
        return pre;
    }
};
```



#### 26. 下一个排列

123的全排列为

123 	132 	213	231 	312	321

123的下一排列为132， 321没有所以为123



直接思路：

从后面往前找，先找出最大的索引 k 满足 nums[k] < nums[k+1]，如果不存在，就翻转整个数组；
从后面往前找，再找出另一个最大索引 l 满足 nums[l] > nums[k]；一定有一个的
交换 nums[l] 和 nums[k]；
最后翻转 nums[k+1:]。

思路： 这道题主要理解排列的意思， 翻译过来就是上面的解法， 

```c
class Solution {
public:
    void nextPermutation(vector<int>& nums) {
        if(nums.size() <= 1) return;
        for(int i = nums.size() - 1 ; i >= 0; i--){
            if(!i) reverse(nums, 0, nums.size() - 1);
            //第一个前面大的
            if(i && nums[i] > nums[i-1]){
                for(int j = nums.size() - 1; j >= 0; j --){
                    if(nums[j] > nums[i - 1]){
                        int temp = nums[j];
                        nums[j] = nums[ i - 1];
                        nums[i - 1] = temp;
                        reverse(nums,i,nums.size() - 1);
                        return ;
                    }
                }

            }
        }
    }

    void reverse(vector<int>& nums,int start, int end){
       int temp;
       for(int i = start; i <= (end + start) / 2; i++){
           temp = nums[i];
           nums[i] = nums[end - i + start];
           nums[end - i + start]  = temp;
       }
    }
};
```



#### 27. 最长有效括号

>  给定一个只包含 '(' 和 ')' 的字符串，找出最长的包含有效括号的子串的长度。

```
输入: "(()"
输出: 2
解释: 最长有效括号子串为 "()"
```

解题思路：

从左边向右遍历， 如果当前的左括号小于右括号证明是没有用的，从新开始计数， 如果当前的左括号等于右括号是一个有效值，判断是否为最大值， 右边一样！



```c
class Solution {
public:
    int longestValidParentheses(string s) {
        int ri, le, ans;
        ri = le = ans = 0;   
        for(int i = 0 ; i < s.length(); i++){
            if(s[i] == '(') le++;
            else ri++;
            if(le == ri) ans = ans > le * 2 ? ans : le * 2;
            else if(le < ri) le = ri = 0;
        }
        ri = le = 0;
        for(int i = s.length() - 1; i >= 0; i--){
            if(s[i] == '(') le++;
            else ri++;
            if(le == ri) ans = ans > le * 2 ? ans : le * 2;
            else if(le > ri) le = ri = 0;
        }
        return ans;
    }
};
```



#### 28. 搜索旋转排序数组

> 假设按照升序排序的数组在预先未知的某个点上进行了旋转。

( 例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2] )。

搜索一个给定的目标值，如果数组中存在这个目标值，则返回它的索引，否则返回 -1 。

你可以假设数组中不存在重复的元素。

你的算法时间复杂度必须是 O(log n) 级别。

```c
输入: nums = [4,5,6,7,0,1,2], target = 0
输出: 4
```

解题思路：

这题主要理解的问题是，一个旋转之后的数组，拆成两半， 总有一个半是有效的！

```c
class Solution {
public:
    int search(vector<int>& nums, int target) {
        if(nums.size() == 0) return -1;
        return devideSearch(nums, 0, nums.size() - 1, target);
    }

    int devideSearch(vector<int>& nums, int le, int ri , int target){
        
        if(ri == le) return nums[ri] == target? ri : -1;
        // if(ri - le == 1){
        //     if(nums[ri] == target) return  ri;
        //     else if( nums[le] == target) return le;
        //     return -1;
        // }        
        int mid = (le + ri) / 2;
       // cout << le << " " << ri << " " << mid << endl;
        // 左边有序
        if(nums[le] < nums[mid] ){
            if( nums[le] <= target && nums[mid] >= target)
                return devideSearch(nums, le, mid,target);
            else return devideSearch(nums, mid + 1, ri,target);
            
        }else {
            if(nums[mid + 1] <= target && nums[ri] >= target)
                return devideSearch(nums,mid + 1, ri,target);
            else return devideSearch(nums, le, mid,target);            
        }
    }



};
```



#### 29. 在排序数组中查找元素的第一个和最后一个位置

给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。

你的算法时间复杂度必须是 O(log n) 级别。

如果数组中不存在目标值，返回 [-1, -1]。

```c
输入: nums = [5,7,7,8,8,10], target = 8
输出: [3,4]
```


```c
输入: nums = [5,7,7,8,8,10], target = 6
输出: [-1,-1]
```

解题思路：
 这道题关键还是二分搜索， 当搜索到答案以后， 往两边找相同的！！！！

```c
class Solution {
public:
    vector<int> searchRange(vector<int>& nums, int target) {
        if(nums.size()==0) {
             vector<int> temp = {-1,-1};
            return temp;
        }
        vector<int> ans;
      
        int mid = search(nums, 0, nums.size() - 1, target);
        if(mid == -1 ) {
            ans.push_back(mid);
            ans.push_back(mid);
            return ans;
        } 
        else {
            int i;
            for(i = mid; i >= 0; i--){
                if(nums[i] != target) break; 
            }
            ans.push_back(i+1);
            for(i = mid; i < nums.size(); i++){
                if(nums[i] != target) break; 
            }
            ans.push_back(i-1);  
        }
        return ans;
    }

    int search(vector<int>& nums, int le, int ri, int target){
        if(target < nums[0] || target > nums[ri]) return -1;
        if(ri == le) return target == nums[le] ? le : -1;
        int mid = (le + ri) / 2;
        if(nums[mid] < target) return search(nums,mid + 1, ri, target);
        else return search(nums,le, mid, target);
    
    }

};
```



#### 30.组数之和

>  给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。

candidates 中的数字可以无限制重复被选取。

说明：

所有数字（包括 target）都是正整数。
解集不能包含重复的组合。 

```c
输入: candidates = [2,3,6,7], target = 7,
所求解集为:
[
  [7],
  [2,2,3]
]
```

解题思路： 我觉得这个回溯算法好简单呀！！！

```c
class Solution {
typedef vector<int>  vint;
public:
    vector<vector<int>> combinationSum(vector<int>& candidates, int target) {
        vector<vector<int>> ans;
        vint temp;
        //sort(candidates.begin(), candidates.end());  
        search(candidates,ans, target, 0, temp);
        return ans;
    }
    void search(vint& c,vector<vint>& ans, int target, int len, vint cur){
        if(len == c.size()) return;
        int sum = addup(cur);
        if(sum > target) return;
        if(sum == target){
            for(int i = 0 ; i < ans.size(); i++){
                if(ans[i] == cur) return;
            }
            ans.push_back(cur);
            return;
        } 
        if(len < c.size()){
            //cout <<len << " " <<  sum<<endl;
            search(c, ans, target, len+1, cur);
            cur.push_back(c[len]);       
            search(c,ans, target, len, cur); // 这里说可以重复
        }
    }
    int addup(vint a){
        int sum = 0;
        for(int i = 0; i < a.size(); i++){
                sum += a[i];
        }
        return sum;
    }
};
```

#### 31.旋转图形

> 给定一个 n × n 的二维矩阵表示一个图像。将图像顺时针旋转 90 度。

说明：

你必须在原地旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要使用另一个矩阵来旋转图像。

```c
给定 matrix = 
[
  [1,2,3],
  [4,5,6],
  [7,8,9]
],

原地旋转输入矩阵，使其变为:
[
  [7,4,1],
  [8,5,2],
  [9,6,3]
]
```

解题思路：先转置， 然后 在进行横排的移动。

```c
class Solution {
public:
    void rotate(vector<vector<int>>& matrix) {
        if(matrix.size() == 0) return ;
        int x = matrix[0].size();
        int y = matrix.size();
        int temp;
        for(int i = 0; i < y; i ++){
            for(int j = 0; j < i; j ++ ){
                temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        for(int i = 0 ; i < x/2; i++){
            for(int j = 0; j < y; j++){
                temp = matrix[j][i];
                matrix[j][i] = matrix[j][x - i - 1];
                matrix[j][x - i - 1] = temp;
            }
        }
    }
};
```



#### 32. 字母异位词分组

> 给定一个字符串数组，将字母异位词组合在一起。字母异位词指字母相同，但排列不同的字符串。

```c
输入: ["eat", "tea", "tan", "ate", "nat", "bat"],
输出:
[
  ["ate","eat","tea"],
  ["nat","tan"],
  ["bat"]
]
说明：

所有输入均为小写字母。
不考虑答案输出的顺序。
```

解题思路： 可以说是暴力求解了， 把每一个单词都排序，排好序之后， map 一下

```c
class Solution {
    typedef vector<vector<string> > vvstr;
    typedef map<string, vector<string> > str2vstr;
public:
    vector<vector<string>> groupAnagrams(vector<string>& strs) {
        vvstr ans;
        str2vstr str2vstr; for_ans;
        for(int i = 0 ; i < strs.size(); i++){
            string temp = strs[i];
            sort(strs[i].begin(), strs[i].end());
            for_ans[strs[i]].push_back(temp);
        }        
        for(str2vstr::iterator i = for_ans.begin(); i != for_ans.end(); i++){
            ans.push_back(i->second);
        }
        return ans;
    }    
};
```



#### 33.最大连续子数组

> 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。

```c
输入: [-2,1,-3,4,-1,2,1,-5,4],
输出: 6
解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
```


进阶:

如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的分治法求解。

解题思路： 使用dp的思想， 如果当前的连续和还是大于 0 的， 可以加到后面， 如果当前的连续和小于0了， 加到后面后面反而减小了， 所以不需要加到后面了！

```c
class Solution {
public:
    int maxSubArray(vector<int>& nums) {
        int ans, cur;
        cur =  0;
        ans = (unsigned)(-1)>>1;
        ans = ~ans;
       for(int i = 0 ; i < nums.size(); i ++ ){
             cur += nums[i];
             ans = cur > ans ? cur : ans;
             if( cur < 0)
                cur = 0;           
       }
        return ans;
    }
};
```



分治法：暂无





#### 34. 最大连续和

> 给定一个非负整数数组，你最初位于数组的第一个位置。数组中的每个元素代表你在该位置可以跳跃的最大长度。判断你是否能够到达最后一个位置。

```c
输入: [2,3,1,1,4]
输出: true

解释: 我们可以先跳 1 步，从位置 0 到达 位置 1, 然后再从位置 1 跳 3 步到达最后一个位置。


```

解题思路： 直接暴力就可以了

```c
class Solution {
public:
    bool canJump(vector<int>& nums) {
        if(nums.size() == 0) return 0;
        int arr = 0;
        for(int i = 0 ; i < nums.size(); i++){
            if(i == nums.size() - 1) return 1;
            arr = i + nums[i] > arr ? i + nums[i] : arr;
            if(arr <= i ) return 0;
        }
        return 1;        
    }
};
```



#### 35. 合并区间

给出一个区间的集合，请合并所有重叠的区间。

```c
输入: [[1,3],[2,6],[8,10],[15,18]]
输出: [[1,6],[8,10],[15,18]]
解释: 区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
```

解题思路： 一开始想到的想到的是给 vector 的开始排序， 然后遍历一遍就可以知道结束的地方

后来发现， 这个是可以分开的， start 和 end 分开来也行！

```c++
class Solution {
typedef vector<vector<int> > vvint;
typedef vector<int> vint;
public:
    vector<vector<int>> merge(vector<vector<int>>& intervals) {
        int len = intervals.size();
        vvint ans;
        if(len == 0) return ans;
        int start[len], end[len];
        for(int i = 0; i < len; i++){
            start[i] = intervals[i][0];
            end[i] = intervals[i][1];
        }
        sort(start, start + len);
        sort(end, end+ len);
        for(int i = 0, j = 0; i < len; i ++){
            if(i == len-1 || start[i + 1] > end[i]){//最后一个直接放
                vint tmp;
                tmp.push_back(start[j]);
                tmp.push_back(end[i]);
                ans.push_back(tmp);
                j = i + 1;
            }
        }
        return ans;
    }
};
```







#### 34. 不同路径

一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。

机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。

问总共有多少条不同的路径？

![](https://raw.githubusercontent.com/Fierygit/picbed/master/20200210100443.png)

```c
例如，上图是一个7 x 3 的网格。有多少可能的路径？

说明：m 和 n 的值均不超过 100。
```

递归求解： 换来的是超时。。。

```c++
class Solution {
private:
    int m,n;
public:
    int uniquePaths(int m, int n) {
        this->m = m;
        this->n = n;
        int ans = 0;
        search(ans,1,1);
        return ans;
    }
    void search(int &ans, int curm, int curn){
        if(curm > m || curn > n) return;
        if(curn == n && curm == m){
            ans++;
            return;
        }
        if(curm < m) search(ans,curm + 1,curn);
        if(curn < n) search(ans,curm, curn + 1);
    }
};
```

换用dp：

```c++
class Solution {
public:
    int uniquePaths(int m, int n) {
        int dp[m][n] = {0};
        for(int i = 0 ; i < m; i++){
            for(int j = 0; j < n; j++){
                if(j == 0 && i == 0) dp[i][j] = 1;
                else if(j > 0 && i > 0)    dp[i][j] = dp[i][j - 1] + dp[i-1][j];
                else if(j == 0)  dp[i][j]= dp[i-1][j];
                else if(i == 0) dp[i][j] = dp[i][j-1];
                }
            }
        
        return dp[m-1][n-1];
    }
};
```

#### 35. 不同路径Ⅱ

题目一样，但是加上一个障碍物的存在， 思路一样的， 这个测试样例坑人的， 返回型是int的， 但是中间值是超过int的， 坑！！！

```c++
class Solution {
    typedef vector<vector<int>> vvint;
public:
    int uniquePathsWithObstacles(vector<vector<int>>& obstacleGrid) {
        if(obstacleGrid.size() == 0) return 0;
        int m = obstacleGrid.size();
        int n = obstacleGrid[0].size();
        long dp[m][n] = {0};
        for(int i = 0 ; i < m; i++){
            for(int j = 0; j < n; j++){
                dp[i][j] = 0;
                if(obstacleGrid[i][j]) continue;
                if(j == 0 && i == 0) dp[i][j] = 1;
                else if(j > 0 && i > 0)    dp[i][j] = dp[i][j - 1] + dp[i-1][j];
                else if(j == 0)  dp[i][j]= dp[i-1][j];
                else if(i == 0) dp[i][j] = dp[i][j-1];
               if(i > 8 && j > 8) cout << i << " " << j << " " << dp[i][j] <<endl;
            }
        }
        return dp[m-1][n-1];

    }
};
```



#### 36.最小路径和

给定一个包含非负整数的 m x n 网格，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。

说明：每次只能向下或者向右移动一步。

```
输入:
[
  [1,3,1],
  [1,5,1],
  [4,2,1]
]
输出: 7
解释: 因为路径 1→3→1→1→1 的总和最小。
```

解题思路： 这题也是dp 挺简单的， 值得注意的是  vvint 的速度执行 会 比  int 快！

```c++
class Solution {
typedef vector<vector<int>> vvint;
typedef vector<int> vint;
public:
    int minPathSum(vector<vector<int>>& grid) {
        int y = grid.size();
        if(y == 0) return 0;
        int x = grid[0].size();
        vvint dp(y,vector<int>(x));
        for(int i = 0; i < y; i++){
            for(int j = 0; j < x; j++){
                dp[i][j] = 0;
                if(i ==0 && j == 0) dp[i][j] = grid[i][j];
                else if(i > 0 && j > 0) dp[i][j] = min(dp[i][j-1],dp[i-1][j]) + grid[i][j];
                else if(i == 0 ) dp[i][j] = dp[i][j-1] + grid[i][j];
                else  dp[i][j] = dp[i-1][j] + grid[i][j];
            }
        }
        return dp[y-1][x-1];
    }
};
```



#### 37.验证给定的字符串是否可以解释为十进制数字。

```c++
"0" => true
" 0.1 " => true
"abc" => false
"1 a" => false
"2e10" => true
" -90e3   " => true
" 1e" => false
"e3" => false
" 6e-1" => true
" 99e2.5 " => false
"53.5e93" => true
" --6 " => false
"-+3" => false
"95a54e53" => false
```

编译原理的自动机！！！ 状态难画！ 

```c
class Solution {
public:
    bool isNumber(string s) {
        int a[9][4]={{1,-1,8,2},{1,4,3,-1},{1,-1,8,-1},{5,4,-1,-1},{7,-1,-1,6},{5,4,-1,-1},{7,-1,-1,-1},{7,-1,-1,-1},{3,-1,-1,-1}};
        int state=0;
        int i=0;
        while(s[i]==' '){
            i+=1;
        }
        int j=s.length()-1;
        while(s[j]==' '){
            j-=1;
        }
        while(i<=j){
            if(s[i]>='0'&&s[i]<='9') state=a[state][0];
            else if(s[i]=='e') state=a[state][1];
            else if(s[i]=='.') state=a[state][2];
            else if(s[i]=='+'||s[i]=='-') state=a[state][3];
            else return false;
            if(state==-1) return false;
            i+=1;
        }
        if(state==7||state==1||state==5||state==3) return true;
        return false;
    }
};
```





#### 38. 爬楼梯

> 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。每次你可以爬 1 或 2 个台阶。你有多少种不同的方可以爬到楼顶呢？

注意：给定 n 是一个正整数。

```
输入： 2
输出： 2
解释： 有两种方法可以爬到楼顶。

1.  1 阶 + 1 阶  2 阶
```

解题思路： 简单的dp问题

```c++
class Solution {
public:
    int climbStairs(int n) {
        if(n == 1) return 1;
        else if(n == 2) return 2;
        int a[n];
        a[0] = 1; a[1] = 2;
        for(int i = 2; i < n; i++){
            a[i] = a[i-1] + a[i-2];
        }
        return a[n-1];
    }
};
```



#### 39. 单词最短距离

> 给定两个单词 word1 和 word2，计算出将 word1 转换成 word2 所使用的最少操作数 。

你可以对一个单词进行如下三种操作：

插入一个字符
删除一个字符
替换一个字符

```c++
输入: word1 = "horse", word2 = "ros"
输出: 3
解释: 
horse -> rorse (将 'h' 替换为 'r')
rorse -> rose (删除 'r')
rose -> ros (删除 'e')
```

解题思路： 刚拿到这道题时完全没有思路可言， 我是看了题解才知道怎么做的！

还是dp， 又是dp！

dp [i] [j]: 表示  `word1` 到 `i` 位置转换成 `word2` 到 `j` 位置需要最少步数 

当 word1[i] == word2[j]，dp[i] [j] = dp[i-1] [j-1]；

当 word1[i] != word2[j]，dp[i][j] = min(dp[i-1] [j-1], dp[i-1] [j], dp[i] [j-1]) + 1

其中，dp[i-1] [j-1] 表示替换操作，dp[i-1] [j] 表示删除操作，dp[i][j-1] 表示插入操作。

![](https://raw.githubusercontent.com/Fierygit/picbed/master/20200214163734.png)

明白后还是挺简单的！

```c++
class Solution {
public:
    int minDistance(string word1, string word2) {
        int x = word1.length();
        int y = word2.length();
        if(x == 0 && y == 0) return 0;
        int dp[x+1][y+1] = {0};
        for(int i = 1; i <= y; i++) dp[0][i] = dp[0][i-1] + 1;
        for(int i = 1; i <= x; i++) dp[i][0] = dp[i-1][0] + 1;

        for(int i = 1; i <= x; i ++){
            for(int j = 1; j <= y; j++){
                if(word1[i-1] == word2[j-1]) dp[i][j] = dp[i-1][j-1];
                else dp[i][j] = min(min(dp[i-1][j-1], dp[i-1][j]) , dp[i][j-1]) + 1;
            }
        }
        return dp[x][y];
    }
};
```



#### 40.颜色分类

> 给定一个包含红色、白色和蓝色，一共 n 个元素的数组，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。

此题中，我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。

```
输入: [2,0,2,1,1,0]
输出: [0,0,1,1,2,2]
```

解题思路： 三个指针， 分为三部分！

```c++
class Solution {
public:
    void sortColors(vector<int>& nums) {
        int p0,cur,p2, len, tmp;
        len = nums.size();
        cur = p0 = 0;
        p2 = len - 1;        
        while(cur <= p2){
            switch(nums[cur]){
                case 1:
                    cur++;
                    break;
                case 0:
                    tmp = nums[cur];
                    nums[cur++] = nums[p0];
                    nums[p0++] = tmp;
                    break;
                case 2:
                    tmp = nums[p2];
                    nums[p2--] = nums[cur];
                    nums[cur] = tmp;
                    break;
            }    
        }   
    }
};
```



#### 42. 单词搜索

>  给定一个二维网格和一个单词，找出该单词是否存在于网格中。

单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。

```
board =
[
  ['A','B','C','E'],
  ['S','F','C','S'],
  ['A','D','E','E']
]

给定 word = "ABCCED", 返回 true.
给定 word = "SEE", 返回 true.
给定 word = "ABCB", 返回 false.
```

解题思路： 这道题最难的是， 回溯，   **找过的路不能再找， 所以临时改变一下路径**， 搜索完了后==重新恢复数据==，记得多看这道题， 经常出现！！！

```c++
class Solution {
public:
    bool exist(vector<vector<char>>& board, string word) {
        for(int i = 0; i < board.size(); i++){
            for(int j = 0; j < board[0].size(); j++){
                if(backtrack(board, word, 0, i , j)){ // 从二维表格的每一个格子出发
                    return true;
                }
            }
        }
        return false;
    }
private:
    bool backtrack(vector<vector<char>>& board, string& word, int wordIndex, int x, int y){
        if( board[x][y] != word[wordIndex]){ // 当前位的字母不相等，此路不通
            return false;
        }
        if(word.size() - 1  == wordIndex){ // 最后一个字母也相等, 返回成功
            return true;
        }
        char tmp = board[x][y]; 
        board[x][y] = 0; // 避免该位重复使用
        wordIndex++;
        if((x > 0 && backtrack(board, word, wordIndex, x - 1, y)) // 往左走
        || (y > 0 && backtrack(board, word, wordIndex, x, y - 1)) // 往上走
        || (x < board.size() - 1 && backtrack(board, word, wordIndex, x + 1, y)) // 往右走
        || (y < board[0].size() - 1 && backtrack(board, word, wordIndex, x, y + 1))){ // 往下走
            return  true; // 其中一条能走通，就算成功
        }
        board[x][y] = tmp; // 如果都不通，则回溯上一状态
        return false;
    }
};
```



####  43. pow(*x*, *n*)

> 实现 pow(*x*, *n*) ，即计算 x 的 n 次幂函数。

```
输入: 2.00000, 10
输出: 1024.00000
```

解题思路： 

这道题我认为关键的地方是分治法， 把底层会重复计算的数据删除掉，不去计算！

```c++
class Solution {
public:
    double myPow(double x, int n) {
        if(n == 0) return 1;
        int flag = n < 0 ? 1 : 0;
        long m = n;
        m = m < 0 ? -m : m;
        if(m == 1) return flag ? 1/x : x;
        else if(m == 2){
            return flag ? 1 / (x * x) : x * x;
        }else {
            double temp = myPow(x, (flag ? - m/2: m/2));
            if(m % 2 == 0){             
                return temp * temp;
            }else{
                return temp * temp  * (flag ? 1/x : x);
            }
        }
    }
};
```



#### 44.八皇后问题

>  n 皇后问题研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。

 ![](https://raw.githubusercontent.com/Fierygit/picbed/master/20200223195130.png)

上图为 8 皇后问题的一种解法。

给定一个整数 n，返回所有不同的 n 皇后问题的解决方案。

每一种解法包含一个明确的 n 皇后问题的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。

解题思路： 回溯， 当此时是可以求解的， 把当前值置为1， 搜索完后恢复状态， 写了1.30 h

```c++
class Solution {
    typedef vector<vector<string>> vvstr;
    typedef vector<vector<int>> vvint;
    typedef vector<int> vint;
    typedef vector<string> vstr;
public:
    vector<vector<string>> solveNQueens(int n) {
        vvint m(n,vint(n));
        vvstr ans;
        search(0, n,ans, m);
        return ans;
    }
    void search(int line,int n, vvstr& ans, vvint& m){
        if(line == n){
            string temp;
            vstr an;
            for(int i = 0; i < n; i++){
                temp = "";
                for(int j = 0; j < n; j++){
                    if(m[i][j] == 0) temp += ".";
                    else temp += "Q"; 
                }
                an.push_back(temp);
            }
            ans.push_back(an);
        }
        for(int i = 0; i < n; i++){  // 对于这一行的每一个作为答案
           // cout << line << " i: " << i  << endl;
            for(int j = 0; j < n; j++){ 
                if(m[j][i] == 1) break; // 检查列
                // 斜
                int x1 = line - (i - j);
                if( x1 >= 0 &&  x1 < n &&  m[x1][j] == 1 ) break;

                x1 = line + (i - j);
                if(x1 >= 0 && x1 < n && m[x1][j] == 1) break; 
               // cout << " " << j;        
                if(j == n - 1){ //列检查完毕
                   cout << endl; 
                    m[line][i] = 1;
                    search(line + 1, n, ans, m);
                    m[line][i] = 0;
                }                  
            }
             //cout << endl;       
        }
    }
};
```



#### 44.  二叉树的中序遍历

给定一个二叉树，返回它的中序遍历。

示例:

```c++
输入: [1,null,2,3]
   1
    \
     2
    /
   3

输出: [1,3,2]
```

解题思路： 递归太简单了

```c++
/**
 * Definition for a binary tree node.
 * struct TreeNode {
 *     int val;
 *     TreeNode *left;
 *     TreeNode *right;
 *     TreeNode(int x) : val(x), left(NULL), right(NULL) {}
 * };
 */
class Solution {
    typedef vector<int> vint;
public:
    vector<int> inorderTraversal(TreeNode* root) {
            vint ans;
            traverse(root,ans);
            return ans;
    }
    void traverse(TreeNode* node, vint& ans){
        if(node != NULL){
            traverse(node->left, ans);
            ans.push_back(node->val);
            traverse(node->right, ans);
        }
    }
};
```

非递归， 多看看！

```c++
/**
 * Definition for a binary tree node.
 * struct TreeNode {
 *     int val;
 *     TreeNode *left;
 *     TreeNode *right;
 *     TreeNode(int x) : val(x), left(NULL), right(NULL) {}
 * };
 */
class Solution {
typedef vector<int> vint;

public:
//递归是使用了函数的栈， 迭代的话模拟出一个栈给它  
    vector<int> inorderTraversal(TreeNode* root) {
          vector<int> ans;
        TreeNode* p = root;
        stack<TreeNode*> st;
        while(p || !st.empty()){
            while(p){
                st.push(p);
                p = p->left;
            }
            p = st.top();
            st.pop();
            ans.push_back(p->val);
            p = p->right;
        }
        return ans;
    }
};
```



#### 45.不同的二叉搜索树


给定一个整数 *n*，求以 1 ... *n* 为节点组成的二叉搜索树有多少种？

**示例:**

```c++
输入: 3
输出: 5
解释:
给定 n = 3, 一共有 5 种不同结构的二叉搜索树:

   1         3     3      2      1
    \       /     /      / \      \
     3     2     1      1   3      2
    /     /       \                 \
   2     1         2                 3
```

解题思路：

这道题主要推导dp的递推式子！

问题是计算不同二叉搜索树的个数。为此，我们可以定义两个函数：

- G(n): 长度为n的序列的不同二叉搜索树个数。

- F(i,n): 以i为根的不同二叉搜索树个数(1 \leq i \leq n1≤i≤n)。

![](https://raw.githubusercontent.com/Fierygit/picbed/master/20200301195813.png)

![](https://raw.githubusercontent.com/Fierygit/picbed/master/20200301195932.png)

```c++
class Solution {
public:
    int numTrees(int n) {
        int g[n+1] = {0};
        g[0] = g[1] = 1;
        for(int i = 2; i <= n; i++){
            for(int j = 1; j <= i; j ++){
                g[i] += g[j-1] * g[i-j];
            }
        }
        return g[n];
    }
};
```



#### 46.验证二叉搜索树

给定一个二叉树，判断其是否是一个有效的二叉搜索树。

假设一个二叉搜索树具有如下特征：

- 节点的左子树只包含小于当前节点的数。
- 节点的右子树只包含大于当前节点的数。
- 所有左子树和右子树自身必须也是二叉搜索树。

```
输入:
    2
   / \
  1   3
输出: true
```

思路不难： 递归处理， 中间的数据会超int， 带取值域搜索

或者  中序遍历， 带一个值搜索！！！


```c
/**
 * Definition for a binary tree node.
 * struct TreeNode {
 *     int val;
 *     TreeNode *left;
 *     TreeNode *right;
 *     TreeNode(int x) : val(x), left(NULL), right(NULL) {}
 * };
 */
class Solution {
public:
    bool isValid(TreeNode* current,long left,long right){
        if(current==nullptr) return true;
        else if(current->val<=left||current->val>=right) return false;
        return isValid(current->left,left,current->val)
            &&isValid(current->right,current->val,right);
    }
    bool isValidBST(TreeNode* root) {
        return isValid(root,LONG_MIN,LONG_MAX);
    }
};
```



#### 47. 对称二叉树

> 给定一个二叉树，检查它是否是镜像对称的。

例如，二叉树 [1,2,2,3,4,4,3] 是对称的。

```
    1
   / \
  2   2
 / \ / \
3  4 4  3
```

解题思路： 

基于递归的解决方案！ 这个较为简单！

```c++
/**
 * Definition for a binary tree node.
 * struct TreeNode {
 *     int val;
 *     TreeNode *left;
 *     TreeNode *right;
 *     TreeNode(int x) : val(x), left(NULL), right(NULL) {}
 * };
 */
class Solution {
public:
    bool isSymmetric(TreeNode* root) {
        if(root == NULL) return true;
        return isValid(root->left,root->right);
    }

    bool isValid(TreeNode* left, TreeNode* right){
        if(left == NULL && right == NULL) return true;
        if(left == NULL || right == NULL) return false;
        if(left->val != right-> val) return false;
        return isValid(left->left, right->right) && 
            isValid(left->right, right->left);
    }
};
```

非递归的方法， 使用一个栈来模拟方法的递归过程：

```c++
/**
 * Definition for a binary tree node.
 * struct TreeNode {
 *     int val;
 *     TreeNode *left;
 *     TreeNode *right;
 *     TreeNode(int x) : val(x), left(NULL), right(NULL) {}
 * };
 */
#include <stack>
class Solution {
public:
    bool isSymmetric(TreeNode* root) {
        if(root == NULL) return true;
        stack<TreeNode*> le, ri;
        le.push(root->left);
        ri.push(root->right);
        TreeNode * t1, * t2;
        while(true){
            if(le.empty() && ri.empty()) return true;
            if(le.empty() || ri.empty()) return false;
            t1 = le.top(); t2 = ri.top();
            le.pop(); ri.pop();
            if(t1 == NULL && t2 == NULL){
                continue;
            } 
            if(t1== NULL || t2 == NULL) return false;
            if(t1->val != t2->val) return false;
            le.push(t1->right);ri.push(t2->left);
            le.push(t1->left); ri.push(t2->right);
        }
        return true;
    }
};
```



#### 48. 二叉树的层次遍历

给定一个二叉树，返回其按层次遍历的节点值。 （即逐层地，从左到右访问所有节点）。

例如:
给定二叉树: [3,9,20,null,null,15,7],
```c
    3
   / \
  9  20
    /  \
   15   7
返回其层次遍历结果：

[
  [3],
  [9,20],
  [15,7]
]
```

解题思路：

​	按照遍历的思想模拟

```c++
/**
 * Definition for a binary tree node.
 * struct TreeNode {
 *     int val;
 *     TreeNode *left;
 *     TreeNode *right;
 *     TreeNode(int x) : val(x), left(NULL), right(NULL) {}
 * };
 */
class Solution {
typedef vector<vector<int> > vvint;
typedef vector<int> vint;
public:
    vector<vector<int>> levelOrder(TreeNode* root) {
        vvint ans;
        if(root == NULL) return ans;
        vector<TreeNode*> set = {root};
        solve(ans,set);
        return ans;
    }
    void solve(vvint &ans, vector<TreeNode*> set){
        vint tmp;
        for(int i = 0; i < set.size(); i++)
            tmp.push_back(set[i]->val);
        ans.push_back(tmp);
        
        vector<TreeNode*> nodes;
        for(int i = 0; i < set.size(); i++){
            if(set[i]->left) nodes.push_back(set[i]->left);
            if(set[i]->right) nodes.push_back(set[i]->right);
        }
        if(nodes.size() != 0) solve(ans, nodes);
    }
};
```



#### 49. 二叉树的最大深度

难度简单471收藏分享切换为英文关注反馈

给定一个二叉树，找出其最大深度。

二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。

**说明:** 叶子节点是指没有子节点的节点。

**示例：**
给定二叉树 `[3,9,20,null,null,15,7]`，

```
    3
   / \
  9  20
    /  \
   15   7
```

返回它的最大深度 3 。

解题思路： 练手题， 太简单了

```c
/**
 * Definition for a binary tree node.
 * struct TreeNode {
 *     int val;
 *     TreeNode *left;
 *     TreeNode *right;
 *     TreeNode(int x) : val(x), left(NULL), right(NULL) {}
 * };
 */

class Solution {
public:
    int maxDepth(TreeNode* root) {
        int ans = 0;
        solve(ans,0,root);
        return ans;
    }

    void solve(int &ans, int cur, TreeNode* cu){
        ans = max(ans,cur);
        if(cu == NULL) return;
        solve(ans, cur + 1, cu->left);
        solve(ans, cur + 1, cu->right);
    }

};
```



#### 50. 从前序与中序遍历序列构造二叉树

> 根据一棵树的前序遍历与中序遍历构造二叉树。

**注意:**
你可以假设树中没有重复的元素。

例如，给出

```
前序遍历 preorder = [3,9,20,15,7]
中序遍历 inorder = [9,3,15,20,7]
```

返回如下的二叉树：

```
    3
   / \
  9  20
    /  \
   15   7
```

解题思路： 按要求来！

```c
/**
 * Definition for a binary tree node.
 * struct TreeNode {
 *     int val;
 *     TreeNode *left;
 *     TreeNode *right;
 *     TreeNode(int x) : val(x), left(NULL), right(NULL) {}
 * };
 */
class Solution {
    typedef vector<int> vint;
public:
    TreeNode* buildTree(vector<int>& preorder, vector<int>& inorder) {
        return solve(preorder,inorder, 0,inorder.size(), 0,preorder.size());
    }
    TreeNode * solve(vint& pre, vint& ino, int a1, int a2, int b1, int b2){
        if(a1 >= a2) return NULL;
        int first = pre[a1];
        int index;
        TreeNode* root= new TreeNode(first);
        for(int i = b1; i < b2; i ++)
            if(ino[i] == first ){
                index = i;
                break;
            }
        //cout << a1 << " " << a2  << " " << b1 << " " << b2 << endl;
        root->left = solve(pre,ino,a1 + 1, a1 + index - b1 + 1, b1, index);
        root->right = solve(pre,ino,a1 + index - b1 + 1,a2, index + 1, b2);
        return root;
    }
};
```





#### 51. 只出现一次的数字

给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。

你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？

```c++
输入: [2,2,1]
输出: 1
```

解题思路：真的没有想到这么巧， 使用位运算, 相同的异或了之后， 数据不变

```c++
class Solution {
public:
    int singleNumber(vector<int>& nums) {
            int num = 0;
            for(int i = 0; i < nums.size(); i++){
                num ^= nums[i];
            }
            return num;
    }
};
```

#### 52. 环形链表

给定一个链表，判断链表中是否有环。

为了表示给定链表中的环，我们使用整数 `pos` 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 `pos` 是 `-1`，则在该链表中没有环。

快慢指针，快点指针会追上慢的指针！

```c++
/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode(int x) : val(x), next(NULL) {}
 * };
 */
class Solution {
public:
    bool hasCycle(ListNode *head) {
        if(head == NULL) return false;
        ListNode* fir = head;
        ListNode* sec = head;
        while(true){
            if(fir->next != NULL) fir = fir->next;
            if(sec->next != NULL) sec = sec->next;
            if(sec->next != NULL) sec = sec ->next;
            if(fir == sec){// 值有可能重复
                if(fir->next ==NULL) return false;
                else return true;
            }
        }
    }
};
```



#### 53. 环形链表 II

给定一个链表，返回链表开始入环的第一个节点。 如果链表无环，则返回 `null`。

为了表示给定链表中的环，我们使用整数 `pos` 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 `pos` 是 `-1`，则在该链表中没有环。

解题思路： 我一开始想到的是让一个节点先走，然后面那个追相等的时候，判断有环

```c++
/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode(int x) : val(x), next(NULL) {}
 * };
 */
class Solution {
public:
    ListNode* detectCycle(ListNode* head) {
	ListNode* fastPtr=head, *slowPtr=head;
	// 让fast与slow指针第一次相遇
	while (fastPtr!=NULL && fastPtr->next!=NULL){
		fastPtr = fastPtr->next->next;
		slowPtr = slowPtr->next;
		if (fastPtr==slowPtr){
			// 从相遇点再走“非环部分长度”一定可以再次走到环起点
			fastPtr = head;
			while (fastPtr != slowPtr){
				fastPtr = fastPtr->next;
				slowPtr = slowPtr->next;
			}
			return fastPtr;
			break;
		}
	}
	return nullptr;
}
};
```







#### 54. LRU缓存机制

运用你所掌握的数据结构，设计和实现一个  LRU (最近最少使用) 缓存机制。它应该支持以下操作： 获取数据 get 和 写入数据 put 。

获取数据 get(key) - 如果密钥 (key) 存在于缓存中，则获取密钥的值（总是正数），否则返回 -1。
写入数据 put(key, value) - 如果密钥不存在，则写入其数据值。当缓存容量达到上限时，它应该在写入新数据之前删除最近最少使用的数据值，从而为新的数据值留出空间。

进阶:

你是否可以在 O(1) 时间复杂度内完成这两种操作？

```c++
LRUCache cache = new LRUCache( 2 /* 缓存容量 */ );

cache.put(1, 1);
cache.put(2, 2);
cache.get(1);       // 返回  1
cache.put(3, 3);    // 该操作会使得密钥 2 作废
cache.get(2);       // 返回 -1 (未找到)
cache.put(4, 4);    // 该操作会使得密钥 1 作废
cache.get(1);       // 返回 -1 (未找到)
cache.get(3);       // 返回  3
cache.get(4);       // 返回  4
```

解题思路：

模仿 redis 的设计思路，  索引用map， 然后移动使用 list， 找基于 map O（1）时间复杂度， 移动基于链表， 也是O（1）时间复杂度！

```c++
class LRUCache {  
    struct List{
        int key,val;
        List* next, *pre;
        List(int key, int v){
            this->val = v;
            this->key = key;
        }
    };
    int capacity, max;
    List *head, *tail;
    map<int, List*> cap;

public:
    LRUCache(int capacity) {
        this->max = capacity;
        this->capacity = 0;
        head = tail = NULL;
    }
    void mv2head(List* tmp){
        if(tmp != head){                 // 如果不是头结点, 头结点不用移动
            List *p = tmp->pre;
            if(tmp == tail){
                p->next = NULL;
                tail = p;     
            }else{
                p->next = tmp->next;
                tmp->next->pre = p;
            }
            //放到前面
            tmp->next = head;
            head->pre = tmp;
            head = tmp;
        } 
    }
    void rm(){
        if(this->capacity > this->max){
            cap[tail->key] = NULL;  
            capacity--;
            tail = tail->pre;  // 空的容器， 不考虑
            delete tail->next;
            tail->next = NULL; 
        }
    }
    
    int get(int key) {
        List* tmp = cap[key];
        //cout << "get key: " << key  << " " << tmp<< endl;
        if(tmp == NULL) return -1;
        mv2head(tmp);
        return tmp->val;
    }
    
    void put(int key, int value) {
        List* tmp = cap[key];
        if(tmp == NULL){  // 不存在键
            List* newL = new List(key,value);
            cap[key] = newL;
            this->capacity++;
            if(head == NULL){
                tail = head = newL;
                newL->pre = newL->next = NULL;
                return;
            }
            head->pre = newL;
            newL->next = head;
            head = newL;
            rm();   
        }else{
            tmp->val = value;
            mv2head(tmp);
        }
    }
};

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache* obj = new LRUCache(capacity);
 * int param_1 = obj->get(key);
 * obj->put(key,value);
 */
```



























