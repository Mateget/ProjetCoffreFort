package util;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import rename.ChestData;

public class JSONData {

	private JSONObject data;
	private JSONArray chestlist;
	private ArrayList<String> difficulty;
	public JSONData() {
		String filepath = "./src/util/data.json";
		this.data = new JSONReadFromFile(filepath).getJsonObject();
		this.chestlist = (JSONArray)data.get("chests");
		initDifficulty();
	}
	
	public void initDifficulty() {
		this.difficulty = new ArrayList<String>();
		for ( int i = 0 ; i < chestlist.size() ; i++) {
			JSONObject obj = (JSONObject)chestlist.get(i);
			String diff = (String) obj.get("difficulty");
			if(!difficulty.contains(diff)) {
				difficulty.add(diff);
			}	
		}
	}
	
	public ArrayList<String> getDifficulty(){
		return this.difficulty;
	}
	
	@SuppressWarnings("unchecked")
	public ChestData getChest(String chestName) {
		for ( int i = 0 ; i < chestlist.size() ; i++) {
			JSONObject obj = (JSONObject)chestlist.get(i);
			String name = (String) obj.get("name");
			if(name.equals(chestName)) {
				JSONArray jsonArray = (JSONArray) obj.get("chest");
				ChestData chestData = new ChestData();
				for ( int j = 0 ; j < jsonArray.size() ; j++) {
					chestData.add(jsonArray.get(j));
				}
				return chestData ;
			}	
		}
		return null;
	}
	
	public ArrayList<String> getChestNames(String difficulty){
		ArrayList<String> list = new ArrayList<String>();
		for ( int i = 0 ; i < chestlist.size() ; i++) {
			JSONObject obj = (JSONObject)chestlist.get(i);
			if ( ((String)obj.get("difficulty")).equals(difficulty)){
				list.add((String) obj.get("name"));
			}
		}
		return list;
	}
	
	public ArrayList<String> getChestNames(){
		ArrayList<String> chestNames = new ArrayList<String>();
		for ( int i = 0 ; i < chestlist.size() ; i++) {
			JSONObject obj = (JSONObject)chestlist.get(i);
			chestNames.add((String) obj.get("name"));
		}
		return chestNames;
	}
}
