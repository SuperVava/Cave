import processing.core.PApplet;
import processing.core.PImage;

public class Grill {
    Slicer slicer;
    int grillWidth;
    int grillHeight;
    int[][] pixelArray;

    public Grill(PApplet processing, int grillWidth, int grillHeight) {
        this.grillWidth = grillWidth;
        this.grillHeight = grillHeight;
        this.slicer = new Slicer(processing, grillWidth, grillHeight);
        this.pixelArray = new int[grillWidth * grillHeight][2];
        }



    public void set(GameElement element, int x, int y) {
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
                pixelArray[ints[0]][1] = ints[1];
            }
        }
    }

    public int[][] getPixelArray() {
        return pixelArray;
    }
}
