package basis;

import java.awt.Color;
import java.io.File;

import javax.swing.border.LineBorder;

/**
 * Interface für nicht aenderbare globale Variablen
 * 
 * @author JT
 * 
 */
public interface HT_GlobaleVariablen {
	public String PROGRAMMNAME = "HammerTime";
	public String PROGRAMMORDNER = System.getProperty("user.home") + File.separator + "."
			+ PROGRAMMNAME.toLowerCase();
	public String DATENBANKNAME = PROGRAMMORDNER + File.separator + PROGRAMMNAME.toLowerCase() + ".sqlite";
	public String TEMPLATEDATENBANK = "templateDatenbank.sqlite";
	public String STATUSDATEI = PROGRAMMORDNER + File.separator + "status";
	public String FILELOCK = "filelock.lock";
	public String FORMATDATUMZEIT = "dd-MM-yyyy (E) HH:mm:ss";
	public String FORMATDATUMZEITEXPORT = "dd-MM-yyyy HH:mm:ss (E)";
	
	public LineBorder MYLINEBORDER = new LineBorder(Color.GRAY, 1);
	
	public Color KNOPFGRAU = new Color(238,238,238);
	public Color KNOPFAKTIVBLAU = new Color(184, 207, 229);
	
	public int ANZAHLLETZTEPROJEKTE = 3;
}
