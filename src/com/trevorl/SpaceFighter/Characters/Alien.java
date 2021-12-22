package com.trevorl.SpaceFighter.Characters;

import java.awt.Graphics;  
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import com.trevorl.SpaceFighter.GameDimensions;

public class Alien implements GameDimensions{

	private Image sprite;

	public int x;
	public int y;

	public Alien(int x, int y) {
		sprite = new ImageIcon(
		        getClass().getResource(
				"/Images/alien.png")).getImage();
		
		this.x = x;
		this.y = y;
	}
	
	void move(int dx, int dy) {
		x += dx;
		y += dy;
	}
	
	Missile shoot() {
		int missileY = y + CHARACTER_LENGTH / 2;
		int missileX = x + 1;
				
		return new Missile(
				Missile.Type.ENEMY_MISSILE, missileX, missileY);
	}

	public void paint(Graphics g, JComponent game) {
		g.drawImage(sprite, 
		        x, y, 
				CHARACTER_LENGTH, 
				CHARACTER_LENGTH, 
                game);
	}

}
