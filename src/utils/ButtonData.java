package utils;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ButtonData {
	private boolean locked;
	private JSONArray jsonArray;
	@SuppressWarnings("unchecked")
	public ButtonData(JSONObject obj) {
		this.locked = (boolean) obj.get("locked");
		if(obj.get("linked")instanceof ArrayList<?>) {
			this.jsonArray = (JSONArray)obj.get("linked");
		}
			
	}
	public boolean isLocked() {
		return locked;
	}
	public ArrayList<Integer> getLinkedLock() {
		ArrayList<Integer> listInteger = new ArrayList<Integer>();
		for ( int i = 0 ; i < jsonArray.size() ; i++) {
			listInteger.add(Integer.parseInt(jsonArray.get(i).toString()));
		}
		return listInteger;
	}
	
	
}

