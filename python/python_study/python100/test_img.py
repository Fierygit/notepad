# -*- encoding: utf-8 -*-
"""
@file: test_img.py
@time: 2019/9/10 16:54
@author: Firefly
@version: 0.1
"""
from PIL import Image, ImageFilter


def test_img():
    img = Image.open('./python_study/python100/test.jpg')
    print(img.format)
    print(img.size)
    print(img.mode)

    # img.show()

    size = 128, 128
    # img.thumbnail(size)
    #  img.show()
    # img.filter(ImageFilter.CONTOUR).show()

    f = open('C:\\Users\\Firefly\\Desktop\\test.jpg', 'w')
    f.write(img)


def main():
    pass
    test_img()


if __name__ == "__main__":
    main()
