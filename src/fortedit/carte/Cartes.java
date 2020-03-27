package fortedit.carte;

import java.util.ArrayList;

public class Cartes
{
  private ArrayList<Carte> cartes;
  private int n;
  
  public Cartes()
  {
    this.cartes = new ArrayList();
    Init();
  }
  
  public void Init()
  {
    this.cartes.clear();
    this.cartes.add(new Carte());
    this.n = 0;
  }
  
  public void Ajouter(Carte carte)
  {
    while (this.n != this.cartes.size() - 1) {
      this.cartes.remove(this.n + 1);
    }
    this.cartes.add(carte.clone());
    this.n += 1;
  }
  
  public void Annuler()
  {
    if (this.n != 0) {
      this.n -= 1;
    }
  }
  
  public void Repeter()
  {
    if (this.n != this.cartes.size() - 1) {
      this.n += 1;
    }
  }
  
  public Carte getPrevious()
  {
    return (Carte)this.cartes.get(this.n - 1);
  }
  
  public Carte getCurrent()
  {
    return (Carte)this.cartes.get(this.n);
  }
}
