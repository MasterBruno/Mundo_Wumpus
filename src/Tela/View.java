package Tela;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import wumpus.Busca;
import wumpus.Matriz;
import wumpus.Posicao;
import wumpus.Percepcao;

/**
 *
 * @author bruno
 */
public class View extends JFrame {

    /**
     * Conventions:
     *
     * maze[row][col]
     *
     * Values: 0 = not-visited node 1 = wall
     *
     * borders must be filled with "1" to void ArrayIndexOutOfBounds exception.
     */
    private Matriz<Percepcao> maze;
    private Posicao agente;

    private final List<Integer> path = new ArrayList<>();
    private int pathIndex;

    public View() {
        setTitle("Labirinto");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public View(Matriz<Percepcao> matriz) {
        this();
        this.maze = matriz;
        this.agente = new Posicao(0, 0);
    }

    /*
     public View(Busca busca) {
     setTitle("Simple Maze Solver");
     setSize(640, 480);
     setLocationRelativeTo(null);
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     this.maze = busca.getMatriz();
     this.agente = busca.getInicio();
     percorrer(busca);
     }
     */
    public void setMaze(Matriz maze) {
        this.maze = maze;
    }

    public void setAgente(Posicao agente) {
        this.agente = agente;
    }

    public void setPathIndex(int pathIndex) {
        this.pathIndex = pathIndex;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.translate(50, 50);

        // draw the maze
        for (int row = 0; row < maze.getLinhas(); row++) {
            for (int col = 0; col < maze.getColunas(); col++) {
                Color color;
                if (this.maze.retornaValor(row, col).isOuro()) {
                    color = Color.YELLOW;
                } else if (this.maze.retornaValor(row, col).isPoco()) {
                    color = Color.BLACK;
                } else if (this.maze.retornaValor(row, col).isWumpus()) {
                    color = Color.GREEN;
                } else if (this.maze.retornaValor(row, col).isBrisa() && this.maze.retornaValor(row, col).isFedor()) {
                    color = Color.RED;
                } else if (this.maze.retornaValor(row, col).isBrisa()) {
                    color = Color.GRAY;
                } else if (this.maze.retornaValor(row, col).isFedor()) {
                    color = Color.ORANGE;
                } else {
                    color = Color.WHITE;
                }
                g.setColor(color);
                g.fillRect(30 * col, 30 * row, 30, 30);
                g.setColor(Color.BLACK);
                g.drawRect(30 * col, 30 * row, 30, 30);
            }
        }
        //  Pintando o caminho
        g.setColor(Color.RED);
        g.fillOval(30 * this.agente.getY(), 30 * this.agente.getX(), 30, 30);
    }

    public void percorrer(Busca busca) {
        Posicao atual;
        for (int i = 0; i < busca.getCaminho().size(); i++) {
            atual = busca.getCaminho().get(i).getAgente();
            this.setAgente(atual);
            revalidate();
            repaint();
            setVisible(true);

            try {
                Thread.sleep(500L);
            } catch (InterruptedException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (i == busca.getCaminho().size() - 1) {
                JOptionPane.showMessageDialog(rootPane, "Objetivo concluido");
                this.setVisible(false);
                return;
            }
        }
    }

    public void iniciar(Matriz busca) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                View view = new View(busca);
                view.setVisible(true);
                //  percorrer(busca);
                /*
                 for (int i = 0; i < busca.getCaminho().size(); i++) {
                 atual = busca.getCaminho().get(i).getAgente();
                 view.setAgente(atual);
                 repaint();
                 try {
                 Thread.sleep(50);
                 } catch (InterruptedException ex) {
                 Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 }
                 */
            }
        });
    }

    /*
     public void iniciar(Busca busca) {
     SwingUtilities.invokeLater(new Runnable() {
     @Override
     public void run() {
     View view = new View(busca);
     view.setVisible(true);
     percorrer(busca);
     /*
     for (int i = 0; i < busca.getCaminho().size(); i++) {
     atual = busca.getCaminho().get(i).getAgente();
     view.setAgente(atual);
     repaint();
     try {
     Thread.sleep(50);
     } catch (InterruptedException ex) {
     Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
     }
     }
     }
     });
     }
     */
}
