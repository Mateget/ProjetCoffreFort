package display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;

import rename.ChestData;
import util.JSONData;
import util.JSONSave;
import util.Observer;
import util.RandomChest;

/*
 * JFrame manage game components
 * Implements Observer to get notify by components
 */

public class Interface extends JFrame implements Observer {
	
	private static final long serialVersionUID = 1L;
	
	private JSONData data;
	private JSONSave save;
	private String currentGame;
	private ChestData chest;
	private ArrayList<String> menuString = new ArrayList<String>();
	private RandomChest randomCoffre;
	
	/*
	 * Constructor
	 */
	
	public Interface() {
		randomCoffre = new RandomChest();
		this.setBackground(new Color(139,139,139));
		menuString.add("Menu");
		menuString.add("Restart");
		this.data = new JSONData();
		this.save = new JSONSave();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initDifficultySelection();
		setSize(1400,800);	
		setResizable(false);
	}
	
	/*
	 * Display game difficulty selection
	 */
	
	private void initDifficultySelection() {
		getContentPane().removeAll();
		ArrayList<String> difficulty = data.getDifficulty();
		
		if(!difficulty.contains("Random")) difficulty.add("Random");
		getContentPane().add(new MenuChoix(this,MenuChoix.HORIZONTALFULL,difficulty));
		getContentPane().repaint();
		getContentPane().revalidate();
	}
	
	/*
	 * Display chest selection
	 */
	
	private void initChestSelection(String str) {
		getContentPane().removeAll();
		ArrayList<String> names = data.getChestNames(str);
		getContentPane().add(new MenuChoix(this,MenuChoix.HORIZONTALFULL,names));
		getContentPane().repaint();
		getContentPane().revalidate();
	}
	
	/*
	 * Initialize game
	 */
	
	private void initGame() {
		getContentPane().removeAll();
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new Chest(this,chest),BorderLayout.CENTER);
		getContentPane().add(new MenuChoix(this, MenuChoix.VERTICAL, menuString),BorderLayout.EAST);
		getContentPane().repaint();
		getContentPane().revalidate();
	}
			
	/*
	 * Update function Overrided from Observer
	 * Manage screen with update recieved from others class
	 */
	
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
				if("Restart".equals(str)) {
					initGame();	
				}
			}
			if("Finish".equals(str)) {
				save.addChest(currentGame);
			}
			
		}
	}

}
