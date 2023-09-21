import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PSurface;

public class Main extends PApplet{

    Screen screen;
    Grill grill;
    Level level;
    int grillWidth = 300;
    int grillHeigh = 150;

    PImage titleScreen;
    PImage titleScreenShader;
    PImage GAME_OVER;
    PImage GAME_OVER_SHADER;
    PImage win;
    PImage winShader;
    Sprite candleSprite;
    Boolean isScreenFull = false;

    private int switcher = 0;
    private int time = 0;
    public static void main(String[] args) { PApplet.main("Main"); }

    public void settings(){
        fullScreen();
    }

    public void setup() {
        this.screen = new Screen(this, grillWidth, grillHeigh);
        this.grill = new Grill(grillWidth, grillHeigh, this);
        this.level = new Level(this, grill);
        titleScreen = loadImage("titleScreen.png");
        titleScreenShader = loadImage("titleScreen_shader.png");
        GAME_OVER = loadImage("GAME_OVER.png");
        GAME_OVER_SHADER = loadImage("GAME_OVER_shader.png");
        win = loadImage("win.png");
        winShader = loadImage("win_shader.png");
        this.candleSprite = new Sprite("candle_1", 10, this);
        this.switcher = 1;
        surface.setTitle("CAVE");
        surface.setResizable(true);
    }

    public void draw(){
        background(0, 255);
        noCursor();
        if(switcher == 1) drawScreen("title");
        if(switcher == 2) drawScreen("black");
        if(switcher == 3) drawScreen("GAME OVER");
        if(switcher == 4) drawScreen("win");

        if(key == ' ' && keyPressed && !level.isGameOn()){
            level.start("level_1");
            key = 'p';
            switcher = 2;
            time = millis();

        } else if (level.isGameOn() && switcher != 2) {
            grill.clearLight();
            grill.clearCollider();
            level.set(key, keyPressed, (millis() / 1000));
            screen.set(grill.getPixelArray());

            if(!level.isGameOn()){
                if(level.isPlayerExit()) switcher = 4;
                else switcher = 3;
                time = millis();
            }
        }else if(switcher == 3 && time + 3000 < millis()) this.setup();
        else if(switcher == 2 && time + 1000 < millis()) switcher = 0;


    }

    private void drawScreen(String screenName){
        switch (screenName) {
            case "title":
                grill.setTexture(titleScreen, 0, 0);
                grill.setTexture(candleSprite.getPicture(false), 15, 70);
                grill.setShader(titleScreenShader, titleScreen, 0, 0);
                screen.set(grill.getPixelArray());
                break;

            case "GAME OVER":
                grill.setTexture(GAME_OVER, 0, 0);
                grill.setShader(GAME_OVER_SHADER, GAME_OVER, 0, 0);
                screen.set(grill.getPixelArray());
                break;

            case "black":
                grill.setTexture(titleScreen, 0, 0);
                grill.clearLight();
                screen.set(grill.getPixelArray());
        }
    }
}
