package util;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/*
 * Manage data to create a buttons, wich lock are affected by the button
 */

public class ButtonData {
	private boolean locked;
	private JSONArray jsonArray;
	
	/*
	 * Constructor
	 * @Param : JSONObject
	 * JSONObject contain button data with a JSON format
	 */
	
	public ButtonData(JSONObject obj) {
		this.locked = (boolean) obj.get("locked");
		if(obj.get("linked")instanceof ArrayList<?>) {
			this.jsonArray = (JSONArray)obj.get("linked");
		}
			
	}
	
	/*
	 * Return initial state
	 */
	
	public boolean isLocked() {
		return locked;
	}
	
	/*
	 * Return linked lock for this button
	 */
	
	public ArrayList<Integer> getLinkedLock() {
		ArrayList<Integer> listInteger = new ArrayList<Integer>();
		for ( int i = 0 ; i < jsonArray.size() ; i++) {
			listInteger.add(Integer.parseInt(jsonArray.get(i).toString()));
		}
		return listInteger;
	}
	
	
}

