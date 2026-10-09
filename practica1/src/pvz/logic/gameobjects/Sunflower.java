package pvz.logic.gameobjects;

import pvz.logic.Game;
import pvz.utils.Position;
import pvz.view.Messages;

/**
 * Representa un girasol del juego.
 * Un girasol genera soles periódicamente mientras permanece vivo.
 */
public class Sunflower {

    public static final int COST = 20;
    private static final int INITIAL_HEALTH = 1;
    private static final int COOLDOWN = 3;
    private static final int GENERATED_COINS = 10;
    private static final int DAMAGE = 0;
    private static final int INITIAL_COOLDOWN_COUNTER = 0;
    private static final String LONG_NAME = "sunflower";
    private static final String SHORT_NAME = "s";

    private Position position;
    private int health;
    private int cooldownCounter;
    private Game game;

    /**
     * Crea un girasol en una posición determinada.
     *
     * @param position posición del girasol
     * @param game     partida a la que pertenece
     */
    public Sunflower(Position position, Game game) {
        this.position = position;
        this.game = game;
        this.health = INITIAL_HEALTH;
        this.cooldownCounter = INITIAL_COOLDOWN_COUNTER;
    }

    
    /**
     * Indica si el girasol continúa vivo.
     *
     * @return true si tiene vida mayor que cero
     */
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * Indica si el girasol se encuentra en una posición.
     *
     * @param position posición que se quiere comprobar
     * @return true si el girasol ocupa esa posición
     */
    public boolean isInPosition(Position position) {
        return this.position.equals(position);
    }

    /**
     * Hace que el girasol reciba daño.
     *
     * @param damage cantidad de daño recibido
     */
    public void receiveDamage(int damage) {
        //health = health - damage;
        health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    /**
     * Actualiza el estado del girasol.
     * Cada tres ciclos genera diez soles.
     */
    public void update() {
        if (isAlive()) {
            if (this.cooldownCounter == COOLDOWN) {
                this.game.generateCoins(GENERATED_COINS);
                this.cooldownCounter = 1;
            } else {
                this.cooldownCounter++;
            }
        }
    }

    /**
     * Devuelve la descripción del girasol.
     *
     * @return descripción del girasol
     */
    public static String getDescription() {
        return Messages.SUNFLOWER_DESCRIPTION.formatted(COST, DAMAGE, INITIAL_HEALTH);
    }

    /**
     * Devuelve la representación del girasol en el tablero.
     *
     * @return icono del girasol
     */
    public String getIcon() {
        return Messages.SUNFLOWER_ICON.formatted(health);
        // String.format("S[%02d]", health);
    }

    /**
     * Devuelve el nombre corto utilizado para identificar
     * al girasol.
     *
     * @return nombre corto del girasol
     */
    public static String shortName() {
        return SHORT_NAME;
    }

    /**
     * Devuelve el nombre completo utilizado para identificar
     * al girasol.
     *
     * @return nombre completo del girasol
     */
    public static String longName() {
        return LONG_NAME;
    }
}