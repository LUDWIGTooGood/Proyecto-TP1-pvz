package pvz.logic;

import java.util.Random;

import pvz.control.Level;
import pvz.logic.gameobjects.Peashooter;
import pvz.logic.gameobjects.PeashooterList;
import pvz.logic.gameobjects.Sunflower;
import pvz.logic.gameobjects.SunflowerList;
import pvz.utils.Position;

// Coordina la partida de Plants vs Zombies y gestiona el estado global del tablero
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

    // Crea una nueva partida con la semilla y nivel dados
    public Game(Long seed, Level level) {
        this.seed = seed;
        this.level = level;
        reset();
    }

    // Devuelve el icono del objeto situado en la posicion indicada, o vacio si no hay ninguno
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

    // Comprueba si el identificador corresponde a un tipo de planta valido
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

    // Devuelve el numero de ciclos transcurridos
    public int getCycles() {
        return this.cycles;
    }

    // Devuelve la cantidad de soles disponibles
    public int getCoins() {
        return this.coins;
    }

    // Devuelve el numero de zombis pendientes por salir
    public int getRemainingZombies() {
        return this.zombieManager.getRemainingZombies();
    }

    // Indica si la partida ha finalizado por victoria, derrota o abandono
    public boolean hasGameFinished() {
        boolean finished = playerWins()
                || this.zombieManager.doZombiesReachedTheHouse()
                || playerQuits();

        return finished;
    }

    // Indica si el jugador ha ganado al eliminar a todos los zombis
    public boolean playerWins() {
        boolean wins = this.zombieManager.allZombiesWereKilled();

        return wins;
    }

    // Indica si el jugador decidio abandonar la partida
    public boolean playerQuits() {
        return this.quit;
    }

    // Marca la partida como abandonada por el jugador
    public void quit() {
        this.quit = true;
    }

    // Avanza un ciclo de juego: genera zombis, actualiza elementos y limpia muertos
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

    // Reinicia la partida a su estado inicial restaurando la semilla original
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

    // Aniade una planta en la posicion indicada si hay soles y la casilla esta libre
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

    // Suma soles al contador del jugador
    public void generateCoins(int amount) {
        this.coins += amount;
    }

    // Ordena atacar al primer zombi a la derecha de la posicion dada
    public void attackZombie(Position position, int damage) {
        this.zombieManager.damageZombie(position, damage);
    }

    // Aplica danio a cualquier planta ubicada en la posicion indicada
    public void attackPlant(Position position, int damage) {
        this.sunflowerList.receiveDamage(position, damage);
        this.peashooterList.receiveDamage(position, damage);
    }

    // Comprueba si una posicion esta completamente libre de plantas y zombis
    public boolean isEmpty(Position position) {
        boolean empty = this.sunflowerList.isEmpty(position)
                && this.peashooterList.isEmpty(position)
                && this.zombieManager.isEmpty(position);

        return empty;
    }

    // Devuelve la posicion inicial de aparicion de un zombi en la fila indicada
    public Position newZombiePosition(int row) {
        Position position = new Position(row, NUM_COLS);

        return position;
    }

    // Comprueba si una posicion se encuentra dentro de los limites del tablero
    public boolean isInsideBoard(Position position) {
        boolean inside = position.row() >= 0
                && position.row() < NUM_ROWS
                && position.column() >= 0
                && position.column() < NUM_COLS;

        return inside;
    }
}