package com.sandwhalestudios.firstjavagame.graphics;

import java.awt.Color;
import java.util.Random;

import com.sandwhalestudios.firstjavagame.entity.mob.Chaser;
import com.sandwhalestudios.firstjavagame.entity.mob.Mob;
import com.sandwhalestudios.firstjavagame.entity.mob.Player;
import com.sandwhalestudios.firstjavagame.entity.mob.Star;
import com.sandwhalestudios.firstjavagame.entity.projectile.Projectile;
import com.sandwhalestudios.firstjavagame.level.tile.Tile;

public class Screen {
	public int width, height;
	public int[] pixels;
	private final int MAP_SIZE = 8;
	private final int MAP_SIZE_MASK = MAP_SIZE - 1;
	private final int TILE_SIZE = 4;	
	public int xOffset, yOffset;
	public int[] tiles = new int[MAP_SIZE * MAP_SIZE];
	
	private Random random = new Random();
	
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		
		for(int i = 0; i < MAP_SIZE * MAP_SIZE; i++) {
			tiles[i] = random.nextInt(0xffffff);
		}
	}
	
	public void clear() {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	public void renderSheet(int xp, int yp, SpriteSheet sheet, boolean fixed) {
		if(fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for(int y = 0; y < sheet.HEIGHT; y++) {
			int ya = y + yp;
			for(int x = 0; x < sheet.WIDTH; x++) {
				int xa = x + xp;
				if(xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				int col = sheet.pixels[x + y * sheet.WIDTH];
				if(col != 0xffff00ff)
					pixels[xa + ya * width] = col;
			}
		}
	}
	
	public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed) {
		if(fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for(int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			for(int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if(xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				int col = sprite.pixels[x + y * sprite.getWidth()];
				if(col != 0xffff00ff)
					pixels[xa + ya * width] = col;
			}
		}
	}
	
	public void renderTile(int xp, int yp, Tile tile) {
		yp -= yOffset;
		xp -= xOffset;
		for(int y = 0; y < tile.sprite.SIZE; y++) {
			int ya = y + yp; //ya means y absolute
			for(int x = 0; x < tile.sprite.SIZE; x++) {
				int xa = x + xp; //xa means x absolute
				if(xa < -tile.sprite.SIZE || xa  >= width || ya < 0 || ya >= height) break;
				if(xa < 0) xa = 0;
				pixels[xa + ya * width] = tile.sprite.pixels[x + y * tile.sprite.SIZE];
			}
		}
	}
	
	public void renderTile(int xp, int yp, Sprite sprite) {
		yp -= yOffset;
		xp -= xOffset;
		for(int y = 0; y < sprite.SIZE; y++) {
			int ya = y + yp; //ya means y absolute
			for(int x = 0; x < sprite.SIZE; x++) {
				int xa = x + xp; //xa means x absolute
				if(xa < -sprite.SIZE || xa  >= width || ya < 0 || ya >= height) break;
				if(xa < 0) xa = 0;
				int col = sprite.pixels[x + y * sprite.SIZE];
				
				if(col != 0xffff00ff)
					pixels[xa + ya * width] = col;
			}
		}
	}
	
	
	public void renderProjectile(int xp, int yp, Projectile p) {
		yp -= yOffset;
		xp -= xOffset;
		for(int y = 0; y < p.getSpriteSize(); y++) {
			int ya = y + yp; //ya means y absolute
			for(int x = 0; x < p.getSpriteSize(); x++) {
				int xa = x + xp; //xa means x absolute
				if(xa < -p.getSpriteSize() || xa  >= width || ya < 0 || ya >= height) break;
				if(xa < 0) xa = 0;
				int col = p.getSprite().pixels[x + y * p.getSpriteSize()];
				
				if(col != 0xffff00ff) {
					if(col == 0xffc500c5) {
						float[] hsv = getHSV(pixels[xa + ya * width]);
						if(hsv[2] > 0.2f)
							hsv[2] -= 0.2f;
						else
							hsv[2] = 0.0f;
						pixels[xa + ya * width] = Color.HSBtoRGB(hsv[0], hsv[1], hsv[2]);
					}else
						pixels[xa + ya * width] = col;
				}
			}
		}
	}
	
	
	public void renderMob(int xp, int yp, Sprite sprite, boolean flip) {
		yp -= yOffset + 16;
		xp -= xOffset + 16;
		for(int y = 0; y < 32; y++) {
			int ya = y + yp; //ya means y absolute
			for(int x = 0; x < 32; x++) {
				int xa = x + xp; //xa means x absolute
				int xf = x;
				if(flip) xf = 31 - x;
				if(xa < -32 || xa  >= width || ya < 0 || ya >= height) break;
				if(xa < 0) xa = 0;
				int col = sprite.pixels[xf + y * sprite.SIZE];
				
				if(col != 0xffff00ff) { //ARGB value
					if(col == 0xffc500c5) {
						float[] hsv = getHSV(pixels[xa + ya * width]);
						if(hsv[2] > 0.2f)
							hsv[2] -= 0.2f;
						else
							hsv[2] = 0.0f;
						pixels[xa + ya * width] = Color.HSBtoRGB(hsv[0], hsv[1], hsv[2]);
					}
					else
						pixels[xa + ya * width] = col;
				}
			}
		}
	}
	
	public void renderMob(int xp, int yp, Mob mob, boolean flip) {
		yp -= yOffset + 16;
		xp -= xOffset + 16;
		for(int y = 0; y < 32; y++) {
			int ya = y + yp; //ya means y absolute
			for(int x = 0; x < 32; x++) {
				int xa = x + xp; //xa means x absolute
				int xf = x;
				if(flip) xf = 31 - x;
				if(xa < -32 || xa  >= width || ya < 0 || ya >= height) break;
				if(xa < 0) xa = 0;
				int col = mob.getSprite().pixels[xf + y * mob.getSprite().SIZE];
				if((mob instanceof Chaser) && col == 0xff4c4c4c) col = 0xff528899;
				if((mob instanceof Star) && col == 0xff4c4c4c) col = 0xff992d35;
				
				if(col != 0xffff00ff) { //ARGB value
					if(col == 0xffc500c5) {
						float[] hsv = getHSV(pixels[xa + ya * width]);
						if(hsv[2] > 0.2f)
							hsv[2] -= 0.2f;
						else
							hsv[2] = 0.0f;
						pixels[xa + ya * width] = Color.HSBtoRGB(hsv[0], hsv[1], hsv[2]);
					}
					else
						pixels[xa + ya * width] = col;
				}
			}
		}
	}
	
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	public float[] getHSV(int h) {
		String hex = Integer.toHexString(h);
		int r = (int)Integer.valueOf(hex.substring(2, 4), 16); //16 means we want to value as base 16
		int g = (int)Integer.valueOf(hex.substring(4, 6), 16);
		int b = (int)Integer.valueOf(hex.substring(6), 16);
		float[] hsv = new float[3];
		Color.RGBtoHSB(r, g, b, hsv);
		return hsv;
	}
	
}
