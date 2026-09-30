package pvz.logic.gameobjects;

import pvz.logic.Game;
import pvz.utils.Position;
import pvz.view.Messages;

public class Peashooter {

    public static final int COST = 50;
    public static final int INITIAL_HEALTH = 3;
    public static final int DAMAGE = 1;

    private Position position;
    private Game game;
    private int health;

    public Peashooter(Position position, Game game) {
        this.position = position;
        this.game = game;
        this.health = INITIAL_HEALTH;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public boolean isInPosition(Position pos) {
        return this.position.equals(pos);
    }

    public void receiveDamage(int damage) {
        this.health -= damage;
    }

    public void update() {
        if (isAlive()) {
            this.game.shootRow(this.position, DAMAGE);
        }
    }

    public String getIcon() {
        return Messages.PEASHOOTER_ICON + "[" + this.health + "]";
    }
}