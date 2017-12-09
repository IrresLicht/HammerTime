package status;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Observable;

import strukturObjekt.HT_StrukturObjekt;

import basis.HT_GlobaleVariablen;
import basis.HT_ProgrammStart;
import buchungsEintrag.HT_BuchungsEintrag;

import datenbank.buchungsEintrag.HT_DB_BuchungsEintragSchreiben;

/**
 * Klasse zum Speichern von aktuellen Zustaenden ueber das Programmende Hinweg
 * @author JT
 *
 */
public class HT_Status extends Observable implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private HT_StrukturObjekt aktuellesStrukturObjekt;
	private HT_BuchungsEintrag aktuellerBuchungsEintrag;
	private boolean aktiv;
	private LinkedList<HT_StrukturObjekt> letzteProjekte;
	
	public HT_Status(){
		this.aktuellesStrukturObjekt = null;
		this.aktuellerBuchungsEintrag = null;
		this.aktiv = false;
		this.letzteProjekte = new LinkedList<HT_StrukturObjekt>();
	}
	
	/**
	 * Erstellt eine neue Buchung in der Datenbank
	 */
	public void neuerAktuellerBuchungsEintrag() {
		long startEndZeit = System.currentTimeMillis();
		int neueBuchungsEintragID = new HT_DB_BuchungsEintragSchreiben().buchungsEintragErstellen(startEndZeit, startEndZeit, aktuellesStrukturObjekt.getID());
		this.aktuellerBuchungsEintrag = new HT_BuchungsEintrag(neueBuchungsEintragID);
	}
	
	/**
	 * Loescht den gespeicherten aktuellen Buchungseintrag. Wird gebraucht, wenn der entsprechende Buchungeintrag
	 * aus der DB geloescht wird.
	 */
	public void aktuellerBuchungsEintragLoeschen() {
		this.aktuellerBuchungsEintrag = null;
	}
	
	/**
	 * Speichert die letzten gewaehlten Strukturobjekte
	 * @param aktuellesStrukturObjekt2
	 */
	private void addToQueue(HT_StrukturObjekt aktuellesStrukturObjekt) {
		if (this.letzteProjekte.contains(aktuellesStrukturObjekt)){
			this.letzteProjekte.remove(aktuellesStrukturObjekt);
			this.letzteProjekte.add(aktuellesStrukturObjekt);
		} else {
			this.letzteProjekte.add(aktuellesStrukturObjekt);
		}
		while (this.letzteProjekte.size() > HT_GlobaleVariablen.ANZAHLLETZTEPROJEKTE+1){
			this.letzteProjekte.remove();
		}
	}
	
	/**
	 * Benachrichtigt die Klassen, die den Status beobachten
	 */
	private void beobachterBenachrichtigen() {
		setChanged();
		notifyObservers(this);
	}
	
	public HT_BuchungsEintrag getAktuellerBuchungsEintrag() {
		return aktuellerBuchungsEintrag;
	}

	public HT_StrukturObjekt getAktuellesStrukturObjekt() {
		return aktuellesStrukturObjekt;
	}
	
	/**
	 * Beendet ggfl. die aktive Buchung und setzt das neue aktuelle Strukturobjekt
	 * @param neuesAktuellesStrukturObjekt
	 */
	public void setAktuellesStrukturObjekt(HT_StrukturObjekt neuesAktuellesStrukturObjekt) {
		setAktiv(false);
		this.aktuellesStrukturObjekt = neuesAktuellesStrukturObjekt;
		addToQueue(this.aktuellesStrukturObjekt);
		new HT_StatusAendern().aktuellenStatusSpeichern();
	}
	
	public boolean isAktiv() {
		return aktiv;
	}

	/**
	 * Beendet oder Startet den Zeitstopp-Thread
	 * @param var
	 */
	public void setAktiv(boolean var) {
		this.aktiv = var;
		if (this.aktiv){
			neuerAktuellerBuchungsEintrag();
			/*
			 * Muss direkt auf Programmstart zugreifen, da der Thread immer ersetzt wird
			 */
			HT_ProgrammStart.getZeitStop().threadStarten(); 
		} else {
			HT_ProgrammStart.getZeitStop().threadStoppen();
		}
		new HT_StatusAendern().aktuellenStatusSpeichern();
		beobachterBenachrichtigen();
	}
	
	public LinkedList<HT_StrukturObjekt> getLetzteProjekte() {
		return letzteProjekte;
	}
}