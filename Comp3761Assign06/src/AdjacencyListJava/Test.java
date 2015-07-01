package AdjacencyListJava;

import java.util.LinkedList;

class Test
{
	public static void main( String[] args )
	{
		Graph g = new AdjacencyListGraph();
		Vertex a = g.addVertex( "a" );
		Vertex b = g.addVertex( "b" );
		Vertex c = g.addVertex( "c" );
		Vertex d = g.addVertex( "d" );
		Vertex e = g.addVertex( "e" );
		Edge ab = g.addEdge( a, b );
		Edge ac = g.addEdge( a, c );
		Edge ad = g.addEdge( a, d );
		Edge be = g.addEdge( b, e );
		Edge ce = g.addEdge( c, e );
		Edge de = g.addEdge( d, e );
		System.out.println( g );
	}
}

