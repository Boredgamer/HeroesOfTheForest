
import javax.sound.sampled.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

public class HotSMain extends JPanel implements ActionListener, MouseListener, MouseMotionListener, KeyListener{
	
	//General
	private int scene = 2;
	private static int TITLE = 0;
	private static int PROLOGUE = 1;
	private static int OVERWORLD = 2;
	private static int MENU = 3;
	private static int BATTLE = 4;
	
	private Timer timer = new Timer(10, this);
	
	private Player player;
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();

	private JFrame frame = new JFrame();
	
	private Container canvas = frame.getContentPane();
	
	private Image background;
	
	//Overworld
	private boolean moveUp = false;
	private boolean moveDown = false;
	private boolean moveLeft = false;
	private boolean moveRight = false;
	
	private JPanel menuScreen = new JPanel();
	private ArrayList<JButton> menuButtons = new ArrayList<JButton>();
	private JButton itemsButton = new JButton("Items");
	private JButton equipButton = new JButton("Equip");
	private JButton skillsButton = new JButton("Skills");
	private JButton statusButton = new JButton("Status");
	private JButton settingsButton = new JButton("Settings");
	private JButton dataButton = new JButton("Data");
	
	//Battle
	ArrayList<Integer> combativeEnemies = new ArrayList<Integer>();	
	private int battleBG;
	
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
		timer.start();
	}
	
	public void actionPerformed(ActionEvent a){
		Object source = a.getSource();
		
		if (scene == 2){
			if (source == timer){
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
		repaint();
		
		//To check collision
		if (scene == 2){
			int loop;
			for (loop = 0; loop < enemies.size(); loop++){
				if (enemies.get(loop).hitbox().intersects(player.hitbox())){
					player.combatChange(true);
					enemies.get(loop).combatChange(true);
					battleBegin();
				}
			}
		}
	}
	
	public void mouseDragged(MouseEvent e){
	}
	
	public void mouseMoved(MouseEvent e){
	}
	
	public void mouseClicked(MouseEvent e){
	}
	
	public void mousePressed(MouseEvent e){
	}
	
	//Hotkeys!
	public void keyPressed(KeyEvent e){
		int keyID = e.getKeyCode();
		
		if (scene == 2){
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
			
			int loop;
			for (loop = 0; loop < enemies.size(); loop++){
				if (enemies.get(loop).hitbox().intersects(player.hitbox())){
					player.combatChange(true);
					enemies.get(loop).combatChange(true);
					battleBegin();
				}
			}
		}
		repaint();
	}
	
	public void battleBegin(){
		scene = 4;
		timer.stop();
		moveUp = false;
		moveDown = false;
		moveLeft = false;
		moveRight = false;
		
		//Background changer
		int bgSelector = (int)(1*Math.random()+1);
		if (bgSelector > 5){
			ImageIcon bg = new ImageIcon("BattleBG1.jpg");
			background = bg.getImage();
			battleBG = 1;
			player.battleBegin(235, 380);
			int placed = 0;
			int loop;
			for (loop = 0; loop < enemies.size(); loop++){
				if (enemies.get(loop).getActivity()){
					enemies.get(loop).battleBegin(440-20*placed, 125);
					placed++;
				}
			}
		}
		else{
			ImageIcon bg = new ImageIcon("BattleBG2.png");
			background = bg.getImage();
			battleBG = 2;
			player.battleBegin(650, 365);
			int placed = 0;
			int loop;
			for (loop = 0; loop < enemies.size(); loop++){
				if (enemies.get(loop).getActivity()){
					enemies.get(loop).battleBegin(340-20*placed, 145);
					placed++;
				}
			}
		}
		menuScreen.setVisible(false);
	}
	
	public void drawMenu(Graphics g){
		//Background
		g.setColor(Color.GREEN);
		g.fillRect(0, 450, 800, 150);
		g.setColor(Color.BLACK);
		g.fillRect(10, 460, 780, 130);
		
		//Player
		//Health
		g.setColor(Color.WHITE);
		g.setFont(new Font("Bell MT", Font.BOLD, 20));
		FontMetrics metrics = g.getFontMetrics(new Font("Bell MT", Font.BOLD, 20));
		int hgt = metrics.getHeight();
		int adv = metrics.stringWidth(player.getHealth(0)+"/"+player.getMaxHealth(0));	
		//Adv = 50; Hgt = 26
		g.drawString(player.getHealth(0)+"/"+player.getMaxHealth(0), getWidth()-adv-180, 461+hgt);		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(getWidth()-170, 470, 150, hgt-6);
		
		//Stamina
		g.fillRect(getWidth()-170, 500, 150, hgt-6);
	}

	public void keyReleased(KeyEvent e){
		int keyID = e.getKeyCode();
		
		if (scene == 2){
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
		if (scene == 2)
			g.drawImage(background, 0, 0, this);
		if (scene == 4){
			if (battleBG == 1)
				g.drawImage(background, 0, -130, this);
			else
				g.drawImage(background, 0, 0, this);
		}
		int loop;
		for (loop = 0; loop < enemies.size(); loop++){
			if (scene == 2){
				enemies.get(loop).drawEnemy(g);
			}
			if (scene == 4){
				if (enemies.get(loop).getActivity()){
					enemies.get(loop).drawEnemy(g);
				}
			}
		}
		player.drawPlayer(g);
		if (scene == 4){
			drawMenu(g);
		}
	}

	public static void main (String[] args){
		new HotSMain();
	}
	
}
