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

	public static final int MENU = 0;
	public static final int LEVELONE = 1;
	public static final int LEVELTWO = 2;
	public static final int LEVELTHREE = 3;
	public static final int END = 4;

	public static int currentState = MENU;
	public int topLevel = 1;

	Font titleFont;
	Font subTextFont;

	Timer frameDraw;

	Player player = new Player(0, 525, 25, 25);

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

			setPreferredSize(new Dimension(FloatingPlatformer.WIDTH, FloatingPlatformer.HEIGHT));
			frame.pack();

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
		if(objectManager.update()) {
			currentState++;
			topLevel = currentState;
		}

		if(player.isActive == false) {
			currentState = END;
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

			FloatingPlatformer.WIDTH = 1000;
			FloatingPlatformer.HEIGHT = 600;

			setPreferredSize(new Dimension(FloatingPlatformer.WIDTH, FloatingPlatformer.HEIGHT));
			frame.pack();
			updateUI();

			addLevelOneObjects();
		}

		else if(currentState == LEVELTWO) {

			FloatingPlatformer.WIDTH = 600;
			FloatingPlatformer.HEIGHT = 1000;

			setPreferredSize(new Dimension(FloatingPlatformer.WIDTH, FloatingPlatformer.HEIGHT));
			frame.pack();
			updateUI();

			addLevelTwoObjects();
		}
		else if(currentState == LEVELTHREE) {

			FloatingPlatformer.WIDTH = 800;
			FloatingPlatformer.HEIGHT = 800;

			setPreferredSize(new Dimension(FloatingPlatformer.WIDTH, FloatingPlatformer.HEIGHT));
			frame.pack();
			updateUI();

			addLevelThreeObjects();
		}

		objectManager.draw(g);

	}
	void drawEndState(Graphics g) {

		if(topLevel <= 3) {
			g.setColor(Color.RED);	
			g.fillRect(0, 0, FloatingPlatformer.WIDTH, FloatingPlatformer.HEIGHT);

			g.setFont(titleFont);
			g.setColor(Color.BLACK);
			g.drawString("Game Over", 165, 150);

			g.setFont(subTextFont);
			g.drawString("You got to level " + topLevel + ".", 205, 350);

		}
		else {
			g.setColor(Color.GREEN);	
			g.fillRect(0, 0, FloatingPlatformer.WIDTH, FloatingPlatformer.HEIGHT);

			g.setFont(titleFont);
			g.setColor(Color.BLACK);
			g.drawString("Congratulations, you won!", 20, 225);
		}
		g.setFont(subTextFont);
		g.drawString("Press ENTER to restart", 180, 425);

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
				resetPlayer();
			}
			else if (currentState == MENU){
				currentState = LEVELONE;

			}

		}

		if(currentState == MENU) {
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				JOptionPane.showMessageDialog (null, "Use the arrow keys or WASD to move and SPACE to jump.\nReach the finish line while avoiding falling into the void or the spikes to beat the level. \n\nSpeed Powerup: Move faster for the rest of the level. \nHigh Jump Powerup: Jump higher for the rest of the level. \nWings Powerup: Gain the ability to double jump for the rest of the level.");
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

			//below add parameters to make it so player doesnt go inside platform
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
		objectManager.purgeObjects();

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
		objectManager.addPlatform(new Platform(220,210,75,40));
		objectManager.addPlatform(new Platform(370,180,75,40));
		objectManager.addPlatform(new Platform(520,150,75,40));
		objectManager.addPlatform(new Platform(670,120,75,40));

		objectManager.addFinishLine(new FinishLine(850,90,120,40));

	}
	void addLevelTwoObjects() {
		objectManager.purgeObjects();

		objectManager.addSpeedPowerup(new SpeedPowerup(150,800,50,50));

		objectManager.addPlatform(new Platform(0,910,100,50));
		objectManager.addPlatform(new Platform(250,900,175,50));
		objectManager.addSpike(new Spike(310,880,50,20));

		objectManager.addPlatform(new Platform(470,860,50,30));
		objectManager.addPlatform(new Platform(550,810,50,30));
		objectManager.addSpike(new Spike(580,795,15,15));
		objectManager.addPlatform(new Platform(450,760,50,30));

		objectManager.addPlatform(new Platform(310,710,100,50));
		objectManager.addSpike(new Spike(320,700,30,10));

		objectManager.addPlatform(new Platform(110,685,100,50));
		objectManager.addSpike(new Spike(120,670,40,15));

		objectManager.addPlatform(new Platform(10,650,50,25));	
		objectManager.addHighJumpPowerup(new HighJumpPowerup(20,560,30,30));

		objectManager.addPlatform(new Platform(100,575,100,25));
		objectManager.addSpike(new Spike(175,570,15,5));

		objectManager.addPlatform(new Platform(250,525,150,50));
		objectManager.addSpike(new Spike(260,520,15,5));
		objectManager.addSpike(new Spike(375,520,15,5));

		objectManager.addPlatform(new Platform(450,470,150,50));
		objectManager.addSpike(new Spike(460,465,15,5));
		objectManager.addSpike(new Spike(575,465,15,5));

		objectManager.addPlatform(new Platform(300,355,100,25));
		objectManager.addSpike(new Spike(310,320,25,35));

		objectManager.addPlatform(new Platform(110,300,150,25));
		objectManager.addSpike(new Spike(120,265,25,35));
		objectManager.addSpike(new Spike(225,265,25,35));

		objectManager.addPlatform(new Platform(325,200,25,10));
		objectManager.addFinishLine(new FinishLine(450,125,100,40));

	}
	void addLevelThreeObjects() {
		objectManager.purgeObjects();

		objectManager.addPlatform(new Platform(0,750,100,50));

		
		//level three objects go here


		objectManager.addFinishLine(new FinishLine(20,20,50,15));

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
	void resetPlayer() {
		topLevel = 1;
		player.isActive = true;
		player = new Player(0, 525, 25, 25);
		objectManager = new ObjectManager(player);
		player.setObjectManager(objectManager);
		player.hasWings = false;
		player.xSpeed = 6;
		player.jumpPower = 13;
		player.currentJumps = 0;
	}
}
