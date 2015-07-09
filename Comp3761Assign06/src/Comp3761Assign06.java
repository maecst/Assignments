/**
 * Project: Comp3761Assign06
 * File: Comp3761Assign06.java
 * Author: Gabriella Cheung, Mae Yee
 * Date: Jun 27, 2015
 * Time: 10:23:28 AM
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class Comp3761Assign06 {

    private static final int numOfTreeSizes = 5;
    private static ArrayList<Integer> treeSizes;
    private static final String fileName = "graph.txt";
    private static Graph graph;
    private static int totalVisited;
    private static final int startingVertex = 1;
    /**
     * @param args
     */
    public static void main(String[] args) {
        
        graph = new AdjacencyListGraph();
        String edge;
        int v1, v2;
        String[] edgeSplit;
        Pattern pattern = Pattern.compile(" ");
        System.out.println("Creating graph from " + fileName + "...");
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            
            while((edge = br.readLine()) != null) {
                
                edgeSplit = pattern.split(edge, 0);
                v1 = Integer.parseInt(edgeSplit[0]);
                v2 = Integer.parseInt(edgeSplit[1]);
                
                Vertex vertex1 = graph.getVertex(v1);
                if (vertex1 == null)
                {
                	vertex1 = graph.addVertex(v1);
                }
                
                Vertex vertex2 = graph.getVertex(v2);
                if (vertex2 == null)
                {
                	vertex2 = graph.addVertex(v2);
                }
                graph.addEdge(vertex1, vertex2);
            }

            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("Graph created successfully.");
        boolean useDFS = true;

        treeSizes = new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0));
        findTreeSizes(useDFS);
        System.out.println(numOfTreeSizes + " largest tree sizes using DFS:");
        printTreeSizes();
        
        useDFS = false;
        treeSizes = new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0));
        
        findTreeSizes(useDFS);
        System.out.println(numOfTreeSizes + " largest tree sizes using BFS:");
        printTreeSizes();
        
        System.out.println("\nProgram completed.");
    }
    
    private static void printTreeSizes()
    {
        for (int i = treeSizes.size()-1; i > 0; i--)
        {
            System.out.print(treeSizes.get(i) + ", ");
        }
        System.out.println(treeSizes.get(0));
    }
    
    private static void findTreeSizes(boolean dfs)
    {
        System.out.println("Now running findTreeSizes using " + (dfs==true? "DFS":"BFS") + "...");
        totalVisited = 0;
        int nextUnvisited = startingVertex;
        int size;
        while(totalVisited < graph.getVertexCount())
        { 
            while ((boolean) graph.getVertex(nextUnvisited).getAttribute(AdjacencyListVertex.visited))
            {
                nextUnvisited++;
            }
            if(dfs)
            {
                size = DFS(nextUnvisited);
            } else {
                size = BFS(nextUnvisited);
            }
            totalVisited += size;
            addTreeSize(size);
        }
        resetVisited();
    }
    
    private static void addTreeSize(int treeSize)
    {
        if (treeSize > treeSizes.get(0))
        {
            treeSizes.remove(0);
            treeSizes.add(treeSize);
            Collections.sort(treeSizes);
        } 
    }
    
    private static int DFS(int vertexName)
    {
        int numOfNodes = 0;
        LinkedList<Iterator> stack = new LinkedList<Iterator>();
        Vertex currentV = graph.getVertex(vertexName);
		
		// set vertex to visited and increment counter
        currentV.setAttribute(AdjacencyListVertex.visited, true);
        numOfNodes++;
		
		stack.addLast(currentV.getAdjacentVertices()); // add list of current vertex's adjacent vertices to stack
        while (stack.size() != 0)
        {
        	Iterator it = stack.removeLast(); // get last list but don't remove yet
        	while (it.hasNext())
        	{
        		currentV = (Vertex) it.next();
        		if (!(boolean) currentV.getAttribute(AdjacencyListVertex.visited)) // if vertex has not been visited
        		{
					// set vertex to visited and increment counter
        			currentV.setAttribute(AdjacencyListVertex.visited, true);
        			numOfNodes++;
					
					// add list of current vertex's adjacent vertices to stack
        			stack.addLast(currentV.getAdjacentVertices());
        		}
        	}
        }
        return numOfNodes;
    }
    
    private static int BFS(int vertexName)
    {
        int numOfNodes = 0;
        LinkedList<Iterator> queue = new LinkedList<Iterator>();
        Vertex currentV = graph.getVertex(vertexName);
		
        // set vertex to visited and increment counter
        currentV.setAttribute(AdjacencyListVertex.visited, true);
        numOfNodes++;
		
		queue.addLast(currentV.getAdjacentVertices()); // add list of current vertex's adjacent vertices to queue
        while (queue.size() != 0)
        {
        	Iterator it = queue.removeFirst();
        	while (it.hasNext())
        	{
        		currentV = (Vertex) it.next();
        		if (!(boolean) currentV.getAttribute(AdjacencyListVertex.visited)) // if vertex has not been visited
        		{
					// set vertex to visited and increment counter
        			currentV.setAttribute(AdjacencyListVertex.visited, true);
        			numOfNodes++;
					
					// add list of current vertex's adjacent vertices to queue
        			queue.addLast(currentV.getAdjacentVertices());
        		}
        	}
        }
        return numOfNodes;
    }
    
    private static void resetVisited()
    {
        for (int i = 1; i <= graph.getVertexCount(); i++)
        {
            graph.getVertex(i).setAttribute(AdjacencyListVertex.visited, false);
        }
    }
}
