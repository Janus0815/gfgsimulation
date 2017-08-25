package Graph;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;


public class paintResult extends JFrame {

	private static int xMax;
	private static int yMax;
	private static int numNodes;
	private static ArrayList<Node> allNodes = new ArrayList<Node>();
	private static Matrix adjacencyMatrixDraw = new Matrix();


	public void setGraphParams(ArrayList<Node> nodes, Matrix matrix, int x, int y, int numberNodes) {
		this.xMax = x;
		this.yMax = y;
		this.adjacencyMatrixDraw = matrix;
		this.allNodes = nodes;
		this.numNodes = numberNodes;
	}

	public BufferedImage createImageOfNetwork() {
		BufferedImage img = new BufferedImage(xMax + 10, yMax + 10, BufferedImage.TYPE_INT_ARGB);
		paintNetWork(img);
		return img;

	}

	public void displayImage(BufferedImage img) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon icon = new ImageIcon(img);
		frame.add(new JLabel(icon));
		frame.pack();
		frame.setVisible(true);
	}


	private void paintNetWork(BufferedImage image) {

		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setColor(Color.red);

		//Knoten
		for (int x = 0; x < allNodes.size(); x++) {
			g.drawOval(allNodes.get(x).getX() - 3 + 10, (yMax - allNodes.get(x).getY() - 3), 6, 6);
			g.fillOval(allNodes.get(x).getX() - 3 + 10, (yMax - allNodes.get(x).getY() - 3), 6, 6);
			g.drawString(String.valueOf(x), allNodes.get(x).getX() + 12, (yMax - allNodes.get(x).getY() - 6));
		}

		//Kanten
		g.setColor(Color.black);
		for (int row = 0; row < numNodes; row++) {
			for (int column = row + 1; column < numNodes; column++) {
				if (adjacencyMatrixDraw.get(row, column)) {
					g.drawLine(allNodes.get(row).getX() + 10, (yMax - allNodes.get(row).getY()),
							allNodes.get(column).getX() + 10, (yMax - allNodes.get(column).getY()));
				}
			}
		}
		g.dispose();

	}
}