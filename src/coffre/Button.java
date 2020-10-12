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
			this.setEnabled(false);
			this.pressed = false;
		}
		else {
			this.setEnabled(true);
			this.pressed = true;
		}
	}
	
	public boolean isPressed() {
		return this.pressed;
	}
}
