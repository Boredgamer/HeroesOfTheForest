import java.awt.Graphics;
import java.awt.Rectangle;


public class Enemy {

	protected int xPos;
	protected int yPos;
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
	
	public boolean getActivity(){
		return inCombat;
	}
}
