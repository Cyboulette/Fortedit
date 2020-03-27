package fortedit;

import fortedit.carte.Carte;
import fortedit.carte.Cartes;
import fortedit.editeur.CarteImage;
import fortedit.editeur.Editeur;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class CarteEditeursetMonde
  extends AbstractAction
{
  private static final long serialVersionUID = 1L;
  private Editeur editeur;
  private int monde;
  
  public CarteEditeursetMonde(Editeur editeur, int monde)
  {
    this.editeur = editeur;
    this.monde = monde;
  }
  
  public void setMonde(Editeur editeur, int monde) {
	  this.editeur = editeur;
	  this.monde = monde;
	  this.editeur.getCartes().Ajouter(this.editeur.getCartes().getCurrent());
	  this.editeur.getCartes().getCurrent().setFond(this.monde);
	  this.editeur.getImage().redessinner();
	  this.editeur.getImage().modif = Boolean.valueOf(true);
	  System.out.println("Type de carte lors du changement de map : "+this.editeur.getCartes().getCurrent().getTypeCarte());
  }
  
  public void actionPerformed(ActionEvent arg0)
  {
    this.editeur.getCartes().Ajouter(this.editeur.getCartes().getCurrent());
    this.editeur.getCartes().getCurrent().setFond(this.monde);
    this.editeur.getImage().redessinner();
    this.editeur.getImage().modif = Boolean.valueOf(true);
    System.out.println("Type de carte lors du changement de map : "+this.editeur.getCartes().getCurrent().getTypeCarte());
  }
}
