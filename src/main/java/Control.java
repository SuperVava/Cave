public class Control {
    GameElement player;
    char key;
    public Control(GameElement player) {
        this.player = player;
        this.key = '*';
    }

    public void treat(char key) { this.key = key; }

    public void stop() {
        key = '*';
    }

    public void update(){
        if(key == 'z'){
            player.setPosition(0, -1);
        }
        if(key == 'q'){
            player.setPosition(-1, 0);
        }
        if(key == 's'){
            player.setPosition(0, +1);
        }
        if(key == 'd'){
            player.setPosition(+1, 0);
        }
        if(key == ' '){
        }
    }
}
