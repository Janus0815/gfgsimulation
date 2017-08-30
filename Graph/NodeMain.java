package Graph;

import java.awt.image.BufferedImage;
import java.util.*;


public class NodeMain {
	//list allNodes for the generated nodes
	private static ArrayList<Node> allNodes = new ArrayList<Node>();
	
	//Adjacency matrix to store connections
	private static Matrix adjacencyMatrix = new Matrix();

	private static long seedb = 112;

	//private static fxVisual blub = new fxVisual();

	public static void main(String[] args) {

		int xMax = 500;					//width of the plane
		int yMax = 500;					//length of the plane
		int numNodes = 100;				//number of nodes			Test:8
		long seedy = 412;				//seed for y-coordinate		Test: 420
		long seedx = 123;				//seed for bool
		int sourceNode = 3;				//Routing: source
		int destinationNode = 1;		//Routing: destination
		
		//method for creating randomly distributed nodes
		createNodes(numNodes, yMax, xMax, seedx, seedy);
		
		//method for creating the edges corresponding to the Log-normal shadowing model
		createEdges(numNodes);
		System.out.println("Originalgraph:");
		showTextResult(numNodes);

		paintResult edgevis = new paintResult();
		edgevis.setGraphParams(allNodes, adjacencyMatrix, xMax, yMax, numNodes);
	    BufferedImage edgeImage = edgevis.createImageOfNetwork();
	    //consider saving the image here?
	    edgevis.displayImage(edgeImage);

	    //method for creating the partially planar subgraph

		doPlanarization(numNodes);
		System.out.println("Teilplanarisiert:");
		showTextResult(numNodes);
		paintResult planvis = new paintResult();
		planvis.setGraphParams(allNodes, adjacencyMatrix, xMax, yMax, numNodes);
		BufferedImage planImage = edgevis.createImageOfNetwork();
		//consider saving the image here?
		edgevis.displayImage(planImage);

		//Greedy Routing
		GreedyRouting greedy = new GreedyRouting();
		greedy.setParams(allNodes, adjacencyMatrix, sourceNode, destinationNode, numNodes);
		List<Integer> greedyRoute = greedy.doGreedyRouting();
		for (int i = 0 ; i < greedyRoute.size() ; i++) System.out.println("Route: " + greedyRoute.get(i));

		paintResult routevis = new paintResult();
		routevis.setGraphParams(allNodes, adjacencyMatrix, xMax, yMax, numNodes);
		routevis.setRoute(greedyRoute);
		BufferedImage routeImage = routevis.createImageWithRoute();
		edgevis.displayImage(routeImage);
	}
	
	
	public static void createNodes(int numNodes, int yMax, int xMax, long seedx, long seedy) {
		int nodeCount = 0;							//counter for generated nodes
		while(nodeCount < numNodes) {
			System.out.println("Node: " + nodeCount);
			Node newNode = new Node();
			
			//random magic goes here
			Random xWert = new Random(seedx);		//random generator for x-coordinate
			xWert.setSeed(seedx);					//setting of the random seed for x-coordinate
			Random yWert = new Random(seedy);		//random generator for y-coordinate
			yWert.setSeed(seedy);					//setting of the random seed for y-coordinate
			
			//finding an new node position which doesn't already exist
			boolean newPositionWithoutOverlap = false;
			while (!newPositionWithoutOverlap) {
				int actualX = (xWert.nextInt(xMax)+1);
				int actualY = (yWert.nextInt(yMax)+1);
				
				int ii = 0;
				boolean isThereAnOverlap = false;
				if (! allNodes.isEmpty()) {
					while (ii <= allNodes.size()-1 && !isThereAnOverlap) {
						if (actualX == allNodes.get(ii).getX() && actualY == allNodes.get(ii).getY()) {
							isThereAnOverlap = true;
						} else ii++;
					} 
				}
				
				//create Node and store	after finding a position without overlap
				if (!isThereAnOverlap) {
					newNode.setX(actualX);
					newNode.setY(actualY);
					allNodes.add(newNode);
					newPositionWithoutOverlap = true;
					System.out.println(nodeCount + "," + allNodes.get(nodeCount).getX() + "," + allNodes.get(nodeCount).getY() + "\n");
					}
			}
			nodeCount++;
		}
	}

	
	public static void createEdges(int numNodes) {		
		//compare a Node to every other Node and determine, if there is a connection
		for (int mainNode=0; mainNode < allNodes.size(); mainNode++ ) {
			for (int subNode=mainNode+1; subNode < allNodes.size(); subNode++) {
 				//System.out.println("Main: " + mainNode + " Sub: " + subNode );
 				
				//generating the adjacency matrix
				if(isConnected(allNodes.get(mainNode), allNodes.get(subNode))) {
					adjacencyMatrix.put(mainNode, subNode, true);
					adjacencyMatrix.put(subNode, mainNode, false);
				}
				else {
					adjacencyMatrix.put(mainNode, subNode, false);
					adjacencyMatrix.put(subNode, mainNode, false);
				}
				System.out.println("Matrixeintrag " + (mainNode+1) +". Zeile, "+ (subNode+1) + ". Spalte: " 
								   +adjacencyMatrix.get(mainNode, subNode));
				System.out.println("Matrixeintrag " + (subNode+1) +". Zeile, "+ (mainNode+1) + ". Spalte: " 
						   +adjacencyMatrix.get(subNode, mainNode) +"\n");
						}
		}
		for (int x = 0; x <= numNodes; x++) {
			adjacencyMatrix.put(x, x, false); 			//no connection from Node x to Node x
			}
		mirrorEdges(numNodes);


	}
	
	
	//determine if there is a connection between Node n1 and Node n2
	public static boolean isConnected(Node n1, Node n2) {
		//compute distance d between Node n1 and Node n2
		double d =  Math.sqrt(Math.pow((n2.getX() - n1.getX()),2) + Math.pow((n2.getY() - n1.getY()),2));	
		System.out.println("Abstand: " +d);
		
		//variables for LNSM
		int PL = 40;												// Pfadverlust bis Referenzdistanz
		double gamma = 2.0;											// Pfadverlustkoeffizient
		double d0 = 1.0;											// Referenzdistanz in Metern
		int c = 58;													// Schwellenwert in dB
		double mittelwert = 1.0;									// Mittelwert für Normalverteilung
		double sigma = 3.0;											// Standardabweichung für Normalverteilung
		double PathLoss; 											// der Gesamtwert
		
		//producing random numbers with a normal distribution
		Random gauss = new Random();								//generate random numbers
		double xGauss = mittelwert + (gauss.nextGaussian()*sigma);	//compute normal distributed random number
		System.out.println("Gauß: " +xGauss);
		
		//computing the pathloss
		PathLoss = PL + 10 * gamma * Math.log10(d/d0) + xGauss;
		System.out.println("PL: "+PathLoss);
		
		//determine whether there is a connection
		if (PathLoss >= c) {return true;}
		else return false;
	}
	
	
	public static void doPlanarization(int numNodes) {
		//detect overlapping Edges
		
		//verschachtelte while-Schleifen gehen Adjazenzmatrix durch, um 1. Kante für Schnitttest zu bestimmen
		boolean removedEdge;
		int firstEdgeRow = 0;
		int firstEdgeColumn = 0;
		int secondEdgeRow = 0;
		int secondEdgeColumn = 0;
		Set<Integer> nodeOrder = new HashSet<Integer>();
		nodeOrder = randomOrder(numNodes);
		Iterator<Integer> nodeIterator = nodeOrder.iterator();
		while ( nodeIterator.hasNext()) {
			firstEdgeRow = nodeIterator.next()-1;
			System.out.println("Looking at Main Node: " + firstEdgeRow);
			secondEdgeRow = 0;
			secondEdgeColumn = 0;
			removedEdge=false;
			firstEdgeColumn = firstEdgeRow+1;
			while (firstEdgeColumn < numNodes && !removedEdge) {
				// wenn Kante gefunden, dann greife Werte der Endpunkte ab
				if (adjacencyMatrix.get(firstEdgeRow, firstEdgeColumn)) {
					//System.out.println("1. Kante: " +(firstEdgeRow)+ ", "+ (firstEdgeColumn));
					//Vergleichskante finden
					secondEdgeRow = firstEdgeRow;
					while (secondEdgeRow < numNodes) {
					    secondEdgeColumn = secondEdgeRow+1;
						while (secondEdgeColumn < numNodes) {
							if(firstEdgeRow!=secondEdgeRow || firstEdgeColumn!=secondEdgeColumn) {
							//System.out.println(secondEdgeRow + "," +  secondEdgeColumn);
							if (adjacencyMatrix.get(secondEdgeRow, secondEdgeColumn)) {
								//System.out.println("Vergleichskante: " +(secondEdgeRow)+ ", "+ (secondEdgeColumn));
								if (doesIntersect(allNodes.get(firstEdgeRow), allNodes.get(firstEdgeColumn), 
										  		  allNodes.get(secondEdgeRow), allNodes.get(secondEdgeColumn))) {
									//System.out.println("Prüfe, ob entfernbar: " + firstEdgeRow + "," + firstEdgeColumn + "-" + secondEdgeRow + "," +  secondEdgeColumn );
									//if (removeEdge(firstEdgeRow, firstEdgeColumn, secondEdgeRow, secondEdgeColumn))
									//		removedEdge = true;
									removeEdge(firstEdgeRow, firstEdgeColumn, secondEdgeRow, secondEdgeColumn);
																	}
								} //if doesIntersect
							}
							secondEdgeColumn++;
						}	//while secondEdgeColumn
						
						secondEdgeRow++;	
					}	//while secondEdgeRow
					
				}	//Ende if-Schleife Eintrag true in Adjazenzmatrix
				
			firstEdgeColumn++;
			}	//Ende while-Schleife firstEdgeColumn	
			if (!removedEdge) {
				firstEdgeRow++;
			}
			else {
				firstEdgeRow = 0;
				firstEdgeColumn = 0;
				
			}
		}	//Ende while-Schleife firstEdgeRow		

		
	}	//Ende Methode doPlanarization
	
	
	//determining whether 2 line segments intersect
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
	
