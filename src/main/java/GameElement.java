import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

import java.io.Serializable;

public class GameElement implements Serializable {
    private int positionX;
    private int positionY;
    transient PImage[] element;
    boolean isGlowing = false;
    transient PApplet processing;
    String elementName;
    boolean isFlipped;

    public GameElement(String elementName, int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.elementName = elementName;
    }

    public void generate(PApplet processing){
        //0:texture 1:shader 2:collider 3:flippedTexture 4:flippedShader 5flippedCollider
        this.processing = processing;
        this.element = new PImage[6];
        this.element[0] = processing.loadImage(elementName + ".png");
        this.element[2] = processing.loadImage(elementName + "_collider.png");
    }

    public void setLight(int glowLevel){
        if(glowLevel == 0){
            isGlowing = false;
        }
        if(glowLevel == 1){
            element[1] = processing.loadImage("shader_50.png");
            isGlowing = true;
        }
        if(glowLevel == 2){
            element[1] = processing.loadImage("shader_100.png");
            isGlowing = true;
        }
    }
    public void setLight(String shader) {
        element[1] = processing.loadImage(shader + "_shader.png");
        flip();
    }

    public PImage getTexture(){
        if(!isFlipped)return this.element[0];
        else return this.element[3];
    }

    public PImage getShader(){
        if(!isFlipped)return this.element[1];
        else return this.element[4];
    }

    public PImage getCollider(){
        if(!isFlipped)return this.element[2];
        else return this.element[5];
    }

    public void setFlipped(boolean flipped) {
        isFlipped = flipped;
    }

    public boolean getIsGlowing() {
        return isGlowing;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionX(int positionX){
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public void flip() {
        //j'ai volé ça sur stackOverflow
        for(int i = 0; i < 3; i++) {
            PImage flipped = processing.createImage(element[i].width, element[i].height, PConstants.RGB); //create a new image with the same dimensions
            for (int j = 0; j < flipped.pixels.length; j++) {       //loop through each pixel
                int srcX = j % flipped.width;                        //calculate source(original) x position
                int dstX = flipped.width - srcX - 1;                     //calculate destination(flipped) x position = (maximum-x-1)
                int y = j / flipped.width;                        //calculate y coordinate
                flipped.pixels[y * flipped.width + dstX] = element[i].pixels[j];//write the destination(x flipped) pixel based on the current pixel
            }
            //y*width+x is to convert from x,y to pixel array index
            flipped.updatePixels();
            this.element[3 + i] = flipped;
        }
    }
}
