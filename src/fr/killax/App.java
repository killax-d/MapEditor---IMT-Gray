package fr.killax;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;

public class App {

	public static Assets[][] map;
	public static JFrame loadingFrame;

	public static void main(String... args) {
		loadingFrame = new JFrame();
		loadingFrame.setAlwaysOnTop(true);
		loadingFrame.setTitle("Loading app...");
		loadingFrame.setSize(400, 0);
		loadingFrame.setLocationRelativeTo(null);
		loadingFrame.setVisible(true);
		
		map = new Assets[Data.baseMap.length][Data.baseMap[0].length];

		Window.instance();

		initMap();

		Window.run();
		
		loadingFrame.dispose();
	}

	public static void initMap() {
		Assets[] baseAssets = new Assets[] { new Assets(new Color(0, 0, 0, 0)), new Assets(new Color(80, 80, 80, 255)),
				new Assets(new Color(200, 200, 200, 255)), };

		for (int i = 0; i < baseAssets.length; i++)
			AppPane.dlm.addElement(baseAssets[i]);

		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[y].length; x++) {
				map[y][x] = baseAssets[Data.baseMap[y][x]];
			}
		}

	}

	public static void processMap() {
		StringBuilder builder = new StringBuilder();
		builder.append('{').append('\n');
		for (int y = 0; y < map.length; y++) {
			builder.append('\t').append('{').append(map[y][0] == null ? 0 : map[y][0].getId());
			for (int x = 1; x < map[y].length; x++) {
				builder.append(',').append(' ').append(map[y][x] == null ? 0 : map[y][x].getId());
			}
			builder.append('}').append(',').append('\n');
		}
		builder.append('}');

		try {
			File file = new File("map.txt");
			if (file.exists()) file.delete();
			
			FileWriter fw = new FileWriter(file);
			fw.write(builder.toString());
			fw.close();
			
			System.out.println("Exported to : " + file.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
