import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;


public class Stalker extends Enemy {

	private static int EYE = 8;
	private static int SIZE = 50;
	
	public Stalker(int x, int y) {
		super(x, y);
		xPos = x;
		yPos = y;
		xSize = SIZE;
		ySize = SIZE;
		overworldCollision = new Rectangle(xPos-1, yPos-1, SIZE+2, SIZE+2);
	}
	
	public void drawEnemy(Graphics g){
		//Body
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(xPos, yPos, SIZE, SIZE);
		
		//Scar
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3));
		g.setColor(new Color(102, 51, 0));
		g.drawLine(xPos+30, yPos+10, xPos+39, yPos+27);
		
		//Eyes
		g2.setStroke(new BasicStroke(2));
		g.setColor(Color.CYAN);
		g.fillOval(xPos+10, yPos+15, EYE, EYE);
		g.setColor(Color.ORANGE);
		g.fillOval(xPos+30, yPos+15, EYE, EYE);
		g.setColor(Color.BLACK);
		g.drawOval(xPos+10, yPos+15, EYE, EYE);
		g.drawOval(xPos+30, yPos+15, EYE, EYE);
		
		//Eyebrows
		g.setColor(Color.BLACK);
		g.drawLine(xPos+10, yPos+10, xPos+20, yPos+20);
		g.drawLine(xPos+28, yPos+20, xPos+40, yPos+10);
		g2.setStroke(new BasicStroke(1));
		
	}

}
