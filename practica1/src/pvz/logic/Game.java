package pvz.logic;

import pvz.control.Level;
import pvz.utils.Position;

public class Game {

    private long seed;
    private Level level;
    private int coins;

    public Game(long seed, Level level) {
        this.seed = seed;
        this.level = level;
        this.coins = 50;
    }

    public int getCoins() {
        return this.coins;
    }

    public void addCoins(int coins) {
        this.coins += coins;
    }

    public void shootRow(Position pos, int damage) {
    }
}