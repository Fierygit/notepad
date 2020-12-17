# -*- encoding: utf-8 -*-
"""
@file: test_socket.py
@time: 2019/9/12 22:31
@author: Firefly
@version: 0.1
"""
import socket


def test_socket():
    client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)  # 声明socket类型，同时生成链接对象
    client.connect(('localhost', 2333))  # 建立一个链接，连接到本地
    while True:
        con, addr = client.accept()
        print('连接地址：', addr)
        msg = '欢迎访问菜鸟教程！'  # strip默认取出字符串的头尾空格
        client.send(msg.encode('utf-8'))  # 发送一条信息 python3 只接收btye流
        data = client.recv(1024)  # 接收一个信息，并指定接收的大小 为1024字节
        print('recv:', data.decode())  # 输出我接收的信息
    client.close()  # 关闭这个链接


def main():
    pass
    test_socket()


if __name__ == "__main__":
    main()
