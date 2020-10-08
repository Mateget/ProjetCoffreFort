package coffrefort;


import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;



public class CoffreFort extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Observer> observers = new ArrayList<Observer>();
    int lockNumber;
    JSONArray listButtons;
    public CoffreFort(JSONObject obj) {
    	this.listButtons = (JSONArray) obj.get("buttons");
    	this.lockNumber = this.listButtons.size();
        setLayout(new GridLayout(2, lockNumber));
        generateChest(listButtons);
        //generateLockHard();
    }
    
    public void generateChest(JSONArray objButtons) {
    	ArrayList<Button> objButtonList = new ArrayList<Button>();
    	for ( int i = 0 ; i < lockNumber ; i++) {
    		ButtonData buttonData = new ButtonData((JSONObject) objButtons.get(i));
    		Lock lock = new Lock("Verrou"+ (i+1),i+1);
    		if(buttonData.isLocked()) lock.lock();
    		else lock.unlock();
    		Button button = new Button("Boutton"+(i+1),buttonData.getLinkedLock(),i+1);
    		if(buttonData.isLocked()) button.unpress();
    		else button.press();
    		lock.setobserver(button);
    		objButtonList.add(button);
    		observers.add(lock);
    		add(lock);
    	}
    	
    	for ( int i = 0 ; i < lockNumber ; i++) {
    		Button button = objButtonList.get(i);
    		button.setObservers(observers);
    		add(button);
    	}
    }
    	
}