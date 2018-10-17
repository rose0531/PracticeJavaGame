package com.sandwhalestudios.firstjavagame.level.tile;

import com.sandwhalestudios.firstjavagame.graphics.Screen;
import com.sandwhalestudios.firstjavagame.graphics.Sprite;

public class Tile {
	public Sprite sprite;
	
	public static Tile grass = new GrassTile(Sprite.grass);
	public static Tile voidTile = new VoidTile(Sprite.voidSprite);
	public static Tile rock = new RockTile(Sprite.rock);
	public static Tile lava = new LavaTile(Sprite.lava);
	public static Tile flower = new FlowerTile(Sprite.flower);
	public static Tile water = new WaterTile(Sprite.water);
	
	public static Tile sand = new SandTile(Sprite.sand);
	public static Tile sandPath = new SandPathTile(Sprite.sandPath);
	
	public static Tile castleWallStraight = new CastleWallStraightTile(Sprite.castleWallStraight);
	public static Tile castleWallCorner = new CastleWallCornerTile(Sprite.castleWallCorner);
	public static Tile castleFloor = new CastleFloorTile(Sprite.castleFloor);
	
	// Grass = 0xFF00FF00
	// Flower = 0xFFAA00FF
	// Rock = 0xFF444444
	// Lava = 0xFFFF0000
	public static final int col_spawn_water = 0xFF0000FF;
	public static final int col_spawn_castleWallStraight = 0xFF8D0CFF;
	public static final int col_spawn_castleFloor = 0xFFA59BA0;
	public static final int col_spawn_sand = 0xFFA59D3D;
	public static final int col_spawn_sandPath = 0xFF858687;
	
	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void render(int x, int y, Screen screen) {}
	
	public boolean solid() {
		return false;
	}
}
