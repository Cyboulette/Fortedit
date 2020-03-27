package fortedit;

import fortedit.editeur.Editeur;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CarteEditeurSymetriserFenetre
  extends AbstractAction
{
  private static final long serialVersionUID = 1L;
  private Editeur editeur;
  private JDialog dialogueFenetre;
  private JPanel dialogue;
  private JComboBox choix;
  
  public CarteEditeurSymetriserFenetre(Editeur editeur)
  {
    this.editeur = editeur;
    
    this.dialogueFenetre = new JDialog();
    this.dialogueFenetre.setTitle("Symétriser");
    
    this.dialogue = new JPanel();
    this.dialogue.add(new JLabel("Garder la partie"));
    Object[] elements = { "gauche", "droite" };
    this.choix = new JComboBox(elements);
    this.dialogue.add(getChoix());
    
    JButton bouton = new JButton(new CarteEditeurSymetriserAction(editeur, this));
    this.dialogue.add(bouton);
    
    this.dialogueFenetre.add(this.dialogue);
    this.dialogueFenetre.pack();
    this.dialogueFenetre.setResizable(false);
  }
  
  public void actionPerformed(ActionEvent arg0)
  {
    this.dialogueFenetre.setVisible(true);
    this.dialogueFenetre.setLocationRelativeTo(this.editeur);
    this.dialogueFenetre.setAlwaysOnTop(true);
  }
  
  public JComboBox getChoix()
  {
    return this.choix;
  }
}
