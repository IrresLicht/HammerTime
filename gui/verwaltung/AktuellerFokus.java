package gui.verwaltung;

import gui.verwaltung.tabelleKlassen.MeinJTable;
import gui.verwaltung.tabelleKlassen.MeineBuchungsListeJScrollPane;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Vector;

import buchungsEintrag.HT_BuchungsEintrag;
import buchungsEintrag.TempBuchungsEintrag;
import buchungsEintrag.buchungsaenderung.HT_MeineZeilenPruefung;

import zusatzKlassen.DatumUmrechung;

import datenbank.buchungsEintrag.HT_DB_BuchungsEintragLesen;

public class AktuellerFokus extends Observable {

	private long anzeigeVon = -1;
	private long anzeigeBis = -1;
	private int strukturObjektFokusID = 1;
	private long zeitInsgesamt = 0;
	private Vector<Vector<Object>> buchungsDaten = null;
	private FormatOptionen formatModus = FormatOptionen.TAG;
	private HT_MeineZeilenPruefung zeilenPruefung;
	private MeinJTable table = null;
	private HT_DB_BuchungsEintragLesen buchungsEintragLesen;
	private DatumUmrechung berechung;
	private boolean tortenAnsicht = true;

	public AktuellerFokus() {
		heuteAnzeigen();
		this.zeilenPruefung = new HT_MeineZeilenPruefung();
		this.buchungsEintragLesen = new HT_DB_BuchungsEintragLesen();
	}

	public long getAnzeigeVon() {
		return anzeigeVon;
	}

	public void setAnzeigeVon(long anzeigeVon) {
		this.anzeigeVon = anzeigeVon;
		beobachterBenachrichtigen();
	}

	public long getAnzeigeBis() {
		return anzeigeBis;
	}

	public void setAnzeigeBis(long anzeigeBis) {
		this.anzeigeBis = anzeigeBis;
		beobachterBenachrichtigen();
	}

	public void setAnzeigeVonBis(long anzeigeVon, long anzeigeBis) {
		this.anzeigeVon = anzeigeVon;
		this.anzeigeBis = anzeigeBis;
		beobachterBenachrichtigen();
	}

	public int getStrukturObjektFokusID() {
		return strukturObjektFokusID;
	}

	public void setStrukturObjektFokusID(int strukturObjektFokusID) {
		if (this.strukturObjektFokusID == strukturObjektFokusID) {
			return;
		}
		this.strukturObjektFokusID = strukturObjektFokusID;
		if (formatModus.equals(FormatOptionen.KOMPLETT)) {
			komplettAnzeigen();
		}
		beobachterBenachrichtigen();
		/*
		 * Beendet den Celleditor, falls dieser noch offen ist
		 */
		if (table != null) {
			table.editorBeenden();
		}
	}

	public Vector<Vector<Object>> getBuchungsDaten() {
		return buchungsDaten;
	}

	public long getZeitInsgesamt() {
		return zeitInsgesamt;
	}

	public FormatOptionen getFormatModus() {
		return formatModus;
	}

	public void setFormatModus(FormatOptionen formatModus) {
		if (this.formatModus.equals(formatModus)) {
			return;
		}
		this.formatModus = formatModus;
		switch (this.formatModus) {
		case TAG:
			setTagesGrenzen(anzeigeVon);
			break;
		case WOCHE:
			setWochenGrenzen(anzeigeVon);
			break;
		case MONAT:
			setMonatGrenzen(anzeigeVon);
			break;
		default:
			break;
		}
		beobachterBenachrichtigen();
	}

	public void setTortenAnsicht(boolean tortenAnsicht) {
		this.tortenAnsicht = tortenAnsicht;
		beobachterBenachrichtigen();
	}

	public boolean isTortenAnsicht() {
		return tortenAnsicht;
	}

	public void heuteAnzeigen() {
		switch (formatModus) {

		case WOCHE:
			setWochenGrenzen(System.currentTimeMillis());
			break;
		case MONAT:
			setMonatGrenzen(System.currentTimeMillis());
			break;
		default:
			setTagesGrenzen(System.currentTimeMillis());
			break;
		}
	}

	public void komplettAnzeigen() {
		int fruehsteBuchungID = buchungsEintragLesen.getfruehsteBuchung(getStrukturObjektFokusID());
		if (fruehsteBuchungID != -1) {
			setAnzeigeVon(new HT_BuchungsEintrag(fruehsteBuchungID).getStartzeit());
		} else {
			setTagesGrenzen(System.currentTimeMillis());
		}
		int spaetesteBuchungsID = buchungsEintragLesen.getspatesteBuchung(getStrukturObjektFokusID());
		if (spaetesteBuchungsID != -1) {
			setAnzeigeBis(new HT_BuchungsEintrag(spaetesteBuchungsID).getEndzeit());
		} else {
			setTagesGrenzen(System.currentTimeMillis());
		}
	}

	public void sprungZurueck() {
		switch (formatModus) {
		case WOCHE:
			sprungEineWocheZurueck();
			break;
		case MONAT:
			sprungEinenMonatZurueck();
			break;
		default:
			sprungEinenTagZurueck();
			break;
		}
	}

	private void sprungEinenTagZurueck() {
		GregorianCalendar datum = new GregorianCalendar();
		datum.setTimeInMillis(getAnzeigeVon());
		datum.add(Calendar.DAY_OF_MONTH, -1);
		setTagesGrenzen(datum.getTimeInMillis());
	}

