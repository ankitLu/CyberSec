package cvedetails;


public class Start
{
    /**
     * This is our test. It creates a spider (which creates spider legs) and crawls the web.
     * 
     * @param args
     *            - not used
     */
    public static void main(String... args)
    {	
        Worker tech = new Worker();
        //tech.crawlMainPage("https://exchange.xforce.ibmcloud.com/search/trojan", "computer");
        //tech.crawlMainPage("https://www.mcafee.com/threat-intelligence/malware/latest.aspx", "computer");
        for(int i = 1; i<= 22; i++){
        	tech.crawlMainPage("https://www.cvedetails.com/vulnerability-list.php?vendor_id=0&product_id=0&version_id=0&page="
        			+i+"&hasexp=0&opdos=1&opec=0&opov=0&opcsrf=0&opgpriv=0&opsqli=0&opxss=0&opdirt=0&opmemc=0&ophttprs=0&opbyp=0&opfileinc=0&opginf=0&cvssscoremin=9&cvssscoremax=0&year=0&month=0&cweid=0&order=1&trc=3549&sha=d217921058214c189913e97e549d11c16c739779\r\n" 
        			, String.valueOf(i));
        }
        
        
    }
}
