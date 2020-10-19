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
	ArrayList<Thread> globalthreads;
 	public RandomCoffre() {
 		coffre = new JSONArray();
		nbMinLock = 3;
		nbMaxLock = 8;
		nbMinInteractionButton = 2;
		nbMaxInteractionButton = 5;
		globalthreads = new ArrayList<Thread>();
	}
	
	public JSONArray getCoffre() {
		generate();
		return this.coffre;
	}
	
	public ArrayList<ArrayList<Integer>> getSolutions() {
		return solutions;
	}

	@SuppressWarnings("unchecked")
	public void generate() {
		System.out.println("Generate");
		coffre = new JSONArray();
		int nbLock = (int) Math.round(( nbMinLock + Math.random() * (nbMaxLock - nbMinLock)));
		//System.out.println("NbLock :" + nbLock);
		final ArrayList<Boolean> etatActuel = new ArrayList<Boolean>();
		for ( int i = 1 ; i <= nbLock ; i++ ) {
			JSONObject jsonObject = new JSONObject();
			JSONArray interactionList = new JSONArray();
			int nbInteractionButton = (int) Math.round(( nbMinInteractionButton + Math.random() * (nbMaxInteractionButton - nbMinInteractionButton)));
			interactionList.add(i);
			while(interactionList.size()<nbInteractionButton) {
				int value = (int)(1 + Math.round((Math.random()*(nbLock-1))));
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
			coffre.add(jsonObject);
			
		}
		if(!resoudre(coffre)) {
			generate();
		} else {
			System.out.println("Solution : " + solutions.size() + " " + solutions);	
		}
	}
	
	private void resolvable(final ArrayList<Boolean> etatActuel,ArrayList<Integer> path ,final ArrayList<ArrayList<Boolean>> dejaFait) {
		//System.out.println(etatActuel + " " + path + " DejaFait : " + dejaFait);	
		boolean unlocked = true;
		for ( int i = 0 ; i < etatActuel.size() ; i++) {
			if(etatActuel.get(i)) {
				unlocked = false;
			}
		}
		if(unlocked) {
			if(solutions.contains(path)) System.out.println("DOUBLON !");
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
						globalthreads.add(new Thread(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								//System.out.println("PATH " + newpath);
								System.out.println("Working");
								resolvable(newState, newpath, dejaFait);
							}
						}));	
					}	
				}
			}
		}
		
	}
	
	private void runThreads() {
		boolean end = true;
		ArrayList<Thread> threads = globalthreads;
		globalthreads = new ArrayList<Thread>();
		while(threads.size()!=0) {
			for ( int i = threads.size()-1 ; i>=0 ; i--) {
				Thread thread = threads.get(i);
				thread.start();
				try {
					thread.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				threads.remove(i);
			}
			end = false;
		}
		if(!end) {
			runThreads();
		}		
	}
	
	public boolean resoudre(JSONArray chest) {
		//System.out.println(chest);
		coffre = chest;
		solutions = new ArrayList<ArrayList<Integer>>();
		final ArrayList<ArrayList<Boolean>> dejaFait = new ArrayList<ArrayList<Boolean>>();
		final ArrayList<Integer> path = new ArrayList<Integer>();
		final ArrayList<Boolean> etatActuel = new ArrayList<Boolean>();
		for ( int i = 0 ; i < chest.size() ; i++) {
			JSONObject obj = (JSONObject) chest.get(i);
			etatActuel.add((boolean)obj.get("locked"));
		}
		resolvable(etatActuel,path,dejaFait);
		runThreads();
		/// Difficulte
		/*for (int i = 0 ; i <solutions.size() ; i++) {
			System.out.println(solutions.get(i));
			if(solutions.get(i).size() < 6) {
				return false;
			}
		}*/
		//System.out.println("Soluce " + solutions);
		if(solutions.size()<1) {
			return false;
		}
		return true;
	}
	
}
