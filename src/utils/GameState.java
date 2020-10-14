package utils;

import java.util.ArrayList;

public class GameState extends ArrayList<Boolean> {

	private static final long serialVersionUID = 1L;
	public GameState(ArrayList<Boolean> list) {
		for( int i = 0 ; i < list.size() ; i ++) {
			add(list.get(i));
		}
	}
}
