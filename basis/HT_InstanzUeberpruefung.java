package basis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

import javax.swing.JOptionPane;

/**
 * Versucht die Datei filelock.lock zu sperren. Wenn diese bereits besperrt ist,
 * läuft das Programm bereits.
 * 
 * @author JT
 * 
 */
public class HT_InstanzUeberpruefung {
	private FileLock lock;

	public HT_InstanzUeberpruefung() {
		lock = null;
		/*
		 * Ueberpruefung, ob Programm-Ordner exisitiert, sonst anlegen
		 */
		File programmOrdnerDatei = new File(HT_GlobaleVariablen.PROGRAMMORDNER);
		if (!programmOrdnerDatei.isDirectory()) {
			try {
				programmOrdnerDatei.mkdir();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Probleme beim Erzeugen des Programmverzeichnisses! ("
						+ this.getClass().getName() + ")", "Fehlermeldung", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}

		/*
		 * Versuch die lockDatei zu sperren
		 */
		File lockDatei = new File(HT_GlobaleVariablen.PROGRAMMORDNER, HT_GlobaleVariablen.FILELOCK);
		try {
			RandomAccessFile raf = new RandomAccessFile(lockDatei, "rw");
			this.lock = raf.getChannel().tryLock();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gibt true zurueck, wenn der filelock aktiv ist, also bereits eine Instanz
	 * des Programms laeuft
	 * 
	 * @return
	 */
	public boolean laeuftBereits() {
		return (lock == null);
	}
}
