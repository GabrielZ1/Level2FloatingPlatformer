package com.gabriel;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ObjectManager implements ActionListener{
	Player player;
	ArrayList<Platform> platforms = new ArrayList<Platform>();
	ArrayList<Spike> spikes = new ArrayList<Spike>();

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

	public void update() {

		//add collision check for player here probably + purge objects? + update player position?

	}

	public void draw(Graphics g) {
		player.draw(g);

		for(int i = 0; i<platforms.size(); i++) {
			platforms.get(i).draw(g);
		}

		for(int i = 0; i<spikes.size(); i++) {
			spikes.get(i).draw(g);
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

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}


	public void checkCollision() {

	}


	public int getCurrentLevel() {
		return currentLevel;
	}


}
