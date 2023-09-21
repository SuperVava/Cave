import processing.core.PApplet;
import processing.core.PConstants;

public class Screen {
    private final PApplet processing;
    private final int grillHeight;
    private final int grillWidth;
    private int pixelSize;

    public Screen(PApplet processing, int grillWidth, int grillHeight) {
        this.grillHeight = grillHeight;
        this.grillWidth = grillWidth;
        this.processing = processing;
    }

    public int getPixelSize() {
        return pixelSize;
    }

    public void set(int[][] pixelArray) {
        //set size en fonction de processing
        this.pixelSize = Math.min(processing.displayWidth / grillWidth, processing.displayHeight / grillHeight);
        int yGap = (processing.displayHeight - grillHeight * pixelSize) / 2;
        int xGap = (processing.displayWidth - grillWidth * pixelSize) / 2;

        processing.rectMode(PConstants.CORNER);
        processing.noStroke();
        processing.colorMode(PConstants.ARGB);
        for (int i = 0; i < grillHeight; i++) {
            for (int j = 0; j < grillWidth; j++) {
                if(pixelArray[i*grillWidth + j][1] != 0){
                    processing.fill(pixelArray[i * grillWidth + j][0], pixelArray[i * grillWidth + j][1]);
                    processing.rect(j * pixelSize + xGap, i * pixelSize + yGap, pixelSize, pixelSize);
                }
            }
        }
        processing.colorMode(PConstants.RGB);
    }
}
