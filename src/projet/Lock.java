package projet;

import java.awt.Color;

import javax.swing.JLabel;

public class Lock extends JLabel {
	private static final long serialVersionUID = 1L;
	private boolean locked;
	public Lock(String text) {
		setText(text);
		setHorizontalAlignment(this.getWidth()/2);
		this.locked = false;
		toggle();
	}
	
	public void toggle() {
		if(locked) {
			unlock();
		}
		else {
			lock();
		}
	}
	
	public boolean isLocked() {
		return this.locked;
	}
	
	public void lock() {
		setForeground(Color.RED);
		this.locked = true;
	}
	public void unlock() {
		setForeground(Color.GREEN);
		this.locked = false;
	}

}
