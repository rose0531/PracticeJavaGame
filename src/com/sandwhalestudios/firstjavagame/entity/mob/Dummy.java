package com.sandwhalestudios.firstjavagame.entity.mob;

import com.sandwhalestudios.firstjavagame.graphics.Screen;
import com.sandwhalestudios.firstjavagame.graphics.Sprite;
import com.sandwhalestudios.firstjavagame.graphics.SpriteSheet;
import com.sandwhalestudios.firstjavagame.graphics.AnimatedSprite;

public class Dummy extends Mob {
	private AnimatedSprite pigExDown = new AnimatedSprite(SpriteSheet.pigExecutionerAnimDown, 32, 32, 8, 7);
	private AnimatedSprite pigExRight = new AnimatedSprite(SpriteSheet.pigExecutionerAnimRight, 32, 32, 8, 7);
	private AnimatedSprite pigExUp = new AnimatedSprite(SpriteSheet.pigExecutionerAnimUp, 32, 32, 8, 7);
	private AnimatedSprite animSprite = pigExDown; //pig executioner
	
	private int time = 0;
	private int xa = 0;
	private int ya = 0;
	private boolean flip = false;
	private int moveTimer = 30;
	
	public Dummy(int x, int y){
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprite.pig;
	}

	public void update() {
		time++;
		if(time >= moveTimer) { //changes direction every 2 seconds
			moveTimer = random.nextInt(30) + 60;
			xa = random.nextInt(3) - 1;
			ya = random.nextInt(3) - 1;
			if(random.nextInt(3) == 0) {
				xa = 0;
				ya = 0;
			}
			time = 0;
		}
		
		
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
		
		if(xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
			animSprite.update();
		}else {
			walking = false;
			animSprite.setFrame(0);
		}
		
		
		sprite = animSprite.getSprite();
	}

	public void render(Screen screen) {
		screen.renderMob((int)x, (int)y, sprite, flip);
	}
	
}
