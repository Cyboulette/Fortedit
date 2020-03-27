package fortedit;

import fortedit.carte.Carte;
import fortedit.carte.Cartes;
import fortedit.editeur.CarteImage;
import fortedit.editeur.Editeur;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.TransferHandler;

public class CarteEditeurTransferHandler
  extends TransferHandler
{
  private static final long serialVersionUID = 1L;
  private Fenetre fenetre;
  private Editeur editeur;
  
  public CarteEditeurTransferHandler(Fenetre fenetre)
  {
    this.fenetre = fenetre;
  }
  
  private void Charger(String fichierAdresse)
  {
    File fichier = new File(fichierAdresse);
    Editeur editeur = fenetre.getEditeur();
    editeur.setFichier(fichier);
    editeur.setRepertoire(fichier.getParentFile());
    fenetre.setTitle(fichier.getName() + " - Éditeur de cartes pour forteresse");
    String name = fichier.getName();
    String ext = name.substring(name.lastIndexOf(".") + 1);
    System.out.println("Extension du fichier chargé : "+ext);
    if(ext.equals("txt")) {
	    editeur.setEtatFichier(fichier.getAbsolutePath());
	    editeur.getCartes().Init();
	    editeur.getCartes().getCurrent().Load(fichier, fenetre);
      try {
	        BufferedReader fileReader = new BufferedReader(new FileReader(fichier));
	        String line = fileReader.readLine();
	        String[] mapNumber = line.split("-");
	        int map = Integer.parseInt(mapNumber[0]);
	        this.fenetre.getMenuEditionMondesBouton()[map].setSelected(true);
	        this.fenetre.getEditeur().getCartes().getCurrent().setFond(map);
	        fileReader.close();
	      } catch (FileNotFoundException e) {
	        e.printStackTrace();
	        this.fenetre.getMenuEditionMondesBouton()[this.fenetre.getEditeur().getCartes().getCurrent().getFond()].setSelected(true);
	      } catch (IOException e) {
	        e.printStackTrace();
	        this.fenetre.getMenuEditionMondesBouton()[this.fenetre.getEditeur().getCartes().getCurrent().getFond()].setSelected(true);
	      }
	    //fenetre.getMenuEditionMondesBouton()[fenetre.getEditeur().getCartes().getCurrent().getFond()].setSelected(true);
	    //fenetre.getEditeur().getCartes().getCurrent().setFond(fenetre.getEditeur().getCartes().getCurrent().getFond());
	    editeur.getImage().redessinner();
	    editeur.getImage().modif = false;
    } else {
	  final JOptionPane pane = new JOptionPane("Cette carte n'est pas valide");
	  final JDialog d = pane.createDialog((JFrame)null, "Erreur");
	  d.setLocation(200,200);
	  d.setVisible(true);
    }
  }
  
  public boolean canImport(JComponent cp, DataFlavor[] df)
  {
    for (int i = 0; i < df.length; i++)
    {
      if (df[i].equals(DataFlavor.javaFileListFlavor)) {
        return true;
      }
      if (df[i].equals(DataFlavor.stringFlavor)) {
        return true;
      }
    }
    return false;
  }
  
  private boolean hasFileFlavor(DataFlavor[] df)
  {
    boolean result = false;
    DataFlavor[] arrayOfDataFlavor; int j = (arrayOfDataFlavor = df).length; for (int i = 0; i < j; i++) { DataFlavor flavor = arrayOfDataFlavor[i];
      
      result = DataFlavor.javaFileListFlavor.equals(flavor);
      if (result) {
        break;
      }
    }
    return result;
  }
  
  private boolean hasStringFlavor(DataFlavor[] df)
  {
    boolean result = false;
    DataFlavor[] arrayOfDataFlavor; int j = (arrayOfDataFlavor = df).length; for (int i = 0; i < j; i++) { DataFlavor flavor = arrayOfDataFlavor[i];
      
      result = DataFlavor.stringFlavor.equals(flavor);
      if (result) {
        break;
      }
    }
    return result;
  }
  
  public boolean importData(JComponent cp, Transferable tr)
  {
    if (hasFileFlavor(tr.getTransferDataFlavors())) {
      try
      {
        Charger(tr.getTransferData(DataFlavor.javaFileListFlavor).toString().substring(1, tr.getTransferData(DataFlavor.javaFileListFlavor).toString().length() - 1));
        return true;
      }
      catch (Exception e)
      {
        e.printStackTrace();
        
        return false;
      }
    }
    if (hasStringFlavor(tr.getTransferDataFlavors())) {
      try
      {
        String myData = (String)tr.getTransferData(DataFlavor.stringFlavor);
        Charger(myData.substring(5, myData.length() - 2));
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    return false;
  }
}
