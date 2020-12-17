'''
@Author: Firefly
@Date: 2019-12-23 20:38:36
@Descripttion: 
@LastEditTime : 2019-12-23 20:58:37
'''
import tensorflow
from tensorflow.examples.tutorials.mnist import input_data
mnist = input_data.read_data_sets("MNIST_data/", one_hot=True)

print ('MINST图像数据：',mnist.train.images)
print ('训练集规模：',mnist.train.images.shape)
print ('测试集规模：',mnist.test.images.shape)
print ('验证集规模：',mnist.validation.images.shape)

import pylab
print('数据集的第一张图片：')
im = mnist.train.images[0]
im = im.reshape(-1,28)
pylab.imshow(im)
pylab.show()