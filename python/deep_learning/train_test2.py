import tensorflow as tf
from tensorflow.examples.tutorials.mnist import input_data

tf.reset_default_graph()  #构建图表
mnist = input_data.read_data_sets("MNIST_data/", one_hot=True) #加载数据

#权重初始化函数
def weight_variable(shape): # 使用truncated_normal进行初始化
  initial = tf.truncated_normal(shape, stddev=0.1)
  return tf.Variable(initial)

#偏置初始化函数
def bias_variable(shape): # 偏置定义为常量
  initial = tf.constant(0.1, shape=shape)
  return tf.Variable(initial)

def conv2d(x, W): #x是图片的所有参数，W是此卷积层的权重
 #卷积使用1步长（stride size），0边距（padding size）的模板，保证输出和输入是同一个大小
  return tf.nn.conv2d(x, W, strides=[1, 1, 1, 1], padding='SAME')

#池化函数
def max_pool_2x2(x): 
  #池化的核函数大小为2x2，因此ksize=[1,2,2,1]，步长为2，因此strides=[1,2,2,1]
  return tf.nn.max_pool(x, ksize=[1, 2, 2, 1],strides=[1, 2, 2, 1], padding='SAME')

def Bulit_CNN(x):

  with tf.name_scope('reshape'):
  #我们把x变成一个4d向量，其第2、第3维对应图片的宽、高，最后一维代表图片的颜色通道数
    x_image = tf.reshape(x, [-1, 28, 28, 1]) 


  with tf.name_scope('conv1'): #第一层卷积层
    W_conv1 = weight_variable([5, 5, 1, 32])# 卷积核定义为5x5,1是输入的通道数目，32是输出的通道数目
    b_conv1 = bias_variable([32]) # 每个输出通道对应一个偏置
    h_conv1 = tf.nn.relu(conv2d(x_image, W_conv1) + b_conv1) # 卷积运算，并使用ReLu激活函数激活

  with tf.name_scope('pool1'): #第一层池化层
    h_pool1 = max_pool_2x2(h_conv1) # pooling操作 

  with tf.name_scope('conv2'): #第二层卷积层
    W_conv2 = weight_variable([5, 5, 32, 64]) # 卷积核还是5x5,32个输入通道，64个输出通道
    b_conv2 = bias_variable([64]) # 与输出通道一致
    h_conv2 = tf.nn.relu(conv2d(h_pool1, W_conv2) + b_conv2) # 卷积运算，并使用ReLu激活函数激活

  with tf.name_scope('pool2'): #第二层池化层
    h_pool2 = max_pool_2x2(h_conv2) # pooling操作 

  with tf.name_scope('fc1'): #图片尺寸减小到7x7，我们加入一个有1024个神经元的全连接层，用于处理整个图片
    W_fc1 = weight_variable([7 * 7 * 64, 1024]) # 下面就是定义一般神经网络的操作了，继续扩大为1024
    b_fc1 = bias_variable([1024]) # 对应的偏置

    h_pool2_flat = tf.reshape(h_pool2, [-1, 7*7*64])  # 将最后操作的数据展开
    h_fc1 = tf.nn.relu(tf.matmul(h_pool2_flat, W_fc1) + b_fc1) #把池化层输出的张量reshape成一些向量，乘上权重矩阵，加上偏置，然后对其使用ReLU

  with tf.name_scope('dropout'): # 为了减少过拟合，我们在输出层之前加入dropout
    keep_prob = tf.placeholder(tf.float32)
    h_fc1_drop = tf.nn.dropout(h_fc1, keep_prob)

  with tf.name_scope('fc2'): #输出
    W_fc2 = weight_variable([1024, 10]) # 最后一层权重初始化
    b_fc2 = bias_variable([10])  # 对应偏置

    y_conv = tf.matmul(h_fc1_drop, W_fc2) + b_fc2 
  return y_conv, keep_prob

def CNN(y_conv, keep_prob,x,y_):
     with tf.name_scope('loss'):
          cross_entropy = tf.nn.softmax_cross_entropy_with_logits(labels=y_,logits=y_conv) #返回交叉熵误差

     cross_entropy = tf.reduce_mean(cross_entropy) #求平均值

     with tf.name_scope('adam_optimizer'):
          train_step = tf.train.AdamOptimizer(1e-4).minimize(cross_entropy)  # 调用梯度下降

     with tf.name_scope('accuracy'): #计算准确率
          correct_prediction = tf.equal(tf.argmax(y_conv, 1), tf.argmax(y_, 1))
          correct_prediction = tf.cast(correct_prediction, tf.float32)

     accuracy = tf.reduce_mean(correct_prediction)

     with tf.Session() as sess:
          temp = input("请输入迭代次数：")
          sess.run(tf.initialize_all_variables())
          for i in range(int(temp)):
              batch = mnist.train.next_batch(100)  #使用SGD，每次选取100个数据训练
              # if i % 1 == 0:
              train_accuracy = accuracy.eval(feed_dict={x: batch[0], y_: batch[1], keep_prob: 1.0})  # dropout值定义为0.5
              print ("第"+str(i+1)+"次迭代，训练集正确率："+str(int(train_accuracy*100))+"%")
              train_step.run(feed_dict={x: batch[0], y_: batch[1], keep_prob: 0.5})
          #测试集正确率
          a = 200
          b = 50
          sum = 0
          for i in range(a):
              testSet = mnist.test.next_batch(b)
              c = accuracy.eval(feed_dict={x: testSet[0], y_: testSet[1], keep_prob: 1.0})
              sum += c * b
          print ("全体测试集正确率：" +str(round(sum/(b*a)*100,2))+"%")

x = tf.placeholder(tf.float32, [None, 784])
y_ = tf.placeholder(tf.float32, [None, 10])
y_conv, keep_prob = Bulit_CNN(x)
CNN(y_conv, keep_prob,x,y_)