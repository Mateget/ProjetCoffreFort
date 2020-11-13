package util;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/*
 * Manage save data
 */

public class JSONSave {

	private JSONObject save;
	private ArrayList<String> completed;
	private String filepath;
	
	/*
	 * Constructor initialize data
	 */
	
	public JSONSave() {
		this.filepath = "./src/util/save.json";
		this.save = new JSONReadFromFile(filepath).getJsonObject();
		initCompleted();
	}
	
	/*
	 * Initialize completed chest list
	 */
	
	private void initCompleted() {
		this.completed = new ArrayList<String>();
		JSONArray list = (JSONArray)save.get("completed");
		for ( int i = 0 ; i < list.size() ; i++) {
			String name = (String) list.get(i);
			completed.add(name);
		}
	}
	
	/*
	 * Return list of completed chest name
	 */
	
	public ArrayList<String> getCompleted(){
		return completed;
	}
	
	/*
	 * Add a chest to completed list
	 * @Param : String
	 * String name of the chest
	 */
	
	public void addChest(String str) {
		if(!completed.contains(str)) {
			completed.add(str);
			save();
		}
	}
	
	/*
	 * Return if the name of a chest is present on the list
	 * @Param : String
	 * String name of the chest
	 */
	
	public boolean isPresent(String str) {
		if(completed.contains(str)) return true;
		return false;
	}
	
	/*
	 * Save completed chest list in file
	 */
	
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