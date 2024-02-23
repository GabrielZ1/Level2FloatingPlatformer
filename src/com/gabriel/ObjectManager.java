package com.gabriel;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ObjectManager implements ActionListener {
	Player player;
	ArrayList<Platform> platforms = new ArrayList<Platform>();
	ArrayList<Spike> spikes = new ArrayList<Spike>();
	ArrayList<LeftFacingSpikeWall> spikeWallsL = new ArrayList<LeftFacingSpikeWall>();
	ArrayList<RightFacingSpikeWall> spikeWallsR = new ArrayList<RightFacingSpikeWall>();
	ArrayList<DownFacingSpikeWall> spikeWallsD = new ArrayList<DownFacingSpikeWall>();
	ArrayList<FinishLine> finishLines = new ArrayList<FinishLine>();
	ArrayList<VictoryFlag> victoryFlags = new ArrayList<VictoryFlag>();
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
	
	public void addLeftFacingSpikeWall(LeftFacingSpikeWall spikeL) {
		spikeWallsL.add(spikeL);
	}
	
	public void addRightFacingSpikeWall(RightFacingSpikeWall spikeR) {
		spikeWallsR.add(spikeR);
	}
	
	public void addDownFacingSpikeWall(DownFacingSpikeWall spikeD) {
		spikeWallsD.add(spikeD);
	}

	public void addFinishLine(FinishLine finishLine) {
		finishLines.add(finishLine);
	}
	
	public void addVictoryFlag(VictoryFlag victoryFlag) {
		victoryFlags.add(victoryFlag);
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

		for(int i = 0; i<spikeWallsL.size(); i++) {
			spikeWallsL.get(i).draw(g);
		}
		
		for(int i = 0; i<spikeWallsR.size(); i++) {
			spikeWallsR.get(i).draw(g);
		}
		
		for(int i = 0; i<spikeWallsD.size(); i++) {
			spikeWallsD.get(i).draw(g);
		}
		
		for(int i = 0; i<finishLines.size(); i++) {
			finishLines.get(i).draw(g);
		}

		for(int i = 0; i<victoryFlags.size(); i++) {
			victoryFlags.get(i).draw(g);
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

		platforms.clear();
		spikes.clear();
		spikeWallsL.clear();
		spikeWallsR.clear();
		spikeWallsD.clear();
		finishLines.clear();
		victoryFlags.clear();
		speedPowerups.clear();
		highJumpPowerups.clear();
		wingPowerups.clear();

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
		
		for(LeftFacingSpikeWall sWL: spikeWallsL){
			if(player.collisionBox.intersects(sWL.collisionBox)){
				player.isActive = false;
			}
		}
		
		for(RightFacingSpikeWall sWR: spikeWallsR){
			if(player.collisionBox.intersects(sWR.collisionBox)){
				player.isActive = false;
			}
		}
		
		for(DownFacingSpikeWall sWD: spikeWallsD){
			if(player.collisionBox.intersects(sWD.collisionBox)){
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
		if(player.ySpeed >= 0 && player.y + player.height < p.y + p.height/4 && player.x + player.width >= p.x + 3 && player.x <= p.x + p.width - 3){
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
				player.hasHighJump = true;
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
