package fortedit;

import fortedit.carte.Cartes;
import fortedit.editeur.CarteImage;
import fortedit.editeur.Editeur;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class CarteEditeurRepeter
  extends AbstractAction
{
  private static final long serialVersionUID = 1L;
  private Editeur editeur;
  
  public CarteEditeurRepeter(Editeur editeur)
  {
    this.editeur = editeur;
  }
  
  public void actionPerformed(ActionEvent arg0)
  {
    this.editeur.getCartes().Repeter();
    this.editeur.getImage().redessinner();
  }
}
