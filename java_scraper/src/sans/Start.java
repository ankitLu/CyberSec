package sans;


public class Start
{
    /**
     * This is our test. It creates a spider (which creates spider legs) and crawls the web.
     * 
     * @param args
     *            - not used
     */
	
	static String folder = "xviii";
	
    public static void main(String... args)
    {	
        Worker tech = new Worker();
        //tech.crawlMainPage("https://exchange.xforce.ibmcloud.com/search/trojan", "computer");
        //tech.crawlMainPage("https://www.mcafee.com/threat-intelligence/malware/latest.aspx", "computer");
        
        for(int i = 1; i<= 220; i++){
        	tech.crawlMainPage("https://www.sans.org/newsletters/newsbites/"+ folder +"/"+i, String.valueOf(i));
        }
        
        
    }
}
