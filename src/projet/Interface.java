package projet;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;

import rename.ChestData;
import util.JSONData;
import util.JSONSave;
import util.Observer;
import util.RandomChest;

public class Interface extends JFrame implements Observer {
	
	private static final long serialVersionUID = 1L;
	
	private JSONData data;
	private JSONSave save;
	private String currentGame;
	private ChestData chest;
	private ArrayList<String> menuString = new ArrayList<String>();
	private RandomChest randomCoffre;
	public Interface() {
		randomCoffre = new RandomChest();
		menuString.add("Menu");
		menuString.add("Recommencer");
		this.data = new JSONData();
		this.save = new JSONSave();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initDifficultySelection();
		setSize(1000,400);	
	}
	
	private void initDifficultySelection() {
		getContentPane().removeAll();
		ArrayList<String> difficulty = data.getDifficulty();
		
		if(!difficulty.contains("Random")) difficulty.add("Random");
		getContentPane().add(new MenuChoix(this,MenuChoix.HORIZONTAL,difficulty));
		getContentPane().repaint();
		getContentPane().revalidate();
	}
	private void initChestSelection(String str) {
		getContentPane().removeAll();
		ArrayList<String> names = data.getChestNames(str);
		getContentPane().add(new MenuChoix(this,MenuChoix.HORIZONTAL,names));
		getContentPane().repaint();
		getContentPane().revalidate();
	}
	
	private void initGame() {
		getContentPane().removeAll();
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new Chest(this,chest),BorderLayout.CENTER);
		getContentPane().add(new MenuChoix(this, MenuChoix.VERTICAL, menuString),BorderLayout.EAST);
		getContentPane().repaint();
		getContentPane().revalidate();
	}
			
	
	@Override
	public void update(Object o) {
		if ( o instanceof String) {
			String str = (String) o;
			if (data.getDifficulty().contains(str)) {
				if("Random".equals(str)) {
					chest = randomCoffre.getCoffre();
					initGame();				
				}
				else {
					initChestSelection(str);
				}
			}
			if (data.getChestNames().contains(str)) {
				currentGame = str;
				chest = data.getChest(currentGame);
				initGame();
			}
			if (menuString.contains(str)) {
				if("Menu".equals(str)) initDifficultySelection();
				if("Recommencer".equals(str)) {
					initGame();
				}
			}
			if("Finish".equals(str)) {
				save.addChest(currentGame);
			}
			
		}
	}

}
