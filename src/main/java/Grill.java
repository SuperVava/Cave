import Exeptions.Collision;
import processing.core.PApplet;
import processing.core.PImage;

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

    public void setTexture(PImage textureImage, int x, int y)  {

        int[][] texture = slicer.getSliced(textureImage, x, y);
        //ajoute la texture à la grille
        for (int[] ints : texture) {
            pixelArray[ints[0]][0] = ints[1];
        }


        }

    public void setShader(PImage shaderPicture, PImage texture, int x, int y) {
        //décalage du shader comparée à la texture
        int gapX = (shaderPicture.width / 2) - (texture.width / 2);
        int gapY = (shaderPicture.height / 2) - (texture.height / 2);
        int[][] shader = slicer.getSliced(shaderPicture, x - gapX, y - gapY);
        //ajoute la valeur de luminosité
        for (int[] ints : shader) {
            //additione les deux valeurs si les faisceaux se croisent
            if (pixelArray[ints[0]][1] == 0) pixelArray[ints[0]][1] = (int) processing.brightness(ints[1]);
            else if (processing.brightness(pixelArray[ints[0]][1]) < processing.brightness(ints[1]))
                pixelArray[ints[0]][1] = (int) processing.brightness(ints[1]);

        }
    }

    public void setCollider(PImage colliderImage, int x, int y)  {
        int[][] collider = slicer.getSliced(colliderImage, x, y);
        //ajoute le collider
        for (int[] ints : collider) {
            pixelArray[ints[0]][2] = ints[1];
        }
    }

    public int[][] getPixelArray() {
        return pixelArray;
    }

    public void tryCollision(GameElement element, int x, int y) throws Collision {
        int[][] collider = slicer.getSliced(element.getCollider(), x, y);
        //test chaque pixels
        for (int[] ints : collider) {
            CollisionController.testForCollision(PApplet.hex(pixelArray[ints[0]][2]), PApplet.hex(ints[1]));
        }
    }

    public int getGrillWidth() {
        return grillWidth;
    }

    public void clearLight(){
        for(int[] ints : pixelArray) ints[1] = 0;
    }
    public void clearCollider(){
        for(int[] ints : pixelArray) ints[2] = 0;
    }
}
