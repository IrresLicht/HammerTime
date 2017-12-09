package buchungsEintrag;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import basis.HT_GlobaleVariablen;


/**
 * Klasse zum temporaerem Speichern von Anderungen an BuchungsEintraegen.
 * @author JT
 *
 */
public class TempBuchungsEintrag {
	
	private int id;
	private long startZeit;
	private long endZeit;
	private String kommentar;
	private int strukturObjektID;
	
	/**
	 * Erstellt aus den uebergebenen Parametern einen temporaeren Buchungseintrag
	 * @param id
	 * @param startZeit
	 * @param endZeit
	 */
	public TempBuchungsEintrag (int id, long startZeit, long endZeit){
		this.id = id;
		this.startZeit = startZeit;
		this.endZeit = endZeit;
		HT_BuchungsEintrag buchung = new HT_BuchungsEintrag(id);
		/*
		 * Tempbuchung ohne reale Buchung in der Datenbank
		 */
		if (id != -1){
			this.strukturObjektID = buchung.getStrukturObjektID();
		}
	}
	
	/**
	 * Erstellt einen temporären Buchungseintrag aus dem uebergebenen Buchungseintrag
	 * @param original
	 */
	public TempBuchungsEintrag(HT_BuchungsEintrag original){
		if (original == null){
			this.id = -1;
			this.startZeit = -1;
			this.endZeit = -1;
			this.strukturObjektID = -1;
		} else{
			this.id =original.getID();
			this.startZeit = original.getStartzeit();
			this.endZeit = original.getEndzeit();
			this.kommentar = original.getKommentar();
			this.strukturObjektID = original.getStrukturObjektID();
		}
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public long getStartZeit() {
		return startZeit;
	}
	
	public void setStartZeit(long startZeit) {
		this.startZeit = startZeit;
	}
	
	public long getEndZeit() {
		return endZeit;
	}

	public void setEndZeit(long endZeit) {
		this.endZeit = endZeit;
	}
	
	public String getKommentar() {
		if (kommentar == null){
			return "";
		} else {
			return kommentar;
		}
	}

	public void setKommentar(String kommentar) {
		this.kommentar = kommentar;
	}

	public int getStrukturObjektID() {
		return strukturObjektID;
	}

	public void setStrukturObjektID(int strukturObjektID) {
		this.strukturObjektID = strukturObjektID;
	}

	public String toString(){
		SimpleDateFormat datumsFormat = new SimpleDateFormat(HT_GlobaleVariablen.FORMATDATUMZEIT);
		return "ID: "+ id + " Start: "+ datumsFormat.format(new Timestamp (startZeit)) + " Ende: "+ datumsFormat.format(new Timestamp (endZeit));
	}

	public long getZeitInsgesamt() {
		long startZeit = getStartZeit();
		long endZeit = getEndZeit();
		return endZeit - startZeit;
	}	
}
