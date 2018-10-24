package com.sandwhalestudios.firstjavagame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.sandwhalestudios.firstjavagame.entity.mob.Player;
import com.sandwhalestudios.firstjavagame.graphics.Font;
import com.sandwhalestudios.firstjavagame.graphics.Screen;
import com.sandwhalestudios.firstjavagame.graphics.Sprite;
import com.sandwhalestudios.firstjavagame.graphics.SpriteSheet;
import com.sandwhalestudios.firstjavagame.input.Keyboard;
import com.sandwhalestudios.firstjavagame.input.Mouse;
import com.sandwhalestudios.firstjavagame.level.Level;
import com.sandwhalestudios.firstjavagame.level.RandomLevel;
import com.sandwhalestudios.firstjavagame.level.SpawnLevel;
import com.sandwhalestudios.firstjavagame.level.tile.TileCoordinate;



public class Game extends Canvas implements Runnable{
	private static final long serialVersionUID = 1L;
	
	public static int width = 600;
	public static int height = width / 16 * 9; //16:9 aspect ratio
	public static int scale = 3;
	public static String title = "Sandwhale Games";
	
	private Thread thread;
	private JFrame frame; //basically like a window
	private Keyboard key;
	private Mouse mouse;
	private Level level;
	private Player player;
	private boolean running = false;
	
	private Screen screen;
	private Font font;
	
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); //create image to draw on
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData(); //access image to draw on
	
	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size); //Canvas method, sets size of Canvas
		
		screen = new Screen(width, height);
		frame = new JFrame();
		key = new Keyboard();
		level = Level.spawn;
		TileCoordinate playerSpawn = new TileCoordinate(38, 22);
		player = new Player(playerSpawn.x(), playerSpawn.y(), key);
		level.add(player);
		font = new Font();
		
		addKeyListener(key);
		
		mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}
	
	public static int getWindowWidth() {
		return width * scale;
	}
	
	public static int getWindowHeight() {
		return height * scale;
	}
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start(); //when thread is started run() is called
	}
	
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() { //when thread is started run() is called
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		
		requestFocus();
		
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				update(); //game
				updates++;
				delta--;
			}
			render(); //images
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle(title + "  |  FPS: " + frames + "  |  UPS: " + updates);
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}
	
	public void update() {
		key.update();
		level.update();
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3); //triple buffer for image rendering
			return;
		}
		
		screen.clear();
		double xScroll = player.getX() - screen.width / 2;
		double yScroll = player.getY() - screen.height / 2;
		level.render((int)xScroll, (int)yScroll, screen);
		font.render(screen);
		
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics(); //links drawing graphics to the screen and the BufferStrategy
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose(); //releases the current graphic
		bs.show();
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle(game.title);
		game.frame.add(game); //adds game component to frame, fills the window with stuff because game is a subclass of Canvas
		game.frame.pack(); //sets the size of the frame to the same size as the game component 
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close app when "x" button clicked, terminates thread
		game.frame.setLocationRelativeTo(null); //centers window in the screen
		game.frame.setVisible(true); //show the window
		
		game.start();
	}
}
