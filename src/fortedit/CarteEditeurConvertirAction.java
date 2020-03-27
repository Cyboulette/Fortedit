package fortedit;

import fortedit.carte.Cartes;
import fortedit.editeur.CarteImage;
import fortedit.editeur.Editeur;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;

class CarteEditeurConvertirAction
  extends AbstractAction
{
  private static final long serialVersionUID = 1L;
  private Editeur carteEditeur;
  private CarteEditeurConvertirFenetre dialogueFenetre;
  
  public CarteEditeurConvertirAction(Editeur carteEditeur, CarteEditeurConvertirFenetre dialogueFenetre)
  {
    this.carteEditeur = carteEditeur;
    this.dialogueFenetre = dialogueFenetre;
  }
  
  public void actionPerformed(ActionEvent arg0)
  {
    this.carteEditeur.getCartes().Ajouter(this.carteEditeur.getCartes().getCurrent());
    for (int i = 0; i < 200; i++) {
      for (int j = 0; j < 100; j++) {
        if (this.carteEditeur.getCartes().getPrevious().getCases()[i][j] == this.dialogueFenetre.getChoix1().getSelectedIndex()) {
          this.carteEditeur.getCartes().getCurrent().getCases()[i][j] = this.dialogueFenetre.getChoix2().getSelectedIndex();
        }
      }
    }
    this.carteEditeur.getImage().redessinner();
    this.carteEditeur.getImage().modif = Boolean.valueOf(true);
  }
}
