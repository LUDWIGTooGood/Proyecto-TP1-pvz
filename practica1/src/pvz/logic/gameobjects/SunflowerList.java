package pvz.logic.gameobjects;

import pvz.utils.Position;
import pvz.view.Messages;

// Gestiona la coleccion de girasoles activos en la partida
public class SunflowerList {

    private static final int MAX_SUNFLOWERS = 32;

    private Sunflower[] sunflowers;
    private int size;

    // Inicializa la lista de girasoles vacia
    public SunflowerList() {
        this.sunflowers = new Sunflower[MAX_SUNFLOWERS];
        this.size = 0;
    }

    // Devuelve el icono del girasol en la posicion dada, o cadena vacia si no existe ninguno
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

    // Comprueba si no hay ningun girasol en la posicion dada
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

    // Elimina de la coleccion todos los girasoles que han muerto
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

    // Actualiza el estado de cada girasol en la lista
    public void update() {
        for (int i = 0; i < this.size; i++) {
            this.sunflowers[i].update();
        }
    }

    // Agrega un nuevo girasol al final de la lista
    public void add(Sunflower sunflower) {
        this.sunflowers[this.size] = sunflower;
        this.size++;
    }

    // Aplica danio al girasol situado en la posicion indicada si existe
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