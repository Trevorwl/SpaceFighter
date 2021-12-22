package com.trevorl.SpaceFighter.Characters;

import java.awt.Graphics;
import java.awt.Image;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import com.trevorl.SpaceFighter.Actions;
import com.trevorl.SpaceFighter.GameDimensions;

public class Ship implements GameDimensions{
	
	private Image sprite;

    public int x;
    public int y;
    
	public boolean canShoot = true;

	private ConcurrentLinkedQueue<Missile> missiles;
	
	public Ship(ConcurrentLinkedQueue<Missile> missiles) {		
		this.missiles = missiles;

		this.x = SHIP_X;
		this.y = SHIP_Y;
		
		sprite = new ImageIcon(
				getClass().getResource(
				"/Images/ship.png")).getImage();
	}
	
	public void reset() {
		this.x = SHIP_X;
		this.y = SHIP_Y;
		canShoot = true;
	}

	public void move(Actions action) {
		switch(action) {
		    case SHOOT:
				if(canShoot) {
				    shoot();
				    canShoot = false;
				}
				
				break;
			case LEFT:
				x -= SHIP_DX;
				
				x = (x <= 0) ? 0 : x;
				
				break;
			case RIGHT:
				x += SHIP_DX;
				
				if(x >= GAME_WIDTH - CHARACTER_LENGTH) {
					x = GAME_WIDTH - CHARACTER_LENGTH;
				}

				break;
		}
	}

	public void shoot() {
	    missiles.add(
		        new Missile(
				Missile.Type.FRIENDLY_MISSILE, x, y));
	} 

	public void paint(Graphics g, JComponent game) {
		g.drawImage(sprite, 
		        x, y, 
				CHARACTER_LENGTH, 
				CHARACTER_LENGTH, 
                game);
	}

}
