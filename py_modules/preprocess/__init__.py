from gensim.parsing.preprocessing import strip_punctuation
from gensim.parsing.preprocessing import strip_non_alphanum
from gensim.parsing.preprocessing import strip_numeric
from gensim.parsing.preprocessing import STOPWORDS
import sys

with open("C:\Ankit\input\M+S+N+W.txt", "r", encoding="utf8") as f:
    data = f.readlines()
 
 
count = 0;
#for line in data:

file = open("C:\Ankit\output\output.txt","w")
# Replace the target string

for stop in STOPWORDS:
     sys.stdout.write(stop+",")


print("total stopwords:" + str(len(STOPWORDS)))
for line in data:
    count = count + 1
    line = line.lower()
    print("processing line no."+ str(count))
    for stop in STOPWORDS:
        line = line.replace(" "+stop+" ", "")
    file.write(str(line.encode("utf-8")))
# Write the file out again

#file.write(data)
file.close
    