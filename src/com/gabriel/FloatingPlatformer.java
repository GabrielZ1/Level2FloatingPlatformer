package com.gabriel;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;

public class FloatingPlatformer implements MouseListener {
	JFrame frame;
	GamePanel gp;
	public static int WIDTH = 600;
	public static int HEIGHT = 600;

	public static void main(String[] args) {
		new FloatingPlatformer().run();
	}

	public FloatingPlatformer() {
		frame = new JFrame();
		gp = new GamePanel(frame);
	}

	public void run() {
		gp.addMouseListener(this);
		frame.add(gp);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(gp);

		gp.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.pack();

		frame.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
