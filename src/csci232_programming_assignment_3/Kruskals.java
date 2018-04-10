package csci232_programming_assignment_3;

/**
*
* Authors: Rusty Clayton, Kamran Hetke, Derek Jacobson
* Date: 4/6/2018
* Overview: This code implements Kruskals, taking in an adjacency matrix
* and outputting the MST
* 
*/

import java.util.*;

public class Kruskals extends Driver {
	
	Map<String, String> parent = new HashMap<>();
	
	public Kruskals(String[][] adjacencyMatrix) {
		
		List<Connection> edges = makeEdges(adjacencyMatrix);
		int nodeCount = adjacencyMatrix[0].length;
		
		List<Connection> MST = process(edges, nodeCount);
		
		for(Connection e: MST) {
			System.out.println(e.toString());
		}
		
	}
	
	public List<Connection> makeEdges(String[][] adjacencyMatrix) {
		List<Connection> edges = new ArrayList<Connection>();
		
    	for (int i = 1; i<adjacencyMatrix.length;i++){
            for (int j=0; j<adjacencyMatrix[1].length; j++){
            	if (adjacencyMatrix[i][j].charAt(0) != '∞' && adjacencyMatrix[i][j].charAt(0) != '0') {
            		edges.add(new Connection(adjacencyMatrix[0][i-1], adjacencyMatrix[0][j], Integer.parseInt(adjacencyMatrix[i][j])));
            		adjacencyMatrix[i][j] = "0";
            		adjacencyMatrix[j+1][i-1] = "0";
                }
            }
        }
    	
    	Collections.sort(edges, (a, b) -> a.weight - b.weight);
    	
		return edges;
	}
	
	public void makeSet(int N) {
		char a = 'A';
		for(int i = 0; i < N; i++) {
			String b = Character.toString(a);
			parent.put(b,b);
			a++;
		}
	}
	
	private String findRoot(String i) {
		if(parent.get(i) == i) {
			return i;
		} else {
			return findRoot(parent.get(i));
		}
	}
	
	private void Union(String a, String b) {
		String x = findRoot(a);
		String y = findRoot(b);
		
		parent.put(x, y);
	}
	
    public List<Connection> process(List<Connection> edges, int nodeCount) {
    	
    	List<Connection> MST = new ArrayList();
    	int i = 0;
    	
    	makeSet(nodeCount);
    	
    	while(MST.size() != nodeCount-1) {
    		Connection nextEdge = edges.get(i++);
    		
    		String x = findRoot(nextEdge.node1);
    		String y = findRoot(nextEdge.node2);
    		
    		if (x != y) {
    			MST.add(nextEdge);
    			Union(x,y);
    		}
    		
    	}
    	
    	return MST;
	}
	
}
