import requests
import os
import time


def modify_markdown(file_path, pattern, replace):
    pattern = '/' + pattern + ')'
    lines = []
    with open(file_path, "r", encoding="utf8") as file:
        lines.extend(file.readlines())
        for index, l in enumerate(lines):
            if l.find(pattern) != -1:
                lines[index] = l[0: l.find('(')] + '(' + replace + ')\n'
    with open(file_path, "w", encoding="utf8") as file:
        file.writelines(lines)


def upload_image(img_path):
    data = {"list": [img_path]}
    try:
        response = requests.post(r"http://127.0.0.1:36678/upload", json=data)
    except requests.exceptions.ConnectionError as e:
        time.sleep(30)
        response = requests.post(r"http://127.0.0.1:36678/upload", json=data)
    result = response.text[27:-3]
    return result


def iter_images(blog_path):
    dirs = os.listdir(blog_path)
    for name in dirs:
        if os.path.isdir(blog_path + '/' + name):
            dir_path = blog_path + '/' + name
            md_path = dir_path + '.md'
            print(f"正在重构{md_path}")
            images = os.listdir(dir_path)
            for img in images:
                time.sleep(15)
                print(f"----------------------正在重构{img}")
                img_path = dir_path + '/' + img
                url = upload_image(img_path)
                modify_markdown(md_path, img, url)


def main():
    print("开始重构markdown博客")
    iter_images(r"D:\blog\source\_posts")
    print("重构完成")


if __name__ == '__main__':
    main()
