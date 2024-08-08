package pingPong;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.ImageIcon;

public class Pelota {
	public static int x = 20;
	public static int y = 400;
	public String lado;

		
	
	public void paint(Graphics g) {
        ImageIcon barra = new ImageIcon(getClass().getResource("../imagenes/barra.png"));
        g.drawImage(barra.getImage(), x, y, 100, 100, null);
    }
	
	public Ellipse2D getBoundsBarra() {
		return new Ellipse2D.Double(x,y+30,80,50);
	}
}
