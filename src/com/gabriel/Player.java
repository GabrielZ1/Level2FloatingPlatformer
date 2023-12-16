package com.gabriel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Player extends GameObject{
	ObjectManager objectManager;

	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;	

	public Player(int x, int y, int width, int height) {
		super(x, y, width, height);
		speed = 5;

		if (needImage) {
			loadImage ("player.png");
		}

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
}
