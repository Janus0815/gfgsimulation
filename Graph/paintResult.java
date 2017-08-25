package Graph;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;


public class paintResult extends JFrame{

	private MyCanvas canvas = new MyCanvas();
	private static int xMax;
	private static int yMax;
	private static int numNodes;
	private static ArrayList<Node> allNodes = new ArrayList<Node>();
	private static Matrix adjacencyMatrix = new Matrix();
	
	public  void setGraphParams(ArrayList<Node> nodes, Matrix matrix, int x, int y, int numberNodes) {
        this.xMax=x;
        this.yMax=y;
        this.adjacencyMatrix=matrix;
        this.allNodes=nodes;
        this.numNodes=numberNodes;
        
        Visualization();	

	}
		
	//public static void main (String[] args) {
	//		
	//}
	
	
	public  void Visualization() {
		setLayout(new BorderLayout());
		setSize(this.xMax+50, this.yMax+50);							//Fenstergröße entsprechend der gewählten Ebene
		setTitle("Graph");
		add("Center", this.canvas);							//Abbildung ins Fenster einsetzen
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//einfache Möglichkeit Fenster zu schließen
		
		setLocationRelativeTo(null);					//Fenster in Bildschirmmitte einblenden
		
		setVisible(true);
	}
	
	
	private class MyCanvas extends Canvas {
		
		//@Override
		public void paint(Graphics g) {
			
			g.setColor(Color.red);
			
			//Knoten
			for (int x = 0; x < allNodes.size(); x++) {
				g.drawOval(allNodes.get(x).getX()-3+10, (yMax - allNodes.get(x).getY()-3), 6, 6);
				g.fillOval(allNodes.get(x).getX()-3+10, (yMax - allNodes.get(x).getY()-3), 6, 6);
				g.drawString(String.valueOf(x), allNodes.get(x).getX()+12, (yMax - allNodes.get(x).getY()-6));
			}
			
			//Kanten
			g.setColor(Color.black);
			for (int row = 0; row < numNodes; row++) {
				for (int column = row + 1; column < numNodes; column++) {
					if (adjacencyMatrix.get(row, column)) {
						g.drawLine(allNodes.get(row).getX()+10, (yMax - allNodes.get(row).getY()), 
								   allNodes.get(column).getX()+10, (yMax - allNodes.get(column).getY()));
					}
				}
			}
		}
	}
}
