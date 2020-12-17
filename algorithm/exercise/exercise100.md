---
title: Test
date: 2019-12-28
categories: ["算法"]
tags: ["算法"]
---

```c
static int hashFunction( char *key)
{
    int temp = 0;
    int i = 0;

    while (key[i] != '\0')
    {
	temp = ((temp << SHIFT) + key[i]) % MAXTABLESIZE;
	++i;
    }

    return temp;
}
```











####  101.删除排序数组中的重复项

> 给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。

不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。

```
给定数组 nums = [1,1,2], 

函数应该返回新的长度 2, 并且原数组 nums 的前两个元素被修改为 1, 2。 

你不需要考虑数组中超出新长度后面的元素。
```

思路： 使用双指针法， 前面的指针判断是否相等！

```c
class Solution {
public:
    int removeDuplicates(vector<int>& nums) {
        int guard, ans;
        ans = guard  = 0;
        if(nums.size() == 0) return 0;
        while(true){
            while(guard + 1 < nums.size() && nums[guard] == nums[guard+1])guard++;
            nums[ans++] = nums[guard++];
            if(guard == nums.size()) break;        
        }
        return ans;
    }
};
```

