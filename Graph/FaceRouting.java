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
        nodeRoute.add(source);	
	    int stStart = source;
	    int stStartX = allNodes.get(stStart).getX();
	    int stStartY = allNodes.get(stStart).getY();
	    int stDestination = destination;
	    int lastNode = source;
        boolean destinationFound = false;
        boolean routingFailed = false;
        boolean faceChange = false;
        while(!destinationFound && !routingFailed) {
	        int actualNode = source; 				//actual considered node
	        
	        
	       

	       
	       
	       // finde nächste Kante zur st-Linie
	       int nextHop;
	       
	       double stAngle = angle(stStartX, stStartY, allNodes.get(stDestination).getX(), allNodes.get(stDestination).getY());
	       
	       nextHop = determineNextHop(stStart, stDestination, stAngle);
	       
	       nodeRoute.add(nextHop);
	       
	       if (nextHop == destination) {
	    	   System.out.println("Routing done");
	           return nodeRoute;
	       }
	       else {
	    	   actualNode = nextHop;		//Endpunkt von s->nextHop wird neuer betrachteter Knoten
	    	   
	    	   double consideredEdgeAngle = angle(allNodes.get(lastNode).getX(), allNodes.get(lastNode).getX(), 
		   				  allNodes.get(actualNode).getX(), allNodes.get(actualNode).getY());
	    	   
	    	   nextHop = determineNextHop(actualNode, lastNode, consideredEdgeAngle);
	    	   
	    	   nodeRoute.add(nextHop);
	    	   
	    	   //ToDo: prüfe, ob schonmal durchlaufen
	    	   if (noch nicht durchlaufen) {
	    		   
		        	//prüfe, ob Schnitt mit st-Linie
		        	if (doesIntersect(allNodes.get(actualNode), allNodes.get(nextHop), 
							  		  allNodes.get(stStart), allNodes.get(stDestination))) {
		        		
		        		//Schnittpunkt berechnen
		        		Node resultNode = new Node();
		        		resultNode = computeIntersection(allNodes.get(actualNode), allNodes.get(nextHop), 
						  		                         allNodes.get(stStart), allNodes.get(stDestination));
		        		stStartX = resultNode.getX();
		        	    stStartY = resultNode.getY();
		        	}// end if doesIntersect
		        } // end if noch nicht durchlaufen
		        
	    	   
	    	   
		        return nodeRoute;
	    	   
	    	   
	    	   
	       }  //else
	       
	       
	       
	        
	        
	        
	        //Methode, um ausgehende Kante zu finden, die zur zuletzt durchlaufenen Kante den kleinsten Winkel hat
	        //Rückgabe: Kante x->potentialNextHop
	        //prüfe, erneuter Durchlauf von sx
	       
    }//while 
    } //doFacerouting
    public static Node computeIntersection(Node actual, Node next, Node startLine, Node destinationLine) {
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
        Node resultNode = new Node();
        int Xint = (int)(x + 0.5d);
        int Yint = (int)(y + 0.5d);
        resultNode.setX(Xint);
        resultNode.setY(Yint);
        return resultNode;
    }
    
    public static double angle (int startpointX, int startpointY, int endpointX, int endpointY) {
    	double originX = endpointX - startpointX;
    	double originY = endpointY - startpointY;
    	double angle = Math.atan2(originX, originY);
    	return angle;    	
    }
    
    public static int determineNextHop (int startNode, int consideredEndpoint, double consideredAngle) {
    	double bestAngle = 999;
    	int potentialNextHop = 0;
    	for (int column = 0; column < numNodes; column++) {
	    	   if (column != consideredEndpoint) {
	    		   if (adjacencyMatrixRoute.get(startNode, column)) {
	    			   double edgeAngle = angle(allNodes.get(startNode).getX(), allNodes.get(startNode).getX(), 
	    		   				  		     	  allNodes.get(column).getX(), allNodes.get(column).getY());
	    			   double actualAngle = edgeAngle - consideredAngle;
	    			   double normalizedAngle = (actualAngle + 2 * Math.PI) % (2 * Math.PI);
	    			   if (normalizedAngle < bestAngle) {
	    				   bestAngle = normalizedAngle;
	    				   potentialNextHop = column;
	    			   }
	    		   }
	    	   }
	       }
    	return potentialNextHop;
    }
    
    public static boolean doesIntersect(Node p1, Node p2, Node p3, Node p4) {
		int p1x = p1.getX();
		int p1y = p1.getY();
		int p2x = p2.getX();
		int p2y = p2.getY();
		int p3x = p3.getX();
		int p3y = p3.getY();
		int p4x = p4.getX();
		int p4y = p4.getY();
				
		//computing the relative orientations 
		int d1 = (p1x - p3x) * (p4y - p3y) - (p4x - p3x) * (p1y - p3y);
		int d2 = (p2x - p3x) * (p4y - p3y) - (p4x - p3x) * (p2y - p3y);
		int d3 = (p3x - p1x) * (p2y - p1y) - (p2x - p1x) * (p3y - p1y);
		int d4 = (p4x - p1x) * (p2y - p1y) - (p2x - p1x) * (p4y - p1y);
		
		//check whether is on segment
		boolean d1_OnSegment = false;
		boolean d2_OnSegment = false;
		boolean d3_OnSegment = false;
		boolean d4_OnSegment = false;
		
		if ((Math.min(p3x, p4x) <= p1x && p1x <= Math.max(p3x, p4x)) && (Math.min(p3y, p4y) <= p1y && p1y <= Math.max(p3y,  p4y))){
			d1_OnSegment = true;
		}
		if ((Math.min(p3x, p4x) <= p2x && p2x <= Math.max(p3x, p4x)) && (Math.min(p3y, p4y) <= p2y && p2y <= Math.max(p3y,  p4y))){
			d2_OnSegment = true;
		}
		if ((Math.min(p1x, p2x) <= p3x && p3x <= Math.max(p1x, p2x)) && (Math.min(p1y, p2y) <= p3y && p3y <= Math.max(p1y,  p2y))){
			d3_OnSegment = true;
		}
		if ((Math.min(p1x, p2x) <= p4x && p4x <= Math.max(p1x, p2x)) && (Math.min(p1y, p2y) <= p4y && p4y <= Math.max(p1y,  p2y))){
			d4_OnSegment = true;
		}
		
					
		//check for intersection
		if  (((d1 > 0 && d2 < 0) || (d1 < 0 && d2 > 0)) && ((d3 > 0 && d4 < 0)|| (d3 < 0 && d4 > 0))) 
		 { return true;
				 
		 }
		else { 
			/*if (d1 == 0 && d1_OnSegment) { 
				return true;
			}
			else {
				if (d2 == 0 && d2_OnSegment) { 
					return true;
				}
				else {
					if (d3 == 0 && d3_OnSegment) { 
						return true;
					}
					else {
						if (d4 == 0 && d4_OnSegment) { 
							return true;
						}
					}
				}
			}*/
			return false;
		}
	}
}
    
