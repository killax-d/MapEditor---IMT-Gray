package fr.killax;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class AppPane extends JPanel implements ListSelectionListener, MouseListener, MouseMotionListener {
	
	private int startX;
	private int startY;

	private JSpinner startXSpinner;
	private JSpinner startYSpinner;
	
	private Assets selectedAsset;
	private Point mousePosition = new Point(200, 0);
	
	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
	private JList<Assets> assetsList;
	public static DefaultListModel<Assets> dlm;
	
	private JButton exportButton;
	
	public AppPane() {
		setLayout(null);
		JLabel labelX = new JLabel("X:");
		labelX.setSize(20, 30);
		labelX.setLocation(250, Window.WINDOW_HEIGHT - 100);
		SpinnerModel modelX = new SpinnerNumberModel(0, 0, App.map[0].length - 15, 1);
		startXSpinner = new JSpinner(modelX);
		startXSpinner.setLocation(270, Window.WINDOW_HEIGHT - 100);
		startXSpinner.setSize(50, 30);
		startXSpinner.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				startX = (Integer) ((JSpinner) e.getSource()).getValue();
			}
		});
		
		JLabel labelY = new JLabel("Y:");
		labelY.setSize(20, 30);
		labelY.setLocation(330, Window.WINDOW_HEIGHT - 100);
		SpinnerModel modelY = new SpinnerNumberModel(0, 0, App.map.length - 15, 1);
		startYSpinner = new JSpinner(modelY);
		startYSpinner.setLocation(350, Window.WINDOW_HEIGHT - 100);
		startYSpinner.setSize(50, 30);
		startYSpinner.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				startY = (Integer) ((JSpinner) e.getSource()).getValue();
			}
		});

		add(labelX);
		add(startXSpinner);
		add(labelY);
		add(startYSpinner);
		
		exportButton = new JButton("export");
		exportButton.setLocation(Window.WINDOW_WIDTH - 250, Window.WINDOW_HEIGHT - 100);
		exportButton.setSize(200, 30);
		exportButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				App.processMap();
			}
		});
		add(exportButton);
		
		scrollPane = new JScrollPane();
		dlm = new DefaultListModel<Assets>();
		assetsList = new JList<Assets>(dlm);
		assetsList.setCellRenderer(new AssetsList());
		assetsList.addListSelectionListener(this);
		scrollPane.setViewportView(assetsList);
		scrollPane.setLocation(0, 0);
		scrollPane.setSize(Window.WINDOW_WIDTH / 4, Window.WINDOW_HEIGHT);
		scrollPane.setDropTarget(new DropTarget() {
			private static final long serialVersionUID = 1L;

			public synchronized void drop(DropTargetDropEvent evt) {
		        try {
		            evt.acceptDrop(DnDConstants.ACTION_COPY);
		            @SuppressWarnings("unchecked")
					List<File> droppedFiles = (List<File>)
		                evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
		            for (File file : droppedFiles) {
		                dlm.addElement(new Assets(file.getAbsolutePath()));
		            }
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }
		    }
		});
		
		add(scrollPane);

		addMouseListener(this);
		addMouseMotionListener(this);
		
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		for (int y = 0; y < 15; y++) {
			if (startY + y >= App.map.length) break;
			for (int x = 0; x < 15; x++) {
				if (startX + x >= App.map[y].length) break;
				if (App.map[startY + y][startX + x] != null)
					g.drawImage(App.map[startY + y][startX + x].getImage(), 200 + x * Assets.SPRITE_SIZE, y * Assets.SPRITE_SIZE, null);
				g.drawRect(200 + x * Assets.SPRITE_SIZE, y * Assets.SPRITE_SIZE, Assets.SPRITE_SIZE, Assets.SPRITE_SIZE);
			}
		}
		
		if (this.selectedAsset != null)
			g.drawImage(this.selectedAsset.getImage(), mousePosition.x, mousePosition.y, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void valueChanged(ListSelectionEvent event) {
		this.selectedAsset = ((JList<Assets>) event.getSource()).getSelectedValue();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (e.getX() > 200) {
			mousePosition = e.getPoint();
			int y = e.getY()/Assets.SPRITE_SIZE;
			int x = (e.getX()-200)/Assets.SPRITE_SIZE;
			if (y < App.map.length && x < App.map[y].length)
				App.map[startY + y][startX + x] = this.selectedAsset;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mousePosition = e.getPoint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getX() < 200)
			assetsList.dispatchEvent(e);
		else {
			int y = e.getY()/Assets.SPRITE_SIZE;
			int x = (e.getX()-200)/Assets.SPRITE_SIZE;
			if (y < App.map.length && x < App.map[y].length)
				App.map[y][x] = this.selectedAsset;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getX() < 200)
			assetsList.dispatchEvent(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getX() < 200)
			assetsList.dispatchEvent(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getX() < 200)
			assetsList.dispatchEvent(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getX() < 200)
			assetsList.dispatchEvent(e);
	}

	
}
