import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;


public class Enemy extends Character {

	protected int xPos;
	protected int yPos;
	protected static int xSize;
	protected static int ySize;
	protected Rectangle overworldCollision;
	protected boolean inCombat = false;


	protected int exp;
	protected ArrayList<Spell> spellsThrown = new ArrayList<Spell>();
	protected int stamina = 0;
	
	public Enemy(int x, int y) {
	}
	
	public Rectangle hitbox(){
		return overworldCollision;
	}

	public void drawEnemy(Graphics g){
	}
	
	public void drawSpells(Graphics g){
		int loop;
		for (loop = 0; loop < spellsThrown.size(); loop++){
			spellsThrown.get(loop).drawSpell(g);
		}
	}
	
	//Overworld
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
	
	//Battle
	public void attack(int x, int y){
	}
	
	public int getEXP(){
		return exp;
	}
	
	public int centerX(){
		return xPos + xSize/2;
	}

	public int centerY(){
		return yPos +ySize/2;
	}
}
