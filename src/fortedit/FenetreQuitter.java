package fortedit;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class FenetreQuitter
  extends AbstractAction
{
  private static final long serialVersionUID = 1L;
  
  public void actionPerformed(ActionEvent e)
  {
    System.exit(0);
  }
}
