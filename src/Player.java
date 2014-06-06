import java.awt.*;


public class Player {
	
	//Details
	private boolean isMale;
	private Color skin;
	private Color hair;
	private String name;
	private int emblem;
	
	//Overworld info
	private int xPos;
	private int yPos;
	private static int xSize = 20;
	private static int ySize = 50;
	private boolean inCombat = false;
	private Rectangle overworldCollision = new Rectangle(xPos, yPos, 20, 50);
	private static int MOVE_DISTANCE = 2;

	//Battle
	int maxhp = 12;
	int hp;
	int stamina = 1000;
	
	public Player(){
		xPos = 390;
		yPos = 500;
		hp = maxhp;
	}
	
	public void setGender(boolean b){
		isMale = b;
	}
	
	public void setSkinColor(Color c){
		skin = c;
	}
	
	public void setHairColor(Color c){
		hair = c;
	}
	
	public void setName(String s){
		name = s;
	}
	
	public void setEmblem(int e){
		emblem = e;
	}
	
	public void defaultPlayer(){
		isMale = true;
		skin = new Color(253, 243, 179);
		hair = Color.BLACK;
		name = "Levin";
		emblem = 0;
	}
	
	public Rectangle hitbox(){
		return overworldCollision;
	}
	
	public void combatChange(boolean b){
		inCombat = b;
	}
	
	public void battleBegin(int x, int y){
		xPos = x-xSize/2;
		yPos = y-ySize/2;
	}
	
	public void moveLeft() {
		xPos -= MOVE_DISTANCE;
		if (xPos <= 0){
			xPos = 0; 
		}
		overworldCollision = new Rectangle(xPos, yPos, xSize, ySize);
	}

	public void moveRight() {
		xPos += MOVE_DISTANCE;
		if (xPos >= 780){
			xPos = 780; 
		}
		overworldCollision = new Rectangle(xPos, yPos, xSize, ySize);
	}
	
	public void moveUp() {
		yPos -= MOVE_DISTANCE;
		if (yPos <= 0){
			yPos = 0; 
		}
		overworldCollision = new Rectangle(xPos, yPos, xSize, ySize);
	}
	
	public void moveDown() {
		yPos += MOVE_DISTANCE;
		if (yPos >= 550){
			yPos = 550; 
		}
		overworldCollision = new Rectangle(xPos, yPos, xSize, ySize);
	}
		
	public void drawPlayer(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g.setColor(skin);
		g.fillRect(xPos, yPos, xSize, ySize);
		g.setColor(hair);
		g.fillRect(xPos, yPos, xSize, 15);
		
		g.setColor(Color.BLACK);
		if (emblem == 0){
			g2.setStroke(new BasicStroke(2));
			g.drawLine(xPos + 15, yPos + 25, xPos + 5, yPos + 35);
			g.drawLine(xPos + 5, yPos + 35, xPos + 15, yPos + 35);
			g.drawLine(xPos + 15, yPos + 35, xPos + 5, yPos + 45);
			
		}
	}
	
	public int getHealth(int x){
		hp += x;
		if (hp > maxhp)
			hp = maxhp;
		return hp;
	}
	
	public int getMaxHealth(int x){
		maxhp += x;
		hp += x;
		return maxhp;
	}
	
	public int getStamina(int x){
		stamina += x;
		if (stamina > 1000)
			stamina = 1000;
		return stamina;
	}

}
