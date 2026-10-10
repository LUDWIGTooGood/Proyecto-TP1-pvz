package pvz.logic.gameobjects;

import pvz.logic.Game;
import pvz.utils.Position;
import pvz.view.Messages;

// Representa un girasol que genera soles periodicamente mientras esta vivo
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

    // Crea un girasol en la posicion y partida indicadas
    public Sunflower(Position position, Game game) {
        this.position = position;
        this.game = game;
        this.health = INITIAL_HEALTH;
        this.cooldownCounter = INITIAL_COOLDOWN_COUNTER;
    }

    // Indica si el girasol sigue con vida
    public boolean isAlive() {
        return health > 0;
    }

    // Comprueba si el girasol se encuentra en la posicion dada
    public boolean isInPosition(Position position) {
        return this.position.equals(position);
    }

    // Reduce la vida del girasol segun el danio recibido
    public void receiveDamage(int damage) {
        health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    // Actualiza el girasol y genera soles cada tres ciclos
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

    // Devuelve la descripcion formateada del girasol
    public static String getDescription() {
        return Messages.SUNFLOWER_DESCRIPTION.formatted(COST, DAMAGE, INITIAL_HEALTH);
    }

    // Devuelve el icono del girasol para el tablero
    public String getIcon() {
        return Messages.SUNFLOWER_ICON.formatted(health);
    }

    // Devuelve el identificador corto del girasol
    public static String shortName() {
        return SHORT_NAME;
    }

    // Devuelve el identificador largo del girasol
    public static String longName() {
        return LONG_NAME;
    }
}