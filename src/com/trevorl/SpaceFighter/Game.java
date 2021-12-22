package com.trevorl.SpaceFighter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.Timer;

import com.trevorl.SpaceFighter.Characters.Alien;
import com.trevorl.SpaceFighter.Characters.AlienMover;
import com.trevorl.SpaceFighter.Characters.Missile;
import com.trevorl.SpaceFighter.Characters.Ship;

@SuppressWarnings("serial")
public class Game extends JComponent  
		implements KeyListener, ActionListener, GameDimensions {
	
	private Image background;

	boolean gameOver;
	boolean paused;
	private boolean inReset;
	
	private Timer updateTimer 
            = new Timer(UPDATE_DELAY, this);

	private Ship ship;
	private AlienMover alienMover;
	private ConcurrentLinkedQueue<Alien> aliens 
	        = new ConcurrentLinkedQueue<>();
	private ConcurrentLinkedQueue<Missile> missiles  
	        = new ConcurrentLinkedQueue<>();

	private final static int NUMBER_OF_ACTIONS = 3;
	
	private HashMap<Integer,Boolean> keysPressed 
	        = new HashMap<>();
	private HashMap<Integer,Actions> keyAssignments 
	        = new HashMap<>();
	
	private Integer[] keyValues = {KeyEvent.VK_LEFT,
			KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE};

	Game() {
		setPreferredSize(
				new Dimension(
				GAME_WIDTH,
				GAME_HEIGHT));
		mapInputs();
		addKeyListener(this);

		background = new ImageIcon(
		        getClass().getResource(
				"/Images/background.jpg")).getImage();
		
		ship = new Ship(missiles);
		initAliens();
		alienMover = new AlienMover(aliens, missiles);

		missiles.add(
				new Missile(
				Missile.Type.FRIENDLY_MISSILE, -5, -5));
	}

	void start() {
		updateTimer.start();
	}
	
	private void pause() {
		updateTimer.stop();
		paused = true;
	}
	
	private void unPause() {
		updateTimer.start();
		paused = false;
	}

	private void reset() {
		inReset = true;
		
		if(updateTimer.isRunning()) {
			updateTimer.stop();
		}
	
		aliens.clear();
		missiles.clear();
		missiles.add(
				new Missile(
				Missile.Type.FRIENDLY_MISSILE, -5, -5));
		
		ship.reset();
		alienMover.reset();
		initAliens();
		
		paused = paused ? false : paused;
		gameOver = gameOver ? false : gameOver;
		
		updateTimer.start();
		repaint();
		inReset = false;
	}
	
	private void initAliens() {
		int leftX = HCENTER - ALIEN_HORIZONTAL_GAP;
		int rightX = HCENTER;
		int y = 0;

		int halfWay = Math.max(1, ALIEN_COLS / 2);
		
		for (int i = 0; i < ALIEN_ROWS; i++) {
			for (int j = 0; j < halfWay; j++) {
				
				aliens.add(new Alien(leftX, y));

				if(halfWay > 1) {
					
					aliens.add(new Alien(rightX, y));
				}

				leftX -= ALIEN_HORIZONTAL_GAP;
				rightX += ALIEN_HORIZONTAL_GAP;
			}
			
			leftX = HCENTER - ALIEN_HORIZONTAL_GAP;
			rightX = HCENTER;
			
			y +=  ALIEN_VERTICAL_GAP;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (!gameOver) {
		    scanInputs();
		    
		    if(!aliens.isEmpty()) {
				alienMover.moveAliens();
				alienMover.haveAnAlienShoot();
		    } 
		    
			if(!missiles.isEmpty()) {
				missiles.forEach(missile-> missile.move());
			}
			
			if(checkIfGameOver()) {
				gameOver = true;
			}
		}
		
		repaint();
	}
	
	private boolean checkIfGameOver() {
		return aliens.isEmpty() 
				|| alienMover.bottomY >= GAME_HEIGHT 
	            ||(!missiles.isEmpty() 
	            && Missile.checkMissileCollisions(missiles, aliens, ship));
	}
	
	private void scanInputs() {
		for(int i = 0; i < NUMBER_OF_ACTIONS; i++) {
			
			if(keysPressed.get(keyValues[i])) {
				
				ship.move(keyAssignments.get(
						keyValues[i]));
			}
		}
	}
	
	private void mapInputs() {
		Actions[] allActions = Actions.values();
		
		for(int i = 0; i <  NUMBER_OF_ACTIONS; i++) {
			
			keyAssignments.put(keyValues[i], 
					allActions[i]);
			
			keysPressed.put(keyValues[i], false);
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_Q) {
			System.exit(0);
			
		} else if(e.getKeyCode() == KeyEvent.VK_R) {
			reset();
			return;
			
		} else if(!inReset && !gameOver 
				&& e.getKeyCode() == KeyEvent.VK_P) {

		    if(!paused) {
			    pause();
			} else {
				unPause();
			}
			
			return;
		}

		keysPressed.put(e.getKeyCode(), true);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		keysPressed.put(e.getKeyCode(), false);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}
	
	public void paintComponent(Graphics g) {
		g.drawImage(background, 
		        0, 0, 
				GAME_WIDTH, 
				GAME_HEIGHT, 
                this);

        if(!gameOver) {
        	
        	missiles.forEach(missile -> missile.paint(g,this));
        	
        	aliens.forEach(alien -> alien.paint(g, this));
   
	        ship.paint(g, this);
	        
        } else {
        	paintGameOver(g);
        }
    }
	
	private void paintGameOver(Graphics g) {
		g.setColor(Color.GRAY);
		 
		g.fillRect(HCENTER - GAME_OVER_WIDTH / 2, 
				VCENTER - GAME_OVER_HEIGHT / 2, 
				GAME_OVER_WIDTH, 
				GAME_OVER_HEIGHT);
   	 
   	    g.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
   	 
   	    g.setColor(Color.GREEN.darker());
   	 
   	    g.drawString("GAME OVER", HCENTER - 60,  VCENTER + 9);
	}

}
