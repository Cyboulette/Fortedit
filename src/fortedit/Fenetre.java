package fortedit;

import fortedit.carte.Carte;
import fortedit.editeur.CarteImage;
import fortedit.editeur.Editeur;
import fortedit.mondes.Mondes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.AbstractButton;
import javax.swing.ActionMap;
import javax.swing.ButtonGroup;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;



public class Fenetre
  extends JFrame
{
  private static final long serialVersionUID = 1L;
  private Editeur editeur;
  public JRadioButtonMenuItem[] menuEditionMondesBouton = new JRadioButtonMenuItem[Mondes.codes.length];
  private JLabel typeCarte;
  public JMenuItem couleurs;
  public JMenu menuEditionMondes;
  public ButtonGroup menuEditionMondesBoutons;
  public JMenu menuEdition;
  public JMenuBar menuBar;
  public JMenu menuPerso;
  public JCheckBox mode;
  public JCheckBox daltonienCheckbox;
  public boolean alreadyPerso = false;
  public CarteEditeurChangerCouleursFenetre changementCouleurs;
  
  public Fenetre() throws IOException
  {
    setTitle("Nouvelle Carte - Éditeur de cartes pour Forteresse");
    setDefaultCloseOperation(0);
    addWindowListener(new FenetreClose(this));
    setPreferredSize(new Dimension(800, 600));
    
    setEditeur(new Editeur(this));
    setContentPane(getEditeur());
    
    menuBar = new JMenuBar();
    
    JMenu menuFichier = new JMenu("Fichier");
    JMenuItem nouvelleCarte = new JMenuItem(new FenetreConfirmation(this, new CarteNouvelle(this, 0)));
    nouvelleCarte.setText("Nouvelle carte");
    getEditeur().getInputMap().put(KeyStroke.getKeyStroke("ctrl N"), "ctrl N");
    getEditeur().getActionMap().put("ctrl N", nouvelleCarte.getAction());
    nouvelleCarte.setToolTipText("ctrl N");
    menuFichier.add(nouvelleCarte);
    
    JMenuItem nouvelleCartePerso = new JMenuItem(new FenetreConfirmation(this, new CarteNouvelle(this, 2)));
    nouvelleCartePerso.setText("Nouvelle carte personnalisable");
    getEditeur().getInputMap().put(null, null);
    getEditeur().getActionMap().put(null, null);
    nouvelleCartePerso.setToolTipText(null);
    menuFichier.add(nouvelleCartePerso);
    
    JMenuItem ouvrirCarte = new JMenuItem(new FenetreConfirmation(this, new CarteLoad(this)));
    ouvrirCarte.setText("Ouvrir");
    getEditeur().getInputMap().put(KeyStroke.getKeyStroke("ctrl O"), "ctrl O");
    getEditeur().getActionMap().put("ctrl O", ouvrirCarte.getAction());
    ouvrirCarte.setToolTipText("ctrl O");
    menuFichier.add(ouvrirCarte);
    
    JMenuItem enregistrer = new JMenuItem(new CarteSave(this, "Enregistrer"));
    getEditeur().getInputMap().put(KeyStroke.getKeyStroke(83, 2), "ctrl S");
    getEditeur().getActionMap().put("ctrl S", enregistrer.getAction());
    enregistrer.setToolTipText("ctrl S");
    menuFichier.add(enregistrer);
    
    JMenuItem enregistrerSous = new JMenuItem(new CarteSaveSous(this, "Enregistrer sous"));
    menuFichier.add(enregistrerSous);
    
    JMenuItem exporter = new JMenuItem(new CarteExporter(this.editeur, "Exporter"));
    getEditeur().getInputMap().put(KeyStroke.getKeyStroke("ctrl E"), "ctrl E");
    getEditeur().getActionMap().put("ctrl E", exporter.getAction());
    exporter.setToolTipText("ctrl E");
    menuFichier.add(exporter);
    
    menuFichier.addSeparator();
    
    JMenuItem quitter = new JMenuItem(new FenetreConfirmation(this, new FenetreQuitter()));
    quitter.setText("Quitter");
    getEditeur().getInputMap().put(KeyStroke.getKeyStroke("ctrl Q"), "ctrl Q");
    getEditeur().getActionMap().put("ctrl Q", quitter.getAction());
    quitter.setToolTipText("ctrl Q");
    menuFichier.add(quitter);
    menuBar.add(menuFichier);
    
    this.menuEdition = new JMenu("Édition");
    JMenuItem annuler = new JMenuItem(new CarteEditeurAnnuler(this.editeur));
    annuler.setText("Annuler");
    menuEdition.add(annuler);
    getEditeur().getInputMap().put(KeyStroke.getKeyStroke("ctrl Z"), "ctrl Z");
    getEditeur().getActionMap().put("ctrl Z", annuler.getAction());
    annuler.setToolTipText("ctrl Z");
    
    JMenuItem repeter = new JMenuItem(new CarteEditeurRepeter(this.editeur));
    repeter.setText("Répéter");
    menuEdition.add(repeter);
    getEditeur().getInputMap().put(KeyStroke.getKeyStroke("ctrl Y"), "ctrl Y");
    getEditeur().getActionMap().put("ctrl Y", repeter.getAction());
    repeter.setToolTipText("ctrl Y");
    
    menuEdition.addSeparator();
    
    mode = new JCheckBox(this.editeur.getImage().getSwitchLibreAction());
    mode.setText("Dessin libre");
    menuEdition.add(mode);
    
    menuEdition.addSeparator();
    
    this.menuEditionMondes = new JMenu("Monde");
    this.menuEditionMondesBoutons = new ButtonGroup();
    for (int i = 0; i < Mondes.codes.length; i++)
    {
      getMenuEditionMondesBouton()[i] = new JRadioButtonMenuItem("monde " + Mondes.codes[i]);
      getMenuEditionMondesBouton()[i].addActionListener(new CarteEditeursetMonde(getEditeur(), i));
      menuEditionMondes.add(getMenuEditionMondesBouton()[i]);
      menuEditionMondesBoutons.add(getMenuEditionMondesBouton()[i]);
    }
    getMenuEditionMondesBouton()[getEditeur().getCartes().getCurrent().getFond()].setSelected(true);
    menuEdition.add(menuEditionMondes);
    
    JMenuItem convertir = new JMenuItem(new CarteEditeurConvertirFenetre(getEditeur()));
    convertir.setText("Convertir");
    menuEdition.add(convertir);
    
    JMenuItem intervertir = new JMenuItem(new CarteEditeurIntervertirFenetre(getEditeur()));
    intervertir.setText("Intervertir");
    menuEdition.add(intervertir);
    
    menuEdition.addSeparator();
    
    JMenuItem changerSens = new JMenuItem(new CarteEditeurChangerSens(getEditeur()));
    changerSens.setText("Changer de sens");
    menuEdition.add(changerSens);
    
    JMenuItem symetriser = new JMenuItem(new CarteEditeurSymetriserFenetre(getEditeur()));
    symetriser.setText("Symétriser");
    menuEdition.add(symetriser);
    
    menuEdition.addSeparator();
    this.changementCouleurs = new CarteEditeurChangerCouleursFenetre(getEditeur());
    this.couleurs = new JMenuItem(this.changementCouleurs);
    this.couleurs.setText("Changer les couleurs");
    menuEdition.add(this.couleurs);
    
    JMenuItem formes = new JMenuItem(new CarteEditeurGererFormesFenetre(getEditeur()));
    formes.setText("Gérer les formes");
    menuEdition.add(formes);
    getEditeur().getInputMap().put(KeyStroke.getKeyStroke("ctrl F"), "ctrl F");
    getEditeur().getActionMap().put("ctrl F", formes.getAction());
    formes.setToolTipText("ctrl F");
    
    menuBar.add(menuEdition);
    
    JMenu menuAffichage = new JMenu("Affichage");
    JCheckBox temp = new JCheckBox(this.editeur.getImage().getSwitchZonesAction());
    temp.setText("Zones");
    temp.setSelected(getEditeur().getImage().zones.booleanValue());
    menuAffichage.add(temp);
    temp = new JCheckBox(this.editeur.getImage().getSwitchFondAction());
    temp.setText("Monde");
    temp.setSelected(getEditeur().getImage().fond.booleanValue());
    menuAffichage.add(temp);
    temp = new JCheckBox(this.editeur.getImage().getSwitchFrigosAction());
    temp.setText("Frigos");
    temp.setSelected(getEditeur().getImage().frigos.booleanValue());
    menuAffichage.add(temp);
    temp = new JCheckBox(this.editeur.getImage().getSwitchSpawnsAction());
    temp.setText("Positions de respawn");
    temp.setSelected(getEditeur().getImage().spawns.booleanValue());
    menuAffichage.add(temp);
    menuAffichage.addSeparator();
    temp = new JCheckBox(this.editeur.getImage().getSwitchGrilleAction());
    temp.setText("Grille");
    temp.setSelected(getEditeur().getImage().grille.booleanValue());
    menuAffichage.add(temp);
    temp = new JCheckBox(this.editeur.getImage().getSwitchMireAction());
    temp.setText("Mire");
    temp.setSelected(getEditeur().getImage().mire.booleanValue());
    menuAffichage.add(temp);
    
    menuAffichage.addSeparator();
    
    JCheckBox zoom = new JCheckBox(this.editeur.getImage().getSwitchZoomAction());
    zoom.setText("Vue d'ensemble");
    zoom.setSelected(getEditeur().getImage().zoom.booleanValue());
    menuAffichage.add(zoom);
    
    JCheckBox daltonien = new JCheckBox(this.editeur.getImage().getDaltonienAction());
    daltonien.setText("Mode daltonien");
    daltonien.setSelected(getEditeur().getImage().daltonien.booleanValue());
    menuAffichage.add(daltonien);
    this.daltonienCheckbox = daltonien;
    
    menuBar.add(menuAffichage);
    



    JMenu menuAide = new JMenu("?");
    JMenuItem apropos = new JMenuItem(new FenetreAPropos(this));
    apropos.setText("À Propos");
    menuAide.add(apropos);
    menuBar.add(menuAide);
    
    this.typeCarte = new JLabel();
    this.typeCarte.setForeground(Color.GRAY);
    this.typeCarte.setText("Type de carte : Par défaut");
    menuBar.add(this.typeCarte);
    
    setJMenuBar(menuBar);
    this.menuPerso = null;
    
    pack();
  }
  
  public void setEditeur(Editeur editeur)
  {
    this.editeur = editeur;
  }
  
  public Editeur getEditeur()
  {
    return this.editeur;
  }
  
  public void setEtatTypeCarte(String nom)
  {
    this.typeCarte.setText(nom);
  }
  
  public void setMenuEditionMondesBouton(JRadioButtonMenuItem[] menuEditionMondesBouton)
  {
    this.menuEditionMondesBouton = menuEditionMondesBouton;
  }
  
  public void menuMapPerso() throws IOException {
	  if(this.alreadyPerso && menuPerso == null) {
		    menuPerso = new JMenu("Paramètres de la map");

		    JMenuItem zonesRespawn = new JMenuItem(new FenetreZonesRespawn(this));
		    zonesRespawn.setText("Zones de respawn");
		    menuPerso.add(zonesRespawn);
		    
		    JMenuItem positionsRespawn = new JMenuItem(new FenetrePositionsRespawn(this));
		    positionsRespawn.setText("Positions de respawn");
		    menuPerso.add(positionsRespawn);

		    JMenuItem frigos = new JMenuItem(new FenetreFrigos(this));
		    frigos.setText("Frigos");
		    menuPerso.add(frigos);
		    
		    JMenuItem gravite = new JMenuItem(new FenetreGravite(this));
		    gravite.setText("Ligne de gravité");
		    menuPerso.add(gravite);
		    
		    JMenuItem bords = new JMenuItem(new FenetreBords(this));
		    bords.setText("Bords de map");
		    menuPerso.add(bords);
		    
		    menuBar.add(menuPerso);
	  } else if(this.alreadyPerso && menuPerso != null && !menuPerso.isShowing()) {
		  menuPerso.show();
	  } else if(!this.alreadyPerso && menuPerso != null){
		 menuPerso.hide();
	  }
	  pack();
  }
  
  public JRadioButtonMenuItem[] getMenuEditionMondesBouton()
  {
    return this.menuEditionMondesBouton;
  }
}
