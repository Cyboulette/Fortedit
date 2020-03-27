package fortedit;

import fortedit.carte.Carte;
import fortedit.carte.Cartes;
import fortedit.editeur.CarteImage;
import fortedit.editeur.Editeur;
import fortedit.mondes.Mondes;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

public class CarteLoad
  extends AbstractAction
{
  private static final long serialVersionUID = 1L;
  private Fenetre fenetre;
  
  public CarteLoad(Fenetre fenetre)
  {
    this.fenetre = fenetre;
  }
  
  public void actionPerformed(ActionEvent arg0)
  {
    JFileChooser adresse = new JFileChooser();
    adresse.addChoosableFileFilter(new Filtre("Carte au format de base", 20002, 20003));
    adresse.addChoosableFileFilter(new Filtre("Carte au format 2016", 20003, 20073));
    adresse.addChoosableFileFilter(new Filtre("Carte au format 2017", 20003, 20131));
    adresse.setSelectedFile(this.fenetre.getEditeur().getFichier());
    adresse.setCurrentDirectory(this.fenetre.getEditeur().getRepertoire());
    if (adresse.showOpenDialog(null) == 0)
    {
      this.fenetre.getEditeur().setFichier(adresse.getSelectedFile());
      this.fenetre.getEditeur().setRepertoire(adresse.getSelectedFile().getParentFile());
      this.fenetre.setTitle(adresse.getSelectedFile().getName() + " - Éditeur de cartes pour forteresse");
      String name = adresse.getSelectedFile().getName();
      String ext = name.substring(name.lastIndexOf(".") + 1);
      if(ext.equals("txt")) {
	      this.fenetre.getEditeur().setEtatFichier(adresse.getSelectedFile().getAbsolutePath());
	      this.fenetre.getEditeur().getCartes().Init();
	      int typeMap = this.fenetre.getEditeur().getCartes().getCurrent().Load(adresse.getSelectedFile(), this.fenetre);
	      System.out.println("Type de carte chargée : "+typeMap);
	      if(typeMap == 0) {
	    	  this.fenetre.setEtatTypeCarte("Type de carte : Par défaut");
	    	  this.fenetre.getEditeur().getCartes().getCurrent().setTypeCarte(0);
	      } else if(typeMap == 1) {
	    	  this.fenetre.setEtatTypeCarte("Type de carte : Format 2016");
	    	  this.fenetre.getEditeur().getCartes().getCurrent().setTypeCarte(1);
	      } else if(typeMap == 2) {
	    	  this.fenetre.setEtatTypeCarte("Type de carte : Format 2017");
	    	  this.fenetre.getEditeur().getCartes().getCurrent().setTypeCarte(2);
	      } else {
	    	  this.fenetre.setEtatTypeCarte("Type de carte : Inconnu");
	    	  this.fenetre.getEditeur().getCartes().getCurrent().setTypeCarte(0);
	      }
	      File File = adresse.getSelectedFile();
	      try {
	    	  if(typeMap != 2) { // Si ce n'est pas le nouveau format
		        BufferedReader fileReader = new BufferedReader(new FileReader(File));
		        String line = fileReader.readLine();
		        String[] mapNumber = line.split("-");
		        int map = Integer.parseInt(mapNumber[0]);
		        this.fenetre.getMenuEditionMondesBouton()[map].setSelected(true);
		        this.fenetre.getEditeur().getCartes().getCurrent().setFond(map);
		        fileReader.close();
		        this.fenetre.alreadyPerso = false;
	    	  } else { // On charge le nouveau format de map (2017 - Personnalisée)
	    		  this.fenetre.alreadyPerso = true;
	    	  }
	    	  this.fenetre.menuMapPerso();
	      } catch (FileNotFoundException e) {
	        //e.printStackTrace();
	        this.fenetre.getMenuEditionMondesBouton()[this.fenetre.getEditeur().getCartes().getCurrent().getFond()].setSelected(true);
	      } catch (IOException e) {
	        //e.printStackTrace();
	        this.fenetre.getMenuEditionMondesBouton()[this.fenetre.getEditeur().getCartes().getCurrent().getFond()].setSelected(true);
	      } catch (Exception e) {
	    	//e.printStackTrace();
	    	this.fenetre.getMenuEditionMondesBouton()[this.fenetre.getEditeur().getCartes().getCurrent().getFond()].setSelected(true);
	      }
	      this.fenetre.getEditeur().getImage().redessinner();
	      this.fenetre.getEditeur().getImage().modif = Boolean.valueOf(false);
      } else {
		  final JOptionPane pane = new JOptionPane("Cette carte n'est pas valide");
		  final JDialog d = pane.createDialog((JFrame)null, "Erreur");
		  d.setLocation(200,200);
		  d.setVisible(true);
      }
    }
  }
}
