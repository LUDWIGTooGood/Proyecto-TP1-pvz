package pvz.utils;

import java.util.Objects;

public class Position {

    private int col;
    private int row;

    public Position(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public boolean isHorizontallyAligned (Position p){
        return this.row == p.row;
    }

    public boolean isVerticallyAligned (Position p){
        return this.col == p.col;
    }

    public Position left(){
        return new Position(this.row, this.col - 1);
    }

     public Position right(){
        return new Position(this.row, this.col + 1);
    }

    @Override
    public string toString(){
        return "(" + this.row + "," + this.col ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position other = (Position) obj;
        return this.col == other.col && this.row == other.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.col, this.row);
    }
}