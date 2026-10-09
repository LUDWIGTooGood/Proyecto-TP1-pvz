package pvz.logic.gameobjects;

import pvz.utils.Position;
import pvz.view.Messages;

public class ZombieList {

    private static final int MAX_ZOMBIES = 100;

    private Zombie[] zombies;
    private int count;

    public ZombieList() {
        this.zombies = new Zombie[MAX_ZOMBIES];
        this.count = 0;
    }

    public int size() {
        return this.count;
    }

    public void add(Zombie z) {
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

    public void update() {
        for (int i = 0; i < this.count; i++) {
            this.zombies[i].update();
        }
    }

    public boolean damage(Position p, int damage) {
        boolean hit = false;
        for (int i = 0; i < this.count; i++) {
            if (this.zombies[i].isInPosition(p)) {
                this.zombies[i].receiveAttack(damage);
                hit = true;
            }
        }
        return hit;
    }

    public void removeDead() {
        int alive = 0;
        for (int i = 0; i < this.count; i++) {
            if (this.zombies[i].isAlive()) {
                this.zombies[alive] = this.zombies[i];
                alive++;
            }
        }
        for (int i = alive; i < this.count; i++) {
            this.zombies[i] = null;
        }
        this.count = alive;
    }

    public boolean anyInColumn(int column) {
        Position reference = new Position(0, column);
        boolean found = false;
        for (int i = 0; i < this.count; i++) {
            if (this.zombies[i].isVerticallyAligned(reference)) {
                found = true;
            }
        }
        return found;
    }
}