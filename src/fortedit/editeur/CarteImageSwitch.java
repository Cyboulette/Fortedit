package fortedit.editeur;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

class CarteImageSwitch
  extends AbstractAction
{
  private static final long serialVersionUID = 1L;
  private int flag;
  private CarteImage image;
  
  CarteImageSwitch(int flag, CarteImage image)
  {
    this.flag = flag;
    this.image = image;
  }
  
  public void actionPerformed(ActionEvent arg0)
  {
    this.image.switchBool(this.flag);
  }
}
