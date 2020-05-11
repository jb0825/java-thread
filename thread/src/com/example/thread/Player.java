package com.example.thread;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

public class Player {
	
	private Image image;	//
	private int x, y;		//좌표
	private int dx, dy;		//이동량
	
	public Player(Image image) {
		this.image = image;
		x = 50;
		y = (Constants.SCREEN_HEIGHT - image.getHeight(null)) / 2; 
	}
	
	public void draw(Graphics g) {
		g.drawImage(image, x, y, null);
	}
	
	public void update() {
		
		if (x + dx >= 0 && x + dx <= Constants.SCREEN_WIDTH - getWidth()) {		
			x += dx;
		}
		if (y + dy >= 0 && y + dy <= Constants.SCREEN_HEIGHT - getHeight()) {
			y += dy;
		}
		
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

	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();//눌려진 키 코드 반환
		switch (code) {
		case KeyEvent.VK_LEFT:
			dx = -5;
			break;
		case KeyEvent.VK_RIGHT:
			dx = 5;
			break;
		case KeyEvent.VK_UP:
			dy = -5;
			break;
		case KeyEvent.VK_DOWN:
			dy = 5;
			break;
		}
		
		
	}

	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		switch (code) {
		case KeyEvent.VK_LEFT :
		case KeyEvent.VK_RIGHT :
			dx = 0;
			break;
		case KeyEvent.VK_UP :
		case KeyEvent.VK_DOWN :
			dy = 0;
			break;
		}
		
		
	}

	
	
}
