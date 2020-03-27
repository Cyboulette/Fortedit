package fortedit.editeur;

import fortedit.CarteEditeursetCouleurBouton;
import fortedit.carte.Carte;
import fortedit.carte.Cartes;
import fortedit.carte.Elements;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;



public class CarteImage
  extends JPanel
  implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener
{
  private static final long serialVersionUID = 1L;
  public int couleur = 1;
  private int i0;
  private int j0;
  private int i1;
  private int j1;
  private int x;
  private int y;
  private long temps;
  public Boolean modif = Boolean.valueOf(false);
  public Boolean encours = Boolean.valueOf(false);
  public Boolean libre = Boolean.valueOf(false);
  public Boolean grille = Boolean.valueOf(false);
  public Boolean fond = Boolean.valueOf(true);
  public Boolean zones = Boolean.valueOf(true);
  public Boolean mire = Boolean.valueOf(true);
  public Boolean zoom = Boolean.valueOf(false);
  public Boolean copy = Boolean.valueOf(false);
  public Boolean frigos = Boolean.valueOf(true);
  public Boolean spawns = Boolean.valueOf(true);
  public Boolean daltonien = Boolean.valueOf(false);
  static final int switchGrille = 0;
  static final int switchFond = 1;
  static final int switchZones = 2;
  static final int switchMire = 3;
  static final int switchZoom = 4;
  static final int switchLibre = 5;
  private AbstractAction switchGrilleAction;
  private AbstractAction switchFondAction;
  private AbstractAction switchZonesAction;
  private AbstractAction switchFrigosAction;
  private AbstractAction switchSpawnsAction;
  private AbstractAction switchMireAction;
  private AbstractAction switchZoomAction;
  private AbstractAction switchLibreAction;
  private AbstractAction switchDaltonienAction;
  public int pas = 10;
  public int actualZoom = pas;
  public Image fondImg;
  public int fondActuel = -1;
  public BufferedImage image;
  public BufferedImage imageTemp;
  public Editeur carteEditeur;
  private CarteEditeursetCouleurBouton setBtn;
  
  //COPY/COLLE
  public Boolean copyState = Boolean.valueOf(false);
  public Boolean isCopied = Boolean.valueOf(false);
  public Boolean isPasted = Boolean.valueOf(false);
  
  //POSITION FRIGO
  public Boolean frigoPositionState = Boolean.valueOf(false);
  public int currentFrigo = 0;
  
  //POSITION RESPAWN
  public Boolean respawnPositionState = Boolean.valueOf(false);
  public int currentRespawn = 0;
  
  //ZONE RESPAWN
  public Boolean respawnZoneState = Boolean.valueOf(false);
  public int currentZone = 0;
  
  public Boolean isAction = Boolean.valueOf(false);
  
  public int[][] copiedCases = new int[201][101];
  //public Stack<String> pixelsPile = new Stack<String>();

  public int nbrCopiedCases = 0;
  public int nbrCopiedCasesX = 0;
  public int nbrCopiedCasesY = 0;
  public int startCopieX = 0;
  public int startCopieY = 0;
  public int endCopieX = 0;
  public int endCopieY = 0;
  
  public CarteImage(Editeur carteEditeur)
  {
    super(new BorderLayout());
    
    this.carteEditeur = carteEditeur;
    this.switchGrilleAction = new CarteImageSwitch(0, this);
    this.switchFondAction = new CarteImageSwitch(1, this);
    this.switchZonesAction = new CarteImageSwitch(2, this);
    this.switchMireAction = new CarteImageSwitch(3, this);
    this.switchZoomAction = new CarteImageSwitch(4, this);
    this.switchLibreAction = new CarteImageSwitch(5, this);
    this.switchFrigosAction = new CarteImageSwitch(6, this);
    this.switchSpawnsAction = new CarteImageSwitch(7, this);
    this.switchDaltonienAction = new CarteImageSwitch(8, this);
    
    setPreferredSize(new Dimension(this.pas * 200, this.pas * 100));
    addMouseListener(this);
    addMouseMotionListener(this);
    addMouseWheelListener(this);
    addKeyListener(this);
    this.image = new BufferedImage(this.pas * 200, this.pas * 100, 2);
    this.imageTemp = new BufferedImage(this.pas * 200, this.pas * 100, 2);
    redessinner();
  }
  
