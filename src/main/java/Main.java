import processing.core.PApplet;

public class Main extends PApplet{

    Screen screen;
    Grill grill;
    Level level;
    int grillWidth = 300;
    int grillHeigh = 150;

    int i = 0;
    public static void main(String[] args) { PApplet.main("Main"); }

    public void settings() {
        this.screen = new Screen(this, grillWidth, grillHeigh);
        this.grill = new Grill(grillWidth, grillHeigh, this);
        this.level = new Level(this, grill, "level_1");
        fullScreen();
    }

    public void draw(){
        background(0);
        grill.clearLight();

        level.set(key, keyPressed);

        screen.draw(grill.getPixelArray());
    }
}
