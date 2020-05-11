package com.example.thread;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

public class PlayerBackup {
	
	private Image image;	//
	private int x, y;		// 좌표
	private int dx, dy; 	// 이동량
	
	public PlayerBackup(Image image) {
		this.image = image;
		x = 50;
		y = (Constants.SCREEN_HEIGHT - image.getHeight(null)) / 2; //화면의 아래위 기준 가운데 위치
	}
	
	public void draw(Graphics g) {
		g.drawImage(image, x, y, null);
	}
	
	public void update() {	// 상태 변경 (여기서는 좌표 이동)
		
		x += dx;
		y += dy;
		
	}

	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode();
		switch (code) {
		case KeyEvent.VK_LEFT: dx = -3; break;
		case KeyEvent.VK_RIGHT: dx = 3; break;
		case KeyEvent.VK_UP: dy = -3; break;
		case KeyEvent.VK_DOWN: dy = 3; break;
			
		}
		
	}

}




