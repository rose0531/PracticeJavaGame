package com.sandwhalestudios.firstjavagame.entity.mob;

import com.sandwhalestudios.firstjavagame.entity.Entity;
import com.sandwhalestudios.firstjavagame.entity.particle.Particle;
import com.sandwhalestudios.firstjavagame.entity.projectile.MeleeAttack;
import com.sandwhalestudios.firstjavagame.entity.projectile.Projectile;
import com.sandwhalestudios.firstjavagame.entity.projectile.SphereProjectile;
import com.sandwhalestudios.firstjavagame.graphics.Screen;
import com.sandwhalestudios.firstjavagame.graphics.Sprite;

public abstract class Mob extends Entity{
	protected Direction dir;
	protected boolean walking = false;
	//private int anim = 0;
	protected boolean isSolid = true;
	
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
	
	
	//TODO: Change Sprite to Projectile for shoot method
	protected void shoot(double x, double y, double theta) {
		//TODO: add code to animate projectiles
		Projectile p = new SphereProjectile(x, y, theta);
		level.add(p);
		//anim++;
	}
	
	protected void melee(double x, double y, double theta) {
		Projectile p = new MeleeAttack(x, y, theta);
		level.add(p);
		//anim++;
	}
	
	
	protected boolean collision(double xa, double ya) {
		boolean solid = false;
		for(int c = 0; c < 4; c++) {
			double xt = ((x + xa) - c % 2 * 15) / 16;
			double yt = ((y + ya) - c / 2 * 15) / 16;
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if(c % 2 == 0) ix = (int) Math.floor(xt);
			if(c / 2 == 0) iy = (int) Math.floor(yt);
			if(level.getTile(ix, iy).solid()) solid = true;
		}
		return solid;
	}
	
	protected boolean isSolid() {
		return isSolid;
	}
}
