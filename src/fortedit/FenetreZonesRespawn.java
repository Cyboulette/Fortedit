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

public class FenetreZonesRespawn extends AbstractAction
{
  private static final long serialVersionUID = 1L;
  private Fenetre fenetre;
  private JDialog dialogueFenetre;
  private JPanel dialogue;
  
  private JButton btnEquipeRouge;
  private JButton btnEquipeBleue;
  
  public FenetreZonesRespawn(Fenetre fenetre) throws IOException
  {
    this.fenetre = fenetre;
   
    Carte current = this.fenetre.getEditeur().getCartes().getCurrent();
    
    this.dialogueFenetre = new JDialog();
    this.dialogueFenetre.setTitle("Régler la position des zones de respawn");
    
    this.dialogue = new JPanel();
    this.dialogue.setLayout(new MigLayout());
    this.dialogue.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    
    this.dialogue.add(new JLabel("Les zones de respawn correspondent aux zones bleutées inconstructibles en jeu"), "dock north");
    
    String[] toDisplayTeam = new String[2];
    toDisplayTeam[1] = "bleue";
    toDisplayTeam[0] = "rouge";
    for(int i = 0 ; i < 2; i++) {
    	JButton btnZone = new JButton();
    	btnZone.setText("Positionner la zone de respawn de l'équipe "+toDisplayTeam[i]);
    	btnZone.setActionCommand("zoneSpawn-"+(i+1));
    	btnZone.addActionListener(this);
    	if(i == 0) {
    		btnEquipeRouge = btnZone;
    	}
    	if(i == 1) {
    		btnEquipeBleue = btnZone;
    	}
    }
    
    this.dialogue.add(btnEquipeBleue, "dock south");
    this.dialogue.add(btnEquipeRouge, "dock south");
    
    this.dialogueFenetre.add(this.dialogue);
    this.dialogueFenetre.pack();
    this.dialogueFenetre.setResizable(false);
  }
  
  public void actionPerformed(ActionEvent e)
  {
	Carte current = this.fenetre.getEditeur().getCartes().getCurrent();
	String action = e.getActionCommand();
	String[] actions = action.split("-");
	if(actions[0].equals("zoneSpawn") && !this.fenetre.getEditeur().getImage().respawnZoneState.booleanValue()
			&& !this.fenetre.getEditeur().getImage().isAction.booleanValue()) {
		this.fenetre.getEditeur().getImage().isAction = Boolean.valueOf(true);
		this.fenetre.getEditeur().getImage().respawnZoneState = Boolean.valueOf(true);
		this.fenetre.getEditeur().getImage().currentZone = Integer.parseInt(actions[1]);
		this.fenetre.getEditeur().setEtatCopie("<html><center>Maintenez cliqué et dessinez pour positionner la zone de respawn</center></html>");
	} else {
	    this.dialogueFenetre.setVisible(true);
	    this.dialogueFenetre.setAutoRequestFocus(true);
	    this.dialogueFenetre.setLocationRelativeTo(this.fenetre);
	    this.dialogueFenetre.setAlwaysOnTop(true);
    }
  }
}