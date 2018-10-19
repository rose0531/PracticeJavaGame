package com.sandwhalestudios.firstjavagame.graphics;

public class Sprite {

	public final int SIZE;
	private int width, height;
	private int x, y;
	public int[] pixels;	
	protected SpriteSheet sheet;
	
	public static Sprite grass = new Sprite(16, 0, 0, SpriteSheet.tiles);
	public static Sprite rock = new Sprite(16, 1, 0, SpriteSheet.tiles);
	public static Sprite voidSprite = new Sprite(16, 0x4E1E84);
	public static Sprite lava = new Sprite(16, 2, 0, SpriteSheet.tiles);
	public static Sprite flower = new Sprite(16, 3, 1, SpriteSheet.tiles);
	public static Sprite water = new Sprite(16, 4, 0, SpriteSheet.tiles);
	public static Sprite sand = new Sprite(16, 1, 1, SpriteSheet.tiles);
	public static Sprite sandPath = new Sprite(16, 1, 2, SpriteSheet.tiles);
	public static Sprite castleWallStraight = new Sprite(16, 3, 1, SpriteSheet.tiles);
	public static Sprite castleWallCorner = new Sprite(16, 3, 2, SpriteSheet.tiles);
	public static Sprite castleFloor = new Sprite(16, 6, 0, SpriteSheet.tiles);
	
	//Weapon, Projectile Sprites:
	public static Sprite fireBall = new Sprite(16, 1, 1, SpriteSheet.projectiles);
	public static Sprite enemyProjectile = new Sprite(16, 2, 1, SpriteSheet.projectiles);
	
	//Corsshair Sprites:
	public static Sprite crosshair = new Sprite(16, 0, 1, SpriteSheet.projectiles);
	
	//Enemy Sprites:
	public static Sprite pig = new Sprite(32, 0, 0, SpriteSheet.pig);
	
	//Player Sprites:
	public static Sprite dummy = new Sprite(32, 0, 0, SpriteSheet.playerAnim);
	
	//Particle Sprites:
	public static Sprite particleNormal = new Sprite(3, 0xffffffff);
	public static Sprite fireParticle = new Sprite(3, 0xffff8463);
	public static Sprite blackParticle = new Sprite(3, 0);
	
	protected Sprite(SpriteSheet sheet, int width, int height) {
		if(width == height) SIZE = width;
		else SIZE = -1;
		this.sheet = sheet;
		this.width = width;
		this.height = height;
	}
	
	
	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		load();
	}
	
	public Sprite(int width, int height, int color) {
		SIZE = -1;
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		setColor(color);
	}
	
	public Sprite(int size, int color) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		setColor(color);
	}

	public Sprite(int[] pixels, int width, int height) {
		if(width == height) SIZE = width;
		else SIZE = -1;
		this.width = width;
		this.height = height;
		this.pixels = pixels;
	}


	private void setColor(int color) {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = color;
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	private void load() {
		for(int y = 0; y < width; y++) {
			for(int x = 0; x < height; x++) {
				pixels[x + y * width] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.WIDTH];
			}
		}
	}
}
