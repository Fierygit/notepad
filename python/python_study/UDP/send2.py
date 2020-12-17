'''
@Author: Firefly
@Date: 2020-02-04 16:05:04
@Descripttion: 
@LastEditTime : 2020-02-04 16:22:50
'''
import socket
import sys
import os
import time

host = '127.0.0.1'
port = 6667

try:
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    sock.connect((host, port))  #服务器和客户端都在一个系统下时使用的ip和端口
except socket.error as msg:
    print(msg)
    print(sys.exit(1))
count = 0
while True:
    start = "firefly"
    for i in range(0, 7):
        sock.sendto(start[i].encode('utf-8'), (host, port))

    
     
    filepath = './beach_sunrise' + '.jpg'

    len = os.path.getsize(filepath)
    size = len.to_bytes(4, byteorder='little', signed=False)
    sock.sendto(size, (host, port))
    fp = open(filepath, 'rb')  # 打开要传输的图片
    
    while True:
        data = fp.read(1024)  # 读入图片数据
        if not data:
            print('{0} send over...'.format(filepath))
            break
        sock.sendto(data, (host, port))  # 以二进制格式发送图片数据

    time.sleep(0.5) # 每隔0.5 秒休眠
