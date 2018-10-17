package com.sandwhalestudios.firstjavagame.entity.mob;

import java.util.List;

import com.sandwhalestudios.firstjavagame.entity.mob.Mob.Direction;
import com.sandwhalestudios.firstjavagame.graphics.AnimatedSprite;
import com.sandwhalestudios.firstjavagame.graphics.Screen;
import com.sandwhalestudios.firstjavagame.graphics.Sprite;
import com.sandwhalestudios.firstjavagame.graphics.SpriteSheet;

public class Star extends Mob{
	private AnimatedSprite pigExDown = new AnimatedSprite(SpriteSheet.pigExecutionerAnimDown, 32, 32, 8, 7);
	private AnimatedSprite pigExRight = new AnimatedSprite(SpriteSheet.pigExecutionerAnimRight, 32, 32, 8, 7);
	private AnimatedSprite pigExUp = new AnimatedSprite(SpriteSheet.pigExecutionerAnimUp, 32, 32, 8, 7);
	private AnimatedSprite animSprite = pigExDown; //pig executioner
	
	private double xa = 0, ya = 0;
	private boolean flip = false;
	private double speed = 1.8;
	
	public Star(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprite.pig;
	}

	public void update() {
		if(ya < 0) {
			animSprite = pigExUp;
			dir = Direction.UP;
			flip = false;
		}
		if(ya > 0) {
			animSprite = pigExDown;
			dir = Direction.DOWN;
			flip = false;
		}
		if(xa < 0) {
			animSprite = pigExRight;
			dir = Direction.LEFT;
			flip = true;
		}
		if(xa > 0) {
			animSprite = pigExRight;
			dir = Direction.RIGHT;
			flip = false;
		}
		
		move();
		sprite = animSprite.getSprite();
	}
	
	private void move() {
		xa = 0;
		ya = 0;
		
		List<Player> players = level.getPlayers(this, 50);
		if(!players.isEmpty()) {
			Player player = players.get(0);
			if(x < player.getX()) xa += speed;
			if(x > player.getX()) xa -= speed;
			if(y < player.getY()) ya += speed;
			if(y > player.getY()) ya -= speed;
		}
		if(xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
			animSprite.update();
		}else {
			walking = false;
			animSprite.setFrame(0);
		}
	}

	public void render(Screen screen) {
		screen.renderMob((int)x, (int)y, sprite, flip);
	}
}
