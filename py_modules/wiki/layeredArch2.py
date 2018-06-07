'''
Created on Jun 6, 2018

@author: still
'''
'''
Created on May 23, 2018

@author: hi5an
'''
import wikipedia;
from builtins import str
from bs4 import BeautifulSoup
from wikipedia.wikipedia import page
import requests
import sys
import logging
import os
from astropy.utils.argparse import directory
from pathlib import Path
#print(wikipedia.summary("1"));


########
logger = logging.getLogger("wiki")
hdlr = logging.FileHandler('ftplog.log')
formatter = logging.Formatter('%(asctime)s %(levelname)s %(message)s')
hdlr.setFormatter(formatter)
logger.addHandler(hdlr)
logger.setLevel(logging.INFO)
########


totalpages = 0
current_page = 0
layer_l1 = []
layer_l2 = []
next_layer = []

class WikiPage:
    pagelist = []
    totalpages = 0


def writePage(pageP):
    try:
        global current_page
        pageTitle = str(pageP.title.encode("utf-8"))
       
        pageP.title = str(pageP.title).replace("/", "_")
        direc = "C:\\Ankit\\Data\\input\\raw\\wiki\\" + pageP.title + ".txt"
        my_file = Path(direc)
        if my_file.is_file():
            print(pageP.title + " already exists")
        else:
            current_page = current_page + 1
            print("Writing page: "+ str(current_page) + ":" + pageTitle)
            file = open(direc,"w");
            file.write(pageP.title + "\n");
            file.write(str(pageP.content.encode("utf-8")) + "\n");
            file.write(pageP.url)
            file.close();
        #if not os.path.exists(direc):
        #    os.makedirs(direc)
        
    except Exception as e:
        print("Exception occurred in writePage with message: "+ str(e))
    return;

def wikiSearch(searchKeyword):
    new_wiki_page = WikiPage()
    new_wiki_page.pagelist = wikipedia.search(searchKeyword)
    new_wiki_page.totalpages = len(new_wiki_page.pagelist)
    return new_wiki_page;


def recursion(arg_wiki_page):
        for i in range(arg_wiki_page.totalpages):
            page = wikipedia.page(arg_wiki_page.pagelist[i])
            writePage (page)
            #url = page.url
            #content = requests.get(url)
            #soup = BeautifulSoup(content.text,"html.parser")
            #links_list = soup.find_all('a')
            
            try:
                links_list = page.links
                global next_layer
                next_layer.append(links_list)
            except:
                logger.error("Exception in recursion finding links for page: "+arg_wiki_page.pagelist[i])
        return;


def loop ():
    global next_layer
    for resultset in next_layer:
        for link in resultset:
            print("Searching on keyword " + link)
            try:
                wiki_page = wikiSearch(link)
                recursion(wiki_page)
            except wikipedia.exceptions.DisambiguationError:
                logger.error("Disambiguation exception with search keyword: "+link)
            except wikipedia.exceptions.PageError:
                logger.error("PageError exception with search keyword: "+link)
            except wikipedia.exceptions.WikipediaException as e:
                logger.error("Wikipedia exception with search keyword: "+ link)
            
    
    
    
## Main code
wiki_page = wikiSearch("Ransomware Israel")
recursion(wiki_page)
loop()



    
    
    
    

    
    