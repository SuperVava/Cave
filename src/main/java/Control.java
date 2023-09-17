public class Control {
    Player player;
    char key;
    public Control(Player player) {
        this.player = player;
        this.key = '*';
    }

    public void treat(char key) { this.key = key; }

    public void stop() {
        key = '*';
    }

    public void update(){
        if(key == 'z'){
            player.move(0, -1);
        }
        if(key == 'q'){
            player.move(-1, 0);
        }
        if(key == 's'){
            player.move(0, +1);
        }
        if(key == 'd'){
            player.move(+1, 0);
        }
        if(key == ' '){
        }
    }
}
