package zusatzKlassen;

/**
 * Exception-Klasse. Uebergabe von Fehlermeldung moeglich.
 * 
 * @author jt
 * 
 */
public class MeineException extends Exception {

	private static final long serialVersionUID = 1L;
	String fehlerMeldung = "";

	public MeineException(String fehlerMeldung) {
		this.fehlerMeldung = fehlerMeldung;
	}

	public String getFehlerMeldung() {
		return fehlerMeldung;
	}
}
