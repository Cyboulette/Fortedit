package fortedit;

import fortedit.editeur.Editeur;
import java.awt.Color;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.KeyStroke;

public class CarteEditeursetCouleurBouton
  extends JButton
{
  private static final long serialVersionUID = 1L;
  
  public CarteEditeursetCouleurBouton(Editeur editeur, int couleur, int typeColor)
  {
    setAction(new CarteEditeursetCouleurAction(editeur, couleur));
    
    String OS = System.getProperty("os.name").toLowerCase();
    if (!OS.startsWith("mac"))
    {
      Color myColor = null;
      if(typeColor == 0) {
    	  myColor = fortedit.carte.Elements.couleurBoutonBackground[couleur];
      } else {
    	  myColor = fortedit.carte.Elements.couleur[couleur];
      }
      setBackground(myColor);
      int red = myColor.getRed();
      int green = myColor.getGreen();
      int blue = myColor.getBlue();
      
	if(red + green + blue < 383) {
		red = 255;
		green = 255;
		blue = 255;
	} else {
		red = 0;
		green = 0;
		blue = 0;
	}
	
	double Y = 0;
	double r = Math.pow((red/255), 2.2);
	double g = Math.pow((green/255), 2.2);
	double b = Math.pow((blue/255), 2.2);
	Y = 0.2126*r + 0.7151*g + 0.0721*b;
		//System.out.println((int) Math.ceil(Y));
	int rY = (int) Math.ceil(Y);
	  if(rY == 1) {
		  setForeground(Color.WHITE); 
	  } else { 
		  setForeground(Color.BLACK);
	  }
    }
    setText("<html><body><center>" + fortedit.carte.Elements.noms[couleur] + "</center></body></html>");
    
    setPreferredSize(getPreferredSize());
    
    editeur.getInputMap().put(KeyStroke.getKeyStroke("F" + (5 + couleur)), "F" + (5 + couleur));
    editeur.getActionMap().put("F" + (5 + couleur), getAction());
    setToolTipText("F" + (5 + couleur));
    setFocusPainted(false);
    
    setMnemonic(fortedit.carte.Elements.touches[couleur]);
    setFocusable(false);
  }
}
