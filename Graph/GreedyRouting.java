package Graph;

import java.util.ArrayList;

public class GreedyRouting {

    //private MyCanvas canvas = new MyCanvas();
    private static int xMax;
    private static int yMax;
    private static int numNodes;
    private static ArrayList<Node> allNodes = new ArrayList<Node>();
    private static Matrix adjacencyMatrixDraw = new Matrix();


    public void setParams(ArrayList<Node> nodes, Matrix matrix, int x, int y, int numberNodes) {
        this.xMax = x;
        this.yMax = y;
        this.adjacencyMatrixDraw = matrix;
        this.allNodes = nodes;
        this.numNodes = numberNodes;
    }

    public ArrayList<Node> doGreedyRouting() {

        //do Routing
        ArrayList<Node> nodeRoute = allNodes;
        //end Routing
        return nodeRoute;
    }
}
