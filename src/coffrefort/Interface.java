package coffrefort;


import javax.swing.JFrame;



public class Interface extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public Interface(String name) {
		super(name);
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new CoffreFort());
	}
}
