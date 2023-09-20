import processing.core.PApplet;

public class Main extends PApplet{

    Screen screen;
    Grill grill;
    Level level;
    int grillWidth = 300;
    int grillHeigh = 150;

    private Text text;

    boolean isGameStarted;

    int i = 0;
    public static void main(String[] args) { PApplet.main("Main"); }

    public void settings() {
        this.text = new Text(this);
        this.screen = new Screen(this, grillWidth, grillHeigh);
        this.grill = new Grill(grillWidth, grillHeigh, this);
        this.level = new Level(this, grill, "level_1");
        fullScreen();
        isGameStarted = false;
    }

    public void draw(){
        background(0);
        if(!isGameStarted && key == ' ') isGameStarted = true;
        else if (isGameStarted) {
            grill.clearLight();

            level.set(key, keyPressed);

            screen.draw(grill.getPixelArray());
        }
        else{
            this.text.write("Press SPACE to start...", width/2, 4 * (height/5), 30);
        }
    }
}
