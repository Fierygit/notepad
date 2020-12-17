# -*- encoding: utf-8 -*-
"""
@file: test_property.py
@time: 2019/9/7 12:19
@author: Firefly
@version: 0.1
"""


class Person:
    """
    通过  slotes 来确认参数的生成
    """
    __slots__ = ('_name', '_age')

    def __init__(self, name, age):
        self._name = name
        self._age = age

    @property
    def name(self):
        return self._name

    @property
    def age(self):
        return self._age

    @age.setter
    def age(self, age):
        self._age = age

    def show_msg(self):
        print(self._name + " " + str(self._age))


def main():
    pass
    person = Person("firefly", 1)
    person.show_msg()
    person.age = 2
    person.show_msg()


if __name__ == "__main__":
    main()
