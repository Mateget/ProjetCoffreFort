package util;

import java.util.ArrayList;
import java.util.Random;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import rename.ChestData;
import rename.GameState;

public class RandomChest {

	private ArrayList<ChestData> coffre;
	private int nbMinLock;
	private int nbMaxLock;
	private int nbMinInteractionButton;
	private int nbMaxInteractionButton;
	private Random randomValue;
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
		while(isEmpty) {
			//System.out.println("Looking for chest !");
			coffre.remove(0);
			coffre.add(generate());
			isEmpty = solver.resolve(this.coffre.get(0),true).isEmpty();
		};
		// Creating 2 more chest in threads to avoid freeze when trying to create a new randomchest later
		newChest();
		newChest();
		
		
	}
 	
 	private void newChest() {
 		//System.out.println("Find chest !");
 		new Thread(new Runnable() {
			
			@Override
			public void run() {
				ChestData chestData = generate();
		 		Solver solver = new Solver();
				while(solver.resolve(chestData,true).isEmpty()) {
					chestData = generate();
				};
				//System.out.println("Founded ! " + chestData );
				coffre.add(chestData);
			}
		}).start();
 		
 	}
 	 		
	public ChestData getCoffre() {
		ChestData chestData = coffre.get(0);
		coffre.remove(0);
		newChest();
		return chestData;
	}
		
	@SuppressWarnings("unchecked")
	public ChestData generate() {
		//System.out.println("Generate");
		ChestData chest = new ChestData();
		int nbLock = nbMinLock + randomValue.nextInt(nbMaxLock - nbMinLock + 1);
		final GameState etatActuel = new GameState();
		for ( int i = 1 ; i <= nbLock ; i++ ) {
			JSONObject objButton = new JSONObject();
			JSONArray linkedButton = new JSONArray();
			int nbInteractionButton = nbMinInteractionButton + randomValue.nextInt(nbMaxInteractionButton - nbMinInteractionButton + 1);
			if ( nbInteractionButton > nbLock ) {
				nbInteractionButton = nbLock;
			}
			linkedButton.add(i);
			while( linkedButton.size() < nbInteractionButton) {
				int value = 1 + randomValue.nextInt(nbLock);
				if(!linkedButton.contains(value)){
					linkedButton.add(value);
				}
			}
			if ( randomValue.nextInt(2) == 1) {
				objButton.put("locked", true);
				etatActuel.add(true);
			}
			else {
				objButton.put("locked", false);
				etatActuel.add(false);
			}
			objButton.put("linked", linkedButton);
			
			chest.add(objButton);
			
		}
		//System.out.println(chest);
		return chest;
		
		
	}
	
}
