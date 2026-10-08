package pvz.logic.gameobjects;

import pvz.utils.Position;
import pvz.view.Messages;

public class ZombieList{
        private static final int MAX_ZOMBIES = 100;

        private Zombie[] zombies;
        private int count;

        public ZombieList(){
            this.zombies = new Zombie[MAX_ZOMBIES];
            this.count = 0;
        }
        public int size(){
            return.this.count;
        }

        public void add(Zombie z){
            this.zombies[this.count] = z;
            this.count++;
        }    
    
    public boolean isEmpty(Position p) {
    boolean empty = true;
    for (int i = 0; i < this.count; i++) {
        if (this.zombies[i].isInPosition(p)) {
            empty = false;
        }
    }
    return empty;
}

public String iconInPosition(Position p) {
    String icon = Messages.EMPTY_STRING;
    for (int i = 0; i < this.count; i++) {
        if (this.zombies[i].isInPosition(p)) {
            icon = this.zombies[i].getIcon();
        }
    }
    return icon;
}

}