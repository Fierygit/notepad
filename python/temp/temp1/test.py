'''
Author: Firefly
Date: 2020-12-17 11:19:19
Descripttion: 
LastEditTime: 2020-12-17 12:24:27
'''

import json
import random
import jieba

def main():

    title = []
    content = []
    teacher_name = []
    teacher_level = []
    msg = {}

    all_word = {}

    with open('data.json',encoding='utf-8') as f:
        
        json_dict = json.loads(f.read())
        for i in json_dict['data']:           
            title.append(i['ktmc'])
            content.append(i['mdyyq'])
            teacher_name.append(i['zdlsname'])
            teacher_level.append(i['jszcname'])

        # msg['title'] = title
        # msg['content'] = content
        # msg['teacher_name'] = teacher_name
        # msg['teacher_level'] = teacher_level
        for i in range(len(title)):
            msg[str(i)] = {'title': title[i],'content':content[i],'teacher_name':teacher_name[i],'teacher_level': teacher_level[i]}

        for k,v in msg.items():
            # print(k)
            # print(v)
            # break
            if v['teacher_name'] == '谭光华':
                print(v)
                print(' ')

            

            # if k == str(random.randint(0,len(msg) - 1)):
            #     print(v)
            # print(v['content'])
            
        #     skip = ['"', '的', ';', ':', ";", '；','.', '1','2','3','4','5','6','7','8','、', '（','）']
            
        #     words = '|'.join(jieba.cut(v['content']))
        #     for i in words.split('|'):
        #         i = i.strip(' ')
        #         if i in skip:
        #             continue
        #         if i not in all_word.keys():
        #             all_word[i] = 1
        #         else:
        #              all_word[i] =  all_word[i] + 1
        # # print(all_word)
        # sort = []   
        # for k,v in all_word.items():
        #     if  v > 50 and len(k) >= 2:
        #         # print(k,v)
        #         sort.append((v,k))
            
        # sort.sort()
        # print(sort)

if __name__ == '__main__':
    main()

