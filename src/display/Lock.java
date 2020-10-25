package display;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Lock extends JLabel {
	
	private static final long serialVersionUID = 1L;
	private ImageIcon lockOpen = new ImageIcon("./libs/lockOpen.png");
	private ImageIcon lockClose = new ImageIcon("./libs/lockClose.png");
	private boolean locked;
	public Lock(String text) {
		//setText(text);
		this.setOpaque(true);
		this.setBackground(new Color(139,139,139));
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
		this.setIcon(lockClose);
		setForeground(Color.RED);
		this.locked = true;
	}
	public void unlock() {
		this.setIcon(lockOpen);
		setForeground(Color.GREEN);
		this.locked = false;
	}

}
