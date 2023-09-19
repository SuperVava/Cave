package Exeptions;

public class Collision extends Exception{
    private final String type;

    public Collision(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
