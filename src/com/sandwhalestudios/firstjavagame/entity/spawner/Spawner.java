package com.sandwhalestudios.firstjavagame.entity.spawner;

import com.sandwhalestudios.firstjavagame.entity.Entity;
import com.sandwhalestudios.firstjavagame.entity.particle.Particle;
import com.sandwhalestudios.firstjavagame.level.Level;

public abstract class Spawner extends Entity{ //emits entities, such as particles
	
	public enum Type {
		PARTICLE, MOB
	}
	
	private Type type;
	
	public Spawner(int x, int y, Type type, int amount, Level level) {
		init(level);
		this.x = x;
		this.y = y;
		this.type = type;
	}
}
