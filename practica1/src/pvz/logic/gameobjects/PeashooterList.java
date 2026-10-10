package pvz.logic.gameobjects;

import pvz.utils.Position;
import pvz.view.Messages;

// Gestiona la coleccion de lanzaguisantes activos en la partida
public class PeashooterList {

    private static final int MAX_PEASHOOTERS = 32;

    private Peashooter[] peashooters;
    private int size;

    // Inicializa la lista de lanzaguisantes vacia
    public PeashooterList() {
        this.peashooters = new Peashooter[MAX_PEASHOOTERS];
        this.size = 0;
    }

    // Devuelve el icono del lanzaguisantes en la posicion indicada, o cadena vacia si no hay
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

    // Actualiza el estado de cada lanzaguisantes en la lista
    public void update() {
        for (int i = 0; i < this.size; i++) {
            this.peashooters[i].update();
        }
    }

    // Elimina de la coleccion todos los lanzaguisantes cuya vida llego a cero
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

    // Comprueba si no hay ningun lanzaguisantes en la posicion indicada
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

    // Agrega un nuevo lanzaguisantes al final de la lista
    public void add(Peashooter peashooter) {
        this.peashooters[this.size] = peashooter;
        this.size++;
    }

    // Aplica danio al lanzaguisantes ubicado en la posicion indicada si existe
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