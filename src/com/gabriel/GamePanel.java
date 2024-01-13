package com.gabriel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements KeyListener, ActionListener {

	final int MENU = 0;
	final int LEVELONE = 1;
	final int LEVELTWO = 2;
	final int LEVELTHREE = 3;
	final int END = 4;

	int currentState = MENU;

	Font titleFont;
	Font subTextFont;

	Timer frameDraw;

	Player player = new Player(50, 525, 25, 25);

	ObjectManager objectManager = new ObjectManager(player);

	JFrame frame;

	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;

	public GamePanel(JFrame frame) {
		titleFont = new Font ("Arial", Font.PLAIN, 48);
		subTextFont = new Font ("Arial", Font.PLAIN, 20);

		frameDraw = new Timer(1000/60, this);
		frameDraw.start();

		this.frame = frame;

		if (needImage) {
			loadImage ("background.png");
		}

		player.setObjectManager(objectManager);
	}

	public void paintComponent(Graphics g) {
		if (currentState == MENU) {
			FloatingPlatformer.WIDTH = 600;
			FloatingPlatformer.HEIGHT = 600;

			drawMenuState(g);

		}
		else if (currentState == LEVELONE || currentState == LEVELTWO || currentState == LEVELTHREE) {

			drawGameState(g);

		}
		else if (currentState == END) {
			FloatingPlatformer.WIDTH = 600;
			FloatingPlatformer.HEIGHT = 600;

			setPreferredSize(new Dimension(FloatingPlatformer.WIDTH, FloatingPlatformer.HEIGHT));
			frame.pack();

			drawEndState(g);

		}
	}

	void updateMenuState() {

	}

	void updateGameState() {
		objectManager.update();
		if(player.isActive == false) {
			//put what happens here when u die (fall into void or get hit by a spike probably)
		}

	}
	void updateEndState() {

	}

	void drawMenuState(Graphics g) {	
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, FloatingPlatformer.WIDTH, FloatingPlatformer.HEIGHT);

		g.setFont(titleFont);
		g.setColor(Color.BLACK);
		g.drawString("Floating Platformer", 100, 150);

		g.setFont(subTextFont);
		g.drawString("Press ENTER to start", 190, 350);
		g.drawString("Press SPACE for instructions", 160, 425);

	}
	void drawGameState(Graphics g) {

		if (gotImage) {
			g.drawImage(image, 0, 0, FloatingPlatformer.WIDTH, FloatingPlatformer.HEIGHT, null);

		} else {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, FloatingPlatformer.WIDTH, FloatingPlatformer.HEIGHT);
		}

		if(currentState == LEVELONE) {
			addLevelOneObjects();
		}
		else if(currentState == LEVELTWO) {
			addLevelTwoObjects();
		}
		else if(currentState == LEVELTHREE) {
			addLevelThreeObjects();
		}

		objectManager.draw(g);

	}
	void drawEndState(Graphics g) {
		g.setColor(Color.GREEN);	
		g.fillRect(0, 0, FloatingPlatformer.WIDTH, FloatingPlatformer.HEIGHT);

		g.setFont(titleFont);
		g.setColor(Color.BLACK);
		g.drawString("Game Over", 125, 150);

		g.setFont(subTextFont);
		if(objectManager.getCurrentLevel() <= 3) {
			g.drawString("You got to level " + objectManager.getCurrentLevel() + ".", 155, 350);
		}
		else {
			g.drawString("Congratulations, you won!", 155, 350);
		}
		g.drawString("Press ENTER to restart", 140, 500);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(currentState == MENU){
			updateMenuState();
		}
		else if(currentState == LEVELONE || currentState == LEVELTWO || currentState == LEVELTHREE){
			updateGameState();
		}
		else if(currentState == END){
			updateEndState();
		}

		repaint();
	}


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (currentState == END) {
				currentState = MENU;
				player = new Player(50, 525, 25, 25);
				objectManager = new ObjectManager(player);
				player.setObjectManager(objectManager);
			}
			else if (currentState == MENU){
				currentState = LEVELONE;

				//NOTE: when changing to the level 2 (beat level 1), use these 4 lines below but set width to 600 and height to 1000 (for lvl 3 same but 800 800)
				//ALSO make sure to call objectManager.purgeObjects(); & i think i have to deactivate all objects? WHEN SWITCHING TO A NEW LEVEL!
				FloatingPlatformer.WIDTH = 1000;
				FloatingPlatformer.HEIGHT = 600;
				setPreferredSize(new Dimension(FloatingPlatformer.WIDTH, FloatingPlatformer.HEIGHT));
				frame.pack();

			}

		}

		if(currentState == MENU) {
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				JOptionPane.showMessageDialog (null, "Use the arrow keys to move. Reach the green finish lined while avoiding falling into the void or the spikes to beat the level.");
			}
		}

		if(currentState == LEVELONE || currentState == LEVELTWO || currentState == LEVELTHREE) {

			if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_SPACE) {
				player.jump();
			}

			if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
				player.movingLeft = true;
			}

			if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
				player.movingRight = true;
			}

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

		if(currentState == LEVELONE || currentState == LEVELTWO || currentState == LEVELTHREE) {

			if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
				player.movingLeft = false;
			}

			if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
				player.movingRight = false;
			}

		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	void addLevelOneObjects() {
		objectManager.addPlatform(new Platform(0,550,150,50));
		objectManager.addPlatform(new Platform(210,550,225,50));
		objectManager.addPlatform(new Platform(500,550,300,50));
		objectManager.addSpike(new Spike(610,520,70,30));

		objectManager.addPlatform(new Platform(850,475,125,50));

		objectManager.addPlatform(new Platform(700,400,100,50));
		objectManager.addPlatform(new Platform(500,400,100,50));
		objectManager.addPlatform(new Platform(50,400,350,50));
		objectManager.addSpike(new Spike(270,370,70,30));
		objectManager.addSpike(new Spike(110,370,70,30));

		objectManager.addPlatform(new Platform(-25,320,50,25));

		objectManager.addPlatform(new Platform(70,240,75,40));
		objectManager.addPlatform(new Platform(220,240,75,40));
		objectManager.addPlatform(new Platform(370,240,75,40));
		objectManager.addPlatform(new Platform(520,240,75,40));
		objectManager.addPlatform(new Platform(670,240,75,40));

		objectManager.addFinishLine(new FinishLine(850,220,120,40));

	}
	void addLevelTwoObjects() {

	}
	void addLevelThreeObjects() {

	}


	void loadImage(String imageFile) {
		if (needImage) {
			try {
				image = ImageIO.read(this.getClass().getResourceAsStream(imageFile));
				gotImage = true;
			} catch (Exception e) {

			}
			needImage = false;
		}
	}
}
