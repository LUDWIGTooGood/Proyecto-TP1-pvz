package pvz.logic.gameobjects;

import pvz.logic.Game;
import pvz.utils.Position;
import pvz.view.Messages;

/**
 * Representa un zombi del juego.
 * Un zombi avanza hacia la izquierda y ataca a las plantas
 * situadas inmediatamente delante de él.
 */
public class Zombie {

    private static final int INITIAL_HEALTH = 5;
    private static final int COOLDOWN = 2;
    private static final int DAMAGE = 1;

    private int health;
    private int cooldownCounter;
    private Position position;
    private Game game;

    /**
     * Crea un zombi en una posición determinada.
     *
     * @param position posición inicial del zombi
     * @param game     partida a la que pertenece
     */
    public Zombie(Position position, Game game) {
        this.position = position;
        this.game = game;
        this.health = INITIAL_HEALTH;
        this.cooldownCounter = 0;
    }

    /**
     * Devuelve la representación del zombi en el tablero.
     *
     * @return icono del zombi
     */
    public String getIcon() {
        return Messages.ZOMBIE_ICON.formatted(this.health);
    }

    /**
     * Indica si el zombi se encuentra en una posición determinada.
     *
     * @param position posición que se quiere comprobar
     * @return true si el zombi ocupa esa posición
     */
    public boolean isInPosition(Position position) {
        return this.position.equals(position);
    }

    /**
     * Indica si el zombi está alineado horizontalmente
     * con una posición determinada.
     *
     * @param position posición con la que se quiere comparar
     * @return true si ambas posiciones están en la misma fila
     */
    public boolean isHorizontallyAligned(Position position) {
        return this.position.isHorizontallyAligned(position);
    }

    /**
     * Indica si el zombi está alineado verticalmente
     * con una posición determinada.
     *
     * @param position posición con la que se quiere comparar
     * @return true si ambas posiciones están en la misma columna
     */
    public boolean isVerticallyAligned(Position position) {
        return this.position.isVerticallyAligned(position);
    }

    /**
     * Hace que el zombi reciba una cantidad de daño.
     *
     * @param damage cantidad de daño recibido
     */
    public void receiveAttack(int damage) {
        this.health -= damage;

        if (this.health < 0) {
            this.health = 0;
        }
    }

    /**
     * Actualiza el estado del zombi.
     * El zombi ataca a una planta situada inmediatamente
     * a su izquierda o, si la posición está libre y corresponde
     * moverse, avanza una casilla hacia la izquierda.
     */
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

    /**
     * Indica si el zombi continúa vivo.
     *
     * @return true si su vida es mayor que cero
     */
    public boolean isAlive() {
        return this.health > 0;
    }
}