'''
@Author: Firefly
@Date: 2019-09-06 23:15:17
@Descripttion: 
@LastEditTime: 2020-02-20 16:36:29
'''
# -*- encoding: utf-8 -*-
"""
@file: test_data_structure.py
@time: 2019/9/6 23:15
@author: Firefly
@version: 0.1
"""

"""
tuple 是不能更改数据的， 除非重新拼接数据
"""


def test_tuple():
    print('tuple_test----------------')
    tuple1 = (1, 'a', 2)
    print(tuple1)
    tuple2 = tuple1 + (1, "k")
    print(tuple2)


def test_list():
    print("list_test------------------")

    list1 = [1, 2, 3, 4, 5, 6, 7, 8, 9]
    list2 = list1[1:5:2]
    # Python的索引是闭区间，闭区间，闭区间，闭区间
    print(list2)
    list1.append(10)
    list1.remove(1)
    print(list1, end='\n')
    print(list1[2])
    list1.remove(10)
    print(list1)
    del list1[1]  # 这里主意了，删除数组里面的某一个元素是del 是del  是del 是 del
    print(list1)
    # python 是面向对象， 变量名只是一个标签， 所以更改list3 的值是同一个对象
    list3 = list1
    del list3[0]
    print(list1)
    print(list1 == list3)


def test_array():
    print("test_array-----------")
    f = [x for x in range(1, 10)]
    print(f)
    f = [x + y for x in 'ABCDE' for y in '1234567']
    print(f)

# TODO: i have try
def test_set():
    print("test_set--------------------------------------")
    set2 = set(range(1, 10))
    print(set2)
    set2.update([11, 12])   # update 是直接加入新的集合元素
    print(set2)
    set1 = {1, 2, 3, 4, 5, 6, 7, 8, 9}
    set2 = {6, 7, 8, 9, 10, 11, 12, 13}

    print(set1 ^ set2)
    print(set1 | set2)
    print(set1 & set2)
    print(set1 - set2)


def test_dic():
    print("test_dic----------------------------")
    scores = {"firelfy": 1, "xxxx": 2, "xxo": 3}
    for elem in scores:
        print('%s----->\t%d' % (elem, scores[elem]))


def main():
    pass
    # test_list()
    # test_array()
    # test_set()
    
    # test_dic()
    test_tuple()


if __name__ == "__main__":
    main()
