import java.util.Objects;

public class Loc {
    int row;
    int col;

    public Loc(int row, int col){
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loc loc = (Loc) o;
        return row == loc.row && col == loc.col;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    @Override
    public String toString() {
        return "("+ row + ", " + col + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }


}
