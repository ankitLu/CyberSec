import wikipedia;
from builtins import str
from bs4 import BeautifulSoup
from wikipedia.wikipedia import page
import requests
import logging
#print(wikipedia.summary("1"));

logger = logging.getLogger("wiki")
hdlr = logging.FileHandler('ftplog.log')
formatter = logging.Formatter('%(asctime)s %(levelname)s %(message)s')
hdlr.setFormatter(formatter)
logger.addHandler(hdlr)
logger.setLevel(logging.INFO)

totalpages = 0
current_page = 0

class WikiPage:
    pagelist = []
    totalpages = 0


def writePage(pageP):
    global current_page
    current_page = current_page + 1
    logger.info("Writing page: "+ str(current_page) + ":" +pageP.title)
    file = open("C:\\Ankit\\Data\\input\\raw\\wiki\\" + pageP.title + ".txt" ,"w");
    file.write(pageP.title + "\n");
    file.write(str(pageP.content.encode("utf-8")) + "\n");
    file.write(pageP.url)
    file.close();
    return;

def wikiSearch(searchKeyword):
        new_wiki_page = WikiPage()
        new_wiki_page.pagelist = wikipedia.search(searchKeyword)
        new_wiki_page.totalpages = len(new_wiki_page.pagelist)
        return new_wiki_page;


def recursion(arg_wiki_page, index):
    while index > 0:
        for i in range(arg_wiki_page.totalpages):
            page = wikipedia.page(arg_wiki_page.pagelist[i])
            writePage (page)
            url = page.url
            content = requests.get(url)
            soup = BeautifulSoup(content.text,"html.parser")
            for link in soup.find_all('a'):
                href_att = link.get("href")
                searchKeyword = None
                if href_att is not None and "/wiki/" in href_att:
                    searchKeyword = href_att.split("/wiki/")[1]
                if searchKeyword is not None and "Wikipedia:" in searchKeyword: 
                        searchKeyword = searchKeyword.split("Wikipedia:")[1]
                if searchKeyword is not None and "File:" not in searchKeyword:
                    try:
                        wiki_page = wikiSearch(searchKeyword)
                        n = index - 1
                        recursion(wiki_page, n)
                    except wikipedia.exceptions.DisambiguationError:
                        logger.error("Disambiguation exception with search keyword: "+searchKeyword)
                    except wikipedia.exceptions.PageError:
                        logger.error("PageError exception with search keyword: "+searchKeyword)
                    except wikipedia.exceptions.WikipediaException as e:
                        logger.error("Wikipedia exception with search keyword: "+str(e))
        
        index = index - 1                
    return;

wiki_page = wikiSearch("mirai botnet")
index = 3
recursion(wiki_page, index)