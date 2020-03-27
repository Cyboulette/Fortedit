package fortedit;

import fortedit.carte.Carte;
import fortedit.carte.Cartes;
import fortedit.editeur.Editeur;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class CarteSaveSousNewFormat
  extends AbstractAction
{
  private static final long serialVersionUID = 1L;
  private Fenetre fenetre;
  private int n;
  
  public CarteSaveSousNewFormat(Fenetre fenetre, String nom)
  {
    super(nom);
    
    this.fenetre = fenetre;
  }
  
  public void actionPerformed(ActionEvent arg0)
  {
    JFileChooser adresse = new JFileChooser();
    adresse.setFileFilter(new Filtre("Carte au format 2016", 20003, 20073));
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
        this.fenetre.getEditeur().getCartes().getCurrent().SaveNewFormat(adresse.getSelectedFile());
        this.fenetre.getEditeur().getImage().modif = Boolean.valueOf(false);
      }
      this.fenetre.setEtatTypeCarte("Type de carte : Format 2016");
      this.fenetre.getEditeur().getCartes().getCurrent().setTypeCarte(1);
    }
  }
}
