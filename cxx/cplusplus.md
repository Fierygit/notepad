---
title: cplusplus
date: 2017-11-21
---
[toc]



## c11的语言特性

.h 头文件可以不加

```c
#include <vector>
#include <vector.h>
```



### function, bind

关于std::function 的用法：
其实就可以理解成函数指针

```c
//保存自由函数
void print(int a){cout<<a<<endl;}

std::function<void(int a)> func;
func = printA;
func(2);

//保存lambda表达式
std::function<void()> func_1 = [](){cout<<"hello world"<<endl;};
func_1();

//保存成员函数
struct Foo {
    Foo(int num) : num_(num) {}
    void print_add(int i) const { cout << num_+i << '\n'; }
    int num_;
};


std::function<void(const Foo&, int)> f_add_display = &Foo::print_add;
Foo foo(2);
f_add_display(foo, 1);

在实际使用中都用 auto 关键字来代替std::function… 这一长串了。
```



```c
#include <iostream>
using namespace std;
class A{
public:
    void fun_3(int k,int m){
        cout<<k<<" "<<m<<endl;
    }
};

void fun(int x,int y,int z){
    cout<<x<<"  "<<y<<"  "<<z<<endl;
}

void fun_2(int &a,int &b){
    a++; b++;
    cout<<a<<"  "<<b<<endl;
}

int main(int argc, const char * argv[]){
    auto f1 = std::bind(fun,1,2,3); //表示绑定函数 fun 的第一，二，三个参数值为： 1 2 3
    f1(); //print:1  2  3

    auto f2 = std::bind(fun, placeholders::_1,placeholders::_2,3);
    //表示绑定函数 fun 的第三个参数为 3，而fun 的第一，二个参数分别有调用 f2 的第一，二个参数指定
    f2(1,2);//print:1  2  3

    auto f3 = std::bind(fun,placeholders::_2,placeholders::_1,3);
    //表示绑定函数 fun 的第三个参数为 3，而fun 的第一，二个参数分别有调用 f3 的第二，一个参数指定
    //注意： f2  和  f3 的区别。
    f3(1,2);//print:2  1  3

    int n = 2;    int m = 3;

    auto f4 = std::bind(fun_2, n,placeholders::_1);
    f4(m); //print:3  4
    
    //print:4  说明：bind对于不事先绑定的参数，通过std::placeholders传递的参数是通过引用传递的
    cout<<m<<endl;
    //print:2  说明：bind对于预先绑定的函数参数是通过值传递的
    cout<<n<<endl;

    A a;
    auto f5 = std::bind(&A::fun_3, a,placeholders::_1,placeholders::_2);
    f5(10,20);//print:10 20

    std::function<void(int,int)> fc = 
        std::bind(&A::fun_3, a,std::placeholders::_1,std::placeholders::_2);
    fc(10,20);//print:10 20
    return 0;
}
```



### 智能指针

auto_ptr、unique_ptr、shared_ptr和weak_ptr 这几个智能指针背后的设计思想: 将基本类型指针封装为类对象指针，并在析构函数里编写delete语句删除指针指向的内存空间。



```c
智能指针析构是会delete 指针， 因此不要这样做
tring vacation("I wandered lonely as a cloud.");
shared_ptr<string> pvac(&vacation);   // No
```



先来看下面的赋值语句:

```c
auto_ptr< string> ps (new string ("I reigned lonely as a cloud.”）;
auto_ptr<string> vocation; 
vocaticn = ps;
```

上述赋值语句将完成什么工作呢？如果ps和vocation是常规指针，则两个指针将指向同一个string对象。这是不能接受的，因为程序将试图删除同一个对象两次——一次是ps过期时，另一次是vocation过期时。要避免这种问题，方法有多种：

- 定义陚值运算符，使之执行深复制。这样两个指针将指向不同的对象，其中的一个对象是另一个对象的副本，缺点是浪费空间，所以智能指针都未采用此方案。
- 建立所有权（ownership）概念。对于特定的对象，只能有一个智能指针可拥有，这样只有拥有对象的智能指针的构造函数会删除该对象。然后让赋值操作转让所有权。这就是用于auto_ptr和uniqiie_ptr 的策略，但unique_ptr的策略更严格。
- 创建智能更高的指针，跟踪引用特定对象的智能指针数。这称为引用计数。例如，赋值时，计数将加1，而指针过期时，计数将减1,。当减为0时才调用delete。这是shared_ptr采用的策略。

