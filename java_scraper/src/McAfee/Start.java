package McAfee;


public class Start
{
    /**
     * This is our test. It creates a spider (which creates spider legs) and crawls the web.
     * 
     * @param args
     * - not used
     */
    public static void main(String[] args)
    {
        Worker tech = new Worker();
        //tech.crawlMainPage("https://exchange.xforce.ibmcloud.com/search/trojan", "computer");
        //tech.crawlMainPage("https://www.mcafee.com/threat-intelligence/malware/latest.aspx", "computer");
        for(int i = 9607000; i<= 9607999; i++){
        	tech.crawlMainPage("https://www.mcafee.com/threat-intelligence/malware/default.aspx?id="+i, String.valueOf(i));
        }
        
        
        
        
    }
}
