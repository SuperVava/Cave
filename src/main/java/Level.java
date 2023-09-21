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
    private boolean isPlayerExit;


    public Level(PApplet processing, Grill grill) {
        this.processing = processing;
        this.grill = grill;
        hidingElements = new ArrayList<>();
        isGameOn = false;
    }

    public void start(String levelName){
        isGameOn = false;
        isPlayerExit = false;
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
        if (player.getPositionX() >= 200 && key == 'd' && keyPressed && positionX < elements.get(0).getTexture().width - grill.getGrillWidth() && positionX > -elements.get(0).getTexture().width + grill.getGrillWidth()) {
            positionX--;
            player.move(-1, 0);
            grill.clearCollider();
            for (GameElement element : elements) {
                element.setPositionX(element.getPositionX() - 1);
            }
        }
        if (player.getPositionX() <= 100 && key == 'q' && keyPressed && positionX != 0 && positionX < 0) {
            positionX++;
            player.move(+1, 0);
            grill.clearCollider();
            for (GameElement element : elements) {
                element.setPositionX(element.getPositionX() + 1);
            }
        }

        //peut faire apparaitre le loup si le joueur parcourt 400px

        if (positionX < -100 && !isWolfCanBe) {
            setWolfNextRound(timer);
            this.wolf = new Wolf(processing, grill.getGrillWidth());
            isWolfCanBe = true;
        }
        if(positionX >= -100 || positionX < -elements.get(0).getTexture().width) isWolfCanBe = false;

        //génère un roar quatre sec avant le loup
        if (isWolfCanBe && timer > wolfTimer - 5 && !isWolfRoar) {
            wolf.roar();
            isWolfRoar = true;
        }

        //place le collider du player pour que les éléments puissent interagir
        grill.setCollider(player.getCollider(), player.getPositionX(), player.getPositionY());

        //ballaye tout les éléments de la liste

        for (int i = 0; i < elements.size(); i++) {

            //place les éléments
            GameElement element = elements.get(i);
            //ne traite que les éléments affichés
            if (element.getPositionX() < grill.getGrillWidth() && element.getPositionX() + element.getTexture().width > 0 || element.getIsGlowing()) {

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
                        sprites[i] = new Sprite("candle_2", 10, processing);
                        elements.set(i, new GameElement("candle_2_on", ElementType.HIDE, element.getPositionX(), element.getPositionY()));
                    }
                    elements.get(i).generate(processing);
                    elements.get(i).setLight(2);
                    element = elements.get(i);
                }
                if (collisions.contains("hide")) {
                    entitiesIsHide = true;
                    hidingElements.add(i);
                } else if (collisions.contains("stacked") && entitiesIsHide) {
                    hidingElements.add(i);
                } else if (collisions.contains("finish")) {
                    isPlayerExit = true;
                }

                grill.setCollider(element.getCollider(), element.getPositionX(), element.getPositionY());

                if (sprites[i] != null) {
                    if (element.getType().equals(ElementType.HOLE) && sprites[i].isFinish()) {
                        isGameOn = false;
                    } else {
                        if (!hidingElements.contains(i))
                            grill.setTexture(sprites[i].getPicture(false), element.getPositionX(), element.getPositionY());
                        grill.setShader(element.getShader(), element.getTexture(), element.getPositionX(), element.getPositionY());
                    }
                }
            }

        }

            if (player.isAlive()) {
                if (keyPressed && !isWolfHere) player.treat(key);
                grill.setTexture(player.getTexture(), player.getPositionX(), player.getPositionY());
                grill.setShader(player.getShader(), player.getTexture(), player.getPositionX(), player.getPositionY());
            }

        //génère un loup si c'est l'heure
        if (isWolfCanBe && timer > wolfTimer && !isWolfHere) {
            setWolf();
        }

            if (isWolfHere) {
                wolf.update();
                grill.setCollider(player.getCollider(), player.getPositionX(), player.getPositionY());
                //si le loup touche le joueur, c'est dead pour sa gueule
                ArrayList<String> collisions = grill.tryCollision(wolf.getElement(), wolf.getPositionX(), wolf.getPositionY());
                if (collisions.contains("kill") && (!player.isHide() || player.isFlipped() == wolf.isFlipped())) {
                    System.out.println("kill" + timer);
                    player.kill();
                    wolf.stop();
                    isGameOn = false;
                }else {
                    grill.setTexture(wolf.getTexture(), wolf.getPositionX(), wolf.getPositionY());
                    grill.setShader(wolf.getShader(), wolf.getShader(), wolf.getPositionX(), wolf.getPositionY());
                    //cache derrière le loup derrière le player si le player est devant
                    if (player.getPositionY() + player.getTexture().height > wolf.getPositionY() + wolf.getShader().height)
                        grill.setTexture(player.getTexture(), player.getPositionX(), player.getPositionY());
                    grill.setCollider(wolf.getCollider(), wolf.getPositionX(), wolf.getPositionY());
                    isWolfHere = wolf.isWolfHere();
                    if (!isWolfHere) setWolfNextRound(timer);
                }
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
        if (!isGameOn) {
            end();
        }
    }


    private void setWolfNextRound(int timer) {
        wolfTimer = timer + (int)((Math.random() * 10) + 20);
        isWolfRoar = false;
    }

    private void end() {
        elements.clear();
    }

    public boolean isGameOn() {
        return isGameOn;
    }

    public boolean isPlayerExit() {
        return isPlayerExit;
    }

    private void setWolf(){
        wolf.set(player.isHide(), player.isFlipped(), grill.getFreeWay(), player.getPositionY());
        isWolfHere = true;
    }
    }
