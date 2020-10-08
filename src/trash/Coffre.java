package trash;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

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
}