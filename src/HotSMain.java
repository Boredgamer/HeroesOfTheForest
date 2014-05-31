import javax.sound.sampled.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

public class HotSMain extends JPanel implements ActionListener, MouseListener, MouseMotionListener, KeyListener{
	
	private int scene = 2;
	private static int TITLE = 0;
	private static int PROLOGUE = 1;
	private static int OVERWORLD = 2;
	private static int MENU = 3;
	private static int BATTLE = 4;
	
	private Player player;
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	//General
	private JFrame frame = new JFrame();
	private JPanel bottomScreen = new JPanel();
	private Container canvas = frame.getContentPane();
	
	private Image background;
	
	public HotSMain(){
		setPreferredSize(new Dimension(800, 600));
		canvas.add(this);
		setBackground(Color.BLACK);
		frame.setResizable(false);
		frame.setTitle("Heroes of the Forest");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		bottomScreen.setPreferredSize(new Dimension(800, 150));
		bottomScreen.setBackground(Color.LIGHT_GRAY);
		canvas.add(bottomScreen, BorderLayout.SOUTH);
		ImageIcon bg = new ImageIcon("FirstBG.jpg");
		background = bg.getImage();
		
		frame.pack();
		frame.setVisible(true);
		player = new Player();
		enemies.add(new Stalker(375, 200));
		player.defaultPlayer();
		frame.addKeyListener(this);
	}
	
	public void actionPerformed(ActionEvent a){
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
