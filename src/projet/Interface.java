package projet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.json.simple.JSONArray;

import utils.JSONData;
import utils.JSONSave;
import utils.Observer;
import utils.RandomCoffre;

public class Interface extends JFrame implements Observer{
	JSONData data;
	JSONSave save;
	private String currentGame;
	private JSONArray chest;
	private int difficulty = 6;
	private ArrayList<String> menuString = new ArrayList<String>();
	private RandomCoffre randomCoffre = new RandomCoffre();
	private ArrayList<Integer> path;
	private MenuChoix menuChoix;
	private boolean randomChest;
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
		menuChoix = new MenuChoix(this,MenuChoix.HORIZONTAL,difficulty);
		getContentPane().add(menuChoix);
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
		randomCoffre.resoudre(chest,randomChest);
		int pathIndex = (int)(Math.round((Math.random()*(randomCoffre.getSolutions().size()-1))));
		//System.out.println(pathIndex + " " + randomCoffre.getSolutions().size());
		path = randomCoffre.getSolutions().get(pathIndex);
		getContentPane().removeAll();
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new Coffre(this,chest,path),BorderLayout.CENTER);
		getContentPane().add(new MenuChoix(this, MenuChoix.VERTICAL, menuString),BorderLayout.EAST);
		getContentPane().repaint();
		getContentPane().revalidate();
	}
			
	
	private void test() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				chest = randomCoffre.getCoffre(difficulty);
			}
		});

		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void update(Object o) {
		// TODO Auto-generated method stub
		if ( o instanceof String) {
			String str = (String) o;
			//System.out.println(str);
			if (data.getDifficulty().contains(str)) {
				if("Random".equals(str)) {
					randomChest = true;
					Runnable run1 = new Runnable() {
						
						@Override
						public void run() {
							menuChoix.removeAll();
							
							JLabel label = new JLabel("Loading . . .");
							label.setFont(new Font("Serif", Font.PLAIN, 50));
							label.setForeground(Color.GRAY);
							label.setHorizontalAlignment(label.getWidth()/2);
							menuChoix.add(label);
							menuChoix.repaint();
							menuChoix.revalidate();
							
						}
					};
					
					Runnable run2 = new Runnable() {
						@Override
						public void run() {
							test();
							initGame();							
						}
					};
					run1.run();
					run2.run();					
				}
				else {
					initChestSelection(str);
				}
			}
			if (data.getChestNames().contains(str)) {
				currentGame = str;
				chest = data.getChest(currentGame);
				randomChest = false;
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
