import processing.core.PApplet;

public class Player {
    GameElement stand;
    GameElement standBackward;
    boolean isFlipped = false;
    Sprite walkSprite;
    Sprite walkBackwardSprite;
    boolean isWalking = false;

    public Player(PApplet processing) {
        this.stand = new GameElement("player", processing, true);
        this.standBackward = new GameElement("player", processing, true);
        standBackward.flip();
        this.walkSprite = new Sprite("player_walk", 3, processing, true);
        this.walkBackwardSprite = new Sprite("player_walk", 3, processing, true);
        walkBackwardSprite.flip();
    }

    public void set(int x, int y){
        stand.setPosition(x, y);
    }

    public void move(int x, int y) {
        stand.setPosition(stand.getPositionX() + x, stand.getPositionY() + y);
        if(x<0) isFlipped = true;
        if(x>0) isFlipped = false;
        isWalking = true;
    }

    public GameElement getGameElement() {
        if(isFlipped) {
            standBackward.setPosition(stand.getPositionX(), stand.getPositionY());
            walkBackwardSprite.setPosition(stand.getPositionX(), stand.getPositionY());
            if(isWalking) {
                isWalking = false;
                return walkBackwardSprite.getElement();
            }
            else return standBackward;
        }
        else if(isWalking) {
            isWalking = false;
            walkSprite.setPosition(stand.getPositionX(), stand.getPositionY());
            return walkSprite.getElement();
        }
        else return stand;
    }
}
