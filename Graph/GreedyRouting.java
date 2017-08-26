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
        System.out.println("Routing");
        ArrayList<Node> nodeRoute = allNodes;
    	int actualNode = source; 				//actual considered node
    	double actualBestDistance = Math.sqrt(Math.pow((allNodes.get(destination).getX() - allNodes.get(source).getX()),2) + 
    										  Math.pow((allNodes.get(destination).getY() - allNodes.get(source).getY()),2));
    	boolean destinationFound = false;
    	boolean stuck = false;
    	int potentialNeighbour = 0; 				//considered column in the row of the actualNode
    	int candidate = actualNode;					//candidate for the next hop
    	
    	while(!destinationFound && !stuck) {		
            System.out.println("Routing");
            System.out.println("Aktueller Knoten: " + actualNode); 
            boolean nextHopFound = false;
	    	while(potentialNeighbour < numNodes && !destinationFound && !nextHopFound) {	    		
	    		if(potentialNeighbour == actualNode) {
	    			System.out.println("Betrachteter Nachbar gleich aktueller Knoten");
	    			potentialNeighbour++;
	    			System.out.println("Nächster betrachteter Nachbar: " +potentialNeighbour);
	    		}

	    		if (potentialNeighbour == destination) {
	    			System.out.println("Erreiche Knoten: " +potentialNeighbour); 		
		    		nodeRoute.add(allNodes.get(potentialNeighbour));
	    			System.out.println("Ziel erreicht");
	    			destinationFound = true;	    			
	    			return nodeRoute;
	    		}
	    		if (potentialNeighbour != actualNode && adjacencyMatrixRoute.get(actualNode, potentialNeighbour)) {
	    			double potentialBestDistance = Math.sqrt(Math.pow((allNodes.get(destination).getX() - allNodes.get(potentialNeighbour).getX()),2) + 
							  Math.pow((allNodes.get(destination).getY() - allNodes.get(potentialNeighbour).getY()),2));
	    			if(potentialBestDistance < actualBestDistance) {
	    				actualBestDistance = potentialBestDistance;
	    				candidate = potentialNeighbour;
	    				System.out.println("Kandidat:" +candidate);
	    				potentialNeighbour++;
	    			}
	    			else {
	    				potentialNeighbour++;
	    				System.out.println("Next neighbour");
	    				if (potentialNeighbour == numNodes) {
	    	    			nextHopFound = true;
	    				}
	    			}
	    		} 
	    		else {
	    			potentialNeighbour++;
	    		}
	    		if (potentialNeighbour == numNodes) {
	    			nextHopFound = true;
	    		}
	    	}//innere while
	    	if (actualNode == candidate) {
	    		System.out.println("Lokales Minimum erreicht");
	    		stuck = true;
	    	}
	    	else {
	    		actualNode = candidate;
	    		System.out.println("Nächster Knoten: "+ actualNode); 		
	    		nodeRoute.add(allNodes.get(actualNode));
	    			    	}
    	}//äußere while
    	

        
        //end Routing
        return nodeRoute;
    }
}
