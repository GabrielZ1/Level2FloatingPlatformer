package com.gabriel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Platformer {
	JFrame frame;
	GamePanel gp;
	public static int WIDTH = 600;
	public static int HEIGHT = 600;

	public static void main(String[] args) {
		new Platformer().run();
	}

	public Platformer() {
		frame = new JFrame();
		gp = new GamePanel();
	}

	public void run() {
		frame.add(gp);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setVisible(true);
		frame.addKeyListener(gp);

	}
}
