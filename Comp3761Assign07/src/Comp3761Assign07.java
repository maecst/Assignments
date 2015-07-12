/**
 * Project: Comp3761Assign07
 * File: Comp3761Assign07.java
 * Author: Gabriella Cheung, Mae Yee
 * Date: Jun 27, 2015
 * Time: 10:23:28 AM
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;

public class Comp3761Assign07 {

    private static final int MAX_VALUE = 1000000;
    private static final String fileName = "dijkstraData.txt";
    private static Graph graph;
    private static final int startingVertex = 1;
    private static final int[] selectedVertices = {7, 37, 59, 82, 99, 115, 133, 165, 188, 197 };
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        
        graph = new AdjacencyListGraph();
        createGraph();
        dijkstra();
	printSelectVertices();
        System.out.println("\nProgram completed.");
    }
	
    private static void createGraph()
    {
        String line;
        int v1, v2;
		
        System.out.println("Creating graph from " + fileName + "...");
		
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
         
            while((line = br.readLine()) != null) {                
                String[] edgeSplit = line.split("\t");
                v1 = Integer.parseInt(edgeSplit[0]);
                Vertex vertex1 = graph.getVertex(v1);
                if (vertex1 == null) {
                    vertex1 = graph.addVertex(v1);
                }
				
                for (int i = 1; i < edgeSplit.length; i++) {
                    String[] edge = edgeSplit[i].split(",");
                    v2 = Integer.parseInt(edge[0]);
                    Vertex vertex2 = graph.getVertex(v2);
                    if (vertex2 == null) {
                        vertex2 = graph.addVertex(v2);
                    }
                    int length = Integer.parseInt(edge[1]);
					
                    //create edge and add into graph
                    Edge e = new AdjacencyListEdge(vertex1, vertex2, length);
                    graph.addEdge(e);
                }
            }
            br.close();
            System.out.println("Graph created successfully.\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void dijkstra()
    {
        System.out.println("Running Dijkstra's algorithm on graph...");
        //VertexComparator comparator = new VertexComparator();
        PriorityQueue<Vertex> minHeap = new PriorityQueue<Vertex>(200, comparator);
	for (int i = 1; i <= graph.getVertexCount(); i++)
	{
            Vertex currentV = graph.getVertex(i);
            currentV.setAttribute( AdjacencyListVertex.visited, false );
            currentV.setAttribute( AdjacencyListVertex.distance, MAX_VALUE );
            currentV.setAttribute( AdjacencyListVertex.prevVertex, null );
            minHeap.add(currentV);
	}
        
	Vertex currentV = graph.getVertex(startingVertex);
	currentV.setAttribute( AdjacencyListVertex.distance , 0 );
	currentV.setAttribute( AdjacencyListVertex.visited, true );
        minHeap.remove(currentV);
        
        int visited = 1;
        while (visited < graph.getVertexCount()) {
            Iterator it = graph.getEdges(currentV);
            while (it.hasNext()) {
                Edge e = (Edge) it.next();
                Vertex otherV = e.otherVertex(currentV);
                if (!(boolean) otherV.getAttribute(AdjacencyListVertex.visited)) {
                    minHeap.remove(otherV);
                    int oldD = (int) otherV.getAttribute(AdjacencyListVertex.distance);
                    int newD = (int) e.getAttribute(AdjacencyListEdge.length)
                             + (int) currentV.getAttribute(AdjacencyListVertex.distance);
                    if (newD < oldD)
                    {
                        otherV.setAttribute(AdjacencyListVertex.distance, newD);
                        otherV.setAttribute(AdjacencyListVertex.prevVertex, currentV);
                    }
                    minHeap.add(otherV);
                }
            }
            //get vertex with shortest distance, update currentV
            currentV = minHeap.poll();
            currentV.setAttribute( AdjacencyListVertex.visited, true );
            visited++;
        }
        System.out.println("Finished running Dijkstra's algorithm.\n");
    }
    
    private static void printSelectVertices()
    {
        System.out.println("Shortest distance from Vertex " + startingVertex + " to:");
        for (int i = 0; i < selectedVertices.length; i++)
        {
            int vName = selectedVertices[i];
            System.out.print("Vertex " + vName + ": ");
            Vertex v = graph.getVertex(vName);
            System.out.print(v.getAttribute(AdjacencyListVertex.distance) 
                    + ", previous vertex is: ");
            Vertex prevV = (Vertex) v.getAttribute(AdjacencyListVertex.prevVertex);
            System.out.println(prevV.getAttribute(AdjacencyListVertex.name));
        }
    }
    
    public static Comparator<Vertex> comparator = new Comparator<Vertex>(){
         
        @Override
        public int compare(Vertex a, Vertex b)
        {
            int aDistance = (int) a.getAttribute(AdjacencyListVertex.distance);
            int bDistance = (int) b.getAttribute(AdjacencyListVertex.distance);
            return aDistance - bDistance;
        }
    };
}