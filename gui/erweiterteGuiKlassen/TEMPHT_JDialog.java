package gui.erweiterteGuiKlassen;
//package gui.meineGuiKlassen;
//
//import java.awt.SystemTray;
//import java.awt.Toolkit;
//import java.awt.event.MouseEvent;
//import java.awt.event.WindowEvent;
//import java.awt.event.WindowFocusListener;
//
//import javax.swing.JDialog;
//import javax.swing.JWindow;
//
//public class TEMPHT_JDialog extends JWindow{
//	
//	private static final long serialVersionUID = 1L;
//
//	public HT_JDialog(){
//		super();
////		this.setUndecorated(true);
//		this.setVisible(true);
//		this.setAlwaysOnTop(true);
//	}
//	
//	/**
//	 * Fuegt einen FocusListener hinzu
//	 */
//	public void setzeFocusListener(){
//		this.addWindowFocusListener(new WindowFocusListener() {
//			public void windowGainedFocus(WindowEvent e) {
//			}
//
//			public void windowLostFocus(WindowEvent e) {
//				System.out.println("lost");
//				dispose();
//				
//			}
//		});
//	}
//	
//	/**
//	 * Positioniert das Fenster in der Mitte des Bildschirms
//	 */
//	public void mittigPositionieren() {
//		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2, (Toolkit
//				.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2);
//	}
//	
//	/**
//	 * Positioniert das Fenster so, dass es nicht aus dem Bildschirm ragt
//	 */
//	public void positionierenAufBildschirm(MouseEvent e){
//		int MonitorBreite = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
//		int MonitorHoehe = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
//		int FensterBreite = (int) this.getSize().getWidth();
//		int FensterHoehe = (int) this.getSize().getHeight();
//		int xKlickPunkt = e.getXOnScreen();
//		int yKlickPunkt = e.getYOnScreen();
//		if (xKlickPunkt + FensterBreite > MonitorBreite){
//			xKlickPunkt = xKlickPunkt - ((xKlickPunkt + FensterBreite) - MonitorBreite);
//		}
//		if (yKlickPunkt + FensterHoehe > MonitorHoehe){
//			yKlickPunkt = yKlickPunkt - ((yKlickPunkt + FensterHoehe) - MonitorHoehe);
//		}
//		setLocation(xKlickPunkt, yKlickPunkt);
//	}
//	
//	/**
//	 * Positioniert das Fenster direkt am Klickpunkt, unabhaengig von der
//	 * Position der Taskleiste
//	 * 
//	 * @param e
//	 */
//	public void positioniereFensterAnTray(MouseEvent e) {
//		int MonitorBreite = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
//		int MonitorHoehe = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
//		int FensterBreite = (int) this.getSize().getWidth();
//		int FensterHoehe = (int) this.getSize().getHeight();
//		int xKlickPunkt = e.getX();
//		int yKlickPunkt = e.getY();
//		int PufferZumKlickPunkt = 10;
//		if (xKlickPunkt + FensterBreite > MonitorBreite) {
//			xKlickPunkt = xKlickPunkt + (MonitorBreite - (xKlickPunkt + FensterBreite));
//		}
//		if (yKlickPunkt + FensterHoehe > MonitorHoehe) {
//			yKlickPunkt = yKlickPunkt + (MonitorHoehe - (yKlickPunkt + FensterHoehe));
//		}
//		if (yKlickPunkt > MonitorHoehe / 2) {
//			yKlickPunkt = yKlickPunkt - SystemTray.getSystemTray().getTrayIconSize().height
//					- PufferZumKlickPunkt;
//		} else {
//			yKlickPunkt = yKlickPunkt + PufferZumKlickPunkt;
//		}
//		if (xKlickPunkt > MonitorBreite / 2) {
//			xKlickPunkt = xKlickPunkt - SystemTray.getSystemTray().getTrayIconSize().width
//					- PufferZumKlickPunkt;
//		} else {
//			xKlickPunkt = xKlickPunkt + PufferZumKlickPunkt;
//		}
//		setLocation(xKlickPunkt, yKlickPunkt);
//	}
//
//}
