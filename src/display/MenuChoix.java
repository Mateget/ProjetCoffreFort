package display;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;

import util.Observer;

public class MenuChoix extends JPanel {

	private static final long serialVersionUID = 1L;
	
	static int HORIZONTAL = 1;
	static int VERTICAL = 2;
	private Observer observer;
	public MenuChoix(Observer o,int value, ArrayList<String> list) {
		this.observer = o;
		if(value == MenuChoix.HORIZONTAL) {
			setLayout(new GridLayout(1,list.size()));
		} else {
			setLayout(new GridLayout(list.size(),1));
		}
		
		for ( int i = 0 ; i < list.size() ; i++) {
			final String buttonName = list.get(i);
			JButton button = new JButton(buttonName);
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					observer.update(buttonName);
				}
			});
			add(button);
		}
	}
		

}
