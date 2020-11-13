package display;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import org.json.simple.JSONObject;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import rename.ChestData;
import rename.GameState;
import rename.Path;
import util.ButtonData;
import util.Observer;
import util.Solver;

/*
 * JPanel used by Interface class
 */

public class Chest extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private ChestData chestData;
	private ArrayList<Button> listButton;
	private ArrayList<Lock> listLock;
	private int lockNumber;
	private boolean gameFinished;
	private JPanel game;
	private JPanel gameInfo;
	private JLabel clicCounter;
	private JLabel time;
	private int currentState;
	private ArrayList<GameState> history;
	private Observer observer;
	private Path path;
	private ImageIcon closeChest = new ImageIcon("./libs/chestClose.png");
	private ImageIcon openChest = new ImageIcon("./libs/chestOpen.png");
	
	/* 
	 * Constructor
	 * @Param : Observer, ChestData
	 * Observer used to notify Interface that a game is finished
	 * ChestData contain all data to create a chest  
	 */
	public Chest(Observer o,ChestData chestData) {
		ArrayList<Path> solutions = new Solver().resolve(chestData,false);
		path = solutions.get(new Random().nextInt(solutions.size()));
		this.chestData = chestData;
		listButton = new ArrayList<Button>();
		listLock = new ArrayList<Lock>();
		lockNumber = chestData.size();
		gameFinished = false;
		game = new JPanel();
		game.setOpaque(false);
		currentState = -1;
		history = new ArrayList<GameState>();
		observer = o;
		setLayout(new BorderLayout());
		initGame();
		add(game,BorderLayout.SOUTH);
		initGameInfo();
		add(gameInfo,BorderLayout.NORTH);
		timer();
	}
	
	/*
	 * Paint background with a chest picture, it change when the game is won
	 */
	
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(!gameFinished) {
        	g.drawImage(closeChest.getImage(),0,0,this.getWidth(),this.getHeight(),null); 
        } else {
        	g.drawImage(openChest.getImage(),0,0,this.getWidth(),this.getHeight(),null); 
        }
         
    }
	
	/*
	 * Initialize a game
	 */
	private void initGame() {
		game.setLayout(new GridBagLayout());
		Dimension dimGame = new Dimension(200, 300);
		game.setSize(dimGame);
		game.setMinimumSize(dimGame);
		game.setPreferredSize(dimGame);
		// Create Locks and Buttons
		for ( int i = 0 ; i < lockNumber ; i++) {
			ButtonData buttonData = new ButtonData((JSONObject) chestData.get(i));
			Lock lock = new Lock();
			if(!buttonData.isLocked()) lock.toggle();
			listLock.add(lock);
			Button button = new Button();
			if(buttonData.isLocked()) button.toggle();
			listButton.add(button);
		}
		// Adding ActionListener
		for ( int i = 0 ; i < lockNumber ; i++) {
			final ButtonData buttonData = new ButtonData((JSONObject) chestData.get(i));
			listButton.get(i).addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					addHistory();
					for ( int j = 0 ; j < buttonData.getLinkedLock().size() ; j++) {
						final int index = j;
						listButton.get(buttonData.getLinkedLock().get(index)-1).toggle();
						listLock.get(buttonData.getLinkedLock().get(index)-1).toggle();
					}
					clicHandle();
					isFinished();
				}
			});			
		}
		GridBagConstraints c = new GridBagConstraints();
		// Adding Locks to JPanel
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 0;
		for ( int i = 0 ; i < lockNumber ; i++ ) {
			c.gridx = i;
			game.add(listLock.get(i),c);
		}
		c.gridy = 1;
		// Adding Buttons to JPanel
		for ( int i = 0 ; i < lockNumber ; i++ ) {
			c.gridx = i;
			game.add(listButton.get(i),c);
		}		
    }
	
	/*
	 * Initiate a bar with game info like Clic number, timer, undo button, etc ...
	 */
	
	private void initGameInfo() {
		gameInfo = new JPanel();
		gameInfo.setOpaque(false);
		JLabel labelClic = new JLabel("Nombre de Cliques : ");
		labelClic.setForeground(Color.WHITE);
		labelClic.setFont(new Font("Comic Sans MS", Font.PLAIN,30));
		clicCounter = new JLabel("0");
		clicCounter.setFont(new Font("Comic Sans MS", Font.PLAIN,30));
		clicCounter.setForeground(Color.WHITE);
		Dimension dimClicCounter = new Dimension(40,38);
		clicCounter.setMinimumSize(dimClicCounter);
		clicCounter.setSize(dimClicCounter);
		clicCounter.setPreferredSize(dimClicCounter);
		JLabel labelTime = new JLabel("Temps : ");
		labelTime.setFont(new Font("Comic Sans MS", Font.PLAIN,30));
		labelTime.setForeground(Color.WHITE);
		time = new JLabel("");
		time.setFont(new Font("Comic Sans MS", Font.PLAIN,30));
		time.setForeground(Color.WHITE);
		Dimension dimTime = new Dimension(80,38);
		time.setMinimumSize(dimTime);
		time.setSize(dimTime);
		time.setPreferredSize(dimTime);
		JLabel labelTimeUnit = new JLabel("sec");
		labelTimeUnit.setFont(new Font("Comic Sans MS", Font.PLAIN,30));
		labelTimeUnit.setForeground(Color.WHITE);
		labelTimeUnit.setHorizontalAlignment(SwingConstants.LEFT);
		gameInfo.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		gameInfo.add(labelClic);
		gameInfo.add(clicCounter);
		gameInfo.add(labelTime);
		gameInfo.add(time);
		gameInfo.add(labelTimeUnit);
		ImageIcon undoImage = new ImageIcon("./libs/buttonUndo.png");
		Image resizedUndoImage = undoImage.getImage().getScaledInstance(38, 38,Image.SCALE_SMOOTH);
		undoImage = new ImageIcon(resizedUndoImage);
		JButton undo = new JButton(undoImage);
		undo.setFocusPainted(false);
		undo.setContentAreaFilled(false);
		undo.setBorderPainted(false);
		undo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!gameFinished) loadHistory(currentState);
			}
		});
		gameInfo.add(undo);
		String pathString = new String();
		for ( int i = 0 ; i  < path.size() ; i++) {
			pathString += path.get(i).toString() + " ";
		}
		final JLabel labelPath = new JLabel(pathString);
		labelPath.setFont(new Font("Comic Sans MS", Font.PLAIN,30));
		labelPath.setForeground(Color.WHITE);
		ImageIcon solutionImage = new ImageIcon("./libs/buttonSolution.png");
		Image resizedSolutionImage = solutionImage.getImage().getScaledInstance(127, 38,Image.SCALE_SMOOTH);
		undoImage = new ImageIcon(resizedSolutionImage);
		final JButton help = new JButton(undoImage);
		help.setFocusPainted(false);
		help.setContentAreaFilled(false);
		help.setBorderPainted(false);
		help.setToolTipText("Afficher une solution. Tricheur !");
		help.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!gameFinished) {
					gameInfo.remove(help);
					gameInfo.add(labelPath);
					repaint();
					revalidate();
				}
			}
		});
		gameInfo.add(help);
	}
	
	/*
	 * Save actual game state
	 */
	
	private void addHistory() {
		currentState++;
		Vector<Boolean> list = new Vector<Boolean>(); 
		for ( int i = 0 ; i < listLock.size() ; i ++) {
			list.add(listLock.get(i).isLocked());
		}
		history.add(new GameState(list));
	}
	
	/*
	 * Load game state
	 * @Param : int
	 * int index of game state to load
	 */
	
	private void loadHistory(int saveValue) {
		if( currentState >= 0 ) {
			clicSound();
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
	
	/*
	 * Handle actions when a button is used, play sound and change clic counter
	 */
	
	private void clicHandle() {
		clicSound();
		clicCounter.setText(Integer.toString(Integer.parseInt(clicCounter.getText())+1));
	}
	
	/*
	 * Manage timer
	 */
	private void timer() {
		new Thread(new Runnable() {
		    public void run() {
		        long start = System.currentTimeMillis();
		        while (!gameFinished) {
		            long startTime = System.currentTimeMillis() - start;
		            final int seconds = (int) (startTime / 1000);
		            final int mili = (int) (startTime / 100) % 10;
		            SwingUtilities.invokeLater(new Runnable() {
		                 public void run() {
		                	time.setText( seconds + "." + mili );
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
	
	/*
	 * Check if the game is finished
	 * 100 ms delay was added to make sure all lock as were correctly switched before checking
	 */
	
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
					e.printStackTrace();
				}
	        }
	    };
	    Thread thread = new Thread(myRunnable);
		thread.start();
	}
	
	/*
	 * Executed when a game is won
	 */
	
	private void finish() {
		playWinSong();
		gameFinished = true; //Stop the timer
		observer.update("Finish");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.repaint();
		this.revalidate();
	}
	
	/*
	 * Play win song
	 */
	
	private void playWinSong() {
		Thread thread = new Thread(){
		    public void run(){
		    	try {
		    		Player playMP3 = new Player(new FileInputStream("./libs/victoryCrew.mp3"));
					playMP3.play();
				}
				catch (JavaLayerException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
		    }
		  };
		  thread.start();
	}
	
	/*
	 * Play button switch song
	 */
	
	private void clicSound() {
		Thread thread = new Thread(){
		    public void run(){
		    	try {
		    		Player playMP3 = new Player(new FileInputStream("./libs/buttonSong.mp3"));
					playMP3.play();
				}
				catch (JavaLayerException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
		    }
		  };
		  thread.start();
	}
}