  public void switchBool(int flag)
  {
    switch (flag)
    {
    case 0: 
      this.grille = Boolean.valueOf(!this.grille.booleanValue());
      break;
    case 1: 
      this.fond = Boolean.valueOf(!this.fond.booleanValue());
      break;
    case 2: 
      this.zones = Boolean.valueOf(!this.zones.booleanValue());
      break;
    case 3: 
      this.mire = Boolean.valueOf(!this.mire.booleanValue());
      break;
    case 4: 
      this.zoom = Boolean.valueOf(!this.zoom.booleanValue());
      
	  if(this.zoom.booleanValue() == false) {
		  this.pas = this.actualZoom;
	  } else {
		  this.pas = (5);
	  }

	  this.image = new BufferedImage(200 * this.pas, 100 * this.pas, 2);
	  setPreferredSize(new Dimension(this.pas * 200, this.pas * 100));
	  
	  redessinner();
	  revalidate();
	  break;
      /*
      this.pas = (15 - this.pas);
      this.image = new BufferedImage(200 * this.pas, 100 * this.pas, 2);
      setPreferredSize(new Dimension(this.pas * 200, this.pas * 100));
      redessinner();
      revalidate();
      break;*/
    case 5: 
      if(!this.isAction.booleanValue()) {
    	  this.libre = Boolean.valueOf(!this.libre.booleanValue());
      } else {
    	  this.carteEditeur.getFenetre().mode.setSelected(false);
    	  this.libre = Boolean.valueOf(false);
      }
      break;
    case 6:
      this.frigos = Boolean.valueOf(!this.frigos.booleanValue());
      break;
    case 7:
      this.spawns = Boolean.valueOf(!this.spawns.booleanValue());
      break;
    case 8:
    	this.daltonien = Boolean.valueOf(!this.daltonien.booleanValue());
    	if(this.daltonien.booleanValue()) {
    		Elements.couleur[0] = Color.decode("#0008AF");
    		Elements.couleur[2] = Color.yellow;
    		this.carteEditeur.changeBackgroundBoutons(1);
    	} else {
    		Elements.couleur[0] = Color.decode("#"+Elements.couleurHexaDefaut[0]);
    		if(this.carteEditeur.getFenetre().changementCouleurs.allFields[2].getText().equals(Elements.couleurHexaDefaut[2])) {
    			Elements.couleur[2] = Color.decode("#"+Elements.couleurHexaDefaut[2]);
    			this.carteEditeur.changeBackgroundBoutons(0);
    		} else {
    			ActionEvent e = new ActionEvent(this.carteEditeur.getFenetre().changementCouleurs,1234,"Sauvegarder");
    			this.carteEditeur.getFenetre().changementCouleurs.actionPerformed(e);
    			this.carteEditeur.changeBackgroundBoutons(1);
    		}
    	}
      break;
    }
    redessinner();
  }
  
  public void paint(Graphics g)
  {
    g.clearRect(0, 0, getWidth(), getHeight());
    g.drawImage(this.image, 0, 0, null);
    if (this.encours.booleanValue()) {
      g.drawImage(this.imageTemp, 0, 0, null);
    }
  }
  
  public void redessinner()
  {
    if (this.carteEditeur.getCartes().getCurrent().getFond() != this.fondActuel)
    {
      this.fondActuel = this.carteEditeur.getCartes().getCurrent().getFond();
      if(this.fondActuel >= 0) {
	      this.fondImg = getToolkit().getImage(getClass().getResource("/fortedit/mondes/" + fortedit.mondes.Mondes.codes[this.fondActuel] + ".png"));
	      this.fondImg = new ImageIcon(this.fondImg).getImage();
      } else {
    	  this.fondImg = null;
      }
    }
    if (!this.encours.booleanValue())
    {
      dessinner(this.image.getGraphics());
    }
    else if (this.libre.booleanValue())
    {
    	if(!this.isAction.booleanValue()) {
		  Graphics g = this.imageTemp.getGraphics();
		  g.setColor(fortedit.carte.Elements.couleur[this.couleur]);
		  g.fillRect(this.i1 * this.pas, this.j1 * this.pas, this.pas, this.pas);
    	}
    }
    else
    {
      this.imageTemp = new BufferedImage(this.pas * 200, this.pas * 100, 2);
      Graphics g = this.imageTemp.getGraphics();
      if(this.copyState.booleanValue() && !this.isCopied.booleanValue()) {
      	g.setColor(Color.WHITE);
      } else if(this.copyState.booleanValue() && this.isCopied.booleanValue()) {
      	g.setColor(Color.RED);
      } else if(this.respawnZoneState.booleanValue() && this.isAction.booleanValue()) {
    	g.setColor(Color.ORANGE);
      } else{
      	g.setColor(Color.GREEN);
      }
      g.drawRect(Math.min(this.i0, this.i1) * this.pas, Math.min(this.j0, this.j1) * this.pas, (Math.abs(this.i0 - this.i1) + 1) * this.pas, (Math.abs(this.j0 - this.j1) + 1) * this.pas);
    }
    repaint();
  }
  
