package projektAuswahl;

import gui.erweiterteGuiKlassen.HT_JDialog;

public class HT_Dialog_ProjektWahl extends HT_JDialog  {
	
	private static final long serialVersionUID = 1L;
	private  HT_ProjektWahlJPanel projektWahlPanel;
	private int strukObjektID;

	public HT_Dialog_ProjektWahl(HT_ProjektWahlJButton aufrufenderButton, int strukturObjektID){
		super(aufrufenderButton);
		this.strukObjektID = strukturObjektID;
		this.projektWahlPanel = new HT_ProjektWahlJPanel(this, strukturObjektID);
		this.add(projektWahlPanel);
		pack();
	}

	public int getStrukObjektID() {
		return strukObjektID;
	}
}
