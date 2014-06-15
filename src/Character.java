
public abstract class Character {
	protected int hp;
	protected int maxhp;
	protected boolean dead = false;
	
	public int getHealth() {
		return hp;
	}
	
	public int takeDamage(int x){
		hp -= x;
		if (hp > maxhp)
			hp = maxhp;
		else if (hp <= 0)
			death();
		return hp;
	}
	
	public void death(){
		dead = true;
	}
	
	public boolean getDeath(){
		return dead;
	}

}
