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

public class Juego extends JPanel implements ActionListener {
    private JFrame frames = new JFrame();
    Barra barraIzq = new Barra("izq");
    Barra barraDer = new Barra("der");
    Pelota pelota;
    private Timer timer;
    private static int timeLeft = 120,timeLeftAux=timeLeft;
    private boolean enPausa = false;
    private static final int PAUSA_TIEMPO = 3; 
    private static Font retroFont;
    private static Font retroFontTitle;
    private static Font retro;
    private boolean tiempoFuera=false;
    private int inicioTemp=3, inicioTexmpAux=inicioTemp;
    private boolean inicio=true; int aux=0;
    private boolean invertido=false;
    private static PongMenu pongMenu;
    private String modo;
    static JFrame frame = new JFrame("Ping Pong");
    static Juego game;
    
    
    public Juego(PongMenu pongMenu, String modo) {
    	this.pongMenu = pongMenu;
    	this.modo = modo;
    	this.pelota = new Pelota(barraIzq,barraDer,modo); 
    	
    	game = this;
    	
    	inicializarFuentes();
    	
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
        temporizador.stop();
        timer.stop();
    }

	private static String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }
    
    Timer temporizador = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        	if(inicio&&frame.isVisible()) {
        		inicioTemp--;
        		repaint();
        		if(inicioTemp<=0) {
        			timer.start();
        			inicio=false;
        		}
        		return;
        	}
            if (!enPausa&&frame.isVisible()) {
                timeLeft--;
                //System.out.println("enPausa: " + enPausa + " inicio: " + inicio + " ");
                if (timeLeft <= 0) {
                    ((Timer) e.getSource()).stop();
                    tiempoFuera=true;
                    enPausa=true;
                }
            }
            repaint();
        }
    });
    
    private void handleKeyDuringPause(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
            pelota.setPuntos1(0);
            pelota.setPuntos2(0);
            enPausa = false;
            tiempoFuera=false;
            timeLeft=timeLeftAux;
        	inicioTemp=inicioTexmpAux;
            temporizador.start();
            timer.stop();
            inicio=true;
			pelota.reset();
			barraIzq.resetAll("izq");
			barraDer.resetAll("der");
			auxx=0;
			invertido=false;
            repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
        	pongMenu.correr();
        	pongMenu.setVisible(true);
        	pongMenu.setVisib(true);
            frame.setVisible(false);
            frame.dispose();
            pelota.setPuntos1(0);
            pelota.setPuntos2(0);
            enPausa = true;
            tiempoFuera=false; 
            timeLeft=timeLeftAux;
            inicio=true;
            timer.stop();
            temporizador.stop();
            inicioTemp=inicioTexmpAux;
			pelota.reset();
			invertido=false;
			barraIzq.resetAll("izq");
			barraDer.resetAll("der");
			auxx=0;
			aux=0;
        }
    }
    
    private int auxx=0;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        //System.out.println("frame.visible" + frame.isVisible());
        
        if(timeLeft<=timeLeftAux/2&&auxx==0) {
        	auxx=1;
        	cambioLado();
        	timer.stop();
        }
    	//System.out.println("invertido: " + invertido);
        
    	if (!enPausa) {
            if (timeLeft <= 0) {
                tiempoFuera=true;
                enPausa=true;
            }
        }
    	
        
        ImageIcon cancha = new ImageIcon(getClass().getResource("/imagenes/fondo.png"));
        g.drawImage(cancha.getImage(), 0, 0, getWidth(), getHeight(), this);
        
        int mayor = (pelota.getPuntos2() >= pelota.getPuntos1()) ? pelota.getPuntos2() : pelota.getPuntos1();
        int menor = (pelota.getPuntos2() >= pelota.getPuntos1()) ? pelota.getPuntos1() : pelota.getPuntos2();
        
        if ((pelota.getPuntos1() >= 7 || pelota.getPuntos2() >= 7)&&(mayor-2>=menor)) {

            Font win = retroFont;
            g.setFont(win);
            String ganador;
            ganador	= (pelota.getPuntos1()>pelota.getPuntos2()) ? "Azul" : "Rojo";
            if(invertido) {
            	ganador = (ganador=="Azul") ? "Rojo" : "Azul";
            }
            drawTextWithOutline(g, "¡El ganador es el jugador " + ganador + "! ", 40, 150, Color.BLACK, Color.WHITE);

            g.setFont(retro);
            drawTextWithOutline(g, "Presione la tecla 'R' para volver a jugar", 80, 396, Color.BLACK, Color.WHITE);
            drawTextWithOutline(g, "Presione la tecla 'ESC' para volver al menú", 75, 430, Color.BLACK, Color.WHITE);

            enPausa = true;
            if (!enPausa) {
                pelota.setPuntos1(0);
                pelota.setPuntos2(0);

                temporizador.start();
            }
            return;
        } else if(tiempoFuera) {
        	Font win = retroFont;
            g.setFont(win);
            String ganador;
            
        	ganador	= (pelota.getPuntos1()>pelota.getPuntos2()) ? "Azul" : "Rojo";
        	
            if(invertido) {
            	ganador = (ganador=="Azul") ? "Rojo" : "Azul";
            }
            if(pelota.getPuntos1()!=pelota.getPuntos2()) {
            drawTextWithOutline(g, "¡El ganador es el jugador " + ganador + "! ", 40, 150, Color.BLACK, Color.WHITE);
            }
            if(pelota.getPuntos1()==pelota.getPuntos2()) {
            	drawTextWithOutline(g, "El resultado fue un empate..." , 75, 150, Color.BLACK, Color.WHITE);
            }

            g.setFont(retro);
            drawTextWithOutline(g, "Presione la tecla 'R' para volver a jugar", 80, 396, Color.BLACK, Color.WHITE);
            drawTextWithOutline(g, "Presione la tecla 'ESC' para volver al menú", 75, 430, Color.BLACK, Color.WHITE);

            enPausa = true;
            if (!enPausa) {
                timeLeft=timeLeftAux;
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
        
        if(inicio) {
        	g.setColor(Color.YELLOW);
        	g.drawString("" + inicioTemp, 360, 250);
        	g.setColor(Color.WHITE);
        }
        
        if(pelota.getPausa()) {
        	pelota.despausar();
        	inicioTemp=inicioTexmpAux;
        	inicio=true;
        	pelota.reset();
        	barraIzq.reset();
        	barraDer.reset();
        	timer.stop();
        	//System.out.println("hola   ");
        }
        
        barraIzq.paint(g);
        barraDer.paint(g);
        pelota.paint(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	if(frame.isVisible()) {
	        if (!enPausa) {
	            barraIzq.mover(); 
	            barraDer.mover();
	            pelota.mover(); 
	        }
	
	        if(frame.isVisible()&&!temporizador.isRunning()) {
	        	temporizador.start();
	        }
	        
	        repaint();
    	}
        //if(!frame.isVisible()){
        	//enPausa=true;
        	//inicio=true;
        	//temporizador.stop();
        	//timer.stop();
        //}
    }


    private void drawTextWithOutline(Graphics g, String text, int x, int y, Color outlineColor, Color textColor) {
       
        Color originalColor = g.getColor();


        g.setColor(outlineColor);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    g.drawString(text, x + i, y + j);
                }
            }
        }


        g.setColor(textColor);
        g.drawString(text, x, y);


        g.setColor(originalColor);
    }
    
    int auxxxx=0;
    
    public void correr() {
        frame.setVisible(true);
        
        if(auxxxx==0) {
	        frame.setBackground(new Color(34,177,76));
	        frame.add(game);
	        frame.setSize(850, 570);
	        frame.setVisible(true);
	        frame.setResizable(false);
	        frame.setLocationRelativeTo(null);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        auxxxx=1;
        }
        

    	if(!temporizador.isRunning()) {
    		temporizador.start();
    	}
    }
    
    public void inicializarFuentes() {
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
    }
    
    public void iniciar() {
    	inicio=true;
    	temporizador.start();
    	auxx=0;
    }
    
    public void cambioLado() {
    	barraIzq.setLado("der");
    	barraDer.setLado("izq");
    	barraIzq.reset();
    	barraDer.reset();
    	pelota.reset();
    	pelota.invertirPuntos();
    	invertido=true;
    	inicio=true;
    	inicioTemp=inicioTexmpAux;
    	timer.stop();
    }
}
