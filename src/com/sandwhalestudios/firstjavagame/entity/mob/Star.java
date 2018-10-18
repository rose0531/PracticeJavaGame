package com.sandwhalestudios.firstjavagame.entity.mob;

import java.util.List;

import com.sandwhalestudios.firstjavagame.entity.mob.Mob.Direction;
import com.sandwhalestudios.firstjavagame.graphics.AnimatedSprite;
import com.sandwhalestudios.firstjavagame.graphics.Screen;
import com.sandwhalestudios.firstjavagame.graphics.Sprite;
import com.sandwhalestudios.firstjavagame.graphics.SpriteSheet;
import com.sandwhalestudios.firstjavagame.level.Node;
import com.sandwhalestudios.firstjavagame.util.Vector2i;

public class Star extends Mob{
	private AnimatedSprite pigExDown = new AnimatedSprite(SpriteSheet.pigExecutionerAnimDown, 32, 32, 8, 7);
	private AnimatedSprite pigExRight = new AnimatedSprite(SpriteSheet.pigExecutionerAnimRight, 32, 32, 8, 7);
	private AnimatedSprite pigExUp = new AnimatedSprite(SpriteSheet.pigExecutionerAnimUp, 32, 32, 8, 7);
	private AnimatedSprite animSprite = pigExDown; //pig executioner
	
	private double xa = 0, ya = 0;
	private boolean flip = false;
	private double speed = 1.8;
	private List<Node> path;
	private int time = 0;
	
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
		
		time++;
		move();
		sprite = animSprite.getSprite();
	}
	
	private void move() {
		xa = 0;
		ya = 0;
		
		int px = (int) level.getPlayerAt(0).getX();
		int py = (int) level.getPlayerAt(0).getY();
		Vector2i start = new Vector2i((int)x >> 4, (int)y >> 4);
		Vector2i goal = new Vector2i(px >> 4, py >> 4);
		if(time % 3 == 0) path = level.findPath(start, goal);
		if(path != null) {
			if(path.size() > 0) {
				Vector2i vec = path.get(path.size() - 1).tile;
				if(x < vec.getX() << 4) xa++;
				if(x > vec.getX() << 4) xa--;
				if(y < vec.getY() << 4) ya++;
				if(y > vec.getY() << 4) ya--;
			}
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
