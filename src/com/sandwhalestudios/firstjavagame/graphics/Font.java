package com.sandwhalestudios.firstjavagame.graphics;

public class Font {
	
	private static SpriteSheet font = new SpriteSheet("/fonts/metal.png", 208, 48);
	private static SpriteSheet font_cropped = new SpriteSheet(font, 0, 0, 13, 5, 16);
	public static Sprite[] characters = Sprite.split(font_cropped);
	
	private static String charIndex = "ABCDEFGHIJKLM" + //
									  "NOPQRSTUVWXYZ" + //
									  "!\"#$%&\'()*+,-" + //
									  "./0123456789:" +//
									  ";<=>?";
	
	public Font() {
		
	}
	
	public void render(int x, int y, Screen screen, String string) {
		String s = string.toUpperCase();
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			int index = charIndex.indexOf(c);
			if(index >= 0)
				screen.renderSprite(x + i * 16, y, characters[index], false);
		}
	}
}
