import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

public class Player {
    private GameElement stand;
    private GameElement hide;
    private GameElement element;
    private Sprite walkSprite;
    private Sprite hideSprite;
    private boolean isWalking = false;
    private final Grill grill;
    private final Control control;
    private boolean isAlive = true;
    private boolean isLighted;
    private boolean canHide = false;
    private boolean isHiding = false;
    private boolean isHide = false;

    public Player(PApplet processing, Grill grill, int positionX, int positionY) {
        this.grill = grill;
        this.stand = new GameElement("player", ElementType.PLAYER, positionX, positionY);
        element = stand;
        element.generate(processing);
        element.setLight(2);
        isLighted = true;
        element.flip();
        this.walkSprite = new Sprite("player_walk",5, processing);
        walkSprite.flip();
        this.hideSprite = new Sprite("hiding",7, processing);
        hideSprite.flip();
        hide = new GameElement("hide", ElementType.PLAYER, 0, 0);
        hide.generate(processing);
        hide.setLight("hide");
        element.flip();
        this.control = new Control(this);
    }


    public void move(int x, int y) {
        isWalking = true;
        canHide = false;
        ArrayList<String> collision = grill.tryCollision(element, element.getPositionX() + x, element.getPositionY() + y);
        if (collision!= null) {
            if (collision.contains("wall")) isWalking = false;
            if (collision.contains("light")) turnOn();
            if (collision.contains("canHideLow")){
                canHide = true;
            }
        }


        if(isWalking) {
            element.setPositionX(element.getPositionX() + x);
            element.setPositionY(element.getPositionY() + y);
        }
    }

    public PImage getTexture() {
        PImage currentPosition;
        if(isHiding){
            currentPosition = showHidingPosition();
        }else if(isWalking) currentPosition= walkSprite.getPicture(element.isFlipped);
        else currentPosition = element.getTexture();

        isWalking = false;
        return currentPosition;
    }

    public PImage getShader() {
        if(isHide) return hide.getShader();
        else return element.getShader();
    }

    public PImage getCollider() {
        if(isHide) return hide.getCollider();
        else return element.getCollider();
    }


    public int getPositionX() {
        if(isHide) return hide.getPositionX();
        else return element.getPositionX();
    }

    public int getPositionY() {
        if(isHide) return hide.getPositionY();
        return element.getPositionY();
    }

    public void hide(){
        hideSprite.restart();
        isHiding = true;
        hide.setPositionX(stand.getPositionX());
        hide.setPositionY(stand.getPositionY() + 7);
    }

    private PImage showHidingPosition() {
        if(!hideSprite.isFinish() && !isHide) return hideSprite.getPicture(element.isFlipped);
        else {
            stand.setLight("player");
            isLighted = false;
            isHide = true;
            hide.setFlipped(element.isFlipped);
            return hide.getTexture();
        }
    }

    public void treat(char key) {
        isHiding = false;
        isHide = false;
        control.treat(key, canHide);
    }

    public void turnOn(){
        element.setLight(2);
        isLighted = true;
    }

    public boolean isLighted() {
        return isLighted;
    }

    public boolean isHide() {
        return isHide;
    }

    public boolean isFlipped() {
        return element.isFlipped;
    }

    public void lookRight(){
        element.setFlipped(false);
    }

    public void lookLeft() {
        element.setFlipped(true);
    }

    public void kill(){
        isAlive = false;
    }

    public boolean isAlive() {
        return isAlive;
    }
}
