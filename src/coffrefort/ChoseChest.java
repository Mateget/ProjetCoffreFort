package coffrefort;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChoseChest implements ActionListener {
	
	private CoffreFort chest;
	private Interface test;
	public ChoseChest(CoffreFort chest,Interface jframe) {
		this.chest = chest;
		this.test = jframe;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		test.getContentPane().removeAll();
		test.getContentPane().add(chest);
		test.repaint();
		test.revalidate();
	}

}
