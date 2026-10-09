package pvz.logic.gameobjects;

import pvz.utils.Position;
import pvz.view.Messages;

/**
 * Representa la lista de girasoles presentes en la partida.
 * Permite almacenar, consultar, actualizar y eliminar girasoles.
 */
public class SunflowerList {

    private static final int MAX_SUNFLOWERS = 32;

    private Sunflower[] sunflowers;
    private int size;

    /**
     * Crea una lista de girasoles inicialmente vacía.
     */
    public SunflowerList() {
        this.sunflowers = new Sunflower[MAX_SUNFLOWERS];
        this.size = 0;
    }

    /**
     * Devuelve el icono del girasol situado en una posición determinada.
     *
     * @param position posición que se quiere consultar
     * @return icono del girasol si existe uno en la posición,
     *         o una cadena vacía si no existe ninguno
     */
    public String iconInPosition(Position position) {
        String icon = Messages.EMPTY_STRING;
        int i = 0;

        while (i < this.size && icon.equals(Messages.EMPTY_STRING)) {
            if (this.sunflowers[i].isInPosition(position)) {
                icon = this.sunflowers[i].getIcon();
            }

            i++;
        }

        return icon;
    }

    /**
     * Indica si una posición está libre de girasoles.
     *
     * @param position posición que se quiere comprobar
     * @return true si no existe ningún girasol en esa posición
     */
    public boolean isEmpty(Position position) {
        boolean empty = true;
        int i = 0;

        while (i < this.size && empty) {
            if (this.sunflowers[i].isInPosition(position)) {
                empty = false;
            }

            i++;
        }

        return empty;
    }

    /**
     * Elimina de la lista todos los girasoles que han muerto.
     */
    public void removeDead() {
        int aliveSunflowers = 0;

        for (int i = 0; i < this.size; i++) {
            if (this.sunflowers[i].isAlive()) {
                this.sunflowers[aliveSunflowers] = this.sunflowers[i];
                aliveSunflowers++;
            }
        }

        for (int i = aliveSunflowers; i < this.size; i++) {
            this.sunflowers[i] = null;
        }

        this.size = aliveSunflowers;
    }

    /**
     * Actualiza todos los girasoles almacenados en la lista.
     */
    public void update() {
        for (int i = 0; i < this.size; i++) {
            this.sunflowers[i].update();
        }
    }

    /**
     * Añade un girasol al final de la lista.
     *
     * @param sunflower girasol que se quiere añadir
     */
    public void add(Sunflower sunflower) {
        this.sunflowers[this.size] = sunflower;
        this.size++;
    }

    /**
     * Hace daño al girasol situado en una posición determinada.
     * Si no existe ningún girasol en esa posición, no realiza ninguna acción.
     *
     * @param position posición en la que se realiza el ataque
     * @param damage   cantidad de daño que recibe el girasol
     */
    public void receiveDamage(Position position, int damage) {
        boolean found = false;
        int i = 0;

        while (i < this.size && !found) {
            if (this.sunflowers[i].isInPosition(position)) {
                this.sunflowers[i].receiveDamage(damage);
                found = true;
            }

            i++;
        }
    }
}