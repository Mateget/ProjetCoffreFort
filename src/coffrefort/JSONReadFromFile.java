package coffrefort;

import java.io.File;
import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
 
 
public class JSONReadFromFile {
	JSONObject jsonObject;
	public JSONReadFromFile(String filePath) {
		JSONParser parser = new JSONParser();
		try {
			System.out.println("Path : "+new File("").getAbsolutePath());
			
			Object obj = parser.parse(new FileReader(filePath));
 
			// A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
			this.jsonObject = (JSONObject) obj;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public JSONObject getJsonObject() {
		return this.jsonObject;
	}
}