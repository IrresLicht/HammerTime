package zusatzKlassen;

public class ZeitToString {
	
	long dauer;
	
	/**
	 * Berechnet die Differenz zweier long-Zeiten (seit 1970) und gibt Sie als String aus
	 * @param startZeit
	 * @param endZeit
	 */
	
	public ZeitToString(long startZeit, long endZeit){
		this.dauer = endZeit - startZeit;
	}
	
	/**
	 * Gibt die Dauer der long-Zeit als String aus
	 * @param dauer
	 */
	public ZeitToString (long dauer){
		this.dauer = dauer;
	}
	
	public String toString(){
		long sekunden = ((dauer) / 1000);
		long division = sekunden / 3600;
		long stunden = (long) Math.floor(division);
		/*
		 * restlichen Sekunden
		 */
		sekunden -= (stunden*3600);
		division = sekunden / 60;
		long minuten = (long) Math.floor(division);
		/*
		 * restlichen Sekunden
		 */
		sekunden -= (minuten*60);
		
		String stundenString;
		String minutenString;
		String sekundenString;
		
		if (stunden < 10) {
			stundenString = "0" + String.valueOf(stunden);
		} else {
			stundenString = String.valueOf(stunden);
		}
		if (minuten < 10) {
			minutenString = "0" + String.valueOf(minuten);
		} else {
			minutenString = String.valueOf(minuten);
		}
		if (sekunden < 10) {
			sekundenString = "0" + String.valueOf(sekunden);
		} else {
			sekundenString = String.valueOf(sekunden);
		}
		return(stundenString + ":" + minutenString + ":" + sekundenString);
	}
}
