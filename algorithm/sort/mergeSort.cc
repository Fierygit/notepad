/*
 * @Author: Firefly
 * @Date: 2020-03-07 18:27:25
 * @Descripttion:
 * @LastEditTime: 2020-03-27 21:31:02
 */

#include <string.h>
#include <iostream>
using namespace std;

bool primeSortGreate(int* arr, int len){
  for(int i = 0 ; i < len; i++){
    if(i + 1 < len && arr[i] > arr[i+1]) return false;
  }
  return true;
}

void helpMergeSort(int* arr, int left, int right);
void mergeSort(int* arr, int len) {
  int* tmp = new int[len];
  memcpy(tmp, arr, len * sizeof(int));

  helpMergeSort(tmp, 0, len - 1);
  cout << "is valid: " << primeSortGreate(tmp,len) << endl;

}

// 左闭右闭 区间
void helpMergeSort(int* arr, int left, int right) {
  if (right <= left) return;

  int mid = (left + right) / 2;

  helpMergeSort(arr, left, mid);
  helpMergeSort(arr, mid + 1, right);

  int* tmp = new int[right - left + 1];
  int index = 0;

  int le = left;
  int ri = mid + 1;
  while (le <= mid || ri <= right) {
    if (ri > right || (arr[le] <= arr[ri] && le <= mid))
      tmp[index++] = arr[le++];
    else if (ri <= right)
      tmp[index++] = arr[ri++];
  }
  index = 0;
  for (int i = left; i <= right; i++) {
    arr[i] = tmp[index++];
  }
  delete tmp;
}