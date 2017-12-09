package zusatzKlassen;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Klasse fuer Datumsumrechungen
 * @author JT
 *
 */
public class DatumUmrechung {
	
	/**
	 * Ermittelt den Zeitpunkt des Beginn des Tages, der den uebergebenen Zeitpunkt beeinhaltet
	 * @param zeitPunkt
	 * @return
	 */
	public long tagStart(long zeitPunkt) {
		Calendar datum = Calendar.getInstance();
		datum.setTimeInMillis(zeitPunkt);
		datum.set(Calendar.HOUR_OF_DAY, 0);
		datum.set(Calendar.MINUTE, 0);
		datum.set(Calendar.SECOND, 0);
		datum.set(Calendar.MILLISECOND, 0);
		return datum.getTimeInMillis();
	}

	/**
	 * Ermittelt den Zeitpunkt des Ende des Tages, der den uebergebenen Zeitpunkt beeinhaltet
	 * @param zeitPunkt
	 * @return
	 */
	public long tagEnde(long zeitPunkt) {
		Calendar datum = Calendar.getInstance();
		datum.setTimeInMillis(zeitPunkt);
		datum.set(Calendar.HOUR_OF_DAY, 23);
		datum.set(Calendar.MINUTE, 59);
		datum.set(Calendar.SECOND, 59);
		datum.set(Calendar.MILLISECOND,999);
		return datum.getTimeInMillis();
	}
	
	public long wochenStart(long zeitPunkt) {
		Calendar datum = Calendar.getInstance();
		datum.setTimeInMillis(zeitPunkt);
		datum.set(Calendar.DAY_OF_WEEK, 2);
		datum.set(Calendar.HOUR_OF_DAY, 0);
		datum.set(Calendar.MINUTE, 0);
		datum.set(Calendar.SECOND, 0);
		datum.set(Calendar.MILLISECOND, 0);
		return datum.getTimeInMillis();
	}
	
	public long wochenEnde(long zeitPunkt) {
		Calendar datum = Calendar.getInstance();
		datum.setTimeInMillis(zeitPunkt);
		datum.set(Calendar.DAY_OF_WEEK, 1);
		datum.set(Calendar.HOUR_OF_DAY, 23);
		datum.set(Calendar.MINUTE, 59);
		datum.set(Calendar.SECOND, 59);
		datum.set(Calendar.MILLISECOND,999);
		return datum.getTimeInMillis();
	}
	
	public long monatStart(long zeitPunkt) {
		Calendar datum = Calendar.getInstance();
		datum.setTimeInMillis(zeitPunkt);
		datum.set(Calendar.DAY_OF_MONTH, 1);
		datum.set(Calendar.HOUR_OF_DAY, 0);
		datum.set(Calendar.MINUTE, 0);
		datum.set(Calendar.SECOND, 0);
		datum.set(Calendar.MILLISECOND, 0);
		return datum.getTimeInMillis();
	}
	
	public long monatEnde(long zeitPunkt) {
		GregorianCalendar tempCalendar = new GregorianCalendar();
		tempCalendar.setTimeInMillis(zeitPunkt);
	    int letzterTagImMonat = tempCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		Calendar datum = Calendar.getInstance();
		datum.setTimeInMillis(zeitPunkt);
		datum.set(Calendar.DAY_OF_MONTH, letzterTagImMonat);
		datum.set(Calendar.HOUR_OF_DAY, 23);
		datum.set(Calendar.MINUTE, 59);
		datum.set(Calendar.SECOND, 59);
		datum.set(Calendar.MILLISECOND,999);
		return datum.getTimeInMillis();
	}
	
	/**
	 * Ermittelt die gegenwartige Uhrzeit fuer das Datum der uebergebenen Uhrzeit
	 * @param uhrZeit
	 * @return
	 */
	public long jetzigeUhrzeitAm(long uhrZeit){
		long millisHeuteVergangen = System.currentTimeMillis() - tagStart(System.currentTimeMillis());
		long angezeigterTagStart = tagStart(uhrZeit);
		return millisHeuteVergangen + angezeigterTagStart;
	}
}