  public void dessinner(Graphics g)
  {
	Carte current = this.carteEditeur.getCartes().getCurrent(); // map actuelle
	// Dessine less cases et rempli les couleurs
    for (int i = 0; i < 200; i++) {
      for (int j = 0; j < 100; j++)
      {
        g.setColor(fortedit.carte.Elements.couleur[this.carteEditeur.getCartes().getCurrent().getCases()[i][j]]);
        g.fillRect(i * this.pas, j * this.pas, this.pas, this.pas);
      }
    }
    
    // Gestion de la grille
    if (this.grille.booleanValue())
    {
      g.setColor(Color.WHITE);
      for (int i = 1; i < 200; i++) {
        g.drawLine(i * this.pas, 0, i * this.pas, 100 * this.pas);
      }
      for (int j = 1; j < 100; j++) {
        g.drawLine(0, j * this.pas, 200 * this.pas, j * this.pas);
      }
    }
    
    // Gestion de la map / bordures
    if (this.fond.booleanValue()) {
      g.drawImage(this.fondImg, 0, 0, 200 * this.pas, 100 * this.pas, this);
      //1 PX = this.pas
      // Gestion des hauteurs de la map
      	if(current.isMapPerso) {
      		g.setColor(Color.BLACK);
      		g.fillRect(0 * this.pas, 0 * this.pas, 200*this.pas, current.hauteurPlafond * this.pas / 10);
      		/*g.fillRect(0 * this.pas, (100 - current.hauteurSol) * this.pas, 200*this.pas, current.hauteurSol * this.pas);
      		System.out.println((100 - current.hauteurSol) * this.pas);
      		System.out.println(current.hauteurSol * this.pas);*/
      		//g.setColor(Color.RED);
      		g.fillRect(0, 100 * this.pas - current.hauteurSol * this.pas / 10, 200*this.pas, current.hauteurSol * this.pas / 10 );
      	}
    }
    
    // Gestion des frigos
    if(this.frigos.booleanValue()) {
    	if(current.isMapPerso) {
			g.setColor(Color.BLUE);
			Image img = getToolkit().getImage(getClass().getResource("/fortedit/mondes/frigo.png"));
			int width = (47*this.pas)/10;
			int hauteur = (57*this.pas)/10;
			int coordX, coordY;
			if(current.nbFrigos == 2) {
				coordX = (current.frigos[0][0])*this.pas;
				coordY = (current.frigos[0][1])*this.pas;
				g.fillRect(coordX, coordY, this.pas, this.pas);
				g.drawImage(img, coordX, coordY, width, hauteur, null);

				coordX = (current.frigos[1][0])*this.pas;
				coordY = (current.frigos[1][1])*this.pas;
				g.fillRect(coordX, coordY, this.pas, this.pas);
				g.drawImage(img, coordX, coordY, width, hauteur, null);
			}
			if(current.nbFrigos == 1) {
				g.fillRect((current.frigos[0][0])*this.pas, (current.frigos[0][1])*this.pas, this.pas, this.pas);
				g.drawImage(img, (current.frigos[0][0])*this.pas, (current.frigos[0][1])*this.pas, width, hauteur, null);
			}
    	}
    }
    
    // Gestion des points de spawn
    if(this.spawns.booleanValue()) {
    	if(current.isMapPerso) {
    		g.setColor(Color.WHITE);
    		int coordX, coordY;
    		coordX = (current.positionsRespawn[0][0]) * this.pas;
    		coordY = (current.positionsRespawn[0][1]) * this.pas;
    		g.fillRect(coordX, coordY, this.pas, this.pas);
    		
    		coordX = (current.positionsRespawn[1][0]) * this.pas;
    		coordY = (current.positionsRespawn[1][1]) * this.pas;
    		g.fillRect(coordX, coordY, this.pas, this.pas);
    	}
    }
    
    // Gestion des zones de respawn (non constructible)
    if (this.zones.booleanValue())
    {
      g.setColor(Color.RED);
      if(current.isMapPerso) {
		// Respawn 1
		for (int i = current.zonesRespawn[0]; i < current.zonesRespawn[2]; i++) {
			for (int j = current.zonesRespawn[1]; j < current.zonesRespawn[3]; j++) {
				g.drawRect(i * this.pas, j * this.pas, this.pas, this.pas);
			}
		}
		
		// Respawn 2
		for (int i = current.zonesRespawn[4]; i < current.zonesRespawn[6]; i++) {
			for (int j = current.zonesRespawn[5]; j < current.zonesRespawn[7]; j++) {
				g.drawRect(i * this.pas, j * this.pas, this.pas, this.pas);
			}
		}
      } else {
	      for (int i = fortedit.mondes.Mondes.zones[this.carteEditeur.getCartes().getCurrent().getFond()][0]; i < fortedit.mondes.Mondes.zones[this.carteEditeur.getCartes().getCurrent().getFond()][2]; i++) {
	        for (int j = fortedit.mondes.Mondes.zones[this.carteEditeur.getCartes().getCurrent().getFond()][1]; j < fortedit.mondes.Mondes.zones[this.carteEditeur.getCartes().getCurrent().getFond()][3]; j++) {
	          g.drawRect(i * this.pas, j * this.pas, this.pas, this.pas);
	        }
	      }
	      for (int i = fortedit.mondes.Mondes.zones[this.carteEditeur.getCartes().getCurrent().getFond()][4]; i < fortedit.mondes.Mondes.zones[this.carteEditeur.getCartes().getCurrent().getFond()][6]; i++) {
	        for (int j = fortedit.mondes.Mondes.zones[this.carteEditeur.getCartes().getCurrent().getFond()][5]; j < fortedit.mondes.Mondes.zones[this.carteEditeur.getCartes().getCurrent().getFond()][7]; j++) {
	          g.drawRect(i * this.pas, j * this.pas, this.pas, this.pas);
	        }
	      }
      }
    }
    
    // gestion des mires + ligne de gravité
    if (this.mire.booleanValue())
    {
      g.setColor(Color.RED);
      if(current.isMapPerso) {
          g.drawLine(100 * this.pas, 0, 100 * this.pas, 100 * this.pas); // Ligne X (milieu map)
          g.drawLine(0, current.miresPerso * this.pas / 10, 200 * this.pas, current.miresPerso * this.pas / 10); // Ligne y (gravité)
      } else {
          g.drawLine(100 * this.pas, 0, 100 * this.pas, 100 * this.pas);
          g.drawLine(0, fortedit.mondes.Mondes.mires[this.carteEditeur.getCartes().getCurrent().getFond()] * this.pas / 10, 200 * this.pas, fortedit.mondes.Mondes.mires[this.carteEditeur.getCartes().getCurrent().getFond()] * this.pas / 10);
      }
    }
  }
  
