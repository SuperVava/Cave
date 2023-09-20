import processing.core.PApplet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;

public class Level {
    private final Player player;
    private Wolf wolf;
    PApplet processing;
    Grill grill;
    int positionX = 0;

    ArrayList<GameElement> elements;
    Sprite[] sprites;

    boolean entitiesIsHide = false;
    ArrayList<Integer> hidingElements;

    boolean isWolfHere = false;
    boolean isWolfAtRight = true;


    public Level(PApplet processing, Grill grill, String levelName) {
        this.processing = processing;
        this.grill = grill;
        hidingElements = new ArrayList<>();
        try {
            FileInputStream loadSave = new FileInputStream(levelName);
            ObjectInputStream list = new ObjectInputStream(loadSave);
            this.elements = (ArrayList<GameElement>) list.readObject();
            elements.add(new GameElement("EXIT", ElementType.EXIT, 0,0));
            this.sprites = new Sprite[elements.size()];
            System.out.println("Votre sauvegarde a été restaurée:");
            loadSave.close();
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println("Sauvegarde non retrouvée!");
            e.printStackTrace();
        }

        this.player = new Player(processing, grill, 50, 70);
        this.wolf = new Wolf(processing, grill.getGrillWidth());

        for(GameElement element : elements){
            element.generate(processing);
        }
        elements.get(elements.size() - 1).setPositionX(elements.get(0).getTexture().width - elements.get(elements.size() - 1).getTexture().width);
        elements.get(elements.size() - 1).setLight("EXIT");
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

        //fait apparaitre le loup si le joueur parcourt 400px
        if(positionX < 0 && !isWolfHere){
            setWolf();
        }

        //place le collider du player pour que les éléments puissent interagir
        grill.setCollider(player.getCollider(), player.getPositionX(), player.getPositionY());
        if(isWolfHere) grill.setCollider(wolf.getCollider(), wolf.getPositionX(), wolf.getPositionY());
        //ballaye tout les éléments de la liste

        for(int i = 0; i < elements.size(); i++){

            //place les éléments
            GameElement element = elements.get(i);
            //ne traite que les éléments affichés
            if(element.getPositionX() < grill.getGrillWidth() && element.getPositionX() + element.getTexture().width > 0 || element.getIsGlowing()){

                grill.setTexture(element.getTexture(), element.getPositionX(), element.getPositionY());
                //test les collisions
                ArrayList<String> collisions = grill.tryCollision(element, element.getPositionX(), element.getPositionY());

                if (collisions.contains("fall") && sprites[i] == null) {
                    element.setLight(2);
                    sprites[i] = new Sprite("hole_1_death", processing);
                    player.kill();
                }
                if (collisions.contains("light") && player.isLighted() && sprites[i] == null) {
                    if (element.elementName.equals("candle_1")) {
                        sprites[i] = new Sprite("candle_1", processing);
                        elements.set(i, new GameElement("candle_1_on", ElementType.CANDLE, element.getPositionX(), element.getPositionY()));
                    }
                    if (element.elementName.equals("candle_2")) {
                        sprites[i] = new Sprite("candle_2", processing);
                        elements.set(i, new GameElement("candle_2_on", ElementType.CANDLE, element.getPositionX(), element.getPositionY()));
                    }
                    elements.get(i).generate(processing);
                    elements.get(i).setLight(2);
                    element = elements.get(i);
                }
                if (collisions.contains("hide") && element.getPositionY() + element.getTexture().height > player.getPositionY() + player.getTexture().height) {
                    entitiesIsHide = true;
                    hidingElements.add(i);
                } else if (collisions.contains("stacked") && entitiesIsHide){
                    hidingElements.add(i);
                } else if (collisions.contains("finish")){
                    end();
                }

                grill.setCollider(element.getCollider(), element.getPositionX(), element.getPositionY());

                if(sprites[i] != null) {
                    if(element.getType().equals(ElementType.HOLE) && sprites[i].isFinish()){
                        end();
                    }else {
                        if (!hidingElements.contains(i))
                            grill.setTexture(sprites[i].getPicture(false), element.getPositionX(), element.getPositionY());
                        grill.setShader(element.getShader(), element.getTexture(), element.getPositionX(), element.getPositionY());
                    }
                }
            }

        }

        if(player.isAlive()) {
            if(keyPressed && !isWolfHere) player.treat(key);
            grill.setTexture(player.getTexture(), player.getPositionX(), player.getPositionY());
            grill.setShader(player.getShader(), player.getTexture(), player.getPositionX(), player.getPositionY());
        }

        if(isWolfHere){
            grill.setTexture(wolf.getTexture(), wolf.getPositionX() + positionX, wolf.getPositionY());
            grill.setShader(wolf.getShader(), wolf.getShader(), wolf.getPositionX()+ positionX, wolf.getPositionY());
            //cache derrière le loup derrière le player si le player est devant
            if(player.getPositionY() + player.getTexture().height > wolf.getPositionY() + wolf.getShader().height)grill.setTexture(player.getTexture(), player.getPositionX(), player.getPositionY());

            isWolfHere = wolf.isWolfHere();
        }

        //cache le joueur derrière les éléments
        if(entitiesIsHide){
            for(int i : hidingElements){
                grill.setTexture(elements.get(i).getTexture(), elements.get(i).getPositionX(), elements.get(i).getPositionY());
                if(sprites[i] != null) grill.setTexture(sprites[i].getPicture(false), elements.get(i).getPositionX(), elements.get(i).getPositionY());
            }
            entitiesIsHide = false;
            hidingElements.clear();
        }

        GameElement exit = elements.get(elements.size()-1);
        grill.setTexture(exit.getTexture(), exit.getPositionX(), exit.getPositionY());
        grill.setShader(exit.getShader(), exit.getTexture(), exit.getPositionX(), exit.getPositionY());

    }

    private void end() {
        System.exit(1);
    }

    private void setWolf(){
        wolf.set(isWolfAtRight, positionX);
        isWolfHere = true;
        isWolfAtRight = !isWolfAtRight;
    }
}
