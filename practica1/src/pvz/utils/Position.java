package pvz.utils;

/**
 * Representa una posición del tablero mediante una fila y una columna.
 */
public class Position {
    
    private int row;
    private int col;

    /**
     * Crea una nueva posición.
     *
     * @param row fila de la posición
     * @param col columna de la posición
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Devuelve la fila de la posición.
     *
     * @return fila de la posición
     */
    public int row() {
        return this.row;
    }

    /**
     * Devuelve la columna de la posición.
     *
     * @return columna de la posición
     */
    public int column() {
        return this.col;
    }

    /**
     * Comprueba si dos posiciones representan la misma casilla.
     *
     * @param obj objeto con el que se compara
     * @return true si ambas posiciones tienen la misma fila y columna
     */
    /*
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Position)) {
            return false;
        }
        Position other = (Position) obj;
        return row == other.row && col == other.col;
    }
     */

    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + row;
        result = prime * result + col;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Position other = (Position) obj;
        if (this.row != other.row)
            return false;
        if (col != other.col)
            return false;
        return true;
    }

    /**
     * Devuelve una representación textual de la posición.
     *
     * @return posición en formato (fila, columna)
     */
    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }

    /**
     * Indica si esta posición está alineada horizontalmente con otra.
     * Dos posiciones están alineadas horizontalmente cuando tienen la misma fila.
     *
     * @param position posición con la que se quiere comparar
     * @return true si ambas posiciones tienen la misma fila
     */
    public boolean isHorizontallyAligned(Position position) {
        return this.row == position.row;
    }

    /**
     * Indica si esta posición está alineada verticalmente con otra.
     * Dos posiciones están alineadas verticalmente cuando tienen la misma columna.
     *
     * @param position posición con la que se quiere comparar
     * @return true si ambas posiciones tienen la misma columna
     */
    public boolean isVerticallyAligned(Position position) {
        return this.col == position.col;
    }

    /**
     * Devuelve la posición situada inmediatamente a la izquierda.
     *
     * @return posición situada una columna a la izquierda
     */
    public Position left() {
        return new Position(row, col - 1);
    }

    /**
     * Devuelve la posición situada inmediatamente a la derecha.
     *
     * @return posición situada una columna a la derecha
     */
    public Position right() {
        return new Position(row, col + 1);
    }
}