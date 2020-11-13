package display;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/*
 * JLabel as a lock with a picture
 * Light green for unlocked
 * Dark green for locked 
 */

public class Lock extends JLabel {
	
	private static final long serialVersionUID = 1L;
	private ImageIcon lockOpen = new ImageIcon("./libs/lockOpen.png");
	private ImageIcon lockClose = new ImageIcon("./libs/lockClose.png");
	private boolean locked;
	
	/*
	 * Constructor
	 */
	
	public Lock() {
		this.setOpaque(true);
		this.setBackground(new Color(139,139,139));
		setHorizontalAlignment(this.getWidth()/2);
		this.locked = false;
		toggle();
	}
	
	/*
	 * Change actual state
	 */
	
	public void toggle() {	
		if(locked) {
			unlock();
		}
		else {
			lock();
		}
	}
	
	/*
	 * Return state
	 * Used to check if a game is finished
	 */
	
	public boolean isLocked() {
		return this.locked;
	}
	
	/*
	 * Lock the lock
	 */
	
	public void lock() {
		this.setIcon(lockClose);
		this.locked = true;
	}
	
	/*
	 * Unlock the lock 
	 */
	
	public void unlock() {
		this.setIcon(lockOpen);
		this.locked = false;
	}

}
