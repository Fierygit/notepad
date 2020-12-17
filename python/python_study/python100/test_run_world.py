# -*- encoding: utf-8 -*-
"""
@file: test_run_world.py
@time: 2019/9/6 23:34
@author: Firefly
@version: 0.1
"""
import os
import time


def main():
    pass
    while True:
        # 清理屏幕上的输出
        os.system('cls')
        # os.system('clear')
        print(content)
        # 休眠200毫秒
        time.sleep(0.6)
        content = content[1:] + content[0]


if __name__ == "__main__":
    main()
