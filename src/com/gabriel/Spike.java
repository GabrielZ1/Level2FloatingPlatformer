package com.gabriel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Spike extends GameObject {
	
	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;	
	
	public Spike(int x, int y, int width, int height) {
		super(x, y, width, height);

		if (needImage) {
			loadImage ("spike.png");
		}
		
	}
	
	public void draw(Graphics g) {
		if (gotImage) {
			g.drawImage(image, x, y, width, height, null);
		} else {
			g.setColor(Color.YELLOW);
			g.fillRect(x, y, width, height);
		}
	}
	
}
