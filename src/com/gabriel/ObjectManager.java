package com.gabriel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;

public class ObjectManager implements ActionListener {
	Player player;
	ArrayList<Platform> platforms = new ArrayList<Platform>();
	ArrayList<Spike> spikes = new ArrayList<Spike>();
	ArrayList<FinishLine> finishLines = new ArrayList<FinishLine>();

	public ObjectManager (Player player) {
		this.player = player;
	}

	public void addPlatform(Platform platform) {
		platforms.add(platform);
	}

	public void addSpike(Spike spike) {
		spikes.add(spike);
	}

	public void addFinishLine(FinishLine finishLine) {
		finishLines.add(finishLine);
	}

	public boolean update() {

		player.update();
		checkDeath();
		checkPlatformCollision();

		return checkFinishLineCollision();

	}

	public void draw(Graphics g) {
		player.draw(g);

		for(int i = 0; i<platforms.size(); i++) {
			platforms.get(i).draw(g);
		}

		for(int i = 0; i<spikes.size(); i++) {
			spikes.get(i).draw(g);
		}

		for(int i = 0; i<finishLines.size(); i++) {
			finishLines.get(i).draw(g);
		}
	}

	public void purgeObjects() {

		for(int i = 0; i<platforms.size(); i++) {
			platforms.remove(i);	
		}

		for(int i = 0; i<spikes.size(); i++) {
			spikes.remove(i);	
		}

		for(int i = 0; i<finishLines.size(); i++) {
			finishLines.remove(i);	
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	//method below returns true if player collides with finish line
	public boolean checkFinishLineCollision() {
		for(FinishLine fL: finishLines){
			if(player.collisionBox.intersects(fL.collisionBox)){

				if(GamePanel.currentState == GamePanel.LEVELONE) {
					player.width = 50;
					player.height = 50;
					player.jumpPower = 10;
					player.x = 0;
					player.y = 850;
				}

				else if (GamePanel.currentState == GamePanel.LEVELTWO){
					player.width = 10;
					player.height = 10;
					player.jumpPower = 20;
					player.x = 0;
					player.y = 740;
				}
				return true;
			}
		}
		return false;
	}

	public void checkDeath() {

		for(Spike s: spikes){
			if(player.collisionBox.intersects(s.collisionBox)){
				player.isActive = false;
			}
		}

		if(player.y > FloatingPlatformer.HEIGHT) {
			player.isActive = false;
		}

	}

	public boolean checkPlatformCollision() {
		for(Platform p: platforms){
			if(player.collisionBox.intersects(p.collisionBox)){
				handleCollision(p);
				return true;
			}
		}
		player.yLimit = 2000;
		return false;
	}

	private void handleCollision(Platform p){
		if(player.ySpeed >= 0 && player.y + player.height < p.y + p.height/2){
			player.yLimit = p.y - player.height;
		}
		else {
			player.yLimit = 2000;
		}
	}


}
