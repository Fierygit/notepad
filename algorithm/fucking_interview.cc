/*
 * @Author: Firefly
 * @Date: 2020-03-28 15:06:26
 * @Descripttion:
 * @LastEditTime: 2020-06-27 11:29:12
 */
#include <time.h>

#include <algorithm>
#include <iostream>

using namespace std;

/**练手题
 * 1、 快排
 * 2、 二分
 * 3、 翻转链表， 递归、非递归
 * 4、 kmp
 * 5、 非递归 遍历二叉树
 * 6、  大数 加法
 *
 * 通用文件系统，实现LRU，手写web socket，手写线程池。
 * strcmp ， strcat ， strcpy
 *
 * 1 socket函数：socket等函数是最基础的了，还有其它相关的一些函数
2 线程（进程）模型：单线程（进程），多线程（进程），线程（进程）池
3 事件监听：select ， epoll ， poll 等知识
4 事件处理模式：reactor ，  proactor
5 I/O类型：同步I/O，异步I/O，多路复用，阻塞式，非阻塞式，信号驱动I/O
6 并发模式：半同步/半异步模式，主-从模式，领导者/追随者模式
7 通信方式：消息队列，共享内存，管道，信号，信号量
8 linux API： fork ， vfork ， exit 等
9 网络框架：libevent ， memcache ， ACE ， muduo等
10
其它：定时器（时间轮/最小堆），信号，进程池，线程池，状态机，心跳检测，内存池等，linux相关的知识，分布式系统的知识
* 分布式一致性协议
 *
 *
 //* Webbench       //一个简单的压测工具，c语言写的，很容易看懂
 Tinyhttpd         //一个超轻量型Http Server，只有500多行代码
 libevent
//一个开源的事件驱动库，将IO事件，定时器，和信号统一放在事件处理这一套框架下处理。基于Reactor模式。
 SGI STL      //封装了一些数据结构，一些算法，迭代器，配合《stl源码剖析》食用
 Memcached    //可用作缓存，重点可以看看线程模型，事件驱动，slab内存管理
 Redis
//kv数据库，可用作缓存，支持的操作和数据类型比Memcached要多，配合《Redis设计与实现》食用
 nginx        //一个高性能的HTTP和反向代理服务器，对高并发的支持和高效的负载均衡
 Boost asio   //一个跨平台的、主要用于网络和其他一些底层输入/输出编程的C++库
 Linux内核    //推荐看早期的源码，现在的源码过于庞大
 libco      //微信开源的协程库
 leveldb
//谷歌开源项目，一个持久化存储的KV系统，与redis不同，将大部分数据存储到磁盘上。
 muduo      //基于 Reactor 模式的现代 C++ 网络库，它采用非阻塞 IO
模型，基于事件驱动和回调，原生支持多核多线程 grpc       //rpc框架 ACE
//网络库，重点关注一下线程池，共享内存，定时器，Reactor和Proactor的应用，事件分派处理，线程调度，架构风格

 *
 * */

const double eps = 1e-5;

void swap(int* a, int le, int ri) {
  int tmp = a[le];
  a[le] = a[ri];
  a[ri] = tmp;
}




// [left, right]
void quick_sort(int* a, int left, int right) {
  if (left >= right) return;
  int key = left + rand() % (right - left);
  swap(a, key, right);
  key = a[right];
  int index = left;
  for (int i = left; i < right; i++)
    if (a[i] <= key) swap(a, i, index++);
  swap(a, right, index);
  quick_sort(a, left, index - 1);
  quick_sort(a, index + 1, right);
}

int binery_search(int* a, int target, int len) {
  int left = 0, right = len - 1, mid;

  while (left < right) {
    mid = left + ((right - left) >> 1);
    if (a[mid] >= target) {
      right = mid;
    } else {
      left = mid + 1;
    }
    // cout << left << " " << right << " " << mid << endl;
  }
  if (a[left] < target) return -1;
  return a[left];
}

struct list {
  list* next;
  int val;
  list(int val) : val(val) {}
};

// 值引用
list* reverse_list1(list* head) {
  if (head == nullptr || head->next == nullptr) return head;
  list* tmp = head->next;
  head->next = nullptr;
  list* ret = reverse_list1(tmp);
  tmp->next = head;
  return ret;
}

list* reverse_list2(list* head) {
  if (head == nullptr || head->next == nullptr) return head;
  list *tmp, *index = head->next;
  head->next = nullptr;
  while (index) {
    list* tmp = index->next;
    index->next = head;
    head = index;
    index = tmp;
  }
  return head;
}

void output_list(list* head) {
  list* l = head;
  while (l) {
    cout << l->val << " ";
    l = l->next;
  }
  cout << endl;
}

int kmp_next[100];
void get_next(std::string pattern) {
  int j = -1;
  kmp_next[0] = -1;
  for (int i = 1; i < pattern.length(); i++) {
    while (j != -1 && pattern[j + 1] != pattern[i]) {
      j = kmp_next[j];
      // cout << j + 1 << i << endl;
    }

    if (pattern[j + 1] == pattern[i]) {
      j++;
    }
    kmp_next[i] = j;
  }
  for (int i = 0; i < pattern.length(); i++) {
    cout << kmp_next[i] << " ";
  }
}

