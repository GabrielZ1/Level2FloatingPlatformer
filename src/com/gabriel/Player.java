package com.gabriel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Player extends GameObject{
	ObjectManager objectManager;

	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;	

	public boolean movingLeft = false;
	public boolean movingRight = false;

	public boolean canJump = false;

	public boolean hasHighJump = false;
	public boolean hasWings = false;

	public int currentJumps = 0;

	Rectangle collisionBox = new Rectangle();

	public Player(int x, int y, int width, int height) {
		super(x, y, width, height);
		xSpeed = 6;
		ySpeed = 0;
		gravity = 1;
		jumpPower = 13;
		yLimit = 2000;

		if (needImage) {
			loadImage ("player.png");
		}

		collisionBox.setBounds(x, y, width, height);

	}

	void draw(Graphics g) {
		if (gotImage) {
			g.drawImage(image, x, y, width, height, null);
		} else {
			g.setColor(Color.BLUE);
			g.fillRect(x, y, width, height);
		}
		super.update();
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

	public void setObjectManager(ObjectManager obj) {
		this.objectManager = obj;
	}

	public void update() {
		//this updates the player's x-position
		if(movingLeft && x > 0) {
			x -= xSpeed;
		}
		if(movingRight && x < FloatingPlatformer.WIDTH - width) {
			x += xSpeed;
		}

		//this updates the player's y-position
		ySpeed += gravity;
		y += ySpeed;		

		//sets the terminal velocity to 11 in level 1, to 6 in level 2, to 8 in level 2 with high jump powerup, and to 8 in level 3
		if(GamePanel.currentState == GamePanel.LEVELONE) {
			if(ySpeed > 11) {
				ySpeed = 11;
			}
		}
		else if(GamePanel.currentState == GamePanel.LEVELTWO && hasHighJump == false) {
			if(ySpeed > 6) {
				ySpeed = 6;
			}
		}
		else if(hasHighJump) {
			if(ySpeed > 8) {
				ySpeed = 8;
			}
		}
		else if(GamePanel.currentState == GamePanel.LEVELTHREE) {
			if(ySpeed > 8) {
				ySpeed = 8;
			}
		}

		//makes sure the player does not fall past the y-limit
		if(y >= yLimit + 1){
			y = yLimit + 1;
			ySpeed = 0;
		}

		//this updates the player's collision box
		collisionBox.setBounds(x, y, width, height);

	}

	void jump() {

		if(hasWings && currentJumps < 2) {
			ySpeed -= jumpPower;
			currentJumps ++;
		}	

		else if(currentJumps < 1) {
			ySpeed -= jumpPower;
			currentJumps ++;
		}

	}

}
