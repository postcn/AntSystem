package tsp;

import java.util.ArrayList;
import java.util.Random;

public class Ant {
	int distance;
	Node currentNode;
	Node startNode;
	AntGraph g;
	
	ArrayList<Integer> tour;
	
	public Ant(AntGraph g) {
		this.g = g;
		tour = new ArrayList<Integer>();
	}
	
	public void setStartCity(Node n) {
		currentNode = n;
		startNode = n;
		tour.add(n.id);
	}
	
	public void nextCity() {
		ArrayList<Double> probs = new ArrayList<Double>();
		ArrayList<Pair> reachable = currentNode.getConnections();
		for (int i=0; i<reachable.size(); i++) {
			Pair pair = reachable.get(i);
			double p = g.pheromones[currentNode.id][pair.n.id];
			double d = 1.0/(double)pair.d;
//			p = p*g.alpha;
//			d = d*g.beta;
			for (int j=0; j<g.alpha-1; j++) {
				p = p*p;
			}
			for (int j=0; j<g.beta-1; j++) {
				d = d*d;
			}
			if (!tour.contains(pair.n.id)) {
				probs.add(p+d);
			}
			else if (pair.n.id == startNode.id && tour.size() == g.nodes.length ) {
				probs.add(p+d);
			}
			else {
				probs.add(0.0);
			}
		}
		
		double tots = 0;
		for (Double d: probs) {
			tots += d;
		}
		for(int i=0; i< probs.size(); i++) {
			probs.set(i, probs.get(i)/tots);
		}
		if (probs.size() == 0) {
			return;
		}
		
		double prevTot = 0;
		int i;
		Random r = new Random();
		double p = r.nextDouble();
		for(i=0; i<probs.size(); i++) {
			prevTot += probs.get(i);
			if (p <= prevTot) {
				break;
			}
		}
		
//		currentNode.print();
//		System.out.println(tour);
		
		Pair next = reachable.get(i);
		currentNode = g.nodes[next.n.id];
		distance += next.d;
		tour.add(currentNode.id);
//		System.out.println(tour);
	}
	
	public boolean tourIsFinished() {
		int totalLength = g.nodes.length;
		return tour.size() == totalLength+1;
	}
	
	public boolean stuck() {
		ArrayList<Pair> reachable = currentNode.getConnections();
		for (int i=0; i<reachable.size(); i++) {
			if (!tour.contains(reachable.get(i).n.id) || reachable.get(i).n.id == startNode.id && tour.size() == g.nodes.length) {
				return false;
			}
		}
		return true;
	}

}
