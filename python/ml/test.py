import tensorflow as tf
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'

hello = tf.constant("hello,Tensorflow")

sess = tf.Session()

print(tf.run(sess))