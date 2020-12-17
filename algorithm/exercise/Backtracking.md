---
title: 回溯
date: 2020-02-28
categories: ["算法"]
tags: ["回溯"]
---


## Backtracking





#### [面试题 08.04. 幂集](https://leetcode-cn.com/problems/power-set-lcci/)

幂集。编写一种方法，返回某集合的所有子集。集合中**不包含重复的元素**。

说明：解集不能包含重复的子集。

**示例:**

```
 输入： nums = [1,2,3]
 输出：
[
  [3],
  [1],
  [2],
  [1,2,3],
  [1,3],
  [2,3],
  [1,2],
  []
]
```
分析： 经典双 DFS

```c++
class Solution {
public:
    using vint = vector<int>;
    using vvint = vector<vint>;
    vvint ans;
    vector<vector<int>> subsets(vector<int>& nums) {
        dfs(vint{},0,nums);
        return ans;
    }
    void dfs(vint cur, int curi, vint& nums){
        if(curi >= nums.size()){
            ans.push_back(cur);
            return;
        }
        dfs(cur,curi + 1,nums);
        cur.push_back(nums[curi]);
        dfs(cur,curi + 1, nums);
    }
};
```



#### [93. 复原IP地址](https://leetcode-cn.com/problems/restore-ip-addresses/)

给定一个只包含数字的字符串，复原它并返回所有可能的 IP 地址格式。

有效的 IP 地址正好由四个整数（每个整数位于 0 到 255 之间组成），整数之间用 `'.' `分隔。

**示例:**

```
输入: "25525511135"
输出: ["255.255.11.135", "255.255.111.35"]
```



```c
class Solution {
public:
    bool isValid(string ip){
        int val = stoi(ip);
        if (val > 255)    return false;
        if (ip.size() >= 2 && ip[0] == '0')    return false;
        return true;
    }
    void dfs(string& s, int pos, vector<string> &path, vector<string>& res){
    //首先判断剩余的位数，是不是还能满足要求，比如25525511135，若2.5.5.25511135显然不满足，这可以预判
    //4组，每组最多3位数字
        int maxLen = (4 - path.size()) * 3;
        if (s.size() - pos > maxLen)    return;
        if (path.size() == 4 && pos == s.size()) {
            string ans = "";
            for (int i = 0; i < 4; ++i) {
                ans += path[i];
                if (i != 3)    ans += ".";
            }
            res.push_back(ans);
            return;
        }
        //回溯算法的典型模式，循环递归
        for (int i = pos; i < s.size() && i <= pos + 2; ++i) {
            string ip = s.substr(pos, i - pos+1);
            if (!isValid(ip))    continue;
            path.push_back(ip);
            dfs(s, i + 1, path, res);
            path.pop_back();
        }
    }
    vector<string> restoreIpAddresses(string s){
        //找3个.的位置,每个.之前组成的的数值必须要小于255，且以0开头的除非数字是0本身，否则也是非法
        vector<string> res;
        if (s.size() == 0 || s.size() < 4)    return res;
        vector<string> path;//存储从根开始的到叶子节点的满足条件的路径,因为最多3位数字一组，所以同一层横向循环时尝试最多3个位的长度
        dfs(s, 0, path, res);
        return res;
    }
};
```





#### [面试题 08.08. 有重复字符串的排列组合](https://leetcode-cn.com/problems/permutation-ii-lcci/)

有重复字符串的排列组合。编写一种方法，计算某字符串的所有排列组合。

**示例1:**

```
 输入：S = "qqe"
 输出：["eqq","qeq","qqe"]
```



```c
class Solution {
public:
using vstr = vector<string>;
    vstr ans;
    string S;
    vector<string> permutation(string S) {

        sort(S.begin(), S.end());
        this->S = S;
        cout << S << endl;
        int size = S.size();
        vector<bool> visited(size,0);
        search(S.size(),  string{}, visited);
        return ans;
    }

    void search(int len, string cstr, vector<bool>& visited){
        cout << cstr.size()  << " " << cstr << endl;
        if(cstr.size() == len){
            ans.push_back(cstr);
            return;
        }

        for(int i = 0; i < len; i++ ){
            if(!visited[i]){
                if((i > 0) && S[i] == S[i - 1] && visited[i-1]) continue;
                visited[i] = 1;
                cstr+=S[i];
                search(len, cstr , visited);
                cstr.pop_back();
                visited[i] = 0;
            }
        }

    }

};
```





#### [40. 组合总和 II](https://leetcode-cn.com/problems/combination-sum-ii/)

给定一个数组 `candidates` 和一个目标数 `target` ，找出 `candidates` 中所有可以使数字和为 `target` 的组合。

`candidates` 中的每个数字在每个组合中只能使用一次。

**说明：**

- 所有数字（包括目标数）都是正整数。
- 解集不能包含重复的组合。 

