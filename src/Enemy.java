import java.awt.Graphics;
import java.awt.Rectangle;


public class Enemy {

	protected int xPos;
	protected int yPos;
	protected static int xSize;
	protected static int ySize;
	protected Rectangle overworldCollision;
	protected boolean inCombat = false;
	
	public Enemy(int x, int y) {
	}
	
	public Rectangle hitbox(){
		return overworldCollision;
	}

	public void drawEnemy(Graphics g){
	}
	
	public void combatChange(boolean b){
		inCombat = b;
	}
	
	public void battleBegin(int x, int y){
		xPos = x-xSize/2;
		yPos = y-ySize/2;
	}
	
	public boolean getActivity(){
		return inCombat;
	}
}
