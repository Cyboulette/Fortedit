package fortedit;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Forteresse
{
  public static final String VERSION = new String("2.0.6");
  public static final String CURRENTDATEVERSION = new String("16/05/2019");
  
  public static void main(String[] args) throws IOException, URISyntaxException {
    Fenetre fenetre = new Fenetre();
    ImageIcon img = new ImageIcon("icone.png");
    fenetre.setIconImage(img.getImage());
    fenetre.setVisible(true);
    
    URL url = new URL("http://cyboulette.fr/extinction/version.txt");
    try {
      InputStream is = url.openConnection().getInputStream();
      
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      
      String lineVersion = null;
      lineVersion = reader.readLine();
      String lineDate = reader.readLine();
      if (lineVersion.equals(VERSION)) {
        System.out.println("Vous êtes à jour !");
      } else {
        JDialog dialogueUpdate = new JDialog();
        dialogueUpdate.setTitle("Mise à jour disponible !");
        
        JPanel dialogue = new JPanel();
        dialogue.setLayout(new BoxLayout(dialogue, 3));
        dialogue.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JLabel labelUpdate = new JLabel("La mise à jour (" + lineVersion + ") du " + lineDate + " est disponible !");
        labelUpdate.setForeground(Color.RED);
        JLabel labelUpdateSite = new JLabel("Cliquez sur le bouton ci-dessous pour la télécharger !");
        
        dialogue.add(labelUpdate);
        dialogue.add(labelUpdateSite);
        
        JButton button = new JButton("Télécharger");
        dialogue.add(button);
        button.setBounds(10, 10, 40, 40);
        URI uri = new URI("http://cyboulette.fr/extinction/?file=FortEdit");
        button.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent evt) {
            Forteresse.open(uri);
          }
          
        });
        dialogueUpdate.add(dialogue);
        dialogueUpdate.pack();
        dialogueUpdate.setLocation(100, 100);
        dialogueUpdate.setResizable(false);
        dialogueUpdate.setVisible(true);
        dialogueUpdate.setAlwaysOnTop(true);
        
        System.out.println("Vous n'êtes pas à jour !");
      }
      reader.close();
    } catch (IOException e) {
      System.out.println("Problème de vérification des mises à jour, le site doit être HS, merci de contacter Cyboulette !");
    }
    System.out.println("Version actuelle = " + VERSION);
  }
  

  private static void open(URI uri)
  {
    if (Desktop.isDesktopSupported()) {
      try {
        Desktop.getDesktop().browse(uri);
      }
      catch (IOException localIOException) {}
    }
  }
}
