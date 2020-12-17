'''
@Author: Firefly
@Date: 2019-12-23 21:23:09
@Descripttion: 
@LastEditTime : 2019-12-24 15:58:36
'''
import numpy as np
import matplotlib.pyplot as plt
def load_mnist():
    
    path = r'mnist.npz' #放置mnist.py的目录。注意斜杠
    f = np.load(path)
    x_train, y_train = f['x_train'], f['y_train']
    x_test, y_test = f['x_test'], f['y_test']
    f.close()
    return (x_train, y_train), (x_test, y_test)
 
(train_image,train_label),(test_image,test_label) = load_mnist()

print(train_image.shape)

for i in range(1,4000,1000):
    print(plt.imshow(train_image[i]))
    print(train_label[i])
    plt.show()
    i = i + 1000
    print(i)