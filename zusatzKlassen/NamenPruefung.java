package zusatzKlassen;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import strukturObjekt.HT_StrukturObjekt;

import datenbank.strukturObjekt.HT_DB_StrukturObjektLesen;

public class NamenPruefung {
	
	HT_DB_StrukturObjektLesen datenbank;
	public NamenPruefung(){
		datenbank = new HT_DB_StrukturObjektLesen();
	}
	
	/**
	 * Prueft, ob es ein StrukturObjekt mit gleichem Namen und Typ (Gruppe/Projekt) wie "quelle" in "ziel" gibt. 
	 * @param quelle
	 * @param ziel
	 * @return
	 */
	public boolean verschieben_nameBereitsVorhanden(HT_StrukturObjekt quelle, HT_StrukturObjekt ziel){
		String quelleName = quelle.getObjektName();
		ArrayList<HT_StrukturObjekt> kinder;
		if (ziel.getHatNachfolger()) { 
			/*
			 * Ziel ist Gruppe
			 */
			kinder = datenbank.getDirekteKinder(ziel.getID());
		} else {
			/*
			 * Ziel ist Projekt
			 */
			kinder = datenbank.getDirekteKinder(ziel.getGruppenID());
		}
		
		if (quelle.getHatNachfolger()) {
			/*
			 * Quelle ist Gruppe
			 */
			for (int index = 0; index < kinder.size(); index++) {
				HT_StrukturObjekt temp = kinder.get(index);
				if (temp.getHatNachfolger()) { 
					/*
					 * Kind ist Gruppe
					 */
					if (quelleName.equals(temp.getObjektName())) {
						nameVorhandenWarnung();
						return true;
					}
				}
			}
		} else {
			/*
			 * Quelle ist Projekt
			 */
			for (int index = 0; index < kinder.size(); index++) {
				HT_StrukturObjekt temp = kinder.get(index);
				if (!temp.getHatNachfolger()) {
					/*
					 * Kind ist Projekt
					 */
					if (quelleName.equals(temp.getObjektName())) {
						nameVorhandenWarnung();
						return true;
					}
				}
			}
		}
		return false;
	}
	/**
	 * Prueft, ob es ein Projekt (!gruppe) oder eine Gruppe (gruppe) mit gleichem Namen (neuerName) in der Gruppe (gruppenID) gibt
	 * @param gruppenID
	 * @param neuerName
	 * @param gruppe
	 * @return
	 */
	public boolean eintragen_nameBereitsVorhanden(int gruppenID, String neuerName, boolean gruppe) {
		ArrayList<HT_StrukturObjekt> kinder = datenbank.getDirekteKinder(gruppenID);
		
		for (int index = 0; index < kinder.size(); index++) {
			HT_StrukturObjekt temp = kinder.get(index);
			if (temp.getHatNachfolger() && gruppe) { 
				/*
				 * Kind ist Gruppe, neues Objekt ist Gruppe
				 */
				if (neuerName.equals(temp.getObjektName())) {
					nameVorhandenWarnung();
					return true;
				}
			} 
			if (!temp.getHatNachfolger() && !gruppe) {
				/*
				 * Kind ist Projekt, neues Objekt ist Projekt
				 */
				if (neuerName.equals(temp.getObjektName())) {
					nameVorhandenWarnung();
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Pruft ob StrukturObjekt (id) in neuerName umbenannt werden darf
	 * @param id
	 * @param neuerName
	 * @return
	 */
	public boolean umbenennen_nameBereitsVorhanden(int id, String neuerName) {
		HT_StrukturObjekt strukturObjekt = new HT_StrukturObjekt(id);
		ArrayList<HT_StrukturObjekt> kinder = datenbank.getDirekteKinder(strukturObjekt.getGruppenID());
		if (strukturObjekt.getHatNachfolger()){
			/*
			 *  id ist Gruppe
			 */
			for (int index = 0; index < kinder.size(); index++){
				HT_StrukturObjekt temp = kinder.get(index);
				if (temp.getHatNachfolger()){
					if (temp.getObjektName().equals(neuerName)){
						nameVorhandenWarnung();
						return true;
					}
				}
			}
		} else { 
			/*
			 * id ist Projekt
			 */
			for (int index = 0; index < kinder.size(); index++){
				HT_StrukturObjekt temp = kinder.get(index);
				if (!temp.getHatNachfolger()){
					if (temp.getObjektName().equals(neuerName)){
						nameVorhandenWarnung();
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private void nameVorhandenWarnung() {
		JOptionPane.showMessageDialog(null, "Der Name ist bereits an dieser Stelle vorhanden.",
				"Ungültiges Buchungsende", JOptionPane.WARNING_MESSAGE);

	}
}
