package display;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/*
 * JButton displayed with an image
 */
public class Button extends JButton {
	
	private static final long serialVersionUID = 1L;
	
	private boolean pressed;
	private ImageIcon buttonUp = new ImageIcon("./libs/buttonUp.png");
	private ImageIcon buttonDown = new ImageIcon("./libs/buttonDown.png");
	
	/*
	 * Constructor
	 * Basic Java Button appearance is removed
	 */
	public Button() {
		this.setBackground(new Color(139,139,139));
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(true);
		this.pressed = true;	
		// Image for clickable button
		this.setIcon(buttonDown);
		// Image for unclickable(disable) button
		this.setDisabledIcon(buttonUp);
		toggle();
	}
	
	/*
	 * Change actual state
	 */
		
	public void toggle() {
		if(pressed) {
			unpress();
		}
		else {
			press();
		}
	}	
	
	/*
	 * Press button
	 */
	 
	public void press() {
		this.pressed = true;
		this.setEnabled(true);
	}
	
	/*
	 * Unpress button
	 */
	
	public void unpress() {
		this.pressed = false;
		this.setEnabled(false);
	}
	
}
