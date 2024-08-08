package pingPong;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Juego extends JPanel{
	
	Barra barraIzq = new Barra("Izq");
	Barra barraDer = new Barra("Der");
	Pelota Pelota = new Pelota();
	
	public Juego() {
		addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {
				
			}	
			@Override
			public void keyPressed(KeyEvent e) {
				barraIzq.KeyPressed(e);
				barraDer.KeyPressed(e);
			}
			@Override
			public void keyReleased(KeyEvent e) {
				
			}
		});
		setFocusable(true);
	}
	
	public void paint(Graphics g) {
		super.paint(g);

		ImageIcon cancha= new ImageIcon(getClass().getResource("..imagenes/fondo.png"));
		g.drawImage(cancha.getImage(),0,0,getWidth(),getHeight(),this);
		
		barraIzq.paint(g);
		barraDer.paint(g);
		
		g.dispose();
	}
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Running Pong");
		Juego game = new Juego();
		frame.add(game);
		frame.setSize(800,520);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		while(true) {
			try {
				Thread.sleep(10);
			} catch(InterruptedException ex){
				System.out.println(ex.toString());
			}
			
			game.repaint();
		}
	}
	
}
