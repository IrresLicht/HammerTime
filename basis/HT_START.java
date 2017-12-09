package basis;

import javax.swing.JOptionPane;

/**
 * Programmanfang (- und Ende)
 * @author JT
 *
 */

public class HT_START {

	public static void main(String[] args) {
		try {
			if (!new HT_InstanzUeberpruefung().laeuftBereits()){
				new HT_ProgrammStart();
			} else{
				JOptionPane.showMessageDialog(null, HT_GlobaleVariablen.PROGRAMMNAME+" läuft bereits.", "Information",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Probleme bei Programmstart (main)", "Fehlermeldung",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}
