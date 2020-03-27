package fortedit;

import fortedit.carte.Cartes;
import fortedit.editeur.CarteImage;
import fortedit.editeur.Editeur;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class CarteEditeurChangerSens
  extends AbstractAction
{
  private static final long serialVersionUID = 1L;
  private Editeur carteEditeur;
  
  public CarteEditeurChangerSens(Editeur carteEditeur)
  {
    this.carteEditeur = carteEditeur;
  }
  
  public void actionPerformed(ActionEvent e)
  {
    this.carteEditeur.getCartes().Ajouter(this.carteEditeur.getCartes().getCurrent());
    for (int i = 0; i < 200; i++) {
      this.carteEditeur.getCartes().getCurrent().getCases()[i] = this.carteEditeur.getCartes().getPrevious().getCases()[(199 - i)];
    }
    this.carteEditeur.getImage().redessinner();
    this.carteEditeur.getImage().modif = Boolean.valueOf(true);
  }
}
