package coffre;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.json.simple.JSONArray;



public class Interface extends JFrame {
	private static final long serialVersionUID = 1L;
	String filePath;
	JSONReadFromFile hey ;
	public Interface(String name) {
		super(name);
		filePath = new String("src/coffre/data.json");
		this.hey = new JSONReadFromFile(filePath);
		
		//ArrayList<ChestGenerator> chestPanelList = chestlistPanel.getChestPanelList();
		
		setSize(900, 450);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setMenu();
	}
	
	private void setMenu() {
		getContentPane().removeAll();
		JSONArray chestslist = (JSONArray) hey.getJsonObject().get("chests");
		
		ListOfChestPanel chestlistPanel = new ListOfChestPanel(chestslist,menuButton());
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(chestlistPanel, BorderLayout.CENTER);
		repaint();
		revalidate();
	}
	
	private JButton menuButton() {
		JButton menuButton = new JButton("Menu");
		menuButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setMenu();
			}
		});
		return menuButton;
	}
	
		
}