当然，同样的策略也适用于复制构造函数，每种方法都有其用途，但为何说要摒弃auto_ptr呢？

```c
#include <iostream>
#include <string>
#include <memory>
using namespace std;

int main() {
  auto_ptr<string> films[5] =
 {
  auto_ptr<string> (new string("Fowl Balls")),
  auto_ptr<string> (new string("Duck Walks")),
  auto_ptr<string> (new string("Chicken Runs")),
  auto_ptr<string> (new string("Turkey Errors")),
  auto_ptr<string> (new string("Goose Eggs"))
 };
 auto_ptr<string> pwin;
 pwin = films[2]; 
 // films[2] loses ownership. 将所有权从films[2]转让给pwin，
 此时films[2]不再引用该字符串从而变成空指针

 cout << "The nominees for best avian baseballl film are\n";
 for(int i = 0; i < 5; ++i)
  cout << *films[i] << endl;
 cout << "The winner is " << *pwin << endl;
 cin.get();

 return 0;
}
```



运行下发现程序崩溃了，原因在上面注释已经说的很清楚，films[2]已经是空指针了，下面输出访问空指针当然会崩溃了。但这里如果把auto_ptr换成shared_ptr或unique_ptr后，程序就不会崩溃，原因如下：

- 使用shared_ptr时运行正常，因为shared_ptr采用引用计数，pwin和films[2]都指向同一块内存，在释放空间时因为事先要判断引用计数值的大小因此不会出现多次删除一个对象的错误。

- 使用unique_ptr时编译出错，与auto_ptr一样，unique_ptr也采用所有权模型，但在使用unique_ptr时，程序不会等到运行阶段崩溃，而在编译器因下述代码行出现错误：

  ```c
  unique_ptr<string> pwin;
  pwin = films[2]; // films[2] loses ownership.
  ```

  指导你发现潜在的内存错误。

这就是为何要摒弃auto_ptr的原因，一句话总结就是：**避免潜在的内存崩溃问题。**

- unique_ptr为何优于auto_ptr？

可能大家认为前面的例子已经说明了unique_ptr为何优于auto_ptr，也就是安全问题，下面再叙述的清晰一点。
请看下面的语句:

```
auto_ptr<string> p1(new string ("auto") ； //#1
auto_ptr<string> p2;                       //#2
p2 = p1;                                   //#3
```

在语句#3中，p2接管string对象的所有权后，p1的所有权将被剥夺。前面说过，这是好事，可防止p1和p2的析构函数试图刪同—个对象；

但如果程序随后试图使用p1，这将是件坏事，因为p1不再指向有效的数据。

下面来看使用unique_ptr的情况：

```
unique_ptr<string> p3 (new string ("auto");   //#4
unique_ptr<string> p4；                       //#5
p4 = p3;                                      //#6
```

编译器认为语句#6非法，避免了p3不再指向有效数据的问题。因此，unique_ptr比auto_ptr更安全。

**但unique_ptr还有更聪明的地方。**
有时候，会将一个智能指针赋给另一个并不会留下危险的悬挂指针。假设有如下函数定义：

```
unique_ptr<string> demo(const char * s)
{
    unique_ptr<string> temp (new string (s))； 
    return temp；
}
```

并假设编写了如下代码：

```
unique_ptr<string> ps;
ps = demo('Uniquely special")；
```

demo()返回一个临时unique_ptr，然后ps接管了原本归返回的unique_ptr所有的对象，而返回时临时的 unique_ptr 被销毁，也就是说没有机会使用 unique_ptr 来访问无效的数据，换句话来说，这种赋值是不会出现任何问题的，即没有理由禁止这种赋值。实际上，编译器确实允许这种赋值，这正是unique_ptr更聪明的地方。

**总之，党程序试图将一个 unique_ptr 赋值给另一个时，如果源 unique_ptr 是个临时右值，编译器允许这么做；如果源 unique_ptr 将存在一段时间，编译器将禁止这么做**，比如：

