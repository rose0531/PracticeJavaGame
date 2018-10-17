package com.sandwhalestudios.firstjavagame.level.tile;

import com.sandwhalestudios.firstjavagame.graphics.Screen;
import com.sandwhalestudios.firstjavagame.graphics.Sprite;

public class GrassTile extends Tile{

	public GrassTile(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}
}
