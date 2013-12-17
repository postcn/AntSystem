package tsp;

import java.util.ArrayList;
import java.util.Random;

public class AntGraph {
	Node[] nodes;
	double[][] pheromones;
	double tau0 = 1E-6;
	ArrayList<Integer> tplus;
	int lplus = Integer.MAX_VALUE;
	ArrayList<Ant> ants;
	ArrayList<Ant> activeAnts;
	int alpha = 3;
	int beta = 1;
	double rho = .3;
	int e = 6;
	
	public AntGraph(int numNodes, double antModifier) {
		nodes = new Node[numNodes];
		pheromones = new double[numNodes][numNodes];
		for (int i =0; i<numNodes; i++) {
			for (int j=0; j<numNodes; j++) {
				pheromones[i][j]=tau0;
			}
		}
		ants = new ArrayList<Ant>();
		activeAnts = new ArrayList<Ant>();
		for (int i=0; i<numNodes*antModifier; i++) {
			ants.add(new Ant(this));
			activeAnts.add(ants.get(i));
		}
	}
	
	public void addNode(int id, int connectedTo, int distance) {
		if (nodes[id] == null) {
			nodes[id] = new Node(id);
		}
		if (nodes[connectedTo] == null) {
			nodes[connectedTo] = new Node(connectedTo);
		}
		
		nodes[id].addConnection(nodes[connectedTo], distance);
		nodes[connectedTo].addConnection(nodes[id], distance);
	}
	
	public void printGraph() {
		for (int i=0; i<nodes.length; i++) {
			nodes[i].print();
		}
	}
	
	private void updatePheromones() {
		int numNodes = pheromones.length;
		for (int i =0; i<numNodes; i++) {
			for (int j=0; j<numNodes; j++) {
				pheromones[i][j] *= (1-rho);
			}
		}
		for (Ant a: ants) {
			if (a.tourIsFinished()) {
				int d = a.distance;
				for (int i = 0; i< a.tour.size()-1; i++) {
					int first = a.tour.get(i);
					int second = a.tour.get(i+1);
					pheromones[first][second] += 1.0/d;
					pheromones[second][first] += 1.0/d;
				}
			}
		}
		
		//elitist ants
		if (tplus == null) {
			return;
		}
		for (int i = 0; i< tplus.size()-1; i++) {
			int first = tplus.get(i);
			int second = tplus.get(i+1);
			pheromones[first][second] += e*1.0/lplus;
			pheromones[second][first] += e*1.0/lplus;
		}
	}
	
	public void iterate(int n) {
		for(; n>0; n--) {
			Random r = new Random();
			for (Ant a: ants) {
				int t = r.nextInt(nodes.length);
				a.setStartCity(nodes[t]);
			}
			while (activeAnts.size() > 0) {
				ArrayList<Ant> toRemove = new ArrayList<Ant>();
				for (Ant a: activeAnts) {
					a.nextCity();
					if (a.stuck() || a.tourIsFinished()) {
						toRemove.add(a);
					}
				}
				activeAnts.removeAll(toRemove);
			}
			for (Ant a: ants) {
				if (a.tourIsFinished()) {
					int d = a.distance;
					if (d<lplus) {
						tplus = a.tour;
						lplus = d;
					}
				}
			}
			
			updatePheromones();
			int l = ants.size();
			ants = new ArrayList<Ant>();
			activeAnts = new ArrayList<Ant>();
			for (int i=0; i<l; i++) {
				ants.add(new Ant(this));
				activeAnts.add(ants.get(i));
			}
		}
		
		if (tplus == null) {
			System.out.println("Ants failed to find a solution");
			return;
		}
		
		System.out.println("Results of ants:");
		System.out.println("" + tplus);
		System.out.println("Distance: "+lplus);
	}

}
