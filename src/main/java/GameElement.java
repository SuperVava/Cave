import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class GameElement {
    PImage[] element;
    boolean isGlowing = false;
    int positionX, positionY;
    PApplet processing;

    public GameElement(String elementName, PApplet processing, boolean isGlowing) {
        this.element = new PImage[3];
        this.processing = processing;
        this.element[0] = processing.loadImage(elementName + ".png");
        if(isGlowing){
            this.element[1] = processing.loadImage("shader_100.png");
            this.isGlowing = true;
        }
        this.element[2] = processing.loadImage(elementName + "_collider.png");
    }

    public PImage getTexture(){
        return this.element[0];
    }

    public PImage getCollider(){
        return this.element[2];
    }

    public PImage getShader(){
        return this.element[1];
    }

    public boolean getIsGlowing() {
        return isGlowing;
    }


    public void setPosition(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void flip() {
        //j'ai volé ça sur stackOverflow
        PImage flipped = processing.createImage(element[0].width,element[0].height, PConstants.RGB); //create a new image with the same dimensions
        for(int i = 0 ; i < flipped.pixels.length; i++){       //loop through each pixel
            int srcX = i % flipped.width;                        //calculate source(original) x position
            int dstX = flipped.width-srcX-1;                     //calculate destination(flipped) x position = (maximum-x-1)
            int y    = i / flipped.width;                        //calculate y coordinate
            flipped.pixels[y*flipped.width+dstX] = element[0].pixels[i];//write the destination(x flipped) pixel based on the current pixel
        }
        //y*width+x is to convert from x,y to pixel array index
        flipped.updatePixels();
        element[0] = flipped;
    }
}
