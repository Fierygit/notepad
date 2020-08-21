/*
 * @Author: Firefly
 * @Date: 2020-03-12 13:58:19
 * @Descripttion:
 * @LastEditTime: 2020-03-12 15:10:47
 */

#include <iostream>
#include "string.h"
#include "time.h"

void swap(int *arr, int a, int b) {
  int tmp = arr[a];
  arr[a] = arr[b];
  arr[b] = tmp;
}

void helpBuild1(int *arr, int cur, int len) {
  if (cur >= len) return;
  int left = cur * 2 + 1;
  int right = cur * 2 + 2;

  if (left > len && right > len) return;  // 两个都超了边界

  if (left < len) {
    if (arr[left] < arr[cur]) swap(arr, left, cur);
    helpBuild1(arr, left, len);
    if (arr[left] < arr[cur]) swap(arr, left, cur);
  }
  if (right < len) {
    if (arr[left] < arr[cur]) swap(arr, left, cur);
    helpBuild1(arr, right, len);
    if (arr[right] < arr[cur]) swap(arr, right, cur);
  }
}

// 已经建立好最小值堆， 把当前元素下滑到正确的位置
void heapify(int *arr, int cur, int len) {
  if (cur >= len) return;
  int left = cur * 2 + 1;
  int right = cur * 2 + 2;
  int tmp = -1;
  if (left < len) tmp = left;
  if (right < len && arr[right] < arr[left]) tmp = right;
  if (tmp != -1 && arr[cur] > arr[tmp]) {
    swap(arr, cur, tmp);
    heapify(arr, tmp, len);
  }
}

void helpBuild2(int *arr, int cur, int len) {
  if (cur >= len) return;
  int start = len / 2 - 1;
  // 从第一个非叶子节点， 建立最小值树
  for (int j = start; j >= 0; j--) {
    heapify(arr, j, len);
  }
}

void buildHeap(int *arr, int len) {
  // helpBuild1(arr,0,len);
  helpBuild2(arr, 0, len);
}

void printArr(int *arr, int len) {
  for (int i = 0; i < len; i++) std::cout << arr[i] << " ";
  std::cout << std::endl;
}

bool primeSortLess(int *arr, int len) {
  for (int i = 0; i < len; i++) {
    if (i + 1 < len && arr[i] < arr[i + 1]) return false;
  }
  return true;
}

void heapSort1(int *arr, int len) {
  // printArr(tmp, len);
  buildHeap(arr, len);
  //// printArr(tmp, len);

  for (int i = len - 1; i >= 0; i--) {
    swap(arr, 0, i);
    heapify(arr, 0, i);
  }
  //std::cout << "is valid:" << primeSortLess(arr, len) << std::endl;

  // printArr(tmp, len);
}

void heapSort(int *arr, int len) {
  int *tmp = new int[len];
  for (int i = 0; i < len; i++) tmp[i] = arr[i];

  int start = clock();
  heapSort1(tmp, len);
  std::cout << "heapSort1(better cmp): \n";
  std::cout << (clock() - start) << std::endl;


  
}
