package wumpus;

/**
 *
 * @author bruno
 * @param <T>
 */
public class Matriz<T> {

    private int linhas;
    private int colunas;
    private T M[][];

    public Matriz() {
    }

    public Matriz(int n, int m) {
        this.setLinhas(n);
        this.setColunas(m);
        this.M = (T[][]) new Object[n][m];
    }

    public T[][] getM() {
        return M;
    }

    public void setM(T[][] M) {
        this.M = M;
    }

    public int getLinhas() {
        return linhas;
    }

    private void setLinhas(int linhas) {
        if (linhas < 0) {
            this.linhas = 0;
        } else {
            this.linhas = linhas;
        }
    }

    public int getColunas() {
        return colunas;
    }

    private void setColunas(int colunas) {
        if (colunas < 0) {
            this.colunas = 0;
        } else {
            this.colunas = colunas;
        }
    }

    public void adiciona(int n, int m, T valor) {
        this.M[n][m] = valor;
    }

    public T retornaValor(int n, int m) {
        return this.M[n][m];
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < this.linhas; i++) {
            for (int j = 0; j < this.colunas; j++) {
                s.append(M[i][j]).append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }
}
