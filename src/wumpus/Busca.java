/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wumpus;

import java.util.ArrayList;
import java.util.List;

/*
 * @author bruno
 */
public class Busca {

    private Posicao inicio;
    private Posicao fim;
    private Matriz<Percepcao> mConhecimento;
    private final World2 mundo;
    private List<Posicao> provWumpus;
    private List<Posicao> provPoco;
    private List<Estado> borda;
    private List<Estado> caminho;

    public Busca(int linhas, int colunas, int nPoco, int nWumpus) {
        this.inicio = new Posicao(0, 0);
        this.fim = this.inicio;
        this.mundo = new World2(linhas, colunas, nPoco, nWumpus);
        this.mConhecimento = new Matriz<>(linhas, colunas);
        this.provPoco = new ArrayList<>();
        this.provWumpus = new ArrayList<>();
        this.borda = new ArrayList<>();
        this.caminho = new ArrayList<>();
        this.inicializa();
        System.out.println(this.provPoco);
    }

    public Posicao getInicio() {
        return inicio;
    }

    public void setInicio(Posicao inicio) {
        this.inicio = inicio;
    }

    public Posicao getFim() {
        return fim;
    }

    public void setFim(Posicao fim) {
        this.fim = fim;
    }

    public Matriz getMatriz() {
        return mConhecimento;
    }

    public void setMatriz(Matriz matriz) {
        this.mConhecimento = matriz;
    }

    public List<Posicao> getProvWumpus() {
        return provWumpus;
    }

    public void setProvWumpus(List<Posicao> provWumpus) {
        this.provWumpus = provWumpus;
    }

    public List<Posicao> getProvPoco() {
        return provPoco;
    }

    public void setProvPoco(List<Posicao> provPoco) {
        this.provPoco = provPoco;
    }

    public List<Estado> getBorda() {
        return borda;
    }

    public void setBorda(List<Estado> borda) {
        this.borda = borda;
    }

    public List<Estado> getCaminho() {
        return caminho;
    }

    public void setCaminho(List<Estado> caminho) {
        this.caminho = caminho;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(this.inicio).append("\n").append(this.fim).append("\n").append(this.mConhecimento).append("\n");
        return s.toString();
    }

    //  Adiciona na lista de prioridades os Estados
    public void addBorda(Estado node) {
        borda.add(node);
    }

    //  Inicializa com estado inicial
    private void inicializa() {
        for (int i = 0; i < this.mConhecimento.getLinhas(); i++) {
            for (int j = 0; j < this.mConhecimento.getColunas(); j++) {
                this.mConhecimento.adiciona(i, j, new Percepcao());
            }
        }
        System.out.println(this.mundo.getMatriz());

        Estado node = new Estado(null);
        node.setAgente(this.inicio);
        node.getRoom().setVisitado(true);
        this.mConhecimento.adiciona(0, 0, node.getRoom());

        node.setAnterior(null);
        node.setPai(-1);
        this.searchGold(node);
    }

    /*
     //  Retorna em int a distância de Manhattan
     public int Manhattan(Estado node) {
     int result = Math.abs(fim.getX() - node.getAgente().getX()) + Math.abs(fim.getY() - node.getAgente().getY());
     node.setHeurist(result);
     return result;
     }
     */
    //  Retorna se o Estado é o objetivo
    public boolean objetivo(Estado node) {
        return this.mundo.getMatriz().retornaValor(node.getAgente().getX(), node.getAgente().getY()).isOuro();
    }

    //  Realiza a busca pelo Ouro 
    public void searchGold(Estado node) {
        this.addBorda(node);
        while (!this.borda.isEmpty()) {
            Estado v = this.borda.remove(0);
            this.mConhecimento.retornaValor(v.getAgente().getX(), v.getAgente().getY()).setVisitado(true);
            //  System.out.println(this.borda);
            //  System.out.println(this.borda);

            //  System.out.println(v.getAgente());
            if (this.objetivo(v)) {
                return;
            } else {
                switch (verificaSala(v.getAgente())) {
                    case 1:
                        possivel(v.getAgente(), "#~");
                        break;
                    case 2:
                        possivel(v.getAgente(), "~");
                        gerarPer(v.getAgente(), "~");
                        break;
                    case 3:
                        possivel(v.getAgente(), "#");
                        gerarPer(v.getAgente(), "#");
                        break;
                    case 4:
                        gerarPer(v.getAgente(), "P");
                        System.out.println("Morreu");
                        break;
                    case 5:
                        gerarPer(v.getAgente(), "W");
                        System.out.println("Morreu");
                        break;
                    case 6:
                        //  gerarPer(v.getAgente(), "");
                        System.out.println("Achou");
                    default:
                        gerarPer(v.getAgente(), "O");
                        break;
                }
            }
        }
    }

    public void move() {

    }

