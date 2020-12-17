'''
@Author: Firefly
@Date: 2020-02-02 12:36:09
@Descripttion: 
@LastEditTime : 2020-02-10 19:54:58
'''

#coding=utf-8

from socket import *

# 1. 创建套接字
udp_socket = socket(AF_INET, SOCK_DGRAM)

# 2. 绑定本地的相关信息，如果一个网络程序不绑定，则系统会随机分配
local_addr = ('', 6666) #    ip地址和端口号，ip一般不用写，表示本机的任何>一个ip
udp_socket.bind(local_addr)

while True :

    print('receive----------------------------------')
    # 3. 等待接收对方发送的数据
    recv_data = udp_socket.recvfrom(1024) #  1024表示本次接收的最大字节数

    # 4. 显示接收到的数据
    print(recv_data[0])
    

# 5. 关闭套接字
udp_socket.close()