void quickSort1(int* num, int le, int ri) {
  if (le >= ri) return;
  int start = le;
  for (int i = le; i < ri; i++)
    if (num[i] < num[ri]) swap(num, i, start++);
  swap(num, start, ri);
  quickSort1(num, le, start - 1);
  quickSort1(num, start + 1, ri);
}

void shellSort(int num[], int len) {
  for (int gap = len / 2; gap > 0; gap /= 2) {  //间隔的大小
    for (int i = 0; i < gap; i++) {             //对于每一个数列的开始
    
      for (int j = i + gap; j < len; j += gap) {
        int tmp = num[j], k = j - gap;  // 记住最后一个数字
        for (; k >= i && num[k] > tmp; k -= gap) num[k + gap] = num[k];
        num[k + gap] = tmp;
      }
    }
  }
}

void mergeSort(int* num, int le, int ri) {
  if (le >= ri) return;
  int mid = le + ((ri - le) >> 1);
  mergeSort(num, le, mid);
  mergeSort(num, mid + 1, ri);

  int tmp[ri - le + 1];
  int start = 0, l = le, r = mid + 1;

  while (l <= mid && r <= ri) {
    if (num[l] < num[r])
      tmp[start++] = num[l++];
    else
      tmp[start++] = num[r++];
  }
  while (l <= mid) tmp[start++] = num[l++];
  while (r <= ri) tmp[start++] = num[r++];
  start = 0;
  for (int i = le; i <= ri; i++) num[i] = tmp[start++];  // 拷贝
}

/**
 * 冒泡排序
 * */
void cmpSort(int* num, int le, int ri) {
  for (int i = le; i <= ri; i++) {
    for (int j = le; j <= ri; j++) {
      if (num[i] > num[j]) swap(num, i, j);
    }
  }
}

/**
 * 选择最小的数字放到前面*/
void selectSort(int* num, int le, int ri) {
  for (int i = le; i <= ri; i++) {
    int min = i;  // 每次最小的下标
    for (int j = i; j <= ri; j++) {
      if (num[j] < num[min]) min = j;
    }
    swap(num, i, min);
  }
}

/** 跟打牌一样，将第i个数插入到合适的地方
 * */
void insertSort(int* num, int le, int ri) {
  for (int i = le; i <= ri; i++) {
    int j = i - 1, tmp = num[i];  // 记住第 i 个数， 等下移动会覆盖
    for (; j >= le && num[j] > tmp; j--) num[j + 1] = num[j];
    num[j + 1] = tmp;
  }
}

void downShit(int* num, int len, int cur) {
  while (true) {
    int ri = (cur + 1) * 2;
    int le = ri - 1;
    int maxi = -1;
    if (le <= len && num[le] > num[cur]) maxi = le;
    if (ri <= len && num[ri] > num[cur]) {
      if (maxi == -1 || num[le] < num[ri]) maxi = ri;  // 选择大的
    }
    if (maxi == -1) return;  // 没有， 结束s
    swap(num, cur, maxi);
    cur = maxi;
  }
}

void buildHeap(int* num, int len) {
  int start = (len + 1) / 2 - 1;  // 计算非叶子节点
  for (int i = len; i >= 0; i--) downShit(num, len, i);  // 从下往上构建
}

void heapSort(int* num, int len) { buildHeap(num, len); }

string big_interger_add(string& a, string& b) {
  int n1 = a.size() - 1;
  int n2 = b.size() - 1;
  int carry = 0;
  string ans;
  while (n1 >= 0 || n2 >= 0 || carry > 0 ) {
    int t1 = n1 >= 0 ? a[n1--] - '0' : 0;
    int t2 = n2 >= 0 ? b[n2--] - '0' : 0;
    ans += (t1 + t2 + carry) % 10 + '0';
    carry = (t1 + t2 + carry) >= 10 ? 1 : 0;
  }
  reverse(ans.begin(), ans.end());
  return ans;
}

int main() {
  int a[6]{1, 4, 6, 7, 8, 9};
  cout << binery_search(a, 11, sizeof(a) / sizeof(int));
  cout << endl;

  int b[7]{4, 5, 3, 6, -2, 4, 6};
  quick_sort(b, 0, sizeof(b) / sizeof(int) - 1);
  for (int& i : b) cout << i << " ";
  cout << endl;

  list *l, *head = new list(rand() % 10);
  l = head;
  for (int i = 4; i < 9; i++) {
    l->next = new list(rand() % 10);
    l = l->next;
  }
  l->next = nullptr;

  output_list(head);
  head = reverse_list1(head);
  output_list(head);
  head = reverse_list2(head);
  output_list(head);

  int c[6]{3, 5, 1, 13, 5, -1};
  selectSort(c, 0, 5);
  for (int& i : c) cout << i << " ";
  cout << endl;

  string sum1{"999"}, sum2{"9999"};
  cout << big_interger_add(sum1, sum2) << endl;

  get_next("aabbaa");
  printf("\n");

  int d[6]{1, 23, 4, 5, 6, 2};
  shellSort(d, 6);
  for (auto& i : d) cout << i << " ";
  cout << endl;

  return 0;
}