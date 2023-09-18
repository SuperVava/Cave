import Exeptions.Collision;
import processing.core.PApplet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Level {
    private final Player player;
    PApplet processing;
    Grill grill;
    int positionX = 0;

    ArrayList<GameElement> elements;
    GameElement backGround;

    Sprite death;


    public Level(PApplet processing, Grill grill, String levelName) {
        this.processing = processing;
        this.grill = grill;
        this.death = new Sprite("hole_1_death", 5, processing);
        try {
            FileInputStream loadSave = new FileInputStream(levelName);
            ObjectInputStream list = new ObjectInputStream(loadSave);
            this.elements = (ArrayList<GameElement>) list.readObject();
            System.out.println("Votre sauvegarde a été restaurée:");
            loadSave.close();
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println("Sauvegarde non retrouvée!");
            e.printStackTrace();
        }

        this.player = new Player(processing, grill, 100, 100);

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

        for(GameElement element : elements){
            try {
                grill.tryCollision(element, element.getPositionX(), element.getPositionY());
                grill.setTexture(element.getTexture(), element.getPositionX(), element.getPositionY());
                if(element.getIsGlowing()) grill.setShader(element.getShader(), element.getTexture(), element.getPositionX(), element.getPositionY());
                grill.setCollider(element.getCollider(), element.getPositionX(), element.getPositionY());
            } catch (Collision collision) {
                if(collision.getType() == ElementType.FOOT && !death.isFinish()){
                    grill.setTexture(death.getPicture(false), element.getPositionX(), element.getPositionY());
                    grill.setShader(processing.loadImage("Shader_100.png"), element.getTexture(), element.getPositionX(), element.getPositionY());
                }
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
