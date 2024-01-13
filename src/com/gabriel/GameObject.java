package com.gabriel;

import java.awt.Rectangle;

public class GameObject {
	int x;
	int y;
	int width;
	int height;
	int xSpeed = 0;
	int ySpeed = 0;
	int gravity = 0;
	int jumpPower = 0;
	int yLimit = 0;
	boolean isActive = true;
	Rectangle collisionBox;

	public GameObject(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		collisionBox = new Rectangle(x, y, width, height);

	}

	public void update() {
		 collisionBox.setBounds(x, y, width, height);
	}

}
