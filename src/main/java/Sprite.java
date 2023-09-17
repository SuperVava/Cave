import processing.core.PApplet;

public class Sprite {
    GameElement[] elements;
    int counter = 0;
    int frame = 0;
    public Sprite(String name, int numberOfFrame, PApplet processing, String collider) {
        elements = new GameElement[numberOfFrame];
        for(int i = 0; i< numberOfFrame; i++){
            elements[i] = new GameElement(name + "_" + (i+1), processing, collider);
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

    public void setLight(int light) {
        for (GameElement element : elements) element.setLight(light);
    }
    public void setLight(String light) {
        for (GameElement element : elements) element.setLight(light);
    }
}
