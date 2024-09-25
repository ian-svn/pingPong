package pingPong;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;

import java.awt.event.KeyEvent;

public class Barra {
    public int x = 60+50;
    public int y = 10+50;
    public String lado;


    private boolean wPressed = false;
    private boolean sPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;

    public Barra(String lado) {
        this.lado = lado;
    	if (lado.equals("izq")) {
            x = 75;
    		y=150;
            
        } else if (lado.equals("der")) {
            x = 750;
    		y=300;
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) wPressed = true;
        if (e.getKeyCode() == KeyEvent.VK_S) sPressed = true;
        if (e.getKeyCode() == KeyEvent.VK_UP) upPressed = true;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) downPressed = true;
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) wPressed = false;
        if (e.getKeyCode() == KeyEvent.VK_S) sPressed = false;
        if (e.getKeyCode() == KeyEvent.VK_UP) upPressed = false;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) downPressed = false;
    }

    public void mover() {
        int velocidad = 5; 

        if (wPressed && y > 60 && lado.equals("izq")) {
            y -= velocidad;
        }
        if (sPressed && y < 400 && lado.equals("izq")) {
            y += velocidad;
        }
        if (upPressed && y > 60 && lado.equals("der")) {
            y -= velocidad;
        }
        if (downPressed && y < 400 && lado.equals("der")) {
            y += velocidad;
        }
    }
    

	ImageIcon barra;

    public void paint(Graphics g) {
    	if(lado=="der") {
    		barra = new ImageIcon(getClass().getResource("/imagenes/barraDer.png"));
    	} else if(lado=="izq"){
    		barra = new ImageIcon(getClass().getResource("/imagenes/barra.png"));
    	} else {
    		barra = new ImageIcon(getClass().getResource("/imagenes/barra.png"));
    	}
    	g.drawImage(barra.getImage(), x, y, 13, 70, null);
    }

    public Rectangle2D getBoundsBarra() {
        return new Rectangle2D.Double(x, y, 13, 70);
    }
    
    public void resetAll(String lado) {
    	this.lado=lado;
    	if (lado.equals("izq")) {
            x = 75;
    		y=150;
            
        } else if (lado.equals("der")) {
            x = 750;
    		y=300;
        }
    }
    
    public void reset() {
    	if (lado.equals("izq")) {
    		y=150;
            
        } else if (lado.equals("der")) {
    		y=300;
        }
    }
    
    public void setLado(String lado) { 
    	this.lado = lado;
    }
    
    public String getLado() {
    	return lado;
    }
}
