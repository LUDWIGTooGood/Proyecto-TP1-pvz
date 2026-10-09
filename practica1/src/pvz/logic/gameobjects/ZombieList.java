package pvz.logic.gameobjects;

import pvz.logic.Game;
import pvz.utils.Position;
import pvz.view.Messages;

/**
 * Representa la lista de zombis de la partida.
 * Permite almacenar, consultar, actualizar y eliminar zombis.
 */
public class ZombieList {

    private static final int MAX_ZOMBIES = 10;

    private Zombie[] zombies;
    private int size;

    /**
     * Crea una lista de zombis inicialmente vacía.
     */
    public ZombieList() {
        this.zombies = new Zombie[MAX_ZOMBIES];
        this.size = 0;
    }

    /**
     * Devuelve el número de zombis almacenados actualmente en la lista.
     *
     * @return número de zombis de la lista
     */
    public int size() {
        return this.size;
    }

    /**
     * Devuelve el icono del zombi situado en una posición determinada.
     *
     * @param position posición que se quiere consultar
     * @return icono del zombi si existe uno en la posición,
     *         o una cadena vacía si no existe ninguno
     */
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

    /**
     * Añade un zombi al final de la lista.
     *
     * @param zombie zombi que se quiere añadir
     */
    public void add(Zombie zombie) {
        this.zombies[this.size] = zombie;
        this.size++;
    }

    /**
     * Ataca al primer zombi situado a la derecha de una posición
     * y en su misma fila.
     *
     * @param position posición desde la que se realiza el ataque
     * @param damage   cantidad de daño que recibe el zombi
     * @return true si se ha encontrado y atacado algún zombi
     */
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

            if (!damaged) {
                targetPosition = targetPosition.right();
            }
        }

        return damaged;
    }

    /**
     * Indica si una posición está libre de zombis.
     *
     * @param position posición que se quiere comprobar
     * @return true si no existe ningún zombi en esa posición
     */
    public boolean isEmpty(Position position) {
        boolean empty = true;
        int i = 0;

        while (i < this.size && empty) {
            if (this.zombies[i].isInPosition(position)) {
                empty = false;
            }

            i++;
        }

        return empty;
    }

    /**
     * Actualiza todos los zombis almacenados en la lista.
     */
    public void update() {
        for (int i = 0; i < this.size; i++) {
            this.zombies[i].update();
        }
    }

    /**
     * Elimina de la lista todos los zombis que han muerto.
     */
    public void removeDead() {
        int aliveZombies = 0;

        for (int i = 0; i < this.size; i++) {
            if (this.zombies[i].isAlive()) {
                this.zombies[aliveZombies] = this.zombies[i];
                aliveZombies++;
            }
        }

        for (int i = aliveZombies; i < this.size; i++) {
            this.zombies[i] = null;
        }

        this.size = aliveZombies;
    }

    /**
     * Indica si existe algún zombi en una columna determinada.
     *
     * @param column columna que se quiere comprobar
     * @return true si existe al menos un zombi en esa columna
     */
    public boolean anyInColumn(int column) {
        boolean found = false;
        Position position = new Position(0, column);
        int i = 0;

        while (i < this.size && !found) {
            if (this.zombies[i].isVerticallyAligned(position)) {
                found = true;
            }

            i++;
        }

        return found;
    }
}