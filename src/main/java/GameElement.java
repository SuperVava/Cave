import processing.core.PApplet;
import processing.core.PImage;

public class GameElement {
    PImage[] element;

    public GameElement(String elementName, PApplet processing, boolean isGlowing) {
        this.element = new PImage[3];

        this.element[0] = processing.loadImage(elementName + ".png");
        this.element[1] = processing.loadImage(elementName + "_collider.png");
        if(isGlowing){
            this.element[2] = processing.loadImage("shader_100.png");
        }
    }

    public PImage getTexture(){
        return this.element[0];
    }

    public PImage getCollider(){
        return this.element[1];
    }

    public PImage getShader(){
        return this.element[2];
    }
}
