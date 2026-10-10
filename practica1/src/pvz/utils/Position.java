package pvz.utils;

// Representa una posición del tablero mediante una fila y una columna
public class Position {
    
    private int row;
    private int col;

    // Crea una nueva posición a partir de fila y columna
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // Devuelve la fila de la posición
    public int row() {
        return this.row;
    }

    // Devuelve la columna de la posición
    public int column() {
        return this.col;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + row;
        result = prime * result + col;
        return result;
    }

    // Comprueba si dos posiciones representan la misma casilla
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

    // Devuelve una representación textual de la posición: (fila, columna)
    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }

    // Comprueba si está alineada horizontalmente (misma fila)
    public boolean isHorizontallyAligned(Position position) {
        return this.row == position.row;
    }

    // Comprueba si está alineada verticalmente (misma columna)
    public boolean isVerticallyAligned(Position position) {
        return this.col == position.col;
    }

    // Devuelve la posición situada una casilla a la izquierda
    public Position left() {
        return new Position(row, col - 1);
    }

    // Devuelve la posición situada una casilla a la derecha
    public Position right() {
        return new Position(row, col + 1);
    }
}