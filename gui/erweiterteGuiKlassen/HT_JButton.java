package gui.erweiterteGuiKlassen;

import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTextField;

import basis.HT_GlobaleVariablen;

public class HT_JButton extends JButton {

	private static final long serialVersionUID = 1L;
	
	private HT_JDialog nachfolgeDialog;
	private boolean dialogIstRechts;
	private boolean obersterButton;
	private boolean untersterButton;
	private HT_JButton dieserButton;
	/*
	 * Liste der Dialoge, die durch diesen Button aufgerufen werden koennen (z.B. Rechts- und LinksKlick)
	 */
	private ArrayList<HT_JDialog> buttonDialoge;
	private HT_JDialog aufrufenderDialog = null;	
	/**
	 * aufrufendeKlasse verschwindet, wenn die Maus in der entsprechenden Richtung verlaesst und der wert auf TRUE gesetzt ist
	 * @param text
	 * @param aufrufenderDialog
	 */
	
	public HT_JButton(HT_JDialog aufrufenderDialog, String text){
		super(text);
		this.aufrufenderDialog = aufrufenderDialog;
		init();
	}	
	
	public HT_JButton(HT_JDialog aufrufenderDialog) {
		super();
		this.aufrufenderDialog = aufrufenderDialog;
		init();
	}
		
	private void init() {
		this.nachfolgeDialog = null;
		this.dialogIstRechts = true;
		this.obersterButton = false;
		this.untersterButton = false;
		this.dieserButton = this;
		this.setFocusPainted(false);
		this.setHorizontalAlignment(JTextField.LEFT);
		this.setBackground(HT_GlobaleVariablen.KNOPFGRAU);
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				if (obersterButton){
					if (getLocationOnScreen().y + 1 > e.getYOnScreen()){
						aufrufenderDialog.alleSchliessen();
						return;
					}
				} 
				if (untersterButton){
					if((getLocationOnScreen().y + getSize().height - 1) < e.getYOnScreen()){
						aufrufenderDialog.alleSchliessen();
						return;
					}
				}
				
				boolean darfGeschlossenWerden = true;
				if((getLocationOnScreen().x + getSize().width - 1) < e.getXOnScreen()){
					if (dialogIstRechts){
						darfGeschlossenWerden = false;	
					} else{
						System.out.println("wech");
						aufrufenderDialog.alleSchliessen();
						return;
					}
				}
				if (getLocationOnScreen().x + 1 > e.getXOnScreen()){
					if (!dialogIstRechts){
						darfGeschlossenWerden = false;
					} else{
						System.out.println("wech2");
						aufrufenderDialog.alleSchliessen();
						return;
					}
				}
				if (darfGeschlossenWerden){
					aufrufenderDialog.nachfolgeDialogschliessen();
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				aufrufenderDialog.nachfolgeDialogschliessen();
				aufrufenderDialog.setMausButton(dieserButton);
				System.out.println("setMausb");
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
	}
	
	/**
	 * Positioniert das Fenster links neben dem Button, im Fall, dass es sonst ueber den Bildschirmrand ragt.
	 * @param meinProjektWahlJButton
	 * @return true = rechts-Positionierung , false = links-Positionierung
	 */
	private void positionierenAnButton() {
		int monitorRechts = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int ausdehnungRechts = this.getLocationOnScreen().x + this.getWidth() + nachfolgeDialog.getWidth();
		int y = this.getLocationOnScreen().y;
		
		int montitorUnten = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		int ausdehnungUnten = this.getLocationOnScreen().y + this.getHeight() + nachfolgeDialog.getHeight();
		int x = this.getLocationOnScreen().x;
		
		if (monitorRechts >= ausdehnungRechts){
			x = this.getLocationOnScreen().x + this.getWidth();
			dialogIstRechts = true;
		} else {
			x = this.getLocationOnScreen().x - nachfolgeDialog.getWidth();
			dialogIstRechts = false;
		}
		if (montitorUnten < ausdehnungUnten){
			y = y - (ausdehnungUnten - montitorUnten);
		}
		nachfolgeDialog.setLocation(x, y);
	}


	public HT_JDialog getAufrufenderDialog() {
		return aufrufenderDialog;
	}
	
	public ArrayList<HT_JDialog> getButtonDialoge() {
		return buttonDialoge;
	}

	
	public void addButtonDialog(HT_JDialog buttonDialog) {
		this.buttonDialoge.add(buttonDialog);
	}

	public void setNachfolgeDialog(HT_JDialog nachfolgeDialog) {
		this.nachfolgeDialog = nachfolgeDialog;
		if (this.nachfolgeDialog != null){
			positionierenAnButton();
		}
	}

	public HT_JDialog getNachfolgeDialog() {
		return nachfolgeDialog;
	}

	public void setObersterButton(boolean obersterButton) {
		this.obersterButton = obersterButton;
	}

//	public boolean isObersterButton() {
//		return obersterButton;
//	}

	public void setUntersterButton(boolean untersterButton) {
		this.untersterButton = untersterButton;
	}

//	public boolean isUntersterButton() {
//		return untersterButton;
//	}
}
