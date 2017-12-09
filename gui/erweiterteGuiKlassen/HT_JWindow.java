package gui.erweiterteGuiKlassen;

import java.awt.Toolkit;
import java.awt.event.MouseEvent;

import javax.swing.JWindow;

/**
 * JWindow fuer Fenster die bei lostFocus NICHT schliessen sollen
 * @author JT
 *
 */
public class HT_JWindow extends JWindow{
	private static final long serialVersionUID = 1L;

	public HT_JWindow() {
		this.setVisible(true);
		this.setAlwaysOnTop(true);
	}

	/**
	 * Positioniert das Fenster in der Mitte des Bildschirms
	 */
	public void mittigPositionieren() {
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2, (Toolkit
				.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2);
	}
	/**
	 * Positioniert das Fenster so, dass es nicht aus dem Bildschirm ragt
	 */
	public void positionierenAufBildschirm(MouseEvent e){
		int MonitorBreite = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int MonitorHoehe = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		int FensterBreite = (int) this.getSize().getWidth();
		int FensterHoehe = (int) this.getSize().getHeight();
		int xKlickPunkt = e.getXOnScreen();
		int yKlickPunkt = e.getYOnScreen();
		if (xKlickPunkt + FensterBreite > MonitorBreite){
			xKlickPunkt = xKlickPunkt - ((xKlickPunkt + FensterBreite) - MonitorBreite);
		}
		if (yKlickPunkt + FensterHoehe > MonitorHoehe){
			yKlickPunkt = yKlickPunkt - ((yKlickPunkt + FensterHoehe) - MonitorHoehe);
		}
		setLocation(xKlickPunkt, yKlickPunkt);
	}
	
}
