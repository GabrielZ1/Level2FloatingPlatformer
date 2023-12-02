package com.gabriel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Platformer extends JPanel implements KeyListener, ActionListener{

	final int MENU = 0;
	final int LEVELONE = 1;
	final int LEVELTWO = 2;
	final int LEVELTHREE = 3;
	final int END = 4;

	int currentState = MENU;

	JFrame frame = new JFrame();
	Timer timer = new Timer();

	ArrayList<Platform> platforms = new ArrayList<Platform>();
	ArrayList<Spike> spikes = new ArrayList<Spike>();

	public int WIDTH = 600; //set to 1000, 600 when i switch screen to level 1
	public int HEIGHT = 600;
	
	Font titleFont;
	Font subTextFont;

	public static void main(String[] args) {
		new Platformer().run();
	}

	public void run() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(this);
		frame.setSize(WIDTH, HEIGHT);
		frame.setVisible(true);
	
	}

	public void paintComponent(Graphics g){
		if(currentState == MENU){

		
			
		}
		else if(currentState == LEVELONE){
			WIDTH = 1000;
			HEIGHT = 600;


		}
		else if(currentState == LEVELTWO){
			WIDTH = 600;
			HEIGHT = 1000;

			
		}
		else if(currentState == LEVELTHREE){
			WIDTH = 800;
			HEIGHT = 800;

			
			
		}
		else if(currentState == END){
			WIDTH = 600;
			HEIGHT = 600;

			
			
		}
	}

	void drawMenuState(Graphics g) {	
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.setFont(titleFont);
		g.setColor(Color.YELLOW);
		g.drawString("_ _ _ _ Platformer", 25, 150);

		g.setFont(subTextFont);
		g.drawString("Press ENTER to start", 150, 350);
		g.drawString("Press SPACE for instructions", 115, 500);

	}
	void drawGameState(Graphics g) {

		if (gotImage) {
			g.drawImage(image, 0, 0, null);
		} else {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, WIDTH, HEIGHT);
		}

		player.draw(g);

		for(int i = 0; i<platforms.size(); i++) {
			platforms.get(i).draw(g);
		}
		for(int i = 0; i<spikes.size(); i++) {
			spikes.get(i).draw(g);
		}

	}
	void drawEndState(Graphics g) {
		g.setColor(Color.GREEN);	
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.setFont(titleFont);
		g.setColor(Color.BLACK);
		g.drawString("Game Over", 125, 150);

		g.setFont(subTextFont);
		if(objectManager.getScore() == 1) {
			g.drawString("You killed " + objectManager.getScore() +  " enemy", 155, 350);
		}
		else {
			g.drawString("You killed " + objectManager.getScore() +  " enemies", 155, 350);
		}
		g.drawString("Press ENTER to restart", 140, 500);

	}



	@Override
	public void actionPerformed(ActionEvent arg0) {





		repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {


	}

	@Override
	public void keyReleased(KeyEvent arg0) {


	}

	@Override
	public void keyTyped(KeyEvent arg0) {


	}






}
