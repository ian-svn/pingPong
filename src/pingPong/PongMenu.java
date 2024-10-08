package pingPong;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import java.awt.FontFormatException;

public class PongMenu extends JFrame {

    private CustomPanel contentPane;
    private Font retroFont;
    private CustomButton btnEasyMode;
    private CustomButton btnNormalMode;
    private CustomButton btnHardMode;
    private CustomButton btnExit;
    private Boolean visible=true;
    private Juego juegoD;
    private Juego juegoF;
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PongMenu frame = new PongMenu();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public PongMenu() {

        juegoF = new Juego(this, "Facil");
        juegoD = new Juego(this, "Dificil");
        
        setTitle("PONG");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 850, 570);
        setResizable(false);
        setLocationRelativeTo(null);
        
        contentPane = new CustomPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        try {
            InputStream is = getClass().getResourceAsStream("/recursos/fuentes/Press_Start_2P/PressStart2P-Regular.ttf");
            if (is == null) {
                throw new IOException("Fuente no encontrada");
            }
            retroFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(24f); 
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            retroFont = new Font("Monospaced", Font.PLAIN, 24); 
        }


        btnNormalMode = new CustomButton("Empezar Juego", retroFont);
        btnNormalMode.setBounds(232,280, 380, 60);
        btnNormalMode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { 
                juegoD.correr();
                juegoD.iniciar();
                setVisible(false);
                dispose();
                visible=false;
            }
        });
        contentPane.add(btnNormalMode);


        btnHardMode = new CustomButton("Cerrar Juego", retroFont);
        btnHardMode.setBounds(245, 380, 350, 60);
        btnHardMode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.exit(0);
            }
        });
        contentPane.add(btnHardMode);
    }

    class CustomButton extends JButton {
        private Font font;

        public CustomButton(String text, Font font) {
            super(text);
            setFont(font);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setOpaque(false);
            setForeground(Color.WHITE);
        	setBorder(new LineBorder(new Color(0, 0, 0, 0), 4));
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                	setBorder(new LineBorder(Color.WHITE,4));
                	setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                	setBorderPainted(true);
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                	setBorder(new LineBorder(new Color(0, 0, 0, 0), 4)); 
                	setBorderPainted(true);
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
        	
        	if(visible) {
        	    super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                drawTextWithOutline(g2d, getText(), getWidth()/12,getHeight()-getHeight()/3);
        	}
        }

        private void drawTextWithOutline(Graphics2D g, String text, int x, int y) {
            g.setFont(font);
            g.setColor(Color.BLACK); 
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    g.drawString(text, x + i, y + j);
                }
            }
            g.setColor(Color.WHITE);  
            g.drawString(text, x, y);
        }
    }

    class CustomPanel extends JPanel {

        private ArrayList<Particle> particles;
        private Random random;
        private Font titleFont;
        private BufferedImage backgroundImage; 

        public CustomPanel() {
            setBackground(Color.WHITE);  
            particles = new ArrayList<>();
            random = new Random();


            try {
                InputStream is = getClass().getResourceAsStream("/recursos/imagenes/fondomenu1.png");
                if (is == null) {
                    throw new IOException("Imagen de fondo no encontrada");
                }
                backgroundImage = ImageIO.read(is);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                InputStream is = getClass().getResourceAsStream("/recursos/fuentes/Press_Start_2P/PressStart2P-Regular.ttf");
                if (is == null) {
                    throw new IOException("Fuente no encontrada");
                }
                titleFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(64f);
            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
                titleFont = new Font("Monospaced", Font.PLAIN, 64);
            }

            for (int i = 0; i < 100; i++) {
                particles.add(new Particle(random.nextInt(800), random.nextInt(520)));
            }

            new Thread(() -> {
                while (true) {
                    for (Particle p : particles) {
                        p.update();
                    }
                    repaint();
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        @Override
        protected void paintComponent(Graphics g) {
        	if(visible) {
	            super.paintComponent(g);
	            Graphics2D g2d = (Graphics2D) g;
	            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	
	            if (backgroundImage != null) {
	                g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
	            }
	
	            for (Particle p : particles) {
	                p.draw(g2d);
	            }
	
	            String title = "PONG";
	            int titleWidth = g2d.getFontMetrics(titleFont).stringWidth(title);
	            int titleX = (getWidth() - titleWidth) / 2;
	            drawGlowEffect(g2d, title, titleX, 100); 
	            g2d.setColor(Color.WHITE);
	            g2d.setFont(titleFont);
	            g2d.drawString(title, titleX, 100);
        	}
        }

        private void drawGlowEffect(Graphics2D g, String text, int x, int y) {
            g.setFont(titleFont);

            for (int i = 10; i > 0; i--) {
                g.setColor(new Color(0,0,0,25)); 
                g.drawString(text, x - i, y - i);
                g.drawString(text, x + i, y + i);
                g.drawString(text, x - i, y + i);
                g.drawString(text, x + i, y - i);
            }
        }
    }

    class Particle {

        private int x, y;
        private int speed;
        private BufferedImage image;

        public Particle(int x, int y) {
            this.x = x;
            this.y = y;
            this.speed = 2 + new Random().nextInt(3);

            try {
                InputStream is = getClass().getResourceAsStream("/recursos/imagenes/pixel2.png");
                if (is == null) {
                    throw new IOException("Imagen no encontrada");
                }
                image = ImageIO.read(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void update() {
            y += speed;
            if (y > 520) {
                y = 0;
                x = new Random().nextInt(800);
            }
        }

        public void draw(Graphics g) {
            if (image != null) {
                g.drawImage(image, x, y, null);
            }
        }
    }
    
    void correr() {
    	setVisible(true);
    }
    
    public void setVisib(Boolean visible) {
    	this.visible = visible;
    }
}
