![](https://raw.githubusercontent.com/Fierygit/picbed/master/20210105102953.png)





## 摘要

MapReduce是一个编程模型，也是一个处理和生成超大数据集的算法模型的相关实现。用户首先创建一个 Map 函数处理一个基于 key/value pair 的数据集合，输出中间的基于 key/value pair 的数据集合；然后再创建一个 Reduce 函数用来合并所有的具有相同中间 key 值的中间 value 值。现实世界中有很多满足上述处理模型的例子，本论文将详细描述这个模型。

MapReduce 架构的程序能够在大量的普通配置的计算机上实现并行化处理。这个系统在运行时只关心：如何分割输入数据，在大量计算机组成的集群上的调度，集群中计算机的错误处理，管理集群中计算机之间必要的通信。采用 MapReduce 架构可以使那些没有并行计算和分布式处理系统开发经验的程序员有效利用分布式系统的丰富资源。

我们的 MapReduce 实现 运行在规模可以灵活调整的由普通机器组成的集群上：一个典型的 MapReduce 计算往往由几千台机器组成、处理以 TB 计算的数据。程序员发现这个系统非常好用：已经实现了数以百计的 MapReduce 程序，在 Google 的集群上，每天都有 1000 多个 MapReduce 程序在执行。





## 编程模型

MapReduce编程模型的原理是：利用一个输入 key/value pair 集合来产生一个输出的 key/value pair 集合。
MapReduce 库的用户用两个函数表达这个计算： Map 和 Reduce 。

用户自定义的 Map 函数接受一个输入的 key/value pair 值，然后产生一个中间 key/value pair 值的集合。

MapReduce 库把所有具有相同中间 key 值的中间 value 值集合在一起后传递给 reduce 函数。

用户自定义的 Reduce 函数接受一个中间 key 的值 I 和相关的一个 value 值的集合。 Reduce 函数合并这些value 值，形成一个较小的 value 值的集合。一般的，每次 Reduce 函数调用只产生 0 或 1 个输出 value 值。通常我们通过一个迭代器把中间 value 值提供给 Reduce 函数，这样我们就可以处理无法全部放入内存中的大量的 value 值的集合。

### 一些例子

- 分布式的Grep:  Map 函数输出匹配某个模式的一行， Reduce 函数是一个恒等函数，即把中间数据复制到
输出。
- 计算URL 访问频率： Map 函数处理日志中 web 页面请求的记录，然后输出 (URL, 1）。 Reduce 函数把相同URL的的valuevalue值都累加起来，产生值都累加起来，产生 (URL,记录总数) 结果。
- 倒转网络链接图：Map 函数在源页面（ source ）中搜索所有的链接目标() target ）并输出为          (target, source)。Reduce 函数把给定链接目标（ target ）的链接组合成一个列表，输出		 (target,list( source))。

- 每个主机的检索词向量：检索词向量用一个(词, 频率) 列表来概述出现在文档或文档集中的最重要的一些词。 Map 函数为每一个输入文档输出 (主机名 检索词向量 )，其中主机名来自文档的 URL 。 Reduce 函数接收给定主机的所有文档的检索词向量，并把这些检索词向量加在一起，丢弃掉低频的检索词，输出一个最终的( 主机名, 检索词向量) 。

- 倒排索引：Map 函数分析每个文档输出一个 词 文档号 的列表， Reduce 函数的输入是一个给定词的所有
  （词，文档号），排序所有的文档号，输出（词 ，list （文档号 ））。所有的输出集合形成一个简单的倒排索引，它以一种简单的算法跟踪词在文档中的位置。

- 分布式排序：Map 函数从每个记录提取 key ，输出 (key,  record)。 Reduce 函数不改变任何的值。

## 实现

通过将Map 调用的输入数据自动分割为 M 个数据片段的集合， Map 调用被分布到多台机器上执行。输入的数据片段能够在不同的机器上并行 处理。使用分区函数将 Map 调用产生的中间 key 值分成 R 个不同分区   （例如，
hash(key) mod R） Reduce 调用也被分布到多台机器上执行。分区数量（ R ）和分区函数由用户来指
定。



1. 用户程序首先调用的 MapReduce 库将输入文件分成 M 个数据片度，每个数据片段的大小一般从
16MB 到 64MB( 可以通过可选的参数来控制每个数据片段的大小 。然后用户 程序在机群中创建大量
的程序副本。
2. 这些程序副本中的有一个特殊的程序 master 。副本中其它的程序都是 worker 程序，由 master 分配
任务。有 M 个 Map 任务和 R 个 Reduce 任务将被分配， master 将一个 Map 任务或 Reduce 任务分配
给一个空闲的 worker 。
3. 被分配了 map 任务的 worker 程序读取相关的输入数据片段，从输入的数据片段中解析出 key/value
pair ，然后把 key/value pair 传递给用户自定义的 Map 函数，由 Map 函数生成并输出的中间 key/value
pair ，并缓存在内存中。
4. 缓存中的 key/value pair 通过<font color='red'>分区函数</font>分成 R 个区域，之后周期性的写入到本地磁盘上。缓存的
key/value pair 在本地磁盘上的存储位置将被回传给 master ，由 master 负责把这些存储位置再传送给
Reduce worker

5. 当 Reduce worker 程序接收到 master 程序发来的数据存储位置信息后，使用 RPC 从 Map worker 所在
  主机的磁盘上读取这些缓存数据。当 Reduce worker 读取了所有的中间数据后，通过对 key 进行排序
  后使得具有相同 key 值的数据聚合在一起。 由于许多不同的 key 值会映射到相同的 Reduce 任务上，
  因此必须进行排序。如果中间数据太大无法在内存中完成排序，那么就要在外部进行排序。
6. Reduce worker 程序遍历排序后的中间数据，对于每一个唯一的中间 key 值， Reduce worker 程序将这
  个 key 值和它相关的中间 value 值的集合传递给用户自定义的 Reduce 函数。 Reduce 函数的输出被追
  加到所属分区的输出文件。
7. 当所有的 Map 和 Reduce 任务都完成之后， master 唤醒用户程序。在这个时候，在用户程序里的对
  MapReduce 调用才返回。

在成功完成任务之后， MapReduce 的输出存放在 R 个输出文件中（对应每个 Reduce 任务产生一个输出
文件，文件名由用户指定）。一般情况下，用户不需要将这 R 个输出文件合并成一个文件 他们经常把这些文
件作为另外一个 MapReduce 的输入，或者在另外一个可以处理多个分割文件的分布式应用中使用。











































