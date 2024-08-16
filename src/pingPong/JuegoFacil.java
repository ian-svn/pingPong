package pingPong;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class JuegoFacil extends JPanel implements ActionListener {
    private JFrame frames = new JFrame();
    Barra barraIzq = new Barra("izq");
    Barra barraDer = new Barra("der");
    Pelotita pelota;
    private Timer timer;
    private static int timeLeft = 300;
    private Timer pausaTimer;
    private boolean enPausa = false;
    private static final int PAUSA_TIEMPO = 3000; 
    private static Font retroFont;
    private static Font retroFontTitle;
    private static Font retro;
    private Boolean tiempoFuera=false;

    static JFrame frame = new JFrame("Ping Pong");
    static JuegoFacil game = new JuegoFacil();
    public JuegoFacil() {
        // Cargar la fuente aquí
        try {
            InputStream is = getClass().getResourceAsStream("/fuentes/PressStart2P-Regular.ttf");
            if (is == null) {
                throw new IOException("Fuente no encontrada");
            }
            retroFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(24f); 
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            retroFont = new Font("Monospaced", Font.PLAIN, 24); 
        }
        try {
            InputStream is = getClass().getResourceAsStream("/fuentes/PressStart2P-Regular.ttf");
            if (is == null) {
                throw new IOException("Fuente no encontrada");
            }
            retro = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(16f); 
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            retro = new Font("Monospaced", Font.PLAIN, 16); 
        }
        try {
            InputStream is = getClass().getResourceAsStream("/fuentes/PressStart2P-Regular.ttf");
            if (is == null) {
                throw new IOException("Fuente no encontrada");
            }
            retroFontTitle = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(35f); 
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            retroFontTitle = new Font("Monospaced", Font.PLAIN, 35); 
        }
        
        pelota = new Pelotita(barraIzq, barraDer);

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            	
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (enPausa) {
                    handleKeyDuringPause(e);
                } else {
                    barraIzq.keyPressed(e);
                    barraDer.keyPressed(e);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                barraIzq.keyReleased(e);
                barraDer.keyReleased(e);
            }
        });
        setFocusable(true);
        
        timer = new Timer(1, this);
        timer.start();
        
        temporizador.start();
    }

    private static String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }
    
    Timer temporizador = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!enPausa) {
                timeLeft--;
                if (timeLeft <= 0) {
                    ((Timer) e.getSource()).stop();
                    tiempoFuera=true;
                }
            }
            repaint();
        }
    });
    
    private void handleKeyDuringPause(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
            pelota.setPuntos1(0);
            pelota.setPuntos2(0);
            enPausa = false; // Reanuda el juego
            repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            PongMenu pong = new PongMenu();
            pong.correr();
            frame.setVisible(false);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        ImageIcon cancha = new ImageIcon(getClass().getResource("/imagenes/fondo.png"));
        g.drawImage(cancha.getImage(), 0, 0, getWidth(), getHeight(), this);
        
        int mayor = (pelota.getPuntos2() >= pelota.getPuntos1()) ? pelota.getPuntos2() : pelota.getPuntos1();
        int menor = (pelota.getPuntos2() >= pelota.getPuntos1()) ? pelota.getPuntos1() : pelota.getPuntos2();
        
        if ((pelota.getPuntos1() >= 7 || pelota.getPuntos2() >= 7)&&(mayor-2>=menor)) {

            Font win = retroFont;
            g.setFont(win);

            String ganador = (pelota.getPuntos1()>pelota.getPuntos2()) ? "Azul" : "Rojo";

            drawTextWithOutline(g, "¡El ganador es el jugador " + ganador + "! ", 40, 150, Color.BLACK, Color.WHITE);

            g.setFont(retro);
            drawTextWithOutline(g, "Presione la tecla 'R' para volver a jugar", 80, 396, Color.BLACK, Color.WHITE);
            drawTextWithOutline(g, "Presione la tecla 'ESC' para volver al menú", 75, 430, Color.BLACK, Color.WHITE);
            enPausa = true;
            if (!enPausa) {
                pausaTimer = new Timer(PAUSA_TIEMPO, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        enPausa = false;
                        pausaTimer.stop();
                    }
                });
                pausaTimer.setRepeats(false);
                pausaTimer.start();
                pelota.setPuntos1(0);
                pelota.setPuntos2(0);

                temporizador.start();
            }
            return;
        } else if(tiempoFuera) {
        	Font win = retroFont;
            g.setFont(win);
            
            if(pelota.getPuntos1()>pelota.getPuntos2()) {
                drawTextWithOutline(g, "¡El ganador es el Jugador Azul!" , 75, 150, Color.BLACK, Color.WHITE);
            } else if(pelota.getPuntos2()>pelota.getPuntos1()) {
                drawTextWithOutline(g, "¡El ganador es el Jugador Rojo!" , 75, 150, Color.BLACK, Color.WHITE);
            } else if(pelota.getPuntos1()==pelota.getPuntos2()) {
                drawTextWithOutline(g, "El resultado fue un empate..." , 75, 150, Color.BLACK, Color.WHITE);
            }

            g.setFont(retro);
            drawTextWithOutline(g, "Presione la tecla 'R' para volver a jugar", 80, 396, Color.BLACK, Color.WHITE);
            drawTextWithOutline(g, "Presione la tecla 'ESC' para volver al menú", 75, 430, Color.BLACK, Color.WHITE);

            enPausa = true;
            if (!enPausa) {
                pausaTimer = new Timer(PAUSA_TIEMPO, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        enPausa = false;
                        pausaTimer.stop();
                    }
                });
                pausaTimer.setRepeats(false);
                pausaTimer.start();
                pelota.setPuntos1(0);
                pelota.setPuntos2(0);
            }
            return;
        }

        Font puntaje = retroFont;
        g.setFont(puntaje);
        g.setColor(Color.WHITE);
        g.drawString(pelota.getPuntos1() + " - " + pelota.getPuntos2(), 360, 32);

        g.drawString(formatTime(timeLeft), 360, 522);
        
        barraIzq.paint(g);
        barraDer.paint(g);
        pelota.paint(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!enPausa) {
            barraIzq.mover(); // Mueve la barra izquierda
            barraDer.mover(); // Mueve la barra derecha
            pelota.mover(); // Mueve la pelota
        }
        repaint();
    }

    // Método auxiliar para dibujar texto con contorno
    private void drawTextWithOutline(Graphics g, String text, int x, int y, Color outlineColor, Color textColor) {
        // Guarda el color original
        Color originalColor = g.getColor();

        // Dibuja el contorno
        g.setColor(outlineColor);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    g.drawString(text, x + i, y + j);
                }
            }
        }

        // Dibuja el texto principal
        g.setColor(textColor);
        g.drawString(text, x, y);

        // Restaura el color original
        g.setColor(originalColor);
    }

    public static void main(String[] args) {
        frame.setBackground(new Color(34,177,76));
        frame.add(game);
        frame.setSize(850, 570);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void correr() {
        frame.setBackground(new Color(34,177,76));
        frame.add(game);
        frame.setSize(850, 570);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
