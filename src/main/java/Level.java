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

    boolean playerIsHide = false;
    ArrayList<Integer> hidingElements;


    public Level(PApplet processing, Grill grill, String levelName) {
        this.processing = processing;
        this.grill = grill;
        hidingElements = new ArrayList<>();
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
    }

    public void set(char key, boolean keyPressed) {

        // controle l'avance de la caméra
        if(player.getPositionX() == 230 && key == 'd' && keyPressed && positionX< elements.get(0).getTexture().width - grill.getGrillWidth()) {
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

        //place le collider du player pour que les éléments puissent interagir
        grill.setCollider(player.getCollider(), player.getPositionX(), player.getPositionY());
        //ballaye tout les éléments de la liste

        for(int i = 0; i < elements.size(); i++){
            //place les éléments
            GameElement element = elements.get(i);
            grill.setTexture(element.getTexture(), element.getPositionX(), element.getPositionY());
            //test les collisions
            try {
                grill.tryCollision(element, element.getPositionX(), element.getPositionY());
            } catch (Collision collision) {
                if(collision.getType().equals("fall") && sprites[i] == null){
                    element.setLight(2);
                    sprites[i] = new Sprite("hole_1_death", processing);
                    player.kill();
                }else if(collision.getType().equals("light") && player.isLighted() && sprites[i] == null){
                    if(element.elementName.equals("candle_1")){
                        sprites[i] = new Sprite("candle_1", processing);
                        elements.set(i, new GameElement("candle_1_on", ElementType.CANDLE, element.getPositionX(), element.getPositionY()));
                    }else if(element.elementName.equals("candle_2")){
                        sprites[i] = new Sprite("candle_2", processing);
                        elements.set(i, new GameElement("candle_2_on", ElementType.CANDLE, element.getPositionX(), element.getPositionY()));
                    }
                    elements.get(i).generate(processing);
                    elements.get(i).setLight(2);
                }else if(collision.getType().equals("hide") && element.getPositionY() + element.getTexture().height > player.getPositionY() + player.getTexture().height){
                    playerIsHide = true;
                    hidingElements.add(i);
                }
            }

            grill.setCollider(element.getCollider(), element.getPositionX(), element.getPositionY());

            if(element.getType() != null) {
                if (element.getType().equals(ElementType.HOLE)) {
                    if (sprites[i] != null && !sprites[i].isFinish())
                        grill.setTexture(sprites[i].getPicture(false), element.getPositionX(), element.getPositionY());
                }
                if (element.getType().equals(ElementType.CANDLE)) {
                    if (element.getShader() != null && !hidingElements.contains(i)) {
                        grill.setTexture(sprites[i].getPicture(false), element.getPositionX(), element.getPositionY());
                        grill.setShader(element.getShader(), element.getTexture(), element.getPositionX(), element.getPositionY());
                    }
                }
            }
        }

        if(player.isAlive()) {
            if(keyPressed) player.treat(key);
            grill.setTexture(player.getTexture(), player.getPositionX(), player.getPositionY());
            grill.setShader(player.getShader(), player.getTexture(), player.getPositionX(), player.getPositionY());
        }

        if(playerIsHide){
            for(int i : hidingElements){
                grill.setTexture(elements.get(i).getTexture(), elements.get(i).getPositionX(), elements.get(i).getPositionY());
                if(sprites[i] != null) grill.setTexture(sprites[i].getPicture(false), elements.get(i).getPositionX(), elements.get(i).getPositionY());
            }
            playerIsHide = false;
        }

    }
}
