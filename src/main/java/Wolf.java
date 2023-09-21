import processing.core.PApplet;
import processing.core.PImage;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.ArrayList;

public class Wolf {

    private final int grillSize;
    private PApplet processing;
    private GameElement element;
    private Sprite walk;
    private Clip roarClip;
    private Clip attackClip;
    private int avance;
    private boolean isRunning = false;
    private boolean isFlipped = true;
    private boolean isStopped = false;

    public Wolf(PApplet processing, int grillSize) {
        this.grillSize = grillSize;
        this.processing = processing;
        this.element = new GameElement("wolf", ElementType.WOLF, 0, 0);
        element.generate(processing);
        element.setLight("wolf");
        element.flip();
        this.walk = new Sprite("wolf_walk",10, processing);
        walk.flip();

        //on mets le roar
        try {
            File file = new File("roar.mp3");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            this.roarClip = AudioSystem.getClip();
            roarClip.open(audioStream);
        }catch (Exception exception){
            System.out.println("Ya pas de roar!");
            System.out.println(exception);
        }
        //on mets le bouffer
        try {
            File file = new File("attack.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            this.attackClip = AudioSystem.getClip();
            attackClip.open(audioStream);
        }catch (Exception exception){
            System.out.println("Ya pas de groRROAAAR!");
            System.out.println(exception);
        }
    }

    public void set(int positionX, boolean isPlayerHide, boolean isPlayerFlipped, ArrayList<Integer> freeWays, int playerPositionY){
        isStopped = false;
        element.setFlipped(isFlipped);
        if(isFlipped)element.setPositionX(grillSize - positionX);
        else element.setPositionX(-element.getTexture().width - positionX);

        if(isPlayerHide && isPlayerFlipped != isFlipped) {
            int max = freeWays.size();
            int min = 1;
            int range = max - min + 1;
            int way = (int) (Math.random() * range) + min;
            if(freeWays.size() == 0) element.setPositionY(100);
            else element.setPositionY(freeWays.get(way) - element.getTexture().height);
            isRunning = false;
            System.out.println("pas vu, pas pris!");
        }else{
            element.setPositionY(playerPositionY - 20);
            isRunning = true;
            System.out.println("VU!");
        }
    }

    public PImage getTexture() {
        //gère l'avance
        if(isRunning) avance = 5;
        else avance = 1;

        if(isStopped) return element.getTexture();
        else {
            if (element.isFlipped) element.setPositionX(element.getPositionX() - avance);
            else element.setPositionX(element.getPositionX() + avance);
            return walk.getPicture(element.isFlipped);
        }
    }

    public int getPositionX() {
        return element.getPositionX();
    }
    public int getPositionY() {
        return element.getPositionY();
    }

    public PImage getShader() {
        return element.getShader();
    }

    public boolean isWolfHere() {
        if (this.element.isFlipped && getPositionX() > -element.getTexture().width) return true;
        else if (!this.element.isFlipped && getPositionX() < grillSize) return true;
        else{
            isRunning = false;
            isFlipped = !isFlipped;
            return false;
        }
    }

    public PImage getCollider() {
        return element.getCollider();
    }

    public void roar(){
        roarClip.start();
    }

    public GameElement getElement() {
        return element;
    }

    public void stop() {
        isStopped = true;
        element.setLight(0);
    }
}
