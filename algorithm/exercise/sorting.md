---
title: 排序
date: 2019-12-28
categories: 
- 算法
tag: leetcode
---
## 排序算法

[toc]



![](https://raw.githubusercontent.com/Fierygit/picbed/master/20200327222126.png)



### insertSort

将数插到正确的地方

```c
void insertSort(int *num, int le, int ri) {
  for (int i = le; i <= ri; i++) {
    int index = -1;
    for (int j = 0; j < i; j++) {
      if (num[j] > num[i]) {
        index = j;
        break;
      }
    }
    if (index == -1) continue;
    int tmp = num[i];
    for (int j = i - 1; j >= index; j--) num[j + 1] = num[j];
    num[index] = tmp;
  }
}
```



### selectSort

选择一个最小的放到前面

```c
void selectSort(int *num, int le, int ri) {
  for (int i = le; i <= ri; i++) {
    int index = -1;
    for (int j = i; j <= ri; j++) {
      if (index == -1 || num[j] < num[i]) {
        index = j;
      }
    }
    swap(num, i, index);
  }
}
```





### quickSort

选择一个key ， 把大于key的都放到右边，小于key的放到左边， 递归求解

```c
void swap(int *num, int a, int b) {
  int tmp = num[a];
  num[a] = num[b];
  num[b] = tmp;
}
void quickSort1(int *num, int le, int ri) {
  if (le >= ri) return; 
  int start = le;
  for (int i = le; i < ri; i++)
    if (num[i] < num[ri]) swap(num, i, start++);
  swap(num, start, ri);
  quickSort1(num, le, start - 1);
  quickSort1(num, start + 1, ri);
}
```



### mergeSort

一直拆分， 然后有序合并

```
void mergeSort(int *num, int le, int ri) {
  if (le >= ri) return;
  int mid = le + ((ri - le) >> 1);
  mergeSort(num, le, mid);
  mergeSort(num, mid + 1, ri);
  int tmp[ri - le + 1];
  int start = 0, l = le, r = mid + 1;
  while (l <= mid && r <= ri) {
    if (num[l] < num[r]) tmp[start++] = num[l++];
    else tmp[start++] = num[r++];
  }
  while (l <= mid) tmp[start++] = num[l++];
  while (r <= ri) tmp[start++] = num[r++];
  start = 0;
  for (int i = le; i <= ri; i++) num[i] = tmp[start++];  // 拷贝
}
```



