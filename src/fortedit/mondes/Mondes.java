package fortedit.mondes;

public class Mondes
{
  public static final String[] codes = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
  public static final int[][] zones = { { 164, 45, 185, 70, 0, 32, 23, 49 }, //Map 0 : Vérifiée & Updatée 0.81
    { 0, 0, 31, 100, 167, 32, 196, 45 }, //Map 1 : Vérifiée & Updatée 0.81
    { 147, 23, 190, 45, 8, 15, 23, 35 }, //Map 2 : Vérifiée & Updatée 0.81
    { 7, 44, 27, 55, 159, 51, 194, 61 }, //Map 3 : Vérifiée & Updatée 0.81
    { 10, 38, 36, 51, 164, 38, 189, 51 }, //Map 4 : Vérifiée & Updatée 0.81
    { 6, 33, 19, 42, 181, 33, 194, 42 }, //Map 5 : Vérifiée & Updatée 0.81
    { 0, 65, 29, 90, 129, 48, 178, 90 }, //Map 6 : Vérifiée & Updatée 0.81 
    { 31, 52, 44, 63, 156, 52, 169, 63 }, //Map 7 : Vérifiée & Updatée 0.81
    { 66, 57, 99, 74, 114, 48, 149, 76 }, //Map 8 : Vérifiée & Updatée 0.81
    { 3, 9, 30, 23, 154, 47, 175, 64 }, //Map 9 : Vérifiée & Updatée 0.81
    { 1, 51, 50, 61, 151, 51, 200, 61 }, //Map 10 : Vérifiée & Updatée 0.81
    { 159, 43, 180, 54, 20, 43, 41, 54 }, //Map 11 : Vérifiée & Updatée 0.81
    { 22, 43, 39, 59, 174, 60, 198, 71 }, //Map 12 : Vérifiée & Updatée 0.81
    { 146, 38, 176, 62, 0, 81, 33, 99 }, //Map 13 : Vérifiée & Updatée 0.81
    { 9, 51, 31, 64, 171, 52, 193, 65 }, //Map 14 : Vérifiée & Updatée 0.81
    { 0, 25, 24, 46, 177, 27, 197, 50 }, //Map 15 : Vérifiée & Updatée 0.81
    { 25, 44, 43, 65, 153, 43, 171, 64 }, //Map 16 : Vérifiée & Updatée 0.81
    { 12, 44, 38, 60, 163, 44, 189, 60 }, //Map 17 : Vérifiée & Updatée 0.81
    { 29, 43, 58, 69, 163, 12, 200, 41 }, //Map 18 : Vérifiée & Updatée 0.81
    { 17, 41, 43, 55, 159, 41, 185, 55 }, //Map 19 : Vérifiée & Updatée 0.81
    { 61, 52, 90, 66, 179, 42, 200, 55 } //Map 20 : Vérifiée & Updatée 0.82
    };
  

  /* Explications Coordonnées
   * A, B, C, D (gauche) => Frigo Gauche
   * A, B = Pixel haut gauche A = X et B = Y
   * C => Pixel haut droite C = X
   * D => Pixel bas gauche D = Y
   * (Pareil pour frigo droite)
   * 
   * Explications Mires
   * Prendre le pixel de la ligne du milieu en Y
   */


  public static final int[] mires = { 700, 464, 370, 510, 437, 325, 630, 550, 560, 500, 548, 475, 535, 558, 940, 475, 548, 502, 595, 495, 469 };
}
