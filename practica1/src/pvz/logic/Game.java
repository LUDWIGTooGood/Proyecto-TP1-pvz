package pvz.logic;

import java.util.Random;

import pvz.control.Level;
import pvz.logic.gameobjects.Peashooter;
import pvz.logic.gameobjects.PeashooterList;
import pvz.logic.gameobjects.Sunflower;
import pvz.logic.gameobjects.SunflowerList;
import pvz.utils.Position;

/**
 * Representa una partida de Plants vs Zombies.
 * Mantiene el estado del juego y coordina los distintos
 * objetos que participan en la partida.
 */
public class Game {

    public static final int NUM_ROWS = 4;
    public static final int NUM_COLS = 8;
    public static final int INITIAL_COINS = 50;

    private long seed;
    private Level level;
    private Random rand;

    private int cycles;
    private int coins;
    private boolean quit;

    private SunflowerList sunflowerList;
    private PeashooterList peashooterList;
    private ZombiesManager zombieManager;

    /**
     * Crea una nueva partida.
     *
     * @param seed  semilla utilizada para generar los valores aleatorios
     * @param level nivel de dificultad de la partida
     */
    public Game(Long seed, Level level) {
        this.seed = seed;
        this.level = level;
        reset();
    }

    /**
     * Devuelve la representación del objeto situado en una posición.
     *
     * @param position posición que se quiere consultar
     * @return representación del objeto situado en la posición,
     *         o una cadena vacía si no existe ninguno
     */
    public String positionToString(Position position) {
        String result = this.sunflowerList.iconInPosition(position);

        if (result.isEmpty()) {
            result = this.peashooterList.iconInPosition(position);
        }

        if (result.isEmpty()) {
            result = this.zombieManager.iconInPosition(position);
        }

        return result;
    }

    /**
     * Comprueba si un nombre corresponde a alguno de los tipos
     * de plantas disponibles.
     *
     * @param objectName nombre del objeto que se quiere comprobar
     * @return true si corresponde a un girasol o lanzaguisantes
     */
    public boolean checkGameObject(String objectName) {
        boolean valid = false;

        if (objectName.equalsIgnoreCase(Sunflower.shortName())
                || objectName.equalsIgnoreCase(Sunflower.longName())
                || objectName.equalsIgnoreCase(Peashooter.shortName())
                || objectName.equalsIgnoreCase(Peashooter.longName())) {

            valid = true;
        }

        return valid;
    }

    /**
     * Devuelve el número de ciclos transcurridos.
     *
     * @return número de ciclos
     */
    public int getCycles() {
        return this.cycles;
    }

    /**
     * Devuelve el número de soles disponibles.
     *
     * @return número de soles
     */
    public int getCoins() {
        return this.coins;
    }

    /**
     * Devuelve el número de zombis que todavía quedan por aparecer.
     *
     * @return número de zombis pendientes
     */
    public int getRemainingZombies() {
        return this.zombieManager.getRemainingZombies();
    }

    /**
     * Indica si la partida ha terminado.
     *
     * @return true si el jugador ha ganado, los zombis han ganado
     *         o el jugador ha decidido abandonar
     */
    public boolean hasGameFinished() {
        boolean finished = playerWins()
                || this.zombieManager.doZombiesReachedTheHouse()
                || playerQuits();

        return finished;
    }

    /**
     * Indica si el jugador ha ganado la partida.
     *
     * @return true si todos los zombis han sido eliminados
     */
    public boolean playerWins() {
        boolean wins = this.zombieManager.allZombiesWereKilled();

        return wins;
    }

    /**
     * Indica si el jugador ha decidido abandonar la partida.
     *
     * @return true si el jugador ha ejecutado el comando de salida
     */
    public boolean playerQuits() {
        return this.quit;
    }

    /**
     * Finaliza la partida por decisión del jugador.
     */
    public void quit() {
        this.quit = true;
    }

