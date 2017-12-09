package basis;

import gui.fenster.WeiterBuchenFenster;
import gui.tray.SystemTrayIcon;

import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import status.HT_Status;
import status.HT_StatusAendern;
import status.HT_StatusDateiErzeugen;
import zeitstoppen.ZeitStoppen;

import datenbank.DatenBankErzeugen;

/**
 * Initiiert das Programm. Vorraussetzungen werden ueberprueft oder geschaffen.
 * 
 * @author jt
 * 
 */
public class HT_ProgrammStart {
	private static HT_Status status;
	private static ZeitStoppen zeitStop;
	
	public HT_ProgrammStart() {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			datenBankCheck();
			zeitStopErzeugen();
			statusLaden();
			statusCheck();
			new SystemTrayIcon();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Probleme bei Programmstart (" + this.getClass().getName()
					+ ")", "Fehlermeldung", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	/**
	 * Prueft, ob die Datenbank im Programmordner vohanden ist.Falls nicht wird
	 * eine Methode zur Erzeugung aufgerufen
	 */
	private void datenBankCheck() {
		File datenBankDatei = new File(HT_GlobaleVariablen.DATENBANKNAME);
		if (!datenBankDatei.exists()) {
			try {
				new DatenBankErzeugen();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Probleme beim Datenbank-Check! ("
						+ this.getClass().getName() + ")", "Fehlermeldung", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Laed den Status aus der Status-Datei, bzw. erzeugt diese
	 */
	private void statusLaden() {
		File statusDatei = new File(HT_GlobaleVariablen.STATUSDATEI);
		if (!statusDatei.exists()){
			try {
				new HT_StatusDateiErzeugen();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Probleme beim StatusDatei-Check! ("
						+ this.getClass().getName() + ")", "Fehlermeldung", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
		status = new HT_StatusAendern().aktuellenStatusLaden();
	}
	
	/**
	 * Uberprueft, ob das Programm richtig beendet wurde (aktiv = false).
	 * Bietet anderenfalls die Moeglichkeit an, das letzte Projekt wieder aufzunehmen.
	 */
	private void statusCheck() {
		WeiterBuchenFenster weiterBuchen = null;
		if (status.isAktiv()){
			weiterBuchen = new WeiterBuchenFenster(status.getAktuellesStrukturObjekt());
			if (weiterBuchen.getBestaetigung()){
				status.setAktiv(true);
			} else {
				status.setAktiv(false);
			}
		}
		if (weiterBuchen != null) {
			weiterBuchen.dispose();
		}
		
	}

	private void zeitStopErzeugen() {
		zeitStop = new ZeitStoppen();
	}

	public static HT_Status getStatus() {
		return status;
	}

	public static void setStatus(HT_Status neuerstatus) {
		status = neuerstatus;
	}

	public static ZeitStoppen getZeitStop() {
		return zeitStop;
	}	
}
