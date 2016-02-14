package main;
import javax.swing.*;
import java.awt.*;

final public class Render {

    JFrame frame;
    DrawPanel drawPanel;
    int last = 0;

    private final int size = 30;
    private final int gap = 10;
    private float hue = (float) 0.3;
    private final double contrast = 0.25;
    private final double speed = 0.1;
    private final Color backgroundColor = new Color(242, 231, 201);
    private final double arch = 2;
    private final int framesize = 600;
    private final boolean rainbow = true;
    
    public static void main(String[] args) {
        new Render().go();
    }

    private void go() {
        frame = new JFrame("Frambozen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        drawPanel = new DrawPanel();

        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setSize(framesize, framesize);
        frame.setLocation(375, 55);
        Animate();
    }

    class DrawPanel extends JPanel {

    	/*
		* 	Gijs Mulder 2016
	   */

		public void paintComponent(Graphics g) {
        	while (true) {
        	int current = (int) ((System.currentTimeMillis()*speed)%(size+gap));
        	if (current == last) {
        		try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        	} else {
            	last = current;
            	break;
        	}
        	}
        	if (rainbow) {
        		hue+=0.001;
        		if (hue > 1) hue = 0;
        	}
        	g.setColor(backgroundColor);
        	g.fillPolygon(new int[]{0,0,frame.getWidth(), frame.getWidth()}, new int[]{0,frame.getHeight(),frame.getHeight(),0}, 4);
        	
        	Color c1 = Color.getHSBColor(hue, (float) 0.7, (float) 0.7);
        	Color c2 = brighten(c1, contrast);
        	
        	for (int x =0; x<frame.getHeight()+size*(1/arch); x+=size+gap) {
        		paintStrip(g, c1, c2, (int) (x+ last));
        	}
        	
            
        }
    }

    private void Animate() {
        while(true){
            frame.repaint();
        }
    }
    
    private void paintStrip(Graphics g, Color c, Color c2, int y) {
    	
    	for (int x=0;x<frame.getWidth();x+=(size*2)) {
    	g.setColor(c);
    	g.fillPolygon(new int[]{x,x,x+size, x+size}, new int[]{y,y-size,(int) (y-size-size/arch),(int) (y-size/arch)}, 4);
    	g.setColor(c2);
    	g.fillPolygon(new int[]{x+size,x+size,x+size*2, x+size*2}, new int[]{(int) (y-size/arch),(int) (y-size/arch-size),y-size,y}, 4);
    	}
    }
    
    public static Color brighten(Color color, double fraction) {

        int red = (int) Math.round(Math.min(255, color.getRed() + 255 * fraction));
        int green = (int) Math.round(Math.min(255, color.getGreen() + 255 * fraction));
        int blue = (int) Math.round(Math.min(255, color.getBlue() + 255 * fraction));

        return new Color(red, green, blue);

    }
}