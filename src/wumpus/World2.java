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
public class World2 {

    private Matriz<Percepcao> matriz;
    private int nPoco;
    private int nWumpus;

    public World2(int linhas, int colunas, int nPoco, int nWumpus) {
        this.matriz = new Matriz<>(linhas, colunas);
        this.setnPoco(nPoco);
        this.setnWumpus(nWumpus);
        this.gerar();
    }

    public Matriz<Percepcao> getMatriz() {
        return matriz;
    }

    public void setMatriz(Matriz<Percepcao> matriz) {
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
        return this.nPoco + this.nWumpus <= (this.matriz.getLinhas() * this.matriz.getColunas() - 2) / 2;
    }

    private void gerar() {
        if (!verifica()) {
            System.out.println("Impossível gerar essa quantidade de Poço e Wumpus.");
        } else {
            //  Gera a matriz
            for (int i = 0; i < this.matriz.getLinhas(); i++) {
                for (int j = 0; j < this.matriz.getColunas(); j++) {
                    this.matriz.adiciona(i, j, new Percepcao());
                }
            }

            int x, y;
            Random gerador = new Random();

            //  Gera os poços
            for (int i = 0; i < this.nPoco; i++) {
                x = gerador.nextInt(this.matriz.getLinhas());
                y = gerador.nextInt(this.matriz.getColunas());
                //  Verifica se foi gerado poço na posição incial e suas proximidades
                if (x <= 1 && y <= 1) {
                    i--;
                } else {
                    if (!this.matriz.retornaValor(x, y).isPoco()) {
                        //  Verifica se já existe Wumpus
                        this.matriz.retornaValor(x, y).setPoco(true);
                        gerarPerigo(x, y, "~");
                    } else {
                        i--;
                    }
                }
            }

            //  Gera os Wumpus
            for (int i = 0; i < this.nWumpus; i++) {
                x = gerador.nextInt(this.matriz.getLinhas());
                y = gerador.nextInt(this.matriz.getColunas());
                //  Verifica se foi gerado poço na posição incial e suas proximidades                
                if (x <= 1 && y <= 1) {
                    i--;
                } else {
                    //  Verifica se já existe Wumpus ou Poço
                    if (!(this.matriz.retornaValor(x, y).isPoco() || this.matriz.retornaValor(x, y).isWumpus())) {
                        this.matriz.retornaValor(x, y).setWumpus(true);
                        gerarPerigo(x, y, "#");
                    } else {
                        i--;
                    }
                }
            }

            do {
                x = gerador.nextInt(this.matriz.getLinhas());
                y = gerador.nextInt(this.matriz.getColunas());
                if (x >= 1 && y >= 1) {
                    if (!(this.matriz.retornaValor(x, y).isPoco() || this.matriz.retornaValor(x, y).isWumpus())) {
                        this.matriz.retornaValor(x, y).setOuro(true);
                        return;
                    }
                }
            } while (true);
        }
    }

    private void gerarPerigo(int x, int y, String value) {
        if ((x - 1) >= 0) {
            if (!(this.matriz.retornaValor(x - 1, y).isPoco() || this.matriz.retornaValor(x - 1, y).isWumpus())) {
                if (value.equals("~")) {
                    this.matriz.retornaValor(x - 1, y).setBrisa(true);
                } else {
                    this.matriz.retornaValor(x - 1, y).setFedor(true);
                }
            }
        }
        if ((x + 1) < this.matriz.getLinhas()) {
            if (!(this.matriz.retornaValor(x + 1, y).isPoco() || this.matriz.retornaValor(x + 1, y).isWumpus())) {
                if (value.equals("~")) {
                    this.matriz.retornaValor(x + 1, y).setBrisa(true);
                } else {
                    this.matriz.retornaValor(x + 1, y).setFedor(true);
                }
            }
        }
        if ((y - 1) >= 0) {
            if (!(this.matriz.retornaValor(x, y - 1).isPoco() || this.matriz.retornaValor(x, y - 1).isWumpus())) {
                if (value.equals("~")) {
                    this.matriz.retornaValor(x, y - 1).setBrisa(true);
                } else {
                    this.matriz.retornaValor(x, y - 1).setFedor(true);
                }
            }
        }
        if ((y + 1) < this.matriz.getColunas()) {
            if (!(this.matriz.retornaValor(x, y + 1).isPoco() || this.matriz.retornaValor(x, y + 1).isWumpus())) {
                if (value.equals("~")) {
                    this.matriz.retornaValor(x, y + 1).setBrisa(true);
                } else {
                    this.matriz.retornaValor(x, y + 1).setFedor(true);
                }
            }
        }
    }

}
