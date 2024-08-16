package pingPong;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import javax.swing.ImageIcon;

public class Puntaje {
	
	public Juego j;
	
	private int x = 385;
	private int y = 228;
	private final int altoEs=450, anchoEs=780-12;
	private int hasta=7;
	public int puntaje1=0;
	public int puntaje2=0;
	
	private String lado;

	public Puntaje(int hasta) {
		this.hasta = hasta;
    }
	
	public void paint(Graphics g) {
        ImageIcon puntaje = new ImageIcon(getClass().getResource("/imagenes/pelota.png"));
        g.drawImage(puntaje.getImage(), x, y, x, y, null);
    }
	public Ellipse2D getBoundsPelota() {
		return new Ellipse2D.Double(x,y+30,x,y);
	}
}
