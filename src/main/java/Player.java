import Exeptions.Collision;
import processing.core.PApplet;

public class Player {
    GameElement stand;
    GameElement standBackward;
    boolean isFlipped = false;
    Sprite walkSprite;
    Sprite walkBackwardSprite;
    boolean isWalking = false;
    int positionX, positionY;
    Grill grill;
    Control control;

    public Player(PApplet processing, Grill grill) {
        this.grill = grill;
        this.stand = new GameElement("player", processing, "player");
        stand.setLight(2);
        this.standBackward = new GameElement("player", processing, "player");
        standBackward.flip();
        standBackward.setLight(2);
        this.walkSprite = new Sprite("player_walk", 8, processing,"player");
        walkSprite.setLight(2);
        this.walkBackwardSprite = new Sprite("player_walk", 8, processing, "player");
        walkBackwardSprite.flip();
        walkBackwardSprite.setLight(2);
        this.control = new Control(this);
    }

    public void setPosition(int x, int y){
        positionX = x;
        positionY = y;
    }

    public void move(int x, int y) {
        isWalking = true;
        try{grill.tryCollision(stand, positionX + x, positionY + y);}
        catch (Collision colision){
            if(colision.getType() == ElementType.WALL) isWalking = false;
            if(colision.getType() == ElementType.CANDLE) turnOn();
        }

        if(isWalking) {
            if((positionX < 230 || x < 0) && (positionX > 23 || x > 0)) positionX += x;
            positionY += y;
            if (x < 0) isFlipped = true;
            if (x > 0) isFlipped = false;
        }
    }

    public void set() {
        GameElement currentPosition;

        if(isFlipped && isWalking) currentPosition= walkBackwardSprite.getElement();
        else if(isFlipped) currentPosition = standBackward;
        else if(isWalking) currentPosition = walkSprite.getElement();
        else currentPosition = stand;

        isWalking = false;
        grill.set(currentPosition, positionX, positionY);
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void treat(char key) {
        control.treat(key);
    }

    public void turnOff(){
        stand.setLight("player");
        standBackward.setLight("player_reversed");
        walkSprite.setLight("player");
        walkBackwardSprite.setLight("player_reversed");
    }

    public void turnOn(){
        stand.setLight(2);
        standBackward.setLight(2);
        walkSprite.setLight(2);
        walkBackwardSprite.setLight(2);
    }
}
