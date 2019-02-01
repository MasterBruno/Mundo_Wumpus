/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wumpus;

import java.util.Random;

/**
 *
 * @author INSS-BENEF
 */
public class World1 {

    private Matriz<String> matriz;
    private int nPoco;
    private int nWumpus;

    public World1(int linhas, int colunas, int nPoco, int nWumpus) {
        this.matriz = new Matriz<>(linhas, colunas);
        this.setnPoco(nPoco);
        this.setnWumpus(nWumpus);
        this.gerar();
    }

    public Matriz<String> getMatriz() {
        return matriz;
    }

    public void setMatriz(Matriz<String> matriz) {
        this.matriz = matriz;
    }

    public int getnPoco() {
        return nPoco;
    }

    private void setnPoco(int nPoco) {
        if (nPoco < 0) {
            this.nPoco = 0;
        } else {
            this.nPoco = nPoco;
        }
    }

    public int getnWumpus() {
        return nWumpus;
    }

    private void setnWumpus(int nWumpus) {
        if (nWumpus < 0) {
            this.nWumpus = 0;
        } else {
            this.nWumpus = nWumpus;
        }
    }

    //  Verifica se é possível gerar essa quantidade de Poços e Wumpus, visto que o agente é necessário
    private boolean verifica() {
        return this.nPoco + this.nWumpus <= this.matriz.getLinhas() * this.matriz.getColunas() - 2;
    }

    private void gerar() {
        if (!verifica()) {
            System.out.println("Impossível gerar essa quantidade de Poço e Wumpus.");
        } else {
            //  Gera a matriz
            for (int i = 0; i < this.matriz.getLinhas(); i++) {
                for (int j = 0; j < this.matriz.getColunas(); j++) {
                    this.matriz.adiciona(i, j, "");
                }
            }

            int x, y;
            Random gerador = new Random();
            for (int i = 0; i < this.nPoco; i++) {
                x = gerador.nextInt(this.matriz.getLinhas());
                y = gerador.nextInt(this.matriz.getColunas());
                if (x == 0 && y == 0) {
                    i--;
                } else {
                    this.matriz.adiciona(x, y, "P");
                    gerarPerigo(x, y, "~");
                }
            }
            for (int i = 0; i < this.nWumpus; i++) {
                x = gerador.nextInt(this.matriz.getLinhas());
                y = gerador.nextInt(this.matriz.getColunas());
                if (x == 0 && y == 0) {
                    i--;
                } else {
                    if (!this.matriz.retornaValor(x, y).equals("P") || !this.matriz.retornaValor(x, y).equals("W")) {
                        this.matriz.adiciona(x, y, "W");
                        gerarPerigo(x, y, "#");
                    } else {
                        i--;
                    }
                }
            }
            do {
                x = gerador.nextInt(this.matriz.getLinhas());
                y = gerador.nextInt(this.matriz.getColunas());
                if (x != 0 || y != 0) {
                    if (!this.matriz.retornaValor(x, y).equals("P") || !this.matriz.retornaValor(x, y).equals("W")) {
                        this.matriz.adiciona(x, y, "O");
                        return;
                    }
                }
            } while (true);
        }
    }

    private void gerarPerigo(int x, int y, String value) {
        if ((x - 1) >= 0) {
            if (!this.matriz.retornaValor(x - 1, y).equals("P") || !this.matriz.retornaValor(x - 1, y).equals("W")) {
                this.matriz.adiciona(x - 1, y, this.matriz.retornaValor(x - 1, y).concat(value));
            }
        }
        if ((x + 1) < this.matriz.getLinhas()) {
            if (!this.matriz.retornaValor(x + 1, y).equals("P") || !this.matriz.retornaValor(x + 1, y).equals("W")) {
                this.matriz.adiciona(x + 1, y, this.matriz.retornaValor(x + 1, y).concat(value));
            }
        }
        if ((y - 1) >= 0) {
            if (!this.matriz.retornaValor(x, y - 1).equals("P") || !this.matriz.retornaValor(x, y - 1).equals("W")) {
                this.matriz.adiciona(x, y - 1, this.matriz.retornaValor(x, y - 1).concat(value));
            }
        }
        if ((y + 1) < this.matriz.getColunas()) {
            if (!this.matriz.retornaValor(x, y + 1).equals("P") || !this.matriz.retornaValor(x, y + 1).equals("W")) {
                this.matriz.adiciona(x, y + 1, this.matriz.retornaValor(x, y + 1).concat(value));
            }
        }
    }

}
