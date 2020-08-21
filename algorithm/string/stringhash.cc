/*
 * @Author: Firefly
 * @Date: 2020-03-15 16:33:12
 * @Descripttion: 
 * @LastEditTime: 2020-03-15 16:39:28
 */

#include <iostream>
using namespace std;

const int P = 10000019;
const int MOD = 1000000007;

int hashStr(string& a){
    long long sum = 1;
    for(int i = 0 ; i < a.length(); i++){
        sum  = (sum * P + a[i] - 'a') % MOD;
    }
    return sum;
}


int main(){
    string a;
    while(cin >> a){
        cout << a << " hash num is : " << hashStr(a) << endl;
    }
}