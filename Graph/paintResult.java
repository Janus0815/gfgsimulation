package Graph;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.awt.geom.AffineTransform;

import javax.swing.*;


public class paintResult extends JFrame {

	private static int xMax;
	private static int yMax;
	private static int numNodes;
	private static ArrayList<Node> allNodes = new ArrayList<Node>();
	private static Matrix adjacencyMatrixDraw = new Matrix();
    private static List<Integer> routeList;
    private static int extendSize = 50;
    private static int shiftPixels = 20;

	public void setGraphParams(ArrayList<Node> nodes, Matrix matrix, int x, int y, int numberNodes) {
		this.xMax = x;
		this.yMax = y;
		this.adjacencyMatrixDraw = matrix;
		this.allNodes = nodes;
		this.numNodes = numberNodes;
	}

	public void setRoute(List<Integer> route) {
		this.routeList=route;
	}

	public BufferedImage createImageOfNetwork() {
		BufferedImage img = new BufferedImage(xMax + extendSize, yMax + extendSize, BufferedImage.TYPE_INT_ARGB);
		paintNetWork(img);
		return img;

	}

	public BufferedImage createImageWithRoute() {
		BufferedImage img = new BufferedImage(xMax + extendSize, yMax + extendSize, BufferedImage.TYPE_INT_ARGB);
		paintNetWork(img);
		paintRoute(img);
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
		
		//Knoten
		g.setColor(Color.red);
		for (int x = 0; x < allNodes.size(); x++) {
			g.drawOval(allNodes.get(x).getX() - 3 + shiftPixels, (yMax - allNodes.get(x).getY() - 3), 6, 6);
			g.fillOval(allNodes.get(x).getX() - 3 + shiftPixels, (yMax - allNodes.get(x).getY() - 3), 6, 6);
			g.drawString(String.valueOf(x), allNodes.get(x).getX() + shiftPixels + 2 , (yMax - allNodes.get(x).getY() - 6));
		}

		//Kanten
		g.setColor(Color.black);
		for (int row = 0; row < numNodes; row++) {
			for (int column = row + 1; column < numNodes; column++) {
				if (adjacencyMatrixDraw.get(row, column)) {
					g.drawLine(allNodes.get(row).getX() + shiftPixels, (yMax - allNodes.get(row).getY()),
							allNodes.get(column).getX() + shiftPixels, (yMax - allNodes.get(column).getY()));
				}
			}
		}
		g.dispose();

	}

	private void paintRoute(BufferedImage image) {

		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setColor(Color.blue);

		boolean done = false;
        int pos =0;

        for (pos = 0; pos < routeList.size()-1 ; pos++) {
        	System.out.println(pos);
			Node fromNode = allNodes.get(routeList.get(pos));
			Node toNode = allNodes.get(routeList.get(pos +1));
			System.out.println("Position: " + pos + " Draw from: " + routeList.get(pos) + " to: " + routeList.get(pos + 1));
			drawArrow(g,fromNode.getX() + shiftPixels, (yMax - fromNode.getY()),
					  toNode.getX() + shiftPixels, (yMax - toNode.getY()));
		}


		g.dispose();

	}

	private void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
		Graphics2D g = (Graphics2D) g1.create();
		int ARR_SIZE = 6;

		double dx = x2 - x1, dy = y2 - y1;
		double angle = Math.atan2(dy, dx);
		int len = (int) Math.sqrt(dx*dx + dy*dy);
		AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
		at.concatenate(AffineTransform.getRotateInstance(angle));
		g.transform(at);

		// Draw horizontal arrow starting in (0, 0)
		g.drawLine(0, 0, len, 0);
		g.fillPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len},
				new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);
	}
}