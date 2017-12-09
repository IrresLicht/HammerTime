package gui.erweiterteGuiKlassen;

import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JDialog;


/**
 * JDialog fuer Fenster die bei lostFocus schliessen sollen
 * @author JT
 *
 */

public class HT_JDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	/*
	 * Button in dem sich die Maus befindet
	 */
	private HT_JButton aufrufenderButton;
	private HT_JButton mausButton;

	public HT_JDialog(HT_JButton aufrufenderButton){
		this.aufrufenderButton = aufrufenderButton;
		init();
	}
	public HT_JDialog(){
		init();
	}
	
	private void init() {
		this.mausButton = null;
		this.setUndecorated(true);
		this.setVisible(true);
		this.setAlwaysOnTop(true);
		this.addWindowFocusListener(new WindowFocusListener() {
			
			@Override
			public void windowLostFocus(WindowEvent e) {
				if (mausButton == null){
					System.out.println("Fokusweg");
					dispose();
					return;
				}	
			}
			
			@Override
			public void windowGainedFocus(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
		

	/**
	 * Positioniert den Dialog in der Mitte des Bildschirms
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
	
	/**
	 * Positioniert das Fenster direkt am Klickpunkt, unabhaengig von der
	 * Position der Taskleiste
	 * 
	 * @param mausEvent
	 */
	public void positioniereFensterAnTray(MouseEvent mausEvent) {
		int MonitorBreite = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int MonitorHoehe = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		int FensterBreite = (int) this.getSize().getWidth();
		int FensterHoehe = (int) this.getSize().getHeight();
		int xKlickPunkt = mausEvent.getX();
		int yKlickPunkt = mausEvent.getY();
		int PufferZumKlickPunkt = 10;
		if (xKlickPunkt + FensterBreite > MonitorBreite) {
			xKlickPunkt = xKlickPunkt + (MonitorBreite - (xKlickPunkt + FensterBreite));
		}
		if (yKlickPunkt + FensterHoehe > MonitorHoehe) {
			yKlickPunkt = yKlickPunkt + (MonitorHoehe - (yKlickPunkt + FensterHoehe));
		}
		if (yKlickPunkt > MonitorHoehe / 2) {
			yKlickPunkt = yKlickPunkt - SystemTray.getSystemTray().getTrayIconSize().height
					- PufferZumKlickPunkt;
		} else {
			yKlickPunkt = yKlickPunkt + PufferZumKlickPunkt;
		}
		if (xKlickPunkt > MonitorBreite / 2) {
			xKlickPunkt = xKlickPunkt - SystemTray.getSystemTray().getTrayIconSize().width
					- PufferZumKlickPunkt;
		} else {
			xKlickPunkt = xKlickPunkt + PufferZumKlickPunkt;
		}
		setLocation(xKlickPunkt, yKlickPunkt);
	}
	
	public void nachfolgeDialogschliessen(){
		if (this.mausButton != null && this.mausButton.getNachfolgeDialog() != null){
			this.mausButton.getNachfolgeDialog().nachfolgeDialogschliessen();
			this.mausButton.getNachfolgeDialog().dispose();
			this.mausButton = null;
		}
	}
	
	public void alleSchliessen(){
		if (aufrufenderButton != null && aufrufenderButton.getAufrufenderDialog() != null){
			aufrufenderButton.getAufrufenderDialog().alleSchliessen();
		}
		this.dispose();
		nachfolgeDialogschliessen();
	}

	public HT_JButton getMausButton() {
		return mausButton;
	}
	
	public void setMausButton(HT_JButton letzterMausButton) {
		this.mausButton = letzterMausButton;
	}
}
