---
title: limter
date: 2020-08-01

---

### 漏桶算法

![image](./assert/rate-limit1.png)

生动形象，容易理解， 往桶里加水的速度大于漏水的速度就超速了， 桶大小表示最大速度大小

缺点： 只能匀速处理， 不能并发处理， 虽然可以一下往桶里加水， 但速度取决于漏水速度


### 令牌桶

>- 每秒会有 r 个令牌放入桶中，或者说，每过 1/r 秒桶中增加一个令牌
>- 桶中最多存放 b 个令牌，如果桶满了，新放入的令牌会被丢弃
>- 当一个 n 字节的数据包到达时，消耗 n 个令牌，然后发送该数据包
>- 如果桶中可用令牌小于 n，则该数据包将被缓存或丢弃

缺点： 可能出现某一个时间段出现超频！（停止一段时间后突然拿掉桶里的所有token， 后面还会慢慢拿， 因此这里超速了）


### 滑动窗口

在窗口内有固定的请求数， 可以处理并发。

缺点：**速度最大时** 不能处理高并发时的匀速请求， 都是像分批一样分发token的，有点像 tcp 一下分发出去很多包，这样对于不是临界的值不公平， 运行 [code](https://github.com/Fierygit/cxxstudy/blob/master/algorithm/limter/token_limter.cc) 查看

### 结合实现（ 令牌桶 + 滑动窗口 ）

既可以处理并发， 又可以让任意一个时间段不超速！

[code](https://github.com/Fierygit/cxxstudy/blob/master/algorithm/limter/rate_limter.cc)
