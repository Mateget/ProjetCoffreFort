package coffre;

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


public class ChestGenerator extends JPanel{
	private static final long serialVersionUID = 1L;
	private ArrayList<Button> listButton= new ArrayList<Button>();
	private ArrayList<Lock> listLock = new ArrayList<Lock>();
	private JSONArray objJSONArray;
	private int lockNumber;
	private boolean gameFinished = false;
	private JPanel game = new JPanel();
	private JPanel gameInfo = new JPanel();
	private JLabel clicCounter = new JLabel("0");
	private JLabel temps = new JLabel("00:00");
	private int currentState = -1;
	private ArrayList<GameState> history = new ArrayList<GameState>();
	private String chestName;
	private JButton save;
	public ChestGenerator(JSONArray objJSONArray,String chestName,boolean fromSave) {
		this.chestName = chestName;
		this.objJSONArray = objJSONArray;
		this.lockNumber = this.objJSONArray.size();
		game.setLayout(new GridLayout(2, lockNumber));
		gameInfo.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		gameInfo.add(new JLabel("Nombre de Cliques : "));
		gameInfo.add(clicCounter);
		gameInfo.add(new JLabel("Temps : "));
		gameInfo.add(temps);
		JButton undo = new JButton("Undo");
		undo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadSave(currentState);
				save();
				
			}
		});
		save = new JButton("Save");
				save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveState();
				
				save();
				
			}
		});
		gameInfo.add(undo);
		gameInfo.add(save);
		generateChest();
		timer();
		/*if(fromSave) {
			currentState = history.size()-1;
			loadSave(currentState);
		}*/
	}
	
	@SuppressWarnings("unchecked")
	private void save() {
		//currentState++;
		JSONObject objSave = new JSONObject();
		objSave.put(chestName, history);
		JSONReadFromFile saveFile = new JSONReadFromFile("./src/coffre/save.json");
		JSONObject objSaveFile = saveFile.getJsonObject();
		
		JSONArray savedChests = (JSONArray)objSaveFile.get("saves");
		if (savedChests.isEmpty()) savedChests.add(objSave);
		boolean present = false;
		for ( int i = 0 ; i < savedChests.size() ; i++) {
			JSONObject obj = (JSONObject)savedChests.get(i);
			System.out.println("IF " +obj +" containt: " + chestName);
			if(obj.containsKey(chestName)) {
				savedChests.remove(i);
				savedChests.add(objSave);
				present = true;
			}
		}
		if(!present) savedChests.add(objSave);
		objSaveFile.put("saves", savedChests);
		System.out.println("Actual history : " + history);
		System.out.println("Saved file :" + objSaveFile);
		new JSONWriteFromFile("./src/coffre/save.json", objSaveFile);
	}
	
	private void saveState() {
		ArrayList<Boolean> list = new ArrayList<Boolean>(); 
		for ( int i = 0 ; i < listLock.size() ; i ++) {
			System.out.println("Add "+ listLock.get(i).getText() + " " + listLock.get(i).isLocked());
			list.add(listLock.get(i).isLocked());
		}
		System.out.println(list);
		history.add(new GameState(list));
		System.out.println(history);
	}
	
	private void loadSave(int saveValue) {
		System.out.println("CurrentState : "+currentState);
		if( currentState >= 0 ) {
			
			GameState state = history.get(currentState);
			history.remove(currentState);
			currentState--;
			System.out.println(history);
			
			System.out.println("Load :" +state);
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
	
	private void generateChest() {
		
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
			ButtonData buttonData = new ButtonData((JSONObject) objJSONArray.get(i));
			listButton.get(i).addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					currentState++;
					saveState();
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
		setLayout(new BorderLayout());
		add(game,BorderLayout.CENTER);
		add(gameInfo,BorderLayout.NORTH);
		
    }
	
	private void clicHandle() {
		//System.out.println(Integer.toString(Integer.parseInt(clicCounter.getText())+1));
		clicCounter.setText(Integer.toString(Integer.parseInt(clicCounter.getText())+1));
	}
	
	private void timer() {
		new Thread(new Runnable()
				{
				    public void run()
				    {
				        long start = System.currentTimeMillis();
				        while (!gameFinished)
				        {

				            long time = System.currentTimeMillis() - start;
				            int seconds = (int) (time / 1000);
				            int mili = (int) (time / 100) % 10;
				            SwingUtilities.invokeLater(new Runnable() {
				                 public void run()
				                 {
				                	 temps.setText( "" + seconds +"."+ mili+ " sec");
				                 }
				            });
				            try { Thread.sleep(100); } catch(Exception e) {}
				        }
				    }
				}).start();
	}
	
	private void isFinished() {
		Runnable myRunnable = new Runnable(){
	        public void run(){
	        	try {
					Thread.sleep(100);
					gameWon();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
	        }
	    };
	    Thread thread = new Thread(myRunnable);
		thread.start();
	}
	
	private void gameWon() {
		Boolean lose = true;
		for ( int i = 0 ; i < listLock.size() ; i ++) {
			if(listLock.get(i).isLocked()) {
				lose = false;
			}
		}
		if (lose) {
			gameFinished = true;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("Game WON !");
			JSONReadFromFile saveFile = new JSONReadFromFile("./src/coffre/save.json");
			JSONObject objSaveFile = saveFile.getJsonObject();
			JSONArray completedChest = (JSONArray)objSaveFile.get("completed");
			boolean present = false;
			for ( int i = 0 ; i < completedChest.size() ; i++) {
				//System.out.println("IF : "+listOfCompletedChest.get(j).toString() + "==" + objChest.get("name"));
				if(completedChest.get(i).toString().equals(chestName)) {
					present = true;
				} 
			}
			if(!present) completedChest.add(chestName);
			
			objSaveFile.replace("completed", completedChest);
			new JSONWriteFromFile("./src/coffre/save.json", objSaveFile);
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

}
