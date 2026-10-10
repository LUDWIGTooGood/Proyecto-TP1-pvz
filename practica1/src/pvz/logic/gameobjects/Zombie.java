package pvz.logic.gameobjects;

import pvz.logic.Game;
import pvz.utils.Position;
import pvz.view.Messages;

// Representa un zombi que avanza hacia la izquierda y ataca plantas frente a el
public class Zombie {

    private static final int INITIAL_HEALTH = 5;
    private static final int COOLDOWN = 2;
    private static final int DAMAGE = 1;

    private int health;
    private int cooldownCounter;
    private Position position;
    private Game game;

    // Crea un nuevo zombi en la posicion inicial y partida indicadas
    public Zombie(Position position, Game game) {
        this.position = position;
        this.game = game;
        this.health = INITIAL_HEALTH;
        this.cooldownCounter = 0;
    }

    // Devuelve el icono del zombi para el tablero
    public String getIcon() {
        return Messages.ZOMBIE_ICON.formatted(this.health);
    }

    // Comprueba si el zombi ocupa la posicion dada
    public boolean isInPosition(Position position) {
        return this.position.equals(position);
    }

    // Comprueba si el zombi esta en la misma fila que otra posicion
    public boolean isHorizontallyAligned(Position position) {
        return this.position.isHorizontallyAligned(position);
    }

    // Comprueba si el zombi esta en la misma columna que otra posicion
    public boolean isVerticallyAligned(Position position) {
        return this.position.isVerticallyAligned(position);
    }

    // Reduce la vida del zombi segun el danio recibido
    public void receiveAttack(int damage) {
        this.health -= damage;

        if (this.health < 0) {
            this.health = 0;
        }
    }

    // Actualiza el zombi atacando o avanzando hacia la izquierda segun su tiempo de recarga
    public void update() {
        if (isAlive()) {

            Position nextPosition = this.position.left();
            if (this.cooldownCounter == COOLDOWN) {
                if (this.game.isEmpty(nextPosition)) {
                    this.position = nextPosition;
                }
                this.cooldownCounter = 0;
            }

            Position attackPosition = this.position.left();
            if (!this.game.isEmpty(attackPosition)) {
                this.game.attackPlant(attackPosition, DAMAGE);
            }

            this.cooldownCounter++;
        }
    }

    // Indica si el zombi sigue con vida
    public boolean isAlive() {
        return this.health > 0;
    }
}