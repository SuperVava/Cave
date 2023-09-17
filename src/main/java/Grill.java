import Exeptions.Collision;
import processing.core.PApplet;

public class Grill {
    Slicer slicer;
    PApplet processing;
    int grillWidth;
    int grillHeight;
    int[][] pixelArray;
    int[] backGroundTexture;

    public Grill(int grillWidth, int grillHeight, PApplet processing) {
        this.grillWidth = grillWidth;
        this.grillHeight = grillHeight;
        this.processing = processing;
        this.slicer = new Slicer(grillWidth, grillHeight);
        this.pixelArray = new int[grillWidth * grillHeight][3];
        //contient la texture en 0, la lumière en 1 et la colision en 2
        this.backGroundTexture = new int[grillWidth * grillHeight];
    }

    public void set(GameElement element, int x, int y)  {

        int[][] collider = slicer.getSliced(element.getCollider(), x, y);
        //ajoute le collider
        for (int[] ints : collider) {
            pixelArray[ints[0]][2] = ints[1];
        }


        int[][] texture = slicer.getSliced(element.getTexture(), x, y);
        //ajoute la texture à la grille
        for (int[] ints : texture) {
            pixelArray[ints[0]][0] = ints[1];
            pixelArray[ints[0]][1] = 0;
        }

        if(element.getIsGlowing()){
            //décalage du shader comparée à la texture
            int gap = (element.getShader().width/2) - (element.getTexture().width / 2);
            int[][] shader = slicer.getSliced(element.getShader(), x - gap, y - gap);
            //ajoute la valeur de luminosité
            for (int[] ints : shader) {
                //additione les deux valeurs si les faisceaux se croisent

                if(pixelArray[ints[0]][1] == 0) pixelArray[ints[0]][1] = (int) processing.brightness(ints[1]);
                else if (processing.brightness(pixelArray[ints[0]][1]) < processing.brightness(ints[1]))
                    pixelArray[ints[0]][1] = (int) processing.brightness(ints[1]);
                }
            }
        }

    public int[][] getPixelArray() {
        return pixelArray;
    }
    public void tryCollision(GameElement element, int x, int y) throws Collision {
        int[][] collider = slicer.getSliced(element.getCollider(), x, y);
        //test chaque pixels
        for (int[] ints : collider) {
            CollisionController.testForCollision(pixelArray[ints[0]][2], ints[1]);
        }
    }
}
