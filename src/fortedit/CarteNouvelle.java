package fortedit;

import fortedit.carte.Carte;
import fortedit.carte.Cartes;
import fortedit.carte.Elements;
import fortedit.editeur.CarteImage;
import fortedit.editeur.Editeur;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JRadioButtonMenuItem;

public class CarteNouvelle
  extends AbstractAction
{
  private static final long serialVersionUID = 1L;
  private Fenetre fenetre;
  private int typeCarte;
  
  public CarteNouvelle(Fenetre fenetre, int typeCarte)
  {
    this.fenetre = fenetre;
    this.typeCarte = typeCarte;
  }
  
  public void actionPerformed(ActionEvent arg0)
  {
    this.fenetre.getEditeur().getCartes().Init();
    this.fenetre.getMenuEditionMondesBouton()[this.fenetre.getEditeur().getCartes().getCurrent().getFond()].setSelected(true);
    this.fenetre.getEditeur().getImage().redessinner();
    this.fenetre.getEditeur().getImage().modif = Boolean.valueOf(false);
    this.fenetre.getEditeur().setFichier(null);
    this.fenetre.setTitle("Nouvelle carte - Éditeur de cartes pour forteresse");
    this.fenetre.getEditeur().setEtatFichier("");
    this.fenetre.setEtatTypeCarte("Type de carte : Par défaut");

	CarteEditeurChangerCouleursFenetre refreshColors = new CarteEditeurChangerCouleursFenetre(fenetre.getEditeur());
	for(ActionListener al : fenetre.couleurs.getActionListeners() ) {
		fenetre.couleurs.removeActionListener(al);
	}
	fenetre.couleurs.setText("Changer les couleurs");
	fenetre.couleurs.addActionListener(refreshColors);
	fenetre.getEditeur().changeBackgroundBoutons(0);
    for (int i = 0; i < Elements.codes.length; i++) {
    	fortedit.carte.Elements.couleur[i] = Color.decode("#"+fortedit.carte.Elements.couleurHexaDefaut[i]);
    }
    
	try {
		Carte current = this.fenetre.getEditeur().getCartes().getCurrent();
		if(this.typeCarte == 2) {
			//génération par défaut
			current.frigos = new int[2][2]; // Frigos
			current.nbFrigos = 2;
			current.miresPerso = 500;
			current.zonesRespawn = new int[8];
			current.positionsRespawn = new int[2][2];
			current.hauteurPlafond = 0;
			current.isMapPerso = true;
			current.setFond(-1);
			
			this.fenetre.getEditeur().getFenetre().setEtatTypeCarte("Type de carte : Format 2017");
    		this.fenetre.alreadyPerso = true;
			this.fenetre.menuMapPerso();
		} else {
			current.isMapPerso = false;
			this.fenetre.getEditeur().getFenetre().setEtatTypeCarte("Type de carte : Par défaut");
    		this.fenetre.alreadyPerso = false;
			this.fenetre.menuMapPerso();
		}
	} catch (IOException e) {}
	
    this.fenetre.getEditeur().getCartes().getCurrent().setTypeCarte(this.typeCarte);
    this.fenetre.getEditeur().getImage().redessinner();
    
  }
}