**示例 1:**

```
输入: candidates = [10,1,2,7,6,1,5], target = 8,
所求解集为:
[
  [1, 7],
  [1, 2, 5],
  [2, 6],
  [1, 1, 6]
]
```



```c
class Solution {
public:
    vector<vector<int>> combinationSum2(vector<int>& candidates, int target) {
        sort(candidates.begin(), candidates.end());
        vector<int> v;
        helper(candidates, target, 0, v);
        return res;
    }
private:
    vector<vector<int> > res;
    void helper(vector<int>& candidates, int target, int start, vector<int>& now) {
        if (target == 0) {
            res.emplace_back(now); 
            return;
        }
        for (int i = start; i < candidates.size(); ++i) {
            if (target - candidates[i] < 0) break; // 无解
            if (i > start && candidates[i] == candidates[i-1]) continue; // 相同层数相同数字只用一次
            now.emplace_back(candidates[i]);
            helper(candidates, target-candidates[i], i+1, now);
            now.pop_back();
        }
        return;
    }
};
```





#### [47. 全排列 II](https://leetcode-cn.com/problems/permutations-ii/)

给定一个可包含重复数字的序列，返回所有不重复的全排列。

**示例:**

```
输入: [1,1,2]
输出:
[
  [1,1,2],
  [1,2,1],
  [2,1,1]
]
```



```c
class Solution {
private:
    vector<vector<int>> res;
    vector<int> sol;
    vector<int> nums;
public:
    vector<vector<int>> permuteUnique(vector<int>& nums) {
        sort(nums.begin(), nums.end());
        this->nums = nums;
        vector<bool> used(nums.size(), false);
        dfs(used);
        return res;
    }
private:
    void dfs(vector<bool> used){
        if(sol.size() == nums.size()){
            res.push_back(sol);
            return;
        }
        for(int i=0; i<nums.size(); i++){
            //当前值用过了 或 
            //当前值等于前一个值： 两种情况：
            //1 nums[i-1] 没用过 说明回溯到了同一层 此时接着用num[i] 则会与 同层用num[i-1] 重复
            //2 nums[i-1] 用过了 说明此时在num[i-1]的下一层 相等不会重复
            if(used[i] || (i>0 && !used[i-1] && nums[i] == nums[i-1])){//用过了
                continue;
            }
            sol.push_back(nums[i]);
            used[i] = true;
            dfs(used);
            sol.pop_back();
            used[i] = false;
        }
    }
};
```



```c
class Solution {
    vector<int> nums;//记录每一次选择后数组状态，包括最终答案
    vector<vector<int>> ans;//选择完每一个数组后并入答案集
    int len;//输入数组元素数量
public:
    vector<vector<int>> permuteUnique(vector<int>& nums) {
        len=nums.size();//更新len
        this->nums=nums;//更新nums
        dfs(0);//开始回溯
        return ans;
    }
    void dfs(int n){
        if(n==len){
            ans.push_back(nums);//已经排列到len位置，即超出数组范围，这意味着已经完成了排列，将此排列并入答案集合
            return;
        }
        vector<int> temp={};//记录该位选择过的元素值，已经选择过的值不再选择
        for(int i=n;i<len;++i){//n为当前正在选择的位，i为准备要作为n位元素目前的位置
            if(find(temp.begin(),temp.end(),nums[i])!=temp.end())continue;//已经选择过的值不再选择
            swap(nums[n],nums[i]);//将第i位数字移动到n位，完成该位选择
            temp.push_back(nums[n]);//记录选择，防止选择相等数字产生多余的解
            dfs(n+1);//选择下一位数字
            swap(nums[n],nums[i]);//变为选择之前的状态，重新选择下一位数字
        }
    }
};
```





#### [剑指 Offer 38. 字符串的排列](https://leetcode-cn.com/problems/zi-fu-chuan-de-pai-lie-lcof/)

输入一个字符串，打印出该字符串中字符的所有排列。

**示例:**

```
输入：s = "abc"
输出：["abc","acb","bac","bca","cab","cba"]
```

```c
class Solution {
public:
    std::vector<std::string> permutation2(std::string s) {
    	// 去重处理
        std::set<std::string> res;
        backtrack2(s, 0, res);
    	return std::vector<std::string>(res.begin(), res.end());
    }

    /*
     * 回溯函数
     * 使用set函数对字符串字符进行去重，
     * 使用swap函数交换两个字符
     * */
    void backtrack2(std::string s, int start, std::set<std::string> &res) {
        // 回溯结束条件
        if(start == s.size()){
            res.insert(s);
            return;
        }

        for(int i = start; i < s.size(); i++){
            // 做选择
            std::swap(s[i], s[start]);
            // 进入下一次决策树
            backtrack2(s, start+1, res);
            // 撤销选择
            std::swap(s[i], s[start]);
        }
    }

};
```



































