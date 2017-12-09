package gui.verwaltung.baumKlassen;

import gui.fenster.WarungBestaetigungFenster;
import gui.verwaltung.ProjekteVerwaltenFenster;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import strukturObjekt.HT_StrukturObjekt;

import buchungsEintrag.HT_BuchungsEintrag;
import buchungsEintrag.TempBuchungsEintrag;

import zusatzKlassen.NamenPruefung;

import datenbank.strukturObjekt.HT_DB_StrukturObjektSchreiben;

public class MeinJTree extends JTree implements DragSourceListener, DropTargetListener, DragGestureListener {

	private static final long serialVersionUID = 1L;
	private static HT_BaumRechtsKlick rechtsKlickJPopUp;
	private static DataFlavor datenSorte;
	static {
		try {
			setDatenSorte(new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	private static DataFlavor[] unterstuetzteDatenSorten = { getDatenSorte() };
	private DragSource dragQuelle;
	private DropTarget dropZiel;
	private DefaultMutableTreeNode dropZielKnoten = null;
	private DefaultMutableTreeNode draggedQuellKnoten = null;

	public MeinJTree(final DefaultMutableTreeNode root) {

		dragQuelle = new DragSource();
		dragQuelle.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_MOVE, this);
		setDropZiel(new DropTarget(this, this));
		this.setModel(new DefaultTreeModel(root));
		this.setCellRenderer(new MeinTreeCellRenderer(this));
		this.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode gewaelterKnoten = (DefaultMutableTreeNode) getLastSelectedPathComponent();
				/*
				 * StrukturObjekt wird gesetzt
				 */
				if (gewaelterKnoten != null) {
					int aktuellesStrukturObjektID = ((HT_StrukturObjekt) gewaelterKnoten.getUserObject())
							.getID();
					ProjekteVerwaltenFenster.getAktuellerFokus().setStrukturObjektFokusID(
							aktuellesStrukturObjektID);
				}
			}
		});
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				switch (e.getButton()) {
				/*
				 *  Linksklick
				 */
				case MouseEvent.BUTTON1:
					baumRechtsKlickSchliessen();
					break;
				/*
				 * Rechtsklick
				 */
				case MouseEvent.BUTTON3:
					TreePath klickPfad = getPathForLocation(e.getX(), e.getY());
					setSelectionPath(klickPfad);
					DefaultMutableTreeNode gewaelterKnoten = (DefaultMutableTreeNode) getLastSelectedPathComponent();
					if (gewaelterKnoten != null) {
						int strukturObjektID = ((HT_StrukturObjekt) gewaelterKnoten.getUserObject())
								.getID();
						baumRechtsKlickSchliessen();
						rechtsKlickJPopUp = new HT_BaumRechtsKlick(strukturObjektID);
						rechtsKlickJPopUp.setVisible(true);
						rechtsKlickJPopUp.setLocation(e.getLocationOnScreen().x, e.getLocationOnScreen().y);
					}
					break;
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}
		});
		expandieren();
	}

	@Override
	public void dragDropEnd(DragSourceDropEvent dsde) {
		setDropZiel(null);
		draggedQuellKnoten = null;
		repaint();
	}

	@Override
	public void dragEnter(DragSourceDragEvent dsde) {
	}

	@Override
	public void dragExit(DragSourceEvent dse) {
	}

	@Override
	public void dragOver(DragSourceDragEvent dsde) {
	}

	@Override
	public void dropActionChanged(DragSourceDragEvent dsde) {
	}

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		try {
			Object objekt = dtde.getTransferable().getTransferData(dtde.getCurrentDataFlavors()[0]);
			if (objekt.getClass() == TempBuchungsEintrag.class) { 
				/*
				 *  Drag kommt aus der Tabelle
				 */
				this.setCellRenderer(new MeinTreeCellRendererBuchung(this));
			} else {
				/*
				 * Drag kommt aus dem Baum
				 */
				this.setCellRenderer(new MeinTreeCellRendererStruktur(this));
			}
		} catch (UnsupportedFlavorException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		dtde.acceptDrag(DnDConstants.ACTION_MOVE);
	}

	@Override
	public void dragExit(DropTargetEvent dte) {
	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		Point dragPunkt = dtde.getLocation();
		TreePath path = getPathForLocation(dragPunkt.x, dragPunkt.y);
		if (path == null) {
			setDropZielKnoten(null);
		} else {
			setDropZielKnoten((DefaultMutableTreeNode) path.getLastPathComponent());
		}
		repaint();
	}

	@Override
	public void drop(DropTargetDropEvent dtde) {
		try {
			Object objekt = dtde.getTransferable().getTransferData(dtde.getCurrentDataFlavors()[0]);
			/*
			 * Drag kommt aus der Tabelle
			 */
			if (objekt.getClass() == TempBuchungsEintrag.class) { 
				dndAusTabelle(dtde);
			/*
			 * Drag kommt aus dem Baum
			 */
			} else { 
				dndStrukturObjekt(dtde);
			}
		} catch (UnsupportedFlavorException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		this.setCellRenderer(new MeinTreeCellRenderer(this));
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
	}

	@Override
	public void dragGestureRecognized(DragGestureEvent dge) {
		Point clickPunkt = dge.getDragOrigin();

		TreePath pfad = getPathForLocation(clickPunkt.x, clickPunkt.y);
		if (pfad == null) {
			return;
		}
		draggedQuellKnoten = (DefaultMutableTreeNode) pfad.getLastPathComponent();
		Transferable transfer = new MeinTransferable(draggedQuellKnoten);
		dragQuelle.startDrag(dge, Cursor.getDefaultCursor(), transfer, this);
	}

	private void dndStrukturObjekt(DropTargetDropEvent dtde) {
		if (!darfVerschobenWerden(getDropZielKnoten(), draggedQuellKnoten)) {
			return;
		}
		Point dropPunkt = dtde.getLocation();
		TreePath pfad = getPathForLocation(dropPunkt.x, dropPunkt.y);
		WarungBestaetigungFenster warnFenster = new WarungBestaetigungFenster("Wirklich verschieben?");
		if (warnFenster.getBestaetigung()) {
			DefaultMutableTreeNode zielKnoten = (DefaultMutableTreeNode) pfad.getLastPathComponent();
			MutableTreeNode bewegterKnoten = null;
			int db_zielKnotenID = -1;
			int db_bewegterKnotenID = -1;
			boolean dropped = false;
			try {
				dtde.acceptDrop(DnDConstants.ACTION_MOVE);
				Object droppedObjekt = dtde.getTransferable().getTransferData(getDatenSorte());
				if (droppedObjekt instanceof MutableTreeNode) {
					bewegterKnoten = (MutableTreeNode) droppedObjekt;
					((DefaultTreeModel) getModel()).removeNodeFromParent(bewegterKnoten);
				} else {
					bewegterKnoten = new DefaultMutableTreeNode(droppedObjekt);
				} 
				db_bewegterKnotenID = ((HT_StrukturObjekt) ((DefaultMutableTreeNode) bewegterKnoten)
						.getUserObject()).getID();
				if (zielKnoten.isLeaf() && !((HT_StrukturObjekt) zielKnoten.getUserObject()).getHatNachfolger()) {
					DefaultMutableTreeNode parent = (DefaultMutableTreeNode) zielKnoten.getParent();
					int index = parent.getIndex(zielKnoten);
					((DefaultTreeModel) getModel()).insertNodeInto(bewegterKnoten, parent, index);
					db_zielKnotenID = ((HT_StrukturObjekt) parent.getUserObject()).getID();
					new HT_DB_StrukturObjektSchreiben().setGruppenID(db_bewegterKnotenID, db_zielKnotenID);
				} else {
					((DefaultTreeModel) getModel()).insertNodeInto(bewegterKnoten, zielKnoten,
							zielKnoten.getChildCount());
					db_zielKnotenID = ((HT_StrukturObjekt) zielKnoten.getUserObject()).getID();
					new HT_DB_StrukturObjektSchreiben().setGruppenID(db_bewegterKnotenID, db_zielKnotenID);
				}
				dropped = true;
				expandieren();
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
			dtde.dropComplete(dropped);
		}
		if (warnFenster != null) {
			warnFenster.dispose();
		}
	}

	private void dndAusTabelle(DropTargetDropEvent dtde) {
		try {
			Object objekt = dtde.getTransferable().getTransferData(dtde.getCurrentDataFlavors()[0]);
			TempBuchungsEintrag buchung = (TempBuchungsEintrag) objekt;
			HT_StrukturObjekt strukturObj = (HT_StrukturObjekt)getDropZielKnoten().getUserObject();
			if (strukturObj.getHatNachfolger()){
			/*
			 * BuchungsEintraege duerfen nicht in Gruppen verschoben werden
			 */
				return;
			}
			/*
			 * Verschiebung in gleiche Gruppe nicht moeglich
			 */
			if (buchung.getStrukturObjektID() == strukturObj.getID()){
				return;
			}
			WarungBestaetigungFenster warnFenster = new WarungBestaetigungFenster("Wirklich verschieben?");
			if (warnFenster.getBestaetigung()) {
				HT_BuchungsEintrag zuAenderndeBuchung = new HT_BuchungsEintrag(buchung.getId());
				zuAenderndeBuchung.setStukturObjektID(strukturObj.getID());
				ProjekteVerwaltenFenster.getAktuellerFokus().beobachterBenachrichtigen();
			}
			warnFenster.dispose();
		} catch (UnsupportedFlavorException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		repaint();
	}

	/**
	 * Prueft, ob die Projektgruppe (Node) oder das Projekt (Leaf) verschoben
	 * werden darf/kann.
	 * 
	 * @param dropZielKnoten
	 * @param draggedQuellKnoten
	 * @return
	 */
	private boolean darfVerschobenWerden(DefaultMutableTreeNode dropZielKnoten,
			DefaultMutableTreeNode draggedQuellKnoten) {
		if (dropZielKnoten == null || dropZielKnoten == draggedQuellKnoten) {
			return false;
		}
		HT_StrukturObjekt quelle = (HT_StrukturObjekt) draggedQuellKnoten.getUserObject();
		HT_StrukturObjekt ziel = (HT_StrukturObjekt) dropZielKnoten.getUserObject();
		if (quelle.getLinks() < ziel.getLinks() && quelle.getRechts() > ziel.getRechts()) {
			return false;
		}
		if (dropZielKnoten == draggedQuellKnoten.getParent()) {
			return false;
		}
		if (new NamenPruefung().verschieben_nameBereitsVorhanden(quelle, ziel)) {
			return false;
		}
		return true;
	}

	/**
	 * Klappt den kompletten Baum aus.
	 */
	private void expandieren() {
		for (int i = 0; i < this.getRowCount(); i++) {
			this.expandRow(i);
		}
	}

	public static void baumRechtsKlickSchliessen() {
		if (rechtsKlickJPopUp != null) {
			rechtsKlickJPopUp.dispose();
			rechtsKlickJPopUp = null;
		}
	}

	public static void setUnterstuetzteDatenSorten(DataFlavor[] unterstuetzteDatenSorten) {
		MeinJTree.unterstuetzteDatenSorten = unterstuetzteDatenSorten;
	}

	public static DataFlavor[] getUnterstuetzteDatenSorten() {
		return unterstuetzteDatenSorten;
	}

	public static void setDatenSorte(DataFlavor datenSorte) {
		MeinJTree.datenSorte = datenSorte;
	}

	public static DataFlavor getDatenSorte() {
		return datenSorte;
	}

	public void setDropZielKnoten(DefaultMutableTreeNode dropZielKnoten) {
		this.dropZielKnoten = dropZielKnoten;
	}

	public DefaultMutableTreeNode getDropZielKnoten() {
		return dropZielKnoten;
	}

	public void setDropZiel(DropTarget dropZiel) {
		this.dropZiel = dropZiel;
	}

	public DropTarget getDropZiel() {
		return dropZiel;
	}
}