package datenbank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import basis.HT_GlobaleVariablen;

/**
 * Erzeugt eine Verbindung (Connection) und zur Datenbank liefert die Anweisungsschnittstelle (Statement).
 * @author JT
 *
 */

public class HT_DatenBankVerbindung {
	
	private static final long serialVersionUID = 1L;
	private Connection verbindung = null;
	private Statement anweisung = null;
	public HT_DatenBankVerbindung() {
		try {
			Class.forName("org.sqlite.JDBC");
			String url = "jdbc:sqlite:"+ HT_GlobaleVariablen.DATENBANKNAME;
			this.verbindung = DriverManager.getConnection(url);
			this.anweisung = this.verbindung.createStatement();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Probleme beim Erzeugen der Datenbankverbindung! ("
					+ this.getClass().getName() + ")", "Fehlermeldung", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	/**
	 * Schliesst die Anweisung (Statement) und die Verbindung (Connection) des Objekts,
	 * falls dies noch nicht geschehen ist.
	 */

	public void verbindungSchliessen(){
		try {
			if (this.anweisung != null){
				this.anweisung.close();
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Probleme beim Schliessen der Anweisung! ("
					+ this.getClass().getName() + ")", "Fehlermeldung", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		try {
			if(this.verbindung != null){
				this.verbindung.close();
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Probleme beim Schliessen der Datenbankverbindung! ("
					+ this.getClass().getName() + ")", "Fehlermeldung", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	public Statement getAnweisung() {
		return anweisung;
	}
}
