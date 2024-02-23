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

	public boolean levelOneObjectsAdded = false;
	public boolean levelTwoObjectsAdded = false;
	public boolean levelThreeObjectsAdded = false;

	public boolean canMoveLeft = true;
	public boolean canMoveRight = true;

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
		//changes level when player intersects with finish line and updates objects in the object manager
		if(objectManager.update()) {
			currentState++;
			topLevel = currentState;
		}

		//collision check for intersection with right side of a platform
		for(Platform p: objectManager.platforms) {

			if(player.x <= p.x + p.width - 2 && player.collisionBox.intersects(p.collisionBox) && player.x > p.x && player.y + player.height >= p.y + 3 && player.y <= p.y + p.height - 3) {
				canMoveLeft = false;
				player.movingLeft = false;
				break;
			}
			else {
				canMoveLeft = true;
			}

		}

		//collision check for intersection with left side of a platform
		for(Platform p: objectManager.platforms) {

			if(player.x+player.width >= p.x + 2 && player.collisionBox.intersects(p.collisionBox) && player.x < p.x && player.y + player.height >= p.y + 3 && player.y <= p.y + p.height - 3) {

				canMoveRight = false;
				player.movingRight = false;
				break;
			}
			else {
				canMoveRight = true;
			}


		}

		//collision check for intersection with bottom of a platform
		for(Platform p: objectManager.platforms) {

			if(player.y < p.y + p.height && player.collisionBox.intersects(p.collisionBox) && player.y + 5 > p.y + p.height) {

				player.ySpeed = 1;
			}

		}

		//checks if player is alive
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

			if(levelOneObjectsAdded == false) {
				addLevelOneObjects();
			}

		}

		else if(currentState == LEVELTWO) {

			FloatingPlatformer.WIDTH = 600;
			FloatingPlatformer.HEIGHT = 1000;

			setPreferredSize(new Dimension(FloatingPlatformer.WIDTH, FloatingPlatformer.HEIGHT));
			frame.pack();
			updateUI();


			if(levelTwoObjectsAdded == false) {
				addLevelTwoObjects();
			}

		}
		else if(currentState == LEVELTHREE) {

			FloatingPlatformer.WIDTH = 800;
			FloatingPlatformer.HEIGHT = 800;

			setPreferredSize(new Dimension(FloatingPlatformer.WIDTH, FloatingPlatformer.HEIGHT));
			frame.pack();
			updateUI();

			if(levelThreeObjectsAdded == false) {
				addLevelThreeObjects();
			}
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
				JOptionPane.showMessageDialog (null, "Use the arrow keys or WASD to move and SPACE to jump.\nReach the finish line while avoiding falling into the void or the spikes to beat the level. \nBeat all 3 levels without dying to complete the game. \n\nSpeed Powerup: Move faster for the rest of the level. \nHigh Jump Powerup: Jump higher for the rest of the level. \nWings Powerup: Gain the ability to double jump for the rest of the level.");
			}
		}

		if(currentState == LEVELONE || currentState == LEVELTWO || currentState == LEVELTHREE) {

			if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_SPACE) {
				player.jump();
			}

			if(e.getKeyCode() == KeyEvent.VK_LEFT && canMoveLeft|| e.getKeyCode() == KeyEvent.VK_A && canMoveLeft) {
				player.movingLeft = true;
			}

			if(e.getKeyCode() == KeyEvent.VK_RIGHT && canMoveRight|| e.getKeyCode() == KeyEvent.VK_D && canMoveRight) {
				player.movingRight = true;
			}

		}

		//secret skip level: press "0" to go to the next level (spawns finish line on top of player)
		if(e.getKeyCode() == KeyEvent.VK_0) {
			if(currentState == LEVELONE || currentState == LEVELTWO || currentState == LEVELTHREE) {
				player.jump();
				objectManager.addFinishLine(new FinishLine(player.x,player.y,player.width,player.height));
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

		loadImage ("background1.png");				
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

		objectManager.addPlatform(new Platform(-25,320,60,40));

		objectManager.addPlatform(new Platform(70,240,75,40));
		objectManager.addPlatform(new Platform(220,210,75,40));
		objectManager.addPlatform(new Platform(370,180,75,40));
		objectManager.addPlatform(new Platform(520,150,75,40));
		objectManager.addPlatform(new Platform(670,120,75,40));

		objectManager.addFinishLine(new FinishLine(850,90,120,40));

		levelOneObjectsAdded = true;

	}
	void addLevelTwoObjects() {

		loadImage ("background2.jpeg");	
		objectManager.purgeObjects();


		objectManager.addPlatform(new Platform(0,900,100,50));

		objectManager.addSpeedPowerup(new SpeedPowerup(150,800,50,50));

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

		objectManager.addPlatform(new Platform(100,550,100,25));
		objectManager.addSpike(new Spike(175,545,15,5));

		objectManager.addPlatform(new Platform(250,500,150,50));
		objectManager.addSpike(new Spike(260,495,15,5));
		objectManager.addSpike(new Spike(375,495,15,5));

		objectManager.addPlatform(new Platform(450,430,150,50));
		objectManager.addSpike(new Spike(460,425,15,5));
		objectManager.addSpike(new Spike(575,425,15,5));

		objectManager.addPlatform(new Platform(300,330,100,40));
		objectManager.addSpike(new Spike(310,295,25,35));

		objectManager.addPlatform(new Platform(110,275,150,40));
		objectManager.addSpike(new Spike(120,240,25,35));
		objectManager.addSpike(new Spike(225,240,25,35));

		objectManager.addPlatform(new Platform(30,180,40,25));
		objectManager.addPlatform(new Platform(100,0,50,25));
		objectManager.addPlatform(new Platform(150,0,50,25));
		objectManager.addPlatform(new Platform(200,0,50,25));
		objectManager.addPlatform(new Platform(250,0,50,25));
		objectManager.addPlatform(new Platform(300,0,50,25));
		objectManager.addPlatform(new Platform(350,0,50,25));

		objectManager.addPlatform(new Platform(150,100,50,25));
		objectManager.addPlatform(new Platform(275,100,50,25));
		objectManager.addPlatform(new Platform(400,100,50,25));

		objectManager.addFinishLine(new FinishLine(550,0,50,25));

		levelTwoObjectsAdded = true;
	}
	void addLevelThreeObjects() {

		player.hasHighJump = false;

		loadImage("background3.jpeg");
		objectManager.purgeObjects();


		objectManager.addPlatform(new Platform(0,750,75,50));
		objectManager.addPlatform(new Platform(175,750,75,50));
		objectManager.addPlatform(new Platform(350,750,75,50));
		objectManager.addPlatform(new Platform(525,750,75,50));
		objectManager.addPlatform(new Platform(700,750,75,50));

		objectManager.addPlatform(new Platform(0,625,90,50));
		objectManager.addPlatform(new Platform(90,625,90,50));
		objectManager.addPlatform(new Platform(180,625,90,50));
		objectManager.addPlatform(new Platform(270,625,90,50));
		objectManager.addPlatform(new Platform(360,625,90,50));
		objectManager.addPlatform(new Platform(450,625,90,50));
		objectManager.addPlatform(new Platform(540,625,90,50));

		objectManager.addSpike(new Spike(0,615,50,15));
		objectManager.addSpike(new Spike(50,615,50,15));
		objectManager.addSpike(new Spike(100,615,50,15));
		objectManager.addSpike(new Spike(150,615,50,15));
		objectManager.addSpike(new Spike(200,615,50,15));
		objectManager.addSpike(new Spike(250,615,50,15));
		objectManager.addSpike(new Spike(300,615,50,15));
		objectManager.addSpike(new Spike(350,615,50,15));
		objectManager.addSpike(new Spike(400,615,50,15));
		objectManager.addSpike(new Spike(450,615,50,15));
		objectManager.addSpike(new Spike(500,615,50,15));
		objectManager.addSpike(new Spike(550,615,75,15));

		objectManager.addWingsPowerup(new WingsPowerup(710,690,50,40));

		objectManager.addPlatform(new Platform(625,75,30,75));
		objectManager.addPlatform(new Platform(625,150,30,75));
		objectManager.addPlatform(new Platform(625,225,30,75));
		objectManager.addPlatform(new Platform(625,300,30,75));
		objectManager.addPlatform(new Platform(625,375,30,75));
		objectManager.addPlatform(new Platform(625,450,30,75));
		objectManager.addPlatform(new Platform(625,525,30,75));
		objectManager.addPlatform(new Platform(625,600,30,75));

		objectManager.addRightFacingSpikeWall(new RightFacingSpikeWall(653,85,25,275));
		objectManager.addRightFacingSpikeWall(new RightFacingSpikeWall(653,360,25,275));

		objectManager.addPlatform(new Platform(770,-75,60,150));
		objectManager.addPlatform(new Platform(770,75,30,75));
		objectManager.addPlatform(new Platform(770,150,30,75));
		objectManager.addPlatform(new Platform(770,225,30,75));
		objectManager.addPlatform(new Platform(770,300,30,75));
		objectManager.addPlatform(new Platform(770,375,30,75));
		objectManager.addPlatform(new Platform(770,450,30,75));
		objectManager.addPlatform(new Platform(770,525,30,75));
		objectManager.addPlatform(new Platform(770,600,30,75));

		objectManager.addLeftFacingSpikeWall(new LeftFacingSpikeWall(748,-75,25,160));
		objectManager.addLeftFacingSpikeWall(new LeftFacingSpikeWall(748,85,25,275));
		objectManager.addLeftFacingSpikeWall(new LeftFacingSpikeWall(748,360,25,275));

		objectManager.addPlatform(new Platform(725,550,45,40));
		objectManager.addPlatform(new Platform(655,400,45,40));
		objectManager.addPlatform(new Platform(725,250,45,40));

		objectManager.addPlatform(new Platform(0,-350,800,300));

		objectManager.addPlatform(new Platform(520,75,105,50));
		objectManager.addPlatform(new Platform(415,75,105,50));
		objectManager.addPlatform(new Platform(310,75,105,50));
		objectManager.addPlatform(new Platform(205,75,105,50));
		objectManager.addPlatform(new Platform(100,75,105,50));

		objectManager.addSpike(new Spike(575,55,45,20));
		objectManager.addSpike(new Spike(530,55,45,20));
		objectManager.addSpike(new Spike(485,55,45,20));
		objectManager.addSpike(new Spike(440,55,45,20));
		objectManager.addSpike(new Spike(395,55,45,20));

		objectManager.addSpike(new Spike(290,55,45,20));
		objectManager.addSpike(new Spike(245,55,45,20));
		objectManager.addSpike(new Spike(200,55,45,20));
		objectManager.addSpike(new Spike(155,55,45,20));

		objectManager.addPlatform(new Platform(100,125,75,50));
		objectManager.addPlatform(new Platform(100,175,75,50));
		objectManager.addPlatform(new Platform(100,225,75,50));
		objectManager.addPlatform(new Platform(100,275,75,50));
		objectManager.addPlatform(new Platform(100,325,75,50));
		objectManager.addPlatform(new Platform(100,375,75,50));
		objectManager.addPlatform(new Platform(100,425,75,50));
		objectManager.addPlatform(new Platform(100,475,75,50));

		objectManager.addLeftFacingSpikeWall(new LeftFacingSpikeWall(73,75,30,220));
		objectManager.addLeftFacingSpikeWall(new LeftFacingSpikeWall(73,295,30,220));

		objectManager.addRightFacingSpikeWall(new RightFacingSpikeWall(0,75,30,220));
		objectManager.addRightFacingSpikeWall(new RightFacingSpikeWall(0,295,30,220));

		objectManager.addPlatform(new Platform(100,475,75,50));
		objectManager.addPlatform(new Platform(175,475,75,50));
		objectManager.addPlatform(new Platform(250,475,75,50));
		objectManager.addPlatform(new Platform(325,475,75,50));
		objectManager.addPlatform(new Platform(400,475,75,50));
		objectManager.addPlatform(new Platform(475,475,75,50));

		objectManager.addDownFacingSpikeWall(new DownFacingSpikeWall(105,520,110,10));
		objectManager.addDownFacingSpikeWall(new DownFacingSpikeWall(215,520,110,10));
		objectManager.addDownFacingSpikeWall(new DownFacingSpikeWall(325,520,110,10));
		objectManager.addDownFacingSpikeWall(new DownFacingSpikeWall(435,520,110,10));

		objectManager.addPlatform(new Platform(10,575,80,40));
		objectManager.addPlatform(new Platform(270,575,80,40));
		objectManager.addPlatform(new Platform(530,575,80,40));

		objectManager.addPlatform(new Platform(510,445,40,30));
		objectManager.addPlatform(new Platform(510,415,40,30));
		objectManager.addPlatform(new Platform(510,385,40,30));
		objectManager.addPlatform(new Platform(510,355,40,30));
		objectManager.addPlatform(new Platform(510,325,40,30));
		objectManager.addPlatform(new Platform(510,295,40,30));
		objectManager.addPlatform(new Platform(510,265,40,30));
		objectManager.addPlatform(new Platform(510,235,40,30));
		objectManager.addPlatform(new Platform(510,205,40,30));
		objectManager.addPlatform(new Platform(510,165,40,40));

		objectManager.addRightFacingSpikeWall(new RightFacingSpikeWall(550,175,10,170));
		objectManager.addRightFacingSpikeWall(new RightFacingSpikeWall(550,345,10,170));

		objectManager.addLeftFacingSpikeWall(new LeftFacingSpikeWall(615,175,10,170));
		objectManager.addLeftFacingSpikeWall(new LeftFacingSpikeWall(615,345,10,170));
		objectManager.addLeftFacingSpikeWall(new LeftFacingSpikeWall(615,515,10,110));

		objectManager.addPlatform(new Platform(550,390,75,40));
		objectManager.addPlatform(new Platform(550,230,75,40));

		objectManager.addDownFacingSpikeWall(new DownFacingSpikeWall(590,430,35,10));
		objectManager.addDownFacingSpikeWall(new DownFacingSpikeWall(550,270,40,10));

		objectManager.addSpike(new Spike(550,220,40,10));

		objectManager.addPlatform(new Platform(460,205,50,30));
		objectManager.addPlatform(new Platform(410,205,50,30));
		objectManager.addPlatform(new Platform(360,205,50,30));
		objectManager.addPlatform(new Platform(310,205,50,30));
		objectManager.addPlatform(new Platform(275,205,35,30));
		objectManager.addPlatform(new Platform(275,165,35,40));

		objectManager.addLeftFacingSpikeWall(new LeftFacingSpikeWall(615,125,10,50));

		objectManager.addDownFacingSpikeWall(new DownFacingSpikeWall(170,125,90,10));
		objectManager.addDownFacingSpikeWall(new DownFacingSpikeWall(260,125,90,10));
		objectManager.addDownFacingSpikeWall(new DownFacingSpikeWall(350,125,90,10));
		objectManager.addDownFacingSpikeWall(new DownFacingSpikeWall(440,125,90,10));
		objectManager.addDownFacingSpikeWall(new DownFacingSpikeWall(530,125,90,10));

		objectManager.addSpike(new Spike(310,195,40,10));
		objectManager.addSpike(new Spike(350,195,40,10));
		objectManager.addSpike(new Spike(390,195,40,10));
		objectManager.addSpike(new Spike(430,195,40,10));
		objectManager.addSpike(new Spike(470,195,40,10));

		objectManager.addRightFacingSpikeWall(new RightFacingSpikeWall(170,135,35,170));
		objectManager.addRightFacingSpikeWall(new RightFacingSpikeWall(170,305,30,170));

		objectManager.addPlatform(new Platform(290,235,50,30));
		objectManager.addPlatform(new Platform(290,265,50,30));
		objectManager.addPlatform(new Platform(290,295,50,30));
		objectManager.addPlatform(new Platform(290,325,50,30));
		objectManager.addPlatform(new Platform(290,355,50,30));

		objectManager.addLeftFacingSpikeWall(new LeftFacingSpikeWall(260,235,35,150));

		objectManager.addSpike(new Spike(175,445,75,30));
		objectManager.addSpike(new Spike(245,445,75,30));

		objectManager.addDownFacingSpikeWall(new DownFacingSpikeWall(295,382,42,25));

		objectManager.addRightFacingSpikeWall(new RightFacingSpikeWall(340,235,10,150));
		objectManager.addDownFacingSpikeWall(new DownFacingSpikeWall(340,235,85,10));
		objectManager.addDownFacingSpikeWall(new DownFacingSpikeWall(425,235,85,10));
		objectManager.addLeftFacingSpikeWall(new LeftFacingSpikeWall(500,235,10,120));
		objectManager.addLeftFacingSpikeWall(new LeftFacingSpikeWall(500,355,10,120));

		objectManager.addPlatform(new Platform(375,435,50,40));
		objectManager.addPlatform(new Platform(425,435,50,40));
		objectManager.addPlatform(new Platform(400,395,50,40));

		objectManager.addFinishLine(new FinishLine(400,280,50,20));
		objectManager.addVictoryFlag(new VictoryFlag(420,260,20,20));

		levelThreeObjectsAdded = true;
	}

	void loadImage(String imageFile) {
		if (needImage) {
			try {
				image = ImageIO.read(this.getClass().getResourceAsStream(imageFile));
				gotImage = true;
			} catch (Exception e) {

			}
		}
	}
	void resetPlayer() {
		topLevel = 1;
		player.isActive = true;
		player = new Player(0, 525, 25, 25);
		objectManager = new ObjectManager(player);
		player.setObjectManager(objectManager);
		player.hasHighJump = false;
		player.hasWings = false;
		player.xSpeed = 6;
		player.jumpPower = 13;
		player.currentJumps = 0;
		levelOneObjectsAdded = false;
		levelTwoObjectsAdded = false;
		levelThreeObjectsAdded = false;
	}
}
