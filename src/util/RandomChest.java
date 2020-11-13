package util;

import java.util.ArrayList;
import java.util.Random;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import rename.ChestData;
import rename.GameState;

/*
 * Random chest generator
 */

public class RandomChest {

	private ArrayList<ChestData> coffre;
	private int nbMinLock;
	private int nbMaxLock;
	private int nbMinInteractionButton;
	private int nbMaxInteractionButton;
	private Random randomValue;
	
	/*
	 * Constructor, initialize parameters for chests generation
	 */
	
 	public RandomChest() {
 		coffre = new ArrayList<ChestData>();
 		randomValue = new Random();
 		nbMinLock = 5;
		nbMaxLock = 8;
		nbMinInteractionButton = 2;
		nbMaxInteractionButton = 5;
		
		this.coffre.add(generate());
		
 		Solver solver = new Solver();
 		boolean isEmpty = true;
 		// Game need at least one chest to open itself
		while(isEmpty) {
			coffre.remove(0);
			coffre.add(generate());
			isEmpty = solver.resolve(this.coffre.get(0),true).isEmpty();
		};
		// Creating 3 more chest in threads to avoid freeze when trying to load new randomchest later
		newChest();
		newChest();
		newChest();
		
		
	}
 	
 	/*
 	 * Generate a chest
 	 */
 	
 	private void newChest() {
 		new Thread(new Runnable() {
			
			@Override
			public void run() {
				ChestData chestData = generate();
		 		Solver solver = new Solver();
				while(solver.resolve(chestData,true).isEmpty()) {
					chestData = generate();
				};
				coffre.add(chestData);
			}
		}).start();
 		
 	}
 	
 	/*
 	 * Return one random chest, remove it from the list, process to find an other chest in a Thread
 	 */
 	 		
	public ChestData getCoffre() {
		ChestData chestData = coffre.get(0);
		coffre.remove(0);
		newChest();
		return chestData;
	}
		
	/*
	 * Generate chest with parameters
	 */
	
	@SuppressWarnings("unchecked")
	public ChestData generate() {
		ChestData chest = new ChestData();
		// Choose number of lock
		int nbLock = nbMinLock + randomValue.nextInt(nbMaxLock - nbMinLock + 1);
		final GameState etatActuel = new GameState();
		for ( int i = 1 ; i <= nbLock ; i++ ) {
			JSONObject objChest = new JSONObject();
			JSONArray linkedButton = new JSONArray();
			// Choose number of lock linked to button
			int nbInteractionButton = nbMinInteractionButton + randomValue.nextInt(nbMaxInteractionButton - nbMinInteractionButton + 1);
			if ( nbInteractionButton > nbLock ) {
				nbInteractionButton = nbLock;
			}
			linkedButton.add(i);
			while( linkedButton.size() < nbInteractionButton) {
				// Choose a linked lock
				int value = 1 + randomValue.nextInt(nbLock);
				if(!linkedButton.contains(value)){
					linkedButton.add(value);
				}
			}
			// Choose default state of lock
			if ( randomValue.nextInt(3) == 1) {
				objChest.put("locked", false);
				etatActuel.add(false);
			}
			else {
				objChest.put("locked", true);
				etatActuel.add(true);
				
			}
			objChest.put("linked", linkedButton);
			
			chest.add(objChest);
			
		}
		return chest;
		
		
	}
	
}
