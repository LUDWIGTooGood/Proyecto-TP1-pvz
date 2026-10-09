package pvz.logic.gameobjects;

import pvz.utils.Position;
import pvz.view.Messages;
import pvz.logic.Game;

/**
 * Representa un lanzaguisantes del juego.
 * Un lanzaguisantes ataca a los zombis que se encuentran
 * en su misma fila mientras permanece vivo.
 */
public class Peashooter {

    public static final int COST = 50;

    private static final int INITIAL_HEALTH = 3;
    private static final int COOLDOWN = 1;
    private static final int DAMAGE = 1;
    private static final String LONG_NAME = "peashooter";
    private static final String SHORT_NAME = "p";

    private int health;
    private int cooldownCounter;
    private Position position;
    private Game game;

    /**
     * Crea un lanzaguisantes en una posición determinada.
     *
     * @param position posición del lanzaguisantes
     * @param game     partida a la que pertenece
     */
    public Peashooter(Position position, Game game) {
        this.position = position;
        this.game = game;
        this.health = INITIAL_HEALTH;
        this.cooldownCounter = 0;
    }

    /**
     * Devuelve la representación del lanzaguisantes en el tablero.
     *
     * @return icono del lanzaguisantes
     */
    public String getIcon() {
        return Messages.PEASHOOTER_ICON.formatted(this.health);
    }

    /**
     * Indica si el lanzaguisantes se encuentra en una posición determinada.
     *
     * @param position posición que se quiere comprobar
     * @return true si el lanzaguisantes ocupa esa posición
     */
    public boolean isInPosition(Position position) {
        return this.position.equals(position);
    }

    /**
     * Devuelve la descripción del lanzaguisantes.
     *
     * @return descripción del lanzaguisantes
     */
    public static String getDescription() {
        return Messages.PEASHOOTER_DESCRIPTION.formatted(COST, DAMAGE, INITIAL_HEALTH);
    }

    /**
     * Indica si el lanzaguisantes continúa vivo.
     *
     * @return true si su vida es mayor que cero
     */
    public boolean isAlive() {
        return this.health > 0;
    }

    /**
     * Actualiza el estado del lanzaguisantes.
     * Si está vivo y ha alcanzado su frecuencia de ataque,
     * dispara al primer zombi de su fila.
     */
    public void update() {
        if (isAlive()) {
            cooldownCounter++;
            if (cooldownCounter >= COOLDOWN) {
                this.game.attackZombie(this.position, DAMAGE);
                cooldownCounter = 0;
            }
        }
    }

    /**
     * Hace que el lanzaguisantes reciba una cantidad de daño.
     *
     * @param damage cantidad de daño recibido
     */
    public void receiveDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    /**
     * Devuelve el nombre corto utilizado para identificar
     * al lanzaguisantes.
     *
     * @return nombre corto del lanzaguisantes
     */
    public static String shortName() {
        return SHORT_NAME;
    }

    /**
     * Devuelve el nombre completo utilizado para identificar
     * al lanzaguisantes.
     *
     * @return nombre completo del lanzaguisantes
     */
    public static String longName() {
        return LONG_NAME;
    }

}