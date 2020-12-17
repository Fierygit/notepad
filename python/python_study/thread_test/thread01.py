'''
@Author: Firefly
@Date: 2019-09-06 15:08:03
@Descripttion: 
@LastEditTime : 2019-12-19 16:15:59
'''
# -*- encoding: utf-8 -*-
"""
@file: thread01.py
@time: 2019/9/6 15:08
@author: Firefly
@version: 0.1
"""
import _thread
import time


def print_time(name, delay, lock):
    print(name)
    count = 0
    while count < 5:
        print("name :" + name + "  delay :" + str(delay))
        time.sleep(delay)
        count += 1
    lock.release()

def main():
    pass
    try:
        #! _thread 模块没有进程的控制， 主进程结束， 子进程就结束了， 要等待子线程结束， ，，，，，
        #! 可以使用跟强大的threading 模块


        print("thread1")
        _thread.start_new_thread(print_time, ("first", 2, _thread.allocate_lock()))
        print("thread2")
        _thread.start_new_thread(print_time, ("second", 3, _thread.allocate_lock()))
        
    except Exception as e:
        print(e)
        print("start error")


# print("start ---------------")
# main()
# print("end------------------")

if __name__ == "__main__":
    main()
