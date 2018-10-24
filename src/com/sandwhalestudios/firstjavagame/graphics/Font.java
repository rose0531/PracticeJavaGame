package com.sandwhalestudios.firstjavagame.graphics;

public class Font {
	
	private static SpriteSheet font = new SpriteSheet("/fonts/metal.png", 208, 48);
	public static Sprite[] characters = Sprite.split(font);
	
	public Font() {
		
	}
	
	public void render(Screen screen) {
		screen.renderSprite(50, 50, characters[1], false);
		//screen.renderSheet(50, 50, font, false);
	}
}
