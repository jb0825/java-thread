package com.example.thread;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class TheScreen extends JPanel implements Runnable {

	private Image image;
	
	@Override
	public void addNotify() { // 부모 윈도우가 add 할 때 자동으로 호출되는 메서드 (초기화 용도)
		// 비행기 이미지 만들어서 변수에 저장
		try {
			URL url = getClass().getResource("/resources/airplane.png");
			Image originalImage = ImageIO.read(url);
			image = originalImage.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
		} catch (Exception ex) {}
		
		Thread thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void paint(Graphics g) { // 화면 갱신이 필요할 때 자동으로 호출 -> 처리 결과가 화면에 반영
		g.setColor(Color.black); // 그리기 도구의 색상을 검정색으로 설정
		g.fillRect(0, 0, getWidth(), getHeight()); // 미리 설정된 그리기 도구로 가상 화면 칠하기
		if (image != null) {
			g.drawImage(image, x, y, null); //가상 화면에 이미지 그리기
		}
	}
	
	int x = 0, y = 320;
	
	@Override 
	public void run() {
		
		while (true) {
			// 비행기 위치 변경
			x += 3;
			if (x > getWidth()) {
				x = -image.getWidth(null);
			}
			
			// 화면 갱신 처리
			getParent().repaint(); // 부모 윈도우에게 화면 갱신 요청 -> paint() 호출
			
			// 일시 정지
			try {
				Thread.sleep(20); // 0.02초
			} catch (Exception ex) {}
		}
	}
	
}