    public int verificaSala(Posicao v) {
        if (this.mundo.getMatriz().retornaValor(v.getX(), v.getY()).isBrisa() && this.mundo.getMatriz().retornaValor(v.getX(), v.getY()).isFedor()) {
            return 1;
        } else if (this.mundo.getMatriz().retornaValor(v.getX(), v.getY()).isBrisa()) {
            return 2;
        } else if (this.mundo.getMatriz().retornaValor(v.getX(), v.getY()).isFedor()) {
            return 3;
        } else if (this.mundo.getMatriz().retornaValor(v.getX(), v.getY()).isPoco()) {
            return 4;
        } else if (this.mundo.getMatriz().retornaValor(v.getX(), v.getY()).isWumpus()) {
            return 5;
        } else if (this.mundo.getMatriz().retornaValor(v.getX(), v.getY()).isOuro()) {
            return 6;
        } else {
            return 0;
        }
    }

    public void possivel(Posicao v, String tipo) {
        int x = v.getX();
        int y = v.getY();
        if ((x - 1) >= 0) {
            if (!this.mConhecimento.retornaValor(x - 1, y).isVisitado()) {
                if (tipo.contains("~")) {
                    if (!this.provWumpus.contains(new Posicao(x - 1, y))) {
                        this.provWumpus.add(new Posicao(x - 1, y));
                    }
                }
                if (tipo.contains("#")) {
                    if (!this.provPoco.contains(new Posicao(x - 1, y))) {
                        this.provPoco.add(new Posicao(x - 1, y));
                    }
                }
            }
        }
        if ((x + 1) < this.mConhecimento.getLinhas()) {
            if (!this.mConhecimento.retornaValor(x + 1, y).isVisitado()) {
                if (tipo.contains("~")) {
                    if (!this.provWumpus.contains(new Posicao(x + 1, y))) {
                        this.provWumpus.add(new Posicao(x + 1, y));
                    }
                }
                if (tipo.contains("#")) {
                    if (!this.provPoco.contains(new Posicao(x + 1, y))) {
                        this.provPoco.add(new Posicao(x + 1, y));
                    }
                }
            }
        }
        if ((y - 1) >= 0) {
            if (!this.mConhecimento.retornaValor(x, y - 1).isVisitado()) {
                if (tipo.contains("~")) {
                    if (!this.provWumpus.contains(new Posicao(x, y - 1))) {
                        this.provWumpus.add(new Posicao(x, y - 1));
                    }
                }
                if (tipo.contains("#")) {
                    if (!this.provPoco.contains(new Posicao(x, y - 1))) {
                        this.provPoco.add(new Posicao(x, y - 1));
                    }
                }
            }
        }
        if ((y
                + 1) < this.mConhecimento.getColunas()) {
            if (!this.mConhecimento.retornaValor(x, y + 1).isVisitado()) {
                if (tipo.contains("~")) {
                    if (!this.provWumpus.contains(new Posicao(x, y + 1))) {
                        this.provWumpus.add(new Posicao(x, y + 1));
                    }
                }
                if (tipo.contains("#")) {
                    if (!this.provPoco.contains(new Posicao(x, y + 1))) {
                        this.provPoco.add(new Posicao(x, y + 1));
                    }
                }
            }
        }
    }

