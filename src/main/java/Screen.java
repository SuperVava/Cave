import processing.core.PApplet;
import processing.core.PConstants;

public class Screen {
    PApplet processing;
    int blackScreenBandSize;
    int grillHeight;
    int grillWidth;
    int pixelSize;

    public Screen(PApplet processing, int grillWidth, int grillHeight) {
        this.grillHeight = grillHeight;
        this.grillWidth = grillWidth;
        this.processing = processing;
        this.pixelSize = processing.displayWidth / grillWidth;
        this.blackScreenBandSize = (processing.displayHeight - grillHeight* getPixelSize()) / 2;

    }

    public int getPixelSize() {
        return pixelSize;
    }

    public void draw(int[][] pixelArray) {
        processing.rectMode(PConstants.CORNER);
        processing.noStroke();
        processing.colorMode(PConstants.ARGB);
        for (int i = 0; i < grillHeight; i++) {
            for (int j = 0; j < grillWidth; j++) {
                processing.fill(pixelArray[i*grillWidth + j][0], pixelArray[i*grillWidth + j][1]);
                processing.rect(j * pixelSize, i * pixelSize + blackScreenBandSize, pixelSize, pixelSize);
            }
        }
    }
}
