package gui.fenster;

import icons.HT_ICONS;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.erweiterteGuiKlassen.HT_JWindow;
import gui.tray.SystemTrayIcon;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import basis.HT_GlobaleVariablen;
import basis.HT_ProgrammStart;

/**
 * Fenster zur Bestaetigung des Programmendes
 * 
 * @author JT
 * 
 */
public class HT_Window_ProgrammBeenden extends HT_JWindow {

	private static final long serialVersionUID = 1L;
	private JLabel rueckfrageLabel;
	private JButton bestaetigenKnopf;
	private JButton abbrechenKnopf;
	private JPanel grundPanel;

	public HT_Window_ProgrammBeenden() {
		SystemTrayIcon.setTrayIconGesperrt(true);
		this.rueckfrageLabel = new JLabel("\"" + HT_GlobaleVariablen.PROGRAMMNAME + "\" wirkich beenden?");
		this.bestaetigenKnopf = new JButton("Ja");
		this.bestaetigenKnopf.setIcon(new ImageIcon(getClass().getResource(HT_ICONS.OK)));
		this.bestaetigenKnopf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				programmBeenden();
			}
		});
		this.abbrechenKnopf = new JButton("Nein");
		this.abbrechenKnopf.setIcon(new ImageIcon(getClass().getResource(HT_ICONS.ABBRUCH)));
		this.abbrechenKnopf.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				programmWeiterfuehren();
			}
		});
		this.bestaetigenKnopf.setPreferredSize(new Dimension(95, 25));
		this.abbrechenKnopf.setPreferredSize(new Dimension(95, 25));

		this.grundPanel = (JPanel) this.getContentPane();
		this.grundPanel.setBorder(HT_GlobaleVariablen.MYLINEBORDER);
		this.grundPanel.setLayout(new GridBagLayout());
		this.grundPanel.add(rueckfrageLabel, new GridBagConstraints(0, 0, 2, 1, 1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		this.grundPanel.add(bestaetigenKnopf, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		this.grundPanel.add(abbrechenKnopf, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		pack();
		this.mittigPositionieren();

	}

	protected void programmWeiterfuehren() {
		SystemTrayIcon.setTrayIconGesperrt(false);
		dispose();
	}

	protected void programmBeenden() {
		try {
			Frame[] offeneFenster = Frame.getFrames();
			for ( Frame fenster : offeneFenster){
				fenster.dispose();
			}
			HT_ProgrammStart.getStatus().setAktiv(false);
			SystemTrayIcon.getTrayLeiste().remove(SystemTrayIcon.getMyTrayIcon());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Probleme beim Schliessen der Frames! (" + this.getClass().getName()
					+ ")", "Fehlermeldung", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}
