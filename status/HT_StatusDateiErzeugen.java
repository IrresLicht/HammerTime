package status;

import javax.swing.JOptionPane;

import basis.HT_ProgrammStart;

public class HT_StatusDateiErzeugen {
	public HT_StatusDateiErzeugen() {
		try {
			HT_ProgrammStart.setStatus(new HT_Status());
			new HT_StatusAendern().aktuellenStatusSpeichern();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Probleme beim Erzeugen der StatusDatei! ("
					+ this.getClass().getName() + ")", "Fehlermeldung", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}
