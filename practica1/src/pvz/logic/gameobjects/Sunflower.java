package pvz.logic.gameobjects;

import pvz.logic.Game;
import pvz.utils.Position;
import pvz.view.Messages;

public class Sunflower {

    public static final int COST = 20;
    public static final int INITIAL_HEALTH = 1;
    public static final int COOLDOWN = 3;
    public static final int COINS_GENERATED = 10;

    private Position position;
    private Game game;
    private int health;
    private int counter;

    public Sunflower(Position position, Game game) {
        this.position = position;
        this.game = game;
        this.health = INITIAL_HEALTH;
        this.counter = 0;
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
            this.counter++;
            if (this.counter >= COOLDOWN) {
                this.game.addCoins(COINS_GENERATED);
                this.counter = 1;
            }
        }
    }

    public String getIcon() {
        return Messages.SUNFLOWER_ICON + "[" + this.health + "]";
    }
}