'''
@Author: Firefly
@Date: 2019-12-19 23:27:30
@Descripttion: 
@LastEditTime: 2020-05-20 09:31:55
'''
"""
@author Firefly
@description 文件批量重命名
"""




import os
import re
def rename():
    root = "C:\\Users\\Firefly\\Desktop"
    old_file = os.listdir(root + "\\test")
    
    faild_file = []
    sucess = 0
    for each in old_file:
        temp = str(each).split('.')
        file_name = temp[0]
        end_name = '.' + temp[1]  # 后缀

        print(file_name + end_name)

        # 提取出学号 12 位
        number = re.findall(r'[0-9]{12}', file_name)[0]
        mat = '%-30s'
        print(mat % ('[number]: ' + number), end="")
        name = re.findall(r'[\u4E00-\u9FA5]{2,4}', file_name)
        # 提取出名字
        for ename in name:
            if ename != '计科':
                name = ename
                break
        print(mat % ('[name]: ' + name))
        # 新的名字
        new_name =    name +  number

        old_path = root + "\\test\\" + str(each)           
        new_path = root + "\\rename_file\\" + new_name + end_name

        flag = (os.path.exists(root + "\\rename_file"))
        if flag == False:
            os.mkdir(root + "\\rename_file")

        print('[copy]: ' + old_path + " -> " + new_path)
        res = os.system("copy " + old_path + " " + new_path)

        if res :
            faild_file.append(old_path)
            print("faild %d",res)
        else :
            print("success")
            sucess = sucess + 1

    print("faild file: ")
    for i in faild_file:
        print(i)
    print("all: {0},\tsuccess: {1}".format(len(old_file),sucess))
    print("done...................................................")
    

def main():
    rename()


if __name__ == "__main__":
    main()
