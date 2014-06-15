
import javax.sound.sampled.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

public class HotSMain extends JPanel implements ActionListener, MouseListener, MouseMotionListener, KeyListener{
	
	//General
	private int scene = OVERWORLD;
	private static int TITLE = 0;
	private static int PROLOGUE = 1;
	private static int OVERWORLD = 2;
	private static int MENU = 3;
	private static int BATTLE = 4;
	
	private Player player;
	ArrayList<Ally> allies = new ArrayList<Ally>();
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private JFrame frame = new JFrame();
	private Container canvas = frame.getContentPane();
	private Image background;
	private int mX;
	private int mY;
	
	//Overworld
	private boolean moveUp = false;
	private boolean moveDown = false;
	private boolean moveLeft = false;
	private boolean moveRight = false;
	private Timer moveTimer = new Timer(10, this);
	
	private JPanel menuScreen = new JPanel();
	private ArrayList<JButton> menuButtons = new ArrayList<JButton>();
	private JButton itemsButton = new JButton("Items");
	private JButton equipButton = new JButton("Equip");
	private JButton skillsButton = new JButton("Skills");
	private JButton statusButton = new JButton("Status");
	private JButton settingsButton = new JButton("Settings");
	private JButton dataButton = new JButton("Data");
	
	//Battle
	private int battleBG;
	private Timer staminaTimer = new Timer(100, this);
	ArrayList<Spell> spellsThrown = new ArrayList<Spell>();
	private Rectangle hurlPebble = new Rectangle(20, 500, 130, 30);
	private boolean battleWon;
	private boolean battleLost;
	private int totalExperience = 0;
	private boolean nextWindow = false;
	
	
	public HotSMain(){
		//General
		setPreferredSize(new Dimension(800, 600));
		canvas.add(this);
		setBackground(Color.GRAY);
		frame.setResizable(false);
		frame.setTitle("Heroes of the Forest");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		//Overworld
		menuScreen.setPreferredSize(new Dimension(800, 150));
		menuScreen.setBackground(Color.DARK_GRAY);
		menuScreen.setVisible(false);
		
		menuButtons.add(itemsButton);
		menuButtons.add(equipButton);
		menuButtons.add(skillsButton);
		menuButtons.add(statusButton);
		menuButtons.add(settingsButton);
		menuButtons.add(dataButton);
		
		for (JButton button : menuButtons) {
			menuScreen.add(button);
			button.addActionListener(this);
		}

		canvas.add(menuScreen, BorderLayout.SOUTH);
		ImageIcon bg = new ImageIcon("OverworldBG.jpg");
		background = bg.getImage();
		
		frame.pack();
		frame.setVisible(true);
		player = new Player();
		enemies.add(new Stalker(375, 200));
		player.defaultPlayer();
		frame.addKeyListener(this);
		frame.addMouseListener(this);
		frame.addMouseMotionListener(this);
		moveTimer.start();
	}
	
	public void actionPerformed(ActionEvent a){
		Object source = a.getSource();
		
		if (scene == OVERWORLD){
			if (source == moveTimer){
				if (moveUp && !moveDown)
					player.moveUp();
				else if (!moveUp && moveDown)
					player.moveDown();
				
				if (moveLeft && !moveRight)
					player.moveLeft();
				else if (!moveLeft && moveRight)
					player.moveRight();
			}		
			
			if (source == itemsButton)
				itemsButton.setText("ITEMS!");
			else if (source == equipButton)
				equipButton.setText("EQUIPMENT!");
			else if (source == skillsButton)
				skillsButton.setText("SKILLS");
			else if (source == settingsButton)
				settingsButton.setText("SETTINGS");
			else if (source == statusButton)
				statusButton.setText("STATUS");
			else if (source == dataButton)
				dataButton.setText("DATA");
			
		}
		if (scene == BATTLE){
			if (source == moveTimer){
				//Spell Movement
				for (int spellNum = 0; spellNum < spellsThrown.size(); spellNum++){
					Spell spell = spellsThrown.get(spellNum);
					spell.move();
					if (spell.spellHit()){
						enemies.get(spell.getTarget()).takeDamage(spell.getDamage());
						spellsThrown.remove(spellNum);
						checkEnemyPresence();
					}
				}
				
				for (int enemyLoop = 0; enemyLoop < enemies.size(); enemyLoop++){
					Enemy enemy = enemies.get(enemyLoop);
					for (int spellNum = 0; spellNum < enemy.spellsThrown.size(); spellNum++){
						Spell spell = enemy.spellsThrown.get(spellNum);
						spell.move();
						if (spell.spellHit()){
							player.takeDamage(spell.getDamage());
							enemy.spellsThrown.remove(spellNum);
							checkAllyPresence();
						}
					}
				}
			}
			if (source == staminaTimer){
				//Stamina Regeneration
				player.staminaGain(16);
				//Enemy Attacks
				int enemyLoop;
				for (enemyLoop = 0; enemyLoop < enemies.size(); enemyLoop++){
					enemies.get(enemyLoop).attack(player.centerX(), player.centerY());
				}
			}
		}
		repaint();
		
		//To check collision
		if (scene == OVERWORLD){
			for (int loop = 0; loop < enemies.size(); loop++){
				Enemy enemy = enemies.get(loop);
				if (enemy.hitbox().intersects(player.hitbox())){
					player.combatChange(true);
					enemy.combatChange(true);
					battleBegin();
				}
			}
		}
	}
	
