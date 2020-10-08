package coffrefort;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;



public class Interface extends JFrame {
	private static final long serialVersionUID = 1L;
	String filePath;
	JSONReadFromFile hey ;
	public Interface(String name) {
		super(name);
		filePath = new String("src/coffrefort/data.json");
		this.hey = new JSONReadFromFile(filePath);
		JSONArray chestslist = (JSONArray) hey.getJsonObject().get("chests");
		JPanel jpanel = new JPanel();
		jpanel.setLayout(new GridLayout(1,chestslist.size()));
		for (int i = 0 ; i < chestslist.size() ; i++) {
			JSONObject objChest = (JSONObject)chestslist.get(i);
			JSONObject coffreData = (JSONObject) chestslist.get(i);
			CoffreFort chest = new CoffreFort(coffreData);
			JButton button = new JButton((String)objChest.get("name"));
			button.addActionListener(new ChoseChest(chest,this));
			jpanel.add(button);
		}
		
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(jpanel);
		
	}
	
}
