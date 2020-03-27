package fortedit;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import fortedit.carte.Carte;
import net.miginfocom.swing.MigLayout;

public class FenetreFrigos extends AbstractAction
{
  private static final long serialVersionUID = 1L;
  private Fenetre fenetre;
  private JDialog dialogueFenetre;
  private JPanel dialogue;
  private JButton[] btnsNbFrigos;
  private JButton btnFrigo1;
  private JButton btnFrigo2;
  
  public FenetreFrigos(Fenetre fenetre) throws IOException
  {
    this.fenetre = fenetre;
   
    Carte current = this.fenetre.getEditeur().getCartes().getCurrent();
    
    this.dialogueFenetre = new JDialog();
    this.dialogueFenetre.setTitle("Régler le nombre et la position des frigos");
    
    this.dialogue = new JPanel();
    this.dialogue.setLayout(new MigLayout());
    this.dialogue.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    
    btnsNbFrigos = new JButton[3];
    this.dialogue.add(new JLabel("Nombre de frigos : "), "push, align center");
    
    for(int i = 0; i <= 2; i++) {
    	btnsNbFrigos[i] = new JButton();
    	btnsNbFrigos[i].setText(Integer.toString(i));
    	btnsNbFrigos[i].addActionListener(this);
    	btnsNbFrigos[i].setActionCommand("bouton-"+i);
    	this.dialogue.add(btnsNbFrigos[i], "push, align center");
    }
    
    for(int i = 2 ; i >= 1; i--) {
    	JButton btnPosition = new JButton();
    	btnPosition.setText("Positionner le frigo n°"+i);
    	btnPosition.setActionCommand("positionFrigo-"+i);
    	btnPosition.addActionListener(this);
    	if(i == 1) {
    		btnFrigo1 = btnPosition;
    	}
    	if(i == 2) {
    		btnFrigo2 = btnPosition;
    	}
    }
    
    resetBG(current.nbFrigos);
    this.dialogue.add(btnFrigo2, "dock south");
    this.dialogue.add(btnFrigo1, "dock south");
    
    this.dialogueFenetre.add(this.dialogue);
    this.dialogueFenetre.pack();
    this.dialogueFenetre.setResizable(false);
  }
  
  public void resetBG(int nbFrigosCurrent) {
	  for(int i = 0; i<btnsNbFrigos.length; i++) {
		  if(i != nbFrigosCurrent) {
			  btnsNbFrigos[i].setBackground(null);
		  }
	  }
	  btnsNbFrigos[nbFrigosCurrent].setBackground(Color.GREEN);
  }
  
  public void actionPerformed(ActionEvent e)
  {
	Carte current = this.fenetre.getEditeur().getCartes().getCurrent();
	String action = e.getActionCommand();
	String[] boutons = action.split("-");
	if(boutons[0].equals("bouton") && !this.fenetre.getEditeur().getImage().frigoPositionState.booleanValue()
			&& !this.fenetre.getEditeur().getImage().isAction.booleanValue()) {
		int nbFrigosChoisi = Integer.parseInt(boutons[1]);
		if(nbFrigosChoisi == 2) {
			btnFrigo1.setEnabled(true);
			btnFrigo2.setEnabled(true);
		} else if(nbFrigosChoisi == 1) {
			btnFrigo1.setEnabled(true);
			btnFrigo2.setEnabled(false);
		} else if(nbFrigosChoisi == 0) {
			btnFrigo1.setEnabled(false);
			btnFrigo2.setEnabled(false);
		}
		current.nbFrigos = nbFrigosChoisi;
		resetBG(current.nbFrigos);
		this.fenetre.getEditeur().getImage().redessinner();
	} else if(boutons[0].equals("positionFrigo")) {
		if(!this.fenetre.getEditeur().getImage().frigoPositionState.booleanValue()
				&& !this.fenetre.getEditeur().getImage().isAction.booleanValue()) {
			this.fenetre.getEditeur().getImage().isAction = Boolean.valueOf(true);
			this.fenetre.getEditeur().getImage().frigoPositionState = Boolean.valueOf(true);
			this.fenetre.getEditeur().getImage().currentFrigo = Integer.parseInt(boutons[1]);
			this.fenetre.getEditeur().setEtatCopie("<html><center>Cliquez pour positionner le frigo</center></html>");
		}
	} else {
		if(current.nbFrigos == 2) {
			btnFrigo1.setEnabled(true);
			btnFrigo2.setEnabled(true);
		} else if(current.nbFrigos == 1) {
			btnFrigo1.setEnabled(true);
			btnFrigo2.setEnabled(false);
		} else if(current.nbFrigos == 0) {
			btnFrigo1.setEnabled(false);
			btnFrigo2.setEnabled(false);
		}
		resetBG(current.nbFrigos);
		
	    this.dialogueFenetre.setVisible(true);
	    this.dialogueFenetre.setAutoRequestFocus(true);
	    this.dialogueFenetre.setLocationRelativeTo(this.fenetre);
	    this.dialogueFenetre.setAlwaysOnTop(true);
	}
  }
}
