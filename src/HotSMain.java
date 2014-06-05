
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
	
	private Player player;
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();

	private JFrame frame = new JFrame();
	
	private Container canvas = frame.getContentPane();
	
	private Image background;
	
	//Overworld
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
	private JPanel battleMenu = new JPanel();
	
	
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
		
		//Battle
		battleMenu.setPreferredSize(new Dimension(800, 150));
		battleMenu.setBackground(Color.DARK_GRAY);
		battleMenu.setVisible(false);
		
		frame.pack();
		frame.setVisible(true);
		player = new Player();
		enemies.add(new Stalker(375, 200));
		player.defaultPlayer();
		frame.addKeyListener(this);
	}
	
	public void actionPerformed(ActionEvent a){
		Object source = a.getSource();
		
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
				player.moveUp();
			}
			if (keyID == KeyEvent.VK_DOWN || keyID == KeyEvent.VK_S){
				player.moveDown();
			}
			if (keyID == KeyEvent.VK_LEFT || keyID == KeyEvent.VK_A){
				player.moveLeft();
			}
			if (keyID == KeyEvent.VK_RIGHT || keyID == KeyEvent.VK_D){
				player.moveRight();
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
		//Background changer
		int bgSelector = (int)(10*Math.random()+10);
		if (bgSelector > 5){
			ImageIcon bg = new ImageIcon("BattleBG1.jpg");
			background = bg.getImage();
			player.battleBegin(235, 440);
			int placed = 0;
			int loop;
			for (loop = 0; loop < enemies.size(); loop++){
				if (enemies.get(loop).getActivity()){
					enemies.get(loop).battleBegin(440-20*placed, 255);
					placed++;
				}
			}
		}
		else{
			ImageIcon bg = new ImageIcon("BattleBG2.png");
			background = bg.getImage();
		}
		battleMenu.setVisible(true);
	}

	public void keyReleased(KeyEvent e){
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
		g.drawImage(background, 0, 0, this);
		int loop;
		for (loop = 0; loop < enemies.size(); loop++){
			enemies.get(loop).drawEnemy(g);
		}
		player.drawPlayer(g);
	}

	public static void main (String[] args){
		new HotSMain();
	}
	
}
