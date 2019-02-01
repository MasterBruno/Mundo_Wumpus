/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wumpus;

/**
 *
 * @author INSS-BENEF
 */
public class Percepcao {

    private boolean wumpus;
    private boolean fedor;
    private boolean poco;
    private boolean brisa;
    private boolean ouro;
    private boolean vazia;
    private boolean visitado;

    public Percepcao() {
        this.setVazia(true);
        this.setVisitado(false);
    }

    /*
     public Percepcao() {
     this.wumpus = false;
     this.fedor = false;
     this.poco = false;
     this.brisa = false;
     this.ouro = false;
     this.vazia = true;
     this.visitado = false;
     }
     */

    public Percepcao(boolean wumpus, boolean fedor, boolean poco, boolean brisa, boolean ouro, boolean visitado) {
        this.wumpus = wumpus;
        this.fedor = fedor;
        this.poco = poco;
        this.brisa = brisa;
        this.ouro = ouro;
        this.vazia = (!poco && !wumpus);
        this.visitado = visitado;
    }

    public boolean isWumpus() {
        return wumpus;
    }

    public void setWumpus(boolean wumpus) {
        this.wumpus = wumpus;
        this.fedor = !wumpus;
        this.poco = !wumpus;
        this.vazia = !wumpus;
        this.ouro = !wumpus;
    }

    public boolean isFedor() {
        return fedor;
    }

    public void setFedor(boolean fedor) {
        this.fedor = fedor;
        this.wumpus = !fedor;
        this.vazia = !fedor;
    }

    public boolean isPoco() {
        return poco;
    }

    public void setPoco(boolean poco) {
        this.poco = poco;
        this.brisa = !poco;
        this.wumpus = !poco;
        this.vazia = !poco;
        this.ouro = !poco;
    }

    public boolean isBrisa() {
        return brisa;
    }

    public void setBrisa(boolean brisa) {
        this.brisa = brisa;
        this.poco = !brisa;
        this.vazia = !brisa;
    }

    public boolean isOuro() {
        return ouro;
    }

    public void setOuro(boolean ouro) {
        this.ouro = ouro;
        this.poco = !ouro;
        this.wumpus = !ouro;
    }

    public boolean isVazia() {
        return vazia;
    }

    public void setVazia(boolean vazia) {
        this.vazia = vazia;
        this.poco = !vazia;
        this.brisa = !vazia;
        this.wumpus = !vazia;
        this.fedor = !vazia;
        this.brisa = !vazia;
    }

    public boolean isVisitado() {
        return visitado;
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        if (this.isWumpus()) {
            s.append("W");
        }
        if (this.isPoco()) {
            s.append("P");
        }
        if (this.isBrisa()) {
            s.append("~");
        }
        if (this.isFedor()) {
            s.append("#");
        }
        if (this.isOuro()) {
            s.append("O");
        }
        if (this.isVisitado()) {
            s.append("-");
        } else {
            s.append("X");
        }
        return s.toString();
    }

}
