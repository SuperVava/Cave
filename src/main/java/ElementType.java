public class ElementType {

    /*
         Les types de colisions sont:
         -0 si libre
         -1 si il s'agit d'un mûr
         -2 si il s'agit du personnage principal
         -3 si il s'agit d'un ennemie
         -4 si il s'agit d'un projectil
         */

    public static int FREE = 0;
    public static int WALL = 1;
    public static int PLAYER = 2;
    public static int ENEMY = 3;
    public static int BULLET = 4;
    public static int HALF_WALL = 5;
    public static int DOOR = 6;
}
