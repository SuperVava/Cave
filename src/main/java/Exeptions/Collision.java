package Exeptions;

public class Collision extends Exception{
    private final int type;

    public Collision(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

}