	public void mouseDragged(MouseEvent e){
	}
	
	public void mouseMoved(MouseEvent e){
		mX = e.getX();
		mY = e.getY();	
	}
	
	public void mouseClicked(MouseEvent e){
		if (scene == BATTLE){
			if (!battleWon){
				int target = 0;
				if (hurlPebble.contains(mX, mY) && player.getStamina() >= 33.3 && !player.getDeath()){
					spellsThrown.add(new Spell(0, Color.LIGHT_GRAY, player.centerX(), player.centerY(), enemies.get(target).centerX(), enemies.get(target).centerY(), target));
					player.staminaGain(-333);
				}
			}
		}
	}
	
	public void mousePressed(MouseEvent e){
	}
	
	//Hotkeys!
	public void keyPressed(KeyEvent e){
		int keyID = e.getKeyCode();
		
		if (scene == OVERWORLD){
			if (keyID == KeyEvent.VK_UP || keyID == KeyEvent.VK_W){
				moveUp = true;
			}
			if (keyID == KeyEvent.VK_DOWN || keyID == KeyEvent.VK_S){
				moveDown = true;
			}
			if (keyID == KeyEvent.VK_LEFT || keyID == KeyEvent.VK_A){
				moveLeft = true;
			}
			if (keyID == KeyEvent.VK_RIGHT || keyID == KeyEvent.VK_D){
				moveRight = true;
			}
			
			if (keyID == KeyEvent.VK_ENTER) {
				// Toggle the visible state of the Game Menu
				Boolean visibleState = menuScreen.isVisible();
				menuScreen.setVisible(!visibleState);
			}
			
			for (int enemyNum = 0; enemyNum < enemies.size(); enemyNum++){
				Enemy enemy = enemies.get(enemyNum);
				if (enemy.hitbox().intersects(player.hitbox())){
					player.combatChange(true);
					enemy.combatChange(true);
					battleBegin();
				}
			}
		}
		
		else if (scene == BATTLE){
			if (keyID == KeyEvent.VK_SPACE && player.getStamina() >= 33.3 && !player.getDeath() && !battleWon){
				int target = 0;
				spellsThrown.add(new Spell(0, Color.LIGHT_GRAY, player.centerX(), player.centerY(), enemies.get(target).centerX(), enemies.get(target).centerY(), target));
				player.staminaGain(-333);
			}
			if ((keyID == KeyEvent.VK_ENTER || keyID == KeyEvent.VK_SPACE) && battleWon) {
				if (player.levelUp()){
					nextWindow = true;
				}
				else{
					battleEnd();
				}
			}
		}
		repaint();
	}
	
	public void keyReleased(KeyEvent e){
		int keyID = e.getKeyCode();
		
		if (scene == OVERWORLD){
			if (keyID == KeyEvent.VK_UP || keyID == KeyEvent.VK_W){
				moveUp = false;
			}
			if (keyID == KeyEvent.VK_DOWN || keyID == KeyEvent.VK_S){
				moveDown = false;
			}
			if (keyID == KeyEvent.VK_LEFT || keyID == KeyEvent.VK_A){
				moveLeft = false;
			}
			if (keyID == KeyEvent.VK_RIGHT || keyID == KeyEvent.VK_D){
				moveRight = false;
			}
		}
	}
	