	//remove not needed connections from connection matrix
	public static boolean removeEdge (int firstEdgeEndpointA, int firstEdgeEndpointB, int secondEdgeEndpointC, int secondEdgeEndpointD) {
		//testing whether edge AB could be removed (if A and B have both a connection to C or D)		
		/* if ((((adjacencyMatrix.get(firstEdgeEndpointA, secondEdgeEndpointC)) || (adjacencyMatrix.get(firstEdgeEndpointA, secondEdgeEndpointD))) ||
		   	  (adjacencyMatrix.get(secondEdgeEndpointC, firstEdgeEndpointA)) || (adjacencyMatrix.get(secondEdgeEndpointD, firstEdgeEndpointA)))
				&&
			(((adjacencyMatrix.get(firstEdgeEndpointB, secondEdgeEndpointC)) || (adjacencyMatrix.get(firstEdgeEndpointB, secondEdgeEndpointD))) ||
			  (adjacencyMatrix.get(secondEdgeEndpointC, firstEdgeEndpointB)) || (adjacencyMatrix.get(secondEdgeEndpointD, firstEdgeEndpointB))))
			{ 
			adjacencyMatrix.put(firstEdgeEndpointA, firstEdgeEndpointB, false);
			adjacencyMatrix.put(firstEdgeEndpointB, firstEdgeEndpointA, false);
			//System.out.println("Entfernte Kante: " + firstEdgeEndpointA + "," + firstEdgeEndpointB );
			return true;
		}
		*/
		 if (
		 		(  (adjacencyMatrix.get(firstEdgeEndpointA, secondEdgeEndpointC)) && (adjacencyMatrix.get(firstEdgeEndpointA, secondEdgeEndpointD) ) )
		   	&&  (  (adjacencyMatrix.get(firstEdgeEndpointB, secondEdgeEndpointC)) && (adjacencyMatrix.get(firstEdgeEndpointB, secondEdgeEndpointD) ) )
				 )
			{
				adjacencyMatrix.put(secondEdgeEndpointD, secondEdgeEndpointC, false);
				adjacencyMatrix.put(secondEdgeEndpointC, secondEdgeEndpointD, false);

			//System.out.println("Entfernte Kante: " + firstEdgeEndpointA + "," + firstEdgeEndpointB );
			return true;
		}
		else {
			 if (
			 		(  (adjacencyMatrix.get(firstEdgeEndpointA, secondEdgeEndpointC)) && (adjacencyMatrix.get(firstEdgeEndpointB, secondEdgeEndpointD) ) )
				 || (  (adjacencyMatrix.get(firstEdgeEndpointA, secondEdgeEndpointD)) && (adjacencyMatrix.get(firstEdgeEndpointB, secondEdgeEndpointC) ) )
					 )   {
			 	if (getRandomBoolean()) {
					adjacencyMatrix.put(firstEdgeEndpointA, firstEdgeEndpointB, false);
					adjacencyMatrix.put(firstEdgeEndpointB, firstEdgeEndpointA, false);
					return true;
				} else
					adjacencyMatrix.put(secondEdgeEndpointD, secondEdgeEndpointC, false);
				    adjacencyMatrix.put(secondEdgeEndpointC, secondEdgeEndpointD, false);

				 return true;
				}


			 else return false;
		 }


	}
	
