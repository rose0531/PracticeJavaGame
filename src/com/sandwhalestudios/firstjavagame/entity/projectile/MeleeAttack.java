package com.sandwhalestudios.firstjavagame.entity.projectile;

import com.sandwhalestudios.firstjavagame.entity.spawner.ParticleSpawner;
import com.sandwhalestudios.firstjavagame.graphics.Screen;
import com.sandwhalestudios.firstjavagame.graphics.Sprite;

public class MeleeAttack extends Projectile{
	public MeleeAttack(double x, double y, double dir) {
		super(x, y, dir);
		range = 16;
		speed = 4.0f;
		damage = 20;
		fireRateMultiplier = 1;
		sprite = Sprite.rotate(Sprite.melee, angle);
		nx = Math.cos(angle);
		ny = Math.sin(angle);
	}
	
	public void update() {
		if(level.tileCollision((int)(x + range * nx), (int)(y + range * ny), 12, 2, 2)) {
			//TODO: add new particle effect for melee attacks against solid objects
			level.add(new ParticleSpawner((int)(x + range * nx), (int)(y + range * ny), 125, 50, level, Sprite.fireParticle));//125,50
		}
		
		x += range * nx;
		y += range * ny;
		
		remove();
	}
	
	public void render(Screen screen) {
		screen.renderProjectile((int)(x + range * nx) - 8, (int)(y + range * ny) - 8, this);
	}
}
