package gui.erweiterteGuiKlassen;

import java.awt.Toolkit;

import javax.swing.JFrame;

public class HT_JFrame extends JFrame{

	private static final long serialVersionUID = 1L;

	public HT_JFrame() {
		super();
	}
	
	public void mittigPositionieren() {
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2, (Toolkit
				.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2);
	}
}
