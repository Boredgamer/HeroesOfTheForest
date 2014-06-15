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
		overworldCollision = new Rectangle(xPos, yPos, SIZE, SIZE);
		maxhp = 10;
		hp = maxhp;
		exp = 500;
	}
	
	public void attack(int x, int y, int s){
		if (status != "Stun"){
				stamina += s;
			if (stamina >= 1000){
				int target = 0;
				spellsThrown.add(new Spell(0, Color.DARK_GRAY, centerX(), centerY(), x, y, target));
				stamina = 0;
			}
		}
		else{
			statusRemover++;
			if (statusRemover == 30)
				status = "None";
		}
		int loop;
		for (loop = 0; loop < spellsThrown.size(); loop++){
			spellsThrown.get(loop).move();
		}
	}
	
	public void drawEnemy(Graphics g){
		if (!dead){
			//Body
			g.setColor(Color.BLACK);
			g.fillRect(xPos, yPos, SIZE, SIZE);
			g.setColor(new Color(210, 210, 210));
			g.fillRect(xPos+2, yPos+2, SIZE-4, SIZE-4);
			
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
			
			if (status == "Stun"){
				g.setColor(Color.RED);
				g2.setStroke(new BasicStroke(4));
				g.drawLine(xPos, yPos-20, xPos+xSize, yPos-5);
				g.drawLine(xPos, yPos-5, xPos+xSize, yPos-20);
				
			}
		}
		
		else{
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(5));
			g.setColor(Color.BLACK);
			g.drawLine(xPos, yPos, xPos+SIZE, yPos+SIZE);
			g.drawLine(xPos, yPos+SIZE, xPos+SIZE, yPos);
		}
	}

}