```c
unique_ptr<string> pu1(new string ("hello world"));
unique_ptr<string> pu2;
pu2 = pu1;                                      // #1 not allowed
unique_ptr<string> pu3;
pu3 = unique_ptr<string>(new string ("You"));   // #2 allowed
```

其中#1留下悬挂的unique_ptr(pu1)，这可能导致危害。而#2不会留下悬挂的unique_ptr，因为它调用 unique_ptr 的构造函数，该构造函数创建的临时对象在其所有权让给 pu3 后就会被销毁。**这种随情况而已的行为表明，unique_ptr 优于允许两种赋值的auto_ptr 。**

当然，您可能确实想执行类似于#1的操作，仅当以非智能的方式使用摒弃的智能指针时（如解除引用时），这种赋值才不安全。要安全的重用这种指针，可给它赋新值。C++有一个标准库函数std::move()，让你能够将一个unique_ptr赋给另一个。下面是一个使用前述demo()函数的例子，该函数返回一个unique_ptr<string>对象：
使用move后，原来的指针仍转让所有权变成空指针，可以对其重新赋值。

```c
unique_ptr<string> ps1, ps2;
ps1 = demo("hello");
ps2 = move(ps1);
ps1 = demo("alexia");
cout << *ps2 << *ps1 << endl;
```

- 如何选择智能指针？

在掌握了这几种智能指针后，大家可能会想另一个问题：在实际应用中，应使用哪种智能指针呢？
下面给出几个使用指南。

（1）如果程序要使用多个指向同一个对象的指针，应选择shared_ptr。这样的情况包括：

- 有一个指针数组，并使用一些辅助指针来标示特定的元素，如最大的元素和最小的元素；
- 两个对象包含都指向第三个对象的指针；
- STL容器包含指针。很多STL算法都支持复制和赋值操作，这些操作可用于shared_ptr，但不能用于unique_ptr（编译器发出warning）和auto_ptr（行为不确定）。如果你的编译器没有提供shared_ptr，可使用Boost库提供的shared_ptr。

（2）如果程序不需要多个指向同一个对象的指针，则可使用unique_ptr。如果函数使用new分配内存，并返还指向该内存的指针，将其返回类型声明为unique_ptr是不错的选择。这样，所有权转让给接受返回值的unique_ptr，而该智能指针将负责调用delete。可将unique_ptr存储到STL容器在那个，只要不调用将一个unique_ptr复制或赋给另一个算法（如sort()）。例如，可在程序中使用类似于下面的代码段。

```c
unique_ptr<int> make_int(int n){
    return unique_ptr<int>(new int(n));
}
void show(unique_ptr<int> &p1){
    cout << *a << ' ';
}
int main(){
    ...
    vector<unique_ptr<int> > vp(size);
    for(int i = 0; i < vp.size(); i++)
        vp[i] = make_int(rand() % 1000);              // copy temporary unique_ptr
    vp.push_back(make_int(rand() % 1000));     // ok because arg is temporary
    for_each(vp.begin(), vp.end(), show);           // use for_each()
    ...
}
```

其中push_back调用没有问题，因为它返回一个临时unique_ptr，该unique_ptr被赋给vp中的一个unique_ptr。另外，如果按值而不是按引用给show()传递对象，for_each()将非法，因为这将导致使用一个来自vp的非临时unique_ptr初始化pi，而这是不允许的。前面说过，编译器将发现错误使用unique_ptr的企图。

在unique_ptr为右值时，可将其赋给shared_ptr，这与将一个unique_ptr赋给一个需要满足的条件相同。与前面一样，在下面的代码中，make_int()的返回类型为unique_ptr<int>：

```c
unique_ptr<int> pup(make_int(rand() % 1000));   // ok
shared_ptr<int> spp(pup);                       // not allowed, pup as lvalue
shared_ptr<int> spr(make_int(rand() % 1000));   // ok
```

模板shared_ptr包含一个显式构造函数，可用于将右值unique_ptr转换为shared_ptr。shared_ptr将接管原来归unique_ptr所有的对象。

