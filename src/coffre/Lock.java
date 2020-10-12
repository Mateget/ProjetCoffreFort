package coffre;

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
			setForeground(Color.GREEN);
			this.locked = false;
		}
		else {
			setForeground(Color.RED);
			this.locked = true;
		}
	}
	
	public boolean isLocked() {
		return this.locked;
	}

}