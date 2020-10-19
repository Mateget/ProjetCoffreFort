package projet;

import utils.ButtonData;
import utils.GameState;
import utils.Observer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Coffre extends JPanel{
	private JSONArray objJSONArray;
	private ArrayList<Button> listButton;
	private ArrayList<Lock> listLock;
	private int lockNumber;
	private boolean gameFinished;
	private JPanel game;
	private JPanel gameInfo;
	private JLabel clicCounter;
	private JLabel temps;
	private int currentState;
	private ArrayList<GameState> history;
	private Observer observer;
	ArrayList<Integer> path;
	
	public Coffre(Observer o,JSONArray objJSONArray,ArrayList<Integer> path) {
		
		this.objJSONArray = objJSONArray;
		this.path = path;
		listButton = new ArrayList<Button>();
		listLock = new ArrayList<Lock>();
		lockNumber = objJSONArray.size();
		gameFinished = false;
		game = new JPanel();
		gameInfo = new JPanel();
		clicCounter = new JLabel("0");
		temps = new JLabel("");
		currentState = -1;
		history = new ArrayList<GameState>();
		observer = o;
		
		setLayout(new BorderLayout());
		initGame();
		add(game,BorderLayout.CENTER);
		initGameInfo();
		add(gameInfo,BorderLayout.NORTH);
		timer();
	}
	
	private void initGame() {
		game.setLayout(new GridLayout(2, lockNumber));
		// Create Locks and Buttons
		for ( int i = 0 ; i < lockNumber ; i++) {
			ButtonData buttonData = new ButtonData((JSONObject) objJSONArray.get(i));
			Lock lock = new Lock("Verrou"+ (i+1));
			if(!buttonData.isLocked()) lock.toggle();
			listLock.add(lock);
			Button button = new Button("Boutton"+(i+1));
			if(buttonData.isLocked()) button.toggle();
			listButton.add(button);
		}
		// Adding ActionListener
		for ( int i = 0 ; i < lockNumber ; i++) {
			final ButtonData buttonData = new ButtonData((JSONObject) objJSONArray.get(i));
			listButton.get(i).addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					addHistory();
					for ( int j = 0 ; j < buttonData.getLinkedLock().size() ; j++) {
						final int index = j;
						//System.out.println("Changing number "+ index);
						listButton.get(buttonData.getLinkedLock().get(index)-1).toggle();
						//System.out.println(listButton.get(buttonData.getLinkedLock().get(index)-1).getText());
						listLock.get(buttonData.getLinkedLock().get(index)-1).toggle();
						//System.out.println(listLock.get(buttonData.getLinkedLock().get(index)-1).getText());	
					}
					clicHandle();
					isFinished();
				}
			});			
		}
		// Adding Locks to JPanel
		for ( int i = 0 ; i < lockNumber ; i++ ) {
			game.add(listLock.get(i));
		}
		// Adding Buttons to JPanel
		for ( int i = 0 ; i < lockNumber ; i++ ) {
			game.add(listButton.get(i));
		}		
    }
	
	private void initGameInfo() {
		gameInfo.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		gameInfo.add(new JLabel("Nombre de Cliques : "));
		gameInfo.add(clicCounter);
		gameInfo.add(new JLabel("Temps : "));
		gameInfo.add(temps);
		JButton undo = new JButton("Undo");
		undo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadHistory(currentState);
				
			}
		});
		gameInfo.add(undo);
		String pathString = new String();
		for ( int i = 0 ; i  < path.size() ; i++) {
			pathString += path.get(i).toString() + " ";
		}
		final JLabel pathLabel = new JLabel(pathString);
		final JButton aide = new JButton("Solution");
		aide.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gameInfo.remove(aide);
				
				gameInfo.add(pathLabel);
				repaint();
				revalidate();
				
			}
		});
		gameInfo.add(aide);
	}
	
	private void addHistory() {
		currentState++;
		ArrayList<Boolean> list = new ArrayList<Boolean>(); 
		for ( int i = 0 ; i < listLock.size() ; i ++) {
			list.add(listLock.get(i).isLocked());
		}
		history.add(new GameState(list));
	}
	
	private void loadHistory(int saveValue) {
		if( currentState >= 0 ) {
			GameState state = history.get(currentState);
			history.remove(currentState);
			currentState--;
			for ( int i = 0 ; i < lockNumber ; i++) {
				if(state.get(i)) {
					listLock.get(i).lock();
					listButton.get(i).press();
				} else {
					listLock.get(i).unlock();
					listButton.get(i).unpress();
				}
					
			}
		}
	}	
	
	private void clicHandle() {
		clicCounter.setText(Integer.toString(Integer.parseInt(clicCounter.getText())+1));
	}
	
	private void timer() {
		new Thread(new Runnable() {
		    public void run() {
		        long start = System.currentTimeMillis();
		        while (!gameFinished) {
		            long time = System.currentTimeMillis() - start;
		            final int seconds = (int) (time / 1000);
		            final int mili = (int) (time / 100) % 10;
		            SwingUtilities.invokeLater(new Runnable() {
		                 public void run() {
		                	 temps.setText( "" + seconds +"."+ mili+ " sec");
		                 }
		            });
		            try {
		            	Thread.sleep(100); 
		            } catch(Exception e) {
		            	
		            }
		        }
			}
		}).start();
	}
	
	private void isFinished() {
		Runnable myRunnable = new Runnable(){
	        public void run(){
	        	try {
					Thread.sleep(100);
					Boolean finished = true;
					for ( int i = 0 ; i < listLock.size() ; i ++) {
						if(listLock.get(i).isLocked()) {
							finished = false;
						}
					}
					if (finished) {
						finish();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    };
	    Thread thread = new Thread(myRunnable);
		thread.start();
	}
	
	private void finish() {
		gameFinished = true; //Stop the timer
		observer.update("Finish");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		game.removeAll();
    	JLabel label = new JLabel("Coffre Ouvert !");
		label.setFont(new Font("Serif", Font.PLAIN, 50));
		label.setForeground(Color.GREEN);
		label.setHorizontalAlignment(label.getWidth()/2);
		game.setLayout(new BorderLayout());
		game.add(label,BorderLayout.CENTER); 
		game.repaint();
		game.revalidate();
	}
}