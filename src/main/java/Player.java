import Exeptions.Collision;
import processing.core.PApplet;
import processing.core.PImage;

public class Player {
    GameElement element;
    boolean isFlipped = false;
    Sprite walkSprite;
    boolean isWalking = false;
    Grill grill;
    Control control;
    private boolean isAlive = true;
    boolean isLighted;

    public Player(PApplet processing, Grill grill, int positionX, int positionY) {
        this.grill = grill;
        this.element = new GameElement("player", ElementType.PLAYER, positionX, positionY);
        element.generate(processing);
        element.setLight(2);
        isLighted = true;
        element.flip();
        this.walkSprite = new Sprite("player_walk", processing);
        walkSprite.flip();
        this.control = new Control(this);
    }


    public void move(int x, int y) {
        isWalking = true;
        try{grill.tryCollision(element, element.getPositionX() + x, element.getPositionY() + y);}
        catch (Collision collision){
            System.out.println(collision.getType());
            if(collision.getType().equals("wall")) isWalking = false;
            if(collision.getType().equals("light")) turnOn();
        }

        if(isWalking) {
            if((element.getPositionX() < 230 || x < 0) && (element.getPositionX() > 23 || x > 0)) element.setPositionX(element.getPositionX() + x);
            element.setPositionY(element.getPositionY() + y);
            if (x < 0) isFlipped = true;
            if (x > 0) isFlipped = false;
        }
    }

    public PImage getTexture() {
        element.setFlipped(isFlipped);
        PImage currentPosition;
        if(isWalking) currentPosition= walkSprite.getPicture(isFlipped);
        else currentPosition = element.getTexture();

        isWalking = false;
        return currentPosition;
    }

    public PImage getShader() {
        return element.getShader();
    }

    public PImage getCollider() {
        return element.getCollider();
    }


    public int getPositionX() {
        return element.getPositionX();
    }

    public int getPositionY() {
        return element.getPositionY();
    }



    public void treat(char key) {
        control.treat(key);
    }

    public void turnOff(){
        element.setLight("player");
        isLighted = false;
    }

    public void turnOn(){
        element.setLight(2);
        isLighted = true;
    }

    public boolean isLighted() {
        return isLighted;
    }

    public void kill(){
        isAlive = false;
    }

    public boolean isAlive() {
        return isAlive;
    }
}
