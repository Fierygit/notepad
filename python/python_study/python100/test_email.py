# -*- encoding: utf-8 -*-
"""
@file: test_email.py
@time: 2019/9/10 16:15
@author: Firefly
@version: 0.1
"""

from smtplib import SMTP
from email.header import Header
from email.mime.text import MIMEText


def test_email(msg):
    sender = '531109985@qq.com'
    receivers = ['507662857@qq.com', '2770394591@qq.com']
    message = MIMEText(msg, 'plain', 'utf-8')
    message['From'] = Header('firefly', 'utf-8')
    message['To'] = Header('wen', 'utf-8')
    message['Subject'] = Header('this is from firefly', 'utf-8')
    smtper = SMTP('smtp.qq.com')
    # with open('/Users/Hao/Desktop/hello.txt', 'rb') as f:
    #     txt = MIMEText(f.read(), 'base64', 'utf-8')
    #     txt['Content-Type'] = 'text/plain'
    #     txt['Content-Disposition'] = 'attachment; filename=hello.txt'
    #     message.attach(txt)
    smtper.login(sender, 'hxuffmhoiuzebjec')
    smtper.sendmail(sender, receivers, message.as_string())
    print('邮件发送完成!')


def main():
    pass
    msg = 'hello this is form python!!!'
    test_email(msg)


if __name__ == "__main__":
    main()
