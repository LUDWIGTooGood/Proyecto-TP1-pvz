package pvz.logic.gameobjects;

import pvz.logic.Game;
import pvz.utils.Position;
import pvz.view.Messages;

public class Zombie {

    public static final int INITIAL_HEALTH = 5;
    public static final int DAMAGE = 1;
    public static final int SPEED = 2;

    private Position position;
    private Game game;
    private int health;
    private int counter;

    public Zombie(Position position, Game game) {
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

    public void receiveAttack(int damage) {
        this.health -= damage;
    }

    public void update() {
        if (!isAlive()) {
            return;
        }

        // Atacar a la casilla de la izquierda
        Position leftPos = new Position(this.position.getCol() - 1, this.position.getRow());
        this.game.attackPlant(leftPos, DAMAGE);

        // Lógica de avance
        if (this.counter == SPEED) {
            if (this.game.isCellEmpty(leftPos)) {
                this.position = leftPos;
            }
            this.counter = 1;
        } else {
            this.counter++;
        }
    }

    public String getIcon() {
        return Messages.ZOMBIE_ICON + "[" + this.health + "]";
    }
}