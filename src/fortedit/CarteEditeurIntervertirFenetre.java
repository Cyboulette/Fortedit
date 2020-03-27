package fortedit;

import fortedit.carte.Elements;
import fortedit.editeur.Editeur;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CarteEditeurIntervertirFenetre
  extends AbstractAction
{
  private static final long serialVersionUID = 1L;
  private Editeur carteEditeur;
  private JDialog dialogueFenetre;
  private JPanel dialogue;
  private JComboBox choix1;
  private JComboBox choix2;
  
  public CarteEditeurIntervertirFenetre(Editeur carteEditeur)
  {
    this.carteEditeur = carteEditeur;
    
    this.dialogueFenetre = new JDialog();
    this.dialogueFenetre.setTitle("Intervertir");
    
    this.dialogue = new JPanel();
    this.choix1 = new JComboBox(Elements.noms);
    this.dialogue.add(getChoix1());
    this.dialogue.add(new JLabel("et"));
    this.choix2 = new JComboBox(Elements.noms);
    this.dialogue.add(getChoix2());
    
    JButton bouton = new JButton(new CarteEditeurIntervertirAction(this.carteEditeur, this));
    bouton.setText("Intervertir");
    this.dialogue.add(bouton);
    
    this.dialogueFenetre.add(this.dialogue);
    this.dialogueFenetre.pack();
    this.dialogueFenetre.setResizable(false);
  }
  
  public void actionPerformed(ActionEvent arg0)
  {
    this.dialogueFenetre.setVisible(true);
    this.dialogueFenetre.setLocationRelativeTo(this.carteEditeur);
    this.dialogueFenetre.setAlwaysOnTop(true);
  }
  
  public JComboBox getChoix1()
  {
    return this.choix1;
  }
  
  public JComboBox getChoix2()
  {
    return this.choix2;
  }
}
