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

public class CarteEditeurConvertirFenetre
  extends AbstractAction
{
  private static final long serialVersionUID = 1L;
  private Editeur carteEditeur;
  private JDialog dialogueFenetre;
  private JPanel dialogue;
  private JComboBox choix1;
  private JComboBox choix2;
  
  public CarteEditeurConvertirFenetre(Editeur carteEditeur)
  {
    this.carteEditeur = carteEditeur;
    
    this.dialogueFenetre = new JDialog();
    this.dialogueFenetre.setTitle("Convertir");
    
    this.dialogue = new JPanel();
    this.choix1 = new JComboBox(Elements.noms);
    this.dialogue.add(getChoix1());
    this.dialogue.add(new JLabel("en"));
    this.choix2 = new JComboBox(Elements.noms);
    this.dialogue.add(getChoix2());
    
    JButton bouton = new JButton(new CarteEditeurConvertirAction(this.carteEditeur, this));
    bouton.setText("Convertir");
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
