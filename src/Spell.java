import java.awt.*;


public class Spell {

	private float xPos;
	private float yPos;
	private int xSize;
	private int ySize;
	private Color color;
	private int targetID;
	private int xTarget;
	private int yTarget;
	private int damage = 0;
	private String effect = "None";
	private boolean particle = true;
	private static final int OUTLINE_SIZE = 2;
	private int velocity = 5;
	private float xVelocity;
	private float yVelocity;
	
	public Spell(int type, int xOrigin, int yOrigin, int xF, int yF, int ID){
		spellType(type);
		xPos = xOrigin-xSize/2;
		yPos = yOrigin-ySize/2;
		xTarget = xF-xSize/2;
		yTarget = yF-ySize/2;
		targetID = ID;
		determineComponentVelocities();
	}
	
	public void spellType(int t){
		if (t == 0){
			color = new Color (102, 51, 0);
			damage = 30;
			xSize = 16;
			ySize = 16;
		}
	}
	
	public void determineComponentVelocities() {
		// Calculate how fast this spell will travel on the x and y axes.
		float xDistance = xTarget - xPos;
		float yDistance = yTarget - yPos;
		float totalDistance = Math.abs(xDistance) + Math.abs(yDistance);
		xVelocity = (xDistance / totalDistance) * velocity;
		yVelocity = (yDistance / totalDistance) * velocity;
	}
	
	public void move(){
		if (Math.abs(xTarget - xPos) < Math.abs(xVelocity) && 
			Math.abs(yTarget - yPos) < Math.abs(yVelocity)) {
			System.out.println("Hit Target!");
			xPos = xTarget;
			yPos = yTarget;
		} else {
			xPos += xVelocity;
			yPos += yVelocity;
		}
	}
	
	public boolean spellHit(){
		if (xPos == xTarget && yPos == yTarget)
			return true;
		else
			return false;
	}
	
	public int getTarget(){
		return targetID;
	}
	
	public int getDamage(){
		return damage;
	}
	
	public void drawSpell(Graphics g){
		// Draw Outline first
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(OUTLINE_SIZE));
		
		// Draw the interior of the spell
		g.setColor(color);
		g.fillOval((int) xPos, (int) yPos, xSize, ySize);
	}
}
