package trash;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import coffrefort.Button;
import coffrefort.Lock;
import coffrefort.Observer;

class Coffre extends JPanel {
	private static final long serialVersionUID = 1L;
	private Color c;
	private Color lockColor = Color.BLACK;
	private int xpos,ypos;
	private int largeur,hauteur;
	private ArrayList<Lock_Button> list = new ArrayList<Lock_Button>();
	public Coffre(int xpos, int ypos, int largeur, int hauteur, Color c) {
		System.out.println(xpos);
		this.xpos = xpos;
		this.ypos = ypos;
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.c = c;
		
	}
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(this.c);
        g.fillRect(xpos, ypos, largeur, hauteur);
        displayLock(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 400);
    }
    private ArrayList<Observer> observers = new ArrayList<Observer>();
    public void displayLock(Graphics g) {
    	generateLock();
    	for ( int i = 0 ; i < list.size() ; i++) {
    		Lock_Button lock = list.get(i);
    		g.setColor(lockColor);
    		g.drawRect(lock.getXpos(), lock.getYpos(), lock.getWidth(), lock.getHeight());
    		if(lock.isLocked()) {
    			g.setColor(Color.RED);
    		}
    		else {
    			g.setColor(Color.GREEN);
    		}
    		drawCenteredCircle(g, lock.getXpos()+lock.getWidth()/2, lock.getYpos()+lock.getHeight()/2-50, 50);
    		if(lock.isPressed()) {
    			g.setColor(Color.RED);
    		}
    		else {
    			g.setColor(Color.GREEN);
    		}
    		drawCenteredCircle(g, lock.getXpos()+lock.getWidth()/2, lock.getYpos()+lock.getHeight()/2+50, 50);
    	}
    }
    
    public void generateLock() {
    	int lockNumber = 3;
    	int lockWidth =(int) (0.90*(this.largeur-100))/lockNumber;
    	int lockHeight = 200;
    	Lock_Button lock;
    	for ( int i = 1 ; i <= lockNumber ; i ++) {
    		lock = new Lock_Button(((this.largeur-100)*i/lockNumber),this.ypos+25,lockWidth,lockHeight);
    		if(i==2) {
    			lock.unlock();
    		}
        	list.add(lock);
    	}
    }
    
    public void drawCenteredCircle(Graphics g, int x, int y, int r) {
    	  x = x-(r/2);
    	  y = y-(r/2);
    	  g.fillOval(x,y,r,r);
    	}
    
public void generateLockBasic() {
    	
    	Lock lock1 = new Lock("Verrou 1",1);
    	lock1.lock();
    	ArrayList<Integer> lockLinked1 = new ArrayList<Integer>();
    	lockLinked1.add(1);
    	lockLinked1.add(2);
    	Button boutton1 = new Button("Boutton 1", lockLinked1,1);
    	boutton1.unpress();
    	lock1.setobserver(boutton1);
    	observers.add(lock1);
    	add(lock1);
    	
    	Lock lock2 = new Lock("Verrou 2",2);
    	lock2.unlock();
    	ArrayList<Integer> lockLinked2 = new ArrayList<Integer>();
    	lockLinked2.add(1);
    	lockLinked2.add(2);
    	lockLinked2.add(3);
    	Button boutton2 = new Button("Boutton 2", lockLinked2,2);
    	boutton2.press();
    	lock2.setobserver(boutton2);
    	observers.add(lock2);
    	add(lock2);
    	
    	
    	Lock lock3 = new Lock("Verrou 3",3);
    	lock3.lock();
    	ArrayList<Integer> lockLinked3 = new ArrayList<Integer>();
    	lockLinked3.add(2);
    	lockLinked3.add(3);
    	Button boutton3 = new Button("Boutton 3", lockLinked3,3);
    	boutton3.unpress();
    	lock3.setobserver(boutton3);
    	observers.add(lock3);
    	add(lock3);
    	
    	boutton1.setObservers(observers);
    	boutton2.setObservers(observers);
    	boutton3.setObservers(observers);
    	    	
    	add(boutton1);
    	add(boutton2);
    	add(boutton3);
    }
    public void generateLockHard() {
    	Lock lock1 = new Lock("Verrou 1",1);
    	lock1.unlock();
    	Lock lock2 = new Lock("Verrou 2",2);
    	lock2.unlock();
    	Lock lock3 = new Lock("Verrou 3",3);
    	lock3.unlock();
    	Lock lock4 = new Lock("Verrou 4",4);
    	lock4.lock();
    	Lock lock5 = new Lock("Verrou 5",5);
    	lock5.unlock();
    	Lock lock6 = new Lock("Verrou 6",6);
    	lock6.unlock();
    	observers.add(lock1);
    	observers.add(lock2);
    	observers.add(lock3);
    	observers.add(lock4);
    	observers.add(lock5);
    	observers.add(lock6);
    	ArrayList<Integer> lockLinked1 = new ArrayList<Integer>();
    	lockLinked1.add(1);
    	lockLinked1.add(2);
    	lockLinked1.add(5);
    	Button boutton1 = new Button("Boutton 1", lockLinked1, 1);
    	boutton1.press();
    	
    	ArrayList<Integer> lockLinked2 = new ArrayList<Integer>();
    	lockLinked2.add(2);
    	lockLinked2.add(4);
    	lockLinked2.add(5);
    	Button boutton2 = new Button("Boutton 2", lockLinked2, 2);
    	boutton2.press();
    	
    	ArrayList<Integer> lockLinked3 = new ArrayList<Integer>();
    	lockLinked3.add(1);
    	lockLinked3.add(3);
    	lockLinked3.add(6);
    	Button boutton3 = new Button("Boutton 3", lockLinked3, 3);
    	boutton3.press();
    	
    	ArrayList<Integer> lockLinked4 = new ArrayList<Integer>();
    	lockLinked4.add(2);
    	lockLinked4.add(4);
    	Button boutton4 = new Button("Boutton 4", lockLinked4, 4);
    	boutton4.unpress();
    	
    	ArrayList<Integer> lockLinked5 = new ArrayList<Integer>();
    	lockLinked5.add(1);
    	lockLinked5.add(2);
    	lockLinked5.add(3);
    	lockLinked5.add(5);
    	Button boutton5 = new Button("Boutton 5", lockLinked5, 5);
    	boutton5.press();
    	
    	ArrayList<Integer> lockLinked6 = new ArrayList<Integer>();
    	lockLinked6.add(1);
    	lockLinked6.add(3);
    	lockLinked6.add(4);
    	lockLinked6.add(5);
    	lockLinked6.add(6);
    	Button boutton6 = new Button("Boutton 6", lockLinked6, 6);
    	boutton6.press();
    	
    	lock1.setobserver(boutton1);
    	lock2.setobserver(boutton2);
    	lock3.setobserver(boutton3);
    	lock4.setobserver(boutton4);
    	lock5.setobserver(boutton5);
    	lock6.setobserver(boutton6);
    	boutton1.setObservers(observers);
    	boutton2.setObservers(observers);
    	boutton3.setObservers(observers);
    	boutton4.setObservers(observers);
    	boutton5.setObservers(observers);
    	boutton6.setObservers(observers);
    	add(lock1);
    	add(lock2);
    	add(lock3);
    	add(lock4);
    	add(lock5);
    	add(lock6);
    	add(boutton1);
    	add(boutton2);
    	add(boutton3);
    	add(boutton4);
    	add(boutton5);
    	add(boutton6);
    }
}