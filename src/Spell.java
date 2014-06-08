import java.awt.*;


public class Spell {

	private int xPos;
	private int yPos;
	private int xSize;
	private int ySize;
	private Color color;
	private int targetID;
	private int xTarget;
	private int yTarget;
	private int damage = 0;
	private String effect = "None";
	private boolean particle;
	
	public Spell(int type, int xO, int yO, int xF, int yF, int ID){
		spellType(type);
		xPos = xO-xSize/2;
		yPos = yO-ySize/2;
		xTarget = xF-xSize/2;
		yTarget = yF-ySize/2;
		targetID = ID;
	}
	
	public void spellType(int t){
		if (t == 0){
			color = new Color (102, 51, 0);
			damage = 3;
			xSize = 16;
			ySize = 16;
			particle = true;
		}
	}
	
	public void move(){
		int moveloop;
		for (moveloop = 0; moveloop < 5  && !spellHit(); moveloop++){
			if (xPos < xTarget)
				xPos++;
			else if (xPos > xTarget)
				xPos--;
			if (yPos < yTarget)
				yPos++;
			else if (yPos > yTarget)
				yPos--;
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
			g.setColor(Color.BLACK);
			g.fillOval(xPos-2, yPos-2, xSize+4, ySize+4);
			g.setColor(color);
			g.fillOval(xPos, yPos, xSize, ySize);
		}
	}
}
