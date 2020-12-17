# -*- encoding: utf-8 -*-
"""
@file: test_json.py
@time: 2019/9/7 20:46
@author: Firefly
@version: 0.1
"""
import json

"""
dump - 将Python对象按照JSON格式序列化到文件中
dumps - 将Python对象处理成JSON格式的字符串
load - 将文件中的JSON数据反序列化成对象
loads - 将字符串的内容反序列化成Python对象
"""
def test_json():

    mydict = {
        'name': '骆昊',
        'age': 38,
        'qq': 957658,
        'friends': ['王大锤', '白元芳'],
        'cars': [
            {'brand': 'BYD', 'max_speed': 180},
            {'brand': 'Audi', 'max_speed': 280},
            {'brand': 'Benz', 'max_speed': 320}
        ]
    }
    try:
        dumps1 = json.dumps(mydict)
        print(type(dumps1))
        # print(dumps1)
        with open('C:\\Users\\Firefly\\Desktop\\data.json', 'w', encoding='utf-8') as fs:
            json.dump(dumps1, fs)
    except IOError as e:
        print(e)
    finally:
        print("saved")


def main():
    pass
    test_json()


if __name__ == "__main__":
    main()
