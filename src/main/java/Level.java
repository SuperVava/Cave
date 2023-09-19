import Exeptions.Collision;
import processing.core.PApplet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;

public class Level {
    private final Player player;
    PApplet processing;
    Grill grill;
    int positionX = 0;

    ArrayList<GameElement> elements;
    Sprite[] sprites;

    GameElement backGround;


    public Level(PApplet processing, Grill grill, String levelName) {
        this.processing = processing;
        this.grill = grill;
        try {
            FileInputStream loadSave = new FileInputStream(levelName);
            ObjectInputStream list = new ObjectInputStream(loadSave);
            this.elements = (ArrayList<GameElement>) list.readObject();
            this.sprites = new Sprite[elements.size()];
            System.out.println("Votre sauvegarde a été restaurée:");
            loadSave.close();
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println("Sauvegarde non retrouvée!");
            e.printStackTrace();
        }

        this.player = new Player(processing, grill, 50, 70);

        for(GameElement element : elements){
            element.generate(processing);
        }

        backGround = elements.get(0);
        elements.remove(0);
    }

    public void set(char key, boolean keyPressed) {

        if(player.getPositionX() == 230 && key == 'd' && keyPressed && positionX< backGround.getTexture().width - grill.getGrillWidth()) {
            positionX--;
            grill.clearCollider();
            for(GameElement element : elements){
                element.setPositionX(element.getPositionX() - 1);
            }
        }
        if(player.getPositionX() == 23 && key == 'q' && keyPressed && positionX != 0) {
            positionX++;
            grill.clearCollider();
            for(GameElement element : elements){
                element.setPositionX(element.getPositionX() + 1);
            }
        }

        grill.setTexture(backGround.getTexture(), positionX, 0);
        grill.setCollider(backGround.getCollider(), positionX, 0);

        for(int i = 0; i<elements.size(); i++){
            GameElement element = elements.get(i);
            //place la texture
            //place le shader si l'objet est brillant
            if(element.getIsGlowing()) grill.setShader(element.getShader(), element.getTexture(), element.getPositionX(), element.getPositionY());
            //test les collisions
            if(sprites[i] == null)try {
                grill.tryCollision(element, element.getPositionX(), element.getPositionY());
            } catch (Collision collision) {
                if(collision.getType().equals(ElementType.FOOT)){
                    element.setLight(2);
                    sprites[i] = new Sprite("hole_1_death", processing);
                }
                if(collision.getType().equals(ElementType.CANDLE) && player.isLighted()){
                    element.setLight(2);
                    if(element.elementName.equals("candle_1")) sprites[i] = new Sprite("candle_1", processing);
                    if(element.elementName.equals("candle_2")) sprites[i] = new Sprite("candle_2", processing);
                }
            }
            if(element.getType().equals(ElementType.HOLE)){
                if(sprites[i] != null && !sprites[i].isFinish()) grill.setTexture(sprites[i].getPicture(false), element.getPositionX(), element.getPositionY());
                else {
                    grill.setCollider(element.getCollider(), element.getPositionX(), element.getPositionY());
                    grill.setTexture(element.getTexture(), element.getPositionX(), element.getPositionY());
                }
            }
            if(element.getType().equals(ElementType.CANDLE)){
                if(element.getShader() != null){
                    grill.setTexture(sprites[i].getPicture(false), element.getPositionX(), element.getPositionY());
                    grill.setCollider(element.getCollider(), element.getPositionX(), element.getPositionY());
                }
                else grill.setTexture(element.getTexture(), element.getPositionX(), element.getPositionY());
            }
            if(element.getType().equals(ElementType.CRATE)){
                grill.setCollider(element.getCollider(), element.getPositionX(), element.getPositionY());
                grill.setTexture(element.getTexture(), element.getPositionX(), element.getPositionY());
            }
        }

        if(player.isAlive()) {
            if(keyPressed) player.treat(key);
            grill.setTexture(player.getTexture(), player.getPositionX(), player.getPositionY());
            grill.setShader(player.getShader(), player.getTexture(), player.getPositionX(), player.getPositionY());
            grill.setCollider(player.getCollider(), player.getPositionX(), player.getPositionY());
        }

    }
}
