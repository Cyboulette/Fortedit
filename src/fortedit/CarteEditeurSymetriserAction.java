package fortedit;

import fortedit.carte.Cartes;
import fortedit.editeur.CarteImage;
import fortedit.editeur.Editeur;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;

class CarteEditeurSymetriserAction
  extends AbstractAction
{
  private static final long serialVersionUID = 1L;
  private Editeur editeur;
  private CarteEditeurSymetriserFenetre dialogueFenetre;
  
  public CarteEditeurSymetriserAction(Editeur editeur, CarteEditeurSymetriserFenetre dialogueFenetre)
  {
    super("Symétriser");
    
    this.editeur = editeur;
    this.dialogueFenetre = dialogueFenetre;
  }
  
  public void actionPerformed(ActionEvent arg0)
  {
    this.editeur.getCartes().Ajouter(this.editeur.getCartes().getCurrent());
    if (this.dialogueFenetre.getChoix().getSelectedIndex() == 0) {
      for (int i = 100; i < 200; i++) {
        this.editeur.getCartes().getCurrent().getCases()[i] = this.editeur.getCartes().getCurrent().getCases()[(199 - i)];
      }
    } else {
      for (int i = 0; i < 100; i++) {
        this.editeur.getCartes().getCurrent().getCases()[i] = this.editeur.getCartes().getCurrent().getCases()[(199 - i)];
      }
    }
    this.editeur.getImage().redessinner();
    this.editeur.getImage().modif = Boolean.valueOf(true);
  }
}
