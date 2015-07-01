/**
 * Project: Comp3761Assign06
 * File: Comp3761Assign06.java
 * Date: Jun 27, 2015
 * Time: 10:23:28 AM
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * @author Gabriella Cheung, A00782867
 *
 */
public class Comp3761Assign06 {

    private static final int numOfTreeSizes = 5;
    private static ArrayList<Integer> treeSizes;
    private static Graph graph;
    /**
     * @param args
     */
    public static void main(String[] args) {
        
        treeSizes = new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0)); // initialize this using numOfTreeSizes?
        graph = new AdjacencyListGraph();
        String edge = "";
        //StringTokenizer vertices;
        int v1, v2;
        System.out.println("Creating graph from graph.txt...");
        try {
            BufferedReader br = new BufferedReader(new FileReader("graph.txt"));
            //Scanner scanner = new Scanner(new File("graph.txt"));
            
            while((edge = br.readLine()) != null) {
                //edge = scanner.nextLine();
                //vertices = new StringTokenizer(edge, "  ,.");
                //v1 = Integer.parseInt(vertices.nextToken());
                //v2 = Integer.parseInt(vertices.nextToken());
                
                String[] edgeSplit = edge.split(" ");
                v1 = Integer.parseInt(edgeSplit[0]);
                v2 = Integer.parseInt(edgeSplit[1]);
                //Vertex vertex1 = graph.addVertex(v1);
                //Vertex vertex2 = graph.addVertex(v2);
                
                //graph.addEdge(vertex1, vertex2);
                graph.addEdge(graph.addVertex(v1), graph.addVertex(v2));
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

        findTreeSizes(true);
        System.out.println(numOfTreeSizes + " largest tree sizes using DFS:");
        printTreeSizes();
        
        treeSizes = new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0));
        //System.out.println(treeSizes);
        
        findTreeSizes(false);
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
        Set<Integer> visitedTotal = new HashSet<Integer>();
        int nextUnvisited = 1;
        int size;
        while(visitedTotal.size() < graph.getVertexCount())
        { 
            while (visitedTotal.contains(nextUnvisited))
            {
                nextUnvisited++;
            }
            //System.out.println("nextUnvisited: " + nextUnvisited);
            if(dfs)
            {
                size = DFS(visitedTotal, nextUnvisited);
            } else {
                size = BFS(visitedTotal, nextUnvisited);
            }
            //System.out.println(tree.size());
            addTreeSize(size);
        }
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
    
    private static int DFS(Set<Integer> visited, int vertexName)
    {
        int numOfNodes = 0;
        Stack<Integer> st = new Stack<Integer>();
        st.push(vertexName);
        int currentV;
        while (!st.empty())
        {
            currentV = st.pop();
            if (!visited.contains(currentV))
            {
                visited.add(currentV);
                numOfNodes++;
                Vertex v = graph.getVertex(currentV);
                Iterator it = v.getEdges();
                while (it.hasNext())
                {
                    Edge e = (Edge) it.next();
                    Vertex nextV = (Vertex) e.otherVertex(v);
                    int nextVName = (int) nextV.getAttribute("Name");
                    st.push(nextVName);
                }
            }
        }
        return numOfNodes;
    }
    
    private static int BFS(Set<Integer> visited, int vertexName)
    {
        int numOfNodes = 0;
        LinkedList<Integer> q = new LinkedList<Integer>();
        q.add(vertexName);
        int currentV;
        while (q.size() != 0)
        {
            currentV = q.remove();
            if (!visited.contains(currentV))
            {
                visited.add(currentV);
                numOfNodes++;
                Vertex v = graph.getVertex(currentV);
                Iterator it = v.getEdges();
                while (it.hasNext())
                {
                    Edge e = (Edge) it.next();
                    Vertex nextV = (Vertex) e.otherVertex(v);
                    int nextVName = (int) nextV.getAttribute("Name");
                    q.add(nextVName);
                }
            }
        }
        return numOfNodes;
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
