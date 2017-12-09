package gui.verwaltung;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JPanel;

import gui.erweiterteGuiKlassen.HT_JFrame;
import gui.tray.SystemTrayIcon;
import gui.verwaltung.baumKlassen.MeinJTree;
import gui.verwaltung.baumKlassen.MeinJTreeBefuellenJScrollPane;
import gui.verwaltung.datumKlassen.MeinBuchungsDatumJPanel;
import gui.verwaltung.tabelleKlassen.MeineBuchungsListeJScrollPane;
import gui.verwaltung.visualisierungKlassen.MeineTorteOderTabelle;
import gui.verwaltung.zusammenfassungsKlassen.ZusammenFassungJPanel;

/**
 * Fenster zur Bearbeitung und Analyse der Buchungseintraege.
 * 
 * @author JT
 * 
 */
public class ProjekteVerwaltenFenster extends HT_JFrame {

	private static final long serialVersionUID = 1L;
	private static AktuellerFokus fokus = new AktuellerFokus();
	private MeinJTreeBefuellenJScrollPane strukturObjekteBaum;
	private MeinBuchungsDatumJPanel buchungsDatumWahl;
	private MeineTorteOderTabelle torteTabelle;
	private ZusammenFassungJPanel buchungsZusammenfassung;
	private JPanel grundPanel;

	public ProjekteVerwaltenFenster() {

		this.setTitle("Projektverwaltung");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/icons/UhrGruen24x24.png")));
		this.strukturObjekteBaum = new MeinJTreeBefuellenJScrollPane();
		this.torteTabelle = new MeineTorteOderTabelle(this);
		this.buchungsDatumWahl = new MeinBuchungsDatumJPanel(this);
		this.buchungsZusammenfassung = new ZusammenFassungJPanel();
		komponentenHinzu();
		;
		this.mittigPositionieren();
		this.setVisible(true);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				fensterSchliessen();
			}
		});
	}

	private void komponentenHinzu() {
		grundPanel = (JPanel) this.getContentPane();
		grundPanel.setLayout(new GridBagLayout());
		grundPanel.add(strukturObjekteBaum, new GridBagConstraints(0, 0, 1, 3, 1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0));
		grundPanel.add(buchungsDatumWahl, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0));
		grundPanel.add(torteTabelle, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0));
		grundPanel.add(buchungsZusammenfassung, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0));
		pack();
	}

	private void fensterSchliessen() {
		fokus.getZeilenPruefung().finalePruefungStarten();
		MeineBuchungsListeJScrollPane.tableRechtsKlickSchliessen();
		SystemTrayIcon.aktualisieren(false);
		MeinJTree.baumRechtsKlickSchliessen();
		dispose();

	}

	public static AktuellerFokus getAktuellerFokus() {
		return fokus;
	}

	public static void setAktuellerFokus(AktuellerFokus aktuellerFokus) {
		ProjekteVerwaltenFenster.fokus = aktuellerFokus;
	}

}
