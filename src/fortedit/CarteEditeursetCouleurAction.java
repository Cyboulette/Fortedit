package fortedit;

import fortedit.editeur.Editeur;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

class CarteEditeursetCouleurAction
  extends AbstractAction
{
  private static final long serialVersionUID = 1L;
  private Editeur editeur;
  private int couleur;
  
  public CarteEditeursetCouleurAction(Editeur editeur, int couleur)
  {
    this.editeur = editeur;
    this.couleur = couleur;
  }
  
  public void actionPerformed(ActionEvent arg0)
  {
    this.editeur.getImage().couleur = this.couleur;
  }
}
