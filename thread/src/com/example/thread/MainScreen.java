package com.example.thread;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MainScreen extends JPanel implements Runnable {
		
	private Player player; //Image image;
	
	private Image missileImage;
	private LinkedList<Missile> missileList;
	
	private Image[] enemyImages;
	private LinkedList<Enemy> enemyList;
	
	private Rectangle mr, er, pr;//각 오브젝트의 충돌 영역을 정의하는 사각형
	
	private boolean running; //쓰레드의 상태 관리하는 변수
	
	public MainScreen() {
		missileList = new LinkedList<>();
		enemyList = new LinkedList<>();
		mr = new Rectangle();
		er = new Rectangle();
		pr = new Rectangle();
		try {
			URL url = getClass().getResource("/resources/airplane.png");
			Image originalImage = ImageIO.read(url);
			Image scaledimage = originalImage.getScaledInstance(64, 64, Image.SCALE_SMOOTH);			
			player = new Player(scaledimage);
			
			url = getClass().getResource("/resources/missile2.png");
			originalImage = ImageIO.read(url);
			missileImage = originalImage.getScaledInstance(32, 16, Image.SCALE_SMOOTH);
			
			String[] files = { "enemy1.jpg", "enemy2.png", "enemy3.png" };
			enemyImages = new Image[3];			
			for (int i = 0; i < files.length; i++) {
				url = getClass().getResource("/resources/" + files[i]);
				originalImage = ImageIO.read(url);
				enemyImages[i] = 
						originalImage.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		addKeyListener(keyListener);
		
		Thread thread = new Thread(this);
		thread.start();
	}	
	
	@Override
	public void paint(Graphics g) {
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		if (running) {
			player.draw(g);

			synchronized (player) {
				for (Missile missile : missileList) {
					missile.draw(g);
				}
				for (Enemy enemy : enemyList) {
					enemy.draw(g);
				}
			}

		}
	}	
	
	@Override
	public void run() {
		
		final long interval = 20;
		long enemyCreationInterval = 500;
		long prevEnemyCreationTime = 0;
		long prevUpdateTime = 0;
		long currentTime = 0;
		
		running = true;
		
		outer: while (true) {
			
			currentTime = System.currentTimeMillis();
			
			//enemy 만들기
			if (currentTime >= prevEnemyCreationTime + enemyCreationInterval) {
				prevEnemyCreationTime = currentTime;
				int index = (int)(Math.random() * 3);
				Enemy enemy = new Enemy(enemyImages[index]);
				synchronized (player) {
					enemyList.add(enemy);
				}
			}
			
			if (currentTime > prevUpdateTime + interval) {
				
				prevUpdateTime = currentTime;
				
				player.update();
				synchronized (player) {
					for (int i = 0; i < missileList.size(); i++) {
						Missile missile = missileList.get(i);
						missile.update();
						if (!missile.isActive()) {
							missileList.remove(missile);
							i--;
						}
					}
					for (int i = enemyList.size() - 1; i >= 0; i--) {
						Enemy enemy = enemyList.get(i);
						enemy.update();
						if (!enemy.isActive()) {
							enemyList.remove(enemy);
						}
					}
				}
				
				//충돌 체크
				for (int j = enemyList.size() - 1; j >= 0; j--) {
					Enemy enemy = enemyList.get(j);
											
					er.x = enemy.getX();
					er.y = enemy.getY();
					er.width = enemy.getWidth();
					er.height = enemy.getHeight();
					
					pr.x = player.getX();
					pr.y = player.getY();
					pr.width = player.getWidth();
					pr.height = player.getHeight();
					
					if (pr.intersects(er)) {
						synchronized (player) {
							enemyList.clear();
							missileList.clear();
						}
						//쓰레드 중단 ( or update 중단)
						//Thread.currentThread().stop();
						running = false;
						repaint();
						continue outer; //outer로 지정된 반복문의 처음으로 이동
					}
				
					for (int i = 0; i < missileList.size(); i++) {
						Missile missile = missileList.get(i);
						
						mr.x = missile.getX();
						mr.y = missile.getY();
						mr.width = missile.getWidth();
						mr.height = missile.getHeight();					
						
						if (mr.intersects(er)) { //두 사각형이 겹치는지 확인
							synchronized (player) {
								enemyList.remove(enemy);
								missileList.remove(missile);
							}
							i--;
							break;
						}
						
					}					
				}
				
				repaint();//화면 갱신 요청 --> paint() 호출
			
//				try { 
//					Thread.sleep(20);
//				} catch (Exception ex) {
//				}

			} else {
				Thread.yield(); //현재 쓰레드에 할당된 작업시간을 포기
			}
			
		}
		
	}
	
	/////////////////
	
	private KeyListener keyListener = new KeyAdapter() {
		
		long shootingInterval = 500;
		long lastShootingTime = 0;
		long currentTime = 0;
		
		@Override
		public void keyPressed(KeyEvent e) {
			currentTime = System.currentTimeMillis();
			int code = e.getKeyCode();
			switch (code) {
			case KeyEvent.VK_F1 :
				if (!running) { //게임이 중단된 경우에만 실행
					//현재 클래스(KeyAdapter 상속)를 포함하는 계층구조의 상위 클래스 중에서
					//MainScreen 클래스의 인스턴스 참조 변수 : MainScreen.this
					Thread thread = new Thread(MainScreen.this);
					thread.start();
				}
				break;
			case KeyEvent.VK_SPACE :
				if (running && currentTime >= lastShootingTime + shootingInterval) {
					lastShootingTime = currentTime;
					int x = player.getX() + player.getWidth();
					int y = player.getY() + (player.getHeight() - missileImage.getHeight(null)) / 2;
					Missile missile = new Missile(missileImage, x, y);
					synchronized (player) {
						missileList.add(missile);
					}
				}
				break;
			}
			
			if (running) {
				player.keyPressed(e);
			}
			
		}
		@Override
		public void keyReleased(KeyEvent e) {
			
			player.keyReleased(e);
			
		}
	};

}







