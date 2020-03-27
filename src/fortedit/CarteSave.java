package fortedit;

import fortedit.carte.Carte;
import fortedit.carte.Cartes;
import fortedit.carte.Elements;
import fortedit.editeur.Editeur;

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class CarteSave
  extends AbstractAction
{
  private static final long serialVersionUID = 1L;
  private Fenetre fenetre;
  private CarteSaveSous carteSaveSous;
  private CarteSaveSousNewFormat carteSaveSousNewFormat;
  private CarteSaveSous2017 carteSaveSous2017;
  
  public CarteSave(Fenetre fenetre, String nom)
  {
    super(nom);
    
    this.fenetre = fenetre;
    this.carteSaveSous = new CarteSaveSous(fenetre, null);
    this.carteSaveSousNewFormat = new CarteSaveSousNewFormat(fenetre, null);
    this.carteSaveSous2017 = new CarteSaveSous2017(fenetre, null);
  }
  
  public void actionPerformed(ActionEvent arg0)
  {
	if(this.fenetre.getEditeur().getImage().daltonien.booleanValue()) {
		Elements.couleur[0] = Color.decode("#"+Elements.couleurHexaDefaut[0]);
		if(this.fenetre.changementCouleurs.allFields[2].getText().equals(Elements.couleurHexaDefaut[2])) {
			Elements.couleur[2] = Color.decode("#"+Elements.couleurHexaDefaut[2]);
			this.fenetre.getEditeur().changeBackgroundBoutons(0);
		} else {
			ActionEvent e = new ActionEvent(this.fenetre.changementCouleurs,1234,"Sauvegarder");
			this.fenetre.changementCouleurs.actionPerformed(e);
			this.fenetre.getEditeur().changeBackgroundBoutons(1);
		}
		this.fenetre.daltonienCheckbox.setSelected(false);
		this.fenetre.getEditeur().getImage().switchBool(8);
		this.fenetre.revalidate();
		this.fenetre.repaint();
	}
	
    if (this.fenetre.getEditeur().getFichier() != null)
    {
      int typeCarte = this.fenetre.getEditeur().getCartes().getCurrent().getTypeCarte();
      if(typeCarte == 0) {
    	  this.fenetre.getEditeur().getCartes().getCurrent().Save(this.fenetre.getEditeur().getFichier());
      } else if(typeCarte == 1) {
    	  this.fenetre.getEditeur().getCartes().getCurrent().SaveNewFormat(this.fenetre.getEditeur().getFichier());
      } else if(typeCarte == 2) {
    	  this.fenetre.getEditeur().getCartes().getCurrent().Save2017(this.fenetre.getEditeur().getFichier());
      } else {
    	  this.fenetre.getEditeur().getCartes().getCurrent().Save(this.fenetre.getEditeur().getFichier());
      }
      this.fenetre.getEditeur().getImage().modif = Boolean.valueOf(false);
      System.out.println("Type de carte enregistrée rapidemment : "+this.fenetre.getEditeur().getCartes().getCurrent().getTypeCarte());
    }
    else
    {
    	int typeCarte = this.fenetre.getEditeur().getCartes().getCurrent().getTypeCarte();
    	if(typeCarte == 0) {
    		this.carteSaveSous.actionPerformed(null);
    	} else if(typeCarte == 1) {
    		this.carteSaveSousNewFormat.actionPerformed(null);
    	} else if(typeCarte == 2) {
    		this.carteSaveSous2017.actionPerformed(null);
    	} else {
    		this.carteSaveSous.actionPerformed(null);
    	}
    }
  }
}
