import processing.core.PApplet;

public class Sprite {
    GameElement[] elements;
    int counter = 0;
    int frame = 0;
    public Sprite(String name, int numberOfFrame, PApplet processing, boolean isGlowing) {
        elements = new GameElement[numberOfFrame];
        for(int i = 0; i< numberOfFrame; i++){
            elements[i] = new GameElement(name + "_" + (i+1), processing, isGlowing);
        }
    }

    public void flip() {
        for (GameElement element : elements) element.flip();
    }

    public GameElement getElement() {
        counter +=1;
        frame = counter / 10;
        try{return elements[frame];}
        catch (Exception exception){
            counter = 0;
            frame = 0;
            return elements[0];
        }
    }

    public void setPosition(int positionX, int positionY) {
        elements[frame].setPosition(positionX, positionY);
    }
}
