package com.sandwhalestudios.firstjavagame.entity.projectile;

import java.util.Random;

import com.sandwhalestudios.firstjavagame.entity.Entity;
import com.sandwhalestudios.firstjavagame.graphics.Screen;
import com.sandwhalestudios.firstjavagame.graphics.Sprite;

public abstract class Projectile extends Entity {
	
	protected final double xOrigin, yOrigin; //projectile spawn point
	protected double angle;
	protected Sprite sprite;
	protected double x, y;
	protected double nx, ny;
	protected double distance;
	protected double speed, fireRateMultiplier, range, damage;
	
	protected final Random random = new Random();
	
	public Projectile(double x, double y, double dir) {
		xOrigin = x;
		yOrigin = y;
		angle = dir;
		this.x = x;
		this.y = y;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public int getSpriteSize() {
		return sprite.SIZE;
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	protected void move() {
		
	}
}
