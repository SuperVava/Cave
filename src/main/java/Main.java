import processing.core.PApplet;

public class Main extends PApplet{

    Screen screen;
    Grill grill;
    GameElement backGroundImage;
    GameElement candle;
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
        this.backGroundImage = new GameElement("background_1", this, false, "background_1");
        fullScreen();
    }

    public void draw(){
        background(0);
        grill.set(backGroundImage, i, 0);
        if(keyPressed) player.treat(key);
        player.set();
        if(player.getPositionX() == 230 && key == 'd' && keyPressed && i<backGroundImage.getTexture().width - grillWidth) i--;
        if(player.getPositionX() == 20 && key == 'q' && keyPressed && i != 0) i++;

        screen.draw(grill.getPixelArray());
    }
}
