package com.trevorl.SpaceFighter.Characters;

import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.Timer;

import com.trevorl.SpaceFighter.GameDimensions;

public class AlienMover implements GameDimensions {

	public int bottomY;
	
	private int dx;

	private boolean canShoot;
	
	private Timer shotTimer = 
			new Timer(ALIEN_SHOT_DELAY, 
			new ShotTimerListener());
	
	private ConcurrentLinkedQueue<Alien> aliens;
	private ConcurrentLinkedQueue<Missile> missiles;
	
	private Random random = new Random();
	
	public AlienMover(ConcurrentLinkedQueue<Alien> aliens,
			ConcurrentLinkedQueue<Missile> missiles) {
		
		this.missiles = missiles;
		this.aliens = aliens;
		
		bottomY = 0;
		dx =  -ALIEN_DX;
		canShoot = true;
	}
	
	public void reset() {
		if(shotTimer.isRunning()) {
			shotTimer.stop();
		}
		
		bottomY = 0;
		dx =  -ALIEN_DX;
		canShoot = true;
	}

	public void haveAnAlienShoot() {
		if(canShoot) { 
		    Alien[] alienList = aliens.toArray(Alien[]::new);
		    
		    missiles.add(alienList[random.nextInt(
		    		alienList.length)].shoot());
		    
		    canShoot = false;
		    shotTimer.start();
		}
	}

	private int shiftPhase;
	
	public void moveAliens() {
        int[] boundaries = getBoundaries();
		int leftX = boundaries[0];
		int rightX = boundaries[1];
		bottomY  = boundaries[2];

		int dy = 0;
		int dx = this.dx;

		if(shiftPhase == 0) {
			if(leftX + this.dx <= 0) {
				dx = -leftX;
				
				this.dx *= -1;
				shiftPhase++;
	
			} else if(rightX + this.dx >= GAME_WIDTH) {
				dx = GAME_WIDTH - rightX;
				
				this.dx *= -1;
				shiftPhase++;
			}
			
		} else if (shiftPhase == 2) {
			dx = 0;	
			dy = ALIEN_DY;
			
			if(bottomY + ALIEN_DY >= GAME_HEIGHT) {
				dy = GAME_HEIGHT - bottomY;
			}

			shiftPhase = 0;
		}

		for(Alien alien: aliens) {
			alien.move(dx, dy);
		}
		
		if(shiftPhase == 1) {
			shiftPhase++;
		}
	}
	
	private int[] getBoundaries(){
	    int[] boundaries = new int[3];

		//leftX
		boundaries[0] = 
		        aliens.stream()
		              .min(Comparator.comparing(e->e.x))
		              .get().x;
			
		//rightX
		boundaries[1] =
				aliens.stream()
		              .max(Comparator.comparing(e->e.x))
		              .get().x
		              + CHARACTER_LENGTH;
			
		//bottomY
		boundaries[2] = 
				aliens.stream()
		              .max(Comparator.comparing(e->e.y))
		              .get().y 
		              + CHARACTER_LENGTH;
			
		return boundaries;
	}

	private class ShotTimerListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			canShoot = true;
			shotTimer.stop();
		}
	}
	
}
