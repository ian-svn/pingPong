package pingPong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;

public class Pelota {

    private int x = 285+50;
    private int y = 128+50;
    private final int ACHO = 25, ALTO = 25;
    private final int altoEs = 450, anchoEs = 758;
    private int velX = 5;
    private int velY = 5;
    public int puntaje1 = 0;
    public int puntaje2 = 0;
    private Barra barraIzq;
    private Barra barraDer;
    private Font retroFont;
    private boolean pausar=false;
    private String modo;
    private int rebotes=0;
    
    public Pelota(Barra barraIzq, Barra barraDer, String modo) {
        this.barraIzq = barraIzq;
        this.barraDer = barraDer;
        this.modo = modo;
    }

    public void paint(Graphics g) {
        ImageIcon pelota = new ImageIcon(getClass().getResource("/imagenes/pelota.png"));
        g.drawImage(pelota.getImage(), x, y, ACHO, ALTO, null);
    }

    public int getPuntos1() {
        return puntaje1;
    }

    public int getPuntos2() {
        return puntaje2;
    }
    
    public void setPuntos1(int puntaje1) {
        this.puntaje1 = puntaje1;
    }
    
    public void setPuntos2(int puntaje2) {
        this.puntaje2 = puntaje2;
    }

    public void reset() {
    	x=375;
    	y=200;
    	
    	
    	velX=5;
    	velY=5;
    	//rebotes=0;
    }
    
    public void mover() {
    	if(modo=="Dificil") {
	    		
	        x += velX;
	        y += velY;
	
	
	
	        if (y > altoEs || y < 50) {
	            velY = -velY;
	        }
	
	        Rectangle2D pelotaRect = new Rectangle2D.Double(x, y, ACHO, ALTO);
	
	        Rectangle2D barraIzqRect = barraIzq.getBoundsBarra();
	        if (pelotaRect.intersects(barraIzqRect)) {
	            if (velX < 0) {
	                x = (int) (barraIzqRect.getMaxX());
	            }
	            velX = -velX;
	
	            
	            double relativeIntersectY = (barraIzqRect.getY() + barraIzqRect.getHeight()) - (y + ALTO / 2);
	            double normalizedRelativeIntersectionY = (relativeIntersectY / (barraIzqRect.getHeight() / 2));
	
	            velY = (int) (-6 * normalizedRelativeIntersectionY);
	            

	            velX += (velX > 0) ? 1 : -1;
	            velY += (velY > 0) ? 1 : -1;
	            
	            //rebotes++;
	        }
	
	        Rectangle2D barraDerRect = barraDer.getBoundsBarra();
	        if (pelotaRect.intersects(barraDerRect)) {
	
	            if (velX > 0) {
	                x = (int) (barraDerRect.getMinX() - ACHO);
	            }
	            velX = -velX;
	
	            double relativeIntersectY = (barraDerRect.getY() + barraDerRect.getHeight()) - (y + ALTO / 2);
	            double normalizedRelativeIntersectionY = (relativeIntersectY / (barraDerRect.getHeight() / 2));
	
	            velY = (int) (-6 * normalizedRelativeIntersectionY);
	
	            velX += (velX > 0) ? 1 : -1;
	            velY += (velY > 0) ? 1 : -1;
	            //rebotes++;
	        }
            //System.out.println("Dificil");
	        if (x > anchoEs) {
	            x = 385;
	            y = 228;
	            puntaje1++;
	            pausar();
	            velX=-velX;
	            velY=-velY;
	            
	        }

	        if (x < 55) {
	            x = 385;
	            y = 288;
	            puntaje2++;
	            pausar();
	        }
    	}
    	/*
    	else if(modo=="Facil"){

            x += velX;
            y += velY;

            if (x > anchoEs) {
                x = 385;
                y = 228;
                velX = -velX;
                puntaje1++;
                pausar();
                
                velX = (velX > 0) ? 6 : -6;
                velY = (velY > 0) ? 6 : -6;
            }

            if (x < 55) {
                x = 385;
                y = 228;
                velX = -velX;
                puntaje2++;
                pausar();
                
                velX = (velX > 0) ? 6 : -6;
                velY = (velY > 0) ? 6 : -6;
            }

            if (y > altoEs || y < 50) {
                velY = -velY;
            }

            Rectangle2D pelotaRect = new Rectangle2D.Double(x, y, ACHO, ALTO);

            Rectangle2D barraIzqRect = barraIzq.getBoundsBarra();
            if (pelotaRect.intersects(barraIzqRect)) {
                if (velX < 0) {
                    x = (int) (barraIzqRect.getMaxX());
                }
                velX = -velX;

    		
    		if (pelotaRect.intersects(barraIzqRect)) {
    		    if (velX < 0) {
    		        x = (int) (barraIzqRect.getMaxX()); 
    		    }
    		    velX = -velX;
    		}
    		
    		Rectangle2D barraDerRect = barraDer.getBoundsBarra();
    		if (pelotaRect.intersects(barraDerRect)) {
    		
    		    if (velX > 0) {
    		        x = (int) (barraDerRect.getMinX() - ACHO); 
    		    }
    		    velX = -velX;
    		    
    		
    		    velX += (velX>0) ? 1 : -1;
    		    velY += (velY>0) ? 1 : -1;
    			}    
            }*/

    }
    
    
    public void pausar() {
    	pausar=true;
    }
    
    public void despausar() {
    	pausar=false;
    }
    
    public boolean getPausa() {
    	return pausar;
    }
    
    public void invertirPuntos() {
    	int aux=getPuntos2();
    	setPuntos2(getPuntos1());
    	setPuntos1(aux);
    }
}
