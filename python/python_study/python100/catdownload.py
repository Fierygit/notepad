'''
@Author: Firefly
@Date: 2019-08-19 23:18:35
@Descripttion: 
@LastEditTime : 2019-12-19 13:56:15
'''
import urllib.request

res = urllib.request.urlopen("http://www.baidu.com")
info = res.info()
print(info)
with open("test.txt", "wb") as f:
          f.write(info)

          
