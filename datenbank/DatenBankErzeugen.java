package datenbank;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import basis.HT_GlobaleVariablen;

/**
 * Klasse zum erzeugen der SQLite3-Datenbank. Neue Datenbank wird von einer
 * Template-Datenbank kopiert.
 * 
 * @author jt
 * 
 */
public class DatenBankErzeugen {

	public DatenBankErzeugen() {
		InputStream in = getClass().getResourceAsStream(HT_GlobaleVariablen.TEMPLATEDATENBANK);
		BufferedInputStream bufIn = new BufferedInputStream(in);
		BufferedOutputStream bufOut = null;

		try {
			bufOut = new BufferedOutputStream(new FileOutputStream(HT_GlobaleVariablen.DATENBANKNAME));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		byte[] inByte = new byte[4096];
		int count = -1;
		try {
			while ((count = bufIn.read(inByte)) != -1) {
				bufOut.write(inByte, 0, count);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			if (bufOut!= null){
				bufOut.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if (bufIn != null){
				bufIn.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
