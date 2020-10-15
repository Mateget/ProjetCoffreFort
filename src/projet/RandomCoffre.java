package projet;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import utils.GameState;

public class RandomCoffre {

	private JSONArray coffre;
	private int nbMinLock;
	private int nbMaxLock;
	private int nbMinInteractionButton;
	private int nbMaxInteractionButton;
	public RandomCoffre() {
		coffre = new JSONArray();
		nbMinLock = 3;
		nbMaxLock = 10;
		nbMinInteractionButton = 2;
		nbMaxInteractionButton = 5;
		generate();
	}
	
	public JSONArray getCoffre() {
		return this.coffre;
	}
	
	@SuppressWarnings("unchecked")
	public void generate() {
		
		int nbLock = (int) Math.round(( nbMinLock + Math.random() * (nbMaxLock - nbMinLock)));
		System.out.println("NbLock :" + nbLock);
		ArrayList etatActuel = new ArrayList<Boolean>();
		for ( int i = 1 ; i <= nbLock ; i++ ) {
			JSONObject jsonObject = new JSONObject();
			JSONArray interactionList = new JSONArray();
			int nbInteractionButton = (int) Math.round(( nbMinInteractionButton + Math.random() * (nbMaxInteractionButton - nbMinInteractionButton)));
			interactionList.add(i);
			while(interactionList.size()<nbInteractionButton) {
				int value = (int)(1 + Math.round((Math.random()*(nbLock-1))));
				System.out.println(interactionList + "contain : " + value + "? " + interactionList.contains(value));
				if(!interactionList.contains(value)){
					System.out.println(value + "added to : " + interactionList );
					interactionList.add(value);
				}
			}
			int test = (int)Math.round(Math.random());
			System.out.println(test);
			if ( test == 1) {
				jsonObject.put("locked", true);
				etatActuel.add(true);
			}
			else {
				jsonObject.put("locked", false);
				etatActuel.add(false);
			}
			jsonObject.put("linked", interactionList);
			coffre.add(jsonObject);
		}
		System.out.println(coffre);
		ArrayList<ArrayList<Boolean>> dejaFait = new ArrayList<ArrayList<Boolean>>();

	}
	
	private void resolvable(ArrayList<Boolean> etatActuel,ArrayList<ArrayList<Boolean>> dejaFait) {
		
	}
	
}
