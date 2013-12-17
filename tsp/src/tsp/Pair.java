package tsp;

public class Pair {
	Node n;
	int d;
	
	public Pair(Node n, int d) {
		this.n = n;
		this.d = d;
	}
	
	public int getDistance() {
		return this.d;
	}
	
	public Node getNode() {
		return this.n;
	}

}
