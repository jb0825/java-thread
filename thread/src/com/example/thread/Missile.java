package com.example.thread;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

public class Missile {
	
	private Image image;	//
	private int x, y;		//좌표
	private int dx = 10;
	private boolean active;
	
	public Missile(Image image, int x, int y) {
		this.image = image;
		this.x = x;
		this.y = y; 
		active = true;
	}	
	public void draw(Graphics g) {
		g.drawImage(image, x, y, null);
	}	
	public void update() {		
		x += dx;
//		if (x > Constants.SCREEN_WIDTH) {
//			active = false;
//		}
		active = !( x > Constants.SCREEN_WIDTH );
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}	
	public int getWidth() { 
		return image.getWidth(null);
	}
	public int getHeight() {
		return image.getHeight(null);
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}

	
}
