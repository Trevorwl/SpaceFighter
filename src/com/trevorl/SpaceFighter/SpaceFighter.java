package com.trevorl.SpaceFighter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
 
@SuppressWarnings("serial")
public class SpaceFighter extends JFrame{ 
	
	private static class CustomButton extends JButton{
		static final int BUTTON_WIDTH = 100;
		static final int BUTTON_HEIGHT = 30;
		
		public CustomButton(String name) {
			super(name);
			
			setFont(new Font("Consolas", Font.BOLD, 16));
			setBackground(new Color(50, 200, 100).darker());
			setFocusPainted(false);
			setBorder(new BevelBorder(
					BevelBorder.RAISED));
		}
	}
	
	private class SplashScreen extends JComponent 
	        implements GameDimensions{
		
		private Image background;
		private JButton continueButton;
		private JButton aboutButton;
		
		private final int CONTINUE_BUTTON_X = HCENTER / 2
				- CustomButton.BUTTON_WIDTH / 2;
		private final int CONTINUE_BUTTON_Y = GAME_HEIGHT 
				- CustomButton.BUTTON_HEIGHT * 2;
		
		private final int ABOUT_BUTTON_X = (int)(HCENTER * 1.5) 
				- CustomButton.BUTTON_WIDTH / 2;
		private final int ABOUT_BUTTON_Y = CONTINUE_BUTTON_Y;
		
		public SplashScreen() {
			setPreferredSize(
					new Dimension(
					GAME_WIDTH,
					GAME_HEIGHT));
			
			background = new ImageIcon(
			        getClass().getResource(
					"/Images/Splash screen.png")).getImage();

			initButtons();
		}
		
		private void initButtons() {
			add(continueButton = new CustomButton("Continue"));
			add(aboutButton = new CustomButton("About"));
			
			continueButton.setBounds(
					CONTINUE_BUTTON_X, 
					CONTINUE_BUTTON_Y,
					CustomButton.BUTTON_WIDTH, 
					CustomButton.BUTTON_HEIGHT);
			
			aboutButton.setBounds(
					ABOUT_BUTTON_X, 
					ABOUT_BUTTON_Y,
					CustomButton.BUTTON_WIDTH, 
					CustomButton.BUTTON_HEIGHT);
			
			continueButton.addActionListener(e -> {
				addWindow(game, this);
				game.requestFocusInWindow();
				game.start();
				
				splashScreen = null;
				aboutScreen = null;
				game = null;
			});
			
			aboutButton.addActionListener(e -> {
				addWindow(aboutScreen, this);
			});
		}

		public void paintComponent(Graphics g) {
			g.drawImage(background, 0, 0, GAME_WIDTH,
					GAME_HEIGHT, this);
		}
	}
	
	private class AboutScreen extends JComponent 
			implements GameDimensions {
		
		private Image background;
		private JButton goBack;
		
		private final int BACK_BUTTON_X = HCENTER 
				- CustomButton.BUTTON_WIDTH / 2;
		private final int BACK_BUTTON_Y = GAME_HEIGHT 
				- CustomButton.BUTTON_HEIGHT * 2;
		
		AboutScreen(){
			setPreferredSize(
					new Dimension(
					GAME_WIDTH,
					GAME_HEIGHT));
			
			background = new ImageIcon(
			        getClass().getResource(
					"/Images/AboutScreen.png")).getImage();
			
			initButton();
		}
		
		private void initButton() {
			add(goBack = new CustomButton("Back"));
			
			goBack.setBounds(
					BACK_BUTTON_X, 
					BACK_BUTTON_Y,
					CustomButton.BUTTON_WIDTH, 
					CustomButton.BUTTON_HEIGHT);
			
			goBack.addActionListener(e-> {
				addWindow(splashScreen, this);
			});
		}
		
		public void paintComponent(Graphics g) {
			g.drawImage(background,0,0, GAME_WIDTH, GAME_HEIGHT, this);
		}
	}
	
	private SplashScreen splashScreen;
	private AboutScreen aboutScreen;
	private Game game;

	public SpaceFighter() {
    	game = new Game();
		splashScreen = new SplashScreen();
		aboutScreen = new AboutScreen();
    	
    	addWindow(splashScreen, null);
    	
    	setTitle("Space Fighters");
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	setResizable(false);
    	setLocationRelativeTo(null);
    	setVisible(true);
	}
	
	private void addWindow(JComponent newWindow, 
			JComponent oldWindow) {
		
		if(oldWindow != null) {
			remove(oldWindow);
		}
		
		add(newWindow);
		pack();
		
		if(oldWindow != null) {
			revalidate();
			repaint();
		}
	}

	public static void main(String[] args) {
		 SwingUtilities.invokeLater(() -> {
			new SpaceFighter();
		});
	}

}
