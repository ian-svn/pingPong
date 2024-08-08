package pingPong;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.ImageIcon;

public class Barra {
    public int x = 50;
    public int y = 10;
    public String lado;

    public Barra(String lado) {
        if (lado.equals("izq")) {
            x = 20;
        } else if (lado.equals("der")) {
            x = 780 - 20;
        }
        this.lado = lado;
    }

    public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
    	    	if(lado.equals("izq")){
	                if (y > 30) {
	                    y -= 20;
	                }
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
    	    	if(lado.equals("izq")){
	                if (y < 400) { 
	                    y += 20;
	                }
    	    	}
            }
    		if (e.getKeyCode() == KeyEvent.VK_W) {
    	    	if(lado.equals("der")){
	                if (y > 30) {
	                    y -= 20;
	                }
    	    	}
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
            	if(lado.equals("der")){
	                if (y < 400) { 
	                    y += 20;
	                }
            	}
            }
    	}

    public void paint(Graphics g) {
        ImageIcon barra = new ImageIcon(getClass().getResource("/imagenes/barra.png"));
        g.drawImage(barra.getImage(), x, y, 10, 50, null);
    }

    public Ellipse2D getBoundsBarra() {
        return new Ellipse2D.Double(x, y + 10, 10, 50);
    }
}
