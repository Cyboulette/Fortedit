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
import javax.swing.JTextField;

import fortedit.carte.Carte;
import net.miginfocom.swing.MigLayout;

public class FenetreBords extends AbstractAction
{
  private static final long serialVersionUID = 1L;
  private Fenetre fenetre;
  private JDialog dialogueFenetre;
  private JPanel dialogue;
  private JTextField hauteurSol;
  private JTextField hauteurPlafond;
  private JButton[][] boutons;
  
  public FenetreBords(Fenetre fenetre) throws IOException
  {
    this.fenetre = fenetre;
   
    Carte current = this.fenetre.getEditeur().getCartes().getCurrent();
    
    this.dialogueFenetre = new JDialog();
    this.dialogueFenetre.setTitle("Régler le plafond et le sol de la map");
    
    this.dialogue = new JPanel();
    this.dialogue.setLayout(new MigLayout());
    this.dialogue.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    
    this.hauteurPlafond = new JTextField();
    this.hauteurPlafond.setText("Plafond : "+current.hauteurPlafond);
    this.hauteurPlafond.setEditable(false);
    this.hauteurPlafond.setFocusable(false);
    
    boutons = new JButton[2][2];
    boutons[0][0] = new JButton();
    boutons[0][0].setText("-");
    boutons[0][0].setActionCommand("minus-plafond");
    boutons[0][0].addActionListener(this);
    boutons[0][0].setFocusable(false);
    
    boutons[0][1] = new JButton();
    boutons[0][1].setText("+");
    boutons[0][1].setActionCommand("plus-plafond");
    boutons[0][1].addActionListener(this);
    boutons[0][1].setFocusable(false);
    
    this.dialogue.add(boutons[0][0], "align center");
    this.dialogue.add(this.hauteurPlafond, "align center");
    this.dialogue.add(boutons[0][1], "align center, wrap");
    
    this.hauteurSol = new JTextField();
    this.hauteurSol.setText("Sol : "+current.hauteurSol);
    this.hauteurSol.setEditable(false);
    this.hauteurSol.setFocusable(false);
    
    boutons[1][0] = new JButton();
    boutons[1][0].setText("-");
    boutons[1][0].setActionCommand("minus-sol");
    boutons[1][0].addActionListener(this);
    boutons[1][0].setFocusable(false);
    
    boutons[1][1] = new JButton();
    boutons[1][1].setText("+");
    boutons[1][1].setActionCommand("plus-sol");
    boutons[1][1].addActionListener(this);
    boutons[1][1].setFocusable(false);
    
    this.dialogue.add(boutons[1][0], "align center");
    this.dialogue.add(this.hauteurSol, "align center");
    this.dialogue.add(boutons[1][1], "align center");
    
    /*btnsNbFrigos = new JButton[3];
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
    
    this.dialogue.add(btnFrigo2, "dock south");
    this.dialogue.add(btnFrigo1, "dock south");*/
    
    this.dialogueFenetre.add(this.dialogue);
    this.dialogueFenetre.pack();
    this.dialogueFenetre.setResizable(false);
  }
  
  public void actionPerformed(ActionEvent e)
  {
	Carte current = this.fenetre.getEditeur().getCartes().getCurrent();
	String action = e.getActionCommand();
	String[] actions = action.split("-");
	if(actions[0].equals("minus")) {
		if(actions[1].equals("plafond")) {
			int nouveauPlafond = current.hauteurPlafond-1;
			if(nouveauPlafond < 0) {
				nouveauPlafond = 0;
			}
			current.hauteurPlafond = nouveauPlafond;
			this.hauteurPlafond.setText("Plafond : "+nouveauPlafond);
		} else if(actions[1].equals("sol")) {
			int nouveauSol = current.hauteurSol-1;
			if(nouveauSol < 0) {
				nouveauSol = 0;
			}
			current.hauteurSol = nouveauSol;
			this.hauteurSol.setText("Sol : "+nouveauSol);
		}
		this.fenetre.getEditeur().getImage().redessinner();
	} else if(actions[0].equals("plus")) {
		if(actions[1].equals("plafond")) {
			int nouveauPlafond = current.hauteurPlafond+1;
			if(nouveauPlafond > 9) {
				nouveauPlafond = 9;
			}
			current.hauteurPlafond = nouveauPlafond;
			this.hauteurPlafond.setText("Plafond : "+nouveauPlafond);
		} else if(actions[1].equals("sol")) {
			int nouveauSol = current.hauteurSol+1;
			if(nouveauSol > 9) {
				nouveauSol = 9;
			}
			current.hauteurSol = nouveauSol;
			this.hauteurSol.setText("Sol : "+nouveauSol);
		}
		this.fenetre.getEditeur().getImage().redessinner();
	} else {		
	    this.dialogueFenetre.setVisible(true);
	    this.dialogueFenetre.setAutoRequestFocus(true);
	    this.dialogueFenetre.setLocationRelativeTo(this.fenetre);
	    this.dialogueFenetre.setAlwaysOnTop(true);
	}
  }
}
