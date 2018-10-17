package com.sandwhalestudios.firstjavagame.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	private String path;
	public final int SIZE;
	public final int WIDTH, HEIGHT;
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
		int w = width * spriteSize;
		int h = height * spriteSize;
		if(width == height) SIZE = width;
		else SIZE = -1;
		WIDTH = w;
		HEIGHT = h;
		pixels = new int[w * h];
		
		for(int y0 = 0; y0 < h; y0++) {
			int yp = yy + y0;
			for(int x0 = 0; x0 < w; x0++) {
				int xp = xx + x0;
				pixels[x0 + y0 * w] = sheet.pixels[xp + yp * sheet.WIDTH];
			}
		}
		
		int frame = 0;
		sprites = new Sprite[width * height];
		for(int ya = 0; ya < height; ya++) {
			for(int xa = 0; xa < width; xa++) {
				int[] spritePixels = new int[spriteSize * spriteSize];
				for(int y0 = 0; y0 < spriteSize; y0++) {
					for(int x0 = 0; x0 < spriteSize; x0++) {
						spritePixels[x0 + y0 * spriteSize] = pixels[(x0 + xa * spriteSize) + (y0 + ya * spriteSize) * WIDTH];
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
		WIDTH = size;
		HEIGHT = size;
		pixels = new int[WIDTH * HEIGHT];
		load();
	}
	
	public SpriteSheet(String path, int width, int height) {
		this.path = path;
		WIDTH = width;
		HEIGHT = height;
		SIZE = -1;
		pixels = new int[WIDTH * HEIGHT];
		load();
	}
	
	public Sprite[] getSprite() {
		return sprites;
	}
	
	private void load() {
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
