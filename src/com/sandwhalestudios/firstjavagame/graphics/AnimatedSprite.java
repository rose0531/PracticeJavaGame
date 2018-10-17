package com.sandwhalestudios.firstjavagame.graphics;

public class AnimatedSprite extends Sprite{
	
	private int frame;
	private Sprite sprite;
	private int rate = 5;
	private int length = -1;
	private int time = 0;
	
	public AnimatedSprite(SpriteSheet sheet, int width, int height, int length, int rate) {
		super(sheet, width, height);
		this.length = length;
		this.rate = rate;
		sprite = sheet.getSprite()[0];
		if(length > sheet.getSprite().length) System.err.println("Error: length of animation is too long");
	}
	
	public void update() {
		time++;
		if(time % rate == 0) {
			if(frame >= length - 1) {
				frame = 0;
				time = 0;
			}
			else frame++;
			
			sprite = sheet.getSprite()[frame];
		}
	}
	
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public int getFrameRate() {
		return rate;
	}
	
	public void restartAnimation() {
		frame = 0;
	}
	
	public void setFrameRate(int frames) {
		rate = frames;
	}
	
	public int getFrame() {
		return frame;
	}
	
	public void setFrame(int frame) {
		this.frame = frame;
		if(this.frame >= length - 1)
			frame = length - 1;
		sprite = sheet.getSprite()[this.frame];
	}
	
	public int length() {
		return length;
	}
}