  public void mouseClicked(MouseEvent arg0) {}
  
  public void mouseEntered(MouseEvent arg0) {}
  
  public void mouseExited(MouseEvent arg0) {}
  
  public void mousePressed(MouseEvent arg0)
  {
    if (SwingUtilities.isLeftMouseButton(arg0))
    {
      this.carteEditeur.getCartes().Ajouter(this.carteEditeur.getCartes().getCurrent()); // Penser à modifie rle clone pour le nouveau format
      if (this.libre.booleanValue())
      {
    	if(!this.isAction.booleanValue()) {
	        this.carteEditeur.getCartes().Ajouter(this.carteEditeur.getCartes().getCurrent());
	        this.carteEditeur.getCartes().getCurrent().getCases()[this.i1][this.j1] = this.couleur;
	        this.imageTemp = new BufferedImage(this.pas * 200, this.pas * 100, 2);
    	}
      }
      else
      {
        this.i0 = this.i1;
        this.j0 = this.j1;
        repaint();
      }
      this.encours = Boolean.valueOf(true);
      redessinner();
      
      if(this.isCopied.booleanValue() && this.isAction.booleanValue()){
    	  int debI = i0+1;
    	  int debJ = j0+1;
    	  
    	  int debutCopiedX = this.startCopieX;
    	  int debutCopiedY = this.startCopieY;
    	  
    	  int evolutionX = debutCopiedX;
    	  int evolutionY = debutCopiedY;
    	  
    	  int finCopieX = this.endCopieX;
    	  int finCopieY = this.endCopieY;
    	  
    	  for(int j=debJ;j<debJ+nbrCopiedCasesY;j++) {
    		  for(int i=debI;i<debI+nbrCopiedCasesX;i++) {
    			  int currentCase = 0;
    			  if(evolutionX < 201 && evolutionY < 101) {
    				  currentCase = copiedCases[evolutionX][evolutionY];
    			  }
				  if((i-1) < 200 && (j-1) < 100) {
					  this.carteEditeur.getCartes().getCurrent().getCases()[i-1][j-1] = currentCase;
				  }
				  
				  evolutionX++;
				  if(evolutionX > finCopieX) {
					  evolutionX = debutCopiedX;
					  break;
				  }
    		  }
    		  evolutionY++;
    		  if(evolutionY > finCopieY) {
    			  evolutionY = debutCopiedY;
    			  break;
    		  }
    	  }
    	  
    	  this.copiedCases = new int[201][101];
    	  
    	  this.copyState = Boolean.valueOf(false);
    	  this.isCopied = Boolean.valueOf(false);
    	  this.isPasted = Boolean.valueOf(true);
    	  this.isAction = Boolean.valueOf(false);
    	  this.carteEditeur.setEtatCopie("Copie : Désactivée");
    	  this.carteEditeur.visibleButtonSaveForme(false);
    	  this.nbrCopiedCases = 0;
    	  this.nbrCopiedCasesX = 0;
    	  this.nbrCopiedCasesY = 0;
    	  this.startCopieX = 0;
    	  this.startCopieY = 0;
    	  this.endCopieX = 0;
    	  this.endCopieY = 0;
    	  evolutionX = 0;
    	  evolutionY = 0;
      }
      int testX = (arg0.getX() / this.pas);
      int testY = (arg0.getY() / this.pas);
      if (testX >= 200) {
    	  testX = 199;
      } else if (testX < 0) {
        testX = 0;
      }
      if (testY >= 100) {
    	  testY = 99;
      } else if (testY < 0) {
    	  testY = 0;
      }
      //remplirCouleur(testX, testY, currentCase, this.couleur);
      //floodFillScanlineStack(testX, testY, currentCase, this.couleur);
      /*if(!this.libre.booleanValue()) {
    	  remplissage4(testX, testY);
      }*/
    }
    else if (SwingUtilities.isMiddleMouseButton(arg0))
    {
      this.x = (arg0.getX() - getVisibleRect().x);
      this.y = (arg0.getY() - getVisibleRect().y);
      this.temps = new Date().getTime();
    }
    else if (SwingUtilities.isRightMouseButton(arg0))
    {
      if(!this.isCopied.booleanValue()) {
    	  this.couleur = this.carteEditeur.getCartes().getCurrent().getCases()[(arg0.getX() / this.pas)][(arg0.getY() / this.pas)];
      } else {
    	  redessinner();
    	  repaint();
    	  Graphics g = this.image.getGraphics();
    	  g.setColor(Color.green);
    	  g.drawRect(i1 * this.pas, j1 * this.pas, (1) * this.pas, (1) * this.pas);
    	  this.copiedCases = new int[201][101];
    	  
    	  this.copyState = Boolean.valueOf(false);
    	  this.isAction = Boolean.valueOf(false);
    	  this.isCopied = Boolean.valueOf(false);
    	  this.isPasted = Boolean.valueOf(false);
    	  this.carteEditeur.setEtatCopie("Copie : Désactivée");
    	  this.carteEditeur.visibleButtonSaveForme(false);
    	  this.nbrCopiedCases = 0;
    	  this.nbrCopiedCasesX = 0;
    	  this.nbrCopiedCasesY = 0;
    	  this.startCopieX = 0;
    	  this.startCopieY = 0;
    	  this.endCopieX = 0;
    	  this.endCopieY = 0;
      }
    }
  }
  
