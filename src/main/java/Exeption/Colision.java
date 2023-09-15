package Exeption;

public class Colision extends Exception{
    private int type;

    public Colision(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

}