	private void sprungEineWocheZurueck() {
		GregorianCalendar datum = new GregorianCalendar();
		datum.setTimeInMillis(getAnzeigeVon());
		datum.add(Calendar.DAY_OF_MONTH, -7);
		setWochenGrenzen(datum.getTimeInMillis());
	}

	private void sprungEinenMonatZurueck() {
		GregorianCalendar datum = new GregorianCalendar();
		datum.setTimeInMillis(getAnzeigeVon());
		datum.add(Calendar.DAY_OF_MONTH, -15);
		setMonatGrenzen(datum.getTimeInMillis());
	}

	public void sprungVor() {
		switch (formatModus) {
		case WOCHE:
			sprungEineWocheVor();
			break;
		case MONAT:
			sprungEinenMonatVor();
			break;
		default:
			sprungEinenTagVor();
			break;
		}
	}

	private void sprungEinenTagVor() {
		GregorianCalendar datum = new GregorianCalendar();
		datum.setTimeInMillis(getAnzeigeBis());
		datum.add(Calendar.DAY_OF_MONTH, 1);
		setTagesGrenzen(datum.getTimeInMillis());
	}

	private void sprungEineWocheVor() {
		GregorianCalendar datum = new GregorianCalendar();
		datum.setTimeInMillis(getAnzeigeBis());
		datum.add(Calendar.DAY_OF_MONTH, 7);
		setWochenGrenzen(datum.getTimeInMillis());
	}

	private void sprungEinenMonatVor() {
		GregorianCalendar datum = new GregorianCalendar();
		datum.setTimeInMillis(getAnzeigeBis());
		datum.add(Calendar.DAY_OF_MONTH, 15);
		setMonatGrenzen(datum.getTimeInMillis());
	}

	/**
	 * Setzt die Tagesgrenzen fuer den Tag, der die uebergebene Uhrzeit
	 * beeinhaltet
	 * 
	 * @param zeitPunkt
	 */

	private void setTagesGrenzen(long zeitPunkt) {
		this.berechung = new DatumUmrechung();
		long von = -1;
		long bis = -1;
		if (zeitPunkt != -1) {
			von = berechung.tagStart(zeitPunkt);
			bis = berechung.tagEnde(zeitPunkt);
		} else {
			von = berechung.tagStart(System.currentTimeMillis());
			bis = berechung.tagEnde(System.currentTimeMillis());
		}
		setAnzeigeVonBis(von, bis);
	}

	private void setWochenGrenzen(long zeitPunkt) {
		this.berechung = new DatumUmrechung();
		long von = -1;
		long bis = -1;
		if (zeitPunkt != -1) {
			von = berechung.wochenStart(zeitPunkt);
			bis = berechung.wochenEnde(zeitPunkt);
		} else {
			von = berechung.wochenStart(System.currentTimeMillis());
			bis = berechung.wochenEnde(System.currentTimeMillis());
		}
		setAnzeigeVonBis(von, bis);
	}

	private void setMonatGrenzen(long zeitPunkt) {
		this.berechung = new DatumUmrechung();
		long von = -1;
		long bis = -1;
		if (zeitPunkt != -1) {
			von = berechung.monatStart(zeitPunkt);
			bis = berechung.monatEnde(zeitPunkt);
		} else {
			von = berechung.monatStart(System.currentTimeMillis());
			bis = berechung.monatEnde(System.currentTimeMillis());
		}
		setAnzeigeVonBis(von, bis);
	}

	/**
	 * Erstellt einen Vector<Vector<Object>> mit den Buchungsdaten, die gerade
	 * angezeigt werden
	 */
	private void buchungsEintraegeVectorErstellen() {
		ArrayList<HT_BuchungsEintrag> listeBuchungsEintraege = new HT_DB_BuchungsEintragLesen()
				.getBuchungsEintraege(getStrukturObjektFokusID(), getAnzeigeVon(), getAnzeigeBis());

		int listenGroesse = listeBuchungsEintraege.size();
		buchungsDaten = new Vector<Vector<Object>>(listenGroesse, 1);
		long zeitInsgesamt = 0;
		for (int listenIndex = 0; listenIndex < listenGroesse; listenIndex++) {
			HT_BuchungsEintrag buchungsObjekt = listeBuchungsEintraege.get(listenIndex);
			long startZeit = buchungsObjekt.getStartzeit();
			long endZeit = buchungsObjekt.getEndzeit();
			long dauer = endZeit - startZeit;
			zeitInsgesamt += dauer;
			TempBuchungsEintrag buchung = new TempBuchungsEintrag(buchungsObjekt);
			Vector<Object> zeile = new Vector<Object>();
			zeile.add(buchung);
			buchungsDaten.add(zeile);
		}
		this.zeitInsgesamt = zeitInsgesamt;

	}

	public void beobachterBenachrichtigen() {
		MeineBuchungsListeJScrollPane.tableRechtsKlickSchliessen();
		buchungsEintraegeVectorErstellen();
		setChanged();
		notifyObservers(this);
	}

	public void setTable(MeinJTable meinJTable) {
		this.table = meinJTable;

	}

	public HT_MeineZeilenPruefung getZeilenPruefung() {
		return zeilenPruefung;
	}
}
