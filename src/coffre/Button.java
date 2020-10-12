package coffre;

import javax.swing.JButton;

public class Button extends JButton{
	private static final long serialVersionUID = 1L;
	private boolean pressed;
	
	public Button(String text) {
		setText(text);
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
	
	public boolean isPressed() {
		return this.pressed;
	}
	
	public void press() {
		this.pressed = true;
		this.setEnabled(true);
	}
	public void unpress() {
		this.pressed = false;
		this.setEnabled(false);
	}
	
}
