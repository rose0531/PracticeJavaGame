package com.sandwhalestudios.firstjavagame.entity.mob;

import java.util.List;

import com.sandwhalestudios.firstjavagame.Game;
import com.sandwhalestudios.firstjavagame.entity.mob.Mob.Direction;
import com.sandwhalestudios.firstjavagame.graphics.AnimatedSprite;
import com.sandwhalestudios.firstjavagame.graphics.Screen;
import com.sandwhalestudios.firstjavagame.graphics.Sprite;
import com.sandwhalestudios.firstjavagame.graphics.SpriteSheet;
import com.sandwhalestudios.firstjavagame.level.Node;
import com.sandwhalestudios.firstjavagame.util.Vector2i;

public class Shooter extends Mob{
	private AnimatedSprite pigExDown = new AnimatedSprite(SpriteSheet.pigExecutionerAnimDown, 32, 32, 8, 7);
	private AnimatedSprite pigExRight = new AnimatedSprite(SpriteSheet.pigExecutionerAnimRight, 32, 32, 8, 7);
	private AnimatedSprite pigExUp = new AnimatedSprite(SpriteSheet.pigExecutionerAnimUp, 32, 32, 8, 7);
	private AnimatedSprite animSprite = pigExDown; //pig executioner
	private Sprite projectileSprite = Sprite.enemyProjectile;
	
	private int fireRate = 0;
	private int time = 0;
	private double xa = 0, ya = 0;
	private List<Node> path;
	private boolean flip = false;
	private int randomMoveTimer = 0;
	
	public Shooter(int x, int y) {
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
		
		List<Player> players = level.getPlayers(this, 200);
		if(players.size() > 0) {
			double min = 0;
			int playerIndex = 0;
			for(int i = 0; i < players.size(); i++) {
				Player p = players.get(i);
				double distance = Vector2i.getDistance(new Vector2i((int)this.getX(), (int)this.getY()), new Vector2i((int)p.getX(), (int)p.getY()));
				if(i == 0 || distance < min) {
					min = distance;
					playerIndex = i;
				}
			}
			shoot(players.get(playerIndex));
			time++;
			fireRate++;
			sprite = animSprite.getSprite();
			move(players.get(playerIndex));
		}else {
			randomMoveTimer++;
			if(randomMoveTimer >= 30) { //changes direction every 2 seconds
				randomMoveTimer = 0;
				xa = random.nextInt(3) - 1;
				ya = random.nextInt(3) - 1;
				if(random.nextInt(3) == 0) {
					xa = 0;
					ya = 0;
				}
			}
			sprite = animSprite.getSprite();
			if(xa != 0 || ya != 0) {
				animSprite.update();
				move(xa, ya);
			}else
				animSprite.setFrame(0);
			
		}
	}
	
	public void shoot(Player player) {
		if(fireRate % 60 == 0) {
			double px = player.getX();
			double py = player.getY();
			double theta = Math.atan2(py - y, px - x); //must put y first then x
			shoot(x, y, theta, projectileSprite);
			fireRate = 1;
		}
	}
	
	
	public void move(Player player) {
		xa = 0;
		ya = 0;
		
		int px = (int) player.getX();
		int py = (int) player.getY();
		Vector2i start = new Vector2i((int)x >> 4, (int)y >> 4);
		Vector2i goal = new Vector2i(px >> 4, py >> 4);
		if(time % 3 == 0) {
			path = level.findPath(start, goal);
			time = 0;
		}
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
		screen.renderMob((int)x, (int)y, this, flip);
	}

}
