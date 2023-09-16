import processing.core.PApplet;

public class Main extends PApplet{

    Screen screen;
    Grill grill;
    GameElement backGroundImage;
    GameElement candle;
    GameElement player;
    int i = 0;
    public static void main(String[] args) { PApplet.main("Main"); }

    public void settings() {
        int grillWidth = 300;
        int grillHeigh = 150;
        this.screen = new Screen(this, grillWidth, grillHeigh);
        this.candle = new GameElement("candle", this, true);
        this.player = new GameElement("player", this, true);
        this.grill = new Grill(this, grillWidth, grillHeigh);
        this.backGroundImage = new GameElement("background_1", this, false);
        grill.set(backGroundImage, 0, 0);
        fullScreen();

    }

    public void draw(){
        background(0);
        grill.set(backGroundImage, 0, 0);
        grill.set(player, 50 + i, 100);
        i++;
        screen.draw(grill.getPixelArray());
    }
}
