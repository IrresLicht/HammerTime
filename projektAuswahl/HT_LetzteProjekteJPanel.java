package projektAuswahl;

import gui.erweiterteGuiKlassen.HT_JDialog;
import gui.erweiterteGuiKlassen.HT_JButton;
//import gui.meineGuiKlassen.MeinJDialog;
import gui.tray.SystemTrayIcon;

import icons.HT_ICONS;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import status.HT_Status;
import strukturObjekt.HT_StrukturObjekt;


import basis.HT_GlobaleVariablen;
import basis.HT_ProgrammStart;

public class HT_LetzteProjekteJPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private HT_Status status;
	private LinkedList<HT_StrukturObjekt> letzteProjekte;
	private HT_JDialog aufrufendesFenster;
//	private MeinJDialog aufrufendesFenster;
	private HT_ProjektWahlRechtsKlickJWindow rechtsKlickFenster;
	
//	public HT_LetzteProjekteJPanel(MeinJDialog aufrufendesFenster){
	public HT_LetzteProjekteJPanel(HT_JDialog aufrufendesFenster){
		this.status = HT_ProgrammStart.getStatus();
		this.setLayout(new GridBagLayout());
		this.aufrufendesFenster = aufrufendesFenster;
		this.letzteProjekte = new LinkedList<HT_StrukturObjekt>(status.getLetzteProjekte());
		/*
		 * Aktives Projekt wird nicht zur Wiederaufnahme angeboten
		 */
		if (status.isAktiv()){
			letzteProjekte.removeLast();
		}
		int grenze = Math.max(0, letzteProjekte.size()-HT_GlobaleVariablen.ANZAHLLETZTEPROJEKTE);
		int start = letzteProjekte.size();
		if (start != -1){
			for (int index = start-1 ; index >= grenze; index--){
				final HT_StrukturObjekt strukObj = letzteProjekte.get(index);
				HT_JButton projektButton = new HT_JButton(this.aufrufendesFenster);
				projektButton.setText(strukObj.getObjektName());
				projektButton.setToolTipText(strukObj.getPfadVonWurzelalsStringReduziert());
				projektButton.setIcon(new ImageIcon(getClass().getResource(HT_ICONS.WIEDERAUFNAHME)));
				projektButton.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						switch (e.getButton()) {
						/*
						 * Linksklick
						 */
						case MouseEvent.BUTTON1: 
							rechtsKlickSchliessen();
							HT_ProgrammStart.getStatus().setAktuellesStrukturObjekt(strukObj);
							HT_ProgrammStart.getStatus().setAktiv(true);
							SystemTrayIcon.aktualisieren(true);
							break;
						/*
						 * Rechtsklick
						 */
						case MouseEvent.BUTTON3:
							rechtsKlick(e);
							break;
						}
					}
					
					private void rechtsKlick(MouseEvent e) {
						rechtsKlickSchliessen();
						rechtsKlickFenster = new HT_ProjektWahlRechtsKlickJWindow(strukObj);
						rechtsKlickFenster.positionierenAufBildschirm(e);
					}

					private void rechtsKlickSchliessen() {
						if (rechtsKlickFenster != null){
							rechtsKlickFenster.dispose();
							rechtsKlickFenster = null;
						}	
					}

					@Override
					public void mousePressed(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseExited(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseEntered(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseClicked(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
				});
				this.add(projektButton, new GridBagConstraints(0, index, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			}
		}
	}
}
