package gui.fenster;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class ErfolgreichFenster{
 
    
    private Timer timer;
	private static final long serialVersionUID = 1L;
    
    public ErfolgreichFenster(String nachricht){
        JOptionPane pane = new JOptionPane(nachricht,JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);
        final JDialog dialog = pane.createDialog("");
        dialog.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/OK16x16.png")));
        
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                timer.stop();
                dialog.dispose();
            }
        });
        timer.start();
        dialog.setVisible(true);
        timer.stop();   
    } 
}