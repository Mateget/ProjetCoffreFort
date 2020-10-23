package dessin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Test extends JFrame{
	
	public Test() {
		
		JLabel image = new JLabel();
		ImageIcon coffrevide = new ImageIcon("./libs/CoffreVide.png");
		ImageIcon locked = new ImageIcon("./libs/locked.png");
		ImageIcon unlocked = new ImageIcon("./libs/unlocked.png");
		image.setIcon(coffrevide);
		
		add(image);
		pack();
		Graphics g = image.getGraphics();
		g.setColor(Color.black);
		g.drawImage(locked.getImage(),50,50,null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//setSize(1000,400);	
		
		
		JLabel verrou1 = new JLabel();
		verrou1.setIcon(locked);
	}
	

}
