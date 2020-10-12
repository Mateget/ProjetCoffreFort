package coffre;

import java.util.ArrayList;

import org.json.simple.JSONObject;

public class ButtonData {
	private boolean locked;
	private ArrayList<Long> linkedLock;
	@SuppressWarnings("unchecked")
	public ButtonData(JSONObject obj) {
		this.locked = (boolean) obj.get("locked");
		if(obj.get("linked")instanceof ArrayList<?>) {
			this.linkedLock = (ArrayList<Long>) obj.get("linked");
		}
			
	}
	public boolean isLocked() {
		return locked;
	}
	public ArrayList<Integer> getLinkedLock() {
		ArrayList<Integer> listInteger = new ArrayList<Integer>();
		for ( int i = 0 ; i < linkedLock.size() ; i++) {
			listInteger.add( linkedLock.get(i).intValue());
		}
		return listInteger;
	}
	
	
}