	//Transitions from Overworld to Battle scene
	public void battleBegin(){
		scene = BATTLE;
		moveUp = false;
		moveDown = false;
		moveLeft = false;
		moveRight = false;
		
		//Background changer
		int bgSelector = (int)(10*Math.random()+1);
		if (bgSelector > 5){
			ImageIcon bg = new ImageIcon("BattleBG1.jpg");
			background = bg.getImage();
			battleBG = 1;
			player.battleBegin(235, 380);
			int placed = 0;
			for (int enemyNum = 0; enemyNum < enemies.size(); enemyNum++){
				Enemy enemy = enemies.get(enemyNum);
				if (enemy.getActivity()){
					enemy.battleBegin(440-20*placed, 125);
					placed++;
					totalExperience += enemy.getEXP();
				}
			}
		}
		else{
			ImageIcon bg = new ImageIcon("BattleBG2.png");
			background = bg.getImage();
			battleBG = 2;
			player.battleBegin(650, 365);
			int placed = 0;
			for (int loop = 0; loop < enemies.size(); loop++){
				if (enemies.get(loop).getActivity()){
					enemies.get(loop).battleBegin(340-20*placed, 145);
					placed++;
					totalExperience += enemies.get(loop).getEXP();
				}
			}
		}
		menuScreen.setVisible(false);
		staminaTimer.start();
	}
	
	//Draws the Battle Menu
	public void drawMenu(Graphics g){
		if (scene == BATTLE){
			//Background
			g.setColor(new Color(0, 100, 0));
			g.fillRect(0, 450, 800, 150);
			g.setColor(Color.BLACK);
			g.fillRect(10, 460, 780, 130);
			
			//Player
			//Health
			g.setColor(Color.WHITE);
			g.setFont(new Font("Bell MT", Font.BOLD, 20));
			FontMetrics metrics = g.getFontMetrics(new Font("Bell MT", Font.BOLD, 20));
			int hgt = metrics.getHeight();
			//Hgt = 26
			int adv = metrics.stringWidth(player.getHealth()+"/"+player.getMaxHealth(0));
			g.setColor(Color.DARK_GRAY);
			g.fillRect(getWidth()-170, 470, 150, hgt-6);
			if ((double)player.getHealth()/player.getMaxHealth(0) > .25)
				g.setColor(Color.RED);
			else
				g.setColor(player.getLowHealth());
			g.drawString(player.getHealth()+"/"+player.getMaxHealth(0), getWidth()-adv-180, 461+hgt);		
			g.fillRect(getWidth()-167, 473, (int)(144*player.getHealth()/player.getMaxHealth(0)), hgt-12);
			
			//Stamina
			g.setColor(Color.DARK_GRAY);
			g.fillRect(getWidth()-170, 500, 150, hgt-6);
			adv = metrics.stringWidth((int)player.getStamina()+"%");	
			if (player.getStamina() < 33.3)
				g.setColor(Color.WHITE);
			else if (player.getStamina() == 100)
				g.setColor(Color.GREEN);
			else
				g.setColor(Color.CYAN);
			g.drawString((int)player.getStamina()+"%", getWidth()-adv-180, 491+hgt);
			g.fillRect(getWidth()-167, 503, (int)(144*player.getStamina()/100), hgt-12);
			
			//Basic Attack
			adv = metrics.stringWidth("Hurl Pebble");
			g.setColor(Color.DARK_GRAY);
			g.fillRect(20, 470, adv+10, 30);
			g.setColor(Color.WHITE);
			g.drawString("Hurl Pebble", 25, 492);
		}
	}
	
	public void checkEnemyPresence(){
		if (scene == BATTLE){
			int loop;
			int deadCounter = 0;
			for (loop = 0; loop < enemies.size(); loop++){
				if (enemies.get(loop).getDeath())
					deadCounter++;
			}
			if (deadCounter == enemies.size()){
				victory();
			}
		}
	}
	
	public void checkAllyPresence(){
		if (scene == BATTLE){
			int loop;
			int deadCounter = 0;
			if (player.getDeath())
				deadCounter++;
			/*for (loop = 0; loop < allies.size(); loop++){
				if (allies.get(loop).getDeath())
					deadCounter++;
			}*/
			if (deadCounter == allies.size()+1)
				defeat();
		}
	}
	
	public void victory(){
		battleWon = true;
		player.expGain(totalExperience);
		enemies.removeAll(enemies);
		spellsThrown.removeAll(spellsThrown);
		staminaTimer.stop();
	}
	
	public void defeat(){
		battleLost = true;
		staminaTimer.stop();
		int loop;
		for (loop = 0; loop < enemies.size(); loop++){
			enemies.get(loop).spellsThrown.removeAll(spellsThrown);
		}
	}
	
