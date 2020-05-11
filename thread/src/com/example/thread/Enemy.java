package com.example.thread;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

public class Enemy {
	
	private Image image;	//
	private int x, y;		//좌표
	private int dx, dy;		//이동량
	private boolean active;
	
	public Enemy(Image image) {
		this.image = image;
		this.x = Constants.SCREEN_WIDTH - image.getWidth(null);
		this.y = (int)(Math.random() * (Constants.SCREEN_HEIGHT - image.getHeight(null)));
		
		this.dx = -((int)(Math.random() * 10) + 5);
		//this.dy = ((int)(Math.random() * 10) - 5);
		
		active = true;
	}	
	public void draw(Graphics g) {
		g.drawImage(image, x, y, null);
	}	
	public void update() {		
		x += dx;
		y += dy;
		
//		if (x < -getWidth() || y < -getHeight() || y > Constants.SCREEN_HEIGHT) {
//			active = false;
//		}
		
		active = !(x < getWidth() || y < -getHeight() || y > Constants.SCREEN_HEIGHT);
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
