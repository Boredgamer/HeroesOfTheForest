import java.awt.*;


public class Player extends Character {
	
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
	private int xOverworld;
	private int yOverworld;

	//Battle
	private int maxhp = 12;

	private int healthBlinker = 0;
	private int stamina = 0;
	private int level = 1;
	private int experience = 0;
	private int expToLevel = 150;
	private int expToGive = 0;
	
	public Player(){
		xPos = 390;
		yPos = 500;
		hp = 12;
	}
	
	//Overworld
	public Rectangle hitbox(){
		return overworldCollision;
	}
	
	public void combatChange(boolean b){
		inCombat = b;
	}
	
	public void battleBegin(int x, int y){
		xOverworld = xPos;
		yOverworld = yPos;
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
	
	public void battleEnd(){
		xPos = xOverworld;
		yPos = yOverworld;
		stamina = 0;
	}
	
	//Battle
	public int takeDamage(int x){
		hp -= x;
		if (hp > maxhp)
			hp = maxhp;
		else if (hp <= 0){
			hp = 0;
			death();
		}
		return hp;
	}
	

	
	public int getMaxHealth(int x){
		maxhp += x;
		hp += x;
		return maxhp;
	}
	
	public Color getLowHealth(){
		healthBlinker++;
		if (healthBlinker <= 20 || dead)
			return Color.WHITE;
		else{
			if (healthBlinker == 40)
				healthBlinker = 0;
			return Color.RED;
		}
	}
	
	public void staminaGain(int x){
		stamina += x;
		if (stamina > 1000)
			stamina = 1000;
	}
	
	public double getStamina(){
		return stamina/10;
	}

	
	public void expGain(int x){
		expToGive = x;
	}
	
	public boolean levelUp(){
		if (experience + expToGive >= expToLevel){
			expToGive -= expToLevel - experience;
			experience = expToLevel;
			level++;
			//ExpToLevel Change
			expToLevel *= level;
			return true;
		}
		else{
			experience += expToGive;
			expToGive = 0;
			return false;
		}
	}
	
	public int getLevel(){
		return level;
	}
	
	public String getName(){
		return name;
	}
	
	public int centerX(){
		return xPos + xSize/2;
	}

	public int centerY(){
		return yPos +ySize/2;
	}
	
	//Details
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
	
	
	public void drawPlayer(Graphics g){
		if (!dead){
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
		else{
			Graphics2D g2 = (Graphics2D) g;
			g.setColor(skin);
			g.fillRect(xPos-(ySize-xSize), yPos+xSize, ySize, xSize);
			g.setColor(hair);
			g.fillRect(xPos-(ySize-xSize), yPos+xSize, 15, xSize);
			
			g.setColor(Color.BLACK);
			if (emblem == 0){
				g2.setStroke(new BasicStroke(2));
				g.drawLine(xPos-(ySize-xSize) + 25, yPos+xSize + 15, xPos-(ySize-xSize) + 35, yPos+xSize + 5);
				g.drawLine(xPos-(ySize-xSize) + 35, yPos+xSize + 5, xPos-(ySize-xSize) + 35, yPos+xSize + 15);
				g.drawLine(xPos-(ySize-xSize) + 35, yPos+xSize + 15, xPos-(ySize-xSize) + 45, yPos+xSize + 5);
				
			}
		}
	}
}
