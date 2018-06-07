package html.writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class HtmlWriter {
	
	static String directoryPath = "C:\\Season\\Spring18\\RA\\Sans";
	static ArrayList<FileModel> fileList = new ArrayList<FileModel>();
	static String filename = "Directory";
	
	
	public static void main(String...strings) {
		HtmlWriter hw = new HtmlWriter();
		readDirectory(hw.directoryPath);
		writeHtml();
		
	}
	
	static void readDirectory(String filePath) {
		final File folder = new File(filePath);
		    for (final File fileEntry : folder.listFiles()) {
		        if (fileEntry.isDirectory()) {
		            //readDirectory(fileEntry);
		        } else {
		            System.out.println(fileEntry.getName());
		            FileModel fm = new FileModel(fileEntry.getName(), fileEntry.getAbsolutePath());
		            fileList.add(fm);
		        }
		    }
		}

	
	static void writeHtml() {
		PrintWriter writer;
		try {
			writer = new PrintWriter(directoryPath + "\\" + filename + ".html", "UTF-8");
			int count = 1;
			writer.println("<table border='1'>");
			for(FileModel fmodel: fileList) {
				fmodel.filePath = fmodel.filePath.replace("#", "%23");
				writer.println("<tr><td>"+count+"</td><td><a href=\"" + fmodel.filePath +"\">"+fmodel.fileName+"</a></td></tr>");
				count++;
			}
			writer.println("</table>");
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

}
