package csci232_programming_assignment_3;

/**
*
* Authors: Rusty Clayton, Kamran Hetke, Derek Jacobson
* Date: 4/9/2018
* Overview: A class to store the value of a given connection.
* This takes two nodes and a weight.
* 
*/

public class Connection {
	
	String node1;
	String node2;
	int weight;

	public Connection(String E1, String E2, int Wt) {
		
		node1 = E1;
		node2 = E2;
		weight = Wt;
		
	}
	
	public String toString() {
		return (node1+node2+", Weight: "+weight);
	}
	
}
