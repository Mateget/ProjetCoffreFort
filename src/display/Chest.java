package display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
	private ImageIcon closeChest = new ImageIcon("./libs/CoffreFerme.png");
	private ImageIcon openChest = new ImageIcon("./libs/CoffreOuvert.png");
	
	public Chest(Observer o,ChestData chestData) {
		ArrayList<Path> solutions = new Solver().resolve(chestData,false);
		//System.out.println(solutions.size()  + " " + solutions);
		path = solutions.get(new Random().nextInt(solutions.size()));
		this.chestData = chestData;
		listButton = new ArrayList<Button>();
		listLock = new ArrayList<Lock>();
		lockNumber = chestData.size();
		gameFinished = false;
		game = new JPanel();
		game.setOpaque(false);
		//game.setBackground(new Color(139,139,139));
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
	
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(!gameFinished) {
        	g.drawImage(closeChest.getImage(),0,0,this.getWidth(),this.getHeight(),null); 
        } else {
        	g.drawImage(openChest.getImage(),0,0,this.getWidth(),this.getHeight(),null); 
        }
         
    }
	
	
	private void initGame() {
		game.setLayout(new GridBagLayout());
		Dimension dimGame = new Dimension(200, 300);
		game.setSize(dimGame);
		game.setMinimumSize(dimGame);
		game.setPreferredSize(dimGame);
		// Create Locks and Buttons
		for ( int i = 0 ; i < lockNumber ; i++) {
			ButtonData buttonData = new ButtonData((JSONObject) chestData.get(i));
			Lock lock = new Lock("Verrou"+ (i+1));
			if(!buttonData.isLocked()) lock.toggle();
			listLock.add(lock);
			Button button = new Button("Boutton"+(i+1));
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
	
	private void initGameInfo() {
		gameInfo = new JPanel();
		gameInfo.setOpaque(false);
		//gameInfo.setBackground(new Color(139,139,139));
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
		Dimension dimTime = new Dimension(60,38);
		time.setMinimumSize(dimTime);
		time.setSize(dimTime);
		time.setPreferredSize(dimTime);
		//time.setHorizontalAlignment(SwingConstants.RIGHT);
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
		JButton undo = new JButton("Undo");
		undo.setFont(new Font("Comic Sans MS", Font.PLAIN,30));
		undo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadHistory(currentState);
				System.out.println(time.getSize());
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
		final JButton help = new JButton("Solution");
		help.setFont(new Font("Comic Sans MS", Font.PLAIN,30));
		help.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gameInfo.remove(help);
				
				gameInfo.add(labelPath);
				repaint();
				revalidate();
				
			}
		});
		gameInfo.add(help);
	}
	
	private void addHistory() {
		currentState++;
		Vector<Boolean> list = new Vector<Boolean>(); 
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
		Thread thread = new Thread(){
		    public void run(){
		    	try { // récupérer le son
		    		Player playMP3 = new Player(new FileInputStream("./libs/buttonSong.mp3"));
					playMP3.play();
				}
				catch (JavaLayerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		  };
		  thread.start();
		clicCounter.setText(Integer.toString(Integer.parseInt(clicCounter.getText())+1));
	}
	
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
		playWinSong();
		gameFinished = true; //Stop the timer
		observer.update("Finish");
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.repaint();
		this.revalidate();
	}
	
	private void playWinSong() {
		Thread thread = new Thread(){
		    public void run(){
		    	try { // récupérer le son
		    		Player playMP3 = new Player(new FileInputStream("./libs/victoryCrew.mp3"));
					playMP3.play();
				}
				catch (JavaLayerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		  };
		  thread.start();
	}
}