'''
@Author: Firefly
@Date: 2019-12-23 20:38:36
@Descripttion: 
@LastEditTime: 2019-12-23 21:09:19
'''
import tensorflow as tf #导入tensorflow库
from tensorflow.examples.tutorials.mnist import input_data
mnist = input_data.read_data_sets("MNIST_data/", one_hot=True)
import pylab

tf.reset_default_graph()
# tf Graph Input
x = tf.placeholder(tf.float32, [None, 784]) # mnist data维度 28*28=784
y = tf.placeholder(tf.float32, [None, 10])  # 0-9 数字=> 10 classes

# Set model weights
W = tf.Variable(tf.random_normal([784, 10]))
b = tf.Variable(tf.zeros([10]))

# 构建模型
pred = tf.nn.softmax(tf.matmul(x, W) + b)   # Softmax分类

# Minimize error using cross entropy
cost = tf.reduce_mean(-tf.reduce_sum(y*tf.log(pred), reduction_indices=1))

# 参数设置
learning_rate = 0.01
# 使用梯度下降优化器
optimizer = tf.train.GradientDescentOptimizer(learning_rate).minimize(cost)

temp = input("请输入迭代次数：")
training_epochs = int(temp)
batch_size = 100
display_step = 100
saver = tf.train.Saver()
model_path = "lab4_model/"

# 启动session
with tf.Session() as sess:
    sess.run(tf.global_variables_initializer())# Initializing OP
    # 启动循环开始训练
    for epoch in range(training_epochs):
        avg_cost = 0.
        total_batch = int(mnist.train.num_examples/batch_size)
        # 遍历全部数据集
        for i in range(total_batch):
            batch_xs, batch_ys = mnist.train.next_batch(batch_size)
            # Run optimization op (backprop) and cost op (to get loss value)
            _, c = sess.run([optimizer, cost], feed_dict={x: batch_xs,y: batch_ys})
            # Compute average loss
            avg_cost += c / total_batch
        # 显示训练中的详细信息
        if (epoch+1) % display_step == 0:
            print ("第",'%d次迭代：'%(epoch+1),"cost=","{:.9f}".format(avg_cost))
    print("训练过程结束！")
    # 测试 model
    correct_prediction = tf.equal(tf.argmax(pred, 1), tf.argmax(y, 1))
    # 计算准确率
    accuracy = tf.reduce_mean(tf.cast(correct_prediction, tf.float32))
    print ("准确率：", round(accuracy.eval({x: mnist.test.images, y: mnist.test.labels})*100,2),"%")
    # Save model weights to disk
    save_path = saver.save(sess, model_path)
    print("模型已经保存，保存位置：%s" % save_path)

# 读取模型
print("\n读取模型，第二次测试")
with tf.Session() as sess:
    # Initialize variables
    sess.run(tf.global_variables_initializer())
    # Restore model weights from previously saved model
    saver.restore(sess, model_path)
    # 测试 model
    correct_prediction = tf.equal(tf.argmax(pred, 1), tf.argmax(y, 1))
    # 计算准确率
    accuracy = tf.reduce_mean(tf.cast(correct_prediction, tf.float32))
    print ("准确率：", round(accuracy.eval({x: mnist.test.images, y: mnist.test.labels})*100,2),"%")
    output = tf.argmax(pred, 1)
    batch_xs, batch_ys = mnist.train.next_batch(2)
    outputval,predv = sess.run([output,pred], feed_dict={x: batch_xs})
    print(outputval,predv,batch_ys)
    im = batch_xs[0]
    im = im.reshape(-1,28)
    pylab.imshow(im)
    pylab.show()
    im = batch_xs[1]
    im = im.reshape(-1,28)
    pylab.imshow(im)
    pylab.show()