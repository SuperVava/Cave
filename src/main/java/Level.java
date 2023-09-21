import processing.core.PApplet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;

public class Level {
    private Player player;
    private Wolf wolf;
    private PApplet processing;
    private Grill grill;
    private int positionX = 0;

    private ArrayList<GameElement> elements;
    private Sprite[] sprites;

    private boolean entitiesIsHide = false;
    private ArrayList<Integer> hidingElements;

    private boolean isWolfHere = false;
    private boolean isWolfRoar = false;
    private boolean isWolfCanBe = false;
    private int wolfTimer;

    private boolean isGameOn;


    public Level(PApplet processing, Grill grill) {
        this.processing = processing;
        this.grill = grill;
        hidingElements = new ArrayList<>();
        isGameOn = false;
    }

    public void start(String levelName){
        isGameOn = false;
        try {
            FileInputStream loadSave = new FileInputStream(levelName);
            ObjectInputStream list = new ObjectInputStream(loadSave);
            this.elements = (ArrayList<GameElement>) list.readObject();
            elements.add(new GameElement("EXIT", ElementType.EXIT, 0,0));
            this.sprites = new Sprite[elements.size()];
            System.out.println("Votre sauvegarde a été restaurée");
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
        elements.get(elements.size() - 1).setPositionX(elements.get(0).getTexture().width - elements.get(elements.size() - 1).getTexture().width);
        elements.get(elements.size() - 1).setLight("EXIT");

        isGameOn = true;
    }

    public void set(char key, boolean keyPressed, int timer) {

        // controle l'avance de la caméra
        if(player.getPositionX() >= 200 && key == 'd' && keyPressed && positionX< elements.get(0).getTexture().width - grill.getGrillWidth() && positionX > -elements.get(0).getTexture().width + grill.getGrillWidth()) {
            positionX--;
            player.move(-1, 0);
            grill.clearCollider();
            for(GameElement element : elements){
                element.setPositionX(element.getPositionX() - 1);
            }
        }
        if(player.getPositionX() <= 100 && key == 'q' && keyPressed && positionX != 0 && positionX < 0) {
            positionX++;
            player.move(+1, 0);
            grill.clearCollider();
            for(GameElement element : elements){
                element.setPositionX(element.getPositionX() + 1);
            }
        }

        //peut faire apparaitre le loup si le joueur parcourt 400px
        if(positionX == 0 && !isWolfCanBe){
            setWolfNextRound(timer);
            this.wolf = new Wolf(processing, grill.getGrillWidth());
            isWolfCanBe = true;
        }
        //génère un roar quatre sec avant le loup
        if(isWolfCanBe && timer > wolfTimer-4 && !isWolfRoar) {
            wolf.roar();
            isWolfRoar = true;
        }
        //génère un loup si c'est l'heure
        if(isWolfCanBe && timer > wolfTimer && !isWolfHere) {
            setWolf();
        }

        //place le collider du player pour que les éléments puissent interagir
        grill.setCollider(player.getCollider(), player.getPositionX(), player.getPositionY());
        if(isWolfHere) {
            ArrayList<String> collisions = grill.tryCollision(wolf.getElement(), wolf.getPositionX(), wolf.getPositionY());
            if(collisions.contains("kill")) {
                System.out.println("kill");
                player.kill();
                wolf.stop();
                end();
            }
            grill.setCollider(wolf.getCollider(), wolf.getPositionX(), wolf.getPositionY());
        }
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
                    sprites[i] = new Sprite("hole_1_death", 7, processing);
                    player.kill();
                }
                if (collisions.contains("light") && player.isLighted() && sprites[i] == null) {
                    if (element.elementName.equals("candle_1")) {
                        sprites[i] = new Sprite("candle_1", 10, processing);
                        elements.set(i, new GameElement("candle_1_on", ElementType.HIDE, element.getPositionX(), element.getPositionY()));
                    }
                    if (element.elementName.equals("candle_2")) {
                        sprites[i] = new Sprite("candle_2",10, processing);
                        elements.set(i, new GameElement("candle_2_on", ElementType.HIDE, element.getPositionX(), element.getPositionY()));
                    }
                    elements.get(i).generate(processing);
                    elements.get(i).setLight(2);
                    element = elements.get(i);
                }
                if (collisions.contains("hide")) {
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
        if(isGameOn) {
            if (player.isAlive()) {
                if (keyPressed && !isWolfHere) player.treat(key);
                grill.setTexture(player.getTexture(), player.getPositionX(), player.getPositionY());
                grill.setShader(player.getShader(), player.getTexture(), player.getPositionX(), player.getPositionY());
            }

            if (isWolfHere) {
                grill.setTexture(wolf.getTexture(), wolf.getPositionX() + positionX, wolf.getPositionY());
                grill.setShader(wolf.getShader(), wolf.getShader(), wolf.getPositionX() + positionX, wolf.getPositionY());
                //cache derrière le loup derrière le player si le player est devant
                if (player.getPositionY() + player.getTexture().height > wolf.getPositionY() + wolf.getShader().height)
                    grill.setTexture(player.getTexture(), player.getPositionX(), player.getPositionY());

                isWolfHere = wolf.isWolfHere();
                if (!isWolfHere) setWolfNextRound(timer);
            }

            //cache le joueur derrière les éléments
            if (entitiesIsHide) {
                for (int i : hidingElements) {
                    grill.setTexture(elements.get(i).getTexture(), elements.get(i).getPositionX(), elements.get(i).getPositionY());
                    if (sprites[i] != null)
                        grill.setTexture(sprites[i].getPicture(false), elements.get(i).getPositionX(), elements.get(i).getPositionY());
                }
                entitiesIsHide = false;
                hidingElements.clear();
            }

            GameElement exit = elements.get(elements.size() - 1);
            grill.setTexture(exit.getTexture(), exit.getPositionX(), exit.getPositionY());
            grill.setShader(exit.getShader(), exit.getTexture(), exit.getPositionX(), exit.getPositionY());
        }
    }

    private void setWolfNextRound(int timer) {
        int max = 20;
        int min = 10;
        int range = max - min + 1;
        wolfTimer = timer + (int)(Math.random() * range) + min;
        System.out.println(timer);
        System.out.println(wolfTimer);
    }

    private void end() {
        isGameOn = false;
        elements.clear();
    }

    public boolean isGameOn() {
        return isGameOn;
    }

    private void setWolf(){
        wolf.set(positionX, player.isHide(), player.isFlipped(), grill.getFreeWay(), player.getPositionY());
        isWolfHere = true;
        isWolfRoar = false;
    }
}
