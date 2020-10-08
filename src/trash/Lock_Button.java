package trash;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;

import coffrefort.Observer;

public class Lock_Button implements Observer{
	private Boolean locked;
	private Boolean pressed;
	private int xpos,ypos;
	private int width,height;
	private JLabel label;
	private JButton button;
	public Lock_Button(int xpos, int ypos, int width, int height) {
		this.locked = true;
		this.pressed = false;
		this.xpos = xpos;
		this.ypos = ypos;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void update(Object o) {
		// TODO Auto-generated method stub
		
	}
	public Lock_Button() {
		this.locked = true;
		this.pressed = false;
	}
	
	
	public JButton getButton() {
		return button;
	}
	public void setButton(JButton button) {
		this.button = button;
	}
	public JLabel getLabel() {
		return label;
	}
	public void setLabel(JLabel label) {
		this.label = label;
	}
	public void lock() {
		this.locked = true;
		this.pressed = false;
		this.label.setForeground(Color.RED);
	}
	public void unlock() {
		this.locked = false;
		this.pressed = true;
		this.label.setForeground(Color.GREEN);
	}
	
	public Boolean isLocked() {
		return locked;
	}

	public Boolean isPressed() {
		return pressed;
	}

	public int getXpos() {
		return xpos;
	}
	public void setXpos(int xpos) {
		this.xpos = xpos;
	}
	public int getYpos() {
		return ypos;
	}
	public void setYpos(int ypos) {
		this.ypos = ypos;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	
}
