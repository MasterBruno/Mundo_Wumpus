/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wumpus;

/**
 *
 * @author bruno
 */
public class Wumpus {

    private Posicao posicao;
    private boolean dead;

    public Wumpus() {
        this.dead = true;
    }

    public Wumpus(Posicao pos) {
        this.dead = true;
        this.posicao = pos;
    }

    public Posicao getPosicao() {
        return posicao;
    }

    public void setPosicao(Posicao posicao) {
        this.posicao = posicao;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

}