  public void saveForme() {
	  StringBuilder sb = new StringBuilder();
	  JFrame frame = new JFrame("Nom de votre forme ?");
	  String name = JOptionPane.showInputDialog(frame, "Nom de votre forme ?");
	  if(name.length() > 3 && !name.equals("")) {
		  String replacedName = name.replace("-", "");
		  replacedName = replacedName.replace(";", "");
		  replacedName = replacedName.replace("'", "");
		  replacedName = replacedName.replace("\"", "");
		  System.out.println(replacedName);
		  sb.append(replacedName+";");
	  } else {
		  sb.append("Forme sans nom;");
	  }
	  
	  int debI = 0;
	  int debJ = 0;
	  
	  int debutCopiedX = this.startCopieX;
	  int debutCopiedY = this.startCopieY;
	  
	  int evolutionX = debutCopiedX;
	  int evolutionY = debutCopiedY;
	  
	  int finCopieX = this.endCopieX;
	  int finCopieY = this.endCopieY;
	  
	  for(int j=debJ;j<debJ+nbrCopiedCasesY;j++) {
		  for(int i=debI;i<debI+nbrCopiedCasesX;i++) {
			  int currentCase = 0;
			  if(evolutionX < 201 && evolutionY < 101) {
				  currentCase = copiedCases[evolutionX][evolutionY];
			  }
			  if((i-1) < 200 && (j-1) < 100) {
				  sb.append(currentCase);
			  }
			  
			  evolutionX++;
			  if(evolutionX > finCopieX) {
				  evolutionX = debutCopiedX;
				  break;
			  }
		  }
		  sb.append("-");
		  evolutionY++;
		  if(evolutionY > finCopieY) {
			  evolutionY = debutCopiedY;
			  break;
		  }
	  }
	  
	  sb.setLength(sb.length() - 1);
	  try {
		  File file = new File("formes.txt");
		  if(!file.exists() && !file.isDirectory()) {
			  file.createNewFile();
		  }
		  
			InputStream is = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null, tmp;
			while((tmp = br.readLine()) != null) {
				line = tmp;
			}
			if(line != null) {
				String lastLine = line;
				String values[] = lastLine.split(";");
				int lastID = Integer.parseInt(values[2]);
				int nextID = lastID+1;
				sb.append(";"+nextID);
			} else {
				sb.append(";0");
			}
			System.out.println("SB = "+sb);
		  OutputStream output = new FileOutputStream(file, true);
		  PrintStream printStream = new PrintStream(output);
		  printStream.println(sb);
		  printStream.flush();
		  printStream.close();
		  
		  final JOptionPane pane = new JOptionPane("Forme sauvegardée avec succès ! ");
		  final JDialog d = pane.createDialog((JFrame)null, "Sauvegarde de forme");
		  d.setAlwaysOnTop(true);
		  d.setLocation(200,200);
		  d.setVisible(true);
	  } catch(Exception e) {
		  final JOptionPane pane = new JOptionPane("Erreur lors de la sauvegarde ! ");
		  final JDialog d = pane.createDialog((JFrame)null, "Erreur");
		  d.setAlwaysOnTop(true);
		  d.setLocation(200,200);
		  d.setVisible(true);
	  }
  }
  
  public void remplirCouleur(int x, int y, int colcible, int colrep) {
	  /*if(x < 0 || x >= 200 || y < 0 || y >= 100) {
		  return;
	  } else {
		  int currentCase = this.carteEditeur.getCartes().getCurrent().getCases()[y][x];
		  if(currentCase != this.couleur && casesTestees[x][y] != true) {
			  casesTestees[x][y] = true;
			  //remplirCouleur(x-1, y);
			  //remplirCouleur(x+1, y);
			  remplirCouleur(x, y+1);
			  remplirCouleur(x, y-1);
			  this.carteEditeur.getCartes().getCurrent().getCases()[x][y] = this.couleur;
		  }
	  }*/
  }
  
