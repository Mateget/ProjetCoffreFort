package projet;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.json.simple.JSONArray;

import utils.JSONData;
import utils.JSONSave;
import utils.Observer;

public class Interface extends JFrame implements Observer{
	
	JSONData data;
	JSONSave save;
	private String currentGame;
	private JSONArray chest;
	private ArrayList<String> menuString = new ArrayList<String>();
	public Interface() {
		menuString.add("Menu");
		menuString.add("Recommencer");
		this.data = new JSONData();
		this.save = new JSONSave();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initDifficultySelection();
		setSize(800,400);
		setVisible(true);
		
	}
	
	private void initDifficultySelection() {
		//System.out.println("initDifficultySelection");
		getContentPane().removeAll();
		ArrayList<String> difficulty = data.getDifficulty();
		if(!difficulty.contains("Random")) difficulty.add("Random");
		getContentPane().add(new MenuChoix(this,MenuChoix.HORIZONTAL,difficulty));
		getContentPane().repaint();
		getContentPane().revalidate();
	}
	private void initChestSelection(String str) {
		//System.out.println("initChestSelectrion : " + str);
		//System.out.println(data.getChestNames(str));
		getContentPane().removeAll();
		ArrayList<String> names = data.getChestNames(str);
		/*for ( int i = 0 ; i < names.size() ; i++) {
			String name = names.get(i);
			if (save.isPresent(name)) {
				names.remove(i);
				i--;
				names.add(name+" (Done)");
			}
		}*/
		getContentPane().add(new MenuChoix(this,MenuChoix.HORIZONTAL,names));
		getContentPane().repaint();
		getContentPane().revalidate();
	}
	
	private void initGame() {
		getContentPane().removeAll();
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new Coffre(this,chest),BorderLayout.CENTER);
		getContentPane().add(new MenuChoix(this, MenuChoix.VERTICAL, menuString),BorderLayout.EAST);
		getContentPane().repaint();
		getContentPane().revalidate();
	}
		
	@Override
	public void update(Object o) {
		// TODO Auto-generated method stub
		if ( o instanceof String) {
			String str = (String) o;
			//System.out.println(str);
			if (data.getDifficulty().contains(str)) {
				if("Random".equals(str)) {
					chest = new RandomCoffre().getCoffre();
					initGame();
				}
				else initChestSelection(str);
			}
			if (data.getChestNames().contains(str)) {
				currentGame = str;
				chest = data.getChest(currentGame);
				initGame();
			}
			if (menuString.contains(str)) {
				if("Menu".equals(str)) initDifficultySelection();
				if("Recommencer".equals(str)) initGame();
			}
			if("Finish".equals(str)) {
				save.addChest(currentGame);
			}
			
		}
	}

}
