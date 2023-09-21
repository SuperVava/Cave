public class Control {
    Player player;
    public Control(Player player) {
        this.player = player;
    }

    public void treat(char key, boolean canHide){
        if(key == 'z'){
            player.move(0, -1);
        }
        if(key == 'q'){
            player.move(-1, 0);
            player.lookLeft();
        }
        if(key == 's'){
            player.move(0, +1);
        }
        if(key == 'd'){
            player.move(+1, 0);
            player.lookRight();
        }
        if(key == ' ' && canHide){
            player.hide();
        }
    }
}
