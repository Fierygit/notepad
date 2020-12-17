'''
@Author: Firefly
@Date: 2020-02-02 12:47:49
@Descripttion: 
@LastEditTime: 2020-02-20 14:48:01
'''
from PIL import Image, ImageFilter
import sys


# 先从文件中读取



def get_img():
    path = r"D:\python\UDP\beach_sunrise.jpg"
    # img = Image.open(path)
    img = open(path, 'rb')
    image = img.read()
    img.close()
    len = sys.getsizeof(image)
    # print(type(len))
    head = 'firefly' + str(len)
    print('send info:' + head)
    return   (len, image)



def main():
    get_img()

if __name__ == "__main__":
    main()
