package com.gabriel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Platform extends GameObject{
	
	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;	
	
	public Platform(int x, int y, int width, int height) {
		super(x, y, width, height);
	
		if (needImage) {
			loadImage ("platform.png");
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
