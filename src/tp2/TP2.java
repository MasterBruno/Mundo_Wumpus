/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp2;

import Tela.View;
import wumpus.Busca;
import wumpus.World1;

/**
 *
 * @author INSS-BENEF
 */
public class TP2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        /*
         World1 wumpus = new World1(4, 3, 1, 2);

         System.out.println(wumpus.getMatriz());

         /*
         TP2.percorrer(wumpus);

         System.out.println(wumpus.getMatriz());
         
         World2 mundo = new World2(4, 6, 2, 1);
         System.out.println(mundo.getMatriz());

         View tela = new View(mundo.getMatriz());
         tela.iniciar(mundo.getMatriz());
         */

        Busca busca = new Busca(4, 6, 3, 1);
        System.out.println(busca.getMatriz());
    
        View tela = new View(busca.getMatriz());
        tela.iniciar(busca.getMatriz());
    }

    public static void percorrer(World1 mundo) {
        int n = 0, m = 0;
        mundo.getMatriz().adiciona(n, m, "A");

    }

    public static void view(World1 mundo) {

    }
}
