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
    private Sprite run;
    private Clip roarClip;
    private Clip attackClip;
    private int avance;
    private boolean isRunning = false;
    private boolean isStopped = false;

    public Wolf(PApplet processing, int grillSize) {
        this.grillSize = grillSize;
        this.processing = processing;
        this.element = new GameElement("wolf", ElementType.WOLF, 0, 0);
        element.generate(processing);
        element.setLight("wolf");
        element.flip();
        this.walk = new Sprite("wolf_walk",15, processing);
        walk.flip();
        this.run = new Sprite("wolf_walk",5, processing);
        run.flip();
        element.setFlipped(true);

        //on mets le roar
        try {
            File file = new File("roar.wav");
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

    public void set(boolean isPlayerHide, boolean isPlayerFlipped, ArrayList<Integer> freeWays, int playerPositionY){
        isStopped = false;
        if(element.isFlipped)element.setPositionX(grillSize);
        else element.setPositionX(-element.getTexture().width);

        if(isPlayerHide && isPlayerFlipped != element.isFlipped) {
            int way = (int) (Math.random() * freeWays.size());
            if(freeWays.size() == 0) element.setPositionY(100);
            else element.setPositionY(freeWays.get(way) - element.getTexture().height);
            isRunning = false;
        }else{
            element.setPositionY(playerPositionY - 20);
            isRunning = true;
            attackClip.start();
        }
    }

    public PImage getTexture() {
        if(isStopped) return element.getTexture();
        else if(isRunning) return run.getPicture(element.isFlipped);
        else return walk.getPicture(element.isFlipped);
    }

    public void update(){
        //gère l'avance
        if(isRunning) avance = 5 ;
        else avance = 1;

        if(!isStopped) {
            if (element.isFlipped) element.setPositionX(element.getPositionX() - avance);
            else element.setPositionX(element.getPositionX() + avance);
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
            element.isFlipped = !element.isFlipped;
            return false;
        }
    }

    public PImage getCollider() {
        return element.getCollider();
    }

    public void roar(){
        System.out.println("roar");
        roarClip.setMicrosecondPosition(1000000);
        roarClip.start();
    }

    public GameElement getElement() {
        return element;
    }

    public void stop() {
        isStopped = true;
        element.setLight(0);
    }

    public boolean isFlipped() {
        return element.isFlipped;
    }
}
