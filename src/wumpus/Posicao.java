package wumpus;

/**
 *
 * @author bruno
 */
public class Posicao {

    int X;
    int Y;

    public Posicao() {

    }

    public Posicao(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    public int getX() {
        return X;
    }

    public void setX(int X) {
        this.X = X;
    }

    public int getY() {
        return Y;
    }

    public void setY(int Y) {
        this.Y = Y;
    }

    @Override
    public String toString() {
        String s = "(" + X + ", " + Y + ")";
        return s;
    }

}
