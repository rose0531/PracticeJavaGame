package com.sandwhalestudios.firstjavagame.entity.projectile;

import com.sandwhalestudios.firstjavagame.entity.spawner.ParticleSpawner;
import com.sandwhalestudios.firstjavagame.graphics.Screen;
import com.sandwhalestudios.firstjavagame.graphics.Sprite;

public class SoundWaveProjectile extends Projectile{
	
	public static final int FIRE_RATE = 20; //Higher is slower
	private int anim = 0;

	public SoundWaveProjectile(double x, double y, double dir) {
		super(x, y, dir);
		range = random.nextInt(20) + 300;
		speed = 4.5f;//3.5
		damage = 20;
		fireRateMultiplier = 1;
		sprite = Sprite.ironMaidenNote1;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}
	
	public void update() {
		if(level.tileCollision((int)(x + nx), (int)(y + ny), 12, 2, 2)) {
			level.add(new ParticleSpawner((int)x + 8, (int)y + 8, 125, 50, level, Sprite.fireParticle));//125,50
			remove();
		}
		//level.add(new ParticleSpawner((int)x, (int)y, 5, 2, level, Sprite.fireParticle));
		move();
	}
	
	protected void move() { //moves the projectile
		x += nx;
		y += ny;
		if(distance() > range) remove();
	}
	
	private double distance() {
		double dist = 0;
		dist = Math.sqrt(Math.pow(x - xOrigin, 2) + Math.pow(y - yOrigin, 2));
		return dist;
	}
	
	
	public void render(Screen screen) {
		screen.renderProjectile((int)x, (int)y, this);
	}

}