在满足unique_ptr要求的条件时，也可使用auto_ptr，但unique_ptr是更好的选择。如果你的编译器没有unique_ptr，可考虑使用Boost库提供的scoped_ptr，它与unique_ptr类似。



### varidic template

模版参数可变

```c++
void print(){}

template <typename T, typename... Types>
void print(const T& firstArg, const Types&... args) {
  cout << firstArg << endl;
  print(args...);// 当参数为0 的时候， 调用上面的空函数
}

print(1,"a");
```



### space in Template Expression, nullptr，auto

```c
vector<vector<int > >  // 旧版
vector<vector<int >>	// c11新版
  
void foo(int);
void foo(int*); 
foo(NULL) // foo1? foo2 ? 旧版有冲突
(NULL == 0) -> true
nullptr(void *) //c11 解决方案
        
auto i = NULL;
```



### Uniform Initiazation

初始化统一可以用大括号，在变量的后面直接用!!!  (强)

```c
int values[] {1,2,3};
vector<int> v {1,2,3};
vector<string> str{"df","df"};

```



### initializer_list

```c
int i;
int p{};  //0
int *p;   //no
int *q{};	  //nullptr
    
void print(int i){cout << i << " ";}
void print(std::initializer_list<int> vals){
    for_each(vals.begin(), vals.end(), print);
}
print({1,2,3,45});
    
class P{
    P(int a){}
    P(initializer_list<int> intlist){}
    void operator=(initializer_list<int> il){}
};

P p(3); 		// 第一个构造函数
P p{3};			// 第二个
P P{3,4};
P P={3,4};		// 第三个

min({1,2,34,4})
max({string("dsf"),string("fsda")}); // 强
```



### explict for actors taking more than one argument

 ```c
class C{
	explict C(int a){}
};
C c = 1; //error  1 -> C not explict
 ```



### for

```c
int a[10] = {      1,      2,      3  };
  //* -----------------------------------
  printf("auto && foreach\n");
  for (auto& i : a) i = 2;  //取地址
  for (auto i : a) {        //取值
    cout << i << " ";
    if (i == 3) break;
  }
//! 尽量用 取值， 不然有拷贝会慢一点

  /*
  template<class InputIterator, class Function>
  Function for_each(InputIterator first, InputIterator last, Function fn){
      while (first!=last) {
        fn (*first);
        ++first;
      }
      return fn;      // or, since C++11: return move(fn);
  }
  */
  cout << endl;
  vector<int> foreach (a, a + 5);
  for_each(foreach.begin(), foreach.end(), [](int a) { cout << a << " "; });
  struct Sum {  //仿函数
    int sum;
    void operator()(int val) { sum += val; }
  };
```



### =defalut, =delete

```c
class B{
    B(int a) : a(a){} // 写了后没有构造函数
    B(const B&) = delete; //不给拷贝构造
    B& operator=(const B&) = delete; //不给拷贝构造
    private: 
    	int a;
}
// 也可以 放到private ，去friend中调用
```



### alias template

```c
template<typename T>
using vec = std::vector<T>;
typedef vector<int> vint // 无法带参数！！！！！
    
vec<int> arr;
vint arr;
    
    
```



### decltype

```c
auto t = [](){}

vector<decltype(t)> v(t);
```



```c
[]{ cou <<"heloo";} 	//函数
[]{cout << "world";}();	//
    
int x;
int y;
auto q = [x, &y]{};

```





## 左值和右值

### 简单的定义

*左值 (lvalue, locator value)* 表示了一个占据内存中某个可识别的位置（也就是一个地址）的对象。

*右值 (rvalue)* 则使用排除法来定义。一个表达式不是 *左值* 就是 *右值* 。 那么，右值是一个 *不* 表示内存中某个可识别位置的对象的表达式。

```c
int globalvar = 20;

int& foo(){
    return globalvar;
}

int main(){
    foo() = 10;
    return 0;
}
```

### 右值引用





## 标准库

### array

将数组封装成模版类， 没有构造函数和析构函数， 模拟出数组！

### tuple

使用模版继承实现

```c
auto tu = make_tuple(22,44,"dsjfk");
tuple_size<>::value
```

