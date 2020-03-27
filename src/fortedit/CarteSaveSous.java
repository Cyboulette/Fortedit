package fortedit;

import fortedit.carte.Carte;
import fortedit.carte.Cartes;
import fortedit.editeur.Editeur;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class CarteSaveSous
  extends AbstractAction
{
  private static final long serialVersionUID = 1L;
  private Fenetre fenetre;
  private int n;
  
  public CarteSaveSous(Fenetre fenetre, String nom)
  {
    super(nom);
    
    this.fenetre = fenetre;
  }
  
  public void actionPerformed(ActionEvent arg0)
  {
	int typeCarte = this.fenetre.getEditeur().getCartes().getCurrent().getTypeCarte();
	System.out.println("Type de carte à save sous : "+typeCarte);
    JFileChooser adresse = new JFileChooser();
    if(typeCarte == 1) {
    	adresse.setFileFilter(new Filtre("Carte au format 2016", 20003, 20073));
    } else if(typeCarte == 2) {
    	adresse.setFileFilter(new Filtre("Carte au format 2017", 20003, 20131));
    } else {
    	adresse.setFileFilter(new Filtre("Carte au format par défaut", 20002, 20003));
    }
    adresse.setSelectedFile(this.fenetre.getEditeur().getFichier());
    adresse.setCurrentDirectory(this.fenetre.getEditeur().getRepertoire());
    if (adresse.showSaveDialog(null) == 0)
    {
      if (Filtre.getExtension(adresse.getSelectedFile()).compareTo("txt") != 0) {
        adresse.setSelectedFile(new File(adresse.getSelectedFile().getAbsolutePath() + ".txt"));
      }
      if (adresse.getSelectedFile().exists()) {
        this.n = JOptionPane.showConfirmDialog(null, "Ce fichier existe déjà , voulez-vous l'écraser ?", "Attention !", 0);
      } else {
        this.n = 0;
      }
      if (this.n == 0)
      {
        this.fenetre.getEditeur().setFichier(adresse.getSelectedFile());
        this.fenetre.getEditeur().setRepertoire(adresse.getSelectedFile().getParentFile());
        this.fenetre.setTitle(adresse.getSelectedFile().getName() + " - Éditeur de cartes pour forteresse");
        this.fenetre.getEditeur().setEtatFichier(adresse.getSelectedFile().getAbsolutePath());
        if(typeCarte == 1) {
        	this.fenetre.getEditeur().getCartes().getCurrent().SaveNewFormat(adresse.getSelectedFile());
        } else if(typeCarte == 2) {
        	this.fenetre.getEditeur().getCartes().getCurrent().Save2017(adresse.getSelectedFile());
        } else {
        	this.fenetre.getEditeur().getCartes().getCurrent().Save(adresse.getSelectedFile());
        }
        this.fenetre.getEditeur().getImage().modif = Boolean.valueOf(false);
      }
      /*this.fenetre.setEtatTypeCarte("Type de carte : Par Défaut");
      this.fenetre.getEditeur().getCartes().getCurrent().setTypeCarte(0);*/
    }
  }
}
