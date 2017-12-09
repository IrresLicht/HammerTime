package projektAuswahl;


import gui.erweiterteGuiKlassen.HT_JDialog;
import icons.HT_ICONS;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


import status.HT_Status;
import strukturObjekt.HT_StrukturObjekt;

import basis.HT_ProgrammStart;

import datenbank.strukturObjekt.HT_DB_StrukturObjektLesen;

public class HT_ProjektWahlJPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HT_JDialog aufrufenderDialog;
	private int strukObjektID;
	
	public HT_ProjektWahlJPanel(HT_JDialog aufrufenderDialog, int strukturObjektID) {
		this.aufrufenderDialog = aufrufenderDialog;
		this.strukObjektID = strukturObjektID;
		this.setLayout(new GridBagLayout());
		ArrayList<HT_StrukturObjekt> aktuelleEbene = new HT_DB_StrukturObjektLesen().getDirekteKinder(strukObjektID);
		HT_Status status = HT_ProgrammStart.getStatus();
		ArrayList<Integer> wegZumAktivenProjekt = new  ArrayList<Integer>();
		
		if (status.isAktiv()){
			HT_StrukturObjekt aktuellesStrukturObjekt = status.getAktuellesStrukturObjekt();
			if (aktuellesStrukturObjekt != null){
				wegZumAktivenProjekt = aktuellesStrukturObjekt.getPfadBisWurzelalsInteger();
			}
		}
		for (int index = 0; index<aktuelleEbene.size(); index++){
			HT_StrukturObjekt strukObjekt = aktuelleEbene.get(index);
			HT_ProjektWahlJButton strukObjektButton = new HT_ProjektWahlJButton(this.aufrufenderDialog, strukObjekt);
			if (index == 0){
				strukObjektButton.setObersterButton(true);
			}
			if (index == aktuelleEbene.size()-1){
				strukObjektButton.setUntersterButton(true);
			}
			
			if(strukObjekt.getHatNachfolger()){
				if (wegZumAktivenProjekt.contains(strukObjekt.getID())){
					strukObjektButton.setIcon(new ImageIcon(getClass().getResource(HT_ICONS.GRUPPEAKTIV)));
				} else{
					strukObjektButton.setIcon(new ImageIcon(getClass().getResource(HT_ICONS.GRUPPEINAKTIV)));
				}
			} else{
				if (wegZumAktivenProjekt.contains(strukObjekt.getID())){
					strukObjektButton.setIcon(new ImageIcon(getClass().getResource(HT_ICONS.PROJEKTAKTIV)));
				} else{
					strukObjektButton.setIcon(new ImageIcon(getClass().getResource(HT_ICONS.PROJEKTINAKTIV)));
				}
			}
			this.add(strukObjektButton, new GridBagConstraints(1, index, 1, 1, 1.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		}
	}
}
