package symantec;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class UrlWriter {

	
	private static String symmantecUrl = "https://www.symantec.com";
	private static BufferedWriter writer;
	private static String outputfile = "C:\\Ankit\\SW\\Symantec\\symantec_urls.csv";
	private static int count = 0;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		try {
			writer = new BufferedWriter(
					new FileWriter(outputfile));
		char a='A';
        
			       for(int i=0;i<26;i++)
			       {
			    	   traverseTheTable(symmantecUrl + "/security-center/a-z/"+ a);
			           a++;
			       }
				traverseTheTable(symmantecUrl + "/security_response/landing/azlisting.jsp?azid=_1234567890");
		writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
							String malwareUrl = symmantecUrl + node.attr("href");
							String fileName = MakeValidFileName(node.text());
							String content = fileName +","+ malwareUrl;
							writer.write(content);
							writer.newLine();
							++count;
							System.out.println(count+">>"+content);
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	private static String MakeValidFileName(String name)
    {
        String validFileName = name.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
        return validFileName;
    }
}
