package com.sandwhalestudios.firstjavagame.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener{
	
	private static int mouseX = -1;
	private static int mouseY = -1;
	private static int mouseB = -1;
	private static boolean mouseLeftButton = false;
	private static boolean mouseRightButton = false;
	
	public static int getX() {
		return mouseX;
	}

	public static int getY() {
		return mouseY;
	}
	
	public static int getButton() {
		return mouseB;
	}
	
	public static void setButtonOff() {
		mouseB = -1;
	}
	
	public static boolean isLeftButtonPressed() {
		return mouseLeftButton;
	}
	
	public static boolean isRightButtonPressed() {
		return mouseRightButton;
	}
	
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		mouseB = e.getButton();
		if(mouseB == 1)
			mouseLeftButton = true;
		if(mouseB == 3)
			mouseRightButton = true;
	}

	public void mouseReleased(MouseEvent e) {
		mouseB = -1;
		if(e.getButton() == 1)
			mouseLeftButton = false;
		if(e.getButton() == 3)
			mouseRightButton = false;
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {
		
	}
	
}
