package miter;


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
        for(int i = 0; i<= 800; i++){
        	tech.crawlMainPage("https://cwe.mitre.org/data/definitions/"+i+".html", String.valueOf(i));
        }
        
        
        
        
    }
}