	public static void showTextResult(int numNodes) {
		//Adjazenzmatrix abbilden
		int showMatrix[][] = new int [numNodes][numNodes];
		for (int row = 0; row < numNodes; row++) {
			for (int column = 0; column < numNodes; column++) {
				if (adjacencyMatrix.get(row, column)) showMatrix[row][column] = 1;
				else showMatrix[row][column] = 0;
			}	
		}
		for (int lineOut = 0; lineOut < showMatrix.length; lineOut++) {
			for (int columnOut = 0; columnOut < showMatrix[lineOut].length; columnOut++) {
				System.out.print(showMatrix[lineOut][columnOut]+" ");
			}
			System.out.println("");
		} 
	}

	private static void mirrorEdges(int numNodes) {
		//Matrix spiegeln, um alle Verbindungen zu erhalten
		for (int mirrorRow = 0; mirrorRow < numNodes; mirrorRow++) {
			for (int mirrorColumn = 0; mirrorColumn < numNodes; mirrorColumn++) {
				if (adjacencyMatrix.get(mirrorRow, mirrorColumn))
					adjacencyMatrix.put(mirrorColumn, mirrorRow, true);
				else
					adjacencyMatrix.put(mirrorColumn, mirrorRow, false);
			}

		}
	}

	private static boolean getRandomBoolean() {
		Random generator = new Random(seedb);
		double random = generator.nextDouble();
		//System.out.println("Random Bool: " + random);
		return random < 0.5;
	}

	private static Set<Integer> randomOrder(int numNodes) {
		Random rng = new Random(); // Ideally just create one instance globally
// Note: use LinkedHashSet to maintain insertion order
		Set<Integer> generated = new LinkedHashSet<Integer>();
		while (generated.size() < numNodes)
		{
			Integer next = rng.nextInt(numNodes) + 1;
			// As we're adding to a set, this will automatically do a containment check
			generated.add(next);
		}
		return generated;
	}



}
