package gui.verwaltung.export;

import gui.verwaltung.AktuellerFokus;
import gui.verwaltung.ProjekteVerwaltenFenster;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MeinExportPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private AktuellerFokus fokus;
	private JButton exportButton;

	public MeinExportPanel() {
		this.fokus = ProjekteVerwaltenFenster.getAktuellerFokus();
		fokus.addObserver(this);
		this.exportButtonErzeugen();
		komponentenSetzen();
	}

	private void exportButtonErzeugen() {
		exportButton = new JButton("Auswahl exportieren");
		exportButton.setIcon(new ImageIcon(getClass().getResource("/icons/Auswertung16x16.png")));
		exportButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new SpeicherOrtAuswahl();

			}
		});
	}

	private void komponentenSetzen() {
		this.setLayout(new GridBagLayout());
		this.add(exportButton, new GridBagConstraints(0, 0, 1, 3, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
	}

	@Override
	public void update(Observable o, Object arg) {
		if (fokus.getBuchungsDaten().isEmpty()) {
			exportButton.setEnabled(false);
		} else {
			exportButton.setEnabled(true);
		}
	}
}