	public void drawVictory(Graphics g){
		if (nextWindow){
			g.setColor(new Color(0, 0, 0, 200));
			g.fillRect(195, 220, 410, 110);
			g.setColor(new Color(255, 255, 255, 200));
			g.fillRect(200, 225, 400, 100);
			g.setColor(new Color(0, 0, 0, 200));
			g.setFont(new Font("Bell MT", Font.BOLD, 20));
			FontMetrics metrics = g.getFontMetrics(new Font("Bell MT", Font.BOLD, 20));
			int hgt = metrics.getHeight();
			//Hgt = 26
			int adv = metrics.stringWidth("You have gotten stronger.");
			g.drawString("You have gotten stronger.", getWidth()/2-adv/2, 269+hgt);
			adv = metrics.stringWidth(player.getName()+ " is now level "+player.getLevel()+"!");
			g.drawString(player.getName()+ " is now level "+player.getLevel()+"!", getWidth()/2-adv/2, 234+hgt);
		}
		else{
			g.setColor(new Color(0, 0, 0, 200));
			g.fillRect(195, 220, 410, 110);
			g.setColor(new Color(255, 255, 255, 200));
			g.fillRect(200, 225, 400, 100);
			g.setColor(new Color(0, 0, 0, 200));
			g.setFont(new Font("Bell MT", Font.BOLD, 20));
			FontMetrics metrics = g.getFontMetrics(new Font("Bell MT", Font.BOLD, 20));
			int hgt = metrics.getHeight();
			//Hgt = 26
			int adv = metrics.stringWidth("You survived!");
			g.drawString("You survived!", getWidth()/2-adv/2, 234+hgt);
			adv = metrics.stringWidth("You and your allies gain "+totalExperience+" experience!");
			g.drawString("You and your allies gain "+totalExperience+" experience!", getWidth()/2-adv/2, 269+hgt);
		}
	}
	
	public void drawDefeat(Graphics g){
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(195, 220, 410, 110);
		g.setColor(new Color(255, 255, 255, 200));
		g.fillRect(200, 225, 400, 100);
		g.setColor(new Color(0, 0, 0, 200));
		g.setFont(new Font("Bell MT", Font.BOLD, 20));
		FontMetrics metrics = g.getFontMetrics(new Font("Bell MT", Font.BOLD, 20));
		int hgt = metrics.getHeight();
		//Hgt = 26
		int adv = metrics.stringWidth("You all fainted.");
		g.drawString("You all fainted.", getWidth()/2-adv/2, 234+hgt);
		adv = metrics.stringWidth("Defenseless, you are all eaten.");
		g.drawString("Defenseless, you are all eaten.", getWidth()/2-adv/2, 269+hgt);
	}
	
	public void battleEnd(){
		scene = OVERWORLD;
		ImageIcon bg = new ImageIcon("OverworldBG.jpg");
		background = bg.getImage();
		player.battleEnd();
		totalExperience = 0;
		nextWindow = false;
		battleWon = false;
		
		enemies.add(new Stalker(175, 200));
	}
	
	public void keyTyped(KeyEvent e){
	}
	 
	public void mouseEntered(MouseEvent e){
	}
	 
	public void mouseExited(MouseEvent e){
		 
	}
	
	public void mouseReleased(MouseEvent e){
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if (scene == OVERWORLD)
			g.drawImage(background, 0, 0, this);
		if (scene == BATTLE){
			if (battleBG == 1)
				g.drawImage(background, 0, -130, this);
			else
				g.drawImage(background, 0, 0, this);
		}
		int loop;
		for (loop = 0; loop < enemies.size(); loop++){
			if (scene == OVERWORLD){
				enemies.get(loop).drawEnemy(g);
			}
			if (scene == BATTLE){
				if (enemies.get(loop).getActivity()){
					enemies.get(loop).drawEnemy(g);
				}
			}
		}
		for (loop = 0; loop < spellsThrown.size(); loop++){
			spellsThrown.get(loop).drawSpell(g);
		}
		player.drawPlayer(g);
		for (loop = 0; loop < enemies.size(); loop++){
			enemies.get(loop).drawSpells(g);
		}
		if (scene == BATTLE){
			drawMenu(g);
			if (battleWon)
				drawVictory(g);
			else if (battleLost)
				drawDefeat(g);
		}
	}

	public static void main (String[] args){
		new HotSMain();
	}
	
}
