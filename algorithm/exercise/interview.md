---
title: 面试算法题
date: 2019-12-28
categories: 
- 算法
tag: 算法
---
## 常见面试题



#### [面试题 04.10. 检查子树](https://leetcode-cn.com/problems/check-subtree-lcci/)

检查子树。你有两棵非常大的二叉树：T1，有几万个节点；T2，有几万个节点。设计一个算法，判断 T2 是否为 T1 的子树。

如果 T1 有这么一个节点 n，其子树与 T2 一模一样，则 T2 为 T1 的子树，也就是说，从节点 n 处把树砍断，得到的树与 T2 完全相同。

**示例1:**

```
 输入：t1 = [1, 2, 3], t2 = [2]
 输出：true
```

经典的双递归。

```c
class Solution {
public:
    bool checkSubTree(TreeNode* t1, TreeNode* t2)     {
        if(t2==nullptr)  return true;
        if(t1==nullptr)  return false;
        if(t1->val != t2->val) 
            return (checkSubTree(t1->left,t2) || checkSubTree(t1->right,t2));
        return (checkSubTree(t1->left,t2->left) && checkSubTree(t1->right,t2->right));

    }
};
```



#### [面试题 05.02. 二进制数转字符串](https://leetcode-cn.com/problems/bianry-number-to-string-lcci/)

二进制数转字符串。给定一个介于0和1之间的实数（如0.72），类型为double，打印它的二进制表达式。如果该数字不在0和1之间，**或者**无法精确地用32位以内的二进制表示，则打印“ERROR”。

**示例1:**

```
 输入：0.625
 输出："0.101"
```

计组知识！

```c
class Solution {
public:
    string printBin(double num) {
        string res = "0.";
        int i = 30;  // 若 "0." 计为两位，i 初始化为 30，若计为 1 位，初始化为 31
        while (num > 0 && i--) {
            num *= 2;
            if (num >= 1) {
                res.push_back('1');
                --num;
            }
            else res.push_back('0');
        }
        return num != 0 ? "ERROR" : res;
    }
};
```



#### [面试题 02.08. 环路检测](https://leetcode-cn.com/problems/linked-list-cycle-lcci/)

给定一个有环链表，实现一个算法返回环路的开头节点。
有环链表的定义：在链表中某个节点的next元素指向在它前面出现过的节点，则表明该链表存在环路。



```c
class Solution {
public:
    ListNode *detectCycle(ListNode *head) {
        if(!head || !head->next) return nullptr;
        ListNode *fir = new ListNode(0), *sec = new ListNode(0);
        delete fir, sec;
        fir = sec = head;
        while(sec != nullptr && sec->next != nullptr){
            fir = fir->next;
            sec = sec->next->next;
            if(fir == sec)  break;  
        }
        if(sec != fir) return nullptr;
        fir = head;
        while(fir != sec){
            fir = fir->next;
            sec = sec->next;
        }
        return fir;
    }
};
```



#### [面试题 08.09. 括号](https://leetcode-cn.com/problems/bracket-lcci/)

设计一种算法，打印n对括号的所有合法的（例如，开闭一一对应）组合。

说明：解集不能包含重复的子集。

例如，给出 n = 3，生成结果为：

```
[
  "((()))",
  "(()())",
  "(())()",
  "()(())",
  "()()()"
]
```

经典递归题目。

```c
class Solution {
    using vstr = vector<string>;
public:
    int n;
    vstr ans;
    vector<string> generateParenthesis(int n) {
        this->n = n;
        search(0,0,string{});
        return ans;
    }

    void search(int left, int right, string cur){
        if(left < right || left > n || right > n) return;
        if(left == right && left == n){
            ans.push_back(cur);
            return;
        }
        search(left + 1, right, cur + "(");
        search(left, right + 1, cur + ")");
    }
};
```



#### [面试题 08.05. 递归乘法](https://leetcode-cn.com/problems/recursive-mulitply-lcci/)

递归乘法。 写一个递归函数，不使用 * 运算符， 实现两个正整数的相乘。可以使用加号、减号、位移，但要吝啬一些。

**示例1:**

```
 输入：A = 1, B = 10
 输出：10
```

大的记得放后面， 小的放前面， 不然会 StackOverflow

```c
class Solution {
public:
    int multiply(int A, int B) {
        if (A > B) return multiply(B, A);
        if (A == 0) return 0;
        return B + multiply(A - 1, B);
    }
};
```





#### [面试题 02.05. 链表求和](https://leetcode-cn.com/problems/sum-lists-lcci/)

给定两个用链表表示的整数，每个节点包含一个数位。 

强行模拟！！！

```c
class Solution {
public:
    ListNode* addTwoNumbers(ListNode* l1, ListNode* l2) {

        int carray = 0;
        ListNode* head = new ListNode(0);
        ListNode* index = head;
        while(l1 != nullptr && l2 != nullptr){
            int tmp = l1->val + l2->val + carray;
            index->next = new ListNode(tmp % 10);
            index = index->next;
            l1 = l1->next, l2 = l2->next;
            carray = tmp / 10;
        }
        while(l1 != nullptr){
            int tmp = carray + l1->val;
            index->next = new ListNode(tmp % 10);
            index = index->next;
            carray = tmp / 10;
            l1 = l1->next;
        }
        while(l2 != nullptr){
            int tmp = carray + l2->val;
            index->next = new ListNode(tmp % 10);
            index = index->next;
            carray = tmp / 10;
            l2 = l2->next;
        }
        if(carray){
            index->next = new ListNode(carray);
        }
        return head->next;
    }
};
```



#### [面试题 01.07. 旋转矩阵](https://leetcode-cn.com/problems/rotate-matrix-lcci/)

给你一幅由 `N × N` 矩阵表示的图像，其中每个像素的大小为 4 字节。请你设计一种算法，将图像旋转 90 度。

上下翻转， 对角线翻转

```c
class Solution {
public:
    // 左右交换， 上下交换
    void rotate(vector<vector<int>>& matrix) {
        int x = matrix.size();
        if(x == 0) return;
        int y = matrix[0].size();
        for(int i = 0; i < (y >> 1); i++){
            for(int j = 0; j < x; j++){
                swap(matrix[i][j],matrix[y - i - 1][ j ]);
            }
        }
        for(int i = 0; i < y; i++){
            for(int j = 0; j < i; j++){
                swap(matrix[i][j],matrix[j][i]);
            }
        }
    }
    void swap(int& a, int & b){
        int tmp = a;
        a = b;
        b = tmp;
    }
};
```





#### [面试题 04.05. 合法二叉搜索树](https://leetcode-cn.com/problems/legal-binary-search-tree-lcci/)

实现一个函数，检查一棵二叉树是否为二叉搜索树。



```c
class Solution {
public:
    bool isValidBST(TreeNode* root) {
      return  helper(((long int)1<<63),~((long int)1<<63),root);
    }
    bool helper(long mini, long maxi, TreeNode* cur){
        if(cur != nullptr)
        if(cur == nullptr) return true;
        if(cur->val >= maxi || cur->val <= mini) return false;
        return helper(mini,cur->val,cur->left) && helper(cur->val,maxi, cur->right);  
    }

};
```







