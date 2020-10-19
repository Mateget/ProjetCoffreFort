package utils;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.SwingUtilities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class RandomCoffre {

	private JSONArray coffre;
	private int nbMinLock;
	private int nbMaxLock;
	private int nbMinInteractionButton;
	private int nbMaxInteractionButton;
	private ArrayList<ArrayList<Integer>> solutions;
	ArrayList<Runnable> globalRunnables;
	ArrayList<Runnable> testedRunnables;
	int difficulty;
 	public RandomCoffre() {
 		globalRunnables = new ArrayList<Runnable>();
		testedRunnables = new ArrayList<Runnable>();
	}
 	
 	private void initRandom() {
 		nbMinLock = 4;
		nbMaxLock = 7;
		nbMinInteractionButton = 2;
		nbMaxInteractionButton = 7;
		globalRunnables = new ArrayList<Runnable>();
		testedRunnables = new ArrayList<Runnable>();
 		coffre = generate();
		while(!resoudre(coffre,true)) {
			coffre = generate();
		};
 	}
 		
	public JSONArray getCoffre(int difficulty) {
		this.difficulty = difficulty;
		initRandom();	
		return this.coffre;
	}
		
	public ArrayList<ArrayList<Integer>> getSolutions() {
		return solutions;
	}

	@SuppressWarnings("unchecked")
	public JSONArray generate() {
		//System.out.println("Generate");
		JSONArray chest = new JSONArray();
		int nbLock = (int) Math.round(( nbMinLock + Math.random() * (nbMaxLock - nbMinLock)));
		//System.out.println("NbLock :" + nbLock);
		final ArrayList<Boolean> etatActuel = new ArrayList<Boolean>();
		for ( int i = 1 ; i <= nbLock ; i++ ) {
			JSONObject jsonObject = new JSONObject();
			JSONArray interactionList = new JSONArray();
			int nbInteractionButton = (int) Math.round(( nbMinInteractionButton + Math.random() * (nbMaxInteractionButton - nbMinInteractionButton)));
			if ( nbInteractionButton > nbLock ) {
				nbInteractionButton = nbLock;
			}
			interactionList.add(i);
			while( interactionList.size() < nbInteractionButton) {
				int value = (int)(1 + Math.round((Math.random()*(nbLock-1))));
				//System.out.println("Value tested " + value + " actual list " + interactionList );
				//System.out.println(interactionList + "contain : " + value + "? " + interactionList.contains(value));
				if(!interactionList.contains(value)){
					//System.out.println(value + "added to : " + interactionList );
					interactionList.add(value);
				}
			}
			int test = (int)Math.round(Math.random());
			//System.out.println(test);
			if ( test == 1) {
				jsonObject.put("locked", true);
				etatActuel.add(true);
			}
			else {
				jsonObject.put("locked", false);
				etatActuel.add(false);
			}
			jsonObject.put("linked", interactionList);
			
			chest.add(jsonObject);
			
		}
		return chest;
		
		
	}
	
	private void resolvable(final ArrayList<Boolean> etatActuel,ArrayList<Integer> path ,final ArrayList<ArrayList<Boolean>> dejaFait, final boolean randomChest) {
		//System.out.println(etatActuel + " " + path + " DejaFait : " + dejaFait);	
		boolean unlocked = true;
		for ( int i = 0 ; i < etatActuel.size() ; i++) {
			if(etatActuel.get(i)) {
				unlocked = false;
			}
		}
		if(unlocked) {
			if(randomChest) {
				if(path.size() < difficulty) {
				//System.out.println("Path to short");
					initRandom();
				} else if(solutions.size() > 50 ) {
					//System.out.println("Too much solutions");
					initRandom();
				}
			}
			
			solutions.add(path);			
		} else {
			for ( int i = 0 ; i < etatActuel.size() ; i++) {
				if(etatActuel.get(i)) {
					final ArrayList<Boolean> newState = new ArrayList<Boolean>();
					for ( int j = 0 ; j < etatActuel.size() ; j++) {
						JSONObject obj = (JSONObject) coffre.get(i);
						JSONArray linked = (JSONArray) obj.get("linked");
						ArrayList<Integer> linkedInt = new ArrayList<Integer>();
						
						for ( int k = 0 ; k < linked.size() ; k++ ) {
							linkedInt.add(Integer.parseInt(linked.get(k).toString()));
						}
						if( linkedInt.contains(j+1)) {
							newState.add(!etatActuel.get(j));
						} else {
							newState.add(etatActuel.get(j));
						}
					}
					final ArrayList<Integer> newpath = new ArrayList<Integer>(path);
					
					newpath.add(i+1);
					for ( int l = 0 ; l < dejaFait.size() ; l++) {
						if (dejaFait.get(l).equals(newState)) {
							//System.out.println("Already done at : " + l);
							//System.out.println("Abandonned " + newpath);
						}							
					}
					
					if(dejaFait.contains(newState)) {
						
						//System.out.println(newState + " est présent dans " + dejaFait);
					} else {
						dejaFait.add(etatActuel);
						globalRunnables.add(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								//System.out.println("PATH " + newpath);
								//System.out.println("Working");
								resolvable(newState, newpath, dejaFait,randomChest);
							}
						});	
					}	
				}
			}
		}
		
	}
	
	private void runRunnables() {
		//System.out.println("Working " + childCount);
		boolean end = true;
		testedRunnables = globalRunnables;
		globalRunnables = new ArrayList<Runnable>();
		while(testedRunnables.size()!=0) {
			//System.out.println("Hey :" + testedRunnables.size());
			for ( int i = testedRunnables.size()-1 ; i>=0 ; i--) {
				if(testedRunnables.size()!=0) testedRunnables.get(i).run();
				if(testedRunnables.size()!=0) testedRunnables.remove(i);
			}
			end = false;
		}
		if(!end) {
			runRunnables();
		}		
	}
	
	public boolean resoudre(JSONArray chest, boolean randomChest) {
		//System.out.println("Resoudre : " + chest);
		coffre = chest;
		solutions = new ArrayList<ArrayList<Integer>>();
		final ArrayList<ArrayList<Boolean>> dejaFait = new ArrayList<ArrayList<Boolean>>();
		final ArrayList<Integer> path = new ArrayList<Integer>();
		final ArrayList<Boolean> etatActuel = new ArrayList<Boolean>();
		for ( int i = 0 ; i < chest.size() ; i++) {
			JSONObject obj = (JSONObject) chest.get(i);
			etatActuel.add((boolean)obj.get("locked"));
		}
		resolvable(etatActuel,path,dejaFait,randomChest);
		runRunnables();
		/// Difficulte
		/*for (int i = 0 ; i <solutions.size() ; i++) {
			System.out.println(solutions.get(i));
			if(solutions.get(i).size() < 8) {
				System.out.println("Too Easy");
				return false;
			}
		}*/
		//System.out.println("Soluce " + solutions);
		if(solutions.size()<=1) {
			return false;
		}
		return true;
	}
	
}
