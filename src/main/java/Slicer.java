import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

public class Slicer {
    PApplet processing;
    int grillWidth;
    int grillHeight;

    public Slicer(PApplet processing, int grillWidth, int grillHeight) {
        this.processing = processing;
        this.grillWidth = grillWidth;
        this.grillHeight = grillHeight;
    }

    public int[][] getSliced(PImage image, int x, int y){

        List<Integer> position = new ArrayList<Integer>();
        List<Integer> color = new ArrayList<Integer>();


        for (int i = 0; i < image.height; i++) {
            for (int j = 0; j < image.width; j++) {
                //n'imprime que si le pixel est coloré et dans la grille
                boolean onGrill = x+j < grillWidth && x+j > 0 && y + i > 0 && y+i < grillHeight;

                if (image.pixels[i * image.width + j] < 0 && onGrill) {
                    //donne la position dans la grille
                    position.add((grillWidth * y + x) + j + (i*grillWidth));
                    //donne la couleur
                    color.add(image.pixels[i*image.width+j]);
                }
            }
        }
        int[][] pixelArray = new int[position.size()][2];
        for(int i = 0; i<position.size(); i++){
            pixelArray[i] = new int[]{position.get(i), color.get(i)};
        }
        return pixelArray;
    }

    private int foundPosition(int positionX, int positionY, int i, int j) {
        return positionX + (positionY * grillWidth) + j + (i * grillWidth);
    }


}
