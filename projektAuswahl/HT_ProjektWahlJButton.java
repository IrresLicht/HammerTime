package projektAuswahl;

import gui.erweiterteGuiKlassen.HT_JButton;
import gui.erweiterteGuiKlassen.HT_JDialog;
import gui.tray.SystemTrayIcon;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;

import status.HT_Status;
import strukturObjekt.HT_StrukturObjekt;

import basis.HT_GlobaleVariablen;
import basis.HT_ProgrammStart;


public class HT_ProjektWahlJButton extends HT_JButton {

	private static final long serialVersionUID = 1L;
	private HT_StrukturObjekt aktuellesStrukObj;
	private HT_Dialog_ProjektWahl nachFolgeFenster;
	private HT_ProjektWahlJButton dieserButton;
	private HT_ProjektWahlRechtsKlickJWindow rechtsKlickFenster;

	public HT_ProjektWahlJButton(HT_JDialog aufrufenderDialog, HT_StrukturObjekt aktuellesStrukturObjekt) {
		super(aufrufenderDialog);
		this.dieserButton = this;
		this.aktuellesStrukObj = aktuellesStrukturObjekt;
		this.setHorizontalAlignment(JTextField.LEFT);
		this.setBackground(HT_GlobaleVariablen.KNOPFGRAU);
		this.setText(aktuellesStrukObj.getObjektName());
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				switch (e.getButton()) {
				/*
				 * Linksklick
				 */
				case MouseEvent.BUTTON1: 
					rechtsKlickSchliessen();
					auswahlSetzen();
					break;
				/*
				 * Rechtsklick
				 */
				case MouseEvent.BUTTON3:
//					rechtsKlick(e);
					break;
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (aktuellesStrukObj.getHatNachfolger()) {
					nachFolgeFenster = new HT_Dialog_ProjektWahl(dieserButton, aktuellesStrukObj.getID());
//					nachFolgeFenster.positionierenAnButton(dieserButton);
					setNachfolgeDialog(nachFolgeFenster);
//					getAufrufenderDialog().setNachfolgenderDialog(nachFolgeFenster);
				} 
			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});
	}
	
	private void rechtsKlickSchliessen() {
		if (rechtsKlickFenster != null){
			rechtsKlickFenster.dispose();
			rechtsKlickFenster = null;
		}	
	}
	
	private void auswahlSetzen() {
		/*
		 * Gruppen koennen nicht ausgewaehlt werden
		 */
		if (aktuellesStrukObj.getHatNachfolger()){
			return;
		}
		HT_Status status = HT_ProgrammStart.getStatus();
		HT_StrukturObjekt aktuellesStrukturObjekt = status.getAktuellesStrukturObjekt();
		/*
		 * Bei Auswahl des aktiven Projekts, wird diese gestoppt
		 */
		if (aktuellesStrukturObjekt != null && status.getAktuellesStrukturObjekt().equals(aktuellesStrukObj) && status.isAktiv()){
			status.setAktiv(false);
		/*
		 * Auswahl wird aktiv gesetzt
		 */
		} else {
			status.setAktuellesStrukturObjekt(aktuellesStrukObj);
			status.setAktiv(true);
		}
		SystemTrayIcon.aktualisieren(true);
	}
}
