package util;

import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/*
 * Read a JSON file
 */

public class JSONReadFromFile {
	JSONObject jsonObject;

	public JSONReadFromFile(String filePath) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(filePath));
			this.jsonObject = (JSONObject) obj;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JSONObject getJsonObject() {
		return this.jsonObject;
	}
}