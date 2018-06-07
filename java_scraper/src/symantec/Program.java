package symantec;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Program {

	private static String thesaurusUrl = "http://www.thesaurus.com/browse/";
	private static String path = "C:\\Ankit\\SW\\Symantec\\";
	private static int fileCount;

	/*
	 * static void Main() { String word = Console.ReadLine();
	 * Program.appendUrlToFile(Program.getAllSynonyms(word, "verb"));
	 * 
	 * }
	 */
	private static String symmantecUrl = "https://www.symantec.com";

	public static void main(String[] args) {
		traverseAllLetter();
	}

	static String traverseAllLetter() {
		char a='A';
        
	       for(int i=0;i<26;i++)
	       {
	    	   traverseTheTable(symmantecUrl + "/security-center/a-z/"+ a);
	           a++;
	       }
		traverseTheTable(symmantecUrl + "/security_response/landing/azlisting.jsp?azid=_1234567890");
		return null;
	}

	static void traverseTheTable(String url) {
		Document doc = null;
		try {
			//System.setProperty("javax.net.ssl.trustStore", "C:\\Ankit\\SW\\Scraper\\Scraper\\wwwsymanteccom.jks");
			doc = Jsoup.connect(url).validateTLSCertificates(false).get();
			doc.removeAttr("option");
			//writeRecommendationToFile("Csource", doc.toString());
			Elements tables = doc.select("table#listings");
			for (Element table : tables) {
				if (table != null) {
					List<Element> nodeList = table.select("tr");
					// table.select("tbody").SelectNodes("tr");
					if (nodeList == null)
						return;
					for (Element row : nodeList) {
						/*
						 * foreach(HtmlNode cell in row.SelectNodes("td")) {
						 * Console.WriteLine(cell.InnerText); }
						 */
						// Console.WriteLine(row.InnerText);

						Element node = row.select("td > a").first();
						if (node != null) {
							String recommendation = getTheRecommendations(node.attr("href"));
							String fileName = MakeValidFileName(node.text());
							if (!IsNullOrEmpty(recommendation)) {
								writeRecommendationToFile(fileName, recommendation);
								//hello
									++fileCount;
									System.out.println(">>"+fileCount+"-"+fileName);
							} else {
								int x = 0;
							}
								
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	static String getTheRecommendations(String url) {
            Document doc = null;
			try {
				//doc = Jsoup.connect(symmantecUrl + url + "&tabid=2").validateTLSCertificates(false).get();
				doc = Jsoup.connect(symmantecUrl + url).validateTLSCertificates(false).get();
				//writeRecommendationToFile("hello", doc.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            doc.removeAttr("option");
            String recommendation = "";

            doc = Jsoup.parse(doc.getElementsByClass("content-band").outerHtml());
            //String ownText = doc.body().ownText();
            recommendation = doc.body().text();

            //System.out.println(ownText);
            //System.out.println(text);
			return recommendation;
        }



	static List<String> getAllSynonyms(String wordToSearch, String partsOfSpeech) {
		String url = Program.thesaurusUrl;
		url += wordToSearch + "/" + partsOfSpeech;
		return Program.collectAllSynonymsByPOS(url, partsOfSpeech);
	}

	/**static void getPartsOfSpeechFilterList(HtmlNode filter) {
            Console.Write(filter.InnerHtml);
            HtmlNodeCollection listItems = filter.SelectNodes(".//select[@id='part-of-speech-filter']//option");
            //Console.Write(listItems.InnerHtml);
           // HtmlNodeCollection tablerow = listItems.SelectNodes("");
            //Console.Write(tablerow);
            foreach (HtmlNode tr in listItems)
            {
                Console.Write(tr.InnerText);
                Console.Write("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            }
        }**/

	static List<String> collectAllSynonymsByPOS(String url, String partsOfSpeach){
            Document doc = null;
			try {
				doc = Jsoup.connect(url).get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
            doc.removeAttr("option");

            //getPartsOfSpeechFilterList(doc.DocumentNode.SelectSingleNode("//div[@class='part-of-speech']//div[@class='main']//div[@class='part-of-speech-filter']"));
            Element synonyms = doc.selectFirst("//div[@id='container']//div[@id='content']//div[@class='main-content']//div[@class='main-content-holder']//div[@class='synonyms_wrapper']//div[@id='synonyms-0']//div[@class='filters']//div[@class='relevancy-block']//div[@class='relevancy-list']");
            List<Element> listItems = synonyms.select("ul");
            int i = 0;
            List<String> synonymList = new ArrayList<String>();
            for (Element tr:listItems) {
                List<Element> inners = tr.select(".//li");
                for(Element li :inners) {
                   Element text = li.selectFirst(".//a/span[@class='text']");
                   synonymList.add(text.text());
                   //Console.Write(text.InnerText);
                   //Console.WriteLine(" ");
                  // i++;
                }
            }
            return synonymList;
        }

	/**
	static void appendUrlToFile(List<String> synonymList) {
            //if (!File.Exists(Program.path)) {
                using (StreamWriter sw = File.CreateText(Program.path))
                {
                    
                    foreach(string s in synonymList) {
                        sw.WriteLine(s);
                    }
                    
                }
            }
            else {
                using (StreamWriter sw = File.AppendText(path))
                {

                    foreach (string s in synonymList)
                    {
                        sw.WriteLine(s);
                    }
                }
            }

        } **/

	private static String MakeValidFileName(String name)
        {
            String validFileName = name.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
            return validFileName;
        }

	static void writeRecommendationToFile(String filename, String recommendation){
		try {
			BufferedWriter writer = new BufferedWriter(
					new FileWriter(path + filename + ".txt"));
			writer.write(recommendation);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
            /*else {
                using (StreamWriter sw = File.AppendText(filename))
                {
                    sw.Write(recommendation);
                    sw.Close();
                }
            }*/

	private static boolean IsNullOrEmpty(String input) {
		if (input!=null && !input.trim().equals(""))
			return false;
		else return true;
	}
		

}
