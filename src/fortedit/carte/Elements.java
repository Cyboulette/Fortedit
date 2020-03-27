package fortedit.carte;

import java.awt.Color;

public class Elements
{
  public static final char[] codes = { '9', '0', '1', '2', '8', '3' };
  public static final String[] noms = { "Vide non Constructible (ALT + S)", "Vide Constructible (ALT + D)", "Carré Destructible (ALT + F)", "Carré Indestructible (ALT + G)", "Carré non Reconstructible (ALT + H)", "Fin de Rally (ALT + O)" };
  public static final Color[] couleur = { Color.getColor(null, 8556447), Color.getColor(null, 10395311), Color.getColor(null, 2040893), Color.BLACK, Color.MAGENTA, Color.getColor(null, 16744231) };
  public static final Color[] couleurBoutonBackground = { Color.getColor(null, 8556447), Color.getColor(null, 10395311), Color.getColor(null, 2040893), Color.BLACK, Color.MAGENTA, Color.getColor(null, 16744231) };
  public static final char[] touches = { 'S', 'D', 'F', 'G', 'H', 'O' };
  public static final String[] couleurHexaDefaut = { "828F9F", "9E9EAF", "1F243D", "000000", "FF00FF", "FF7F27" };
  
  public static String[] getHexaDefaut() {
	  String[] couleurHexaDefaut = new String[10];
	  couleurHexaDefaut[9] = "828F9F";
	  couleurHexaDefaut[0] = "9E9EAF";
	  couleurHexaDefaut[1] = "1F243D";
	  couleurHexaDefaut[2] = "000000";
	  couleurHexaDefaut[8] = "FF00FF";
	  couleurHexaDefaut[3] = "FF7F27";
	  return couleurHexaDefaut;
  }
}
