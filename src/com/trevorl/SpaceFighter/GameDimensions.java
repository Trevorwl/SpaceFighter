package com.trevorl.SpaceFighter;


public interface GameDimensions {
	
	public final static int GAME_WIDTH = 375;
	public final static int GAME_HEIGHT = 300;
	
	public final static int GAME_OVER_WIDTH = GAME_WIDTH / 2;
	public final static int GAME_OVER_HEIGHT = GAME_HEIGHT / 2;

	public final static int HCENTER = GAME_WIDTH / 2;
	public final static int VCENTER = GAME_HEIGHT / 2;
	
	public final static int CHARACTER_LENGTH = GAME_WIDTH / (GAME_WIDTH / 13);

	public final static int UPDATE_DELAY = 17;
	public final static int ALIEN_SHOT_DELAY = 700;

	public final static int SHIP_X = HCENTER - CHARACTER_LENGTH;
	public final static int SHIP_Y = GAME_HEIGHT - CHARACTER_LENGTH; 
	
	public final static int ALIEN_HORIZONTAL_GAP = CHARACTER_LENGTH + CHARACTER_LENGTH / 2;
	public final static int ALIEN_VERTICAL_GAP = CHARACTER_LENGTH 
	        + CHARACTER_LENGTH / 2;
	
	public final static int ALIEN_ROWS = 4;
	public final static int ALIEN_COLS = 10;

	public final static int SHIP_DX = GAME_WIDTH / (GAME_WIDTH / 2);
	public final static int ALIEN_DX = GAME_WIDTH / 250;
	public final static int ALIEN_DY = CHARACTER_LENGTH * 2;
	
	public final static int SHIP_MISSILE_DY = GAME_WIDTH / 30;
	public final static int ALIEN_MISSILE_DY = GAME_WIDTH / 100;
	
	public final static int SHIP_MISSILE_LENGTH = GAME_WIDTH / (GAME_WIDTH / 12);
	public final static int SHIP_MISSILE_WIDTH = GAME_WIDTH / (GAME_WIDTH / 10);
	public final static int ALIEN_MISSILE_LENGTH = GAME_WIDTH / (GAME_WIDTH / 10);
	public final static int ALIEN_MISSILE_WIDTH = GAME_WIDTH / (GAME_WIDTH / 10);
	
}
