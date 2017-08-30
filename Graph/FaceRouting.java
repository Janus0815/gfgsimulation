package Graph;

import java.util.ArrayList;
import java.util.List;

public class FaceRouting {

    private static int source;
    private static int destination;
    private static int numNodes;
    private static ArrayList<Node> allNodes = new ArrayList<Node>();
    private static Matrix adjacencyMatrixRoute = new Matrix();


    public void setParams(ArrayList<Node> nodes, Matrix matrix, int sourceNode, int destinationNode, int numberNodes) {
        this.source = sourceNode;
        this.destination = destinationNode;
        this.adjacencyMatrixRoute = matrix;
        this.allNodes = nodes;
        this.numNodes = numberNodes;
    }

    public List<Integer> doFaceRouting() {
        List<Integer> nodeRoute = new ArrayList<>();
        int actualNode = source; 				//actual considered node
        nodeRoute.add(source);

        //do Face Routing

        return nodeRoute;
    }
}
