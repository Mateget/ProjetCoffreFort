package display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;


public class Button extends JButton {
	
	private static final long serialVersionUID = 1L;
	
	private boolean pressed;
	private ImageIcon buttonUp = new ImageIcon("./libs/buttonUp.png");
	private ImageIcon buttonDown = new ImageIcon("./libs/buttonDown.png");
	public Button(String text) {
		this.setBackground(new Color(139,139,139));
        setFocusPainted(false);
        //setMargin(new Insets(0, 0, 0, 0));
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(true);
		//setText(text);
		this.pressed = true;
		toggle();
	}
		
	public void toggle() {
		if(pressed) {
			unpress();
		}
		else {
			press();
		}
	}
	
	public boolean isPressed () {
		return this.pressed;
	}
	 
	public void press() {
		this.pressed = true;
		this.setIcon(buttonDown);
		this.setEnabled(true);
	}
	public void unpress() {
		this.pressed = false;
		this.setIcon(buttonUp);
		this.setDisabledIcon(buttonUp);
		this.setEnabled(false);
	}
	
}
