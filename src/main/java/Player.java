import processing.core.PApplet;

public class Player {
    GameElement stand;
    GameElement standBackward;
    boolean isFlipped = false;
    Sprite walkSprite;
    Sprite walkBackwardSprite;
    boolean isWalking = false;
    int positionX, positionY;

    public Player(PApplet processing) {
        this.stand = new GameElement("player", processing, true, "player");
        this.standBackward = new GameElement("player", processing, true, "player");
        standBackward.flip();
        this.walkSprite = new Sprite("player_walk", 8, processing, true,"player");
        this.walkBackwardSprite = new Sprite("player_walk", 8, processing, true, "player");
        walkBackwardSprite.flip();
    }

    public void set(int x, int y){
        positionX = x;
        positionY = y;
    }

    public void move(int x, int y) {
        positionX += x;
        positionY += y;
        if(x<0) isFlipped = true;
        if(x>0) isFlipped = false;
        isWalking = true;
    }

    public GameElement getGameElement() {
        if(isFlipped) {
            if(isWalking) {
                isWalking = false;
                return walkBackwardSprite.getElement();
            }
            else return standBackward;
        }
        else if(isWalking) {
            isWalking = false;
            return walkSprite.getElement();
        }
        else return stand;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }
}
