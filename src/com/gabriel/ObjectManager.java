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
	ArrayList<SpeedPowerup> speedPowerups = new ArrayList<SpeedPowerup>();
	ArrayList<HighJumpPowerup> highJumpPowerups = new ArrayList<HighJumpPowerup>();
	ArrayList<WingsPowerup> wingPowerups = new ArrayList<WingsPowerup>();


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

	public void addSpeedPowerup(SpeedPowerup speed) {
		speedPowerups.add(speed);
	}

	public void addHighJumpPowerup(HighJumpPowerup highJump) {
		highJumpPowerups.add(highJump);
	}

	public void addWingsPowerup(WingsPowerup wing) {
		wingPowerups.add(wing);
	}

	public boolean update() {

		player.update();
		checkDeath();
		checkPlatformCollision();
		checkSpeedCollision();
		checkHighJumpCollision();
		checkWingsCollision();

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

		for(int i = 0; i<speedPowerups.size(); i++) {
			speedPowerups.get(i).draw(g);
		}

		for(int i = 0; i<highJumpPowerups.size(); i++) {
			highJumpPowerups.get(i).draw(g);
		}

		for(int i = 0; i<wingPowerups.size(); i++) {
			wingPowerups.get(i).draw(g);
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

		for(int i = 0; i<speedPowerups.size(); i++) {
			speedPowerups.remove(i);	
		}

		for(int i = 0; i<highJumpPowerups.size(); i++) {
			highJumpPowerups.remove(i);	
		}

		for(int i = 0; i<wingPowerups.size(); i++) {
			wingPowerups.remove(i);	
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
					player.xSpeed = 5;
					player.jumpPower = 10;
					player.x = 0;
					player.y = 850;
				}

				else if (GamePanel.currentState == GamePanel.LEVELTWO){
					player.width = 10;
					player.height = 10;
					player.xSpeed = 8;
					player.jumpPower = 13;
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
				if(player.y + player.height - 1 <= p.y) {
					player.currentJumps = 0;
				}
				handleCollision(p);
				return true;
			}
			else if(player.hasWings == false) {
				player.currentJumps = 1;
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

	public void checkSpeedCollision() {
		for(int i = 0; i < speedPowerups.size(); i++) {
			if(player.collisionBox.intersects(speedPowerups.get(i).collisionBox)) {
				player.xSpeed = 11;
				speedPowerups.remove(i);
			}
		}

	}

	public void checkHighJumpCollision() {
		for(int i = 0; i < highJumpPowerups.size(); i++){
			if(player.collisionBox.intersects(highJumpPowerups.get(i).collisionBox)) {
				player.jumpPower = 15;
				highJumpPowerups.remove(i);

			}
		}
	}

	public void checkWingsCollision() {
		for(int i = 0; i< wingPowerups.size(); i++){
			if(player.collisionBox.intersects(wingPowerups.get(i).collisionBox)) {
				player.hasWings = true;
				wingPowerups.remove(i);
			}
		}
	}

}
