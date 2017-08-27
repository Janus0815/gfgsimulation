package Graph;

import java.util.ArrayList;
import java.util.List;


public class GreedyRouting {

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

    public List<Integer> doGreedyRouting() {
    	//do Routing
        System.out.println("Routing");
        List<Integer> nodeRoute = new ArrayList<>();
    	int actualNode = source; 				//actual considered node
        nodeRoute.add(source);

    	boolean stop = false;
    	int nextHop = 0;					//candidate for the next hop
        while(!stop) {
            nextHop=getBestDistanceToTarget(actualNode);
            System.out.println("next hop: " + nextHop);
            if (actualNode == nextHop || nextHop == -1) {
                System.out.println("Reached local Minimum, Routing failed");
                stop = true;
                nodeRoute.clear(); //routing failed
            }
            else {
                    if (!nodeRoute.contains(nextHop)) { //check if we've been there
                        actualNode = nextHop;
                        nodeRoute.add(actualNode);
                        System.out.println("Routed to next Node: " + actualNode);
                    } adjacencyMatrixRoute.put(actualNode, nextHop,false); //avoid turning in circles
            }
            if (actualNode == destination) stop=true;
        }
        System.out.println("Routing done");
        return nodeRoute;
    }

    private int getBestDistanceToTarget(int actualNode) {
        int potentialNeighbour = 0;
        double actualBestDistance = calcDistance(destination, actualNode);
        double potentialBestDistance = 9999999;
        int candidate=-1;
        boolean stop = false;

        while (potentialNeighbour < numNodes - 1) {
            potentialBestDistance=9999999;
            if (potentialNeighbour!=actualNode) {
                if (adjacencyMatrixRoute.get(actualNode, potentialNeighbour)) {
                    potentialBestDistance = calcDistance(destination, potentialNeighbour);
                    if (potentialNeighbour == destination) {
                        candidate=potentialNeighbour;
                        stop = true;
                    }
                }
                if (potentialBestDistance < actualBestDistance && !stop) {
                    actualBestDistance = potentialBestDistance;
                    candidate = potentialNeighbour;
                    System.out.println("Bester Kandidat:" + candidate);
                    potentialNeighbour++;
                } else {
                    System.out.println("Distance larger, PBD:" + potentialBestDistance + " ABD: " + actualBestDistance);
                    potentialNeighbour ++;
                    if (potentialNeighbour == numNodes -1) {
                     if (potentialBestDistance!=9999999) candidate = potentialNeighbour;
                    }
                }
            } else potentialNeighbour++;

        }
        return candidate;
    }

    private double calcDistance(int src, int trgt) {
        return Math.sqrt(Math.pow((allNodes.get(trgt).getX() - allNodes.get(src).getX()),2) +
                                              Math.pow((allNodes.get(trgt).getY() - allNodes.get(src).getY()),2));
    }

}
