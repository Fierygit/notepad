/*
 * @Author: Firefly
 * @Date: 2020-03-16 00:15:42
 * @Descripttion: 
 * @LastEditTime: 2020-03-20 22:32:41
 */
#include <iostream>
using namespace std;

// 交换 a， b
void swap(int *arr, int le, int ri){
    int tmp = arr[le];
    arr[le] = arr[ri];
    arr[ri] = tmp;
}

int devide(int* arr, int le, int ri){
    if(le >= ri) return le;
    int key = ri;
    while(le < ri){
        while(arr[le] < arr[key]) le++;
        while(arr[ri] > arr[key]) ri--;
        if(le >= ri) return le;
        swap(arr,le,ri);
        le++,ri--;
    }
    return le;
}


void quickSort(int *arr, int le, int ri){
    if(le >= ri) return;
    int mid = devide(arr,le,ri);
    quickSort(arr,le,mid-1);
    quickSort(arr,mid,ri);
    
}

void test1(){

    int a[10] = {1,4,2,4,2,-4,-8,-98,-8,0};   
    int b[5] = {1,1,1,1,-1};
    int c[2] = {5,4};
    int d[1] = {1};
    
    
    quickSort(a,0,sizeof(a)/sizeof(int) - 1);
    quickSort(b,0,sizeof(b)/sizeof(int) - 1);
    quickSort(c,0,sizeof(c)/sizeof(int) - 1);
    quickSort(d,0,sizeof(d)/sizeof(int) - 1);


    for(int i : a) cout << i << " ";cout << endl;
    for(int i : b) cout << i << " ";cout << endl;
    for(int i : c) cout << i << " ";cout << endl;
    for(int i : d) cout << i << " ";cout << endl;
}

void test0(){
     int a[5] = {1,4,2,4,5};   
     devide(a,0,sizeof(a)/sizeof(int)-1);
     for(int i : a) cout << i << " "; cout << endl;

}

int main(){
    test1();

    return 0;
}