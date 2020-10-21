package util;

import java.io.FileWriter;
import java.io.IOException;
 
import org.json.simple.JSONObject;

public class JSONWriteFromFile {
    public JSONWriteFromFile(String filePath, JSONObject objJSON) {         

        try (
        	FileWriter file = new FileWriter(filePath)) {
            file.write(objJSON.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}