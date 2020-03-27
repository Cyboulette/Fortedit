package fortedit;

import fortedit.carte.Elements;
import fortedit.editeur.Editeur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

public class CarteEditeurChangerCouleursFenetre
  extends AbstractAction
{
  private static final long serialVersionUID = 1L;
  private Editeur editeur;
  private JDialog dialogueFenetre;
  private JPanel dialogue;
  private JComboBox choix;
  public JTextField[] allFields;
  
  public CarteEditeurChangerCouleursFenetre(Editeur editeur)
  {
    this.editeur = editeur;
    
    this.dialogueFenetre = new JDialog();
    this.dialogueFenetre.setTitle("Changer la couleur des blocs");
    
    this.dialogue = new JPanel(new BorderLayout());
    JPanel subPanel = new JPanel();
    
    this.allFields = new JTextField[Elements.codes.length];

    for (int i = 0; i < Elements.codes.length; i++) {
    	int elementColor = Integer.parseInt(Character.toString(Elements.codes[i]));
    	//subPanel.add(new JLabel(fortedit.carte.Elements.noms[i]));
    	this.allFields[i] = new JTextField();
    	this.allFields[i].setColumns(6);
    	this.allFields[i].setDocument(new JTextFieldLimit(6));
    	this.allFields[i].setText(fortedit.carte.Elements.couleurHexaDefaut[i]);
    	this.allFields[i].setBackground(Color.decode("#"+fortedit.carte.Elements.couleurHexaDefaut[i]));
    	this.allFields[i].setForeground(Color.WHITE);
    	//if(elementColor != 9 && elementColor != 0) {
    		subPanel.add(new JLabel(fortedit.carte.Elements.noms[i]));
    		subPanel.add(this.allFields[i], BorderLayout.AFTER_LAST_LINE);
    	//}
    	//subPanel.add(this.allFields[i], BorderLayout.AFTER_LAST_LINE);
    }
    
    JButton save = new JButton();
    save.setText("Sauvegarder");
    save.setEnabled(true);
    
    save.addActionListener(this);
    
    subPanel.add(save, BorderLayout.CENTER);
    
    this.dialogue.add(subPanel);
    
    this.dialogueFenetre.add(this.dialogue);
    this.dialogueFenetre.pack();
    this.dialogueFenetre.setSize(400, 200);
    this.dialogueFenetre.setResizable(false);
  }
 
  public CarteEditeurChangerCouleursFenetre(Editeur editeur, int value)
  {
    this.editeur = editeur;
    
    this.dialogueFenetre = new JDialog();
    this.dialogueFenetre.setTitle("Changer la couleur des blocs");
    
    this.dialogue = new JPanel(new BorderLayout());
    JPanel subPanel = new JPanel();
    
    this.allFields = new JTextField[Elements.codes.length];

    for (int i = 0; i < Elements.codes.length; i++) {
    	int elementColor = Integer.parseInt(Character.toString(Elements.codes[i]));
    	this.allFields[i] = new JTextField();
    	this.allFields[i].setColumns(6);
    	this.allFields[i].setDocument(new JTextFieldLimit(6));
    	String hex = Integer.toHexString(fortedit.carte.Elements.couleur[i].getRGB()).substring(2);
    	this.allFields[i].setText(hex.toUpperCase());
    	this.allFields[i].setBackground(fortedit.carte.Elements.couleur[i]);
    	this.allFields[i].setForeground(Color.WHITE);
    	//if(elementColor != 9 && elementColor != 0) {
    		subPanel.add(new JLabel(fortedit.carte.Elements.noms[i]));
    		subPanel.add(this.allFields[i], BorderLayout.AFTER_LAST_LINE);
    	//}
    }
    
    JButton save = new JButton();
    save.setText("Sauvegarder");
    save.setEnabled(true);
    
    save.addActionListener(this);
    
    subPanel.add(save, BorderLayout.CENTER);
    
    this.dialogue.add(subPanel);
    
    this.dialogueFenetre.add(this.dialogue);
    this.dialogueFenetre.pack();
    this.dialogueFenetre.setSize(400, 200);
    this.dialogueFenetre.setResizable(false);
  }
  
  public void actionPerformed(ActionEvent arg0)
  {
	if(arg0.getActionCommand() == "Sauvegarder") {
		int nbrChangedColors = 0;
		int nbrResetcolors = 0;
		for(int i=0; i < this.allFields.length; i++) {
			//if(i != 0 && i != 9) { // Pour virer le vide
				String coloredString = allFields[i].getText();
				if(!coloredString.equals(Elements.couleurHexaDefaut[i])) {
					if(coloredString.length() == 6) {
						try {
						    Color color = Color.decode("#"+coloredString);
						    if(color.equals(Color.WHITE)) {
						    	allFields[i].setForeground(Color.BLACK);
						    } else {
						    	allFields[i].setForeground(Color.WHITE);
						    }
						    if(color.equals(Color.decode("#"+Elements.couleurHexaDefaut[i]))) {
						    	nbrResetcolors++;
						    }
						    allFields[i].setBackground(color);
							nbrChangedColors++;
							if(editeur.getCartes().getCurrent().getTypeCarte() != 2) {
								editeur.getCartes().getCurrent().setTypeCarte(1);
								editeur.getFenetre().setEtatTypeCarte("Type de carte : Format 2016");
							}
							Elements.couleur[i] = color;
						} catch (Exception e) {
							allFields[i].setText(Elements.couleurHexaDefaut[i]);
							allFields[i].setBackground(Color.decode("#"+Elements.couleurHexaDefaut[i]));
							allFields[i].setForeground(Color.WHITE);
							Elements.couleur[i] = Color.decode("#"+Elements.couleurHexaDefaut[i]);
							nbrResetcolors++;
						}
					} else {
						allFields[i].setText(Elements.couleurHexaDefaut[i]);
						allFields[i].setBackground(Color.decode("#"+Elements.couleurHexaDefaut[i]));
						allFields[i].setForeground(Color.WHITE);
						Elements.couleur[i] = Color.decode("#"+Elements.couleurHexaDefaut[i]);
						nbrResetcolors++;
					}
				} else {
					nbrResetcolors++;
				}
			//}
		}
		
		if(nbrResetcolors == Elements.couleurHexaDefaut.length) {
			if(editeur.getCartes().getCurrent().getTypeCarte() != 2) {
				editeur.getFenetre().setEtatTypeCarte("Type de carte : Par défaut");
				editeur.getCartes().getCurrent().setTypeCarte(0);
			}
			editeur.changeBackgroundBoutons(0);
			for(int i=0; i < Elements.codes.length; i++) {
				Elements.couleur[i] = Color.decode("#"+ Elements.couleurHexaDefaut[i]);
				allFields[i].setText(Elements.couleurHexaDefaut[i]);
				allFields[i].setBackground(Color.decode("#"+Elements.couleurHexaDefaut[i]));
				allFields[i].setForeground(Color.WHITE);
			}
		} else {
			editeur.changeBackgroundBoutons(1);
		}
		System.out.println("Type de carte actuelle : "+editeur.getCartes().getCurrent().getTypeCarte());
		this.dialogueFenetre.setVisible(false);
	} else {
	    this.dialogueFenetre.setVisible(true);
	    this.dialogueFenetre.setLocationRelativeTo(this.editeur);
	    this.dialogueFenetre.setAlwaysOnTop(true);
	}
  }
  
  public JComboBox getChoix()
  {
    return this.choix;
  }
}
