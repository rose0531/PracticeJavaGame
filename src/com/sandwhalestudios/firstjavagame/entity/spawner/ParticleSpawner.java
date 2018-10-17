package com.sandwhalestudios.firstjavagame.entity.spawner;

import com.sandwhalestudios.firstjavagame.entity.particle.Particle;
import com.sandwhalestudios.firstjavagame.entity.spawner.Spawner.Type;
import com.sandwhalestudios.firstjavagame.graphics.Sprite;
import com.sandwhalestudios.firstjavagame.level.Level;

public class ParticleSpawner extends Spawner{
	
	private int life;

	public ParticleSpawner(int x, int y, int life, int amount, Level level, Sprite sprite) {
		super(x, y, Type.PARTICLE, amount, level);
		this.life = life;
		for(int i = 0; i < amount; i++) {
			level.add(new Particle(x, y, life, sprite));
		}
	}
	
}
