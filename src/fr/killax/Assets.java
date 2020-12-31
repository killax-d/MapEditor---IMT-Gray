package fr.killax;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Assets {
	
	public static int SPRITE_SIZE = 32;
	private static int GLOBAL_ID = 0;
	
	private int id;
	private Path path;
	private String name;
	private Image image;
	private ImageIcon icon;
	
	public Assets(String path) throws IOException {
		this.path = Path.of(path);
		buildAsset(path);
		this.id = GLOBAL_ID++;
		this.name = this.path.getFileName().toString();
	}
	
	public Assets(Color color) {
		this.id = GLOBAL_ID++;
		this.name = "Default : " + id;
		BufferedImage color_image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		color_image.setRGB(0, 0, color.getRGB());
		
		this.image = color_image.getScaledInstance(SPRITE_SIZE, SPRITE_SIZE, Image.SCALE_FAST);
		this.icon = new ImageIcon(this.image);
	}
	
	private void buildAsset(String path) throws IOException {
		this.image = ImageIO.read(new File(path)).getScaledInstance(SPRITE_SIZE, SPRITE_SIZE, Image.SCALE_SMOOTH);
		this.icon = new ImageIcon(this.image);
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Image getImage() {
		return this.image;
	}
	
	public ImageIcon getIcon() {
		return this.icon;
	}
}
