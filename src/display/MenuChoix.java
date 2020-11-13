package display;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import util.Observer;

/*
 * JPanel used to create list of buttons
 * It call Observer.update() to notify for clic
 */

public class MenuChoix extends JPanel {

	private static final long serialVersionUID = 1L;
	
	static int HORIZONTALFULL = 1;
	static int VERTICALFULL = 2;
	static int HORIZONTAL = 3;
	static int VERTICAL = 4;
	private Observer observer;
	ImageIcon buttonImage;
	
	/*
	 * Constructor
	 * @Param : Observer, int, ArrayList<String>
	 * Observer used to notify Interface that a button is clicked
	 * int type of display
	 * ArrayList<String> text for buttons
	 */
	public MenuChoix(Observer o,int value, ArrayList<String> list) {
		buttonImage = new ImageIcon("./libs/buttonEmpty.png");
		Image resizedsSolutionImage = buttonImage.getImage().getScaledInstance((int)(buttonImage.getIconWidth()*0.4), (int)(buttonImage.getIconHeight()*0.4),Image.SCALE_SMOOTH);
		buttonImage = new ImageIcon(resizedsSolutionImage);
		this.observer = o;
		setBackground(new Color(100,100,100));
		if(value == MenuChoix.HORIZONTALFULL) {
			setLayout(new GridLayout(1,list.size()));
		} else if(value == MenuChoix.VERTICALFULL) {
			setLayout(new GridLayout(list.size(),1));
		} else if(value==MenuChoix.HORIZONTAL) {
			setLayout(new FlowLayout());
		} else if(value==MenuChoix.VERTICAL) {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		}
		
		for ( int i = 0 ; i < list.size() ; i++) {
			final String buttonName = list.get(i);
			JButton button = new JButton(buttonName,buttonImage);
			button.setHorizontalTextPosition(SwingConstants.CENTER);
			button.setFont(new Font("Comic Sans MS", Font.PLAIN,30));
			button.setForeground(Color.WHITE);
			button.setFocusPainted(false);
			button.setContentAreaFilled(false);
			button.setBorderPainted(false);
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					observer.update(buttonName);
				}
			});
			add(button);
		}
	}
		

}
