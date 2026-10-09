package pvz.logic.gameobjects;

import pvz.utils.Position;
import pvz.view.Messages;

/**
 * Representa la lista de lanzaguisantes presentes en la partida.
 * Permite almacenar, consultar, actualizar y eliminar lanzaguisantes.
 */
public class PeashooterList {

    private static final int MAX_PEASHOOTERS = 32;

    private Peashooter[] peashooters;
    private int size;

    /**
     * Crea una lista de lanzaguisantes inicialmente vacía.
     */
    public PeashooterList() {
        this.peashooters = new Peashooter[MAX_PEASHOOTERS];
        this.size = 0;
    }

    /**
     * Devuelve el icono del lanzaguisantes situado en una posición determinada.
     *
     * @param position posición que se quiere consultar
     * @return icono del lanzaguisantes si existe uno en la posición,
     *         o una cadena vacía si no existe ninguno
     */
    public String iconInPosition(Position position) {
        String icon = Messages.EMPTY_STRING;
        int i = 0;

        while (i < this.size && icon.equals(Messages.EMPTY_STRING)) {
            if (this.peashooters[i].isInPosition(position)) {
                icon = this.peashooters[i].getIcon();
            }

            i++;
        }

        return icon;
    }

    /**
     * Actualiza todos los lanzaguisantes almacenados en la lista.
     */
    public void update() {
        for (int i = 0; i < this.size; i++) {
            this.peashooters[i].update();
        }
    }

    /**
     * Elimina de la lista todos los lanzaguisantes que han muerto.
     */
    public void removeDead() {
        int alivePeashooters = 0;

        for (int i = 0; i < this.size; i++) {
            if (this.peashooters[i].isAlive()) {
                this.peashooters[alivePeashooters] = this.peashooters[i];
                alivePeashooters++;
            }
        }

        for (int i = alivePeashooters; i < this.size; i++) {
            this.peashooters[i] = null;
        }

        this.size = alivePeashooters;
    }

    /**
     * Indica si una posición está libre de lanzaguisantes.
     *
     * @param position posición que se quiere comprobar
     * @return true si no existe ningún lanzaguisantes en esa posición
     */
    public boolean isEmpty(Position position) {
        boolean empty = true;
        int i = 0;

        while (i < this.size && empty) {
            if (this.peashooters[i].isInPosition(position)) {
                empty = false;
            }

            i++;
        }

        return empty;
    }

    /**
     * Añade un lanzaguisantes al final de la lista.
     *
     * @param peashooter lanzaguisantes que se quiere añadir
     */
    public void add(Peashooter peashooter) {
        this.peashooters[this.size] = peashooter;
        this.size++;
    }

    /**
     * Hace daño al lanzaguisantes situado en una posición determinada.
     * Si no existe ningún lanzaguisantes en esa posición,
     * no realiza ninguna acción.
     *
     * @param position posición en la que se realiza el ataque
     * @param damage   cantidad de daño que recibe el lanzaguisantes
     */
    public void receiveDamage(Position position, int damage) {
        boolean found = false;
        int i = 0;

        while (i < this.size && !found) {
            if (this.peashooters[i].isInPosition(position)) {
                this.peashooters[i].receiveDamage(damage);
                found = true;
            }

            i++;
        }
    }
}