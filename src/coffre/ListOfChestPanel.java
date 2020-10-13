package coffre;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ListOfChestPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JSONArray chestslist;
	private JButton menuButton;
	public ListOfChestPanel(JSONArray chestslist,JButton menuButton) {
		this.chestslist = chestslist;
		this.menuButton = menuButton;
		setLayout(new GridLayout(1,chestslist.size()));
		
		init();
		
	}

	
	public void init() {
		for (int i = 0 ; i < chestslist.size() ; i++) {
			JSONObject objChest = (JSONObject) chestslist.get(i);
			//ChestGenerator chest = new ChestGenerator((JSONArray) objChest.get("buttons"));
			String chestName = (String) objChest.get("name");
			JButton button = new JButton(chestName);
			JSONReadFromFile saveFile = new JSONReadFromFile("./src/coffre/save.json");
			JSONArray listOfCompletedChest = (JSONArray) saveFile.getJsonObject().get("completed");
			for ( int j = 0 ; j < listOfCompletedChest.size() ; j++) {
				//System.out.println("IF : "+listOfCompletedChest.get(j).toString() + "==" + objChest.get("name"));
				if(listOfCompletedChest.get(j).toString().equals(chestName)) {
					button.setText("<Html>"+chestName+"<br>"+"Already Done</Html>");
				}
			}
			
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					initGame(objChest);
				}
			});
			add(button);
		}
	}
	
	private void initGame(JSONObject objChest) {
		ChestGenerator chest = new ChestGenerator((JSONArray) objChest.get("buttons"),(String) objChest.get("name"),true);
		//System.out.println("INIT game"+objChest.get("name"));
		JPanel pan = new JPanel();
		pan.setLayout(new GridLayout(2,1));
		pan.setName("test");
		pan.add(menuButton);
		pan.add(resetButton(objChest));
		removeAll();
		setLayout(new BorderLayout());
		add(chest,BorderLayout.CENTER);
		add(pan,BorderLayout.EAST);
		repaint();
		revalidate();
	}
		
	private JButton resetButton(JSONObject objChest) {
		
		JButton resetButton = new JButton("Recommencer");
		resetButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println("Reset game"+objChest.get("name"));
				initGame(objChest);
				
			}
		});
		return resetButton;
	}

}
