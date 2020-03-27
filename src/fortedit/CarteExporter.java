package fortedit;

import fortedit.editeur.CarteImage;
import fortedit.editeur.Editeur;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class CarteExporter
  extends AbstractAction
{
  private static final long serialVersionUID = 1L;
  private Editeur editeur;
  private int n;
  
  public CarteExporter(Editeur editeur, String nom)
  {
    super(nom);
    
    this.editeur = editeur;
  }
  
  public void actionPerformed(ActionEvent arg0)
  {
    JFileChooser adresse = new JFileChooser();
    adresse.setFileFilter(new Filtre("Image", "png"));
    if (adresse.showDialog(null, "Exporter") == 0)
    {
      if (Filtre.getExtension(adresse.getSelectedFile()).compareTo("png") != 0) {
        adresse.setSelectedFile(new File(adresse.getSelectedFile().getAbsolutePath() + ".png"));
      }
      if (adresse.getSelectedFile().exists()) {
        this.n = JOptionPane.showConfirmDialog(null, "Ce fichier existe déjàÂ , voulez-vous l'écraser ?", "Attention !", 0);
      } else {
        this.n = 0;
      }
      if (this.n == 0) {
        this.editeur.getImage().Exporte(adresse.getSelectedFile());
      }
    }
  }
}