  public void remplissage4(int x, int y) {
	  Stack<String> pixelsPile = new Stack<String>();
	  int currentCase = this.carteEditeur.getCartes().getCurrent().getCases()[x][y];
	  if(currentCase == this.couleur) {
		  System.out.println("Bah c'est déjà la même couleur :/");
		  return;
	  } else {
		  this.carteEditeur.getCartes().getCurrent().getCases()[x][y] = this.couleur;
		  StringBuilder sb = new StringBuilder();
		  pixelsPile.push(x+";"+y); // On push la case actuelle.
		  while(!pixelsPile.empty()) {
			  System.out.println("On rentre dans la pile");
			  String pixelToTest = pixelsPile.pop(); // On remove le 1er élement de la pile
			  System.out.println(pixelToTest);
			  String values[] = pixelToTest.split(";");
			  int newX = Integer.parseInt(values[0]);
			  int newY = Integer.parseInt(values[1]);
			  if(newX < 200 && newY < 100) {
				  if((newX+1) < 200) {
					  int x1 = this.carteEditeur.getCartes().getCurrent().getCases()[(newX+1)][newY];
					  System.out.println(x1);
					  if(x1 != this.couleur && (x1 == 0 || x1 == 1)) {
						  this.carteEditeur.getCartes().getCurrent().getCases()[(newX+1)][newY] = this.couleur;
						  pixelsPile.push((newX+1)+";"+newY);
					  }
				  }
				  if((newX-1) > 0) {
					  int x1 = this.carteEditeur.getCartes().getCurrent().getCases()[(newX-1)][newY];
					  if(x1 != this.couleur && (x1 == 0 || x1 == 1)) {
						  this.carteEditeur.getCartes().getCurrent().getCases()[(newX-1)][newY] = this.couleur;
						  pixelsPile.push((newX-1)+";"+newY);
					  }
				  }
				  if((newY+1) < 100) {
					  int y1 = this.carteEditeur.getCartes().getCurrent().getCases()[newX][(newY+1)];
					  if(y1 != this.couleur && (y1 == 0 || y1 == 1)) {
						  this.carteEditeur.getCartes().getCurrent().getCases()[newX][(newY+1)] = this.couleur;
						  pixelsPile.push(newX+";"+(newY+1));
					  }
				  }
				  if((newY-1) > 0) {
					  int y1 = this.carteEditeur.getCartes().getCurrent().getCases()[newX][(newY-1)];
					  if(y1 != this.couleur && (y1 == 0 || y1 == 1)) {
						  this.carteEditeur.getCartes().getCurrent().getCases()[newX][(newY-1)] = this.couleur;
						  pixelsPile.push(newX+";"+(newY-1));
					  }
				  }
			  }
			  /*if(newX < 200 && newY < 100) {
				  if((newX+1) < 200 && this.carteEditeur.getCartes().getCurrent().getCases()[newX+1][newY] == colcible) {
					  this.carteEditeur.getCartes().getCurrent().getCases()[newX+1][newY] = colrep;
					  pixelsPile.push((newX+1)+";"+newY);
				  }
				  if((newX-1) > 0 && this.carteEditeur.getCartes().getCurrent().getCases()[newX-1][newY] == colcible) {
					  this.carteEditeur.getCartes().getCurrent().getCases()[newX-1][newY] = colrep;
					  pixelsPile.push((newX-1)+";"+newY);
				  }
				  if((newY+1) < 100 && this.carteEditeur.getCartes().getCurrent().getCases()[newX][newY+1] == colcible) {
					  this.carteEditeur.getCartes().getCurrent().getCases()[newX][newY+1] = colrep;
					  pixelsPile.push(newX+";"+(newY+1));
				  }
				  if((newY-1) > 0 && this.carteEditeur.getCartes().getCurrent().getCases()[newX][newY-1] == colcible) {
					  this.carteEditeur.getCartes().getCurrent().getCases()[newX][newY-1] = colrep;
					  pixelsPile.push(newX+";"+(newY-1));
				  }
			  }*/
		  }
	  }
  }
  
  	boolean intersectRect(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2) {
	    return intersectRange(x1, x1+w1, x2, x2+w2)
	        && intersectRange(y1, y1+h1, y2, y2+h2);
	}
	boolean intersectRange(int ax1, int ax2, int bx1, int bx2) {
	    return Math.max(ax1, bx1) <= Math.min(ax2, bx2);
	}
  
