import processing.core.PApplet;

public class Main extends PApplet{

    Screen screen;
    Grill grill;
    GameElement backGround;
    GameElement candle;
    public static void main(String[] args) { PApplet.main("Main"); }

    public void settings() {
        int grillWidth = 300;
        int grillHeigh = 150;
        this.screen = new Screen(this, grillWidth, grillHeigh);
        this.candle = new GameElement("candle", this, true);
        this.grill = new Grill(this, grillWidth, grillHeigh);
        this.backGround = new GameElement("background_1", this, false);
        grill.set(backGround, 0, 0);
        grill.set(candle, 10, 100);
        fullScreen();

    }

    public void draw(){
        background(0);
        screen.draw(grill.getPixelArray());
    }
}
