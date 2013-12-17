package tsp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		for (int i=0; i<3; i++) {
			run();
		}
	}
	
	public static void run() {
		long startTime = System.currentTimeMillis();
		BufferedReader br;
		ArrayList<int[]> temp = new ArrayList<int[]>();
		int nodes = 0;
		ArrayList<Integer> nodeIds = new ArrayList<Integer>();
		try {
			br = new BufferedReader(new FileReader("thirtySparse.txt"));
			String line;
			while ((line = br.readLine()) != null) {
			   String[] s = line.split(" ");
			   int id = Integer.parseInt(s[0]);
			   if (!nodeIds.contains(id)) {
				   nodes++;
				   nodeIds.add(id);
			   }
			   int con = Integer.parseInt(s[1]);
			   if (!nodeIds.contains(con)) {
				   nodes++;
				   nodeIds.add(con);
			   }
			   int[] l = {id, con, Integer.parseInt(s[2])};
			   temp.add(l);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		AntGraph g = new AntGraph(nodes, 1.0);
		for (int i=0; i< temp.size(); i++) {
			int[] elements = temp.get(i);
			g.addNode(elements[0], elements[1], elements[2]);
		}
//		g.printGraph();
		g.iterate(10000);
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Program Ran in : " + (double)totalTime/1000.0 + "seconds.");
	}

}
