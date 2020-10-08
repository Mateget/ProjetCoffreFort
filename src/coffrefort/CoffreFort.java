package coffrefort;


import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;



public class CoffreFort extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Observer> observers = new ArrayList<Observer>();
    int lockNumber = 6;
    public CoffreFort() {
    	
        setLayout(new GridLayout(2, lockNumber));
        generateLockBasic();
    }
    
    public void generateLockBasic() {
    	Lock lock1 = new Lock("Verrou 1",1);
    	lock1.lock();
    	Lock lock2 = new Lock("Verrou 2",2);
    	lock2.unlock();
    	Lock lock3 = new Lock("Verrou 3",3);
    	lock3.lock();
    	observers.add(lock1);
    	observers.add(lock2);
    	observers.add(lock3);
    	ArrayList<Integer> lockLinked1 = new ArrayList<Integer>();
    	lockLinked1.add(1);
    	lockLinked1.add(2);
    	Boutton boutton1 = new Boutton("Boutton 1", lockLinked1, observers,1);
    	boutton1.unpress();
    	
    	ArrayList<Integer> lockLinked2 = new ArrayList<Integer>();
    	lockLinked2.add(1);
    	lockLinked2.add(2);
    	lockLinked2.add(3);
    	Boutton boutton2 = new Boutton("Boutton 2", lockLinked2, observers,2);
    	boutton2.press();
    	
    	ArrayList<Integer> lockLinked3 = new ArrayList<Integer>();
    	lockLinked3.add(2);
    	lockLinked3.add(3);
    	Boutton boutton3 = new Boutton("Boutton 3", lockLinked3, observers,3);
    	boutton3.unpress();
    	lock1.setobserver(boutton1);
    	lock2.setobserver(boutton2);
    	lock3.setobserver(boutton3);
    	add(lock1);
    	add(lock2);
    	add(lock3);
    	add(boutton1);
    	add(boutton2);
    	add(boutton3);
    }
    public void generateLockHard() {
    	Lock lock1 = new Lock("Verrou 1",1);
    	lock1.unlock();
    	Lock lock2 = new Lock("Verrou 2",2);
    	lock2.unlock();
    	Lock lock3 = new Lock("Verrou 3",3);
    	lock3.unlock();
    	Lock lock4 = new Lock("Verrou 4",4);
    	lock4.lock();
    	Lock lock5 = new Lock("Verrou 5",5);
    	lock5.unlock();
    	Lock lock6 = new Lock("Verrou 6",6);
    	lock6.unlock();
    	observers.add(lock1);
    	observers.add(lock2);
    	observers.add(lock3);
    	observers.add(lock4);
    	observers.add(lock5);
    	observers.add(lock6);
    	ArrayList<Integer> lockLinked1 = new ArrayList<Integer>();
    	lockLinked1.add(1);
    	lockLinked1.add(2);
    	lockLinked1.add(5);
    	Boutton boutton1 = new Boutton("Boutton 1", lockLinked1, observers,1);
    	boutton1.press();
    	
    	ArrayList<Integer> lockLinked2 = new ArrayList<Integer>();
    	lockLinked2.add(2);
    	lockLinked2.add(4);
    	lockLinked2.add(5);
    	Boutton boutton2 = new Boutton("Boutton 2", lockLinked2, observers,2);
    	boutton2.press();
    	
    	ArrayList<Integer> lockLinked3 = new ArrayList<Integer>();
    	lockLinked3.add(1);
    	lockLinked3.add(3);
    	lockLinked3.add(6);
    	Boutton boutton3 = new Boutton("Boutton 3", lockLinked3, observers,3);
    	boutton3.press();
    	
    	ArrayList<Integer> lockLinked4 = new ArrayList<Integer>();
    	lockLinked4.add(2);
    	lockLinked4.add(4);
    	Boutton boutton4 = new Boutton("Boutton 4", lockLinked4, observers,4);
    	boutton4.unpress();
    	
    	ArrayList<Integer> lockLinked5 = new ArrayList<Integer>();
    	lockLinked5.add(1);
    	lockLinked5.add(2);
    	lockLinked5.add(3);
    	lockLinked5.add(5);
    	Boutton boutton5 = new Boutton("Boutton 5", lockLinked5, observers,5);
    	boutton5.press();
    	
    	ArrayList<Integer> lockLinked6 = new ArrayList<Integer>();
    	lockLinked6.add(1);
    	lockLinked6.add(3);
    	lockLinked6.add(4);
    	lockLinked6.add(5);
    	lockLinked6.add(6);
    	Boutton boutton6 = new Boutton("Boutton 6", lockLinked6, observers,6);
    	boutton6.press();
    	
    	lock1.setobserver(boutton1);
    	lock2.setobserver(boutton2);
    	lock3.setobserver(boutton3);
    	lock4.setobserver(boutton4);
    	lock5.setobserver(boutton5);
    	lock6.setobserver(boutton6);
    	add(lock1);
    	add(lock2);
    	add(lock3);
    	add(lock4);
    	add(lock5);
    	add(lock6);
    	add(boutton1);
    	add(boutton2);
    	add(boutton3);
    	add(boutton4);
    	add(boutton5);
    	add(boutton6);
    }

	
}