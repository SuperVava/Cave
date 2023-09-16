import processing.core.PApplet;
import processing.core.PImage;

public class GameElement {
    PImage[] element;
    boolean isGlowing = false;

    public GameElement(String elementName, PApplet processing, boolean isGlowing) {
        this.element = new PImage[3];

        this.element[0] = processing.loadImage(elementName + ".png");
        this.element[1] = processing.loadImage(elementName + "_collider.png");
        if(isGlowing){
            this.element[2] = processing.loadImage("shader_100.png");
            this.isGlowing = true;
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

    public boolean getIsGlowing() {
        return isGlowing;
    }
}
