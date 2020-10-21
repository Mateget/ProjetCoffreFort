package util;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import rename.ChestData;
import rename.GameState;
import rename.Path;

public class Solver {
	private ArrayList<Path> solutions;
	private ArrayList<Runnable> nextRunnables;
	private ArrayList<Runnable> actualRunnables;
	private int minPathSize;
	private ChestData chest;
	@SuppressWarnings("unused")
	private int counter = 0;
	
	public Solver() {
		minPathSize = 6;
		nextRunnables = new ArrayList<Runnable>();
		actualRunnables = new ArrayList<Runnable>();
		solutions = new ArrayList<Path>();
		
	}
		
	private void resolvable(final GameState etatActuel, Path path, final ArrayList<GameState> dejaFait, final boolean isRandom) {
		boolean unlocked = true;
		for ( int i = 0 ; i < etatActuel.size() ; i++) {
			if(etatActuel.get(i)) {
				unlocked = false;
			}
		}
		if(unlocked) {
			if( isRandom && ( path.size() < minPathSize || solutions.size() > 50 )) {
				nextRunnables = new ArrayList<Runnable>();
				actualRunnables = new ArrayList<Runnable>();
				solutions = new ArrayList<Path>();
				return;
			}
			solutions.add(path);			
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
	
	private void runRunnables() {
		counter++;
		//System.out.println(counter);
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
		if(!end) {
			runRunnables();
		}		
	}
	
	public ArrayList<Path> resolve(ChestData chest, boolean isRandom) {
		this.chest = chest;
		//System.out.println(chest);
		final Path path = new Path();
		ArrayList<GameState> dejaFait = new ArrayList<GameState>();
		final GameState etatActuel = new GameState();
		for ( int i = 0 ; i < chest.size() ; i++) {
			JSONObject obj = (JSONObject) chest.get(i);
			etatActuel.add((boolean)obj.get("locked"));
		}
		resolvable(etatActuel, path , dejaFait , isRandom);
		runRunnables();
		return solutions;
	}
}
