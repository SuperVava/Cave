import processing.core.PApplet;

public class Sprite {
    GameElement[] elements;
    int counter = 0;
    int frame = 0;
    public Sprite(String name, int numberOfFrame, PApplet processing, boolean isGlowing, String collider) {
        elements = new GameElement[numberOfFrame];
        for(int i = 0; i< numberOfFrame; i++){
            elements[i] = new GameElement(name + "_" + (i+1), processing, isGlowing, collider);
        }
    }

    public void flip() {
        for (GameElement element : elements) element.flip();
    }

    public GameElement getElement() {
        counter +=1;
        frame = counter / 10;
        if(frame >= elements.length){
            counter = 0;
            frame = 0;
        }
            return elements[frame];
    }
}
