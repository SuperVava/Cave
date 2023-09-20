import processing.core.PApplet;
import processing.core.PImage;

public class Wolf {

    private final int grillSize;
    PApplet processing;
    GameElement element;
    Sprite walk;

    public Wolf(PApplet processing, int grillSize) {
        this.grillSize = grillSize;
        this.processing = processing;
        this.element = new GameElement("wolf", ElementType.WOLF, 0, 0);
        element.generate(processing);
        element.setLight("wolf");
        element.flip();
        this.walk = new Sprite("wolf_walk", processing);
        walk.flip();
    }

    public void set(boolean isFlipped, int positionX){
        element.setFlipped(isFlipped);
        if(isFlipped)element.setPositionX(grillSize - positionX);
        else element.setPositionX(-element.getTexture().width - positionX);

        element.setPositionY(75);
    }

    public PImage getTexture() {
        if(element.isFlipped)element.setPositionX(element.getPositionX() - 1);
        else element.setPositionX(element.getPositionX() + 1);
        return walk.getPicture(element.isFlipped);
    }

    public int getPositionX() {
        return element.getPositionX();
    }
    public int getPositionY() {
        return element.getPositionY();
    }

    public PImage getShader() {
        return element.getShader();
    }

    public boolean isWolfHere() {
        if (this.element.isFlipped && getPositionX() > -element.getTexture().width) return true;
        else if (!this.element.isFlipped && getPositionX() < grillSize) return true;
        else return false;
    }

    public PImage getCollider() {
        return element.getCollider();
    }
}
