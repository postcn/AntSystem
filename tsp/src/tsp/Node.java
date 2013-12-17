package tsp;

import java.util.ArrayList;

public class Node {
	ArrayList<Pair> connectedNodes;
	int id;
	
	public Node(int id) {
		this.id = id;
		this.connectedNodes = new ArrayList<Pair>();
	}
	
	public void addConnection(Node n, int distance) {
		if (!connectedNodes.contains(new Pair(n,distance))) {
			this.connectedNodes.add(new Pair(n,distance));
		}
	}
	
	public ArrayList<Pair> getConnections() {
		return connectedNodes;
	}
	
	public int connectedTo(Node n) {
		for(int i=0; i<connectedNodes.size(); i++) {
			Node test = connectedNodes.get(i).getNode();
			if (test.id == this.id) {
				return connectedNodes.get(i).getDistance();
			}
		}
		return -1;
	}
	
	public void print() {
		for (int i=0; i<connectedNodes.size(); i++) {
			System.out.println(id + " " + connectedNodes.get(i).n.id + " " + connectedNodes.get(i).d);
		}
	}
}
