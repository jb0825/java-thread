package com.example.thread;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GameFrame extends JFrame { // JFrame 상속 -> 윈도우 객체 생성 가능
	
	private static final long serialVersionUID = 1L;
	
	public GameFrame() { //생성자
		init();
	}
	private void init() { //초기화 목적 함수
		
		setTitle("테스트 게임");		
		setResizable(false);
		setLocation(50, 50);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //윈도우가 닫히면 프로그램 종료 설정
		
		MainScreen screen = new MainScreen();
		screen.setPreferredSize(
			new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
		screen.setFocusable(true);
		
		add(screen); // --> TheScreen.addNotify() 호출
		
		pack(); //설정에 따라 윈도우의 크기를 조정하는 명령
		
	}
	
	public static void main(String[] args) {
		
		GameFrame window = new GameFrame();
		window.setVisible(true);

	}

}




