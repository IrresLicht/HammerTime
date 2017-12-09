package status;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import basis.HT_GlobaleVariablen;
import basis.HT_ProgrammStart;

public class HT_StatusAendern {

	static File aktuelleStatusDatei = new File(HT_GlobaleVariablen.STATUSDATEI);

	public HT_StatusAendern() {
	}

	public HT_Status aktuellenStatusLaden() {
		HT_Status geladenerStatus = null;
		try {
			FileInputStream fis = new FileInputStream(aktuelleStatusDatei);
			ObjectInputStream ois = new ObjectInputStream(fis);
			geladenerStatus = (HT_Status) ois.readObject();
			fis.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Probleme beim Laden des Status!", "Fehlermeldung",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return geladenerStatus;
	}

	public void aktuellenStatusSpeichern() {
		HT_Status zuSpeichernderStatus = HT_ProgrammStart.getStatus();
		try {
			FileOutputStream fos = new FileOutputStream(aktuelleStatusDatei);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(zuSpeichernderStatus);
			fos.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Probleme beim Speichern des Status!", "Fehlermeldung",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}
