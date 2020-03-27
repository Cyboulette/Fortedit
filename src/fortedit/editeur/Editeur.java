package fortedit.editeur;

import fortedit.CarteEditeurTransferHandler;
import fortedit.CarteEditeursetCouleurBouton;
import fortedit.Fenetre;
import fortedit.carte.Cartes;
import fortedit.carte.Elements;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

public class Editeur
  extends JPanel
{
  private static final long serialVersionUID = 1L;
  private Cartes cartes;
  private CarteImage image;
  private JScrollPane scroll;
  private File fichier;
  private File repertoire;
  private JLabel barreEtatFichier;
  private JLabel barreEtatCoor;
  private JLabel barreEtatCopie;
  private Fenetre actualFenetre;
  private JButton buttonSaveForme;
  
  public static Boolean formesFenetreOpened = false;
  
  public JPanel outils;
  
  public Editeur(Fenetre fenetre)
  {
    super(new BorderLayout());
    this.actualFenetre = fenetre;
    setCartes(new Cartes());
    
    setRepertoire(new File(getClass().getProtectionDomain().getCodeSource().getLocation().getFile()));
    
    this.outils = new JPanel();
    for (int i = 0; i < Elements.codes.length; i++) {
      this.outils.add(new CarteEditeursetCouleurBouton(this, i, 0));
    }
    add(this.outils, "North");
    
    JPanel barreEtat = new JPanel(new BorderLayout());
    this.barreEtatFichier = new JLabel();
    this.barreEtatFichier.setEnabled(false);
    barreEtat.add(this.barreEtatFichier, "West");
    this.barreEtatCoor = new JLabel();
    this.barreEtatCoor.setEnabled(false);
    this.barreEtatCoor.setText("[O, O]");
    //barreEtat.add(this.barreEtatCoor, "East");
    
    JPanel subPanel = new JPanel();
    JButton btnZoomPlus = new JButton("Zoom +");
    btnZoomPlus.setActionCommand("zoom+");

    JButton btnZoomMoins = new JButton("Zoom -");
    btnZoomMoins.setActionCommand("zoom-");
    
    JButton btnZoomReset = new JButton("Reset Zoom");
    btnZoomReset.setActionCommand("zoomReset");
    
    btnZoomPlus.addActionListener(actionZoom);
    btnZoomMoins.addActionListener(actionZoom);
    btnZoomReset.addActionListener(actionZoom);
    
    JButton btnCopy = new JButton("Copier");
    btnCopy.setActionCommand("copy");
    btnCopy.addActionListener(actionCopy);
    
    btnZoomPlus.setFocusable(false);
    btnZoomReset.setFocusable(false);
    btnZoomMoins.setFocusable(false);
    btnCopy.setFocusable(false);
    
    this.barreEtatCopie = new JLabel();
    this.barreEtatCopie.setEnabled(true);
    this.barreEtatCopie.setForeground(Color.BLACK);
    this.barreEtatCopie.setText("Copie : Désactivée");
    
    this.buttonSaveForme = new JButton("<html><body><center>Sauvegarder <br/>la forme</center></body></html>");
    this.buttonSaveForme.setEnabled(false);
    this.buttonSaveForme.setVisible(false);
    this.buttonSaveForme.setFocusable(false);
    this.buttonSaveForme.setActionCommand("saveForme");
    this.buttonSaveForme.addActionListener(actionSaveForme);
    
    subPanel.add(this.barreEtatCopie);
    subPanel.add(btnCopy);
    subPanel.add(this.buttonSaveForme);
    subPanel.add(btnZoomPlus);
    subPanel.add(btnZoomMoins);
    subPanel.add(btnZoomReset);
    subPanel.add(this.barreEtatCoor);
    
    barreEtat.add(subPanel, BorderLayout.EAST);

    add(barreEtat, "South");
    
    setImage(new CarteImage(this));
    this.scroll = new JScrollPane(getImage());
    add(this.scroll, "Center");
    
    setTransferHandler(new CarteEditeurTransferHandler(fenetre));
    getEditeur().addKeyListener(new KeyListener(){

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if ((e.getKeyCode() == KeyEvent.VK_C) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
				copy();
			}
			
			if ((e.getKeyCode() == KeyEvent.VK_ADD || e.getKeyCode() == KeyEvent.VK_EQUALS) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
				ActionEvent eZoomPlus = new ActionEvent(this, 789, "zoom+");
				zoom(eZoomPlus);
			}
			
			if ((e.getKeyCode() == 109 || e.getKeyCode() == 54) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
				ActionEvent eZoomMoins = new ActionEvent(this, 790, "zoom-");
				zoom(eZoomMoins);
			}
			
			if ((e.getKeyCode() == 96 || e.getKeyCode() == 48) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
				ActionEvent eZoomReset = new ActionEvent(this, 790, "zoomReset");
				zoom(eZoomReset);
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
    	
    });
  }
  
  
  ActionListener actionCopy = new ActionListener(){
	  public void actionPerformed(ActionEvent e) {
		  if ("copy".equals(e.getActionCommand())) {
			  copy();
		  }
	  }
  };
  
  ActionListener actionSaveForme = new ActionListener(){
	  public void actionPerformed(ActionEvent e) {
		  if ("saveForme".equals(e.getActionCommand())) {
			  if(getImage().copyState.booleanValue()) {
				  getImage().saveForme();
			  } else {
				  final JOptionPane pane = new JOptionPane("Vous ne pouvez pas sauvegarder une forme sans avoir copié cette forme");
				  final JDialog d = pane.createDialog((JFrame)null, "Erreur");
				  d.setLocation(200,200);
				  d.setVisible(true);
			  }
		  }
	  }
  };
  
  public void copy() {
	  if(!getImage().frigoPositionState.booleanValue()) {
		  if(!getImage().libre.booleanValue()) {
			  if(!getImage().respawnPositionState.booleanValue()) {
				  if(!getImage().respawnZoneState.booleanValue()) {
					  if(!getImage().copyState.booleanValue() && !getImage().isAction.booleanValue()) {
						  getImage().isAction = Boolean.valueOf(true);
						  getImage().copyState = Boolean.valueOf(true);
						  getImage().nbrCopiedCases = 0;
						  getImage().startCopieX = 0;
						  getImage().startCopieY = 0;
						  getImage().endCopieX = 0;
						  getImage().endCopieY = 0;
						  getEditeur().setEtatCopie("Copie : Activée");
					  } else {
						  getImage().isAction = Boolean.valueOf(false);
						  getImage().copyState = Boolean.valueOf(false);
						  getEditeur().setEtatCopie("Copie : Désactivée");
					  }
				  }
			  }
		  } else {
			  final JOptionPane pane = new JOptionPane("Vous ne pouvez-pas copier en ayant le mode libre d'activé ");
			  final JDialog d = pane.createDialog((JFrame)null, "Erreur");
			  d.setLocation(200,200);
			  d.setVisible(true);
		  }
	  }
  }
  
  ActionListener actionZoom = new ActionListener(){
	  public void actionPerformed(ActionEvent e) {
		  zoom(e);
	  } 
  };
  
  public void zoom(ActionEvent e) {
	  int needToZoom = 0;
	  boolean modifyZoom = false;
	  int[] valuesZoom = {};
	  String zoomToDisplay = "maximum";
      if ("zoom+".equals(e.getActionCommand())) {
    	  valuesZoom = new int[]{5, 10, 15, 20, 25};
    	  zoomToDisplay = "maximum";
      } else if("zoom-".equals(e.getActionCommand())) {
    	  valuesZoom = new int[]{25, 20, 15, 10, 5};
    	  zoomToDisplay = "minimum";
      } else if("zoomReset".equals(e.getActionCommand())) {
    	  modifyZoom = true;
    	  needToZoom = 10;
      }

      for(int i=0; i<valuesZoom.length; i++) {
    	  if(getImage().actualZoom == valuesZoom[i] && i < valuesZoom.length-1) {
    		  needToZoom = valuesZoom[i+1];
    		  modifyZoom = true;
    	  }
    	  
    	  if(getImage().actualZoom == valuesZoom[i] && i == valuesZoom.length-1) {
    		  modifyZoom = true;
    		  needToZoom = getImage().actualZoom;

			  final JOptionPane pane = new JOptionPane("Vous avez atteint le zoom "+zoomToDisplay);
			  final JDialog d = pane.createDialog((JFrame)null, "Information");
			  d.setLocation(200,200);
			  d.setVisible(true);
    	  }
      }
      
	  
	  if(needToZoom >= 0 && needToZoom != getImage().actualZoom && modifyZoom == true) {
		  System.out.println("NeedToZoom = "+needToZoom);
		  getImage().pas = (needToZoom);
		  getImage().image = new BufferedImage(200 * getImage().pas, 100 * getImage().pas, 2);
		  getImage().setPreferredSize(new Dimension(getImage().pas * 200, getImage().pas * 100));
		  
		  getImage().actualZoom = needToZoom;
		  getImage().getSwitchZoomAction();
	  }
      
      getImage().redessinner();
      getImage().revalidate();
      getImage().redessinner();
  }
  
  public void changeBackgroundBoutons(int typeBouton) {
	remove(this.outils);
    this.outils = new JPanel();
    for (int i = 0; i < Elements.codes.length; i++) {
      this.outils.add(new CarteEditeursetCouleurBouton(this, i, typeBouton));
    }
    add(this.outils, "North");
    this.repaint();
    this.revalidate();
  }
  
  public void setCartes(Cartes cartes)
  {
    this.cartes = cartes;
  }
  
  public Cartes getCartes()
  {
    return this.cartes;
  }
  
  public void setImage(CarteImage image)
  {
    this.image = image;
  }
  
  public CarteImage getImage()
  {
    return this.image;
  }
  
  public Editeur getEditeur() {
	  return this;
  }
  
  public void setFichier(File fichier)
  {
    this.fichier = fichier;
  }
  
  public File getFichier()
  {
    return this.fichier;
  }
  
  public void setRepertoire(File repertoire)
  {
    this.repertoire = repertoire;
  }
  
  public File getRepertoire()
  {
    return this.repertoire;
  }
  
  public void setEtatFichier(String nom)
  {
    this.barreEtatFichier.setText(nom);
  }
  
  public void setEtatCoor(String nom)
  {
    this.barreEtatCoor.setText(nom);
  }
  
  public void setEtatCopie(String nom)
  {
    this.barreEtatCopie.setText(nom);
  }
  
  public void visibleButtonSaveForme(Boolean etat)
  {
    this.buttonSaveForme.setVisible(etat);
    this.buttonSaveForme.setEnabled(etat);
  }
  
  public Fenetre getFenetre() {
	return this.actualFenetre;
  }
}
