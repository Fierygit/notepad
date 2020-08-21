/*
 * @Author: Firefly
 * @Date: 2020-03-05 15:01:23
 * @Descripttion:
 * @LastEditTime: 2020-03-20 22:25:13
 */

#include "global.h"
#include "mergeSort.h"
#include "quickSort.h"
#include "heapSort.h"
#include "time.h"

int *arr;
int len;

int main() {
  len = 1000000;
  arr = new int[len];

  for (int i = 0; i < len; i++) {
    arr[i] = rand() % (len);
    //cout << arr[i] << " ";
  }
  cout << endl;

  cout << "quick sort: \n";
  quickSort(arr, len);
  
  cout << "merge sort: \n";
  int s = clock();
  mergeSort(arr, len);
  cout << (clock() - s) << endl;

  cout << "heap sort: \n";
  heapSort(arr,len);

  
  return 0;
}
