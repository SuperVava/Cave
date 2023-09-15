import processing.core.PApplet;

public class Grill {
    Slicer slicer;
    int grillWidth;
    int grillHeight;
    int[] pixelArray;

    public Grill(PApplet processing, int grillWidth, int grillHeight) {
        this.grillWidth = grillWidth;
        this.grillHeight = grillHeight;

        this.slicer = new Slicer(processing, grillWidth, grillHeight);

        this.pixelArray = new int[grillWidth * grillHeight];
        }



    public void set(GameElement element, int x, int y) {
        int[][] texture = slicer.getSliced(element.getTexture(), x, y);
        //ajoute la texture à la grille
        for (int i = 0; i < texture.length; i++){
            pixelArray[texture[i][0]] = texture[i][1];
        }
    }

    public int[] getPixelArray() {
        return pixelArray;
    }
}
