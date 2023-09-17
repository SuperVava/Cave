import processing.core.PApplet;

public class Main extends PApplet{

    Screen screen;
    Grill grill;
    BackGroundElement backGroundElement;
    Player player;
    int grillWidth = 300;
    int grillHeigh = 150;

    int i = 0;
    public static void main(String[] args) { PApplet.main("Main"); }

    public void settings() {
        this.screen = new Screen(this, grillWidth, grillHeigh);
        this.grill = new Grill(grillWidth, grillHeigh, this);

        this.player = new Player(this, grill);
        player.setPosition(50, 50);
        this.backGroundElement = new BackGroundElement(this, grill, grillWidth);
        fullScreen();
    }

    public void draw(){
        background(0);
        backGroundElement.set(player.getPositionX(), key, keyPressed);

        if(keyPressed) player.treat(key);
        player.set();


        screen.draw(grill.getPixelArray());
    }
}
