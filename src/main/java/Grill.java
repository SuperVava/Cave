

public class Grill {
    Slicer slicer;
    int grillWidth;
    int grillHeight;
    int[][] pixelArray;
    int[] backGroundTexture;

    public Grill(int grillWidth, int grillHeight) {
        this.grillWidth = grillWidth;
        this.grillHeight = grillHeight;
        this.slicer = new Slicer(grillWidth, grillHeight);
        this.pixelArray = new int[grillWidth * grillHeight][3];
        //contient la texture en 0, la lumière en 1 et la colision en 2
        this.backGroundTexture = new int[grillWidth * grillHeight];
    }

    public void set(GameElement element) {
        int x = element.getPositionX();
        int y = element.getPositionY();
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
