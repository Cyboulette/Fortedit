package fortedit.carte;

import fortedit.CarteEditeurChangerCouleursFenetre;
import fortedit.Fenetre;
import fortedit.mondes.Mondes;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Component;

public class Carte
  implements Cloneable
{
  private int fond;
  private int[][] cases = new int[200][100]; // Anciennement new int['È'][100]
  private int typeCarte;
  
  public int miresPerso = 0; // Ligne de gravité (y)
  public int[][] frigos; // [0][..] = 1er [1][..] = 2eme
  public int[] zonesRespawn; // Contient les zones de respawn
  public int[][] positionsRespawn; // [0][..] = rouge [1][..] = bleu
  public int hauteurPlafond = 0; // Hauteur du plafond de la map
  public int hauteurSol = 0; // Hauteur du sol de la map
  public int nbFrigos = 0; // Nombre de frigos
  public boolean isMapPerso = false; // Est-ce que la map est custom
  
  public Carte()
  {
    setFond(7);
    for (int i = 0; i < 200; i++) {
      for (int j = 0; j < 100; j++) {
        this.cases[i][j] = 1;
      }
    }
  }
  
  public int Load(File file, Fenetre fenetre)
  {
    try
    {
      if (file != null)
      {
        BufferedReader fichier = new BufferedReader(new FileReader(file));
        String fichierS = fichier.readLine();
        fichier.close();
        
        String[] fichierST = fichierS.split("-");
        String mondeS = fichierST[0];
        char[] casesC = new char[20000];
        if(fichierST[1].length() >= 20000 && fichierST[1].length() <= 20003) { //Ancien Format
	        fichierST[1].getChars(0, 19999, casesC, 0);
	        for (int k = 0; k < Mondes.codes.length; k++) {
	          if (mondeS == Mondes.codes[k]) {
	            this.fond = k;
	          }
	        }
	        for (int i = 0; i < 200; i++) {
	          for (int j = 0; j < 100; j++) {
	            for (int k = 0; k < Elements.codes.length; k++) {
	              if (casesC[(i * 100 + j)] == Elements.codes[k]) {
	                this.cases[i][j] = k;
	              }
	            }
	          }
	        }
	        this.typeCarte = 0;
        } else { //Nouveau Format
        	int nbTirets = fichierST.length-1;
        	if(nbTirets >= 16 && fichierS.length() >= 20003 && fichierS.length() <= 20131) { // Format 2017
	        	System.out.println("On a reçu : "+nbTirets+" tirets");
	        	if(nbTirets >= 16) {
	        		Boolean isWithColors = (nbTirets > 20);
	        		System.out.println("On charge du format 2017 (Map Perso)");
	        		System.out.println("Avec couleurs : "+isWithColors);
	        		this.nbFrigos = Integer.parseInt(fichierST[1]);
	        		System.out.println("Avec "+this.nbFrigos+" frigos");
	        		
	        		int nbTiretsForGravite = 0;
	        		//Chargement des frigos
	        		this.frigos = new int[2][2];
	        		if(this.nbFrigos == 2) {
	        			this.frigos[0][0] = Integer.parseInt(fichierST[2])/10; // Frigo rouge x
	        			this.frigos[0][1] = Integer.parseInt(fichierST[3])/10; // Frigo rouge y
	        			this.frigos[1][0] = Integer.parseInt(fichierST[4])/10; // Frigo bleu x
	        			this.frigos[1][1] = Integer.parseInt(fichierST[5])/10; // Frigo bleu y
	        			nbTiretsForGravite = 6;
	        		} else if(this.nbFrigos == 1) {
	        			this.frigos[0][0] = Integer.parseInt(fichierST[2])/10; // Frigo rouge x
	        			this.frigos[0][1] = Integer.parseInt(fichierST[3])/10; // Frigo rouge y
	        			this.frigos[1][0] = 0; // Frigo bleu x
	        			this.frigos[1][1] = 0; // Frigo bleu y
	        			nbTiretsForGravite = 4;
	        		} else if(this.nbFrigos == 0) {
	        			nbTiretsForGravite = 2;
	        		} else {
	        			this.nbFrigos = 0;
	        			nbTiretsForGravite = 2;
	        		}
	        		// Gestion zone de gravité
	        		this.miresPerso = Integer.parseInt(fichierST[nbTiretsForGravite]);
	        		
	        		// Gestion zones de respawn
	        		this.zonesRespawn = new int[8];
	        		this.zonesRespawn[0] = Integer.parseInt(fichierST[nbTiretsForGravite+1]);
	        		this.zonesRespawn[1] = Integer.parseInt(fichierST[nbTiretsForGravite+2]);
	        		this.zonesRespawn[2] = Integer.parseInt(fichierST[nbTiretsForGravite+3]);
	        		this.zonesRespawn[3] = Integer.parseInt(fichierST[nbTiretsForGravite+4]);
	        		this.zonesRespawn[4] = Integer.parseInt(fichierST[nbTiretsForGravite+5]);
	        		this.zonesRespawn[5] = Integer.parseInt(fichierST[nbTiretsForGravite+6]);
	        		this.zonesRespawn[6] = Integer.parseInt(fichierST[nbTiretsForGravite+7]);
	        		this.zonesRespawn[7] = Integer.parseInt(fichierST[nbTiretsForGravite+8]);
	        		
	        		int nbTiretsPositionsRespawn = nbTiretsForGravite+9;
	        		// Gestion positions de spawn des joueurs
	        		this.positionsRespawn = new int[2][2];
	        		this.positionsRespawn[0][0] = Integer.parseInt(fichierST[nbTiretsPositionsRespawn])/10; //x rouge
	        		this.positionsRespawn[0][1] = Integer.parseInt(fichierST[nbTiretsPositionsRespawn+1])/10; //y rouge
	        		this.positionsRespawn[1][0] = Integer.parseInt(fichierST[nbTiretsPositionsRespawn+2])/10; //x bleu
	        		this.positionsRespawn[1][1] = Integer.parseInt(fichierST[nbTiretsPositionsRespawn+3])/10; //y bleu
	        		
	        		int nbTiretsBords = nbTiretsPositionsRespawn+4;
	        		// Gestion des bords de la map
	        		this.hauteurPlafond = Integer.parseInt(fichierST[nbTiretsBords])/10;
	        		if(this.hauteurPlafond > 9) {
	        			this.hauteurPlafond = 9;
	        		}
	        		this.hauteurSol = Integer.parseInt(fichierST[nbTiretsBords])%10;
	        		if(this.hauteurSol > 9) {
	        			this.hauteurSol = 9;
	        		}
	        		
	        		this.typeCarte = 2;
	        		this.isMapPerso = true;
	        		this.fond = -1;
	        		this.loadCases(fenetre, fichierST, (nbTiretsBords+1));
	        	} else {
	        		this.typeCarte = 0;
	        	}
	        } else {
	        	for(int c = 0; c < Elements.couleurHexaDefaut.length; c++) {
	        		Elements.couleur[c] = Color.decode("#"+Elements.couleurHexaDefaut[c]);
	        	}
	        	
	        	System.out.println("On charge du nouveau format");
		        for (int k = 0; k < Mondes.codes.length; k++) {
		          if (mondeS == Mondes.codes[k]) {
		            this.fond = k;
		          }
		        }
		        System.out.println("Map détectée : " + this.fond);
		        System.out.println("Nombre de - : " + fichierST.length);
		        
		        fichierST[fichierST.length-1].getChars(0, 19999, casesC, 0);
		        for(int i = 1; i < fichierST.length-1; i++) {
		        	String[] newFormat = fichierST[i].split(":");
		        	int codeElement = Integer.parseInt(newFormat[0]);
		        	Color colorElement = Color.decode("#"+newFormat[1]);
		        	int position = -1;
		        	for(int a = 0; a < Elements.codes.length; a++) {
		        		if(codeElement == Character.getNumericValue(Elements.codes[a])) {
		        			position = a;
		        			break;
		        		}
		        	}
		        	fortedit.carte.Elements.couleur[position] = colorElement;
		        }
		        for (int i = 0; i < 200; i++) {
		          for (int j = 0; j < 100; j++) {
		            for (int k = 0; k < Elements.codes.length; k++) {
		              if (casesC[(i * 100 + j)] == Elements.codes[k]) {
		                this.cases[i][j] = k;
		              }
		            }
		          }
		        }
	        	CarteEditeurChangerCouleursFenetre refreshColors = new CarteEditeurChangerCouleursFenetre(fenetre.getEditeur(), 0);
	        	for(ActionListener al : fenetre.couleurs.getActionListeners() ) {
	        		fenetre.couleurs.removeActionListener(al);
	        	}
	        	fenetre.couleurs.setText("Changer les couleurs");
	        	fenetre.couleurs.addActionListener(refreshColors);
	        	fenetre.getEditeur().changeBackgroundBoutons(1);
		        this.typeCarte = 1;
	        }
        }
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    
    return this.typeCarte;
  }
  
  public void loadCases(Fenetre fenetre, String[] fichierST, int start) {
	  String string = fichierST[start];
	  char[] chars = string.toCharArray();
	  int i = start;
	  StringBuilder colors = new StringBuilder();
	  while(chars.length <= 19999) {
		  colors.append(string+"-");
		  string = fichierST[i+1];
		  chars = string.toCharArray();
		  i++;
	  }
	  if(colors.length() > 0) { // Si on trouve des couleurs
		  String[] colorST = colors.toString().split("-");
		  for(int j = 0; j < colorST.length; j++) {
			  String[] newFormat = colorST[j].split(":");
			  int codeElement = Integer.parseInt(newFormat[0]);
			  Color colorElement = Color.decode("#"+newFormat[1]);
			  int position = -1;
			  for(int a = 0; a < Elements.codes.length; a++) {
				  if(codeElement == Character.getNumericValue(Elements.codes[a])) {
					  position = a;
					  break;
				  }
			  }
			  //if(codeElement != 9 && codeElement != 0) {
				  fortedit.carte.Elements.couleur[position] = colorElement;
			  //}
		  }
		  
		  CarteEditeurChangerCouleursFenetre refreshColors = new CarteEditeurChangerCouleursFenetre(fenetre.getEditeur(), 0);
		  for(ActionListener al : fenetre.couleurs.getActionListeners() ) {
		  	fenetre.couleurs.removeActionListener(al);
		  }
		  fenetre.couleurs.setText("Changer les couleurs");
		  fenetre.couleurs.addActionListener(refreshColors);
		  fenetre.getEditeur().changeBackgroundBoutons(1);
	  } else {
			fenetre.getEditeur().changeBackgroundBoutons(0);
		    for (int ii = 0; ii < Elements.codes.length; ii++) {
		    	fortedit.carte.Elements.couleur[ii] = Color.decode("#"+fortedit.carte.Elements.couleurHexaDefaut[ii]);
		    }
	  }
	  
	  // Gestion des cases
	  char[] casesC = new char[20000];
	  casesC = chars;
      for (int ii = 0; ii < 200; ii++) {
          for (int jj = 0; jj < 100; jj++) {
            for (int k = 0; k < Elements.codes.length; k++) {
              if (casesC[(ii * 100 + jj)] == Elements.codes[k]) {
                this.cases[ii][jj] = k;
              }
            }
          }
        }
  }
  
  public void Save(File file)
  {
    try
    {
      if (file != null)
      {
        String mondeS = Mondes.codes[this.fond];
        char[] casesC = new char[20000];
        for (int i = 0; i < 200; i++) {
          for (int j = 0; j < 100; j++) {
            casesC[(i * 100 + j)] = Elements.codes[this.cases[i][j]];
          }
        }
        String casesS = new String(casesC);
        
        String fichierS = mondeS + "-" + casesS;
        
        BufferedWriter fichier = new BufferedWriter(new FileWriter(file));
        fichier.write(fichierS);
        fichier.close();
        
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  public final static String toHexString(Color colour) throws NullPointerException {
	  String hexColour = Integer.toHexString(colour.getRGB() & 0xffffff);
	  if (hexColour.length() < 6) {
	    hexColour = "000000".substring(0, 6 - hexColour.length()) + hexColour;
	  }
	  return hexColour.toUpperCase();
	}
  
  public void SaveNewFormat(File file)
  {
    try
    {
      if (file != null)
      {
        String mondeS = Mondes.codes[this.fond];
        
        char[] casesC = new char[20000];
		for (int i = 0; i < 200; i++) {
		    for (int j = 0; j < 100; j++) {
		      casesC[(i * 100 + j)] = Elements.codes[this.cases[i][j]];
		    }
		}
        String casesS = new String(casesC);
        String entete = "";
        for(int i = 0; i <Elements.couleur.length; i++) {
        	int elementColor = Integer.parseInt(Character.toString(Elements.codes[i]));
        	if(!Elements.couleur[i].equals(Color.decode("#"+Elements.couleurHexaDefaut[i]))) {
        		entete = entete+Elements.codes[i]+":"+toHexString(Elements.couleur[i])+"-";
        	}
        }

        String fichierS = mondeS + "-" + entete + casesS;
        
        BufferedWriter fichier = new BufferedWriter(new FileWriter(file));
        fichier.write(fichierS);
        fichier.close();
        
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public void Save2017(File file)
  {
    try
    {
      if (file != null)
      {
        String mondeS = "P";
        
        char[] casesC = new char[20000];
		for (int i = 0; i < 200; i++) {
		    for (int j = 0; j < 100; j++) {
		      casesC[(i * 100 + j)] = Elements.codes[this.cases[i][j]];
		    }
		}
        String casesS = new String(casesC);
        String frigoS;
        if(this.nbFrigos == 2) {
        	frigoS = (this.frigos[0][0]*10)+"-"+(this.frigos[0][1]*10)+"-"+(this.frigos[1][0]*10)+"-"+(this.frigos[1][1]*10);
        } else if(this.nbFrigos == 1) {
        	frigoS = (this.frigos[0][0]*10)+"-"+(this.frigos[0][1]*10);
        } else {
        	frigoS = "";
        }
        
        StringBuilder zonesRespawnString = new StringBuilder();
        for(int i = 0; i<this.zonesRespawn.length; i++) {
        	zonesRespawnString.append(this.zonesRespawn[i]+"-");
        }
        
        StringBuilder positionRespawnString = new StringBuilder();
        for(int i = 0; i<this.positionsRespawn[0].length; i++) {
        	for(int j = 0; j<this.positionsRespawn.length; j++) {
        		int position = this.positionsRespawn[i][j] * 10;
        		positionRespawnString.append(position+"-");
        	}
        }

        StringBuilder frigosString = new StringBuilder();
        if(this.nbFrigos == 0) {
        	frigosString.append(this.nbFrigos);
        } else {
        	frigosString.append(this.nbFrigos+"-"+frigoS);
        }
        
        String parametres = frigosString+"-"+this.miresPerso+"-"+zonesRespawnString+positionRespawnString
        		+this.hauteurPlafond+this.hauteurSol;
        
        String entete = "";
        for(int i = 0; i <Elements.couleur.length; i++) {
        	int elementColor = Integer.parseInt(Character.toString(Elements.codes[i]));
        	if(!Elements.couleur[i].equals(Color.decode("#"+Elements.couleurHexaDefaut[i]))) {
        		entete = entete+Elements.codes[i]+":"+toHexString(Elements.couleur[i])+"-";
        	}
        }

        String fichierS = mondeS + "-" + parametres +"-"+ entete + casesS;
        
        BufferedWriter fichier = new BufferedWriter(new FileWriter(file));
        fichier.write(fichierS);
        fichier.close();
        
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public Carte clone()
  {
    Carte carte = new Carte();
    carte.setFond(this.fond);
    carte.setTypeCarte(this.typeCarte);
    for (int i = 0; i < 200; i++) {
      carte.getCases()[i] = ((int[]) this.cases[i].clone());
    }
    carte.isMapPerso = this.isMapPerso;
    carte.miresPerso = this.miresPerso;
    carte.nbFrigos = this.nbFrigos;
    carte.frigos = this.frigos;
    carte.zonesRespawn = this.zonesRespawn;
    carte.positionsRespawn = this.positionsRespawn;
    carte.hauteurPlafond = this.hauteurPlafond;
    carte.hauteurSol = this.hauteurSol;
    return carte;
  }
  
  public int[][] getCases()
  {
    return this.cases;
  }
  
  public void setFond(int fond)
  {
    this.fond = fond;
  }
  
  public int getFond()
  {
    return this.fond;
  }
  
  public int getTypeCarte() {
	return this.typeCarte;
  }
  
  public void setTypeCarte(int type) {
	this.typeCarte = type;
  }
}
