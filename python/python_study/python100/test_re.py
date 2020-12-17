'''
@Author: Firefly
@Date: 2019-12-20 19:07:57
@Descripttion: 
@LastEditTime : 2019-12-20 19:30:08
'''
import os
import re


def test():
    with open('test.txt') as f:
        info = ""
        for line in f:
            info += line.strip() + "\n"
        print(info)
        print("匹配三个英文接上三个数字： " , end="")
        ans = re.findall(r'\w{3}\d{3}', info)
        for a in ans:
            print(a, end=" || ")
        print("")
        
        print("匹配空行： ")
        print(re.findall(r'^\s*\n', info))

def main():
    test()


if __name__ == "__main__":
    main()
