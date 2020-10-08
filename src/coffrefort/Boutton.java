package coffrefort;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

public class Boutton extends JButton implements Receiver,Observer {
	private boolean pressed = false;
	private ArrayList<Integer> lockLinked;
	private ArrayList<Observer> observers;
	private int bouttonID;
	public Boutton(String str,ArrayList<Integer> lockLinked,ArrayList<Observer> observers, int bouttonID) {
		this.bouttonID = bouttonID;
		this.observers = observers;
		this.lockLinked = lockLinked;
		setText(str);
		setEnabled(true);
		ActionListener click = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				pressNotif();
			}
		};
		addActionListener(click);
	}
	
	public int getBouttonID() {
		return bouttonID;
	}

	public boolean isPressed() {
		return this.pressed;
	}
	public void pressNotif() {
		this.pressed = true;
		setEnabled(false);
		notification();
	}
	public void press() {
		this.pressed = true;
		setEnabled(false);
	}
	public void unpress() {
		this.pressed = false;
		setEnabled(true);
	}
	
	
	
	public ArrayList<Integer> getLockLinked() {
		return lockLinked;
	}

	@Override
	public void add(Observer o) {
		// TODO Auto-generated method stub
		observers.add(o);
	}

	@Override
	public void remove(Observer o) {
		// TODO Auto-generated method stub
		observers.remove(o);
	}

	@Override
	public void notification() {
		// TODO Auto-generated method stub
		for ( int i = 0 ; i < observers.size() ; i++) {
			observers.get(i).update(this);
		}
	}

	@Override
	public void update(Object o) {
		// TODO Auto-generated method stub
		System.out.println(getText() +" Mon verrou à changé");
		if (o instanceof Lock) {
			Lock lock = (Lock) o;
			if(!lock.isLocked()) {
				System.out.println(getText()+" I have to press");
				press();
			}
			else {
				System.out.println(getText()+" I have to unpress");
				unpress();
			}
		}
	}
}
