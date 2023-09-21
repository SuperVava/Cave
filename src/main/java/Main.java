import processing.core.PApplet;

public class Main extends PApplet{

    Screen screen;
    Grill grill;
    Level level;
    int grillWidth = 300;
    int grillHeigh = 150;

    private Text text;

    public static void main(String[] args) { PApplet.main("Main"); }

    public void settings() {
        this.text = new Text(this);
        this.screen = new Screen(this, grillWidth, grillHeigh);
        this.grill = new Grill(grillWidth, grillHeigh, this);
        this.level = new Level(this, grill);
        fullScreen();
    }

    public void draw(){
        background(0, 255);
        if(!level.isGameOn() && key == ' ' && keyPressed){
            level.start("level_1");
            key = 'p';
        }
        else if (level.isGameOn()) {
            grill.clearLight();
            level.set(key, keyPressed, second());
            screen.set(grill.getPixelArray());

            if(!level.isGameOn()){
                clear();
                this.settings();
            }
        }
        else{
            fill(200, 196, 12);
            this.text.write("Press SPACE to start...", width/2, 4 * (height/5), 30);
        }
    }
}
