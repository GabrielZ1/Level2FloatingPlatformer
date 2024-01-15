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
		return checkCollision();

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

	//returns true if player collides with finish line
	public boolean checkCollision() {
		for(Platform p: platforms){
			if(player.collisionBox.intersects(p.collisionBox)){
				handleCollision(p);
			}
		}

		for(Spike s: spikes){
			if(player.collisionBox.intersects(s.collisionBox)){
				player.isActive = false;
			}
		}

		for(FinishLine fL: finishLines){
			if(player.collisionBox.intersects(fL.collisionBox)){

				if(true /*put something here that makes it check if currentLevel = LEVELONE*/) {
					player.x = 25;
					player.y = 875;
					player.width = 50;
					player.height = 50;
				}
				
				//	else { or else if currentLevel = LEVELTWO
				//	player.x = 25
				//	player.y = 725
				//	player.width = 10;
				//	player.height = 10;
				//	}

				return true;

			}
		}

		if(player.y > FloatingPlatformer.HEIGHT) {
			player.isActive = false;
		}
		return false;
	}

	private void handleCollision(Platform p){
		if(player.ySpeed >= 0 && player.y + player.height < p.y + p.height/2){
			player.yLimit = p.y - player.height;
		}
		else {
			player.yLimit = 1000;
		}
	}


}
