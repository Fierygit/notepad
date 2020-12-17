'''
@Author: Firefly
@Date: 2020-02-02 12:36:09
@Descripttion: 
@LastEditTime : 2020-02-10 20:28:10
'''

#coding=utf-8

from socket import *

import get_image as gi
import time

def send():
    
    # 1. 创建udp套接字
    udp_socket = socket(AF_INET, SOCK_DGRAM)
    # 2. 准备接收方的地址
    # '192.168.1.103'表示目的ip地址   # 8080表示目的端口
    dest_addr = ('119.3.239.133', 6667)  # 注意 是元组，ip是字符串，端口是数字
    # 3.获取数据
    flag = True
    while flag :
        # 尝试连接
        try:
            udp_socket.connect(dest_addr)
        except socket.error as msg:
            print(msg)
            print(sys.exit(1))
        # 传输的文件头
        start_info  = "firefly"
        for i in range(0, 7):
            udp_socket.sendto(start_info[i].encode('utf-8'), dest_addr)

        data = gi.get_img()# 获取图片， 调用实现 看树莓派有什么截取图片

        len_data = data[0].to_bytes(4, byteorder='little', signed=False)
        udp_socket.sendto(len_data,dest_addr)
        
        send_data = data[1]
  
        for i in range(len(send_data)//1024+1):
            if 1024*(i+1)>len(send_data):
    	        udp_socket.sendto(send_data[1024*i:], dest_addr)
            else:
                udp_socket.sendto(send_data[1024*i:1024*(i+1)], dest_addr)
        
        flag = True
        time.sleep(3)

        # 5. 关闭套接字
    udp_socket.close()



def main():   
    send()

if __name__ == "__main__" :
    main()