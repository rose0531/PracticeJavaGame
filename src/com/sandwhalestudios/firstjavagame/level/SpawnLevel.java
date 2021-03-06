package com.sandwhalestudios.firstjavagame.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sandwhalestudios.firstjavagame.entity.mob.Chaser;
import com.sandwhalestudios.firstjavagame.entity.mob.Dummy;
import com.sandwhalestudios.firstjavagame.entity.mob.Shooter;
import com.sandwhalestudios.firstjavagame.entity.mob.Star;
import com.sandwhalestudios.firstjavagame.level.tile.Tile;

public class SpawnLevel extends Level{

	public SpawnLevel(String path) {
		super(path);
	}
	
	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			width = image.getWidth();
			height = image.getHeight();
			tiles = new int[width * height];
			image.getRGB(0, 0, width, height, tiles, 0, width);
		}catch(IOException e) {
			e.printStackTrace();
			System.out.println("Exception: Could not load level file!");
		}
		//add(new Star(45, 22));
		//add(new Star(50, 22));
		add(new Shooter(50, 22));
	}	
	
	protected void generateLevel() {
		
	}
}
