package gui.tray;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.util.ArrayList;

import buchungsEintrag.HT_BuchungsEintrag;
import datenbank.buchungsEintrag.HT_DB_BuchungsEintragLesen;

import status.HT_Status;
import zusatzKlassen.DatumUmrechung;
import zusatzKlassen.ZeitToString;

public class HT_IconToolTipUpdate {
	private HT_Status status;
	private TrayIcon trayIcon;
	private boolean buchungAktiv;
	
	public HT_IconToolTipUpdate(HT_Status status, TrayIcon myTrayIcon){
		this.status = status;
		this.trayIcon = myTrayIcon;
		this.buchungAktiv = status.isAktiv();
		
		setzeIcon();
		setzeToolTip();
	}

	private void setzeIcon() {
		Image icon = null;
		if (buchungAktiv){
			icon = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/icons/UhrGruen24x24.png"));
		} else{
			icon = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/icons/UhrRot24x24.png"));
		}
		this.trayIcon.setImage(icon);
	}
	
	private void setzeToolTip() {
		String dauerHeuteInsgesamt = new ZeitToString(dauerHeuteInsgesamt()).toString();
		if (!buchungAktiv){
			trayIcon.setToolTip("Keine Buchung aktiv \nHeute insgesamt: "+dauerHeuteInsgesamt);
		} else{ 
			String dauerAktuelleBuchung = new ZeitToString(status.getAktuellerBuchungsEintrag().getZeitInsgesamt()).toString();
			String dauerHeuteProjekt = new ZeitToString(dauerHeuteProjekt()).toString();
			trayIcon.setToolTip("Projekt: "+status.getAktuellesStrukturObjekt().getPfadVonWurzelalsStringReduziert()+
					"\nAktuell: "+ dauerAktuelleBuchung+
					"\nHeute: "+dauerHeuteProjekt+
					"\nHeute insgesamt: "+dauerHeuteInsgesamt);
		}
		
	}

	private long dauerHeuteProjekt() {
		DatumUmrechung berechung = new DatumUmrechung();
		long von = berechung.tagStart(System.currentTimeMillis());
		long bis = berechung.tagEnde(System.currentTimeMillis());
		ArrayList<HT_BuchungsEintrag> listeBuchungsEintraege = new HT_DB_BuchungsEintragLesen().getBuchungsEintraege(status.getAktuellesStrukturObjekt().getID(), von, bis);
		int listenGroesse = listeBuchungsEintraege.size();
		long heuteInsgesamtLong  = 0;
		for(int listenIndex = 0; listenIndex < listenGroesse; listenIndex++){
			HT_BuchungsEintrag buchungsObjekt = listeBuchungsEintraege.get(listenIndex);
			long startZeit = buchungsObjekt.getStartzeit();
			long endZeit = buchungsObjekt.getEndzeit();
			long dauer = endZeit - startZeit;
			heuteInsgesamtLong += dauer;
		}
		return heuteInsgesamtLong;
	}

	private long dauerHeuteInsgesamt() {
		DatumUmrechung berechung = new DatumUmrechung();
		long von = berechung.tagStart(System.currentTimeMillis());
		long bis = berechung.tagEnde(System.currentTimeMillis());
		ArrayList<HT_BuchungsEintrag> listeBuchungsEintraege = new HT_DB_BuchungsEintragLesen().getBuchungsEintraege(1, von, bis);
		int listenGroesse = listeBuchungsEintraege.size();
		long heuteInsgesamtLong  = 0;
		for(int listenIndex = 0; listenIndex < listenGroesse; listenIndex++){
			HT_BuchungsEintrag buchungsObjekt = listeBuchungsEintraege.get(listenIndex);
			long startZeit = buchungsObjekt.getStartzeit();
			long endZeit = buchungsObjekt.getEndzeit();
			long dauer = endZeit - startZeit;
			heuteInsgesamtLong += dauer;
		}
		return heuteInsgesamtLong;
	}
}
