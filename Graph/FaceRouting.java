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
        boolean destinationFound = false;
        boolean routingFailed = false;
        boolean faceChange = false;
        while(!destinationFound && !routingFailed) {
	        int actualNode = source; 				//actual considered node
	        nodeRoute.add(source);					//source kommt nicht drauf, wenn Schnittpunkt -> ToDo
	        
	        
	        // Werte für Source-Destination Linie
	       int stStart = source;
	       int stDestination = destination;
	        
	        //Methode, um ausgehende Kante zu finden, die zu st den kleinesten Winkel (rechtsrum) hat
	        //Rückgabe: Kante s->nextHop
	       
	       
	        //Kante speichern, um Routingfehlschlag zu erkennen (falls in gleicher Richtung zum 2. Mal durchlaufen wird)
	        
	        actualNode = nextHop; //Endpunkt von s->nextHop wird neuer betrachteter Knoten
	        
	        
	        //Methode, um ausgehende Kante zu finden, die zur zuletzt durchlaufenen Kante den kleinsten Winekl hat
	        //Rückgabe: Kante x->potentialNextHop
	        //prüfe, erneuter Durchlauf von sx
	        if (noch nicht durchlaufen) {
	        	//prüfe, ob Schnitt mit st-Linie
	        	if (doesIntersect(allNodes.get(actualNode), allNodes.get(potentialNextHop), 
						  		  allNodes.get(stStart), allNodes.get(stDestination))) {
	        		
	        		//Schnittpunkt berechnen#
	        		computeIntersection(allNodes.get(actualNode), allNodes.get(potentialNextHop), 
					  		  allNodes.get(stStart), allNodes.get(stDestination));
	        		
	        	}// end if doesIntersect
	        	
	        	
	        }
	        else {
	        	System.out.println("Routing failed");
	        	routingFailed = true;
	        }
	        
	        
	        
	        
	
	        return nodeRoute;
    }//while 
    } //doFacerouting
    public static void computeIntersection(Node actual, Node next, Node startLine, Node destinationLine) {
    	double m1;
    	double m2;
    	double b1;
    	double b2;
    	/*int x1 = actual.getX();
    	int y1 = actual.getY();
    	int x2 = next.getX();
    	int y2 = next.getY();
    	int x3 = startLine.getX();
    	int y3 = startLine.getY();
    	int x4 = destinationLine.getX();
    	int y4 = destinationLine.getY();*/
    	
    	//Berchnung: m1 = (y2-y1)/(x2-x1), b1 = y2-m1*x2
    	
    	m1 = (next.getY()-actual.getY())/(next.getX()-actual.getX());
    	m2 = (destinationLine.getY()-startLine.getY())/(destinationLine.getX()-startLine.getX());
    	b1 = next.getY()-m1*next.getX();
    	b2 = destinationLine.getY()-m2*destinationLine.getX();
    	
    	double x = Math.abs((b1 - b2) / (m1 - m2));
        double y = Math.abs(m1 * x + b1);
        //ToDo: Knoten newSource = (x,y) und zurückgeben
    	
    }
}
    
