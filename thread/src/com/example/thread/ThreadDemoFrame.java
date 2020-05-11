package com.example.thread;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ThreadDemoFrame extends JFrame { // JFrame 상속 -> 윈도우 객체 생성 가능
	
	public ThreadDemoFrame() { //생성자
		init();
	}
	private void init() { //초기화 목적 함수
		
		setTitle("쓰레드 테스트");
		setSize(300, 500);
		setLocation(300, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //윈도우가 닫히면 프로그램 종료 설정
		setLayout(null);
		
		//1. 동기 방식 호출
		JButton button1 = new JButton("동기 방식 호출");
		button1.setSize(265, 50);
		button1.setLocation(10, 10);
		//addActionListener : button을 클릭하면 호출할 메서드를 등록
		button1.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("1.");
				Worker1 worker = new Worker1();
				worker.work();
				System.out.println("4.");
			}
		});		
		add(button1); // 윈도우에 버튼을 추가 (붙이기)
		
		//2. 비동기 방식 호출
		JButton button2 = new JButton("비동기 방식 호출 1");
		button2.setSize(265, 50);
		button2.setLocation(10, 70);
		//addActionListener : button을 클릭하면 호출할 메서드를 등록
		button2.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("1.");
				Worker2 worker = new Worker2();
				// worker.work(); //동기 호출
				worker.start(); // 비동기 호출 : 새 쓰레드를 만들고 run메서드를 비동기로 호출
				System.out.println("4.");
			}
		});		
		add(button2); // 윈도우에 버튼을 추가 (붙이기)
		
		//3. 비동기 방식 호출 2
		JButton button3 = new JButton("비동기 방식 호출 2");
		button3.setSize(265, 50);
		button3.setLocation(10, 130);
		//addActionListener : button을 클릭하면 호출할 메서드를 등록
		button3.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("1.");
				Thread worker = new Thread(new Worker3());
				// worker.work(); //동기 호출
				worker.start(); // 비동기 호출 : 새 쓰레드를 만들고 run메서드를 비동기로 호출
				System.out.println("4.");
			}
		});		
		add(button3); // 윈도우에 버튼을 추가 (붙이기)
		
		//4. 비동기 처리의 문제점 : 공유 자웤 경쟁 문제
		JButton button4 = new JButton("공유 자원 경쟁 문제");
		button4.setSize(265, 50);
		button4.setLocation(10, 190);
		//addActionListener : button을 클릭하면 호출할 메서드를 등록
		button4.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("1.");
				
				Thread worker = new Thread(new Worker4());
				worker.start();
				
				Thread worker2 = new Thread(new Worker4());
				worker2.start();
				
				System.out.println("4.");
			}
		});		
		add(button4); // 윈도우에 버튼을 추가 (붙이기)
		
		//5. 비동기 처리의 문제점 : 공유 자웤 경쟁 문제 해결 - 쓰레드 동기화
		JButton button5 = new JButton("쓰레드 동기화 1");
		button5.setSize(265, 50);
		button5.setLocation(10, 250);
		//addActionListener : button을 클릭하면 호출할 메서드를 등록
		button5.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("1.");
				
				Thread worker = new Thread(new Worker5());
				worker.start();
				
				Thread worker2 = new Thread(new Worker5());
				worker2.start();
				
				System.out.println("4.");
			}
		});		
		add(button5); // 윈도우에 버튼을 추가 (붙이기)
		
		//6. 비동기 처리의 문제점 : 공유 자웤 경쟁 문제 해결 - 쓰레드 동기화 2
		JButton button6 = new JButton("쓰레드 동기화 2");
		button6.setSize(265, 50);
		button6.setLocation(10, 310);
		button6.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("1.");
				
				Thread worker = new Thread(new Worker6());
				worker.start();
				
				Thread worker2 = new Thread(new Worker6());
				worker2.start();
				
				System.out.println("4.");
			}
		});		
		add(button6); // 윈도우에 버튼을 추가 (붙이기)
		
		
	}
	
	
	

	public static void main(String[] args) {
		
		ThreadDemoFrame window = new ThreadDemoFrame();
		window.setVisible(true);

	}

}


class Worker1 {
	public void work() {
		System.out.println("2.");
		try {
			Thread.sleep(10 * 1000);//10초간 실행 중지
		} catch (InterruptedException e) { } 
		System.out.println("3.");
	}
}
class Worker2 extends Thread { // Thread 상속 -> 새 쓰레드에서 실행되는 객체 생성 가능
	@Override
	public void run() { //쓰레드가 만들어지고 비동기로 호출될 약속된 메서드
		work();
	}
	
	public void work() {
		System.out.println("2.");
		try {
			Thread.sleep(10 * 1000);//10초간 실행 중지
		} catch (InterruptedException e) { } 
		System.out.println("3.");
	}
}
class Worker3 implements Runnable { // Runnable 구현 -> 새 쓰레드에서 실행되는 객체 생성 가능
	@Override
	public void run() { //쓰레드가 만들어지고 비동기로 호출될 약속된 메서드
		work();
	}
	
	public void work() {
		System.out.println("2.");
		try {
			Thread.sleep(10 * 1000);//10초간 실행 중지
		} catch (InterruptedException e) { } 
		System.out.println("3.");
	}
}

class Worker4 implements Runnable { 
	@Override
	public void run() {
		work();
	}	
	
	private static int sum = 0;
	public void work() {
		System.out.println("2.");
		sum = 0;
		
		for (int i = 0; i <= 100; i++) {
			sum += i;
			
			if (i % 10 == 0) { // 10번으로 나눠서 1초간 실행 중지
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) { } 
			}
		}
		
		System.out.println("RESULT : " + sum);
		System.out.println("3.");
	}
}

class Worker5 implements Runnable { 
	@Override
	public void run() {
		work();
	}	
	
	private static int sum = 0;
	private static Object key = new Object();
	
	public void work() {
		synchronized (key) { // key 객체를 요청하고 획득하면 잠그기 (쓰레드를 동시에 실행하지 않고 작업이 끝날때까지 멈춤)
			
			System.out.println("2.");
			sum = 0;
			
			for (int i = 0; i <= 100; i++) {
				sum += i;
				
				if (i % 10 == 0) { // 10번으로 나눠서 1초간 실행 중지
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) { } 
				}
			}
			
			System.out.println("RESULT : " + sum);
			System.out.println("3.");
			
		} // 획득한 key 를 반환

	}
}

class Worker6 implements Runnable { 
	@Override
	public void run() {
		work();
	}	
	
	private static int sum = 0;
	
	// 이 메서드는 서로 다른 쓰레드에서 동시에 호출될 수 없다
	synchronized public static void work() {
		
		System.out.println("2.");
		sum = 0;
		
		for (int i = 0; i <= 100; i++) {
			sum += i;
			
			if (i % 10 == 0) { // 10번으로 나눠서 1초간 실행 중지
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) { } 
			}
		}
		
		System.out.println("RESULT : " + sum);
		System.out.println("3.");
	
	}
}




