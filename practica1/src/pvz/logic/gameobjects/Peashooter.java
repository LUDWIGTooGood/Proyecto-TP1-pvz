package pvz.logic.gameobjects;

import pvz.utils.Position;
import pvz.view.Messages;
import pvz.logic.Game;

// Representa un lanzaguisantes que ataca a zombis en su misma fila
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

    // Crea un nuevo lanzaguisantes en la posicion y partida indicadas
    public Peashooter(Position position, Game game) {
        this.position = position;
        this.game = game;
        this.health = INITIAL_HEALTH;
        this.cooldownCounter = 0;
    }

    // Devuelve el icono del lanzaguisantes para el tablero
    public String getIcon() {
        return Messages.PEASHOOTER_ICON.formatted(this.health);
    }

    // Comprueba si el lanzaguisantes se encuentra en la posicion dada
    public boolean isInPosition(Position position) {
        return this.position.equals(position);
    }

    // Devuelve la descripcion formateada del lanzaguisantes
    public static String getDescription() {
        return Messages.PEASHOOTER_DESCRIPTION.formatted(COST, DAMAGE, INITIAL_HEALTH);
    }

    // Indica si el lanzaguisantes sigue con vida
    public boolean isAlive() {
        return this.health > 0;
    }

    // Actualiza el lanzaguisantes y ataca si se cumple el tiempo de recarga
    public void update() {
        if (isAlive()) {
            cooldownCounter++;
            if (cooldownCounter >= COOLDOWN) {
                this.game.attackZombie(this.position, DAMAGE);
                cooldownCounter = 0;
            }
        }
    }

    // Reduce la vida del lanzaguisantes segun el danio recibido
    public void receiveDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    // Devuelve el identificador corto del lanzaguisantes
    public static String shortName() {
        return SHORT_NAME;
    }

    // Devuelve el identificador largo del lanzaguisantes
    public static String longName() {
        return LONG_NAME;
    }

}