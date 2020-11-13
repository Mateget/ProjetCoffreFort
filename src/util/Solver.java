package util;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import rename.ChestData;
import rename.GameState;
import rename.Path;

/*
 * Chest solver
 */

public class Solver {
	private ArrayList<Path> solutions;
	private ArrayList<Runnable> nextRunnables;
	private ArrayList<Runnable> actualRunnables;
	private int minPathSize;
	private ChestData chest;
	@SuppressWarnings("unused")
	private int counter = 0;
	
	/*
	 * Constructor, initialize variables
	 */
	
	public Solver() {
		minPathSize = 6; // Minimum actions require to open chest
		nextRunnables = new ArrayList<Runnable>();
		actualRunnables = new ArrayList<Runnable>();
		solutions = new ArrayList<Path>();
		
	}
		
	/*
	 * Generate all path to resolve a chest recursively 
	 * @Param : GameState, Path, ArrayList<GameSatate>, boolean
	 * GameState current game state
	 * path actions already done
	 * ArrayList<GameState> all state already reached
	 * boolean specify if the chest is a random chest 
	 */ 
	
	private void resolvable(final GameState etatActuel, Path path, final ArrayList<GameState> dejaFait, final boolean isRandom) {
		boolean unlocked = true;
		for ( int i = 0 ; i < etatActuel.size() ; i++) {
			if(etatActuel.get(i)) {
				unlocked = false;
			}
		}
		if(unlocked) {
			// If path size is too small or number of path too high clear all Runnables
			if( isRandom && ( path.size() < minPathSize || solutions.size() > 50 )) {
				nextRunnables = new ArrayList<Runnable>();
				actualRunnables = new ArrayList<Runnable>();
				solutions = new ArrayList<Path>();
				return;
			} else {
				solutions.add(path);
			}		
		} else {
			for ( int i = 0 ; i < etatActuel.size() ; i++) {
				if(etatActuel.get(i)) {
					ButtonData buttonData = new ButtonData((JSONObject) chest.get(i));
					ArrayList<Integer> linkedInt = buttonData.getLinkedLock();
					final GameState newState = new GameState();
					for ( int j = 0 ; j < etatActuel.size() ; j++) {
						if( linkedInt.contains(j+1)) {
							newState.add(!etatActuel.get(j));
						} else {
							newState.add(etatActuel.get(j));
						}
					}
					final Path newpath = new Path(path);
					newpath.add(i+1);
					if(!dejaFait.contains(newState)) {
						dejaFait.add(etatActuel);
						// Because it need to browse path simultaneously I use multiple Runnables
						nextRunnables.add(new Runnable() {
							@Override
							public void run() {
								resolvable(newState, newpath , dejaFait , isRandom);
							}
						});	
					}	
				}
			}
		}
		
	}
	
	/*
	 * Run Runnables, it create a copy of Runnables, clear the list a run all Runnables copied
	 */
	
	private void runRunnables() {
		counter++;
		boolean end = true;
		actualRunnables = nextRunnables;
		nextRunnables = new ArrayList<Runnable>();
		while(actualRunnables.size()!=0) {
			for ( int i = actualRunnables.size()-1 ; i>=0 ; i--) {
				if(actualRunnables.size()!=0) actualRunnables.get(i).run();
				if(actualRunnables.size()!=0) actualRunnables.remove(i);
			}
			end = false;
		}
		// When all runnables are done, it run next runnables
		if(!end) {
			runRunnables();
		}		
	}
	
	/*
	 * Return a list of solutions
	 * @Param : ChestData, boolean
	 * ChestData the chest it need to find solutions
	 * boolean specify if the chest is a random chest
	 */
	
	public ArrayList<Path> resolve(ChestData chest, boolean isRandom) {
		this.chest = chest;
		final Path path = new Path();
		ArrayList<GameState> dejaFait = new ArrayList<GameState>();
		final GameState etatActuel = new GameState();
		for ( int i = 0 ; i < chest.size() ; i++) {
			JSONObject obj = (JSONObject) chest.get(i);
			etatActuel.add((boolean)obj.get("locked"));
		}
		resolvable(etatActuel, path , dejaFait , isRandom); // First occurrence of this function generate first Runnables
		runRunnables();
		return solutions;
	}
}
