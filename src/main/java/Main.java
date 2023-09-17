import processing.core.PApplet;

public class Main extends PApplet{

    Screen screen;
    Grill grill;
    GameElement backGroundImage;
    GameElement candle;
    Player player;
    Control control;

    int i = 0;
    public static void main(String[] args) { PApplet.main("Main"); }

    public void settings() {
        int grillWidth = 300;
        int grillHeigh = 150;
        this.screen = new Screen(this, grillWidth, grillHeigh);
        this.grill = new Grill(grillWidth, grillHeigh, this);


        this.candle = new GameElement("candle", this, true, "candle");
        this.player = new Player(this, grill);
        player.setPosition(50, 50);
        this.control = new Control(player);
        this.backGroundImage = new GameElement("background_1", this, false, "background_1");
        fullScreen();
    }

    public void draw(){
        background(0);
        grill.set(backGroundImage, 0, 0);

        if (!keyPressed) control.stop();
        control.update();
        player.set();

        grill.set(candle, 100, 100);
        i++;


        screen.draw(grill.getPixelArray());
    }

    @Override
    public void keyTyped() {
        control.treat(key);
    }
}
