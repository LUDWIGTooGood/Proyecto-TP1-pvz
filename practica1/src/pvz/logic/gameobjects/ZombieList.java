package pvz.logic.gameobjects;

import pvz.logic.Game;
import pvz.utils.Position;
import pvz.view.Messages;

// Gestiona la coleccion de zombis presentes en la partida
public class ZombieList {

    private static final int MAX_ZOMBIES = 10;

    private Zombie[] zombies;
    private int size;

    // Inicializa la lista de zombis vacia
    public ZombieList() {
        this.zombies = new Zombie[MAX_ZOMBIES];
        this.size = 0;
    }

    // Devuelve el numero actual de zombis en la lista
    public int size() {
        return this.size;
    }

    // Devuelve el icono del zombi en la posicion indicada, o cadena vacia si no hay
    public String iconInPosition(Position position) {
        String icon = Messages.EMPTY_STRING;
        int i = 0;
        while (i < this.size && icon.equals(Messages.EMPTY_STRING)) {
            if (this.zombies[i].isInPosition(position)) {
                icon = this.zombies[i].getIcon();
            }
            i++;
        }

        return icon;
    }

    // Agrega un nuevo zombi al final de la lista
    public void add(Zombie zombie) {
        this.zombies[this.size] = zombie;
        this.size++;
    }

    // Dania al primer zombi a la derecha de la posicion en su misma fila
    public boolean damage(Position position, int damage) {
        boolean damaged = false;
        Position targetPosition = position.right();

        while (targetPosition.column() < Game.NUM_COLS && !damaged) {

            int i = 0;

            while (i < this.size && !damaged) {
                if (this.zombies[i].isInPosition(targetPosition)) {
                    this.zombies[i].receiveAttack(damage);
                    damaged = true;
                }

                i++;
            }