package main;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class Solucion {

	private double[][] flow;
	private double[][] capacity; 
	private int[] parent; 
	private boolean[] visited; 
	private int n;

	public double[][] getflows()
	{
		return flow;
	}
	public Solucion(int numOfVerticles) {
		this.n = numOfVerticles;
		this.flow = new double[n][n];
		this.capacity = new double[n][n];
		this.parent = new int[n];
		this.visited = new boolean[n];
	}

	public void addEdge(int from, int to, long capacity) 
	{
		this.capacity[from][to] += capacity;
	}

	public double getMaxFlow(int origen, int destino)
	{
		while(true)
		{
			Queue<Integer> queue = new ArrayDeque<Integer>();
			queue.add(origen);
			Arrays.fill(visited, false);
			visited[origen] = true;
			boolean llega = false;

			int actual;
			while(!queue.isEmpty()) 
			{
				actual = queue.peek();
				if(actual == destino)
				{
					llega = true;
					break;
				}
				queue.remove();
				for (int i = 0; i < n; i++) 
				{
					if(!visited[i] && capacity[actual][i] >  flow[actual][i])
					{
						visited[i] = true;
						queue.add(i);
						parent[i] = actual;
					}
				}
			}

			if(!llega) break;
			
			double temp = capacity[parent[destino]][destino] - flow[parent[destino]][destino];

			for (int i = destino; i != origen; i = parent[i])
				temp = Math.min(temp, (capacity[parent[i]][i] - flow[parent[i]][i]));

			for (int i = destino; i != origen; i = parent[i]) {
				flow[parent[i]][i] += temp;
				flow[i][parent[i]] -= temp;
			}
		}
		long result = 0;
		for (int i = 0; i < n; ++i)
			result += flow[i][destino];
		return (result);
	}
}
