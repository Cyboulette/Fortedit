package fortedit;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class Filtre
  extends FileFilter
{
  private int j;
  private int i;
  private String extension;
  private int type;
  private String nom;
  public final int TYPE_EXTENSION = 1;
  public final int TYPE_POIDS = 0;
  
  public Filtre(String nom, String extension)
  {
    this.nom = nom;
    this.extension = extension;
    this.type = 1;
  }
  
  public Filtre(String nom, int i, int j)
  {
    this.nom = nom;
    this.i = i;
    this.j = j;
    this.type = 0;
  }
  
  public boolean accept(File f)
  {
    if (f.isDirectory()) {
      return true;
    }
    if (this.type == 0)
    {
      if ((f.length() >= this.i) && (f.length() <= this.j)) {
        return true;
      }
      return false;
    }
    if (this.type == 1)
    {
      String extension = getExtension(f);
      if (extension != null)
      {
        if (extension.equals(this.extension)) {
          return true;
        }
        return false;
      }
    }
    return false;
  }
  
  public String getDescription()
  {
    return this.nom;
  }
  
  public static String getExtension(File f)
  {
    String ext = null;
    String s = f.getName();
    int i = s.lastIndexOf('.');
    if ((i > 0) && (i < s.length() - 1)) {
      ext = s.substring(i + 1).toLowerCase();
    } else {
      ext = ".TXT";
    }
    return ext;
  }
}
