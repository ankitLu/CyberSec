package attack.jsonParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ParserTest {
	
	
	
public  void parseRelationFile(){
	JSONParser parser = new JSONParser();

    try {     
              
        final File folder = new File("C:/Users/hi5an/Downloads/cti-master/cti-master/enterprise-attack/relationship");
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            
	        } else {
	            String filepath = fileEntry.getAbsolutePath();
	            Object obj = parser.parse(new FileReader(filepath));
	            JSONObject jsonObject =  (JSONObject) obj;

	            JSONArray jsonAray =(JSONArray) jsonObject.get("objects");
	           for (int i=0; i<jsonAray.size(); i++){
	        	   JSONObject jObjjjj = (JSONObject) jsonAray.get(i);
	        	   String targetRef = (String) jObjjjj.get("target_ref");
	        	   if (targetRef.contains("attack-pattern")){
	        		   String attackPatternId = targetRef;
	        		   String example = (String) jObjjjj.get("description");
	        		   //match with attack file
	        		   if (example!=null)
	        			   matchWithAttackFiles(attackPatternId, example);
	        		   
	        	   }
	           }
	        }
	    }
	    System.out.println("Success!");
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    } catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}


/**
 * 
 */

	public void matchWithAttackFiles(String attackPatternId, String example){
		readAttackDirectory(attackPatternId, example);
	}


		public void readAttackDirectory(String attackPatternId, String example){
			final File folder = new File("C:/Users/hi5an/Downloads/cti-master/cti-master/enterprise-attack/attack-pattern");
			    for (final File fileEntry : folder.listFiles()) {
			        if (fileEntry.isDirectory()) {
			            
			        } else {
			            //System.out.println(fileEntry.getAbsolutePath());
			        	parseAttackFile(fileEntry.getAbsolutePath(), attackPatternId, example);
			        }
			    }
			}
		
		/**
		 * 
		 * 
		 * @param file
		 */
		
		public void parseAttackFile(String file, String attackPatternId, String example){
			JSONParser parser = new JSONParser();

		    try {     
		        Object obj = parser.parse(new FileReader(file));

		        JSONObject jsonObject =  (JSONObject) obj;

		        JSONArray jsonAray =(JSONArray) jsonObject.get("objects");
		       for (int i=0; i<jsonAray.size(); i++){
		    	   JSONObject jObjjjj = (JSONObject) jsonAray.get(i);
		    	   String id = (String) jObjjjj.get("id");
		    	   if (id.equals(attackPatternId)){
		    		   JSONArray jsonArr = (JSONArray) jObjjjj.get("external_references");
		    		   String filename = (String) ((JSONObject) jsonArr.get(0)).get("external_id");
		    		   //String example = (String) jObjjjj.get("description");
		    		   // write example to file
		    		   writeExampleTofile(filename, example);
		    	   }
		       }
		   
		    } catch (FileNotFoundException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    } catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		public void writeExampleTofile(String filename, String example){
			String workingDir = "C:/Season/Spring18/RA/output";
			filename += ".txt";
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter("C:/Season/Spring18/RA/output/"+filename, true));
				System.out.println("writing file "+filename);
				//example = example.replace("\n\n","\n");
				writer.write(example + "\n");
				//writer.write("\n");
				writer.close();
			} catch (IOException e) {
					    System.out.println("Exception writing file:"+filename);
					}
			
		}

}
