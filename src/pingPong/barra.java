package pingPong;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.ImageIcon;

public class Barra {
	public static int x = 20;
	public static int y = 400;
	public String lado;
	
	
	public Barra(String lado) {
		if(lado.equals("izq")) {
			x=20;
		} if(lado.equals("der")) {
			x=780-20;
		}
	}
	
	public void KeyPressed(KeyEvent e) {
		if(e.getKeyCode()==38) {
			if(y>0){
				y-=20;
			}
		}
		if(e.getKeyCode()==40) {
			if(y<800) {
				y+=20;
			}
		}
	}
	
	public void paint(Graphics g) {
        ImageIcon barra = new ImageIcon(getClass().getResource("../imagenes/pelota.png"));
        g.drawImage(barra.getImage(), x, y, 100, 100, null);
    }
	
	public Ellipse2D getBoundsBarra() {
		return new Ellipse2D.Double(x,y+30,80,50);
	}
}
