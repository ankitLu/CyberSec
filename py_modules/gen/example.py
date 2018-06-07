
# imports needed and logging
import logging
import gensim
import sys
#from pymongo import MongoClient
from nltk.corpus import stopwords
from string import ascii_lowercase
import pandas as pd
import gensim, os, re, itertools, nltk, snowballstemmer
from gensim.parsing.preprocessing import preprocess_documents, stem_text
from gensim.parsing.preprocessing import strip_punctuation
from gensim.parsing.preprocessing import strip_non_alphanum
from gensim.parsing.preprocessing import strip_numeric
from gensim.utils import lemmatize
from gensim.parsing.preprocessing import STOPWORDS

#from nltk.stem.wordnet import WordNetLemmatizer


 
logging.basicConfig(format="%(asctime)s : %(levelname)s : %(message)s", level=logging.INFO)


with open("C:\Ankit\input\M+S+N+W.txt", "r", encoding="utf8") as f:
    data = f.readlines()
 
 
sentences = [] 



# initialize stemmer
#stemmer = snowballstemmer.EnglishStemmer()

# grab stopword list, extend it a bit, and then turn it into a set for later
#stop = stopwords.words('english')
#stop.extend(['may','also','zero','one','two','three','four','five','six','seven','eight','nine','ten','across','among','beside','however','yet','within']+list(ascii_lowercase))
#stoplist = stemmer.stemWords(stop)
#stoplist = set(stoplist)
#stop = set(sorted(stop + list(stoplist))) 


logging.basicConfig(format='%(asctime)s : %(levelname)s : %(message)s', level=logging.INFO)
 
#print(list(lemmatize("Hello World! record records going goes browser browsers browsing How is it going?! Nonexistentword, 21"  ) ))


wl = print(stem_text("escalate exfiltrate application"))



# remove characters and stoplist words, then generate dictionary of unique words
#data['text_data'].replace('[!"#%\'()*+,-./:;<=>?@\[\]^_`{|}~1234567890’”“′‘\\\]',' ',inplace=True,regex=True)
#wordlist = filter(None, " ".join(list(set(list(itertools.chain(*data['text_data'].str.split(' ')))))).split(" "))
#data['stemmed_text_data'] = [' '.join(filter(None,filter(lambda word: word not in stop, line))) for line in data['text_data'].str.lower().str.split(' ')]

#data.replace('[!"#%\'()*+,-./:;<=>?@\[\]^_`{|}~1234567890’”“′‘\\\]',' ',inplace=True,regex=True)
#lemmatizer = WordNetLemmatizer()

file = open("C:\Ankit\output\output.txt","w")  

for line in data:
    line = strip_punctuation(strip_non_alphanum(strip_numeric(line.lower())))
    file.write(str(line.encode("utf-8")))
    #sentences.append(line.split())
    #line =  lemmatizer.lemmatize(line, nltk.stem.wordnet.NOUN)
    #line = lemmatize(line, allowed_tags=re.compile("NN|VB|JJ|RB", 0))
    #print(list(line))
    #print(line)
    #sentences.append(line)
  
file.close()

print("done")

# build vocabulary and train model
model = gensim.models.Word2Vec(
        sentences,
        size=200,
        window=20,
        min_count=10,
        workers=10)


model.train(sentences, total_examples=len(sentences), epochs=10)

wl = ["run", "send","execut","open","upload","cat","dog","file","screenshot","audio","record","escal","exfiltr","kill", "applic", "offic", "powerpoint",
     "firefox", "browser" ]

for word in wl:
    try:
        print()
        result = model.wv.most_similar(positive=word, topn=10)
        sys.stdout.write(word + ":")
        print(result)
        
    except KeyError:
        print(word +" word not in vocabulary")
