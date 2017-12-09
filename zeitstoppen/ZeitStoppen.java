package zeitstoppen;

import java.io.Serializable;
import java.util.Observable;

import javax.swing.JOptionPane;

import status.HT_Status;


import basis.HT_ProgrammStart;
import buchungsEintrag.HT_BuchungsEintrag;

public class ZeitStoppen extends Observable implements Serializable {
	
	private static final long serialVersionUID = 1L;;
	private transient ZeitStoppThread aktuellerThread;
	
	public ZeitStoppen(){
	}
	
	public void threadStarten(){
		if (aktuellerThread != null){
			aktuellerThread.kill();
		}
		aktuellerThread = new ZeitStoppThread();
		aktuellerThread.start();
	}
	
	public void threadStoppen(){
		if (aktuellerThread != null){
			aktuellerThread.kill();
		}
	}
	
	
	private class ZeitStoppThread extends Thread{
		
		private HT_Status status;
		private HT_BuchungsEintrag aktuelleBuchung;
		private boolean kill = false;
		
		public void run() {
			this.status = HT_ProgrammStart.getStatus();
			this.aktuelleBuchung = status.getAktuellerBuchungsEintrag();
			while (HT_ProgrammStart.getStatus().isAktiv() && !kill) {
				try {
					aktuelleBuchung.setEndzeit(System.currentTimeMillis());
					setChanged();
			        notifyObservers(new HT_BuchungsEintrag(aktuelleBuchung));
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					JOptionPane.showMessageDialog(null, "Probleme mit ZeitStoppThread! ("
							+ this.getClass().getName() + ")", "Fehlermeldung", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		}
		
		private void kill(){
			this.kill = true;
		}
	}
}
