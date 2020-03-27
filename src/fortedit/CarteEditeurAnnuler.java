package fortedit;

import fortedit.carte.Cartes;
import fortedit.editeur.CarteImage;
import fortedit.editeur.Editeur;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class CarteEditeurAnnuler
  extends AbstractAction
{
  private static final long serialVersionUID = 1L;
  private Editeur editeur;
  
  public CarteEditeurAnnuler(Editeur editeur)
  {
    this.editeur = editeur;
  }
  
  public void actionPerformed(ActionEvent arg0)
  {
    this.editeur.getCartes().Annuler();
    this.editeur.getImage().redessinner();
  }
}
