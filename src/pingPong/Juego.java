package pingPong;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Juego extends JPanel {

    Barra barraIzq = new Barra("izq");
    Barra barraDer = new Barra("der");
    Pelota pelota = new Pelota();

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

        barraIzq.paint(g);
        barraDer.paint(g);
        pelota.paint(g);
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Running Pong");
        Juego game = new Juego();
        frame.add(game);
        frame.setSize(800, 520);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
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
