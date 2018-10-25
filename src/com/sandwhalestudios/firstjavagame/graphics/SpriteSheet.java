package com.sandwhalestudios.firstjavagame.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	private String path;
	public final int SIZE; //in pixels
	public final int SPRITE_WIDTH, SPRITE_HEIGHT;
	private int width, height; //in pixels
	public int[] pixels;
	
	private Sprite[] sprites;
	
	public static SpriteSheet tiles = new SpriteSheet("/textures/sheets/spritesheet.png", 256);
	public static SpriteSheet projectiles = new SpriteSheet("/textures/sheets/projectiles/projectilesheet.png", 256);
	public static SpriteSheet playerAnim = new SpriteSheet("/textures/sheets/player/playerAnimationSheet.png", 512);
	public static SpriteSheet pig = new SpriteSheet("/textures/sheets/enemies/pig.png", 512);
	
	//sub sprite sheets
	public static SpriteSheet playerAnimDown = new SpriteSheet(playerAnim, 0, 0, 1, 8, 32);
	public static SpriteSheet playerAnimRight = new SpriteSheet(playerAnim, 1, 0, 1, 8, 32);
	public static SpriteSheet playerAnimUp = new SpriteSheet(playerAnim, 2, 0, 1, 8, 32);
	public static SpriteSheet playerAnimFrontBob = new SpriteSheet(playerAnim, 3, 0, 1, 4, 32);
	public static SpriteSheet playerAnimRightBob = new SpriteSheet(playerAnim, 4, 0, 1, 4, 32);
	public static SpriteSheet playerAnimUpBob = new SpriteSheet(playerAnim, 5, 0, 1, 4, 32);
	public static SpriteSheet playerAnimRollUp = new SpriteSheet(playerAnim, 6, 0, 1, 6, 32);
	public static SpriteSheet playerAnimRollRight = new SpriteSheet(playerAnim, 7, 0, 1, 6, 32);
	public static SpriteSheet playerAnimRollDown = new SpriteSheet(playerAnim, 8, 0, 1, 6, 32);
	
	public static SpriteSheet pigExecutionerAnimDown = new SpriteSheet(pig, 0, 0, 1, 8, 32);
	public static SpriteSheet pigExecutionerAnimRight = new SpriteSheet(pig, 1, 0, 1, 8, 32);
	public static SpriteSheet pigExecutionerAnimUp = new SpriteSheet(pig, 2, 0, 1, 8, 32);
	
	public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteSize) {
		int xx = x * spriteSize;
		int yy = y * spriteSize;
		this.width = width * spriteSize;
		this.height = height * spriteSize;
		SPRITE_WIDTH = spriteSize;
		SPRITE_HEIGHT = spriteSize;
		if(width == height) SIZE = this.width;
		else SIZE = -1;
		pixels = new int[this.width * this.height];
		
		for(int y0 = 0; y0 < this.height; y0++) {
			int yp = yy + y0;
			for(int x0 = 0; x0 < this.width; x0++) {
				int xp = xx + x0;
				pixels[x0 + y0 * this.width] = sheet.pixels[xp + yp * sheet.getWidth()];
			}
		}
		
		int frame = 0;
		sprites = new Sprite[width * height];
		for(int ya = 0; ya < height; ya++) {
			for(int xa = 0; xa < width; xa++) {
				int[] spritePixels = new int[spriteSize * spriteSize];
				for(int y0 = 0; y0 < spriteSize; y0++) {
					for(int x0 = 0; x0 < spriteSize; x0++) {
						spritePixels[x0 + y0 * spriteSize] = pixels[(x0 + xa * spriteSize) + (y0 + ya * spriteSize) * this.width];
					}
				}
				Sprite sprite = new Sprite(spritePixels, spriteSize, spriteSize);
				sprites[frame++] = sprite;
			}
		}
	}
	
	
	public SpriteSheet(String path, int size) {
		this.path = path;
		SIZE = size;
		width = size;
		height = size;
		SPRITE_WIDTH = 16;
		SPRITE_HEIGHT = 16;
		pixels = new int[width * height];
		load();
	}
	
	public SpriteSheet(String path, int width, int height) {
		this.path = path;
		this.width = width;
		this.height = height;
		SPRITE_WIDTH = 16;
		SPRITE_HEIGHT = 16;
		SIZE = 16;
		pixels = new int[this.width * this.height];
		load();
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Sprite[] getSprite() {
		return sprites;
	}
	
	private void load() {
		try {
			System.out.print("Trying to load: " + path);
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			System.out.println(" success!");
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		} catch(Exception e) {
			System.err.println(" failed!");
		}
	}
	
	public int[] getPixels() {
		return pixels;
	}
}
