'''
Author: Firefly
Date: 2020-10-11 17:39:42
Descripttion: 
LastEditTime: 2020-10-11 23:18:21
'''
import time
import json
import random

def get_config(key):
    with open('./config', encoding='utf-8') as f:
        info = f.read().split('\n')
        for val in info:
            if len(val) == 0 or val[0] == '#': continue
            i = 0
            while val[i] != '=':
                i += 1
            if val[0:i] == key: return val[i + 1:]


def save_file(filename, file):
    with open(filename, encoding='utf-8') as f:
        f.write(file)


def get_cookie():
    cookie = get_config('cookie')
    if cookie == None: print("warning!!! cookie is none----------------")
    return cookie


headers = {
    'cookie':
    get_cookie(),
    'user-agent':
    'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36',
    'referer':
    'https://mp.weixin.qq.com/cgi-bin/appmsg?t=media/appmsg_edit_v2&action=edit&isNew=1&type=10&token=370616041&lang=zh_CN',
}


def unix2time(timestamp):
    #转换成localtime
    time_local = time.localtime(timestamp)
    #转换成新的时间格式(2016-05-05 20:28:54)
    return time.strftime("%Y-%m-%d %H:%M:%S", time_local)


def time2unix(dt):
    #转换成时间数组
    timeArray = time.strptime(dt, "%Y-%m-%d %H:%M:%S")
    #转换成时间戳
    return time.mktime(timeArray)


def dict2json(mydict):
    return json.dumps(mydict,
                      sort_keys=True,
                      indent=4,
                      separators=(', ', ': '),
                      ensure_ascii=False)


if __name__ == "__main__":
    print(time2unix('2020-10-01 00:00:00'))
    time.sleep(random.randint(3, 9))
    # print(time2unix(unix2time(1602150215)))
    # mydict = {}
    # mydict['df'] = 123
    # print(dict2json(mydict))
    with open("test.txt", mode='a') as f:
        title = "df"
        detail_url = "dfdf"
        f.write('\n' + title + '\n' + title + '=' + '\n' + detail_url + '\n')
