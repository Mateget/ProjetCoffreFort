package util;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONSave {

	private JSONObject save;
	private ArrayList<String> completed;
	private String filepath;
	public JSONSave() {
		this.filepath = "./src/util/save.json";
		this.save = new JSONReadFromFile(filepath).getJsonObject();
		initCompleted();
	}
	
	private void initCompleted() {
		this.completed = new ArrayList<String>();
		JSONArray list = (JSONArray)save.get("completed");
		for ( int i = 0 ; i < list.size() ; i++) {
			String name = (String) list.get(i);
			completed.add(name);
		}
	}
	public ArrayList<String> getCompleted(){
		return completed;
	}
	
	public void addChest(String str) {
		if(!completed.contains(str)) {
			completed.add(str);
			save();
		}
	}
	
	public boolean isPresent(String str) {
		if(completed.contains(str)) return true;
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public void save() {
		JSONArray list = new JSONArray();
		for ( int i = 0 ; i < completed.size() ; i++) {
			list.add(completed.get(i));
		}
		JSONObject objSave = new JSONObject();
		objSave.put("completed", list);
		new JSONWriteFromFile(filepath,objSave);
	}

}