  public void mouseReleased(MouseEvent arg0) //PB CORRIGE, COPY HERE
  {
    if (SwingUtilities.isLeftMouseButton(arg0))
    {
      if (!this.libre.booleanValue() && !this.copyState.booleanValue() && !this.isAction.booleanValue()
    		  && !this.isPasted.booleanValue()) {
        for (int i = Math.min(this.i0, this.i1); i <= Math.max(this.i0, this.i1); i++) {
          for (int j = Math.min(this.j0, this.j1); j <= Math.max(this.j0, this.j1); j++) {
    	    if(i < 200 && j < 100) {
    	    	this.carteEditeur.getCartes().getCurrent().getCases()[i][j] = this.couleur;
    	    }
          }
        }
      }
      
      // Gestion de la copie
      if (!this.libre.booleanValue() && this.copyState.booleanValue() && !this.isCopied.booleanValue()) {
    	  nbrCopiedCases = 0;
    	  nbrCopiedCasesX = 0;
    	  nbrCopiedCasesY = 0;
		for (int i = Math.min(this.i0, this.i1); i <= Math.max(this.i0, this.i1); i++) {
			nbrCopiedCasesX++;
		  for (int j = Math.min(this.j0, this.j1); j <= Math.max(this.j0, this.j1); j++) {
			  nbrCopiedCasesY = Math.abs(j1 - j0)+1;
		    if(i < 200 && j < 100) {
		    	nbrCopiedCases++;
		    	copiedCases[i+1][j+1] = this.carteEditeur.getCartes().getCurrent().getCases()[i][j];
		    }
		  }
		}
		
		this.startCopieX = Math.min(this.i0, this.i1)+1;
		this.startCopieY = Math.min(this.j0, this.j1)+1;
		
		this.endCopieX = Math.max(this.i0, this.i1)+1;
		this.endCopieY = Math.max(this.j0, this.j1)+1;
		
		//System.out.println("La copie est de : "+startCopieX+";"+startCopieY+";"+endCopieX+";"+endCopieY);

		this.isCopied = Boolean.valueOf(true);
		this.isPasted = Boolean.valueOf(false);
		this.carteEditeur.setEtatCopie("<html><center>Cliquez à nouveau pour coller votre sélection <br/> Clic droit pour annuler la copie</center></html>");
		this.carteEditeur.visibleButtonSaveForme(true);
		/*System.out.println("Cases totales copiées = "+nbrCopiedCases);
		System.out.println("Cases totales copiées X = "+nbrCopiedCasesX);
		System.out.println("Cases totales copiées Y = "+nbrCopiedCasesY);*/
      }
      
      // Gestion du paste
      if(this.isPasted.booleanValue()) {
    	  this.isPasted = Boolean.valueOf(false);
      }
      
      // Gestion de la zone de respawn
      
      if(this.respawnZoneState.booleanValue() && this.isAction.booleanValue()) {
    	  int startX = Math.min(this.i0, this.i1);
    	  int endX = Math.max(this.i0, this.i1)+1;
    	  
    	  int startY = Math.min(this.j0, this.j1);
    	  int endY = Math.max(this.j0, this.j1)+1;
    	  
    	  if(endX >= 200) {
    		  endX = 200;
    	  }
    	  if(endY >= 100) {
    		  endY = 100;
    	  }
    	  
    	  if(endX < 0) {
    		  endX = 0;
    	  }
    	  
    	  if(endY < 0) {
    		  endY = 0;
    	  }
    	  
    	  Carte current = this.carteEditeur.getCartes().getCurrent();
    	  int actualZonePoint = this.currentZone-1;
    	  if(actualZonePoint == 0) {
	    	  current.zonesRespawn[0] = startX;
	    	  current.zonesRespawn[1] = startY;
	    	  current.zonesRespawn[2] = endX;
	    	  current.zonesRespawn[3] = endY;
    	  } else {
	    	  current.zonesRespawn[4] = startX;
	    	  current.zonesRespawn[5] = startY;
	    	  current.zonesRespawn[6] = endX;
	    	  current.zonesRespawn[7] = endY;
    	  }
    	  
    	  this.currentZone = 0;
    	  this.respawnZoneState = Boolean.valueOf(false);
    	  this.isAction = Boolean.valueOf(false);
      }
      
      // Gestion de la position du point de respawn
      if(this.respawnPositionState.booleanValue() && this.isAction.booleanValue()) {
    	  Carte current = this.carteEditeur.getCartes().getCurrent();
    	  int actualSpawnPoint = this.currentRespawn-1;
    	  int positionX = this.i0;
    	  int positionY = this.j0;
    	  int autreSpawn;
    	  if(actualSpawnPoint == 1) {
    		  autreSpawn = 0;
    	  } else {
    		  autreSpawn = 1;
    	  }
    	  
    	  if(positionX > 199) {
    		  positionX = 199;
    	  }
    	  
    	  if(positionX < 0) {
    		  positionX = 0;
    	  }
    	  
    	  if(positionY > 99) {
    		  positionY = 99;
    	  }
    	  
    	  if(positionY < 0) {
    		  positionY = 0;
    	  }
    	  
    	  int autrePositionX = current.positionsRespawn[autreSpawn][0];
    	  int autrePositionY = current.positionsRespawn[autreSpawn][1];
    	  
    	  Boolean chevauche = false;
    	  if(positionX == autrePositionX) {
    		  if(positionY == autrePositionY) {
    			  chevauche = true;
    		  }
    	  }
    	  
    	  if(!chevauche) {
    		  current.positionsRespawn[actualSpawnPoint][0] = positionX;
    		  current.positionsRespawn[actualSpawnPoint][1] = positionY;
    		  this.currentRespawn = 0;
    		  this.respawnPositionState = Boolean.valueOf(false);
    		  this.isAction = Boolean.valueOf(false);
    	  }
      }
      
      // Gestion de la position du frigo
      if(this.frigoPositionState.booleanValue() && this.isAction.booleanValue()) {
    	  Carte current = this.carteEditeur.getCartes().getCurrent();
    	  int actualFrigo = this.currentFrigo-1;
    	  int positionX = this.i0; // On multiplie par 10 pour avoir des "grosses" coordonnées multiples de 10
    	  int positionY = this.j0; // Vu que le système /10 * this.pas pour l'affichage.
    	  
    	  if(positionX > 195) {
    		  positionX = 195;
    	  }
    	  
    	  if(positionX < 0) {
    		  positionX = 0;
    	  }
    	  
    	  if(positionY > 94) {
    		  positionY = 94;
    	  }
    	  
    	  if(positionY < 0) {
    		  positionY = 0;
    	  }
    	  
    	  Boolean chevauche = false;
    	  if(current.nbFrigos == 2) {
    		  int autreFrigo = 1-actualFrigo;
    		  int autreFrigoX = current.frigos[autreFrigo][0];
    		  int autreFrigoY = current.frigos[autreFrigo][1];
    		  
    		  Vector<Point> myVecAutreFrigo = new Vector<Point>();
    		  for(int i = 0; i<5; i++) {
    			  for(int j = 0; j<6; j++) {
    				  myVecAutreFrigo.add(new Point(autreFrigoX+i, autreFrigoY+j));
    			  }
    		  }

    		  for(int i = 0; i<5; i++) {
    			  for(int j = 0; j<6; j++) {
    				  if(myVecAutreFrigo.contains(new Point(positionX+i, positionY+j))) {
    					  chevauche = true;
    					  break;
    				  }
    			  }
    		  }
    	  }
    	  
    	  if(!chevauche) {
    		  current.frigos[actualFrigo][0] = positionX;
    		  current.frigos[actualFrigo][1] = positionY;
    		  this.currentFrigo = 0;
    		  this.frigoPositionState = Boolean.valueOf(false);
    		  this.isAction = Boolean.valueOf(false);
    	  }
      }
      
      this.modif = Boolean.valueOf(true);
      this.encours = Boolean.valueOf(false);
      redessinner();
    }
  }
  
