package pvz.logic;

import java.util.Random;

import pvz.control.Level;
import pvz.logic.gameobjects.Zombie;
import pvz.logic.gameobjects.ZombieList;
import pvz.utils.Position;

/**
 * Manages the full lifecycle of zombies for a game session.
 *
 * <p>Responsibilities: deciding each cycle whether to spawn a new zombie
 * (probabilistically, subject to the remaining quota from {@link Level}),
 * delegating per-cycle updates and dead-removal to the underlying
 * {@link ZombieList}, and answering win/loss queries.
 */
public class ZombiesManager {

    private Game game;

    private Level level;

    private Random rand;

    private int remainingZombies;

    private ZombieList zombies;

    public ZombiesManager(Game game, Level level, Random rand) {
        this.game = game;
        this.level = level;
        this.rand = rand;
        this.remainingZombies = level.getNumberOfZombies();
        this.zombies = new ZombieList();
    }

    /**
     * Checks if the game should add (if possible) a zombie to the game.
     *
     * @return true if a zombie should be added to the game
     */
    private boolean shouldAddZombie() {
        return rand.nextDouble() < level.getZombieFrequency();
    }

    /**
     * Returns a random row within the board limits.
     *
     * @return a random row
     */
    private int randomZombieRow() {
        return rand.nextInt(Game.NUM_ROWS);
    }

    /**
     * Intenta añadir un zombi en una fila elegida aleatoriamente.
     *
     * @return true si el zombi ha podido ser añadido
     */
    public boolean addZombie() {
        int row = randomZombieRow();
        return addZombie(row);
    }

    /**
     * Intenta añadir un zombi en una fila determinada.
     *
     * @param row fila en la que se intenta añadir el zombi
     * @return true si el zombi ha podido ser añadido
     */
    public boolean addZombie(int row) {
        boolean canAdd = getRemainingZombies() > 0 && shouldAddZombie()
                && isPositionEmpty(Game.NUM_COLS, row);

        if (canAdd) {
            Position position = new Position(row, Game.NUM_COLS);
            Zombie zombie = new Zombie(position, this.game);

            this.zombies.add(zombie);
            this.remainingZombies--;
        }

        return canAdd;
    }

    /**
     * Indica si una posición está libre de zombis.
     *
     * @param column columna de la posición
     * @param row fila de la posición
     * @return true si no existe ningún zombi en dicha posición
     */
    private boolean isPositionEmpty(int column, int row) {
        Position position = new Position(row, column);
        return this.zombies.isEmpty(position);
    }

    /**
     * Devuelve el número de zombis que todavía quedan por aparecer.
     *
     * @return número de zombis pendientes
     */
    public int getRemainingZombies() {
        return this.remainingZombies;
    }

    /**
     * Indica si algún zombi ha alcanzado la casa.
     *
     * @return true si existe algún zombi en la columna -1
     */
    public boolean doZombiesReachedTheHouse() {
        return this.zombies.anyInColumn(-1);
    }

    /**
     * Hace daño al primer zombi situado a la derecha de una posición.
     *
     * @param position posición desde la que se realiza el ataque
     * @param damage cantidad de daño
     */
    public void damageZombie(Position position, int damage) {
        this.zombies.damage(position, damage);
    }

    /**
     * Devuelve el icono del zombi situado en una posición.
     *
     * @param position posición consultada
     * @return icono del zombi o cadena vacía si no existe
     */
    public String iconInPosition(Position position) {
        return this.zombies.iconInPosition(position);
    }

    /**
     * Indica si una posición está libre de zombis.
     *
     * @param position posición que se quiere comprobar
     * @return true si no existe ningún zombi en ella
     */
    public boolean isEmpty(Position position) {
        return this.zombies.isEmpty(position);
    }

    /**
     * Actualiza todos los zombis.
     */
    public void update() {
        this.zombies.update();
    }

    /**
     * Indica si todos los zombis de la partida han sido eliminados.
     *
     * @return true si no quedan zombis por aparecer ni zombis activos
     */
    public boolean allZombiesWereKilled() {
        return this.remainingZombies == 0 && this.zombies.size() == 0;
    }

    /**
     * Elimina los zombis muertos.
     */
    public void removeDead() {
        this.zombies.removeDead();
    }
}