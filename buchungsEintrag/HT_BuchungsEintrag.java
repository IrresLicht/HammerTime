package buchungsEintrag;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javax.swing.JOptionPane;

import datenbank.buchungsEintrag.HT_DB_BuchungsEintragSchreiben;
import datenbank.buchungsEintrag.HT_DB_BuchungsEintragLesen;

/**
 * Pseudo-Klasse fuer den Zugriff auf BuchungsObjekte aus der Datenbank
 * 
 * @author JT
 * 
 */

public class HT_BuchungsEintrag implements Comparable<HT_BuchungsEintrag>, Externalizable {

	private static final long serialVersionUID = 1L;
	int buchungsEintragID;
	HT_DB_BuchungsEintragLesen dbLesen;
	HT_DB_BuchungsEintragSchreiben dbSchreiben;

	/**
	 * Sollte nicht verwendet werden! Nur fuer Externalizable implementiert
	 * worden.
	 */
	public HT_BuchungsEintrag() {
		this.buchungsEintragID = -1;
		dbInit();
	}

	public HT_BuchungsEintrag(int buchungsEintragID) {
		this.buchungsEintragID = buchungsEintragID;
		dbInit();
	}

	public HT_BuchungsEintrag(HT_BuchungsEintrag buchung) {
		this.buchungsEintragID = buchung.getID();
		dbInit();
	}

	public HT_BuchungsEintrag(TempBuchungsEintrag tempBuchungsEintrag) {
		this.buchungsEintragID = tempBuchungsEintrag.getId();
		dbInit();
	}

	private void dbInit() {
		this.dbLesen = new HT_DB_BuchungsEintragLesen();
		this.dbSchreiben = new HT_DB_BuchungsEintragSchreiben();
	}

	public long getZeitInsgesamt() {
		return getEndzeit() - getStartzeit();
	}

	public int getID() {
		return buchungsEintragID;
	}

	public long getStartzeit() {
		return dbLesen.getStartZeit(buchungsEintragID);
	}

	public void setStartzeit(long neueStartzeit) {
		if (neueStartzeit > getEndzeit()) {
			JOptionPane.showMessageDialog(null, "Der Buchungsbeginn muss vor dem Buchungsende liegen.",
					"Ungültiger Buchungsbeginn", JOptionPane.WARNING_MESSAGE);
			return;
		}
		dbSchreiben.setStartZeit(buchungsEintragID, neueStartzeit);
	}

	public long getEndzeit() {
		return dbLesen.getEndZeit(buchungsEintragID);
	}

	public void setEndzeit(long neueEndzeit) {
		if (neueEndzeit > System.currentTimeMillis()) {
			JOptionPane.showMessageDialog(null, "Das Buchungsende darf nicht in der Zukunft liegen.",
					"Ungültiges Buchungsende", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (neueEndzeit < getStartzeit()) {
			JOptionPane.showMessageDialog(null, "Das Buchungsende muss hinter dem Buchungsbeginn liegen.",
					"Ungültiges Buchungsende", JOptionPane.WARNING_MESSAGE);
			return;
		}
		dbSchreiben.setEndZeit(buchungsEintragID, neueEndzeit);
	}

	public String getKommentar() {
		return dbLesen.getKommentar(buchungsEintragID);
	}

	public void setKommentar(String neuerKommentar) {
		dbSchreiben.setKommentar(buchungsEintragID, neuerKommentar);
	}

	public int getStrukturObjektID() {
		return dbLesen.getStrukturObjektID(buchungsEintragID);
	}

	public void setStukturObjektID(int neueStrukurObjektID) {
		dbSchreiben.setStrukturObjektID(buchungsEintragID, neueStrukurObjektID);
	}

	@Override
	public String toString() {
		return "ID: " + getID() + " Startzeit: " + getStartzeit() + " Endzeit: "
				+ getEndzeit();
	}

	/**
	 * Der Vergleich von Buchungseintraegen richtet sich nach der Startzeit
	 */
	@Override
	public int compareTo(HT_BuchungsEintrag argument) {
		if (this.getStartzeit() <= argument.getStartzeit()) {
			return -1;
		} else {
			return 1;
		}
	}

	/**
	 * Ein BuchungsEintrag ist bei gleicher ID gleich.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof HT_BuchungsEintrag)) {
			return false;
		}
		return getID() == ((HT_BuchungsEintrag) o).getID();
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		this.buchungsEintragID = in.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(this.buchungsEintragID);
	}
}
