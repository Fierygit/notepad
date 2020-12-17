'''
@Author: Firefly
@Date: 2019-09-07 16:33:48
@Descripttion: 
@LastEditTime : 2019-12-20 19:07:16
'''
import os
def main():
    pass
    test_os()
    # test_file()
    # test_image()

def test_os():
    print("打印当前目录： " + os.getcwd())
    path = os.getcwd()
    print(os.path.dirname(path))
    # print("改变当前目录:  " + os.chdir(path))
    # print("查看文件夹下面有什么文件： " + os.listdir(str(path)))
    # print("使用系统的命令： " +  os.system("echo hello"))



def test_file():
    f = open('test.txt', 'r')
    for line in f:
        print(line.replace(' ', '').replace('\n', '').replace('\\',''))
    # 文件不会自动关
    f.close()
    # 但是使用下面的可以
    with open("test.txt") as f:
        for line in f:
            print(line)
        
def test_image():
    # 二进制打开文件
    f = open("test.jpg", 'rb')
    data = f.read()
    # print(data)
    # 写文件
    f1 = open('C:\\Users\\Firefly\\Desktop\\firefly.jpg', 'wb')
    f1.write(data)
    f.close()
    f1.close()




if __name__ == "__main__":
    main()
