'''
Author: Firefly
Date: 2020-10-08 23:02:57
Descripttion: 
LastEditTime: 2020-10-13 18:00:11
'''
import requests
import re
import url2md
import util
import os



def read_from_file(filename):
    all_text = []
    flag = set() # 去重
    with open(filename ,encoding='utf8') as f:
        article_list = f.read().split('\n\n')
        i = 0
        while i < len(article_list):
            # 跳过广告
            if len(article_list[i].split('\n')) == 1:
                print(article_list[i])
                i += 2
                continue
            if len(article_list[i].split('\n')) != 3:
                print(article_list[i])
                i += 1
                continue
            if article_list[i] not in flag:
                flag.add(article_list[i]) 
                all_text.append(article_list[i])
            i += 1
            
        print('article nums: ', str(len(all_text)))
    return all_text    
        
def get_res(detail_url):
    headers = util.headers
    detail_res = requests.get(detail_url, headers=headers, verify=False).text
    con = ''.join(
            re.findall(r'<div class="rich_media_content " id="js_content".*?</div>', detail_res, re.DOTALL))
    # video = re.search(r'video', con)
    # if video:
    #     return "video"
    # con = con.replace('\n', '').replace('style="visibility: hidden;"', '').replace('data-src', 'src')
    # rep = re.search(r'<img.*?>', con, re.DOTALL)
    # if rep:
    #     rep = rep.group()
    # rep1 = '点击上方蓝色字体，关注我们'
    # rep2 = '给我点【在看】你也越好看'
    # content = con.replace(str(rep), '').replace(rep1, '').replace(rep2, '')
    return con

def get_article_by_links(filename):

    all_text = read_from_file(filename)
    return None
    dir_name = filename.split('.')[0]
    if not os.path.exists(dir_name):
        os.mkdir(dir_name)
    beiebi = 1
    for index, one_text in enumerate(all_text):       
        print(index, end=" ")       
        one_info = one_text.split('\n')
        if len(one_info) != 3:
            print(one_text)
            continue
        if len(one_info[1]) == 0 or len(one_info[1].split('='))== 0:
            print(one_text)
            continue
        name = dir_name + '/' + one_info[1].split('=')[1] + str(beibei)
        beiebi += 1
        name = name.replace(' ','_').replace(':','_')
        print(name)
        html = get_res(one_info[2])
        
        with open( name,mode='a',encoding='utf8') as f:
            f.write(html)
    


def main():
    
    filename = '丁香医生_links.txt'

    get_article_by_links(filename)


if __name__ == '__main__':
    main()