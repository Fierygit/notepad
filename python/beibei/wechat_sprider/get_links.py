'''
Author: Firefly
Date: 2020-10-08 14:42:52
Descripttion: 
LastEditTime: 2020-10-12 00:16:26
'''
import requests
import json
import re
import util
import time
import random

def get_links():
    url_dict = {
        '三甲传真':
'https://mp.weixin.qq.com/cgi-bin/appmsg?action=list_ex&begin=15&count=5&fakeid=MzAxODI5OTI5Nw==&type=9&query=&token=173417427&lang=zh_CN&f=json&ajax=1'    }
    headers = util.headers

    for name, url in url_dict.items():
        print('start to deal with ', name)
        
        with open(name + "_links.txt",mode='a',encoding='utf-8') as f:

            begin_num = 5
            while True:
                index_i = url.find('begin=')
                index_start = index_i + 6
                while True:
                    if (url[index_i] == '&'): break
                    index_i += 1
                index_end = index_i
                url = url[0:index_start] + str(begin_num) + url[index_end:]
                res = requests.get(url, headers=headers, verify=False).text
                res_dict = json.loads(res)
                if 'app_msg_list' in res_dict:
                    details = res_dict['app_msg_list']
                    stop = False
                    for detail in details:
                        title = detail['title']
                        detail_url = detail['link']
                        create_time = detail['create_time']
                        f.write('\n'+title+'\n'+str(create_time)+'='+util.unix2time(create_time)+'\n'+detail_url+'\n')
                        if util.time2unix('2020-7-01 00:00:00') > create_time : stop = True
                    if stop : break    
                    if len(details) == 0:  
                        f.write('\nsomthing happend in '+ str(begin_num) + '\n'+util.dict2json(res_dict))
                        break
                else: 
                    # 记录出错的位置
                    f.write('\nsomthing happend in '+ str(begin_num) + '\n'+util.dict2json(res_dict))
                    break# 出错了， 暂停
                print('sleep for 10-20s for ' + str(begin_num))
                time.sleep(random.randint(1,2))
                begin_num += 5
                

if __name__ == '__main__':
    get_links()