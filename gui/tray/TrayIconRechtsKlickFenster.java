package gui.tray;

import gui.erweiterteGuiKlassen.HT_JDialog;
import gui.erweiterteGuiKlassen.HT_JButton;
import gui.fenster.HT_Window_ProgrammBeenden;
//import gui.meineGuiKlassen.MeinJDialog;
import gui.verwaltung.ProjekteVerwaltenFenster;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import basis.HT_GlobaleVariablen;

/**
 * An das TrayIcon gebundene Fenster für sekundaere Funktionen des Programms
 * 
 * @author JT
 * 
 */
//public class TrayIconRechtsKlickFenster extends MeinJDialog {
public class TrayIconRechtsKlickFenster extends HT_JDialog {


	private static final long serialVersionUID = 1L;
	static ProjekteVerwaltenFenster verwaltungsFenster = null;
	private JPanel grundPanel;
	private HT_JButton projekteVerwalten;
	private HT_JButton beendenKnopf;

	public TrayIconRechtsKlickFenster() {
		beendenKnopf = new HT_JButton(this,HT_GlobaleVariablen.PROGRAMMNAME + " beenden");
		beendenKnopf.setIcon(new ImageIcon(getClass().getResource("/icons/Abbruch16x16.png")));
		beendenKnopf.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new HT_Window_ProgrammBeenden();
				dispose();
			}
		});
		
		projekteVerwalten = new HT_JButton(this,"Projekte verwalten");
		projekteVerwalten.setIcon(new ImageIcon(getClass().getResource("/icons/Hinzu16x16.png")));
		projekteVerwalten.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				projektVerwaltungNeuErstellen();
				dispose();
			}
		});
		
		grundPanel = (JPanel) this.getContentPane();
		grundPanel.setLayout(new GridBagLayout());
		grundPanel.add(projekteVerwalten, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		grundPanel.add(beendenKnopf, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		pack();
	}
	
	/**
	 * Erstellt eine neues Projektverwaltungsfenster
	 */
	public static void projektVerwaltungNeuErstellen(){
		if (verwaltungsFenster != null){
			verwaltungsFenster.dispose();
		}
		verwaltungsFenster = new ProjekteVerwaltenFenster();
	}
}
