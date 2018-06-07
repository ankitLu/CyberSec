'''
Created on May 22, 2018

@author: still
'''


from gensim.scripts.glove2word2vec import glove2word2vec
glove_input_file = 'C:\Ankit\SW\glove.42B.300d.txt'
word2vec_output_file = 'C:\Ankit\SW\word2vec.txt'
glove2word2vec(glove_input_file, word2vec_output_file)