package gui.fenster;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class WarungBestaetigungFenster extends JDialog{
	
	private static final long serialVersionUID = 1L;
	private JLabel rueckfrageLabel;
	private JButton bestaetigenKnopf;
	private JButton abbrechenKnopf;
	private JPanel grundPanel;
	private boolean bestaetigung = false;
	
	public WarungBestaetigungFenster(String warnung) {
		rueckfrageLabel = new JLabel(warnung);
		bestaetigenKnopf = new JButton("Ja");
		bestaetigenKnopf.setIcon(new ImageIcon(getClass().getResource("/icons/OK16x16.png")));
		bestaetigenKnopf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				bestaetigung = true;
				dispose();
			}
		});
		abbrechenKnopf = new JButton("Nein");
		abbrechenKnopf.setIcon(new ImageIcon(getClass().getResource("/icons/Abbruch16x16.png")));
		abbrechenKnopf.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				bestaetigung = false;
				dispose();
			}
		});
		bestaetigenKnopf.setPreferredSize(new Dimension(95, 25));
		abbrechenKnopf.setPreferredSize(new Dimension(95, 25));

		grundPanel = (JPanel) this.getContentPane();
		grundPanel.setLayout(new GridBagLayout());
		grundPanel.add(rueckfrageLabel, new GridBagConstraints(0, 0, 2, 1, 1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		grundPanel.add(bestaetigenKnopf, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		grundPanel.add(abbrechenKnopf, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
		pack();
		this.setModal(true);
		this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2, (Toolkit
				.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2);
		this.setAlwaysOnTop(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/UhrGruen16x16.png")));
		this.setVisible(true);
	}

	public boolean getBestaetigung(){
		return bestaetigung;
	}
}
