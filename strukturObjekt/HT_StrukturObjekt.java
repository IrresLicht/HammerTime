package strukturObjekt;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;

import buchungsEintrag.HT_BuchungsEintrag;

import datenbank.buchungsEintrag.HT_DB_BuchungsEintragLesen;
import datenbank.strukturObjekt.HT_DB_StrukturObjektLesen;

/**
 * Pseudo-Klasse fuer den Zugriff auf StrukturObjekte aus der Datenbank
 * @author JT
 *
 */

public class HT_StrukturObjekt implements Comparable<HT_StrukturObjekt>, Externalizable{
	
	int strukturObjektID;
	HT_DB_StrukturObjektLesen dbLesen;
	
	/**
	 * Sollte nicht verwendet werden! Nur fuer Externalizable implementiert worden.
	 */
	public HT_StrukturObjekt(){
		this.strukturObjektID = -1;
		this.dbLesen = new HT_DB_StrukturObjektLesen();
	}
	
	public HT_StrukturObjekt(int strukturObjektID){
		this.strukturObjektID = strukturObjektID;
		this.dbLesen = new HT_DB_StrukturObjektLesen();
	}
	
	/**
	 * Liefert den Pfad mit Root-Knoten
	 * @return
	 */
	public String getPfadVonWurzelalsString() {
		StringBuffer ausgabe = new StringBuffer();
		HT_StrukturObjekt strukturObj = new HT_StrukturObjekt(strukturObjektID);
		ausgabe.insert(0, strukturObj.getObjektName());
		int suche = strukturObj.getGruppenID();
		while (suche != -1){
			HT_StrukturObjekt strukturObjekt = new HT_StrukturObjekt(suche);
			suche = strukturObjekt.getGruppenID();
			ausgabe.insert(0, strukturObjekt.getObjektName()+ " > ");
		}
		return ausgabe.toString();
	}
	
	/**
	 * Liefert den Pfad ohne Root-Knoten
	 * @return
	 */
	public String getPfadVonWurzelalsStringReduziert() {
		StringBuffer ausgabe = new StringBuffer();
		HT_StrukturObjekt strukturObj = new HT_StrukturObjekt(strukturObjektID);
		ausgabe.insert(0, strukturObj.getObjektName());
		int suche = strukturObj.getGruppenID();
		while (suche > 1){
			HT_StrukturObjekt strukturObjekt = new HT_StrukturObjekt(suche);
			suche = strukturObjekt.getGruppenID();
			ausgabe.insert(0, strukturObjekt.getObjektName()+ " > ");
		}
		return ausgabe.toString();
	}
	
	/**
	 * Liefert eine ArrayList mit allen StrukturObjekt-IDs von diesem Strukturobjekt bis zur Wurzel
	 * @return
	 */
	public ArrayList<Integer> getPfadBisWurzelalsInteger(){
		ArrayList<Integer> ausgabe = new ArrayList<Integer>();
		int suche = strukturObjektID;
		ausgabe.add(suche);
		while (suche != -1){
			HT_StrukturObjekt strukturObjekt = new HT_StrukturObjekt(suche);
			suche = strukturObjekt.getGruppenID();
			ausgabe.add(suche);
		}
		return ausgabe;
	}
	
	/**
	 * Liefert die Summe der Buchungen in dem angegebenen Zeitraum
	 * @param start
	 * @param ende
	 * @return
	 */
	public long getZeitSummeVonBis(long start, long ende){
		ArrayList<HT_BuchungsEintrag> liste = new HT_DB_BuchungsEintragLesen().getBuchungsEintraege(strukturObjektID, start, ende);
		long ausgabe = 0;
		for (int index = 0; index < liste.size(); index++){
			HT_BuchungsEintrag temp = liste.get(index);
			ausgabe += (temp.getEndzeit()-temp.getStartzeit());
		}
		return ausgabe;
	}
	
	public int getID() {
		return strukturObjektID;
	}
	
	public int getGruppenID(){
		return dbLesen.getGruppenID(strukturObjektID);
	}
	
	public String getObjektName(){
		return dbLesen.getStrukturObjektName(strukturObjektID);
	}
	
	public boolean getHatNachfolger(){
		return dbLesen.getHatNachfolger(strukturObjektID);
	}
	
	public int getLinks(){
		return dbLesen.getLinks(strukturObjektID);
	}
	
	public int getRechts(){
		return dbLesen.getRechts(strukturObjektID);
	}
	
	@Override
	public String toString() {
		return dbLesen.getStrukturObjektName(strukturObjektID);
	}
	
	@Override   
	public int compareTo(HT_StrukturObjekt argument) {
		if (this.getHatNachfolger() && !argument.getHatNachfolger()){
			return -1;
		}
		if (!this.getHatNachfolger() && argument.getHatNachfolger()){
			return 1;
		}
		if (this.getHatNachfolger() && argument.getHatNachfolger()){
			String dieserName = this.getObjektName();
			String argumentName = argument.getObjektName();
			dieserName = dieserName.toLowerCase();
			argumentName = argumentName.toLowerCase();
		    return dieserName.compareTo(argumentName);
		}
		if (!this.getHatNachfolger() && !argument.getHatNachfolger()){
			String dieserName = this.getObjektName();
			String argumentName = argument.getObjektName();
			dieserName = dieserName.toLowerCase();
			argumentName = argumentName.toLowerCase();
		    return dieserName.compareTo(argumentName);
		}
		return 0;
	}
	
	/**
	 * Ein StrukturObjekt ist bei gleicher ID gleich.
	 */
	@Override
	public boolean equals(Object o){
		if (o == null) {
			return false;
		}
		if (!(o instanceof HT_StrukturObjekt)) {
			return false;
		}
		return getID() == ((HT_StrukturObjekt) o).getID();
	}
	
	@Override
	public int hashCode(){
		return super.hashCode();
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		this.strukturObjektID = in.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(this.strukturObjektID);
	}
}