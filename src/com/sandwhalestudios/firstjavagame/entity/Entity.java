package com.sandwhalestudios.firstjavagame.entity;

import java.util.Random;

import com.sandwhalestudios.firstjavagame.graphics.Screen;
import com.sandwhalestudios.firstjavagame.graphics.Sprite;
import com.sandwhalestudios.firstjavagame.level.Level;

public class Entity {
	protected double x, y;
	private boolean removed = false;
	protected Level level;
	protected final Random random = new Random();
	protected Sprite sprite;
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void update() {}
	
	public void render(Screen screen) {
		if(sprite != null)
			screen.renderSprite((int)x, (int)y, sprite, true);
	}
	
	public void remove() {
		//Remove entity from level
		removed = true;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public void init(Level level) {
		this.level = level;
	}
}
