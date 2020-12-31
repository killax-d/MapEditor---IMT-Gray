package fr.killax;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public class Window extends JFrame {

	private static final long serialVersionUID = 1L;

	private static Window instance;
	private static final String WINDOW_TITLE = "TEAM Gray - Map Editor @ Killax";
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	
	
	private static final Timer timer = new Timer();
	
	
	private Window() {
		setTitle(WINDOW_TITLE);
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		
		setContentPane(new AppPane());
		setVisible(true);
	}
	
	public static Window instance() {
		if (instance == null) instance = new Window();
		return instance;
	}
	
	public static void run() {
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				instance.getContentPane().revalidate();
				instance.getContentPane().repaint();
			}
			
		}, 0L, 1000L / 30);
	}
}
