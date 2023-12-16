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
			Platformer.WIDTH = 600;
			Platformer.HEIGHT = 600;

			drawMenuState(g);

		}
		else if (currentState == LEVELONE || currentState == LEVELTWO || currentState == LEVELTHREE) {

			drawGameState(g);

		}
		else if (currentState == END) {
			Platformer.WIDTH = 600;
			Platformer.HEIGHT = 600;

			setPreferredSize(new Dimension(Platformer.WIDTH, Platformer.HEIGHT));
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
		g.fillRect(0, 0, Platformer.WIDTH, Platformer.HEIGHT);

		g.setFont(titleFont);
		g.setColor(Color.BLACK);
		g.drawString("_ _ (name) _ _ Platformer", 25, 150);

		g.setFont(subTextFont);
		g.drawString("Press ENTER to start", 150, 350);
		g.drawString("Press SPACE for instructions", 115, 500);

	}
	void drawGameState(Graphics g) {

		if (gotImage) {
			g.drawImage(image, 0, 0, Platformer.WIDTH, Platformer.HEIGHT, null);

		} else {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, Platformer.WIDTH, Platformer.HEIGHT);
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
		g.fillRect(0, 0, Platformer.WIDTH, Platformer.HEIGHT);

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
		//add things here
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
				//ALSO make sure to call objectManager.purgeObjects(); WHEN SWITCHING TO A NEW LEVEL!
				Platformer.WIDTH = 1000;
				Platformer.HEIGHT = 600;
				setPreferredSize(new Dimension(Platformer.WIDTH, Platformer.HEIGHT));
				frame.pack();

			}

		}

		if(currentState == MENU) {
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				JOptionPane.showMessageDialog (null, "Use the arrow keys to move. Reach the green finish lined while avoiding falling into the void or the spikes to beat the level.");
			}
		}

		if(currentState == LEVELONE || currentState == LEVELTWO || currentState == LEVELTHREE) {
			//movement commands go here


		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(currentState == LEVELONE || currentState == LEVELTWO || currentState == LEVELTHREE) {
			//more movement commands go here


		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	void addLevelOneObjects() {
		objectManager.platforms.add();
		objectManager.spikes.add();
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
