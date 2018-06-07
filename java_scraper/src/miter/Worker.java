package miter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

public class Worker {
	private static final int MAX_PAGES_TO_SEARCH = 20;
	private Set<String> pagesVisited = new HashSet<String>();
	private List<String> pagesToVisit = new LinkedList<String>();
	Document htmlDocument;
	private List<String> links = new ArrayList<String>();
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

	/**
	 * Our main launching point for the Spider's functionality. Internally it
	 * creates spider legs that make an HTTP request and parse the response (the web
	 * page).
	 * 
	 * @param url
	 *            - The starting point of the spider
	 * @param searchWord
	 *            - The word or string that you are searching for
	 */
	public void crawlMainPage(String url, String id) {
		try {
			Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
			Document htmlDocument = connection.timeout(600000).get();
			this.htmlDocument = htmlDocument;

			// System.out.println(text);
			if (connection.response().statusCode() == 200) // 200 is the HTTP OK status code
															// indicating that everything is great.
			{
				System.out.println("\n**Visiting** Received web page at " + url);
			}
			if (!connection.response().contentType().contains("text/html")) {
				System.out.println("**Failure** Retrieved something other than HTML");
				System.exit(0);
			}

			String text = htmlDocument.body().text();
			boolean check = false;
			if (text.contains("Terminology")) {
				text = text.split("Terminology")[1];
				if (text.contains("Taxonomy Mappings")) {
					text = text.split("Taxonomy Mappings")[0];
					check = true;
				}
				if(check)
					System.out.println("OK "+ id);
				else
					System.out.println("Error Taxonomy not found "+ id);
				writeToFile(text, id);
			}
				
			
			//activities = activities.split("Activities Risk Levels")[1];
			
			
			

		} catch (Exception ex) {
			System.out.println("OOOPpppsss!! Exception");
			ex.printStackTrace();
		}

	}

	

	public void writeToFile(String text, String filename) {

		try {
			filename = "CWE-"+ filename;
			BufferedWriter writer = new BufferedWriter(
					new FileWriter("C:/Season/Spring18/RA/miter/" + filename + ".txt"));
			writer.write(text);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
