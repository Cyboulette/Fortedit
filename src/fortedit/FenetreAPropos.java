package fortedit;

import java.awt.Color;
import java.awt.event.ActionEvent;
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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FenetreAPropos extends AbstractAction
{
  private static final long serialVersionUID = 1L;
  private Fenetre fenetre;
  private JDialog dialogueFenetre;
  private JPanel dialogue;
  
  public FenetreAPropos(Fenetre fenetre) throws IOException
  {
    this.fenetre = fenetre;
    
    this.dialogueFenetre = new JDialog();
    this.dialogueFenetre.setTitle("À propos");
    
    this.dialogue = new JPanel();
    this.dialogue.setLayout(new BoxLayout(this.dialogue, 3));
    this.dialogue.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    


    URL url = new URL("http://cyboulette.fr/extinction/version.txt");
    try {
      InputStream is = url.openConnection().getInputStream();
      
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      
      String lineVersion = null;
      lineVersion = reader.readLine();
      String lineDate = reader.readLine();
      if (!lineVersion.equals(Forteresse.VERSION))
      {

        JLabel update = new JLabel("La mise à jour (" + lineVersion + ") du " + lineDate + " est disponible !", 0);
        update.setForeground(Color.RED);
        this.dialogue.add(update);
      }
      reader.close();
    } catch (IOException e) {
      System.out.println("Problème de vérification des mises à jour, le site doit être HS, merci de contacter Cyboulette !");
    }
    

    this.dialogue.add(new JLabel("Éditeur de cartes pour forteresse version " + Forteresse.VERSION + " du " + Forteresse.CURRENTDATEVERSION));
    this.dialogue.add(new JLabel("Site internet: http://www.lantreduphenix.fr/minijeux/fortedit/"));
    this.dialogue.add(new JLabel("Retrouvez les Mini jeux sur http://www.extinction.fr/minijeux/"));
    this.dialogue.add(new JLabel("Mises à jour disponibles sur : http://cyboulette.fr/extinction/"));
    this.dialogue.add(new JLabel("Ce programme a été développé par dede et mis à jour par Cyboulette"));
    
    this.dialogueFenetre.add(this.dialogue);
    this.dialogueFenetre.pack();
    this.dialogueFenetre.setResizable(false);
  }
  
  public void actionPerformed(ActionEvent e)
  {
    this.dialogueFenetre.setVisible(true);
    this.dialogueFenetre.setLocationRelativeTo(this.fenetre);
    this.dialogueFenetre.setAlwaysOnTop(true);
  }
}