    /**
     * Realiza un ciclo de actualización del juego.
     * Primero se intenta generar un nuevo zombi y después
     * se actualizan los distintos objetos del tablero.
     */
    public void update() {

        this.zombieManager.addZombie();

        this.sunflowerList.update();
        this.peashooterList.update();
        this.zombieManager.update();

        this.sunflowerList.removeDead();
        this.peashooterList.removeDead();
        this.zombieManager.removeDead();

        this.cycles++;
    }

    /**
     * Reinicia la partida a su estado inicial.
     * También reinicia el generador aleatorio utilizando
     * la misma semilla.
     */
    public void reset() {
        this.cycles = 0;
        this.coins = INITIAL_COINS;
        this.quit = false;

        this.rand = new Random(this.seed);

        this.sunflowerList = new SunflowerList();
        this.peashooterList = new PeashooterList();
        this.zombieManager = new ZombiesManager(
                this,
                this.level,
                this.rand);
    }

    /**
     * Añade una planta a una posición del tablero si dispone
     * de suficientes soles y la posición está disponible.
     *
     * @param plantType tipo de planta que se quiere añadir
     * @param position  posición donde se quiere colocar
     */
    public void addGameObject(String plantType, Position position) {

        if (isInsideBoard(position) && isEmpty(position)) {

            if (plantType.equalsIgnoreCase(Sunflower.shortName())
                    || plantType.equalsIgnoreCase(Sunflower.longName())) {

                if (this.coins >= Sunflower.COST) {
                    Sunflower sunflower = new Sunflower(position, this);
                    this.sunflowerList.add(sunflower);
                    this.coins -= Sunflower.COST;
                }
            }

            else if (plantType.equalsIgnoreCase(Peashooter.shortName())
                    || plantType.equalsIgnoreCase(Peashooter.longName())) {

                if (this.coins >= Peashooter.COST) {
                    Peashooter peashooter = new Peashooter(position, this);
                    this.peashooterList.add(peashooter);
                    this.coins -= Peashooter.COST;
                }
            }
        }
    }

    /**
     * Añade una cantidad de soles a los disponibles.
     *
     * @param amount cantidad de soles generados
     */
    public void generateCoins(int amount) {
        this.coins += amount;
    }

    /**
     * Ataca al primer zombi situado a la derecha de una posición.
     *
     * @param position posición desde la que se realiza el ataque
     * @param damage   cantidad de daño
     */
    public void attackZombie(Position position, int damage) {
        this.zombieManager.damageZombie(position, damage);
    }

    /**
     * Ataca a la planta situada en una posición determinada.
     *
     * @param position posición de la planta atacada
     * @param damage   cantidad de daño
     */
    public void attackPlant(Position position, int damage) {
        this.sunflowerList.receiveDamage(position, damage);
        this.peashooterList.receiveDamage(position, damage);
    }

    /**
     * Indica si una posición está libre de plantas y zombis.
     *
     * @param position posición que se quiere comprobar
     * @return true si no contiene ningún objeto
     */
    public boolean isEmpty(Position position) {
        boolean empty = this.sunflowerList.isEmpty(position)
                && this.peashooterList.isEmpty(position)
                && this.zombieManager.isEmpty(position);

        return empty;
    }

    /**
     * Devuelve la posición inicial de un nuevo zombi.
     * Los zombis aparecen inmediatamente a la derecha del tablero.
     *
     * @param row fila en la que aparece el zombi
     * @return posición inicial del zombi
     */
    public Position newZombiePosition(int row) {
        Position position = new Position(row, NUM_COLS);

        return position;
    }

    /**
     * Indica si una posición se encuentra dentro del tablero.
     *
     * @param position posición que se quiere comprobar
     * @return true si la posición pertenece al tablero
     */
    public boolean isInsideBoard(Position position) {
        boolean inside = position.row() >= 0
                && position.row() < NUM_ROWS
                && position.column() >= 0
                && position.column() < NUM_COLS;

        return inside;
    }
}