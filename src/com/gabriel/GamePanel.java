package com.gabriel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
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

	Player player = new Player(50, 400, 50, 50);

	ObjectManager objectManager = new ObjectManager(player);

	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;

	public GamePanel() {
		titleFont = new Font ("Arial", Font.PLAIN, 48);
		subTextFont = new Font ("Arial", Font.PLAIN, 20);

		frameDraw = new Timer(1000/60, this);
		frameDraw.start();

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
		else if (currentState == LEVELONE) {
			Platformer.WIDTH = 1000;
			Platformer.HEIGHT = 600;

			drawGameState(g);

		}
		else if (currentState == LEVELTWO) {
			Platformer.WIDTH = 600;
			Platformer.HEIGHT = 1000;

			drawGameState(g);

		}
		else if (currentState == LEVELTHREE) {
			Platformer.WIDTH = 800;
			Platformer.HEIGHT = 800;

			drawGameState(g);

		}
		else if (currentState == END) {
			Platformer.WIDTH = 600;
			Platformer.HEIGHT = 600;

			drawEndState(g);

		}
	}

	void updateMenuState() {

	}

	void updateGameState() {
		objectManager.update();
		if(player.isActive == false) {
			//put what happens here when i die (fall into void or get hit by a spike probably)
		}

	}
	void updateEndState() {

	}

	void drawMenuState(Graphics g) {	
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, Platformer.WIDTH, Platformer.HEIGHT);

		g.setFont(titleFont);
		g.setColor(Color.YELLOW);
		g.drawString("_ _ (name) _ _ Platformer", 25, 150);

		g.setFont(subTextFont);
		g.drawString("Press ENTER to start", 150, 350);
		g.drawString("Press SPACE for instructions", 115, 500);

	}
	void drawGameState(Graphics g) {

		if (gotImage) {
			g.drawImage(image, 0, 0, null);
		} else {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, Platformer.WIDTH, Platformer.HEIGHT);
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
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		//add things here
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

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

	public void startGame() {

	}
}
