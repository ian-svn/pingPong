package pingPong;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Juego extends JPanel {

    Barra barraIzq = new Barra("izq");
    Barra barraDer = new Barra("der");
    Pelota pelota = new Pelota();
    public int puntosJugador1;
    public int puntosJugador2;

    public Juego() {
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                barraIzq.keyPressed(e);
                barraDer.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        setFocusable(true);
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        ImageIcon cancha = new ImageIcon(getClass().getResource("/imagenes/fondo.png"));
        g.drawImage(cancha.getImage(), 0, 0, getWidth(), getHeight(), this);

        Font puntaje = new Font("Arial",Font.BOLD,25);
        g.setFont(puntaje);
        g.setColor(getBackground().WHITE);
        g.drawString("Puntaje: " + pelota.getPuntos1() + " a " + pelota.getPuntos2(), 300, 464 );
        
        barraIzq.paint(g);
        barraDer.paint(g);
        pelota.paint(g);
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Ping Pong");
        Juego game = new Juego();
        frame.add(game);
        frame.setSize(800, 520);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        // Image icono = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/pelota.ico"));        no me dejo
        // frame.setIconImage(icono);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        
        while (true) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                System.out.println(ex.toString());
            }
            game.repaint();
        }
    }
    
    
}
