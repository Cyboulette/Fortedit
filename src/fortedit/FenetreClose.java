package fortedit;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

class FenetreClose
  implements WindowListener
{
  private FenetreConfirmation fenetreConfirmation;
  
  public FenetreClose(Fenetre fenetre)
  {
    this.fenetreConfirmation = new FenetreConfirmation(fenetre, new FenetreQuitter());
  }
  
  public void windowActivated(WindowEvent arg0) {}
  
  public void windowClosed(WindowEvent arg0) {}
  
  public void windowClosing(WindowEvent arg0)
  {
    this.fenetreConfirmation.actionPerformed(null);
  }
  
  public void windowDeactivated(WindowEvent arg0) {}
  
  public void windowDeiconified(WindowEvent arg0) {}
  
  public void windowIconified(WindowEvent arg0) {}
  
  public void windowOpened(WindowEvent arg0) {}
}
