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
	public static Sprite wave = new Sprite(16, 3, 1, SpriteSheet.projectiles);
	
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
		this.pixels = new int[width * height];
		for(int i = 0; i < pixels.length; i++) {
			this.pixels[i] = pixels[i];
		}
	}
	
	public static Sprite[] split(SpriteSheet sheet) {
		int amount = (sheet.getWidth() * sheet.getHeight()) / (sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT);
		Sprite[] sprites = new Sprite[amount];
		int current = 0;
		int[] pixels = new int[sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT];
		
		for(int yp = 0; yp < sheet.getHeight() / sheet.SPRITE_HEIGHT; yp++) {
			for(int xp = 0; xp < sheet.getWidth() / sheet.SPRITE_WIDTH; xp++) {
					
				for(int y = 0; y < sheet.SPRITE_HEIGHT; y++) {
					for(int x = 0; x < sheet.SPRITE_WIDTH; x++) {
						int xo = x + xp * sheet.SPRITE_WIDTH;
						int yo = y + yp * sheet.SPRITE_HEIGHT;
						pixels[x + y * sheet.SPRITE_WIDTH] = sheet.getPixels()[xo + yo * sheet.getWidth()];
					}
				}
				sprites[current++] = new Sprite(pixels, sheet.SPRITE_WIDTH, sheet.SPRITE_HEIGHT);
			}
		}
		return sprites;
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
				pixels[x + y * width] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.getWidth()];
			}
		}
	}
	
	public static Sprite rotate(Sprite sprite, double angle) {
		return new Sprite(rotate(sprite.pixels, sprite.width, sprite.height, angle), sprite.width, sprite.height);
	}
	
	private static int[] rotate(int[] pixels, int height, int width, double angle) {
		int[] result = new int[width * height];
		
		double nx_x = rotX(-angle, 1.0, 0.0);
		double nx_y = rotY(-angle, 1.0, 0.0);
		double ny_x = rotX(-angle, 0.0, 1.0);
		double ny_y = rotY(-angle, 0.0, 1.0);
		
		double x0 = rotX(-angle, -width / 2.0, -height / 2.0) + width / 2.0;
		double y0 = rotY(-angle, -width / 2.0, -height / 2.0) + height / 2.0;
		
		for(int y = 0; y < height; y++) {
			double x1 = x0;
			double y1 = y0;
			for(int x = 0; x < width; x++) {
				int xx = (int) x1;
				int yy = (int) y1;
				int col = 0;
				if(xx < 0 || xx >= width || yy < 0 || yy >= height) col = 0xffff00ff;
				else col = pixels[xx + yy * width];
				result[x + y * width] = col;
				x1 += nx_x;
				y1 += nx_y;
			}
			x0 += ny_x;
			y0 += ny_y;
		}
		
		return result;
	}
	
	private static double rotX(double angle, double x, double y) {
		double cos = Math.cos(angle - Math.PI / 2);
		double sin = Math.sin(angle - Math.PI / 2);
		return x * cos + y * -sin;
	}
	
	private static double rotY(double angle, double x, double y) {
		double cos = Math.cos(angle - Math.PI / 2);
		double sin = Math.sin(angle - Math.PI / 2);
		return x * sin + y * cos;
	}
}
