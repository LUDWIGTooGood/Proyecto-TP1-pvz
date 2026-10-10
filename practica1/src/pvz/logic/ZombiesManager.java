package pvz.logic;

import java.util.Random;

import pvz.control.Level;
import pvz.logic.gameobjects.Zombie;
import pvz.logic.gameobjects.ZombieList;
import pvz.utils.Position;

/**
- Manages the full lifecycle of zombies for a game session.
 *
- <p>Responsibilities: deciding each cycle whether to spawn a new zombie
- (probabilistically, subject to the remaining quota from {@link Level}),
- delegating per-cycle updates and dead-removal to the underlying
- {@link ZombieList}, and answering win/loss queries.
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
- Checks if the game should add (if possible) a zombie to the game.
     *
- 
@return true if a zombie should be added to the game
     */
    private boolean shouldAddZombie() {
        return rand.nextDouble() < level.getZombieFrequency();
    }

    /**
- Returns a random row within the board limits.
     *
- 
@return a random row
     */
    private int randomZombieRow() {
        return rand.nextInt(Game.NUM_ROWS);
    }

    // Intenta anadir un zombi en una fila elegida aleatoriamente
    public boolean addZombie() {
        int row = randomZombieRow();
        return addZombie(row);
    }

    // Intenta anadir un zombi en una fila determinada
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

    // Indica si una posicion esta libre de zombis
    private boolean isPositionEmpty(int column, int row) {
        Position position = new Position(row, column);
        return this.zombies.isEmpty(position);
    }

    // Devuelve el numero de zombis que todavia quedan por aparecer
    public int getRemainingZombies() {
        return this.remainingZombies;
    }

    // Indica si algun zombi ha alcanzado la casa (columna -1)
    public boolean doZombiesReachedTheHouse() {
        return this.zombies.anyInColumn(-1);
    }

    // Hace danio al primer zombi situado a la derecha de una posicion
    public void damageZombie(Position position, int damage) {
        this.zombies.damage(position, damage);
    }

    // Devuelve el icono del zombi situado en una posicion
    public String iconInPosition(Position position) {
        return this.zombies.iconInPosition(position);
    }

    // Indica si una posicion esta libre de zombis
    public boolean isEmpty(Position position) {
        return this.zombies.isEmpty(position);
    }

    // Actualiza todos los zombis
    public void update() {
        this.zombies.update();
    }

    // Indica si todos los zombis de la partida han sido eliminados
    public boolean allZombiesWereKilled() {
        return this.remainingZombies == 0 && this.zombies.size() == 0;
    }

    // Elimina los zombis muertos
    public void removeDead() {
        this.zombies.removeDead();
    }
}