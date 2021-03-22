'''
Author: Firefly
Date: 2021-02-02 21:30:19
Descripttion: 
LastEditTime: 2021-02-03 00:58:32
'''

import xlrd  # 引入模块
import xlwt

def deleteSame(l: list):
    ret = []
    first = True
    last = ''
    for line in l:
        if first:
            last = line
            first = False
        else:
            if(line == last or line.replace("\n", "") == last.replace("\n", "")):
                continue
            else:
                last = line
        ret.append(last)
    l = ret

    for i in range(len(l)):
        for j in range(i + 1, len(l)):
            if i < len(l) and j < len(l) and l[i] == l[j]:
                del l[j]
    ret = l

    return ret


def deleteDouble(article: str, fir: str, sec: str):
    temp = deleteSame(article.split(fir))
    temp_new = []
    for line in temp:
        line_temp = deleteSame(line.split(sec))
        temp_new.append(sec.join(line_temp))
    temp = temp_new
    return fir.join(temp)

def deal():

    # 打开文件，获取excel文件的workbook（工作簿）对象
    workbook = xlrd.open_workbook("./三甲传真_补充(1).xls")  # 文件路径

    # 获取所有sheet的名字
    names = workbook.sheet_names()
    print(names)  # ['各省市', '测试表']  输出所有的表名，以列表的形式
    worksheet = workbook.sheet_by_name("Sheet1")
    nrows = worksheet.nrows  # 获取该表总行数

    xls = xlwt.Workbook()
    sht1 = xls.add_sheet('Sheet1')

    slist = ['，', '。', '？', '！', '；']
    header = True
    cnt = 0

    for i in range(nrows):  # 循环打印每一行
        if header:
            print(worksheet.row_values(i)[3])
            header = False
        else:
            # 对于每一篇文章
            content = worksheet.row_values(i)[3]
            content = content.replace("\n", '')
            # 这一篇有重复的测试一下
            for fi in slist:
                for si in slist:
                    content = deleteDouble(content, fi, si)       

            sht1.write(cnt,0,content)
        cnt += 1
        print(cnt)
        
    xls.save('./mydata.xls')
deal()