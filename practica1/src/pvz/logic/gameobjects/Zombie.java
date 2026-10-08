package pvz.logic.gameobjects;

import pvz.logic.game;
import pvz.utils.position;



public class Zombie {
    private static final int MAX_HEALTH = 5;
    private static final int MOVE_EVERY_CYCLES = 2;
    private static final int DAMAGE = 1;

    private Position position;
    private int health;
    private int cycles;
    private final Game game;

    public Zombie (Position position, Game game){
        this.position = position;
        this.game = game;
        this.health = MAX_HEALTH;
        this.cycles = 0;
    }

    public boolean isAlive(){
        return this.health > 0;
    }

    public boolean isInPosition(Position p){
        return this.position.isHorizontallyAligned(p);
    }

    public boolean isHorizontallyAligned(Position p){
        return this.position.isHorizontallyAligned(p);
    }

     public boolean isVerticallyAligned(Position p){
        return this.position.isVerticallyAligned(p);
    }

    public void receiveAttack (int damage){
        this.health -= damage;
    }
    public String getIcon(){
        return String.format(Messages.ZOMBIE_ICON, this.health);
    }

}