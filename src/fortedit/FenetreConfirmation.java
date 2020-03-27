package fortedit;

import fortedit.editeur.CarteImage;
import fortedit.editeur.Editeur;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

public class FenetreConfirmation
  extends AbstractAction
{
  private static final long serialVersionUID = 1L;
  private Fenetre fenetre;
  private CarteSave carteSave;
  private AbstractAction action;
  private int n;
  
  public FenetreConfirmation(Fenetre fenetre, AbstractAction action)
  {
    this.fenetre = fenetre;
    this.carteSave = new CarteSave(fenetre, null);
    this.action = action;
  }
  
  public void actionPerformed(ActionEvent arg0)
  {
    if (this.fenetre.getEditeur().getImage().modif.booleanValue()) {
      this.n = JOptionPane.showConfirmDialog(null, "La carte a été modifiée, sauvegarder les changements ?", "Attention !", 1);
    } else {
      this.n = 1;
    }
    if (this.n == 0)
    {
      this.carteSave.actionPerformed(null);
      this.action.actionPerformed(null);
    }
    else if (this.n == 1)
    {
      this.action.actionPerformed(null);
    }
  }
}
