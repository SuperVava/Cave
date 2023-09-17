import processing.core.PApplet;

public class BackGroundElement {
    PApplet processing;
    GameElement backGround;
    Grill grill;
    int positionX = 0;
    int grillWidth;

    public BackGroundElement(PApplet processing, Grill grill, int grillWidth) {
        this.processing = processing;
        this.grill = grill;
        this.grillWidth = grillWidth;
        this.backGround = new GameElement("background_1", processing, false, "background_1");
    }

    public void set(int playerPositionX, char key, boolean keyPressed) {
        if(playerPositionX == 230 && key == 'd' && keyPressed && positionX< backGround.getTexture().width - grillWidth) positionX--;
        if(playerPositionX == 23 && key == 'q' && keyPressed && positionX != 0) positionX++;
        grill.set(backGround, positionX, 0);
    }
}
