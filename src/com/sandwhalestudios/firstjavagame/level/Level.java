package com.sandwhalestudios.firstjavagame.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.sandwhalestudios.firstjavagame.entity.Entity;
import com.sandwhalestudios.firstjavagame.entity.mob.Player;
import com.sandwhalestudios.firstjavagame.entity.particle.Particle;
import com.sandwhalestudios.firstjavagame.entity.projectile.Projectile;
import com.sandwhalestudios.firstjavagame.graphics.Screen;
import com.sandwhalestudios.firstjavagame.level.tile.Tile;
import com.sandwhalestudios.firstjavagame.util.Vector2i;

/*Level Class keeps track of everything in the level
 * -> Players, Enemies, Projectiles, Entities*/

public class Level {
	
	public int width, height;
	protected int[] tilesInt;
	protected int[] tiles;
	
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Particle> particles = new ArrayList<Particle>();	
	private List<Player> players = new ArrayList<Player>();
	
	public static Level spawn = new SpawnLevel("/textures/levels/SpawnLevel.png");
	
	private Comparator<Node> nodeSorter = new Comparator<Node>() {
		public int compare(Node n0, Node n1) {
			if(n1.fCost < n0.fCost) return 1;
			if(n1.fCost > n0.fCost) return -1;
			return 0;
		}		
	};
	
	
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		tilesInt = new int[width * height];
		generateLevel();
	}
	
	public Level(String path) {
		loadLevel(path);
		generateLevel();
	}
	
	protected void generateLevel() {
		
	}
	
	protected void loadLevel(String path) {
		
	}
	
	public void update() {
		for(int i = 0; i < entities.size(); i++)
			entities.get(i).update();
		for(int i = 0; i < projectiles.size(); i++)
			projectiles.get(i).update();
		for(int i = 0; i < particles.size(); i++)
			particles.get(i).update();
		for(int i = 0; i < players.size(); i++)
			players.get(i).update();
		remove();
	}
	
	private void remove() {
		for(int i = 0; i < entities.size(); i++)
			if(entities.get(i).isRemoved()) entities.remove(i);
		for(int i = 0; i < projectiles.size(); i++)
			if(projectiles.get(i).isRemoved()) projectiles.remove(i);
		for(int i = 0; i < particles.size(); i++)
			if(particles.get(i).isRemoved()) particles.remove(i);
		for(int i = 0; i < players.size(); i++)
			if(players.get(i).isRemoved()) players.remove(i);
	}
	
	public List<Projectile> getProjectiles(){
		return projectiles;
	}
	
	private void time() {
		
	}
	
	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		int x0 = xScroll >> 4;
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4;
		
		for(int y = y0; y < y1; y++) {
			for(int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, screen);
			}
		}
		for(int i = 0; i < entities.size(); i++)
			entities.get(i).render(screen);
		for(int i = 0; i < projectiles.size(); i++)
			projectiles.get(i).render(screen);
		for(int i = 0; i < particles.size(); i++)
			particles.get(i).render(screen);
		for(int i = 0; i < players.size(); i++)
			players.get(i).render(screen);
	}
	
	public void add(Entity e) {
		e.init(this);
		if(e instanceof Particle) {
			particles.add((Particle)e);
		}else if(e instanceof Projectile) {
			projectiles.add((Projectile)e);
		}else if(e instanceof Player){
			players.add((Player)e);
		}else {
			entities.add(e);
		}
	}
	
	public List<Player> getPlayer() {
		return players;
	}
	
	public Player getPlayerAt(int index) {
		return players.get(index);
	}
	
	public Player getClientPlayer() {
		return players.get(0);
	}
	
	public List<Entity> getEntities(Entity e, int radius){
		List<Entity> result = new ArrayList<Entity>();
		int ex = (int)e.getX();
		int ey = (int)e.getY();
		for(int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			int x = (int)entity.getX();
			int y = (int)entity.getY();
			
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if(distance <= radius)
				result.add(entity);
		}
		return result;
	}
	
	public List<Player> getPlayers(Entity e, int radius){
		List<Player> result = new ArrayList<Player>();
		int ex = (int)e.getX();
		int ey = (int)e.getY();
		for(int i = 0; i < players.size(); i++) {
			Player p = players.get(i);
			int x = (int)p.getX();
			int y = (int)p.getY();
			
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if(distance <= radius)
				result.add(p);
		}
		return result;
	}
	
	public Tile getTile(int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;
		if(tiles[x + y * width] == 0xFF00FF00) return Tile.grass;
		if(tiles[x + y * width] == 0xFF444444) return Tile.rock;
		if(tiles[x + y * width] == 0xFFAA00FF) return Tile.flower;
		if(tiles[x + y * width] == 0xFFFF0000) return Tile.lava;
		if(tiles[x + y * width] == Tile.col_spawn_water) return Tile.water;
		if(tiles[x + y * width] == Tile.col_spawn_castleWallStraight) return Tile.castleWallStraight;
		if(tiles[x + y * width] == Tile.col_spawn_castleFloor) return Tile.castleFloor;
		if(tiles[x + y * width] == Tile.col_spawn_sand) return Tile.sand;
		if(tiles[x + y * width] == Tile.col_spawn_sandPath) return Tile.sandPath;
		return Tile.voidTile;
	}
	
	public boolean tileCollision(int x, int y, int size, int xOffset, int yOffset) {
		boolean solid = false;
		for(int i = 0; i < 4; i++) {
			//x and y are always the top left corner of the projectile sprite
			int xt = (x + i % 2 * size + xOffset) >> 4;
			int yt = (y + i / 2 * size + yOffset) >> 4;
			if(getTile(xt, yt).solid()) solid = true;
		}
		return solid;
	}
	
	public List<Node> findPath(Vector2i start, Vector2i goal){
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		Node current = new Node(start, null, 0, getDistance(start, goal));
		openList.add(current);
		while(openList.size() > 0) {
			Collections.sort(openList, nodeSorter);
			current = openList.get(0);
			if(current.tile.equals(goal)) {
				List<Node> path = new ArrayList<Node>();
				while(current.parent != null) {
					path.add(current);
					current = current.parent;
				}
				openList.clear();
				closedList.clear();
				return path;
			}
			openList.remove(current);
			closedList.add(current);
			for(int i = 0; i < 9; i++) {
				if(i == 4) continue;
				int x = current.tile.getX();
				int y = current.tile.getY();
				int xi = (i % 3) - 1;
				int yi = (i / 3) - 1;
				Tile at = getTile(x + xi, y + yi);
				if(at == null) continue;
				if(at.solid()) continue;
				Vector2i a = new Vector2i(x + xi, y + yi);
				double gCost = current.gCost + (getDistance(current.tile, a) > 1 ? 0.95 : 1);
				double hCost = getDistance(a, goal);
				Node node = new Node(a, current, gCost, hCost);
				if(vectorInList(closedList, a) && gCost >= current.gCost) continue;
				if(!vectorInList(openList, a) || gCost < current.gCost)
					openList.add(node);
			}
		}
		closedList.clear();
		return null;
	}
	
	private boolean vectorInList(List<Node> list, Vector2i vector) {
		for(int i = 0; i < list.size(); i++)
			if(list.get(i).tile.equals(vector)) return true;
		return false;
	}
	
	private double getDistance(Vector2i start, Vector2i goal) {
		double dx = start.getX() - goal.getX();
		double dy = start.getY() - goal.getY();
		return Math.sqrt((dx * dx) + (dy * dy));
	}
}
