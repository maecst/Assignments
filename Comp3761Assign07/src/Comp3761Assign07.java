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
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public class Comp3761Assign07 {

    private static final int MAX_VALUE = 1000000;
    private static final String fileName = "dijkstraData.txt";
    private static Graph graph;
    private static final int startingVertex = 1;
    
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        
        graph = new AdjacencyListGraph();
        createGraph();
        

        dijkstra();
		
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
            System.out.println("Graph created.");
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
        VertexComparator comparator = new VertexComparator();
        PriorityQueue<Vertex> minHeap = new PriorityQueue<Vertex>(10, comparator);
	for (int i = 1; i <= graph.getVertexCount(); i++)
	{
            Vertex currentV = graph.getVertex(i);
            currentV.setAttribute( AdjacencyListVertex.distance, MAX_VALUE );
            currentV.setAttribute( AdjacencyListVertex.prevVertex, null );
            minHeap.add(currentV);
	}
		
	Vertex currentV = graph.getVertex(startingVertex);
	currentV.setAttribute( AdjacencyListVertex.distance , 0 );
	currentV.setAttribute( AdjacencyListVertex.visited, true );
        
        
        // add some kind of loop here, to loop through the whole graph?
        int visited = 1;
        while (visited < graph.getVertexCount()) {
            minHeap.remove(currentV);
            Iterator it = graph.getEdges(currentV);
            while (it.hasNext()) {
                Edge e = (Edge) it.next();
                Vertex otherV = e.otherVertex(currentV);
                if (!(boolean) otherV.getAttribute(AdjacencyListVertex.visited)) {
                    int oldD = (int) otherV.getAttribute(AdjacencyListVertex.distance);
                    int newD = (int) e.getAttribute(AdjacencyListEdge.length)
                             + (int) currentV.getAttribute(AdjacencyListVertex.distance);
                    if (newD < oldD)
                    {
                        otherV.setAttribute(AdjacencyListVertex.distance, newD);
                        otherV.setAttribute(AdjacencyListVertex.prevVertex, currentV);
                    }
                }
            }
            //get vertex with shortest distance, update currentV
            currentV = minHeap.poll();
            currentV.setAttribute( AdjacencyListVertex.visited, true );
            visited++;
        }
    }
}

class VertexComparator implements Comparator
{
        public int compare(Object a, Object b)
        {
            Vertex v = (Vertex) a;
            Vertex otherV = (Vertex) b;
            if ((int) v.getAttribute(AdjacencyListVertex.distance) > (int) otherV.getAttribute(AdjacencyListVertex.distance)) {
                return 1;
            } else if ((int) v.getAttribute(AdjacencyListVertex.distance) < (int) otherV.getAttribute(AdjacencyListVertex.distance)) {
                return -1;
            } else {
                return 0;
            }
        }
    }