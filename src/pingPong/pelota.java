package pingPong;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import javax.swing.ImageIcon;

public class Pelota {
	
	public Juego j;
	
	private int x = 385;
	private int y = 228;
	private final int ACHO=20, ALTO=20;
	private final int altoEs=450, anchoEs=780-12;
	private int velX=1;
	private int velY=1;
	private int puntaje1=0;
	private int puntaje2=0;
	
	private String lado;

	public Pelota() {
    }
	
	public void paint(Graphics g) {
        ImageIcon pelota = new ImageIcon(getClass().getResource("/imagenes/pelota.png"));
        g.drawImage(pelota.getImage(), x, y, ACHO, ALTO, null);
        mover();
    }
	
	public boolean gol1() {
		puntaje1++;
		return true;
	}
	
	public boolean gol2() {
		puntaje2++;
		return true;
	}
	
	private void mover() {
		x+= velX;
		y+= velY;
		
		if(x > anchoEs) {
			velX = -velX;
			if(velX<0) {
				gol1();
			}
		}
		if(y > altoEs) {
			velY=-velY;
		}
		if(x < 0) {
			velX = -velX;
			gol2();
		}
		if(y < 0) {
			velY=-velY;
		}
			
	}
	
	public boolean choque1() {
		Area areaBarraI = new Area(j.barraIzq.getBoundsBarra());
		areaBarraI.intersect(getBoundsPelota());
		return !areaBarraI.isEmpty();
	}
	
	public Ellipse2D getBoundsPelota() {
		return new Ellipse2D.Double(x,y+30,ACHO,ALTO);
	}
}
