import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Sprite {
    PImage[] frames;
    PImage[] flippedFrames;
    PApplet processing;
    int counter = 0;
    int frame = 0;

    public Sprite(String name, int numberOfFrame, PApplet processing) {
        this.processing = processing;
        frames = new PImage[numberOfFrame];
        flippedFrames = new PImage[numberOfFrame];
        for(int i = 1; i<= numberOfFrame; i++){
            frames[i - 1] = processing.loadImage(name + "_" + i +".png");
        }
    }

    public void flip() {
        for (int i = 0; i < frames.length; i++) {
            //j'ai volé ça sur stackOverflow
            PImage flipped = processing.createImage(frames[i].width, frames[i].height, PConstants.RGB); //create a new image with the same dimensions
            for (int j = 0; j < flipped.pixels.length; j++) {       //loop through each pixel
                int srcX = j % flipped.width;                        //calculate source(original) x position
                int dstX = flipped.width - srcX - 1;                     //calculate destination(flipped) x position = (maximum-x-1)
                int y = j / flipped.width;                        //calculate y coordinate
                flipped.pixels[y * flipped.width + dstX] = frames[i].pixels[j];//write the destination(x flipped) pixel based on the current pixel
            }
            //y*width+x is to convert from x,y to pixel array index
            flipped.updatePixels();
            flippedFrames[i] = flipped;
        }
    }

    public PImage getFrame(boolean isFlipped) {
        counter +=1;
        frame = counter / 10;
        if(frame >= frames.length){
            counter = 0;
            frame = 0;
        }
            if(!isFlipped) return frames[frame];
            else return flippedFrames[frame];
    }
}
