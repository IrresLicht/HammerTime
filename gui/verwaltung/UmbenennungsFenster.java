package gui.verwaltung;

import gui.tray.TrayIconRechtsKlickFenster;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import strukturObjekt.HT_StrukturObjekt;

import zusatzKlassen.NamenPruefung;

import basis.HT_GlobaleVariablen;

import datenbank.strukturObjekt.HT_DB_StrukturObjektSchreiben;

public class UmbenennungsFenster extends JFrame {

	private static final long serialVersionUID = 1L;
	JLabel orientierungsLabel;
	JTextField namenFeld;
	JButton ok;
	JButton abbruch;
	JPanel grundPanel;
	int id;
	String strukturObjektName;
	HT_StrukturObjekt gewaehltesStrukturObjekt;

	public UmbenennungsFenster(int strukturObjektID) {
		this.id = strukturObjektID;
		strukturObjektName = new HT_StrukturObjekt(strukturObjektID).getObjektName();
		setUndecorated(true);
		setAlwaysOnTop(true);
		setVisible(true);

		orientierungsLabel = new JLabel("\"" + strukturObjektName + "\" umbennennen in:");
		namenFeld = new JTextField(strukturObjektName);
		ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String neuerName = namenFeld.getText();
				if (!(neuerName.isEmpty())) {
					if (!new NamenPruefung().umbenennen_nameBereitsVorhanden(id, neuerName)) {
						new HT_DB_StrukturObjektSchreiben().setStrukturObjektName(id, neuerName);
						TrayIconRechtsKlickFenster.projektVerwaltungNeuErstellen();
						dispose();
					}
				}
			}
		});
		abbruch = new JButton("Abbruch");
		abbruch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		grundPanel = (JPanel) this.getContentPane();
		grundPanel.setBorder(HT_GlobaleVariablen.MYLINEBORDER);
		grundPanel.setLayout(new GridBagLayout());
		grundPanel.add(orientierungsLabel, new GridBagConstraints(0, 0, 2, 1, 1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		grundPanel.add(namenFeld, new GridBagConstraints(0, 1, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		grundPanel.add(ok, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		grundPanel.add(abbruch, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		pack();
	}
}
