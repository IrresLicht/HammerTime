package gui.tray;

import gui.tray.linksKlick.TrayIconLinksKlickFenster;

import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import status.HT_Status;

import basis.HT_ProgrammStart;


import zusatzKlassen.MeineException;

public class SystemTrayIcon implements Observer {

	private HT_Status status;
	private static SystemTray trayLeiste;
	private static boolean trayIconGesperrt = false;
	private static TrayIcon myTrayIcon;
	private static TrayIconLinksKlickFenster trayIconLinksFenster;
	private TrayIconRechtsKlickFenster trayIconRechtsFenster;
	
	/*
	 * Speichert die letzte Position des Linksklick-Fensters
	 */
	static int linksKlickX = 0; 
	static int linksKlickY = 0;
	

	public SystemTrayIcon() {
		this.status = HT_ProgrammStart.getStatus();
		this.status.addObserver(this);
		HT_ProgrammStart.getZeitStop().addObserver(this);
		try {
			if (!SystemTray.isSupported()) {
				throw new MeineException("Systemtray wird nicht unterstuetzt");
			}
			iconErzeugen();
			iconInteraktionErzeugen();
		} catch (MeineException e) {
			JOptionPane.showMessageDialog(null, e.getFehlerMeldung() + "(" + this.getClass().getName() + ")",
					"Fehlermeldung", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Probleme mit dem Systemtray! (" + this.getClass().getName()
					+ ")", "Fehlermeldung", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();

		}
	}

	/**
	 * Setzt das Programm-Icon in die Taskleiste
	 */
	private void iconErzeugen() {
		try {
			Image icon = null;
			if (HT_ProgrammStart.getStatus().isAktiv()){
				icon = Toolkit.getDefaultToolkit().getImage(
						getClass().getResource("/icons/UhrGruen24x24.png"));
			} else{
				icon = Toolkit.getDefaultToolkit().getImage(
						getClass().getResource("/icons/UhrRot24x24.png"));
			}
			myTrayIcon = new TrayIcon(icon);
			myTrayIcon.setImageAutoSize(true);
			trayLeiste = SystemTray.getSystemTray();
			trayLeiste.add(myTrayIcon);
			new HT_IconToolTipUpdate(status, myTrayIcon);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Probleme beim Setzen des SystemIcons! ("
					+ this.getClass().getName() + ")", "Fehlermeldung", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	/**
	 * Erzeugt Interaktionsmoeglichkeiten mit dem Programm-Icon
	 */
	private void iconInteraktionErzeugen() {
		myTrayIcon.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				if (!trayIconGesperrt) {
					switch (e.getButton()) {
					/*
					 * Linksklick
					 */
					case MouseEvent.BUTTON1:
						if(trayIconRechtsFenster != null){
							trayIconRechtsFenster.dispose();
							trayIconRechtsFenster = null;
						}
						if(trayIconLinksFenster != null){
							if (trayIconLinksFenster.isVisible()){
								trayIconLinksFenster.dispose();
								trayIconLinksFenster = null;
							} else{
								linksFensterErzeugen(e);
							}
							
						}else{
							linksFensterErzeugen(e);
						}
						break;
					/*
					 * Rechtsklick
					 */
					case MouseEvent.BUTTON3: 
						if(trayIconLinksFenster != null){
							trayIconLinksFenster.dispose();
							trayIconLinksFenster = null;
						}
						if (trayIconRechtsFenster != null){
							if (trayIconRechtsFenster.isVisible()){
								trayIconRechtsFenster.dispose();
								trayIconRechtsFenster = null;
							} else{
								rechtsFensterErzeugen(e);
							}
						}else{
							rechtsFensterErzeugen(e);
						}
						break;
					}
				}
			}

			private void linksFensterErzeugen(MouseEvent e) {
				trayIconLinksFenster = new TrayIconLinksKlickFenster();
				trayIconLinksFenster.setVisible(true);
				trayIconLinksFenster.positioniereFensterAnTray(e);
				linksKlickX = trayIconLinksFenster.getLocationOnScreen().x;
				linksKlickY = trayIconLinksFenster.getLocationOnScreen().y + trayIconLinksFenster.getHeight();
			}
			
			private void rechtsFensterErzeugen(MouseEvent e) {
				trayIconRechtsFenster = new TrayIconRechtsKlickFenster();
				trayIconRechtsFenster.setVisible(true);
				trayIconRechtsFenster.positioniereFensterAnTray(e);
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e){
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
	}

	public static SystemTray getTrayLeiste() {
		return trayLeiste;
	}

	public static TrayIcon getMyTrayIcon() {
		return myTrayIcon;
	}

	public boolean isTrayIconGesperrt() {
		return trayIconGesperrt;
	}

	public static void setTrayIconGesperrt(boolean trayIconSperren) {
		trayIconGesperrt = trayIconSperren;
	}
	
	/**
	 * Aktualisiert die trayIcon-Fenster.
	 * True = Fenster nach Aktualisieung sichbar
	 * False = Fenster nach Aktualisierung nicht sichbar
	 * @param sichbar
	 */
	
	public static void aktualisieren(boolean sichbar){
		if (trayIconLinksFenster != null){
			trayIconLinksFenster.dispose();
			trayIconLinksFenster = new TrayIconLinksKlickFenster();
			trayIconLinksFenster.setLocation(linksKlickX, linksKlickY - trayIconLinksFenster.getHeight());
			trayIconLinksFenster.setVisible(sichbar);
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		new HT_IconToolTipUpdate(status, myTrayIcon);
	}
}
