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
public class Estado implements Comparable<Estado> {

    private Percepcao room;
    private Posicao agente;
    private Posicao anterior;
    private int pai;

    public Estado() {
        this.room = new Percepcao();
    }

    public Estado(Posicao anterior) {
        this.anterior = anterior;
        this.room = new Percepcao();
    }

    public Percepcao getRoom() {
        return room;
    }

    public void setRoom(Percepcao room) {
        this.room = room;
    }

    public Posicao getAgente() {
        return agente;
    }

    public void setAgente(Posicao agente) {
        this.agente = agente;
    }

    public Posicao getAnterior() {
        return anterior;
    }

    public void setAnterior(Posicao anterior) {
        this.anterior = anterior;
    }

    public int getPai() {
        return pai;
    }

    public void setPai(int pai) {
        this.pai = pai;
    }

    @Override
    public String toString() {
        return "Estado{agente=" + agente + '}';
    }

    @Override
    public int compareTo(Estado node) {
        if ((agente.getX() - node.getAgente().getX()) <= 1) {
            return 1;
        } else if ((agente.getX() - node.getAgente().getX()) <= 1) {
            return -1;
        } else {
            return 0;
        }
    }

}
