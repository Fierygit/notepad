/*
 * @Author: Firefly
 * @Date: 2020-03-05 14:59:09
 * @Descripttion:
 * @LastEditTime: 2020-03-20 22:26:46
 */

#include "quickSort.h"
#include "global.h"
#include "string.h"
#include "time.h"
#include "algorithm"
/**
 * 数组传参的时候， 只能传递指针， 因此获取不了数组的参度！
 * */

void helpSort(int *, int, int);
void helpSort2(int *, int, int);
void helpSort3(int *, int, int);
void helpSort4(int *, int, int);
void helpSort5(int *arr, int left, int right);
int devide1(int *arr, int left, int right);
int devide2(int *arr, int left, int right);
int devide3(int *arr, int left, int right);

void exch(int *arr, int a, int b);
void quickSort(int *arr, int len) {
  srand(int(time(0)));

  int *tmp = new int[len];
  memcpy(tmp, arr, len * sizeof(int));

  int start = clock();
  helpSort(tmp, 0, len);
  cout << "quick sort1(stl like): \n";
  cout << (clock() - start) << endl;
  // for(int i = 0; i < len; i++) cout << tmp[i] << " ";
  // cout << endl;

  memcpy(tmp, arr, len * sizeof(int));
  start = clock();
  helpSort2(tmp, 0, len);
  cout << "quick sort2(devide 1): \n";
  cout << (clock() - start) << endl;

  memcpy(tmp, arr, len * sizeof(int));
  start = clock();
  helpSort3(tmp, 0, len);
  cout << "quick sort3(devide 2): \n";
  cout << (clock() - start) << endl;

  memcpy(tmp, arr, len * sizeof(int));
  start = clock();
  helpSort4(tmp, 0, len - 1);
  cout << "quick sort4(less move and rand): \n";
  cout << (clock() - start) << endl;
  //   for(int i = 0; i < len; i++) cout << tmp[i] << " ";
  // cout << endl;

  memcpy(tmp, arr, len * sizeof(int));
  start = clock();
  sort(tmp, tmp + len);
  cout << "quick sort(stl): \n";
  cout << (clock() - start) << endl;

  // memcpy(tmp, arr, len * sizeof(int));
  // start = clock();
  // helpSort5(tmp,0, len - 1);
  // cout << "quick sort(best): \n";
  // cout << (clock() - start) << endl;

  // for(int i = 0; i < len; i++) cout << tmp[i] << " ";
  // cout << endl;

  // memcpy(tmp, arr, len * sizeof(int));
  // cout << "index: " <<  devide1(tmp,0, len - 1) << endl;
  // for(int i = 0; i < len; i++) cout << tmp[i] << " ";
  // cout << endl;

  // memcpy(tmp, arr, len * sizeof(int));
  // cout << "index: " <<  devide2(tmp,0, len - 1) << endl;
  // for(int i = 0; i < len; i++) cout << tmp[i] << " ";
  // cout << endl;

  delete[] tmp;
}

void helpSort(int *arr, int left, int right) {
  if (left >= right - 1) return;
  // 公平起见， 也加上
  srand(int(time(0)));
  int flag = rand() % (right - left + 1) + left;
  int le = left;
  int ri = right - 1;
  int key = ri;

  while (le < ri) {
    while (arr[le] < arr[key]) le++;
    while (arr[ri] > arr[key]) ri--;
    if (le >= ri) break;
    exch(arr, le, ri);
    le++, ri--;
  }

  helpSort(arr, left, le);
  helpSort(arr, le, right);
}

void exch(int *arr, int a, int b) {
  int tmp = arr[a];
  arr[a] = arr[b];
  arr[b] = tmp;
}

void helpSort2(int *arr, int left, int right) {
  if (left >= right) return;
  int mid = devide3(arr, left, right);
  helpSort2(arr, left, mid - 1);
  helpSort2(arr, mid + 1, right);
}

void helpSort3(int *arr, int left, int right) {
  if (left >= right) return;
  int mid = devide2(arr, left, right);
  helpSort3(arr, left, mid - 1);
  helpSort3(arr, mid + 1, right);
}

// 随机选取一个数字， 并且把大于这个数的移到右边
int devide1(int *arr, int left, int right) {
  // for(int i = 0; i < right; i++) cout << arr[i] << " ";
  // cout << endl;
  if (left >= right) return left;
  srand(int(time(0)));
  int flag = rand() % (right - left + 1) + left;
  exch(arr, right, flag);  // 放到最后面
  flag = left - 1;         // 大于 falg 的 index
  for (int i = left; i < right; i++) {
    if (arr[i] < arr[right]) {
      flag++;
      if (flag != i) exch(arr, i, flag);
    }
  }
  flag++;
  exch(arr, right, flag);
  return flag;
}
int devide2(int *arr, int left, int right) {
  if (left >= right) return left;
  srand(int(time(0)));
  int flag = rand() % (right - left + 1) + left;
  exch(arr, right, flag);
  flag = left;
  bool first = 1;
  for (int i = left; i < right; i++) {
    if (arr[i] <= arr[right]) {  //一定是小于 等于
      if (!first) {
        exch(arr, i, flag);
      }
      flag++;
    } else if (arr[i] > arr[right] && first) {
      first = 0;
    }
  }
  exch(arr, flag, right);
  return flag;
}

int devide3(int *arr, int left, int right) {
  if (left >= right) return left;
  srand(int(time(0)));
  int flag = rand() % (right - left + 1) + left;
  exch(arr, right, flag);
  flag = left;
  for (int i = left; i < right; i++) {
    if (arr[i] <= arr[right]) {
      exch(arr, i, flag++);
    }
  }
  exch(arr, flag, right);
  return flag;
}

void helpSort4(int *arr, int left, int right) {
  if (left >= right) return;
  srand(int(time(0)));
  int flag = rand() % (right - left + 1) + left;

  exch(arr, flag, right);

  int le = left;
  int ri = right;
  int key = arr[right];

  while (le < ri) {
    while (arr[le] <= key && le < ri) le++;
    arr[ri] = arr[le];
    while (arr[ri] >= key && le < ri) ri--;
    arr[le] = arr[ri];
  }

  arr[ri] = key;

  helpSort4(arr, left, ri - 1);
  helpSort4(arr, ri + 1, right);
}

void helpSort5(int *arr, int left, int right) {
  if (left >= right) return;

  srand(int(time(0)));
  int flag = rand() % (right - left + 1) + left;

  exch(arr, flag, right);

  bool first = 1;
  flag = left;
  for (int i = left; i < right; i++) {
    if (arr[i] < arr[right]) {
      if (!first) {
        exch(arr, i, flag);
      }
      flag++;
    } else if (arr[i] >= arr[right] && first) {
      first = 0;
    }
  }
  exch(arr, flag, right);

  int le, ri;
  le = ri = flag;
  while (le >= left && arr[le] == arr[flag]) le--;
  while (ri <= right && arr[ri] == arr[flag]) ri++;
  // cout << "fdasf: " << le << " " << flag << " " << ri <<endl;

  // for(int i = 0; i < right; i++) cout << arr[i] << " ";
  // cout << endl;
}