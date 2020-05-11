package com.example.thread;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MainScreenBackup extends JPanel implements Runnable {

	private Player player;
	
	public MainScreenBackup() { 
		// 비행기 이미지 만들어서 변수에 저장
		try {
			URL url = getClass().getResource("/resources/airplane.png");
			Image originalImage = ImageIO.read(url);
			Image scaledImage = originalImage.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
			player = new Player(scaledImage);
		} catch (Exception ex) {}

		addKeyListener(keyListener); // 키보드 이벤트 처리기 등록
		
		Thread thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void paint(Graphics g) { // 화면 갱신이 필요할 때 자동으로 호출 -> 처리 결과가 화면에 반영
		g.setColor(Color.black); // 그리기 도구의 색상을 검정색으로 설정
		g.fillRect(0, 0, getWidth(), getHeight()); // 미리 설정된 그리기 도구로 가상 화면 칠하기
		
		player.draw(g);
	}
		
	@Override 
	public void run() {
		
		while (true) {
			// 비행기 위치 변경
			player.update();
			
			// 화면 갱신 처리
			getParent().repaint(); // 화면 갱신 요청 -> paint() 호출
			
			// 일시 정지
			try {
				Thread.sleep(20); // 0.02초
			} catch (Exception ex) {}
		}
	}
	
	///////////////////////////////////////////////////////////
	
//	private KeyListener keyListener = new KeyListener() {
//		@Override
//		public void keyTyped(KeyEvent e) {}
//		@Override
//		public void keyReleased(KeyEvent e) {}
//		@Override
//		public void keyPressed(KeyEvent e) {}
//	};
	
	private KeyListener keyListener = new KeyAdapter() {
		
		@Override
		public void keyPressed(KeyEvent e) {

			player.keyPressed(e);
			
		}
		
	};
			
	
}




















