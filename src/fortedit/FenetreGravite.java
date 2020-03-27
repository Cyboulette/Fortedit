package fortedit;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import fortedit.carte.Carte;

public class FenetreGravite extends AbstractAction
{
  private static final long serialVersionUID = 1L;
  private Fenetre fenetre;
  private JDialog dialogueFenetre;
  private JPanel dialogue;
  private JTextField valeurMire;
  private JButton validateChange;
  
  public FenetreGravite(Fenetre fenetre) throws IOException
  {
    this.fenetre = fenetre;
    
    this.dialogueFenetre = new JDialog();
    this.dialogueFenetre.setTitle("Régler la ligne de gravité de la map");
    
    this.dialogue = new JPanel();
    this.dialogue.setLayout(new BoxLayout(this.dialogue, 3));
    this.dialogue.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    
    valeurMire = new JTextField();
    Carte current = this.fenetre.getEditeur().getCartes().getCurrent();
    valeurMire.setText(Integer.toString(current.miresPerso));
    
    validateChange = new JButton();
    validateChange.addActionListener(this);
    validateChange.setText("Valider");
    
    this.dialogue.add(new JLabel("Position actuelle : "));
    this.dialogue.add(valeurMire);
    this.dialogue.add(validateChange);
    
    
    this.dialogueFenetre.add(this.dialogue);
    this.dialogueFenetre.pack();
    this.dialogueFenetre.setResizable(false);
  }
  
  public void actionPerformed(ActionEvent e)
  {
	Carte current = this.fenetre.getEditeur().getCartes().getCurrent();
	Object source = e.getSource();
	if(source == validateChange) {
		String valeur = valeurMire.getText();
		if(!valeur.isEmpty()) {
			try {
				int valeurInt = Integer.parseInt(valeur);
				if(valeurInt >= 0 && valeurInt <= 1000) {
					if(current.isMapPerso) {
						current.miresPerso = valeurInt;
						this.fenetre.getEditeur().getImage().redessinner();
						this.dialogueFenetre.setVisible(false);
					}
				}
			} catch (NumberFormatException e1) {
				  final JOptionPane pane = new JOptionPane("Merci de rentrer un entier >= 0 et <= 1000");
				  final JDialog d = pane.createDialog((JFrame)null, "Erreur");
				  d.setLocation(200,200);
				  d.setVisible(true);
			}
		}
	} else {
	    this.dialogueFenetre.setVisible(true);
	    this.dialogueFenetre.setAutoRequestFocus(true);
	    this.dialogueFenetre.setLocationRelativeTo(this.fenetre);
	    this.dialogueFenetre.setAlwaysOnTop(true);
	}
  }
}
