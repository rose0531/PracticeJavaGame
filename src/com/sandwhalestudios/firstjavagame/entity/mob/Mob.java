package com.sandwhalestudios.firstjavagame.entity.mob;

import com.sandwhalestudios.firstjavagame.entity.Entity;
import com.sandwhalestudios.firstjavagame.entity.particle.Particle;
import com.sandwhalestudios.firstjavagame.entity.projectile.Projectile;
import com.sandwhalestudios.firstjavagame.entity.projectile.SoundWaveProjectile;
import com.sandwhalestudios.firstjavagame.graphics.Screen;
import com.sandwhalestudios.firstjavagame.graphics.Sprite;

public abstract class Mob extends Entity{
	protected Direction dir;
	protected boolean walking = false;
	private int anim = 0;
	
	protected enum Direction{
		UP, DOWN, LEFT, RIGHT
	}
	
	
	public void move(double xa, double ya) {
		if(xa != 0 && ya != 0) {
			move(0, ya);
			move(xa, 0);
			return;
		}
		
		if(ya < 0 && xa == 0) dir = Direction.UP; //N
		if(xa > 0 && ya == 0) dir = Direction.RIGHT; //E
		if(ya > 0 && xa == 0) dir = Direction.DOWN; //S
		if(xa < 0 && ya == 0) dir = Direction.LEFT; //W
		
		while(xa != 0) {
			if(Math.abs(xa) > 1) {
				if(!collision(abs(xa), ya))
					this.x += abs(xa);
				xa -= abs(xa);
			}else {
				if(!collision(abs(xa), ya))
					this.x += xa;
				xa = 0;
			}
		}
		while(ya != 0) {
			if(Math.abs(ya) > 1) {
				if(!collision(xa, abs(ya)))
					this.y += abs(ya);
				ya -= abs(ya);
			}else {
				if(!collision(xa, abs(ya)))
					this.y += ya;
				ya = 0;
			}
		}	
	}
	
	private int abs(double value) {
		if(value < 0) return -1;
		return 1;
	}
	
	public abstract void update();
	
	public abstract void render(Screen screen);
	
	protected void shoot(double x, double y, double theta) {
		if(anim >= 8) anim = 0;
		Projectile p = new SoundWaveProjectile(x, y, theta);
		if(anim == 0)
			p.setSprite(Sprite.ironMaidenNote1);
		else if(anim == 1)
			p.setSprite(Sprite.ironMaidenNote1);
		else if(anim == 2)
			p.setSprite(Sprite.ironMaidenNote1);
		else if(anim == 3)
			p.setSprite(Sprite.ironMaidenNote1);
		else if(anim == 4)
			p.setSprite(Sprite.ironMaidenNote1);
		else if(anim == 5)
			p.setSprite(Sprite.ironMaidenNote1);
		else if(anim == 6)
			p.setSprite(Sprite.ironMaidenNote1);
		else
			p.setSprite(Sprite.ironMaidenNote1);
		level.add(p);
		anim++;
	}
	
	
	protected boolean collision(double xa, double ya) {
		boolean solid = false;
		double xt = (x + xa - 8) / 16;	//x is Mob's x coordinate 528, 352 8,14
		double yt = (y + ya - 2) / 16;	//y is Mob's y coordinate
		int ix = (int) Math.ceil(xt);
		int iy = (int) Math.ceil(yt);
		if(level.getTile(ix, iy).solid()) solid = true;
		if(level.getTile(ix - 1, iy).solid()) solid = true;
		if(level.getTile(ix, iy - 1).solid()) solid = true;
		if(level.getTile(ix - 1, iy - 1).solid()) solid = true;
		return solid;
	}
}
