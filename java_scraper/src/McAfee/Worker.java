package McAfee;

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

			StringBuilder sb = new StringBuilder();
			boolean start = false;
			org.jsoup.select.Elements tables = htmlDocument.select("table");
			for (org.jsoup.nodes.Element table : tables) {
				org.jsoup.select.Elements heads = table.select("th");
				if (heads.text().contains("Activities")) {
					org.jsoup.select.Elements rows = table.select("tr");
					for (org.jsoup.nodes.Element row : rows) {
								sb.append(row.text()+"\n");
								//System.out.println(column.text());
					}
				}
			}

			String activities = sb.toString();
			//activities = activities.split("Activities Risk Levels")[1];
			
			String text = textRefine(htmlDocument, activities);
			
			writeToFile(text, id);

		} catch (Exception ex) {
			System.out.println("OOOPpppsss!! Exception");
			ex.printStackTrace();
		}

	}

	private String textRefine(Document htmlDocument, String activities) {
		String text;
		Element body = htmlDocument.body();

		String html = htmlDocument.html();

		// htmlDocument.outputSettings(new
		// Document.OutputSettings().prettyPrint(false));//makes html() preserve
		// linebreaks and spacing

		String s = body.wholeText();
		s = s.replaceAll("( \r\n)+", "");
		s = s.replaceAll("(\r\n)+", "\n");
		s = s.trim();

		text = Jsoup.clean(s, "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false));
		text = text.split("Security Advisory")[1];
		text = text.split("Corporate Headquarters")[0];
		text = text.split("This page shows details and results of our analysis on the malware")[1];
		text = text.replaceAll("( )+", " ");
		String title = text.split("Download Current DAT")[0];
		String para = text.split("System Changes")[1];
		para = para.replaceAll("( \r\n)+", "");
		para = para.replaceAll("(\r\n)+", "\n");
		para = para.trim();
		text = title + "\nActivities\n" + activities + "\nSystem Changes\n" + para;
		return text;
	}

	public void writeToFile(String text, String filename) {

		try {
			BufferedWriter writer = new BufferedWriter(
					new FileWriter("C:/Season/Spring18/RA/McAfee2/" + filename + ".txt"));
			writer.write(text);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
