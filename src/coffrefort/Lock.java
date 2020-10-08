package coffrefort;

import java.awt.Color;

import javax.swing.JLabel;

public class Lock extends JLabel implements Observer,Receiver{
	private static final long serialVersionUID = 1L;
	private boolean locked = true;
	private int lockID;
	private Observer observer;
	public Lock(String str,int lockID) {
		this.lockID = lockID;
		setText(str);
		setHorizontalAlignment(this.getWidth()/2);
		setForeground(Color.RED);
	}
	
	public boolean isLocked() {
		return this.locked;
	}
	public void unlock() {
		this.locked = false;
		setForeground(Color.GREEN);
	}
	public void lock() {
		this.locked = true;
		setForeground(Color.RED);
	}
		
	public void setobserver(Observer observers) {
		this.observer = observers;
	}

	@Override
	public void update(Object o) {
		// TODO Auto-generated method stub
		if (o instanceof Button) {
			System.out.println(getText() +" Update");
			Button boutton = (Button) o;
			for (int i = 0 ; i < boutton.getLockLinked().size() ; i++) {
				System.out.println(getText()+" j'ai reçu Verroux "+boutton.getLockLinked().get(i));
				if(boutton.getLockLinked().get(i).equals(this.lockID)) {
					System.out.println(getText() + " Je change");
					if(isLocked()) {
						unlock();
						System.out.println(getText() + " Je passe en unlock");
					}
					else {
						lock();
						System.out.println(getText() + " Je passe en lock");
					}
					notification();
				}
			}
		}
	}

	@Override
	public void add(Observer o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Observer o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notification() {
		// TODO Auto-generated method stub
		observer.update(this);
	}

}
