package dessin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

public class Test extends JFrame{
	
	public Test() {
		//this.setLocationRelativeTo(null);
		ImageIcon coffrevide = new ImageIcon("./libs/CoffreVide.png");
		ImageIcon lockOpen = new ImageIcon("./libs/lockOpen.png");
		ImageIcon lockClose = new ImageIcon("./libs/lockClose.png");
		ImageIcon buttonUp = new ImageIcon("./libs/buttonUp.png");
		ImageIcon buttonDown = new ImageIcon("./libs/buttonDown.png");
		ImageIcon unlocked = new ImageIcon("./libs/unlocked.png");
		JLabel image = new JLabel() {
			 @Override
	            protected void paintComponent(Graphics grphcs) {
	                super.paintComponent(grphcs);
	                levier(grphcs);
	            }
			 
			 	public void levier(Graphics g) {
			 		//g.drawImage(locked.getImage(),140,450,null);
			 	}
		};
		image.addMouseListener(new MouseAdapter() { 
	          public void mousePressed(MouseEvent me) { 
	              System.out.println(me.getX() + " " +  me.getY()); 
	            } 
	    });   
	    JLayeredPane pane = getLayeredPane();  
	    //creating buttons  
	    image.setIcon(coffrevide); 
	    image.setBounds(0,0,coffrevide.getIconWidth(), coffrevide.getIconHeight());  
	    JPanel middle = new JPanel();   
	    middle.setOpaque(false);


	    middle.setBounds(150, 430, 690, 400);  
	    middle.setLayout(new GridLayout(2,3));
	    JLabel lock1 = new JLabel(lockOpen);
	    middle.add(lock1);
	    JLabel lock2 = new JLabel(lockOpen);
	    middle.add(lock2);
	    JLabel lock3 = new JLabel(lockOpen);
	    middle.add(lock3);
	    /// Button
	    JLabel button1 = new JLabel(buttonUp);
	    button1.addMouseListener(new MouseAdapter() {
	    	public void mousePressed(MouseEvent me) {
	    		if(lock1.getIcon().equals(lockOpen)) {
	    			lock1.setIcon(lockClose);
	    			button1.setIcon(buttonDown);
	    		} else {
	    			lock1.setIcon(lockOpen);
	    			button1.setIcon(buttonUp);
	    		}
	    		
	    	}
		});
	    middle.add(button1);
	    JLabel button2 = new JLabel(buttonUp);
	    button2.addMouseListener(new MouseAdapter() {
	    	public void mousePressed(MouseEvent me) {
	    		if(lock2.getIcon().equals(lockOpen)) {
	    			lock2.setIcon(lockClose);
	    			button2.setIcon(buttonDown);
	    		} else {
	    			lock2.setIcon(lockOpen);
	    			button2.setIcon(buttonUp);
	    		}
	    		
	    	}
		});
	    middle.add(button2);
	    JLabel button3 = new JLabel(buttonUp);
	    button3.addMouseListener(new MouseAdapter() {
	    	public void mousePressed(MouseEvent me) {
	    		if(lock3.getIcon().equals(lockOpen)) {
	    			lock3.setIcon(lockClose);
	    			button3.setIcon(buttonDown);
	    		} else {
	    			lock3.setIcon(lockOpen);
	    			button3.setIcon(buttonUp);
	    		}
	    		
	    	}
		});
	    middle.add(button3);
	    
	    
	    
	    
	    
	    image.setIcon(coffrevide);
	    //adding buttons on pane  
	    pane.add(image, new Integer(1));  
	    pane.add(middle, new Integer(2)); 
	    
		
				//pack();		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(1200,1000);	

	}
	

}
