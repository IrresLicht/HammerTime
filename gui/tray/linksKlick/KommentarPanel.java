package gui.tray.linksKlick;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


import status.HT_Status;

import basis.HT_ProgrammStart;
import buchungsEintrag.HT_BuchungsEintrag;

/**
 * Klasse fuer die Bereitstellung des Kommentarfeldes
 * @author JT
 *
 */

public class KommentarPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private HT_Status status;
	private HT_BuchungsEintrag aktuelleBuchung;
	private String aktuellerKommentar;
	private String platzHalter = "Kommentar";
	private JTextArea kommentarFeld;
	private JScrollPane scrollPane;
	
	public KommentarPanel(){
		this.status = HT_ProgrammStart.getStatus();
		this.aktuelleBuchung = status.getAktuellerBuchungsEintrag();
		if (aktuelleBuchung != null){
			this.aktuellerKommentar = aktuelleBuchung.getKommentar();
		} else {
			this.aktuellerKommentar = null;
		}
		this.kommentarFeld = new JTextArea();
		this.kommentarFeld.setWrapStyleWord(true);
		this.kommentarFeld.setLineWrap(true); 	
		this.kommentarFeld.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				String feldText = kommentarFeld.getText();
				if (!feldText.equals(aktuellerKommentar) && !feldText.equals(platzHalter)){
					aktuelleBuchung.setKommentar(feldText);
				}
				if (feldText.isEmpty()){
					platzHalterSetzen();
				}
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				editierFontSetzen();
				kommentarFeld.setSize(500, 500);
				String feldText = kommentarFeld.getText();
				if (feldText.equals(platzHalter)){
					kommentarFeld.setText("");
				}
			}
		});
		this.scrollPane = new JScrollPane(kommentarFeld);
		this.scrollPane.setPreferredSize(new Dimension(kommentarFeld.getWidth(),kommentarFeld.getFont().getSize()*3));
		aktualisieren();
		komponentenSetzen();
	}

	private void aktualisieren() {
		if (aktuelleBuchung != null){
			aktuellerKommentar = aktuelleBuchung.getKommentar();
		}
		if (aktuellerKommentar == null){
			platzHalterSetzen();
		}else{
			editierFontSetzen();
			kommentarFeld.setText(aktuellerKommentar);
		}
	}

	private void platzHalterSetzen() {
		kommentarFeld.setFont(new Font("Dialog.plain", Font.ITALIC, kommentarFeld.getFont().getSize()));
		kommentarFeld.setText(platzHalter);
	}
	
	private void editierFontSetzen() {
		kommentarFeld.setFont(new Font("Dialog.plain", Font.PLAIN, kommentarFeld.getFont().getSize()));
	}

	private void komponentenSetzen() {
		this.setLayout(new GridBagLayout());
		this.add(scrollPane, new GridBagConstraints(0, 0, 2, 1, 1.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
	}

}
