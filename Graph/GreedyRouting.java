package Graph;

import java.util.ArrayList;

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

    public ArrayList<Node> doGreedyRouting() {
    	//do Routing
        ArrayList<Node> nodeRoute = allNodes;
    	int actualNode = source; 				//actual considered node
    	double actualBestDistance = Math.sqrt(Math.pow((allNodes.get(destination).getX() - allNodes.get(source).getX()),2) + 
    										  Math.pow((allNodes.get(destination).getY() - allNodes.get(source).getY()),2));
    	boolean destinationFound = false;
    	boolean stucked = false;
    	int potentialNeighbor = 0; 				//considered column in the row of the actualNode
    	int candidate = actualNode;				//candidate for the next hop
    	
    	while(!destinationFound || !stucked) {
    	
	    	while(potentialNeighbor < numNodes && !destinationFound) {
	    		if (potentialNeighbor == destination) {
	    			System.out.println("Ziel erreicht");
	    			destinationFound = true;
	    			return nodeRoute;
	    		}
	    		if (potentialNeighbor != actualNode && adjacencyMatrixRoute.get(actualNode, potentialNeighbor)) {
	    			double potentialBestDistance = Math.sqrt(Math.pow((allNodes.get(destination).getX() - allNodes.get(potentialNeighbor).getX()),2) + 
							  Math.pow((allNodes.get(destination).getY() - allNodes.get(potentialNeighbor).getY()),2));
	    			if(potentialBestDistance < actualBestDistance) {
	    				candidate = potentialNeighbor;
	    				potentialNeighbor++;
	    			}
	    			else {
	    				potentialNeighbor++;
	    			}
	    		}
	    			    		
	    	}//innere while
	    	if (actualNode == candidate) {
	    		System.out.println("Lokales Minimum erreiche");
	    		stucked = true;
	    	}
	    	else {
	    		actualNode = candidate;
	    		System.out.println(actualNode); 		//Node Array List Zeugs
	  //  		nodeRoute.add(allNodes(actualNode));
	    	}
    	}//äußere while
    	

        
        //end Routing
        return nodeRoute;
    }
}
