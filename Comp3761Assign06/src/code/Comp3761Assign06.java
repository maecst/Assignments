/**
 * Project: Comp3761Assign06
 * File: Comp3761Assign06.java
 * Date: Jun 27, 2015
 * Time: 10:23:28 AM
 */
package code;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import AdjacencyListJava.*;

/**
 * @author Gabriella Cheung, A00782867
 *
 */
public class Comp3761Assign06 {

    
    private static ArrayList<Integer> treeSizes;
    private static AdjacencyListGraph graph;
    /**
     * @param args
     */
    public static void main(String[] args) {
        
        treeSizes = new ArrayList<Integer>();

        ArrayList<String> edges = readFile("graph.txt");
        
        graph = new AdjacencyListGraph();
        createGraph(edges);
        
        // after adding edges, perform DFS and BFS on the graph
        // keep track of number of nodes of each tree, add to treeSizes
        // sort treeSizes
    }
    
    private static void createGraph(ArrayList<String> edges)
    {
        for (String edge : edges)
        {
            StringTokenizer vertices = new StringTokenizer(edge, "  ,.");
            int v1 = Integer.parseInt(vertices.nextToken());
            int v2 = Integer.parseInt(vertices.nextToken());
            
            Vertex vertex1 = graph.addVertex(v1);
            Vertex vertex2 = graph.addVertex(v2);
            
            graph.addEdge(vertex1, vertex2);
        }
        
        System.out.println(graph.getEdgeCount());
        System.out.println(graph.getVertexCount());
        
    }
    
    private static ArrayList<String> readFile(String fileName)
    {
        ArrayList<String> lines = new ArrayList<String>();
        try {
            Scanner scanner = new Scanner(new File(fileName));
            
            while(scanner.hasNext()) {
                lines.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        return lines;
    }

}
