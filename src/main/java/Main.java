import processing.core.PApplet;

public class Main extends PApplet{

    Screen screen;
    Grill grill;
    GameElement backGroundImage;
    GameElement candle;
    GameElement player;
    Control control;

    int i = 0;
    public static void main(String[] args) { PApplet.main("Main"); }

    public void settings() {
        int grillWidth = 300;
        int grillHeigh = 150;
        this.screen = new Screen(this, grillWidth, grillHeigh);
        this.grill = new Grill(grillWidth, grillHeigh);


        this.candle = new GameElement("candle", this, true, 100, 100);
        this.player = new GameElement("player", this, true, 50, 70);
        this.control = new Control(player);
        this.backGroundImage = new GameElement("background_1", this, false, 0, 0);
        fullScreen();
    }

    public void draw(){
        background(0);
        grill.set(backGroundImage);

        if (!keyPressed) control.stop();
        control.update();
        grill.set(player);

        grill.set(candle);
        i++;


        screen.draw(grill.getPixelArray());
    }

    @Override
    public void keyTyped() {
        control.treat(key);
    }
}
