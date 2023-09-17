import Exeption.Collision;
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

    public Player(PApplet processing, Grill grill) {
        this.grill = grill;
        this.stand = new GameElement("player", processing, true, "player");
        this.standBackward = new GameElement("player", processing, true, "player");
        standBackward.flip();
        this.walkSprite = new Sprite("player_walk", 8, processing, true,"player");
        this.walkBackwardSprite = new Sprite("player_walk", 8, processing, true, "player");
        walkBackwardSprite.flip();
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
        }

        if(isWalking) {
            positionX += x;
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
}