    public void gerarPer(Posicao v, String tipo) {
        Percepcao sala;
        int x = v.getX();
        int y = v.getY();
        Estado node = new Estado(new Posicao(x, y));
        if ((x - 1) >= 0) {
            if (!this.mConhecimento.retornaValor(x - 1, y).isVisitado()) {
                sala = new Percepcao();
                if (tipo.isEmpty()) {
                    sala.setPoco(false);
                    sala.setWumpus(false);
                } else {
                    if (tipo.contains("~")) {
                        sala.setPoco(true);
                    }
                    if (tipo.contains("#")) {
                        sala.setWumpus(true);
                    }
                    if (tipo.contains("P")) {
                        sala.setBrisa(true);
                    }
                    if (tipo.contains("W")) {
                        sala.setFedor(true);
                    }
                }
                this.mConhecimento.adiciona(x - 1, y, sala);

                node.setAgente(new Posicao(x - 1, y));
                node.setRoom(sala);

                this.borda.add(node);
            }
        }
        if ((x + 1) < this.mConhecimento.getLinhas()) {
            if (!this.mConhecimento.retornaValor(x + 1, y).isVisitado()) {
                sala = new Percepcao();
                if (tipo.isEmpty()) {
                    sala.setPoco(false);
                    sala.setWumpus(false);
                } else {
                    if (tipo.contains("~")) {
                        sala.setPoco(true);
                    }
                    if (tipo.contains("#")) {
                        sala.setWumpus(true);
                    }
                    if (tipo.contains("P")) {
                        sala.setBrisa(true);
                    }
                    if (tipo.contains("W")) {
                        sala.setFedor(true);
                    }
                }
                this.mConhecimento.adiciona(x + 1, y, sala);

                node.setAgente(new Posicao(x + 1, y));
                node.setRoom(sala);

                this.borda.add(node);
            }
        }
        if ((y - 1) >= 0) {
            if (!this.mConhecimento.retornaValor(x, y - 1).isVisitado()) {
                sala = new Percepcao();
                if (tipo.isEmpty()) {
                    sala.setPoco(false);
                    sala.setWumpus(false);
                } else {
                    if (tipo.contains("~")) {
                        sala.setPoco(true);
                    }
                    if (tipo.contains("#")) {
                        sala.setWumpus(true);
                    }
                    if (tipo.contains("P")) {
                        sala.setBrisa(true);
                    }
                    if (tipo.contains("W")) {
                        sala.setFedor(true);
                    }
                }
                this.mConhecimento.adiciona(x, y - 1, sala);

                node.setAgente(new Posicao(x, y - 1));
                node.setRoom(sala);

                this.borda.add(node);
            }
        }
        if ((y + 1) < this.mConhecimento.getColunas()) {
            if (!this.mConhecimento.retornaValor(x, y + 1).isVisitado()) {
                sala = new Percepcao();
                if (tipo.isEmpty()) {
                    sala.setPoco(false);
                    sala.setWumpus(false);
                } else {
                    if (tipo.contains("~")) {
                        sala.setPoco(true);
                    }
                    if (tipo.contains("#")) {
                        sala.setWumpus(true);
                    }
                    if (tipo.contains("P")) {
                        sala.setBrisa(true);
                    }
                    if (tipo.contains("W")) {
                        sala.setFedor(true);
                    }
                }
                this.mConhecimento.adiciona(x, y + 1, sala);

                node.setAgente(new Posicao(x, y + 1));
                node.setRoom(sala);

                this.borda.add(node);
            }
        }
    }

    public void gerar(Estado node) {
        if (node.getRoom().isBrisa()) {
            sucessores(node, "~");
        }
        if (node.getRoom().isFedor()) {
            sucessores(node, "#");
        }
    }

    //  Atualizar matriz de conhecimento
    //  Busca sucessores
    public void sucessores(Estado node, String tipo) {
        int x = node.getAgente().getX();
        int y = node.getAgente().getY();
        Estado novo;
        if ((x - 1) >= 0) {
            novo = new Estado(new Posicao(x - 1, y));
            if (tipo.contains("~")) {

            }
            if (tipo.contains("#")) {

            }
            this.borda.add(novo);
            /*
             if ((int) this.mConhecimento.retornaValor(x - 1, y) == 1) {
             novo = new Estado(new Posicao(x - 1, y));
             this.calcula(novo, node);
             this.borda.add(novo);
             }
             */
        }
        if ((x + 1) < this.mConhecimento.getLinhas()) {
            novo = new Estado(new Posicao(x + 1, y));
            if (tipo.contains("~")) {

            }
            if (tipo.contains("#")) {

            }
            this.borda.add(novo);

        }
        if ((y - 1) >= 0) {
            novo = new Estado(new Posicao(x, y - 1));
            if (tipo.contains("~")) {

            }
            if (tipo.contains("#")) {

            }
            this.borda.add(novo);

        }
        if ((y + 1) < this.mConhecimento.getColunas()) {
            novo = new Estado(new Posicao(x, y + 1));
            if (tipo.contains("~")) {

            }
            if (tipo.contains("#")) {

            }
            this.borda.add(novo);
        }
    }

    /*
     //  Realiza todos os calculos necessário e relaciona o indice do Estado pai
     public void calcula(Estado novo, Estado node) {
     novo.setCusto(node.getCusto() + 1);
     novo.setHeurist(this.Manhattan(novo));
     novo.setAvaliacao();
     novo.setPai(this.caminho.indexOf(node));
     }

     //  Acha o caminho a partir do indice do pai
     public void achaCaminho() {
     List<Estado> caminhoCerto = new ArrayList<>();
     Estado node = this.caminho.get(this.caminho.size() - 1);
     caminhoCerto.add(node);

     while (node.getPai() != -1) {
     node = this.caminho.get(node.getPai());
     caminhoCerto.add(node);
     }

     Collections.reverse(caminhoCerto);

     this.setCaminho(caminhoCerto);
     }

     //  Retorna o caminho encontrado
     public String retornaCaminho() {
     StringBuilder s = new StringBuilder();
     Estado node;

     for (int j = 0; j <= this.caminho.size() - 1; j++) {
     node = this.caminho.get(j);
     s.append(" -> ").append(node.getAgente());
     }

     //  System.out.println(s.toString());
     return s.toString();
     }
     */
}
