package com.gabriel;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ObjectManager implements ActionListener{
	Player player;
	ArrayList<Platform> platforms = new ArrayList<Platform>();
	ArrayList<Spike> spikes = new ArrayList<Spike>();
	ArrayList<FinishLine> finishLines = new ArrayList<FinishLine>();


	int currentLevel = 1;

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

	public void update() {

		//add collision check for player here probably + purge objects? + update player position?
		checkCollision();
		player.update();


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
			if(platforms.get(i).isActive == false) {
				platforms.remove(i);	
			}	
		}

		for(int i = 0; i<spikes.size(); i++) {
			if(spikes.get(i).isActive == false) {
				spikes.remove(i);	
			}	
		}

		for(int i = 0; i<finishLines.size(); i++) {
			if(finishLines.get(i).isActive == false) {
				finishLines.remove(i);	
			}	
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}


	public void checkCollision() {
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
		
		for(FinishLine f: finishLines){
			if(player.collisionBox.intersects(f.collisionBox)){
				currentLevel ++;
			}
		}
		
		if(player.y > FloatingPlatformer.HEIGHT) {
			player.isActive = false;
		}
	}

	private void handleCollision(Platform p){
		if(player.ySpeed >= 0 && player.y + player.height < p.y + p.height/2){
			player.yLimit = p.y - player.height;
		}
		else {
			player.yLimit = 1000;
		}
	}
	public int getCurrentLevel() {
		return currentLevel;
	}


}
