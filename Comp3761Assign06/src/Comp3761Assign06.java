/**
 * Project: Comp3761Assign06
 * File: Comp3761Assign06.java
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

/**
 * @author Gabriella Cheung, A00782867
 *
 */
public class Comp3761Assign06 {

    private static final int numOfTreeSizes = 5;
    private static ArrayList<Integer> treeSizes;
    private static final String fileName = "graph.txt";
    private static Graph graph;
    private static int totalVisited = 0;
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
                //graph.addEdge(graph.addVertex(edgeSplit[0]), graph.addVertex(edgeSplit[1]));
                //graph.addEdge(graph.addVertex(v1), graph.addVertex(v2));
                //System.out.println(graph.getEdgeCount());
                //System.out.println(graph.getVertexCount());
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

        treeSizes = new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0)); // initialize this using numOfTreeSizes?
        findTreeSizes(useDFS);
        System.out.println(numOfTreeSizes + " largest tree sizes using DFS:");
        printTreeSizes();
        
        useDFS = false;
        treeSizes = new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0));
        //System.out.println(treeSizes);
        
        findTreeSizes(useDFS);
        System.out.println(numOfTreeSizes + " largest tree sizes using BFS:");
        printTreeSizes();
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
        System.out.println("Now running findTreeSizes using " + (dfs==true? "DFS":"BFS"));
        totalVisited = 0;
        int nextUnvisited = 1;
        int size;
        while(totalVisited < graph.getVertexCount())
        { 
            while ((boolean) graph.getVertex(nextUnvisited).getAttribute(AdjacencyListVertex.visited))
            {
                nextUnvisited++;
            }
            System.out.println("nextUnvisited: " + nextUnvisited);
            if(dfs)
            {
                size = DFS(nextUnvisited);
            } else {
                size = BFS(nextUnvisited);
            }
            //System.out.println(tree.size());
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
        LinkedList<Integer> stack = new LinkedList<Integer>();
        stack.addFirst(vertexName);
        while (stack.size() != 0)
        {
            Vertex currentV = graph.getVertex(stack.removeLast());
            if (!(boolean) currentV.getAttribute(AdjacencyListVertex.visited))
            {
                currentV.setAttribute(AdjacencyListVertex.visited, true);
                numOfNodes++;
                totalVisited++;
                Iterator it = currentV.getAdjacentVertices();
                while (it.hasNext())
                {
                	Vertex nextV = (Vertex) it.next();
                    int nextVName = (int) nextV.getAttribute(AdjacencyListVertex.name);
                    stack.addFirst(nextVName);
                }
            }
        }
        return numOfNodes;
    }
    
    private static int BFS(int vertexName)
    {
        int numOfNodes = 0;
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.addLast(vertexName);
        int currentV;
        while (queue.size() != 0)
        {
            currentV = queue.removeFirst();
            Vertex v = graph.getVertex(currentV);
            if (!(boolean) v.getAttribute(AdjacencyListVertex.visited))
            {
                v.setAttribute(AdjacencyListVertex.visited, true);
                numOfNodes++;
                totalVisited++;
                Iterator it = v.getAdjacentVertices();
                while (it.hasNext())
                {
                	Vertex nextV = (Vertex) it.next();
                    int nextVName = (int) nextV.getAttribute(AdjacencyListVertex.name);
                    queue.addLast(nextVName);
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
