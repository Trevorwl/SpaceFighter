package com.trevorl.SpaceFighter.Characters;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import com.trevorl.SpaceFighter.GameDimensions;

public class Missile implements GameDimensions {
	
	public static enum Type {
		ENEMY_MISSILE, FRIENDLY_MISSILE 
	}

	private Image sprite;
	
	public int x;
	public int y;

	public Type missileType;

	public Missile(Type missileType, int x, int y) {
		
		this.missileType = missileType;
		
		this.x = x;
		this.y = y;

		if(missileType == Type.ENEMY_MISSILE) {
			sprite = new ImageIcon(
					getClass().getResource(
					"/Images/AlienShot.png")).getImage();
		} else {
			sprite = new ImageIcon(
					getClass().getResource(
					"/Images/shot.png")).getImage();
		}
	}
	
	public void move() {
		if(missileType == Type.FRIENDLY_MISSILE) {
			y -= SHIP_MISSILE_DY;
		} else {
			y += ALIEN_MISSILE_DY;
		}
	}

	/**
	 * SpaceFighter.Game calls
	 * this method to remove aliens, missiles and check
	 * if the game is over. 
	 *
	 * @return true if ship has been hit, false otherwise.
	 */
	public static boolean checkMissileCollisions(
			ConcurrentLinkedQueue<Missile>missiles,
			ConcurrentLinkedQueue<Alien>aliens,
			Ship ship) {
		
		Iterator<Missile> missilesItr = missiles.iterator();
		
		while(missilesItr.hasNext()) {
			Missile missile = missilesItr.next();
			
			if(missile.y <= 0 - SHIP_MISSILE_LENGTH || missile.y > GAME_HEIGHT) {
				if(missile.missileType == Type.FRIENDLY_MISSILE ) {
					ship.canShoot = true;
				}
				
				missilesItr.remove();
				continue;
			}
			
			if(missile.missileType == Type.FRIENDLY_MISSILE) {
				int endOfMissileX = missile.x + SHIP_MISSILE_WIDTH / 2;
				
				int neededProximity = CHARACTER_LENGTH / 2 + SHIP_MISSILE_WIDTH / 2;
				
				for(Alien alien: aliens) {
					int alienCenterX = alien.x + CHARACTER_LENGTH / 2;
					int alienCenterY = alien.y + CHARACTER_LENGTH / 2;

					int distance = (int)Math.sqrt(
							Math.pow(alienCenterX - endOfMissileX, 2) 
						  + Math.pow(alienCenterY - missile.y, 2));
					
					if (distance <= neededProximity) {
						aliens.remove(alien);
						missilesItr.remove();
						ship.canShoot = true;
						break;
					}
				}
				
			} else {
				int endOfMissileX = missile.x + ALIEN_MISSILE_WIDTH / 2;
				int endOfMissileY = missile.y + ALIEN_MISSILE_LENGTH;
				
				int shipCenterX = ship.x + CHARACTER_LENGTH / 2;
				int shipCenterY = ship.y + CHARACTER_LENGTH / 2;
				
				int distance = (int)Math.sqrt(
						Math.pow(shipCenterX - endOfMissileX, 2) 
					  + Math.pow(shipCenterY - endOfMissileY, 2));

				if (distance <= CHARACTER_LENGTH / 2 + ALIEN_MISSILE_WIDTH / 2) {
					return true;
				}
			}
		}
		
		return false;
	}

	public void paint(Graphics g, JComponent game) {
		if(missileType == Type.ENEMY_MISSILE) {
			g.drawImage(sprite, x ,y, ALIEN_MISSILE_WIDTH,
					ALIEN_MISSILE_LENGTH, game);
		} else {
			g.drawImage(sprite, x ,y, SHIP_MISSILE_WIDTH,
					SHIP_MISSILE_LENGTH, game);
		}
	}
	
}
