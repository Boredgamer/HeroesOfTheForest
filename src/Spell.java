import java.awt.*;


public class Spell {
	
	private static int BASIC = 0;

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
	
	public Spell(int type, Color c, int xOrigin, int yOrigin, int xF, int yF, int ID){
		spellType(type);
		color = c;
		xPos = xOrigin-xSize/2;
		yPos = yOrigin-ySize/2;
		xTarget = xF-xSize/2;
		yTarget = yF-ySize/2;
		targetID = ID;
		determineComponentVelocities();
	}
	
	public void spellType(int t){
		if (t == BASIC){
			damage = 3;
			xSize = 14;
			ySize = 14;
			particle = true;
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
	
	public String getEffect(){
		return effect;
	}
	
	public void drawSpell(Graphics g){
		if (particle){
			// Draw Outline first
			g.setColor(Color.BLACK);
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(OUTLINE_SIZE));
			g.drawOval((int) xPos, (int) yPos, xSize, ySize);
			
			// Draw the interior of the spell
			g.setColor(color);
			g.fillOval((int) xPos, (int) yPos, xSize, ySize);
		}
	}
}