  public void mouseDragged(MouseEvent arg0)
  {
    this.i1 = (arg0.getX() / this.pas);
    this.j1 = (arg0.getY() / this.pas);
    if (this.i1 >= 200) {
      this.i1 = 199;
    } else if (this.i1 < 0) {
      this.i1 = 0;
    }
    if (this.j1 >= 100) {
      this.j1 = 99;
    } else if (this.j1 < 0) {
      this.j1 = 0;
    }
    this.carteEditeur.setEtatCoor("[" + (this.i1 + 1) + ", " + (this.j1 + 1) + "]");
    if (SwingUtilities.isLeftMouseButton(arg0))
    {
      if (this.libre.booleanValue() && !this.frigoPositionState.booleanValue()) {
        this.carteEditeur.getCartes().getCurrent().getCases()[this.i1][this.j1] = this.couleur;
      }
      redessinner();
    }
    if ((SwingUtilities.isMiddleMouseButton(arg0)) && (new Date().getTime() - this.temps >= 20L))
    {
      scrollRectToVisible(new Rectangle(getVisibleRect().x + (arg0.getX() - getVisibleRect().x - this.x) / 3 * 2, getVisibleRect().y + (arg0.getY() - getVisibleRect().y - this.y) / 3 * 2, getVisibleRect().width, getVisibleRect().height));
      this.x = (arg0.getX() - getVisibleRect().x);
      this.y = (arg0.getY() - getVisibleRect().y);
      this.temps = new Date().getTime();
    }
  }
  
  public void mouseMoved(MouseEvent arg0)
  {
	Graphics g = this.image.getGraphics();
	redessinner();
	
    this.i1 = (arg0.getX() / this.pas);
    this.j1 = (arg0.getY() / this.pas);
    this.carteEditeur.setEtatCoor("[" + (this.i1 + 1) + ", " + (this.j1 + 1) + "]");
    
    if(this.copyState.booleanValue() && !this.isCopied.booleanValue()) {
    	g.setColor(Color.WHITE);
    } else if(this.copyState.booleanValue() && this.isCopied.booleanValue()) {
    	g.setColor(Color.RED);
    	g.drawRect(i1 * this.pas, j1 * this.pas, nbrCopiedCasesX * this.pas, nbrCopiedCasesY * this.pas);
    } else if(this.frigoPositionState.booleanValue()) {
    	g.setColor(Color.BLUE); // On positionne le frigo
    } else if(this.respawnPositionState.booleanValue()) {
    	g.setColor(Color.WHITE);
    } else if(this.respawnZoneState.booleanValue()) {
    	g.setColor(Color.orange);
    } else {
    	g.setColor(Color.GREEN);
    }
    g.drawRect(i1 * this.pas, j1 * this.pas, (1) * this.pas, (1) * this.pas);
    repaint();
  }
  







  public void mouseWheelMoved(MouseWheelEvent e) {}
  




  public void keyPressed(KeyEvent e) {}
  
  public int getI1() {
	  return this.i1;
  }

  public int getJ1() {
	  return this.j1;
  }

  public void Exporte(File file)
  {
    try
    {
      if (file != null)
      {
        BufferedImage imageExporte = new BufferedImage(200 * this.pas, 100 * this.pas, 2);
        paint(imageExporte.createGraphics());
        ImageIO.write(imageExporte, "png", file);
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public AbstractAction getSwitchGrilleAction()
  {
    return this.switchGrilleAction;
  }
  
  public AbstractAction getSwitchFondAction()
  {
    return this.switchFondAction;
  }
  
  public AbstractAction getSwitchZonesAction()
  {
    return this.switchZonesAction;
  }
  
  public AbstractAction getSwitchFrigosAction() {
	  return this.switchFrigosAction;
  }
  
  public AbstractAction getSwitchSpawnsAction() {
	  return this.switchSpawnsAction;
  }
  
  public AbstractAction getSwitchMireAction()
  {
    return this.switchMireAction;
  }
  
  public AbstractAction getSwitchZoomAction()
  {
    return this.switchZoomAction;
  }
  
  public AbstractAction getDaltonienAction()
  {
    return this.switchDaltonienAction;
  }
 
  
  public AbstractAction getSwitchLibreAction()
  {
    return this.switchLibreAction;
  }
  
  public void keyReleased(KeyEvent paramKeyEvent) {}
  
  public void keyTyped(KeyEvent paramKeyEvent) {}
}
