package com.sandwhalestudios.firstjavagame.entity.mob;

import java.util.List;

import com.sandwhalestudios.firstjavagame.Game;
import com.sandwhalestudios.firstjavagame.entity.Entity;
import com.sandwhalestudios.firstjavagame.entity.projectile.Projectile;
import com.sandwhalestudios.firstjavagame.entity.projectile.SoundWaveProjectile;
import com.sandwhalestudios.firstjavagame.graphics.AnimatedSprite;
import com.sandwhalestudios.firstjavagame.graphics.Screen;
import com.sandwhalestudios.firstjavagame.graphics.Sprite;
import com.sandwhalestudios.firstjavagame.graphics.SpriteSheet;
import com.sandwhalestudios.firstjavagame.input.Keyboard;
import com.sandwhalestudios.firstjavagame.input.Mouse;

public class Player extends Mob{
	
	private static final int ANIM_SPEED = 60;
	private static final float ANIM_RATIO = 0.125f;
	private static final int DODGE_RATE = 50;
	
	private Keyboard input;
	private Sprite sprite;
	private int anim = 0;
	private boolean dodging = false;
	private boolean dodgeCooldown = false;
	private boolean shooting = false;
	private double prevX = 0, prevY = 0;
	private int fireRate = 0;
	private int dodgeTimer = 0;
	private double speed = 2.5;
	
	//Animated Player Sprites
	private AnimatedSprite playerAnimDown = new AnimatedSprite(SpriteSheet.playerAnimDown, 32, 32, 8, 8);
	private AnimatedSprite playerAnimRight = new AnimatedSprite(SpriteSheet.playerAnimRight, 32, 32, 8, 8);
	private AnimatedSprite playerAnimUp = new AnimatedSprite(SpriteSheet.playerAnimUp, 32, 32, 8, 8);
	private AnimatedSprite playerAnimFrontBob = new AnimatedSprite(SpriteSheet.playerAnimFrontBob, 32, 32, 4, 10);
	private AnimatedSprite playerAnimRightBob = new AnimatedSprite(SpriteSheet.playerAnimRightBob, 32, 32, 4, 10);
	private AnimatedSprite playerAnimUpBob = new AnimatedSprite(SpriteSheet.playerAnimUpBob, 32, 32, 4, 10);
	private AnimatedSprite playerAnimRollUp = new AnimatedSprite(SpriteSheet.playerAnimRollUp, 32, 32, 6, 6);
	private AnimatedSprite playerAnimRollRight = new AnimatedSprite(SpriteSheet.playerAnimRollRight, 32, 32, 6, 6);
	private AnimatedSprite playerAnimRollDown = new AnimatedSprite(SpriteSheet.playerAnimRollDown, 32, 32, 6, 6);

	private AnimatedSprite animSprite = playerAnimFrontBob;
	
	public Player(Keyboard input) {
		this.input = input;
		dir = Direction.DOWN;
		//sprite = Sprite.player32FrontStill;
	}
	
	public Player(int x, int y, Keyboard input) {
		this.x = x;
		this.y = y;
		this.input = input;
		dir = Direction.DOWN;
		//sprite = Sprite.player32FrontStill;
		fireRate = SoundWaveProjectile.FIRE_RATE;
	}
	
	public void update() {
		animSprite.update();
		if(fireRate > 0) fireRate--;
		if(anim < 7500) anim ++;
		else anim = 0;
		if(Mouse.getButton() == 3 && !dodgeCooldown) {
			dodging = true;
			walking = false;
		}
		if(Mouse.isLeftButtonPressed())
			shooting = true;
		else
			shooting = false;
		
		
		
		//complicated dodging mechanism
		//TODO: functionize dodging
		if(dodging) {
			dodgeTimer++;
			if(dodgeTimer >= animSprite.length() * 5) {
				dodging = false;
				dodgeCooldown = true;
				animSprite.restartAnimation();
			}else {
				if(prevX < 0 && prevY > 0)
					move(prevX - 1, prevY + 1);
				else if(prevX < 0 && prevY < 0)
					move(prevX - 1, prevY - 1);
				else if(prevX > 0 && prevY > 0)
					move(prevX + 1, prevY + 1);
				else if(prevX > 0 && prevY < 0) {
					move(prevX + 1, prevY - 1);
				}else if(prevX == 0 && prevY == 0){
					dodging = false;
					dodgeTimer = 0;
				}else
					move(prevX * 2, prevY * 2);
			}
			return;
		}else if(dodgeCooldown) {
			if(dodgeTimer < 40) {
				dodgeTimer++;
			}else {
				dodgeCooldown = false;
				dodgeTimer = 0;
			}
		}
		
		double xa = 0, ya = 0;
		

		if(input.up) {
			ya -= speed;
			animSprite = playerAnimUp;
		}
		if(input.down) {
			ya += speed;
			animSprite = playerAnimDown;
		}
		if(input.left) {
			xa -= speed;
			animSprite = playerAnimRight;
		}
		if(input.right) {
			xa += speed;
			animSprite = playerAnimRight;
		}
		

		
		if(xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		}else {
			walking = false;
		}
		
		prevX = xa;
		prevY = ya;
		clear();
		updateShooting();
	}
	
	
	private void clear() {
		for(int i = 0; i < level.getProjectiles().size(); i++) {
			Projectile p = level.getProjectiles().get(i);
			if(p.isRemoved())
				level.getProjectiles().remove(i);
		}
	}
	
	
	private void updateShooting() {
		if(shooting && fireRate <= 0) {
			double dx = Mouse.getX() - Game.getWindowWidth() / 2;
			double dy = Mouse.getY() - Game.getWindowHeight() / 2;
			double theta = Math.atan2(dy, dx); //must put y first then x
			shoot(x, y, theta);
			fireRate = SoundWaveProjectile.FIRE_RATE;
		}
	}

	public void render(Screen screen) {
		boolean flip = false;
		if(walking) {
			if(dir == Direction.LEFT)
				flip = true;
		}else if(dodging) {
			if(dir == Direction.UP)
				animSprite = playerAnimRollUp;
			else if(dir == Direction.RIGHT || dir == Direction.LEFT) {
				animSprite = playerAnimRollRight;
				if(dir == Direction.LEFT)
					flip = true;
			}else
				animSprite = playerAnimRollDown;
		}else{
			if(dir == Direction.DOWN) {
				animSprite = playerAnimFrontBob;
			}else if(dir == Direction.RIGHT || dir == Direction.LEFT){
				animSprite = playerAnimRightBob;
				if(dir == Direction.LEFT)
					flip = true;
			}else{
				animSprite = playerAnimUpBob;
			}
		}
		
		sprite = animSprite.getSprite();
		screen.renderMob((int)x, (int)y, sprite, flip);
	}
}
